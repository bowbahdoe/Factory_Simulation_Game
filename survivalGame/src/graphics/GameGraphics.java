package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

import javax.swing.JPanel;

import survivalGame.InputListener;
import survivalGame.Player;
import survivalGame.TileChunk;
import survivalGame.Updatable;
import survivalGame.Updater;


public final class GameGraphics extends JPanel implements Updatable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static GameGraphics graphicsInstance;
	private static TextureManager textureManage;
	private static TreeMap<Integer, List<WorldRenderable>> WorldRenderLayers = new TreeMap<>();
	private static List<UIRenderable> UIRenderLayers = new ArrayList<>();
	
	
	public final TileChunk[] chunks;
	
	AffineTransform uiTransform;
	
	public final int worldSize;
    final int worldPixelSize;

	public final static int TILESIZE = 100;
	public final int chunkSize;
	
	private float cameraZoom = 1;
	
	public final int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	public final int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
	public float getCameraZoom() {
		return cameraZoom;
	}


	public void setCameraZoom(float cameraZoom) {
		this.cameraZoom = cameraZoom;
	}
	
	//singleton lolololol
	public static GameGraphics getInstance() {
        return graphicsInstance;
    }
	
	
	public void registerWorldObj(WorldRenderable toRender, int layer) {
		WorldRenderLayers.computeIfAbsent(layer, k -> new ArrayList<>()).add(toRender);
	}
	
	public void registerUI(UIRenderable toRender) {
		UIRenderLayers.add(toRender);
	}
	
	public void registerAll(List<WorldRenderable> toRender, int layer) {
		WorldRenderLayers.computeIfAbsent(layer, k -> new ArrayList<>()).addAll(toRender);
	}

	private Player player;
	private int[] originOffset = new int[2];
	
	public GameGraphics(TileChunk[] chunks, int worldSize, int chunkSize)
    {		
		graphicsInstance = this;
		this.chunks = chunks;
		this.worldSize = worldSize;
		this.chunkSize = chunkSize;
		worldPixelSize = (worldSize * GameGraphics.TILESIZE) - 2600;
		//rough estimate
		
    	Updater.getInstance();
		Updater.register(this);
		
		this.setFocusable(true);  // Make sure the panel can receive focus
		WorldRenderLayers.put(0,new ArrayList<>());
		for (int layer : WorldRenderLayers.keySet()) {
			WorldRenderLayers.get(layer).sort(Comparator.comparing(WorldRenderable::getY));
		}
		
    }
	public void attachPlayer(Player player) {
		this.player = player;
		InputListener input = InputListener.getInstance();
		this.addKeyListener(input);  // Adds key listener to the panel
		this.addMouseListener(input);
		this.addMouseWheelListener(input);
	}
    @Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        if (uiTransform == null) {
            uiTransform = g2d.getTransform();
        }
        
        
        g2d.setStroke(new BasicStroke(4));
        g2d.setColor(new Color(0,0,0));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        
        translateByPlayerView(g2d);
        g2d.scale(cameraZoom,cameraZoom); //Needs to be other way around, therefore apply zoom to origin offset for player :/

        renderWorld(g2d);
        
        g2d.setTransform(uiTransform);
        //g2d.setClip(0,0,200,500);
        
        renderUI(g2d);
        
        Font largeFont = new Font("Arial", Font.BOLD, 45);
        g2d.setFont(largeFont);
        g2d.setColor(Color.BLUE);
        g2d.drawString(originOffset[0] + ", " + originOffset[1] + "  Z: " + cameraZoom, 222, 222);
        //g2d.fillRect(this.getWidth() / 2 - 5, this.getHeight() / 2 - 5, 10, 10);
        
       
    }
    private void translateByPlayerView(Graphics2D g2d) {
    	 if (-player.getXCoord() >= 0 && -player.getXCoord() < worldPixelSize || true) {
        	 originOffset[0] = (int) (-player.getXCoord() );
        }
        if (-player.getYCoord() >= 0 && -player.getYCoord() < worldPixelSize) {
        	 originOffset[1] =  (int) (-player.getYCoord());
        }
        originOffset[0] = (int) (-player.getXCoord() );
        originOffset[1] =  (int) (-player.getYCoord());
        
        g2d.translate(-originOffset[0] * cameraZoom + this.getWidth() / 2, -originOffset[1]  * cameraZoom + this.getHeight() / 2 );
    }
    private void renderWorld(Graphics2D g) {

    	for (TileChunk chunk : chunks) {
    		chunk.renderChunk(g,this);
		}
    	
    	
    	for (int layer : WorldRenderLayers.keySet()) {
    	
    		//Loops through every tile in your view
    		 for (int y = ((originOffset[1] / TILESIZE) - chunkSize * TILESIZE) / TILESIZE; y < worldSize; y++) {
     			for (int x = ((originOffset[0] / TILESIZE ) - chunkSize * TILESIZE) / TILESIZE ; x < worldSize; x++) {

     				if (((y * worldSize) + x - 1) > WorldRenderLayers.get(layer).size()  - 1|| (y * worldSize) + x <= 0 ){
     					continue;
     				}
     				WorldRenderLayers.get(layer).get((y * worldSize) + x - 1).render(g, this);	
     		}
     		} 
    	}
    }
    
    private void renderUI(Graphics2D g) {
    	
    	for (UIRenderable ui : UIRenderLayers) {
	    	if (ui.isActive()) {
	    		ui.renderUI(g, this);
	    	}
	    }
   	}
    
    public List<UIRenderable> getUILayers() {
    	return UIRenderLayers;
    }
	@Override
	public void update() {
		this.repaint();
		
	}
	
	public void addOnTextureManager(TextureManager textureM) {
    	textureManage = textureM;
    }
	
	public static TextureManager getTextureManager() {
		return textureManage;
	}

	@Override
	public void fixedUpdate(long delta) {
		// TODO Auto-generated method stub
		
	}
	public int[] getOriginOffset() {
		return originOffset;
	}
	

}
