package niles.lwjgl.loop;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Q;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import java.util.ArrayList;

import org.joml.Vector3f;
import org.joml.Vector4f;

import niles.lwjgl.world.Input;
import niles.lwjgl.world.Mouse;
import niles.lwjgl.world.Window;

public abstract class Game {
	
	private Window window;
	private Input input;
	
	private Vector4f backgroundColor;
	private int fpsCap;
	
	private ArrayList<Scene> scenes;
	private int currentScene = 0;
	
	
	public Game(int width,int height,boolean fullsceen) {
		window=new Window(width, height, fullsceen);
		input = new Input(getWindow());
		scenes = new ArrayList<Scene>();
		
		this.backgroundColor=new Vector4f(0,0,0,1);
		this.fpsCap=120;
		
		loop();
	}
	
	public Game() {
		window=new Window(1920, 1080, true);
		input = new Input(getWindow());
		scenes = new ArrayList<Scene>();
		
		this.backgroundColor=new Vector4f(0,0,0,1);
		this.fpsCap=120;
		
		loop();
	}
	
	public abstract void init();
	
	private void loop() {
		
		init();
		
		while(window.shouldUpdate()) {
			window.drawInit(backgroundColor);
			
			if(scenes.size() > 0) {
				scenes.get(currentScene).loop();
			}
			
			window.clean();
			window.update(fpsCap);
		}
	}
	
	
	public void simpleCameraRotation(float sensitivity) {
		Mouse.isVisible(getWindow(), false);
		Mouse.moveMouse(getWindow(), sensitivity);
		scenes.get(currentScene).rotateCamera(-Mouse.y, -Mouse.x);
	}
	
	public void simpleCameraMovement(float speed) {
		if(getInput().isDown(GLFW_KEY_W)) {
			scenes.get(currentScene).moveCameraForward(speed);
		}
		if(getInput().isDown(GLFW_KEY_S)) {
			scenes.get(currentScene).moveCameraBackward(speed);
		}
		if(getInput().isDown(GLFW_KEY_A)) {
			scenes.get(currentScene).moveCameraLeft(speed);
		}
		if(getInput().isDown(GLFW_KEY_D)) {
			scenes.get(currentScene).moveCameraRight(speed);
		}
		if(getInput().isDown(GLFW_KEY_Q)) {
			scenes.get(currentScene).moveCameraDown(speed);
		}
		if(getInput().isDown(GLFW_KEY_E)) {
			scenes.get(currentScene).moveCameraUp(speed);
		}
	}
	
	
	public void addScene(Scene scene) {
		scenes.add(scene);
	}
	
	public void useScene(int sceneIndex) {
		Mouse.x = 0;
		Mouse.y = 0;
		scenes.get(currentScene).clean();
		
		currentScene = sceneIndex;
	}
	
	
	public Window getWindow() {
		return window;
	}

	public Vector4f getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Vector4f backgroundColor) {
		this.backgroundColor = backgroundColor;
	}


	public int getFpsCap() {
		return fpsCap;
	}

	public void setFpsCap(int fps) {
		this.fpsCap = fps;
	}

	public int getFps() {
		return window.getFps();
	}
	public Input getInput() {
		return input;
	}


}
