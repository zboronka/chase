package zach.chase;

/** 
 * Simple clock
 * <p>
 * Code stolen/translated from 
 * @author Alex Pantaleev */
public class Clock {
	private long cycles;
	private boolean paused = false;

	Clock(double startTime) {
		set(startTime);
	}

	public void tick(long delta) {
		if(!paused) cycles += delta;
	}

	public double time() {
		return Clocks.cyclesToSeconds(cycles);
	}

	public void set(double time) {
		cycles = Clocks.secondsToCycles(time);
	}

	public double minus(Clock other) {
		return time() - other.time();
	}
}
