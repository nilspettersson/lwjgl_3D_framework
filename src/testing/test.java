package testing;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import niles.lwjgl.entity.Entity;
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
	
	Entity entity;

	@Override
	public void setup() {
		shader = new Shader("shader");
		g = new Geometry(400);
		
		
		g.addVertice(new Vertex(new Vector3f(-1, 1, 0), new Vector4f(0, 0, 1, 1), 0, new Vector2f(0, 0)));
		g.addVertice(new Vertex(new Vector3f(1, 1, 0), new Vector4f(0, 0, 1, 1), 0, new Vector2f(1, 0)));
		g.addVertice(new Vertex(new Vector3f(1, -1, 0), new Vector4f(1, 0, 0, 1), 0, new Vector2f(1, 1)));
		g.addVertice(new Vertex(new Vector3f(-1, -1, 0), new Vector4f(1, 0, 0, 1), 0, new Vector2f(0, 1)));
		
		g.addIndex(0);
		g.addIndex(1);
		g.addIndex(2);
		
		g.addIndex(2);
		g.addIndex(3);
		g.addIndex(0);
		
		g.updateVertices();
		g.updateIndices();
		
		
		entity = new Entity(400);
		entity.setGeometry(g);
		
		getCamera().setPosition(new Vector3f(0, 0, 30));
	}

	@Override
	public void update() {
		shader.bind();
		
		
		//entity.getTransform().setScale(new Vector3f(0.4f));
		//entity.getTransform().getPosition().z-=0.001f;
		
		
		Matrix4f projection = getCamera().getProjection();
		Matrix4f transform = getCamera().getTransformation();
		Matrix4f object = entity.getTransform().getTransformation();
		
		Matrix4f send = new Matrix4f();
		projection.mul(transform, send);
		send.mul(object, send);
		
		shader.setUniform("projection", send);		
		entity.getGeometry().getVao().render();
		
		setFpsCap(120);
	}

}
