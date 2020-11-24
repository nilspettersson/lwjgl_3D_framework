package niles.lwjgl.loop;

import java.util.ArrayList;

import org.joml.Vector4f;

import niles.lwjgl.entity.Entity;
import niles.lwjgl.fbo.Fbo;
import niles.lwjgl.light.Lights;
import niles.lwjgl.npsl.Shader;
import niles.lwjgl.world.Camera;

public abstract class Scene {
	
	private Camera camera;
	
	private ArrayList<Entity> entities;
	private Lights lights;
	private Fbo fbo;
	
	private boolean isLoaded;
	
	public Scene() {
		camera = new Camera();
		camera.setPerspective((float) Math.toRadians(70), 1920f / 1080f, 0.1f, 1000);
		
		lights = new Lights();
		fbo = new Fbo();
		entities = new ArrayList<Entity>();
		
		isLoaded = false;
	}
	
	public abstract void onload();
	
	public abstract void update();
	


	public void addEntity(Entity entity) {
		entities.add(entity);
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
