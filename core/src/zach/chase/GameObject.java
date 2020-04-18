package zach.chase;

/** Base class for Game objects */
public interface GameObject {
	public void update(double delta);
	public void render();
	public Vector2d pos();
}
