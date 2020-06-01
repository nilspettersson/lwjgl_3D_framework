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
import niles.lwjgl.util.Texture;
import niles.lwjgl.world.Input;
import niles.lwjgl.world.Mouse;
import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;

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
	
	Geometry g;
	
	ArrayList<Entity> entites;
	
	Input input;

	@Override
	public void setup() {
		g = new Geometry(400);
		
		
		g.addVertice(new Vertex(new Vector3f(-1, 1, -1), new Vector4f(1), 0, new Vector2f(0, 0)));
		g.addVertice(new Vertex(new Vector3f(1, 1, -1), new Vector4f(1), 0, new Vector2f(1, 0)));
		g.addVertice(new Vertex(new Vector3f(1, -1, -1), new Vector4f(1), 0, new Vector2f(1, 1)));
		g.addVertice(new Vertex(new Vector3f(-1, -1, -1), new Vector4f(1), 0, new Vector2f(0, 1)));
		
		g.addVertice(new Vertex(new Vector3f(-1, 1, 1), new Vector4f(1), 0, new Vector2f(0, 0)));
		g.addVertice(new Vertex(new Vector3f(1, 1, 1), new Vector4f(1), 0, new Vector2f(1, 0)));
		g.addVertice(new Vertex(new Vector3f(1, -1, 1), new Vector4f(1), 0, new Vector2f(1, 1)));
		g.addVertice(new Vertex(new Vector3f(-1, -1, 1), new Vector4f(1), 0, new Vector2f(0, 1)));
		
		g.addVertice(new Vertex(new Vector3f(-1, 1, -1), new Vector4f(1), 0, new Vector2f(0, 0)));
		g.addVertice(new Vertex(new Vector3f(-1, 1, 1), new Vector4f(1), 0, new Vector2f(1, 0)));
		g.addVertice(new Vertex(new Vector3f(-1, -1, 1), new Vector4f(1), 0, new Vector2f(1, 1)));
		g.addVertice(new Vertex(new Vector3f(-1, -1, -1), new Vector4f(1), 0, new Vector2f(0, 1)));
		
		g.addVertice(new Vertex(new Vector3f(1, 1, -1), new Vector4f(1), 0, new Vector2f(0, 0)));
		g.addVertice(new Vertex(new Vector3f(1, 1, 1), new Vector4f(1), 0, new Vector2f(1, 0)));
		g.addVertice(new Vertex(new Vector3f(1, -1, 1), new Vector4f(1), 0, new Vector2f(1, 1)));
		g.addVertice(new Vertex(new Vector3f(1, -1, -1), new Vector4f(1), 0, new Vector2f(0, 1)));
		
		g.addVertice(new Vertex(new Vector3f(1, -1, -1), new Vector4f(1), 0, new Vector2f(0, 0)));
		g.addVertice(new Vertex(new Vector3f(1, -1, 1), new Vector4f(1), 0, new Vector2f(1, 0)));
		g.addVertice(new Vertex(new Vector3f(-1, -1, 1), new Vector4f(1), 0, new Vector2f(1, 1)));
		g.addVertice(new Vertex(new Vector3f(-1, -1, -1), new Vector4f(1), 0, new Vector2f(0, 1)));
		
		g.addVertice(new Vertex(new Vector3f(1, 1, -1), new Vector4f(1), 0, new Vector2f(0, 0)));
		g.addVertice(new Vertex(new Vector3f(1, 1, 1), new Vector4f(1), 0, new Vector2f(1, 0)));
		g.addVertice(new Vertex(new Vector3f(-1, 1, 1), new Vector4f(1), 0, new Vector2f(1, 1)));
		g.addVertice(new Vertex(new Vector3f(-1, 1, -1), new Vector4f(1), 0, new Vector2f(0, 1)));
		
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
		
		
		
		/*entity.addTexture(new Texture("res/floor.png"));
		entity.bindTextures();*/
		
		
		entites = new ArrayList<Entity>();
		for(int i = 0; i < 1; i++) {
			entites.add(new Entity(200));
			entites.get(i).setGeometry(g);
			entites.get(i).getTransform().getPosition().x += i;
		}
		
		
		getCamera().setPosition(new Vector3f(0, 0, 10));
		
		
		
		input = new Input(getWindow());
		
		getWindow().setVSync(false);
	}

	@Override
	public void update() {
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
		
		
		for(int i = 0; i < entites.size(); i++) {
		render(entites.get(i));
		}
		
		setFpsCap(120);
	}

}
