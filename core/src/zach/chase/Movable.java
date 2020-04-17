package zach.chase;

/** 
 * Base class for movable objects
 * @author Zach Boronka 
 */
public class Movable {
	private Vector2d pos;
	private double mass;
	private Vector2d v;
	private double r;

	Movable(Vector2d pos, double r, double mass) {
		this.pos = pos;
		this.r = r;
		this.mass = mass;

		v = new Vector2d(0, 0);
	}

	public Vector2d pos() {
		return pos;
	}

	public Vector2d v() {
		return v;
	}

	public double r() {
		return r;
	}

	public void update(double delta) {
		pos = pos.plus(v);
	}

	// TODO: this isn't correct, we need time for force
	public void addForce(Vector2d force) {
		v = v.times(mass).plus(force);
	}
}
