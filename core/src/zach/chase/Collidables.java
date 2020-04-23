package zach.chase;

import java.util.List;
import java.util.ArrayList;

public class Collidables {
	public static List<Movable> movables = new ArrayList<>();
	public static List<Obstacle> obstacles = new ArrayList<>();
	public static boolean lose = false;

	public static void addMovable(Movable movable) {
		movables.add(movable);
	}

	public static void addObstacle(Obstacle obstacle) {
		obstacles.add(obstacle);
	}

	public static void update(double delta) {
		for(Movable movable : movables) {
			for(Movable other : movables) {
				if(movable.collision(other)) {
					lose = true;
				}
			}

			for(Obstacle obstacle : obstacles) movable.hitObstacle(obstacle);
		}
	}

	public static void dispose() {
		movables.clear();
		obstacles.clear();
	}

	public static double toRadians(double atan2) {
		return (atan2 + 2*Math.PI) % (2*Math.PI);
	}

	public static double toAtan2(double radians) {
		return (radians + 3 * Math.PI) % (2*Math.PI) - Math.PI;
	}
}
