package zach.chase;

import java.util.List;
import java.util.ArrayList;

/** 
 * Manages a group of clocks
 * <p>
 * Code stolen/translated from 
 * @author Alex Pantaleev */
public class Clocks {
	private List<Clock> clocks = new ArrayList<>();
	private long lastCycles;
	private long currentCycles;

	/** Using nanosecond based time */
	public static final double Hz = 1000000000;

	public static long secondsToCycles(double seconds) {
		return (long)(seconds * Hz);
	}

	public static double cyclesToSeconds(long cycles) {
		return cycles / Hz;
	}

	/** Adds a clock and returns a handle to it */
	public Clock Clock(double startTime) {
		Clock clock = new Clock(startTime);
		clocks.add(clock);
		return clock;
	}

	/** And we can remove them too */
	public boolean removeClock(Clock clock) {
		return clocks.remove(clock);
	}

	/** Should be called before the main loop, updates time */
	public void update() {
		currentCycles = System.nanoTime();
	}

	/** Should be called every main loop iteration */
	public void tick() {
		lastCycles = currentCycles;
		update();
		for(Clock clock : clocks) {
			clock.tick(currentCycles - lastCycles);
		}
	}
}
