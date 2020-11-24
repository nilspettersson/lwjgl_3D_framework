package niles.lwjgl.loop;

import java.util.ArrayList;

import org.joml.Vector3f;
import org.joml.Vector4f;

import niles.lwjgl.entity.Entity;
import niles.lwjgl.fbo.Fbo;
import niles.lwjgl.light.Lights;
import niles.lwjgl.npsl.PostProcessingShader;
import niles.lwjgl.npsl.Shader;
import niles.lwjgl.rendering.Renderer;
import niles.lwjgl.world.Camera;

public abstract class Scene {
	
	private Camera camera;
	private Renderer renderer;
	
	private ArrayList<Entity> entities;
	private Lights lights;
	
	private Fbo fbo;
	private Shader postProcessingShader;
	private boolean postProcessing;
	
	private boolean isLoaded;
	
	public Scene() {
		camera = new Camera();
		camera.setPerspective((float) Math.toRadians(70), 1920f / 1080f, 0.1f, 1000);
		
		renderer = new Renderer();
		lights = new Lights();
		fbo = new Fbo();
		postProcessing = false;
		entities = new ArrayList<Entity>();
		
		isLoaded = false;
	}
	
	public abstract void onload();
	
	public abstract void update();
	
	protected void loop() {
		if(!isLoaded){
			onload();
			isLoaded = true;
		}
		update();
		
		if(postProcessing) {
			bindFbo();
			for(int i = 0; i < entities.size(); i++) {
				render(entities.get(i));
			}
			unbindFbo();
			renderFbo(postProcessingShader);
		}
		else {
			for(int i = 0; i < entities.size(); i++) {
				render(entities.get(i));
			}
		}
		
		renderer.clean();
	}
	
	
	public void render(Entity entity) {
		renderer.render(getCamera(), entity, getLights());
	}

	public void addEntityToScene(Entity entity) {
		entities.add(entity);
	}
	
	public void usePostProcessing(Shader shader) {
		postProcessingShader = shader;
		postProcessing = true;
	}
	
	public void removePostProcessing() {
		postProcessingShader = null;
		postProcessing = false;
	}
	
	//fbo is used for post processing
	public void renderFbo(Shader shader) {
		fbo.render(shader, getCamera(), getLights());
	}
	
	public void bindFbo() {
		fbo.bind();
	}
	
	public void unbindFbo() {
		fbo.unbind();
	}
	
	
	public void setFboUniform(String name, Object value) {
		fbo.setUniform(name, value);
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
	
	
	public Camera getCamera() {
		return camera;
	}
	
	
	public void setCamera(Camera camera) {
		this.camera = camera;
	}


	public ArrayList<Entity> getEntities() {
		return entities;
	}


	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}


	public Lights getLights() {
		return lights;
	}


	public void setLights(Lights lights) {
		this.lights = lights;
	}


	public Fbo getFbo() {
		return fbo;
	}


	public void setFbo(Fbo fbo) {
		this.fbo = fbo;
	}

	public boolean isLoaded() {
		return isLoaded;
	}

	public void setLoaded(boolean isLoaded) {
		this.isLoaded = isLoaded;
	}
	
}
