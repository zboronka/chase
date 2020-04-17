package zach.chase;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/** 
 * Base class for movable objects
 * @author Zach Boronka 
 */
public class Movable {
	private Vector2d pos;
	private Vector2d v;
	private double r;

	Movable(Vector2d pos, double r) {
		this.pos = pos;
		this.r = r;

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

	public void addV(Vector2d velocity) {
		v = v.plus(velocity);
	}

	public void render() {
		Graphics.renderer.begin(ShapeType.Filled);
		Graphics.renderer.circle((float)pos.x(), (float)pos.y(), (float)r);
		Graphics.renderer.end();
	}
}
