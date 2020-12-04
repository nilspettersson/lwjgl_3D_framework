package niles.lwjgl.rendering;
import org.joml.Matrix4f;

import niles.lwjgl.line.Lines;
import niles.lwjgl.world.Camera;

public class LineRenderer {
	
	public void render(Camera camera, Lines lines) {
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
	
}
