package testing;

import org.joml.Vector3f;

import niles.lwjgl.loop.Game;
import niles.lwjgl.loop.Scene;
import niles.lwjgl.npsl.PostProcessingShader;

public class RayMarching extends Game{

	public static void main(String[] args) {
		new RayMarching();
	}
	
	float value = 0;

	@Override
	public void init() {
		addScene(new Scene(getWindow()) {
			@Override
			public void onload() {
				usePostProcessing(new PostProcessingShader("RayMarching.glsl"));
				
				getLights().addLight(new Vector3f(8, 6, 1), new Vector3f(1, 0.5f, 0.5f), 5);
				getLights().addLight(new Vector3f(-20, 6, 5), new Vector3f(0.5f, 0.5f, 1), 5);
				getLights().addLight(new Vector3f(0, 6, 5), new Vector3f(0.5f, 1, 0.5f), 5);
			}
			
			@Override
			public void update() {
				simpleCameraMovement(0.1f);
				simpleCameraRotation(1);
				
				value += 0.01f;
				for(int i = 0; i < getLights().getLights().size(); i++) {
					if(i == 0) {
						getLights().getLights().get(i).setPosition(getLights().getLights().get(i).getPosition().add(new Vector3f((float)Math.sin(value) * -0.1f, 0, (float)Math.cos(value) * -0.1f)));
					}
					else {
						getLights().getLights().get(i).setPosition(getLights().getLights().get(i).getPosition().add(new Vector3f((float)Math.sin(value) * 0.1f, 0, (float)Math.cos(value) * 0.1f)));
					}
					
				}
				
			}
			
		});
	}

}
