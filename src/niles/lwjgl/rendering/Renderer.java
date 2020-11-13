package niles.lwjgl.rendering;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform1iv;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import niles.lwjgl.entity.Entity;
import niles.lwjgl.light.Lights;
import niles.lwjgl.util.Shader;
import niles.lwjgl.world.Camera;

public class Renderer {
	
	private Shader shader;
	
	
	public Renderer() {
		shader = new Shader("shader");
	}
	
	
	public void bindShader() {
		shader.bind();
	}
	
	public void render(Camera camera, Entity entity) {
		entity.getMaterial().useShader();
		
		glUniform1iv(glGetUniformLocation(shader.getProgram(), "sampler"), new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
		
		Matrix4f projection = camera.getProjection();
		Matrix4f transform = camera.getTransformation();
		Matrix4f object = entity.getTransform().getTransformation();
		
		entity.getMaterial().getShader().setUniform("projection", projection);
		entity.getMaterial().getShader().setUniform("transform", transform);	
		entity.getMaterial().getShader().setUniform("objectTransform", object);
		entity.getMaterial().getShader().setUniform("cameraPosition", camera.getPosition());
		
		
		entity.getGeometry().getVao().render();
	}
	
	public void useLights(Lights lights, Entity entity) {
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


	public Shader getShader() {
		return shader;
	}


	public void setShader(Shader shader) {
		this.shader = shader;
	}
	
	

}
