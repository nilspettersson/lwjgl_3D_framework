package testing;

import org.joml.Vector3f;
import niles.lwjgl.entity.Entity;
import niles.lwjgl.loop.Game;
import niles.lwjgl.npsl.MeshShader;
import niles.lwjgl.npsl.PostProcessingShader;
import niles.lwjgl.npsl.Shader;
import niles.lwjgl.util.Texture;

public class test2 extends Game {
	public static void main(String[] args) {
		new test2();
	}
	
	Entity e;
	Entity e2;
	
	Shader shader;
	Shader postProcessing;
	
	@Override
	public void setup() {
		postProcessing = new PostProcessingShader("postShader2.glsl");
		shader = new MeshShader("test.glsl");
		
		getCamera().setPosition(new Vector3f(0, 0, 10));
		
		e = Entity.cube(0, -2, 0, 8, new Vector3f(1), shader);
		e.addTexture(new Texture("res/rock.jpg"));
		
		e2 = Entity.cube(0, -15, 0, 8, new Vector3f(1), shader);
		e2.addTexture(new Texture("res/wood.jpg"));
		e2.getTransform().setScale(new Vector3f(0.5f));
		
		getLights().addLight(new Vector3f(-3,-4 ,-4), new Vector3f(1), 6);
		getLights().addLight(new Vector3f(8,-4 ,-4), new Vector3f(1), 6);
	}
	
	float time = 0;
	
	@Override
	public void update() {
		simpleCameraRotation(1f);
		simpleCameraMovement(0.03f);
		
		setFboUniform("time", time);
		time += 1;
		
		bindFbo();
		render(e);
		render(e2);
		unbindFbo();
		
		renderFbo(postProcessing);
		
		setFpsCap(120);
	}
}