package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.MouseMotionListener;
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
import survivalGame.WorldInfo;


public final class GameGraphics extends JPanel implements Updatable {
	
	private static TextureManager textureManager;
	private static TreeMap<Integer, List<WorldRenderable>> WorldRenderLayers = new TreeMap<>();
	private static List<UIRenderable> UIRenderLayers = new ArrayList<>();
	
	
	public static TileChunk[] chunks;
	
	AffineTransform uiTransform;
	
	public static int worldSize;
    static int worldPixelSize;

	public final static int TILESIZE = 100;
	public static int chunkSize;
	
	private static float cameraZoom = 1;
	
	public static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
	public static float getCameraZoom() {
		return cameraZoom;
	}


	public static void setCameraZoom(float cameraZoom) {
		GameGraphics.cameraZoom = cameraZoom;
	}
	
	
	public static void registerWorldObj(WorldRenderable toRender, int layer) {
		WorldRenderLayers.computeIfAbsent(layer, k -> new ArrayList<>()).add(toRender);
	}
	
	public static void registerUI(UIRenderable toRender) {
		UIRenderLayers.add(toRender);
	}
	
	public static void registerAll(List<WorldRenderable> toRender, int layer) {
		WorldRenderLayers.computeIfAbsent(layer, k -> new ArrayList<>()).addAll(toRender);
	}

	private Player player;
	private static int[] originOffset = new int[2];
	
	public GameGraphics() {
		this.setFocusable(true);
	}

	public void init(WorldInfo info)
    {		

		GameGraphics.chunks = info.chunks;
		GameGraphics.worldSize = info.worldSize;
		GameGraphics.chunkSize = info.chunkSize;
		worldPixelSize = (worldSize * GameGraphics.TILESIZE) - 2600;
		//rough estimate
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
        
        // save default transform.
        if (uiTransform == null) {
            uiTransform = g2d.getTransform();
        }
        
        g2d.setStroke(new BasicStroke(4));
        //background color
        g2d.setColor(new Color(0,0,0));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        //translates world objects by player position.
        translateByPlayerView(g2d);
        g2d.scale(cameraZoom,cameraZoom); //must translate then scale otherwise everything will break :(

        renderWorld(g2d);
        
        //sets transform back to default so that UI doesn't get affected by player position or zoom
        g2d.setTransform(uiTransform);
        
        renderUI(g2d);
        
        Font largeFont = new Font("Arial", Font.BOLD, 45);
        g2d.setFont(largeFont);
        g2d.setColor(Color.BLUE);
        g2d.drawString(originOffset[0] + ", " + originOffset[1] + "  Z: " +  cameraZoom, 222, 222);
        
       
    }
    private void translateByPlayerView(Graphics2D g2d) {
    	//discontinue displacing originOffset(player position) when out of bounds
    	if (-player.getXCoord() >= 0 && -player.getXCoord() < worldPixelSize) {
        	 originOffset[0] = (int) (-player.getXCoord() );
        }
        if (-player.getYCoord() >= 0 && -player.getYCoord() < worldPixelSize) {
        	 originOffset[1] =  (int) (-player.getYCoord());
        }
      
        
        g2d.translate(-originOffset[0] * cameraZoom + this.getWidth() / 2, -originOffset[1]  * cameraZoom + this.getHeight() / 2 );
    }
    private void renderWorld(Graphics2D g) {

    	for (TileChunk chunk : chunks) {
    		chunk.renderChunk(g,this);
		}

    	for (int layer : WorldRenderLayers.keySet()) {
    	
    		//Loops through every tile in your view
    		 for (int y = 0; y < worldSize; y++) {
     			for (int x = 0 ; x < worldSize; x++) {

     				int index = ((y * worldSize) + x - 1);
     				if (index > WorldRenderLayers.get(layer).size() - 1|| index <= -1 ){
     					continue;
     				}
     				WorldRenderLayers.get(layer).get(index).render(g, this);	
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
    	textureManager = textureM;
    }
	
	public static TextureManager getTextureManager() {
		return textureManager;
	}

	@Override
	public void fixedUpdate(long delta) {
		// TODO Auto-generated method stub
		
	}
	public static int[] getOriginOffset() {
		return originOffset;
	}
	

}
