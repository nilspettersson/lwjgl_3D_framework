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
import niles.lwjgl.world.Input;
import niles.lwjgl.world.Mouse;
import static org.lwjgl.glfw.GLFW.*;

public class test extends Game {

	public static void main(String[] args) {
		new test();
	}
	
	Shader shader;
	Geometry g;
	
	Entity entity;
	
	
	Input input;

	@Override
	public void setup() {
		shader = new Shader("shader");
		g = new Geometry(400);
		
		
		g.addVertice(new Vertex(new Vector3f(-1, 1, -1), new Vector4f(0, 0, 1, 1), 0, new Vector2f(0, 0)));
		g.addVertice(new Vertex(new Vector3f(1, 1, -1), new Vector4f(0, 0, 1, 1), 0, new Vector2f(1, 0)));
		g.addVertice(new Vertex(new Vector3f(1, -1, -1), new Vector4f(1, 0, 0, 1), 0, new Vector2f(1, 1)));
		g.addVertice(new Vertex(new Vector3f(-1, -1, -1), new Vector4f(1, 0, 0, 1), 0, new Vector2f(0, 1)));
		
		g.addVertice(new Vertex(new Vector3f(-1, 1, 1), new Vector4f(0, 0, 1, 1), 0, new Vector2f(0, 0)));
		g.addVertice(new Vertex(new Vector3f(1, 1, 1), new Vector4f(0, 0, 1, 1), 0, new Vector2f(1, 0)));
		g.addVertice(new Vertex(new Vector3f(1, -1, 1), new Vector4f(1, 0, 0, 1), 0, new Vector2f(1, 1)));
		g.addVertice(new Vertex(new Vector3f(-1, -1, 1), new Vector4f(1, 0, 0, 1), 0, new Vector2f(0, 1)));
		
		g.addVertice(new Vertex(new Vector3f(-1, 1, -1), new Vector4f(0, 0, 1, 1), 0, new Vector2f(0, 0)));
		g.addVertice(new Vertex(new Vector3f(-1, 1, 1), new Vector4f(0, 0, 1, 1), 0, new Vector2f(1, 0)));
		g.addVertice(new Vertex(new Vector3f(-1, -1, 1), new Vector4f(1, 0, 0, 1), 0, new Vector2f(1, 1)));
		g.addVertice(new Vertex(new Vector3f(-1, -1, -1), new Vector4f(1, 0, 0, 1), 0, new Vector2f(0, 1)));
		
		g.addVertice(new Vertex(new Vector3f(1, 1, -1), new Vector4f(0, 0, 1, 1), 0, new Vector2f(0, 0)));
		g.addVertice(new Vertex(new Vector3f(1, 1, 1), new Vector4f(0, 0, 1, 1), 0, new Vector2f(1, 0)));
		g.addVertice(new Vertex(new Vector3f(1, -1, 1), new Vector4f(1, 0, 0, 1), 0, new Vector2f(1, 1)));
		g.addVertice(new Vertex(new Vector3f(1, -1, -1), new Vector4f(1, 0, 0, 1), 0, new Vector2f(0, 1)));
		
		g.addVertice(new Vertex(new Vector3f(1, -1, -1), new Vector4f(0, 0, 1, 1), 0, new Vector2f(0, 0)));
		g.addVertice(new Vertex(new Vector3f(1, -1, 1), new Vector4f(0, 0, 1, 1), 0, new Vector2f(1, 0)));
		g.addVertice(new Vertex(new Vector3f(-1, -1, 1), new Vector4f(1, 0, 0, 1), 0, new Vector2f(1, 1)));
		g.addVertice(new Vertex(new Vector3f(-1, -1, -1), new Vector4f(1, 0, 0, 1), 0, new Vector2f(0, 1)));
		
		g.addVertice(new Vertex(new Vector3f(1, 1, -1), new Vector4f(0, 0, 1, 1), 0, new Vector2f(0, 0)));
		g.addVertice(new Vertex(new Vector3f(1, 1, 1), new Vector4f(0, 0, 1, 1), 0, new Vector2f(1, 0)));
		g.addVertice(new Vertex(new Vector3f(-1, 1, 1), new Vector4f(1, 0, 0, 1), 0, new Vector2f(1, 1)));
		g.addVertice(new Vertex(new Vector3f(-1, 1, -1), new Vector4f(1, 0, 0, 1), 0, new Vector2f(0, 1)));
		
		g.addIndex(0);
		g.addIndex(1);
		g.addIndex(2);
		g.addIndex(2);
		g.addIndex(3);
		g.addIndex(0);
		
		g.addIndex(0 + 4);
		g.addIndex(1 + 4);
		g.addIndex(2 + 4);
		g.addIndex(2 + 4);
		g.addIndex(3 + 4);
		g.addIndex(0 + 4);
		
		g.addIndex(0 + 4*2);
		g.addIndex(1 + 4*2);
		g.addIndex(2 + 4*2);
		g.addIndex(2 + 4*2);
		g.addIndex(3 + 4*2);
		g.addIndex(0 + 4*2);
		
		g.addIndex(0 + 4*3);
		g.addIndex(1 + 4*3);
		g.addIndex(2 + 4*3);
		g.addIndex(2 + 4*3);
		g.addIndex(3 + 4*3);
		g.addIndex(0 + 4*3);
		
		g.addIndex(0 + 4*4);
		g.addIndex(1 + 4*4);
		g.addIndex(2 + 4*4);
		g.addIndex(2 + 4*4);
		g.addIndex(3 + 4*4);
		g.addIndex(0 + 4*4);
		
		g.addIndex(0 + 4*5);
		g.addIndex(1 + 4*5);
		g.addIndex(2 + 4*5);
		g.addIndex(2 + 4*5);
		g.addIndex(3 + 4*5);
		g.addIndex(0 + 4*5);
		
		g.updateVertices();
		g.updateIndices();
		
		
		entity = new Entity(400);
		entity.setGeometry(g);
		
		getCamera().setPosition(new Vector3f(0, 0, 10));
		
		
		
		input = new Input(getWindow());
		
		getWindow().setVSync(false);
	}

