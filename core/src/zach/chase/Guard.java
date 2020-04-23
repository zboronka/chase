package zach.chase;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Guard extends Movable {
	private State state = new WatchState();
	private Vector2d home;
	private double FOV = Math.PI/3; // (half of) FOV of 120 degrees
	double rate = 0.01;

	public Guard(Vector2d pos, Vector2d dir, double r, double max_speed) {
		super(pos, dir, r, max_speed);
		home = pos;
	}

	@Override
	public void update(double delta) {
		state = state.lambda(delta);
		state.delta(delta);

		super.update(delta);
	}

	@Override
	public void render() {
		Graphics.renderer.begin(ShapeType.Filled);
		Graphics.renderer.setColor(0.5f, 0.5f, 0.5f, 1.0f);
		Graphics.renderer.circle((float)pos.x(), (float)pos.y(), (float)r);
		Graphics.renderer.end();

		Graphics.renderer.begin(ShapeType.Line);
		Graphics.renderer.setColor(0.0f, 0.0f, 0.0f, 1.0f);
		Graphics.renderer.line(pos.toVector2(), pos.plus(dir.times(r)).toVector2());
		Graphics.renderer.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		Graphics.renderer.line(pos.toVector2(), pos.plus(dir.rotate(FOV).times(300)).toVector2());
		Graphics.renderer.line(pos.toVector2(), pos.plus(dir.rotate(-FOV).times(300)).toVector2());
		Graphics.renderer.end();
	}

	private boolean canSee(Movable movable) {
		Vector2d rel_pos = movable.pos().minus(pos); // Position relative to guard
		Vector2d r_vec = rel_pos.rotate(Math.PI/2).normalize().times(movable.r()); // The distance from the player to it's edge relative to guard

		Vector2d tangent = rel_pos.plus(r_vec); // Coordinate of tangent intersection with player edge relative to guard
		Vector2d a_tangent = rel_pos.plus(r_vec.negative()); // Coordinate of tangent intersection with alternate player edge relative to guard

		double angle_tan = Collidables.toRadians(Math.atan2(tangent.y(), tangent.x()));
		double angle_a_tan = Collidables.toRadians(Math.atan2(a_tangent.y(), a_tangent.x()));
		double angle_dir = Collidables.toRadians(Math.atan2(dir.y(), dir.x()));

		double angle_diff = Collidables.toAtan2(angle_dir - angle_tan);
		double angle_a_diff = Collidables.toAtan2(angle_dir - angle_a_tan);

		if(-FOV <= angle_diff && angle_diff <= FOV)
			for(Obstacle obstacle : Collidables.obstacles)
				if(!obstacle.intersects(pos, pos.plus(tangent)))
					return true;

		if(-FOV <= angle_a_diff && angle_a_diff <= FOV)
			for(Obstacle obstacle : Collidables.obstacles)
				if(!obstacle.intersects(pos, pos.plus(a_tangent)))
					return true;

		return false;
	}

	private interface State {
		public State lambda(double delta);
		public void delta(double delta);
	}

	private class WatchState implements State {
		public State lambda(double delta) {
			for(Movable movable : Collidables.movables)
				if(movable instanceof Player)
					if(canSee(movable))
						return new ChaseState((Player)movable);

			return this;
		}

		public void delta(double delta) {
			if(Math.abs(Math.atan2(dir().y(), dir().x()) - Math.PI/2) < 0.1 ||
			   Math.abs(Math.atan2(dir().y(), dir().x()) -   Math.PI) < 0.1)
				rate *= -1;

			rotate(rate);
		}
	}

	private class ChaseState implements State {
		Player player;

		ChaseState(Player player) {
			this.player = player;
		}

		public State lambda(double delta) {
			return canSee(player) ? this : new ReturnState();
		}

		public void delta(double delta) {
			seek(player.pos().plus(player.v().times(0.5)));
		}
	}

	private class ReturnState implements State {
		public State lambda(double delta) {
			return pos.minus(home).length() > 10 ? this : new SlowState();
		}

		public void delta(double delta) {
			seek(home);
		}
	}

	private class SlowState implements State {
		public State lambda(double delta) {
			return pos.minus(home).length() < 0.00001 ? this : new StopState();
		}

		public void delta(double delta) {
			seek(home);
			slow(0.3);
		}
	}

	private class StopState implements State {
		public State lambda(double delta) {
			return new WatchState();
		}

		public void delta(double delta) {
			pos = home;
			v = new Vector2d(0, 0);
		}
	}
}
