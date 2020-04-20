package zach.chase.views;

import com.badlogic.gdx.Screen;
import zach.chase.Chase;

public class LoadingScreen implements Screen {
	private Chase parent;

	public LoadingScreen(Chase chase) {
		parent = chase;
	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		parent.changeScreen(Chase.ScreenType.MENU);
	}

	@Override
	public void resize(int width, int height) {
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
	}
}
