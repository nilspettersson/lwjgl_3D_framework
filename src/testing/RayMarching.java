package testing;

import org.joml.Vector3f;

import niles.lwjgl.entity.Entity;
import niles.lwjgl.loop.Game;
import niles.lwjgl.loop.Scene;
import niles.lwjgl.npsl.MeshShader;
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
				getCamera().setPosition(new Vector3f(0, 4, 0));
				
				addLight(new Vector3f(8, 8, 1), new Vector3f(1, 0.4f, 0.4f), 12);
				addLight(new Vector3f(-20, 8, 5), new Vector3f(0.4f, 0.4f, 1), 12);
				addLight(new Vector3f(0, 6, 8), new Vector3f(0.4f, 1, 0.4f), 12);
				
				usePostProcessing(new PostProcessingShader("RayMarching.glsl"));
			}
			
			@Override
			public void update() {
				simpleCameraMovement(1f);
				simpleCameraRotation(1);
				
				value += 0.01f;
				for(int i = 0; i < getLights().size(); i++) {
					if(i == 0) {
						getLights().get(i).translate(new Vector3f((float)Math.sin(value) * -0.2f, 0, (float)Math.cos(value) * -0.2f));
					}
					else {
						getLights().get(i).translate(new Vector3f((float)Math.sin(value) * 0.2f, 0, (float)Math.cos(value) * 0.2f));
					}
				}
				
			}
			
		});
		
	}
}
