package zach.chase;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Player extends Movable {
	public Player(Vector2d pos, Vector2d dir, double r, double max_speed) {
		super(pos, dir, r,max_speed);
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
}
