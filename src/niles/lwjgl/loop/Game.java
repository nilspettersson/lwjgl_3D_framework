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
	
	public Game(int width,int height,boolean fullsceen,Vector4f backgroundColor,int fpsCap) {
		window=new Window(width, height, fullsceen);
		input = new Input(getWindow());
		
		scenes = new ArrayList<Scene>();
		
		this.backgroundColor=backgroundColor;
		this.fpsCap=fpsCap;
		
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
	
	public void loop() {
		
		init();
		
		while(window.shouldUpdate()) {
			window.drawInit(backgroundColor);
			
			scenes.get(currentScene).loop();
			
			window.clean();
			window.update(fpsCap);
		}
	}
	

	public void rotateCamera(float xAxis, float yAxis) {
		scenes.get(currentScene).getCamera().getRotation().setAngleAxis(yAxis, 0, 1, 0);
		scenes.get(currentScene).getCamera().getRotation().rotate(xAxis, 0, 0);
	}
	
	public void moveCameraForward(float amount) {
		Vector3f move = new Vector3f(0, 0, -amount);
		move.rotate(scenes.get(currentScene).getCamera().getRotation());
		scenes.get(currentScene).getCamera().getPosition().add(move);
	}
	public void moveCameraBackward(float amount) {
		Vector3f move = new Vector3f(0, 0, amount);
		move.rotate(scenes.get(currentScene).getCamera().getRotation());
		scenes.get(currentScene).getCamera().getPosition().add(move);
	}
	public void moveCameraLeft(float amount) {
		Vector3f move = new Vector3f(-amount, 0, 0);
		move.rotate(scenes.get(currentScene).getCamera().getRotation());
		scenes.get(currentScene).getCamera().getPosition().add(move);
	}
	public void moveCameraRight(float amount) {
		Vector3f move = new Vector3f(amount, 0, 0);
		move.rotate(scenes.get(currentScene).getCamera().getRotation());
		scenes.get(currentScene).getCamera().getPosition().add(move);
	}
	public void moveCameraUp(float amount) {
		Vector3f move = new Vector3f(0, amount, 0);
		move.rotate(scenes.get(currentScene).getCamera().getRotation());
		scenes.get(currentScene).getCamera().getPosition().add(move);
	}
	public void moveCameraDown(float amount) {
		Vector3f move = new Vector3f(0, -amount, 0);
		move.rotate(scenes.get(currentScene).getCamera().getRotation());
		scenes.get(currentScene).getCamera().getPosition().add(move);
	}
	
	
	public void simpleCameraRotation(float sensitivity) {
		Mouse.isVisible(getWindow(), false);
		Mouse.moveMouse(getWindow(), 1f);
		rotateCamera(-Mouse.myY, -Mouse.myX);
	}
	
	public void simpleCameraMovement(float speed) {
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
	}
	
	
	public void addScene(Scene scene) {
		scenes.add(scene);
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
