package zach.chase;

import java.util.List;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;

public class Graphics {
	public static List<GameObject> members = new ArrayList<>();
	public static ShapeRenderer renderer = new ShapeRenderer();

	public static void setProjectionMatrix(Matrix4 m) {
		renderer.setProjectionMatrix(m);
	}

	public static void addMember(GameObject member) {
		members.add(member);
	}

	public static void update(double delta) {
		for(GameObject member : members) {
			member.update(delta);
			member.render();
		}
	}

	public static void dispose() {
		members.clear();
	}
}
