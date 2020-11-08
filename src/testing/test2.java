package testing;

import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;
import org.joml.Vector3f;
import org.joml.Vector4f;

import niles.lwjgl.entity.Entity;
import niles.lwjgl.entity.Geometry;
import niles.lwjgl.fbo.Fbo;
import niles.lwjgl.light.Lights;
import niles.lwjgl.loop.Game;
import niles.lwjgl.util.Shader;
import niles.lwjgl.util.Texture;
import niles.lwjgl.world.Input;
import niles.lwjgl.world.Mouse;

public class test2 extends Game {
	public static void main(String[] args) {
		new test2();
	}
	
	Geometry loaded;
	
	Entity e;
	
	Input input;
	
	Lights lights;
	

    
	@Override
	public void setup() {
		
		lights = new Lights();
		lights.addLight(new Vector3f(4,4,4), new Vector3f(1), 10);
		
		
		getCamera().setPosition(new Vector3f(0, 0, 10));
		
		
		
		Geometry gg = new Geometry(36 * 20 * 20 * 20);
		gg.createCube(0, 0, 0, new Vector4f(1));

		
		
		
		e = new Entity(36);
		e.getGeometry().createCube(0, 0, 0, new Vector4f(1));
		e.updateGeometry();
		e.addTexture(new Texture("res/wood.jpg"));
		
		System.out.println(gg);
		System.out.println(e.getGeometry());
		
		input = new Input(getWindow());
		
	}
	

	@Override
	public void update() {
		Mouse.isVisible(getWindow(), false);
		
		
		//camera rotation
		Mouse.moveMouse(getWindow(), 1f);
		rotateCamera(-Mouse.myY, -Mouse.myX);
		
		float speed = 0.2f;
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
		getRenderer().useLights(lights);
		
		e.bindTextures();
		render(e);
		
		
		//System.out.println(getFps());
		setFpsCap(120);
	}

}
