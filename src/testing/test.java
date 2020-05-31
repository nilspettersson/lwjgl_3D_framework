package testing;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import niles.lwjgl.entity.Geometry;
import niles.lwjgl.entity.Vertex;
import niles.lwjgl.loop.Game;
import niles.lwjgl.util.Model;
import niles.lwjgl.util.Shader;

public class test extends Game {

	public static void main(String[] args) {
		new test();
	}
	
	Shader shader;
	Geometry g;

	@Override
	public void setup() {
		shader = new Shader("shader");
		g = new Geometry(400);
		
		
		g.addVertice(new Vertex(new Vector3f(-1, 1, 0), new Vector4f(1, 0, 0, 1), 0, new Vector2f(0, 0)));
		g.addVertice(new Vertex(new Vector3f(1, 1, 0), new Vector4f(0, 1, 0, 1), 0, new Vector2f(1, 0)));
		g.addVertice(new Vertex(new Vector3f(1, -1, 0), new Vector4f(0, 0, 1, 1), 0, new Vector2f(1, 1)));
		
		g.addIndex(0);
		g.addIndex(1);
		g.addIndex(2);
		
		
		
		
		
	}

	@Override
	public void update() {
		shader.bind();
		
		
		g.updateVertices();
		g.updateIndices();
		
		g.getVao().render();
		
		
	}

}
