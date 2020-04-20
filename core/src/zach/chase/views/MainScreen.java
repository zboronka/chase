package zach.chase.views;

import com.badlogic.gdx.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import zach.chase.*;

public class MainScreen implements Screen {
	final public static float WIDTH =  320;
	final public static float HEIGHT = 240;
	final public static float A_R = WIDTH/HEIGHT; // Aspect ratio
	final public double frameDuration = 1000.0f / 30.0f; // Measured in milliseconds

	ExtendViewport viewport;
	Controller controller;
	Clocks clocks;
	Clock clock;
	double lastTime;

	Player player;
	Movable guard;
	Obstacle theRoom;

	Chase parent;

	public MainScreen(Chase chase) {
		parent = chase;
		controller = new Controller();
		viewport = new ExtendViewport(WIDTH, HEIGHT);
		Graphics.setProjectionMatrix(viewport.getCamera().combined);

		player = new Player(new Vector2d(-100,60), new Vector2d(-1.0, 0.0), 10d);
		guard = new Movable(new Vector2d(0.0, 0.0), new Vector2d(0.0, 1.0), 10d);
		theRoom = new Obstacle(-80.0, 30.0, 80.0, 60.0);
		
		Collidables.addMovable(player);
		Collidables.addMovable(guard);
		Collidables.addObstacle(theRoom);
		Graphics.addMember(player);
		Graphics.addMember(guard);
		Graphics.addMember(theRoom);

		controller = new Controller();
		Gdx.input.setInputProcessor(controller);

		clocks = new Clocks();
		clock = clocks.Clock(0);
		lastTime = clock.time();
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(controller);
	}

	@Override
	public void render(float d) {
		if(Collidables.lose)
			parent.changeScreen(Chase.ScreenType.ENDGAME);

		clocks.tick();
		double currentTime = clock.time();
		double delta = currentTime - lastTime;
		lastTime = currentTime;

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Collidables.update(delta);
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
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override 
	public void hide() {
	}

	@Override
	public void dispose() {
		Graphics.dispose();
	}
}
