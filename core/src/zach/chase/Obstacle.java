package zach.chase;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Obstacle implements GameObject {
	private double left, bottom, width, height;
	private Vector2d pos;

	public Obstacle(double left, double bottom, double width, double height) {
		this.left = left;
		this.bottom = bottom;
		this.width = width;
		this.height = height;
		pos = new Vector2d(left, bottom);
	}

	public void update(double delta) {}

	public void render() {
		Graphics.renderer.begin(ShapeType.Filled);
		Graphics.renderer.setColor(0.5f, 0.15f, 0.7f, 1.0f);
		Graphics.renderer.box((float)left, (float)bottom, 0.0f, (float)width, (float)height, 0.0f);
		Graphics.renderer.end();
	}

	public Vector2d pos() {
		return pos;
	}

	public double width() {
		return width;
	}
	
	public double height() {
		return height;
	}
}
