package survivalGame;

import java.util.ArrayList;
import java.util.List;

public final class Updater implements Runnable {
    private static Updater instance;
    private static List<Updatable> updatables = new ArrayList<>();
    
    final int fps = 120;
    final long frameTime = 1000 / fps; // 16 ms target
    
    
    private Updater() {}

    public static Updater getInstance() {
        if (instance == null) {
            instance = new Updater();
        }
        return instance;
    }


    public static void register(Updatable updatable) {
    	 if (updatable == null) {
    	        throw new IllegalArgumentException("Trying to register null!");
    	    }
        updatables.add(updatable);
    }
    
    @Override
    public void run() {
       
    	long lastTime = System.currentTimeMillis();
        while (true) {
            long now = System.currentTimeMillis();
            long delta = now - lastTime;
            lastTime = now;
            
            //delta means difference, fixed update is updating on time passed, not on performance. 
            updateAll(); 
            fixedUpdateAll(delta);
          //If frameTime = 16 ms (for 60 FPS) And updateAll() took 6 ms Then you sleep for 10 ms to keep consistent framerate 
            long sleep = frameTime - (System.currentTimeMillis() - now);
            if (sleep > 0) {
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void updateAll() {
        

        for (Updatable updatable : updatables) {
            updatable.update();  
        }
        
    }
    public void fixedUpdateAll(long delta) {
        for (Updatable updatable : updatables) {
            updatable.fixedUpdate(delta);  
        }
     
    }
}

