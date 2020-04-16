package zach.chase;

public class Movable {
	public Vector2d pos;
	
	private Vector2d v;
	private float r;

	Movable(Vector2d pos, float r) {
		this.pos = pos;
		this.r = r;

		v = new Vector2d(0, 0);
	}

	public Vector2d v() {
		return v;
	}

	public float r() {
		return r;
	}
}
