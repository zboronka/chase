package zach.chase;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Game extends ApplicationAdapter {
	final float WIDTH =  320;
	final float HEIGHT = 240;
	final float frameDuration = 1000.0f / 30.0f;

	ShapeRenderer shapeRenderer;
	ExtendViewport viewport;
	Movable m;

	@Override
	public void create() {
		viewport = new ExtendViewport(WIDTH, HEIGHT);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
		m = new Movable(new Vector2d(0,0), 10d, 1d);
		m.addForce(new Vector2d(1,1));
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.circle((float)m.pos().x(), (float)m.pos().y(), (float)m.r());
		shapeRenderer.end();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, false);
		shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
	}
	
	@Override
	public void dispose() {
		shapeRenderer.dispose();
	}
}
