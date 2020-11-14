package testing;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector3f;
import org.joml.Vector4f;

import niles.lwjgl.entity.Entity;
import niles.lwjgl.entity.Geometry;
import niles.lwjgl.light.Lights;
import niles.lwjgl.loop.Game;
import niles.lwjgl.npsl.Material;
import niles.lwjgl.npsl.ShaderNp;
import niles.lwjgl.util.Texture;
import niles.lwjgl.world.Mouse;

public class test2 extends Game {
	public static void main(String[] args) {
		new test2();
	}
	
	Entity e;
	Lights lights;
	
	ShaderNp shader;
	
	@Override
	public void setup() {
		shader = new ShaderNp("test.glsl");
		
		
		
		getCamera().setPosition(new Vector3f(0, 0, 10));
		
		e = new Entity(36, shader);
		e.getGeometry().createCube(0, 0, 0, new Vector4f(1));
		e.bindGeometry();
		e.addTexture(new Texture("res/wood.jpg"));
		e.getMaterial().setProperty("c", new Vector3f(1, 0, 1));
		
		lights = new Lights();
		lights.addLight(new Vector3f(4,4,4), new Vector3f(1), 4);
	}
	
	@Override
	public void update() {
		Mouse.isVisible(getWindow(), false);
		
		
		//camera rotation
		Mouse.moveMouse(getWindow(), 1f);
		rotateCamera(-Mouse.myY, -Mouse.myX);
		
		float speed = 0.02f;
		if(getInput().isDown(GLFW_KEY_W)) {
			moveCameraForward(speed);
		}
		if(getInput().isDown(GLFW_KEY_S)) {
			moveCameraBackward(speed);
		}
		if(getInput().isDown(GLFW_KEY_A)) {
			moveCameraLeft(speed);
		}
		if(getInput().isDown(GLFW_KEY_D)) {
			moveCameraRight(speed);
		}
		if(getInput().isDown(GLFW_KEY_Q)) {
			moveCameraDown(speed);
		}
		if(getInput().isDown(GLFW_KEY_E)) {
			moveCameraUp(speed);
		}
		
		
		render(e, lights);
		
		//System.out.println(getFps());
		setFpsCap(120);
	}

}
