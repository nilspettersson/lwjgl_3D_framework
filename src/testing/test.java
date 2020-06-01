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
	
	
	//implement later.
	/*float maxAngle = 0.4f;
	
	if(getCamera().getRotation().x > maxAngle && Mouse.myY < 0) {
		Mouse.myY = 0;
	}
	else if(getCamera().getRotation().x < -maxAngle && Mouse.myY > 0) {
		Mouse.myY = 0;
	}*/

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
		
		
		
		Mouse.moveMouse(getWindow(), 1f);
		rotateCamera(-Mouse.myY, -Mouse.myX);
		
		
		if(input.isDown(GLFW_KEY_W)) {
			moveCameraForward(0.1f);
		}
		if(input.isDown(GLFW_KEY_S)) {
			moveCameraBackward(0.1f);
		}
		if(input.isDown(GLFW_KEY_A)) {
			moveCameraLeft(0.1f);
		}
		if(input.isDown(GLFW_KEY_D)) {
			moveCameraRight(0.1f);
		}
		if(input.isDown(GLFW_KEY_Q)) {
			moveCameraDown(0.1f);
		}
		if(input.isDown(GLFW_KEY_E)) {
			moveCameraUp(0.1f);
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
