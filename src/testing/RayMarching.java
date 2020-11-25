package testing;

import org.joml.Vector3f;

import niles.lwjgl.loop.Game;
import niles.lwjgl.loop.Scene;
import niles.lwjgl.npsl.PostProcessingShader;

public class RayMarching extends Game{

	public static void main(String[] args) {
		new RayMarching();
	}

	@Override
	public void init() {
		addScene(new Scene(getWindow()) {
			@Override
			public void onload() {
				usePostProcessing(new PostProcessingShader("RayMarching.glsl"));
				
				getLights().addLight(new Vector3f(1), new Vector3f(1), 10);
			}
			
			@Override
			public void update() {
				simpleCameraMovement(0.2f);
				simpleCameraRotation(1);
				
			}
			
		});
	}

}
