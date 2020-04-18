package zach.chase;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.graphics.GL20;

public class Game extends ApplicationAdapter {
	final static float WIDTH =  320;
	final static float HEIGHT = 240;
	final static float A_R = WIDTH/HEIGHT; // Aspect ratio
	final double frameDuration = 1000.0f / 30.0f; // Measured in milliseconds

	ExtendViewport viewport;
	Controller controller;
	Clocks clocks;
	Clock clock;
	double lastTime;

	Player player;

	@Override
	public void create() {
		viewport = new ExtendViewport(WIDTH, HEIGHT);
		Graphics.setProjectionMatrix(viewport.getCamera().combined);

		player = new Player(new Vector2d(-100,60), new Vector2d(-1.0, 0.0), 10d);
		Graphics.addMember(player);
		Graphics.addMember(new Obstacle(-80.0, 40.0, 80.0, 60.0));

		controller = new Controller();
		Gdx.input.setInputProcessor(controller);

		clocks = new Clocks();
		clock = clocks.Clock(0);
		lastTime = clock.time();
	}

	@Override
	public void render() {
		clocks.tick();
		double currentTime = clock.time();
		double delta = currentTime - lastTime;
		lastTime = currentTime;

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Graphics.update(delta);

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
		Graphics.setProjectionMatrix(viewport.getCamera().combined);
	}
	
	@Override
	public void dispose() {
		Graphics.dispose();
	}
}
