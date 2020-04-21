package zach.chase;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Guard extends Movable {
	private State state = new WatchState();
	double rate = 0.01;

	public Guard(Vector2d pos, Vector2d dir, double r, double max_speed) {
		super(pos, dir, r, max_speed);
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
		Graphics.renderer.line(pos.toVector2(), pos.plus(dir.rotate(Math.PI/3).times(300)).toVector2());
		Graphics.renderer.line(pos.toVector2(), pos.plus(dir.rotate(-Math.PI/3).times(300)).toVector2());
		Graphics.renderer.end();
	}

	private interface State {
		public State lambda(double delta);
		public void delta(double delta);
	}

	private class WatchState implements State {
		public State lambda(double delta) {
			for(Movable movable : Collidables.movables) {
				if(movable instanceof Player) {
					Vector2d rel_pos = movable.pos().minus(pos);
					double angle = Math.atan2(rel_pos.y(), rel_pos.x());
					Vector2d vm60 = dir.rotate(-Math.PI/3);
					Vector2d vp60 = dir.rotate(Math.PI/3);
					if(Math.atan2(vm60.y(), vm60.x()) <= angle &&
					   angle <= Math.atan2(vp60.y(), vp60.x()))
					   	return new ChaseState();
				}
			}

			return this;
		}

		public void delta(double delta) {
			if(Math.abs(Math.atan2(dir().y(), dir().x()) - Math.PI/2) < 0.1 ||
			   Math.abs(Math.atan2(dir().y(), dir().x()) - Math.PI) < 0.1)
				rate *= -1;

			rotate(rate);
		}
	}

	private class ChaseState implements State {
		public State lambda(double delta) {
			return this;
		}

		public void delta(double delta) {
			thrust(0.1);
		}
	}
}
