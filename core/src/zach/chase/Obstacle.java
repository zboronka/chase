package zach.chase;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Obstacle implements GameObject {
	private double left, bottom, width, height;
	private Vector2d pos;

	// Corner points, helps keep intersection code cleaner
	private Vector2d A, B, C, D;

	public Obstacle(double left, double bottom, double width, double height) {
		this.left = left;
		this.bottom = bottom;
		this.width = width;
		this.height = height;
		pos = new Vector2d(left, bottom);

		A = pos.plus(new Vector2d(0, height));
		B = pos;
		C = pos.plus(new Vector2d(width, 0));
		D = pos.plus(new Vector2d(width, height));
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

	/**
	 * Checks if a line crosses through this object
	 * given a certain direction
	 **/
	public boolean intersects(Vector2d E, Vector2d F) {
		// If the points are on the same side of the obstacle
		// then there can't be an intersection so don't check
		if((pos.x() + width <= E.x() && pos.x() + width <= F.x())   ||
		   (pos.x() >= E.x() && pos.x() >= F.x())                   ||
		   (pos.y() + height <= E.y() && pos.y() + height <= F.y()) ||
		   (pos.y() >= E.y() && pos.y() >= F.y())) {
			return false;
		}

		// If the points are across from each other across the Y axis
		// And between the bottom and top of the obstacle
		// Then there's an intersection
		// This case is to avoid dealing with the PI/-PI boundary
		if((E.x() > B.x() ^ F.x() > B.x())    &&
		   (B.y() <= E.y() && E.y() <= A.y()) &&
		   (B.y() <= F.y() && F.y() <= A.y())) {
			return true;
		}

		Vector2d rel_A = A.minus(E); // A relative to E
		Vector2d rel_B = B.minus(E); // B relative to E
		Vector2d rel_C = C.minus(E); // C relative to E
		Vector2d rel_D = D.minus(E); // D relative to E
		Vector2d rel_F = F.minus(E); // F relative to E

		// Get the angles to the box corners and other point from point E
		double AE = Math.atan2(rel_A.y(), rel_A.x());
		double CE = Math.atan2(rel_C.y(), rel_C.x());

		double BE = Math.atan2(rel_B.y(), rel_B.x());
		double DE = Math.atan2(rel_D.y(), rel_D.x());

		double FE = Math.atan2(rel_F.y(), rel_F.x());

		return (((AE < CE ? AE : CE) <= FE && FE <= (AE < CE ? CE : AE)) ||
		        ((BE < DE ? BE : DE) <= FE && FE <= (BE < DE ? DE : BE)));
	}
}
