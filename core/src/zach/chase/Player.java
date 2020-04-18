package zach.chase;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Player extends Movable implements Collidable {
	Player(Vector2d pos, double r) {
		super(pos, r);
	}

	Player(Vector2d pos, Vector2d dir, double r) {
		super(pos, dir, r);
	}

	@Override
	public void update(double delta) {
		if(!((Gdx.input.isKeyPressed(Keys.UP)    &&    up)
		  || (Gdx.input.isKeyPressed(Keys.LEFT)  &&  left)
		  || (Gdx.input.isKeyPressed(Keys.DOWN)  &&  down)
		  || (Gdx.input.isKeyPressed(Keys.RIGHT) && right)))
			slow();
		else {
			if(Gdx.input.isKeyPressed(Keys.UP) && up)
				addV(0.0, 0.1);
			if(Gdx.input.isKeyPressed(Keys.LEFT) && left)
				addV(-0.1, 0.0);
			if(Gdx.input.isKeyPressed(Keys.DOWN) && down)
				addV(0.0, -0.1);
			if(Gdx.input.isKeyPressed(Keys.RIGHT) && right)
				addV(0.1, 0.0);
		}

		super.update(delta);
	}

	public boolean collision(Collidable that) {
		if(that instanceof Obstacle) {
		}
		return true;
	}
}
