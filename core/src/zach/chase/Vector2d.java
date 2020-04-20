package zach.chase;

import com.badlogic.gdx.math.Vector2;

/** 
 * 2 dimensional vector
 * <p>
 * Code stolen/translated from 
 * @author Alex Pantaleev */
public class Vector2d {
	private Double x, y;
	private Double sqLength;	
	private Double length;

	public Vector2d(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double x() {
		return x;
	}

	public double y() {
		return y;
	}

	public double sqLength() {
		return sqLength == null ? sqLength = x * x + y * y : sqLength;
	}

	public double length() {
		return length == null ? length = Math.sqrt(this.sqLength()) : length;
	}

	public Vector2d plus(Vector2d that) {
		return new Vector2d(x + that.x, y + that.y);
	}

	public Vector2d minus(Vector2d that) {
		return new Vector2d(x - that.x, y - that.y);
	}

	public Vector2d negative() { 
		return new Vector2d(-x, -y);
	}

	public Vector2d times(double that) {
		return new Vector2d(that * x, that * y);
	}

	public double dot(Vector2d that) {
     	return x * that.x + y * that.y;
	}

  	//pseudo-cross in 2D, take the z-component only as a scalar
  	public double cross (Vector2d that) {
    	return x * that.y - y * that.x;
  	}

  	public Vector2d normalize() {
    	return new Vector2d(x / length, y / length);
  	}

  	public Vector2d rotate(double angle) {
    	return new Vector2d(x * Math.cos(angle) - y * Math.sin(angle), y * Math.cos(angle) + x * Math.sin(angle));
  	}

	public Vector2 toVector2() {
		return new Vector2((float)x(), (float)y());
	}

  	@Override
  	public String toString() {
   		return  "x: " + x + ", y: " + y;
  	}
}
