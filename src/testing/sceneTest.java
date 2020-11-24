package testing;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import niles.lwjgl.entity.Entity;
import niles.lwjgl.loop.Game;
import niles.lwjgl.loop.Scene;
import niles.lwjgl.npsl.MeshShader;

public class sceneTest extends Game{

	public static void main(String[] args) {
		new sceneTest();
	}

	@Override
	public void init() {
		addScene(new Scene() {
			
			@Override
			public void onload() {
				addEntityToScene(Entity.cube(0, 0, 0, 1, new Vector3f(1), new MeshShader("test.glsl")));
				getLights().addLight(new Vector3f(0, 8, 8), new Vector3f(1), 10);
			}
			
			@Override
			public void update() {
				simpleCameraMovement(0.06f);
				simpleCameraRotation(1);
				
				if(getInput().isDown(GLFW.GLFW_KEY_1)) {
					useScene(1);
				}
			}
		});
		
		addScene(new Scene() {
			
			@Override
			public void onload() {
				addEntityToScene(Entity.cube(0, 0, 0, 1, new Vector3f(1, 0, 0), new MeshShader("test.glsl")));
				getLights().addLight(new Vector3f(0, 8, 8), new Vector3f(1), 10);
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
