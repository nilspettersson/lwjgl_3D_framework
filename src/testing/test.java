package testing;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import niles.lwjgl.entity.Entity;
import niles.lwjgl.entity.Geometry;
import niles.lwjgl.entity.Vertex;
import niles.lwjgl.light.Lights;
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
	
	Geometry loaded;
	
	
	ArrayList<Entity> entites;
	
	Input input;
	
	Lights lights;

	@Override
	public void setup() {
		lights = new Lights();
		lights.addLight(new Vector3f(0, 6, 0), new Vector3f(0.9f, 0.9f, 1f), 2);
		//lights.addLight(new Vector3f(0, 10, 0), new Vector3f(1f, 0.2f, 0.2f));
		
		g = new Geometry(48*8);
		g.createCube(0, 0, 0, new Vector4f(1));
		
		//loaded = Geometry.loadModel("res/terain");
		
		
		g.updateVertices();
		g.updateIndices();
		
		
		entites = new ArrayList<Entity>();
		int index = 0;
		for(int x = 0; x < 1; x++) {
			for(int y = 0; y < 1; y++) {
				for(int z = 0; z < 1; z++) {
					entites.add(new Entity(48));
					entites.get(index).setGeometry(g);
					
					entites.get(index).getTransform().setScale(new Vector3f(10, 1, 10));
					entites.get(index).getTransform().getPosition().x += x * 6;
					entites.get(index).getTransform().getPosition().y += y * 6;
					entites.get(index).getTransform().getPosition().z += z * 6;
					
					
					index++;
				}
			}
		}
		
		entites.get(0).addTexture(new Texture("res/wood.jpg"));
		entites.get(0).bindTextures();
		
		
		getCamera().setPosition(new Vector3f(0, 0, 10));
		
		
		
		input = new Input(getWindow());
		
		getWindow().setVSync(false);
	}
	
	float value=0;

	@Override
	public void update() {
		Mouse.isVisible(getWindow(), false);
		
		
		
		Mouse.moveMouse(getWindow(), 1f);
		rotateCamera(-Mouse.myY, -Mouse.myX);
		
		float speed = 0.1f;
		if(input.isDown(GLFW_KEY_W)) {
			moveCameraForward(speed);
		}
		if(input.isDown(GLFW_KEY_S)) {
			moveCameraBackward(speed);
		}
		if(input.isDown(GLFW_KEY_A)) {
			moveCameraLeft(speed);
		}
		if(input.isDown(GLFW_KEY_D)) {
			moveCameraRight(speed);
		}
		if(input.isDown(GLFW_KEY_Q)) {
			moveCameraDown(speed);
		}
		if(input.isDown(GLFW_KEY_E)) {
			moveCameraUp(speed);
		}
		
		
		
		getRenderer().bindShader();
		
		//lights.getLights().get(0).getPosition().x=(float) Math.sin(value)*70 + (7 * 10);
		//lights.getLights().get(0).getPosition().y=(float) Math.cos(value)*70 + (7 * 10);
		value+=0.02f;
		getRenderer().useLights(lights);
		
		for(int i = 0; i < entites.size(); i++) {
			//entites.get(i).getTransform().getRotation().rotateAxis(0.01f, 0, 1, 0);
			render(entites.get(i));
		}
		
		
		//System.out.println(getFps());
		setFpsCap(120);
	}

}
