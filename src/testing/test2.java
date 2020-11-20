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
		e.getGeometry().createCube(0, -2, 0, new Vector4f(1));
		e.getTransform().setScale(new Vector3f(8));
		e.bindGeometry();
		e.addTexture(new Texture("res/wood.jpg"));
		e.getMaterial().setProperty("c", 3);
		
		lights = new Lights();
		lights.addLight(new Vector3f(4,-6,4), new Vector3f(1), 6);
		
		
	}
	float value = 0;
	
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
		
		value+=0.03f;
		//lights.getLights().get(0).setPosition(new Vector3f( 3,  (float)Math.sin(value * 1) * 6 - 16, 10));
		lights.getLights().get(0).setPosition(new Vector3f( (float)Math.sin(value * 1) * 6,  -2, 3));
		//lights.getLights().get(0).setPosition(new Vector3f( 14,  (float)Math.sin(value * 1) * 6 - 16, 6));
		//lights.getLights().get(0).setPosition(new Vector3f( -14,  (float)Math.sin(value * 1) * 6 - 16, 6));
		//lights.getLights().get(0).setPosition(new Vector3f( 3,  (float)Math.sin(value * 1) * 6 - 16, -12));
		//lights.getLights().get(0).setPosition(new Vector3f( (float)Math.sin(value * 1) * 6,  -30, 3));
		
		
		render(e, lights);
		
		//System.out.println(getFps());
		setFpsCap(120);
	}

}
