package testing;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import niles.lwjgl.entity.Entity;
import niles.lwjgl.entity.Geometry;
import niles.lwjgl.loop.Game;
import niles.lwjgl.loop.Scene;
import niles.lwjgl.npsl.MeshShader;
import niles.lwjgl.npsl.PostProcessingShader;
import niles.lwjgl.npsl.Shader;
import niles.lwjgl.util.Texture;

public class sceneTest extends Game{

	public static void main(String[] args) {
		new sceneTest();
	}

	
	
	@Override
	public void init() {
		Shader shader = new MeshShader("test.glsl");
		
		Shader post = new PostProcessingShader("postShader2.glsl");
		
		addScene(new Scene(getWindow()) {
			
			@Override
			public void onload() {
				Entity e2 = Entity.cube(1, 0, 0, 1, new Vector3f(1), shader);
				e2.addTexture(new Texture("res/rock.jpg"));
				
				addEntityToScene(e2);
				addLight(new Vector3f(0, 8, 8), new Vector3f(1), 10);
				
			}
			
			@Override
			public void update() {
				simpleCameraMovement(0.06f);
				simpleCameraRotation(1);
				
				getEntities().get(0).getTransform().getRotation().rotateAxis(0.01f, new Vector3f(0, 1, 1));
				
				if(getInput().isDown(GLFW.GLFW_KEY_1)) {
					useScene(1);
				}
			}
		});
		
		addScene(new Scene(getWindow()) {
			
			@Override
			public void onload() {
				
				addEntityToScene(Entity.cube(0, 0, 0, 1, new Vector3f(1, 0, 0), shader));
				addLight(new Vector3f(0, 8, 8), new Vector3f(1), 100);
			}
			
			@Override
			public void update() {
				simpleCameraMovement(0.06f);
				simpleCameraRotation(1);
				
				if(getInput().isDown(GLFW.GLFW_KEY_2)) {
					useScene(0);
				}
			}
		});
		
	}

}
