package niles.lwjgl.loop;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Q;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;

import niles.lwjgl.entity.Entity;
import niles.lwjgl.rendering.Renderer;
import niles.lwjgl.world.Camera;
import niles.lwjgl.world.Mouse;
import niles.lwjgl.world.Window;

public abstract class Game {
	
	private Window window;
	private Camera camera;
	private Renderer renderer;
	
	private Vector4f backgroundColor;
	private int fpsCap;
	
	public Game(int width,int height,boolean fullsceen,Vector4f backgroundColor,int fpsCap) {
		window=new Window(width, height, fullsceen);
		camera=new Camera();
		
		this.backgroundColor=backgroundColor;
		this.fpsCap=fpsCap;
		
		
		
		loop();
	}
	
	public Game() {
		window=new Window(1920, 1080, true);
		camera=new Camera();
		camera.setPerspective((float) Math.toRadians(70), 1920f / 1080f, 0.001f, 10000);
		
		this.backgroundColor=new Vector4f(0,0,0,1);
		this.fpsCap=120;
		
		loop();
	}
	
	public abstract void setup();
	
	public abstract void update();
	
	public void loop() {
		renderer = new Renderer();
		
		
		setup();
		
		while(window.shouldUpdate()) {
			window.drawInit(backgroundColor);
			
			update();
			
			window.clean();
			window.update(fpsCap);
			
		}
		
	}
	
	public void render(Entity entity) {
		renderer.render(getCamera(), entity);
	}
	
	
	public void rotateCamera(float xAxis, float yAxis) {
		getCamera().getRotation().setAngleAxis(yAxis, 0, 1, 0);
		getCamera().getRotation().rotate(xAxis, 0, 0);
	}
	
	
	
	public void moveCameraForward(float amount) {
		Vector3f move = new Vector3f(0, 0, -amount);
		move.rotate(getCamera().getRotation());
		getCamera().getPosition().add(move);
	}
	public void moveCameraBackward(float amount) {
		Vector3f move = new Vector3f(0, 0, amount);
		move.rotate(getCamera().getRotation());
		getCamera().getPosition().add(move);
	}
	public void moveCameraLeft(float amount) {
		Vector3f move = new Vector3f(-amount, 0, 0);
		move.rotate(getCamera().getRotation());
		getCamera().getPosition().add(move);
	}
	public void moveCameraRight(float amount) {
		Vector3f move = new Vector3f(amount, 0, 0);
		move.rotate(getCamera().getRotation());
		getCamera().getPosition().add(move);
	}
	public void moveCameraUp(float amount) {
		Vector3f move = new Vector3f(0, amount, 0);
		move.rotate(getCamera().getRotation());
		getCamera().getPosition().add(move);
	}
	public void moveCameraDown(float amount) {
		Vector3f move = new Vector3f(0, -amount, 0);
		move.rotate(getCamera().getRotation());
		getCamera().getPosition().add(move);
	}
	
	

	

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
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

	public Renderer getRenderer() {
		return renderer;
	}

	public void setRenderer(Renderer renderer) {
		this.renderer = renderer;
	}

	
	
	
	
	

}
