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
	final double frameDuration = 1000.0f / 30.0f; // Measured in milliseconds

	ShapeRenderer shapeRenderer;
	ExtendViewport viewport;
	Clocks clocks;
	Clock clock;
	double lastTime;

	Movable m;

	@Override
	public void create() {
		viewport = new ExtendViewport(WIDTH, HEIGHT);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
		clocks = new Clocks();
		clock = clocks.Clock(0);
		lastTime = clock.time();

		m = new Movable(new Vector2d(0,0), 10d);
		m.addV(new Vector2d(1.0,1.0));
	}

	@Override
	public void render() {
		clocks.tick();
		double currentTime = clock.time();
		double delta = currentTime - lastTime;
		lastTime = currentTime;
		m.update(delta);

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.circle((float)m.pos().x(), (float)m.pos().y(), (float)m.r());
		shapeRenderer.end();

		clocks.tick();
		double sleepTime = frameDuration - ((clock.time() - lastTime) * 1000); // Convert to milliseconds and subtract from frameDuration
		try {
			if(sleepTime > 0) Thread.sleep((long)sleepTime);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
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
