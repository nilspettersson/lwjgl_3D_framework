package niles.lwjgl.rendering;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform1iv;

import org.joml.Matrix4f;

import niles.lwjgl.entity.Entity;
import niles.lwjgl.util.Shader;
import niles.lwjgl.world.Camera;

public class Renderer {
	
	Shader shader;
	
	
	public Renderer() {
		shader = new Shader("shader");
	}
	
	
	public void bindShader() {
		shader.bind();
	}
	
	public void render(Camera camera, Entity entity) {
		
		
		glUniform1iv(glGetUniformLocation(shader.getProgram(), "sampler"), new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
	
		
		Matrix4f projection = camera.getProjection();
		Matrix4f transform = camera.getTransformation();
		Matrix4f object = entity.getTransform().getTransformation();
		
		/*Matrix4f send = new Matrix4f();
		projection.mul(transform, send);
		send.mul(object, send);*/
		
		shader.setUniform("projection", projection);
		shader.setUniform("transform", transform);	
		shader.setUniform("objectTransform", object);
		shader.setUniform("cameraPosition", camera.getPosition());
		entity.getGeometry().getVao().render();
		
	}

}
