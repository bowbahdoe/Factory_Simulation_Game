package survivalGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public final class GameGraphics extends JPanel implements Updatable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static GameGraphics graphicsInstance;
	private static TextureManager textureManage;
	private static TreeMap<Integer, List<RenderComponent>> renderLayers = new TreeMap<>();
	private static Map<Integer, List<RenderComponent>> dynamicRenderLayers = new HashMap<>();//where y value is key
	
	final TileChunk[] chunks;
	
	AffineTransform uiTransform;
	
	final int worldSize;
    final int worldPixelSize;
	final int tileSize;
	final int chunkSize;
	//singleton lolololol
	public static GameGraphics getInstance() {
        return graphicsInstance;
    }
	
	public static void register(RenderComponent toRender, int layer) {
		renderLayers.computeIfAbsent(layer, k -> new ArrayList<>()).add(toRender);
	}
	public static void registerDynamic(RenderComponent toRender, int layer) {
		dynamicRenderLayers.computeIfAbsent(layer, k -> new ArrayList<>()).add(toRender);
	}
	public static void registerAll(List<RenderComponent> toRender, int layer) {
		renderLayers.computeIfAbsent(layer, k -> new ArrayList<>()).addAll(toRender);
	}

	private Player player;
	private int[] originOffset = new int[2];
	
	public GameGraphics(TileChunk[] chunks, Player player, int worldSize, int chunkSize , int tilePixelSize)
    {		
		graphicsInstance = this;
		this.player = player;
		this.chunks = chunks;
		this.worldSize = worldSize;
		this.tileSize = tilePixelSize;
		this.chunkSize = chunkSize;
		worldPixelSize = (worldSize * tilePixelSize) - 2600;
		//rough estimate
		
		
    	Updater.getInstance();
		Updater.register(this);
		InputListener input = player.input;
		this.addKeyListener(input);  // Adds key listener to the panel
		this.setFocusable(true);  // Make sure the panel can receive focus
		renderLayers.put(0,new ArrayList<>());
		for (int layer : renderLayers.keySet()) {
			renderLayers.get(layer).sort(Comparator.comparing(RenderComponent::getY));
		}
	
    }
	
    @Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        if (uiTransform == null) {
            uiTransform = g2d.getTransform();
        }
        
        g2d.setStroke(new BasicStroke(4));
        
        
        if (-player.getXCoord() >= 0 && -player.getXCoord() < worldPixelSize ) {
        	 originOffset[0] = (int) -player.getXCoord();
        }
        if (-player.getYCoord() >= 0 && -player.getYCoord() < worldPixelSize ) {
        	 originOffset[1] = (int) -player.getYCoord();
        }
        g2d.translate(-originOffset[0], -originOffset[1]);
       
        
        g2d.setColor(new Color(0,0,0));
        
        renderAll(g2d);
        
        
        g2d.setTransform(uiTransform);
        Font largeFont = new Font("Arial", Font.BOLD, 50);
        g2d.setFont(largeFont);
        g2d.setColor(Color.BLUE);
        g2d.drawString(originOffset[0] + ", " + originOffset[1], 250, 250);
        //g2d.fillRect(this.getWidth() / 2 - 5, this.getHeight() / 2 - 5, 10, 10);
        
       
    }
    private void renderAll(Graphics2D g) {
    	//for dynamic layers:
    	//render all layers, but also check their y axis,
    	//check if that y axis is one of the keys to the dynamic render layers map
    	//render it after the y axis of static elements been rendered. 
    	for (TileChunk chunk : chunks) {
    		chunk.activateChunk(g,this);
		}
    	
    	
    	for (int layer : renderLayers.keySet()) {
    		//btw the 6 is the chunksize, needs to be variable next time, also 100 is tilesize
    	
    		 for (int y = ((originOffset[1] / tileSize) - chunkSize * tileSize) / tileSize; y < worldSize; y++) {
     			for (int x = ((originOffset[0] / tileSize) - chunkSize * tileSize) / tileSize ; x < worldSize; x++) {

     				if (((y * worldSize) + x - 1) > renderLayers.get(layer).size()  - 1|| (y * worldSize) + x <= 0 ){
     					continue;
     				}
     				renderLayers.get(layer).get((y * worldSize) + x - 1).render(g, this);
     				
     				
     		}
     		} 
    	}
    	
    
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
