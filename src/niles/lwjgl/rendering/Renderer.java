package niles.lwjgl.rendering;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform1iv;

import java.util.ArrayList;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import niles.lwjgl.entity.Entity;
import niles.lwjgl.light.Lights;
import niles.lwjgl.line.LineEntity;
import niles.lwjgl.npsl.Shader;
import niles.lwjgl.world.Camera;

public class Renderer {
	
	private ArrayList<Object> usedShaders;
	
	public Renderer() {
		usedShaders = new ArrayList<Object>();
	}
	
	//renders object with out any lighting information
	public void render(Camera camera, Entity entity) {
		entity.getMaterial().useShader();
		entity.bindTextures();
		
		//if shader is used man times per game step the only add camera data once.
		if(!usedShaders.contains(entity.getMaterial().getShader())) {
			glUniform1iv(glGetUniformLocation(entity.getMaterial().getShader().getProgram(), "sampler"), new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
			
			Matrix4f projection = camera.getProjection();
			Matrix4f transform = camera.getTransformation();
			Matrix4f object = entity.getTransform().getTransformation();
			
			entity.getMaterial().getShader().setUniform("projection", projection);
			entity.getMaterial().getShader().setUniform("transform", transform);	
			entity.getMaterial().getShader().setUniform("objectTransform", object);
			entity.getMaterial().getShader().setUniform("cameraPosition", camera.getPosition());
		}
		
		entity.getGeometry().getVao().render();
	}
	
	//renders object wit lighting information
	public void render(Camera camera, Entity entity, Lights lights) {
		entity.getMaterial().useShader();
		entity.bindTextures();
		
		//if shader is used man times per game step the only add lighting and camera data once.
		if(!usedShaders.contains(entity.getMaterial().getShader())) {
			useLights(lights, entity.getMaterial().getShader());
			glUniform1iv(glGetUniformLocation(entity.getMaterial().getShader().getProgram(), "sampler"), new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
		
			Matrix4f projection = camera.getProjection();
			Matrix4f transform = camera.getTransformation();
			
			entity.getMaterial().getShader().setUniform("projection", projection);
			entity.getMaterial().getShader().setUniform("transform", transform);	
			
			entity.getMaterial().getShader().setUniform("cameraPosition", camera.getPosition());
		}
		Matrix4f object = entity.getTransform().getTransformation();
		entity.getMaterial().getShader().setUniform("objectTransform", object);
		
		usedShaders.add(entity.getMaterial().getShader());
		
		entity.getGeometry().getVao().render();
	}
	
	public void render(Camera camera, LineEntity lines) {
		lines.getShader().bind();
		
		
		Matrix4f projection = camera.getProjection();
		Matrix4f transform = camera.getTransformation();
		Matrix4f object = lines.getTransform().getTransformation();
		
		lines.getShader().setUniform("projection", projection);
		lines.getShader().setUniform("transform", transform);	
		lines.getShader().setUniform("objectTransform", object);
		lines.getShader().setUniform("cameraPosition", camera.getPosition());
		
		
		lines.getVao().render();
	}
	
	
	public void useLights(Lights lights, Shader shader) {
		int size = lights.getLights().size();
		Vector3f[] positions = new Vector3f[size];
		Vector3f[] colors = new Vector3f[size];
		float[] intensity = new float[size];
		
		for(int i = 0; i < size; i++) {
			positions[i] = lights.getLights().get(i).getPosition();
			colors[i] = lights.getLights().get(i).getColor();
			intensity[i] = lights.getLights().get(i).getIntensity();
		}
		
		shader.setUniform("lightPositions", positions);
		shader.setUniform("lightColors", colors);
		shader.setUniform("lightIntensity", intensity);
		shader.setUniform("lightCount", colors.length);
	}
		
	public void clean() {
		usedShaders = new ArrayList<Object>();
	}

}
