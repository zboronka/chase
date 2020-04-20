package zach.chase.views;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import zach.chase.Chase;

public class MenuScreen implements Screen {
	private Chase parent;
	private Stage stage;

	public MenuScreen(Chase chase) {
		parent = chase;
		stage = new Stage(new ScreenViewport());

		Gdx.input.setInputProcessor(stage);

		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
		stage.draw();
	}

	@Override
	public void show() {
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);

		Skin skin = new Skin(Gdx.files.internal("skin/vhs-ui.json"));

		TextButton newGame = new TextButton("New Game", skin);
		TextButton exit = new TextButton("Exit", skin);

		newGame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.changeScreen(Chase.ScreenType.APPLICATION);
			}
		});

		exit.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}
		});

		table.add(newGame).fillX().uniformX();
		table.row().pad(10, 0, 10, 0);
		table.add(exit).fillX().uniformX();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
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
		stage.dispose();
	}
}
