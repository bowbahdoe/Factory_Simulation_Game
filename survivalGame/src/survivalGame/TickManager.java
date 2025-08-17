package survivalGame;

import java.util.ArrayList;
import java.util.List;

public final class TickManager implements Updatable{
	
	private static List<ITickable> toTick = new ArrayList<>();
	private static TickManager TickManagerInstance;
	private int tick = 0;
	private static final int tickrate = 300;
	public static TickManager getInstance() {
		if (TickManagerInstance == null) {
            Updater.getInstance();
			TickManagerInstance = new TickManager();
			Updater.register(TickManagerInstance);
	    }
        return TickManagerInstance;
    }
	
	
	public static void TickAll() {
		for (ITickable tick : toTick) {
			tick.onTick();
			 
		}
		
	}

	public static void register(ITickable obj) {
		toTick.add(obj);
	}

	@Override
	public void update() {
		
		
	}

	@Override
	public void fixedUpdate(long delta) {
		tick += tickrate * delta / 1000f;
		if (tick > 250) {
			//System.out.println("ticked +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			TickAll();
			tick = 0;
		}
		
	}
}
