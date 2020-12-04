package niles.lwjgl.rendering;
import org.joml.Matrix4f;
import niles.lwjgl.draw.Lines;
import niles.lwjgl.world.Camera;

public class LineRenderer {
	
	
	
	public void render(Camera camera, Lines entity) {
		entity.getShader().bind();
		
		
		Matrix4f projection = camera.getProjection();
		Matrix4f transform = camera.getTransformation();
		Matrix4f object = entity.getTransform().getTransformation();
		
		entity.getShader().setUniform("projection", projection);
		entity.getShader().setUniform("transform", transform);	
		entity.getShader().setUniform("objectTransform", object);
		entity.getShader().setUniform("cameraPosition", camera.getPosition());
		
		
		entity.getVao().render();
	}
	
}
