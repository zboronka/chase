package zach.chase;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import zach.chase.views.MainScreen;

/** 
 * Base class for movable objects
 * @author Zach Boronka 
 */
public class Movable implements GameObject {
	protected Vector2d pos;
	protected Vector2d dir;
	protected Vector2d v;
	protected double r;
	protected double max_speed;

	protected boolean up = true, left = true, down = true, right = true;

	public Movable(Vector2d pos, Vector2d dir, double r, double max_speed) {
		this.pos = pos;
		this.dir = dir;
		this.r = r;
		this.max_speed = max_speed;

		v = new Vector2d(0, 0);
	}

	public Vector2d pos() {
		return pos;
	}

	public Vector2d dir() {
		return dir;
	}

	public Vector2d v() {
		return v;
	}

	public double r() {
		return r;
	}

	public void addV(Vector2d velocity) {
		v = v.plus(velocity);
		clampSpeed();
	}

	public void addV(double x, double y) {
		v = v.plus(new Vector2d(x, y));
		clampSpeed();
	}

	private void clampSpeed() {
		if(v.length() > max_speed)
			v = v.normalize().times(max_speed);
	}

	public void slow() {
		slow(0.1);
	}

	public void slow(double speed) {
		v = v.plus(v.negative().times(speed));
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

	public void seek(Vector2d target) {
		Vector2d desired_v = pos.minus(target).normalize().times(max_speed);
		Vector2d steering = desired_v.minus(v);

		double des_a = Collidables.toRadians(Math.atan2(desired_v.y(), desired_v.x()));
		double dir_a = Collidables.toRadians(Math.atan2(dir.y(), dir.x()));
		double diff = Collidables.toAtan2(dir_a - des_a);

		double rate = diff * 0.03;

		rotate(rate);
		thrust(0.1);
	}

	public void update(double delta) {
		if(pos.x() <= -MainScreen.WIDTH/2+r) {
			left = false;
			v = new Vector2d(0, v.y());
			pos = new Vector2d(-MainScreen.WIDTH/2+r+0.1, pos.y());
		} else {
			left = true;
		}
		if(pos.x() >= MainScreen.WIDTH/2-r) {
			right = false;
			v = new Vector2d(0, v.y());
			pos = new Vector2d(MainScreen.WIDTH/2-r-0.1, pos.y());
		} else {
			right = true;
		}
		if(pos.y() >= MainScreen.HEIGHT/2-r) {
			up = false;
			v = new Vector2d(v.x(), 0);
			pos = new Vector2d(pos.x(), MainScreen.HEIGHT/2-r-0.1);
		} else {
			up = true;
		}
		if(pos.y() <= -MainScreen.HEIGHT/2+r) {
			down = false;
			v = new Vector2d(v.x(), 0);
			pos = new Vector2d(pos.x(), -MainScreen.HEIGHT/2+r+0.1);
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

	public boolean collision(Movable that) {
		if(this == that) {
			return false;
		}

		double distance = new Vector2d(pos().x() - that.pos().x(), pos().y() - that.pos().y()).length();
		return distance <= r + that.r();
	}

	public void hitObstacle(Obstacle obstacle) {
		if(obstacle.pos().x() - r <= pos.x() && pos.x() <= obstacle.pos().x() + obstacle.width() + r &&
		   obstacle.pos().y() - r <= pos.y() && pos.y() <= obstacle.pos().y() + obstacle.height() + r) {
		   	if(pos.x() <= obstacle.pos().x()) {
				right = false;
				v = new Vector2d(0, v.y());
				pos = new Vector2d(obstacle.pos().x()-r-0.1, pos.y());
			} else {
				right = true;
			}
			if(pos.x() >= obstacle.pos().x() + obstacle.width()) {
				left = false;
				v = new Vector2d(0, v.y());
				pos = new Vector2d(obstacle.pos().x() + obstacle.width() + r + 0.1, pos.y());
			} else {
				left = true;
			}
		   	if(pos.y() <= obstacle.pos().y()) {
				up = false;
				v = new Vector2d(v.x(), 0);
				pos = new Vector2d(pos.x(), obstacle.pos().y()-r-0.1);
			} else {
				up = true;
			}
			if(pos.y() >= obstacle.pos().y() + obstacle.height()) {
				down = false;
				v = new Vector2d(v.x(), 0);
				pos = new Vector2d(pos.x(), obstacle.pos().y() + obstacle.height() + r + 0.1);
			} else {
				down = true;
			}
		}
	}
}
