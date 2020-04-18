package zach.chase;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/** 
 * Base class for movable objects
 * @author Zach Boronka 
 */
public class Movable implements GameObject {
	private Vector2d pos;
	private Vector2d dir;
	private Vector2d v;
	private double r;

	protected boolean up = true, left = true, down = true, right = true;

	Movable(Vector2d pos, double r) {
		this.pos = pos;
		this.r = r;

		v = new Vector2d(0, 0);
		dir = new Vector2d(1, 0);
	}

	Movable(Vector2d pos, Vector2d dir, double r) {
		this.pos = pos;
		this.dir = dir;
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

	public void addV(Vector2d velocity) {
		v = v.plus(velocity);
	}

	public void addV(double x, double y) {
		v = v.plus(new Vector2d(x, y));
	}

	public void slow() {
		v = v.plus(v.negative().times(0.1));
	}

	public void stop() {
		v = new Vector2d(0, 0);
	}

	public void thrust(double amount) {
		addV(dir.times(amount));
	}

	public void rotate(double angle) {
		dir = dir.rotate(angle);
	}

	public void update(double delta) {
		if(pos.x() <= -Game.WIDTH/2+r) {
			left = false;
			v = new Vector2d(0, 0);
			pos = new Vector2d(-Game.WIDTH/2+r+0.1, pos.y());
		} else {
			left = true;
		}
		if(pos.x() >= Game.WIDTH/2-r) {
			right = false;
			v = new Vector2d(0, 0);
			pos = new Vector2d(Game.WIDTH/2-r-0.1, pos.y());
		} else {
			right = true;
		}
		if(pos.y() >= Game.HEIGHT/2-r) {
			up = false;
			v = new Vector2d(0, 0);
			pos = new Vector2d(pos.x(), Game.HEIGHT/2-r-0.1);
		} else {
			up = true;
		}
		if(pos.y() <= -Game.HEIGHT/2+r) {
			down = false;
			v = new Vector2d(0, 0);
			pos = new Vector2d(pos.x(), -Game.HEIGHT/2+r+0.1);
		} else {
			down = true;
		}

		pos = pos.plus(v);
	}

	public void render() {
		Graphics.renderer.begin(ShapeType.Filled);
		Graphics.renderer.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		Graphics.renderer.circle((float)pos.x(), (float)pos.y(), (float)r);
		Graphics.renderer.end();

		Graphics.renderer.begin(ShapeType.Line);
		Graphics.renderer.setColor(0.0f, 0.0f, 0.0f, 1.0f);
		Graphics.renderer.line(pos.toVector2(), pos.plus(dir.times(r)).toVector2());
		Graphics.renderer.end();
	}
}
