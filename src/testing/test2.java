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
		shader = new ShaderNp("asd");
		/*Material m = new Material(shader);
		
		m.setProperty("x", 2);
		m.setProperty("position", new Vector3f(1));
		m.useShader();*/
		
		getCamera().setPosition(new Vector3f(0, 0, 10));
		
		e = new Entity(36, shader);
		e.getGeometry().createCube(0, 0, 0, new Vector4f(1));
		e.bindGeometry();
		
		lights = new Lights();
		lights.addLight(new Vector3f(4,4,4), new Vector3f(1), 10);
	}
	
	@Override
	public void update() {
		Mouse.isVisible(getWindow(), false);
		
		
		//camera rotation
		Mouse.moveMouse(getWindow(), 1f);
		rotateCamera(-Mouse.myY, -Mouse.myX);
		
		float speed = 0.2f;
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
		
		//getRenderer().bindShader();
		getRenderer().useLights(lights, e);
		
		e.bindTextures();
		render(e);
		
		//System.out.println(getFps());
		setFpsCap(120);
	}

}
