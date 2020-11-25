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

	
	Shader shader;
	Shader post;
	
	@Override
	public void init() {
		shader = new MeshShader("test.glsl");
		
		post = new PostProcessingShader("postShader2.glsl");
		
		addScene(new Scene() {
			
			@Override
			public void onload() {
				Geometry g = Geometry.loadModel("res/terain");
				Texture t = new Texture("res/rock.jpg");
				Entity e = new Entity(10, shader);
				e.setGeometry(g);
				e.getTransform().setScale(new Vector3f(8));
				e.getTransform().setPosition(new Vector3f(0, -4, 0));
				addEntityToScene(e);
				
				Entity e2 = Entity.cube(0, 0, 0, 1, new Vector3f(1), shader);
				e2.addTexture(t);
				addEntityToScene(e2);
				getLights().addLight(new Vector3f(0, 8, 8), new Vector3f(1), 10);
				
				
			}
			
			@Override
			public void update() {
				//usePostProcessing(post);
				
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
				
				addEntityToScene(Entity.cube(0, 0, 0, 1, new Vector3f(1, 0, 0), shader));
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