	@Override
	public void update() {
		shader.bind();
		
		
		
		Mouse.isVisible(getWindow(), false);
		//Mouse.moveMouse(getWindow(), 0.4f);
		
		Vector2f m = Mouse.getMousePosition(getWindow(), 0.001f);
		getCamera().getRotation().rotate(-m.y, -m.x, 0);
		
		
		//getCamera().getRotation().z = 0;
		
		Mouse.setMouseLocation(getWindow(), 1920/2, 1080/2);
		
		
		if(input.isDown(GLFW_KEY_W)) {
			//getCamera().getPosition().z -=0.2f;
			Vector3f move = new Vector3f(0, 0, -0.1f);
			move.rotate(getCamera().getRotation());
			getCamera().getPosition().add(move);
		}
		if(input.isDown(GLFW_KEY_S)) {
			Vector3f move = new Vector3f(0, 0, 0.1f);
			move.rotate(getCamera().getRotation());
			getCamera().getPosition().add(move);
		}
		if(input.isDown(GLFW_KEY_A)) {
			Vector3f move = new Vector3f(-0.1f, 0, 0);
			move.rotate(getCamera().getRotation());
			getCamera().getPosition().add(move);
		}
		if(input.isDown(GLFW_KEY_D)) {
			Vector3f move = new Vector3f(0.1f, 0, 0);
			move.rotate(getCamera().getRotation());
			getCamera().getPosition().add(move);
		}
		if(input.isDown(GLFW_KEY_Q)) {
			Vector3f move = new Vector3f(0, -0.1f, 0);
			move.rotate(getCamera().getRotation());
			getCamera().getPosition().add(move);
		}
		if(input.isDown(GLFW_KEY_E)) {
			Vector3f move = new Vector3f(0, 0.1f, 0);
			move.rotate(getCamera().getRotation());
			getCamera().getPosition().add(move);
		}
		
		
		
		
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
