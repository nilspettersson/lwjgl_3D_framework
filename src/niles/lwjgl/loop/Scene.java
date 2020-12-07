package niles.lwjgl.loop;

import static org.lwjgl.opengl.GL15.*;

import java.util.ArrayList;

import org.joml.Vector3f;

import niles.lwjgl.entity.Entity;
import niles.lwjgl.fbo.Fbo;
import niles.lwjgl.light.Light;
import niles.lwjgl.light.Lights;
import niles.lwjgl.line.LineEntity;
import niles.lwjgl.line.LineEntity;
import niles.lwjgl.npsl.Shader;
import niles.lwjgl.rendering.Renderer;
import niles.lwjgl.world.Camera;
import niles.lwjgl.world.Window;

public abstract class Scene {
	
	private Camera camera;
	private Renderer renderer;
	private Window window;
	
	private ArrayList<Entity> entities;
	private Lights lights;
	private ArrayList<LineEntity> lineEntites;
	
	private Fbo fbo;
	private Shader postProcessingShader;
	private boolean postProcessing;
	
	private boolean isLoaded;
	
	public Scene(Window window) {
		camera = new Camera();
		camera.setPerspective((float) Math.toRadians(70), 1920f / 1080f, 0.1f, 1000);
		this.window = window;
		
		renderer = new Renderer();
		lights = new Lights();
		postProcessing = false;
		entities = new ArrayList<Entity>();
		lineEntites = new ArrayList<>();
		
		isLoaded = false;
	}
	
	protected abstract void onload();
	
	protected abstract void update();
	
	void clean() {
		camera = new Camera();
		camera.setPerspective((float) Math.toRadians(70), 1920f / 1080f, 0.1f, 1000);
		lights = new Lights();
		
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).getGeometry().deleteBuffers();
			entities.get(i).DeleteTextures();
		}
		entities = new ArrayList<Entity>();
		
		isLoaded = false;
		System.gc();
	}
	
	void loop() {
		if(!isLoaded){
			onload();
			isLoaded = true;
		}
		
		if(postProcessing) {
			bindFbo();
			for(int i = 0; i < entities.size(); i++) {
				render(entities.get(i));
			}
			for(int i = 0; i < lineEntites.size(); i++) {
				renderer.render(camera, lineEntites.get(i));
			}
			unbindFbo();
			renderFbo(postProcessingShader);
		}
		else {
			for(int i = 0; i < entities.size(); i++) {
				render(entities.get(i));
			}
			for(int i = 0; i < lineEntites.size(); i++) {
				renderer.render(camera, lineEntites.get(i));
			}
		}
		
		update();
		
		renderer.clean();
	}
	
	
	public void render(Entity entity) {
		renderer.render(getCamera(), entity, lights);
	}

	public void addEntityToScene(Entity entity) {
		entities.add(entity);
	}
	
	public void addLineEntityToScene(LineEntity lines) {
		lineEntites.add(lines);
	}
	
	
	public void usePostProcessing(Shader shader) {
		postProcessingShader = shader;
		if(fbo == null) {
			fbo = new Fbo(window);
		}
		postProcessing = true;
	}
	
	public void removePostProcessing() {
		postProcessingShader = null;
		postProcessing = false;
	}
	
	//fbo is used for post processing
	private void renderFbo(Shader shader) {
		fbo.render(shader, getCamera(), lights);
	}
	
	private void bindFbo() {
		fbo.bind();
	}
	
	private void unbindFbo() {
		fbo.unbind();
	}
	
	public void delete(Entity entity){
		for(int i = 0; i < entities.size(); i++) {
			if(entities.get(i).equals(entity)) {
				entities.get(i).getGeometry().deleteBuffers();
				entities.get(i).setGeometry(null);
				entities.remove(i);
			}
		}
	}
	
	public void delete(int entityIndex){
		entities.get(entityIndex).getGeometry().deleteBuffers();
		entities.get(entityIndex).setGeometry(null);
		entities.remove(entityIndex);
	}
	
	public void setPostProcessingUniform(String name, Object value) {
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

	public ArrayList<Light> getLights() {
		return lights.getLights();
	}
	
	public void addLight(Vector3f position,Vector3f color, float intensity) {
		lights.addLight(position, color, intensity);
	}

	public void setLights(Lights lights) {
		this.lights = lights;
	}
	
}
