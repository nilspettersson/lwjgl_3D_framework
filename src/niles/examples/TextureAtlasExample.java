package niles.examples;

import org.joml.Vector3f;

import niles.lwjgl.entity.Entity;
import niles.lwjgl.entity.Geometry;
import niles.lwjgl.loop.Game;
import niles.lwjgl.loop.Scene;
import niles.lwjgl.npsl.MeshShader;
import niles.lwjgl.util.Texture;

public class TextureAtlasExample extends Game {

	public static void main(String[] args) {
		new TextureAtlasExample();

	}

	@Override
	public void init() {
		addScene(new Scene(getWindow()) {
			
			@Override
			protected void onload() {
				Entity e = new Entity(100, new MeshShader("test.glsl"));
				
				Texture texture = new Texture("res/atlas.png");
				texture.setSpriteWidth(16);
				texture.setSpriteHeight(16);
				
				e.addTexture(texture);
				e.getGeometry().createFace(0, 0, 0, texture, 1, 1);
				e.bindGeometry();
				
				addEntityToScene(e);
				addLight(new Vector3f(0, 3, 8), new Vector3f(1), 10);
			}
			
			@Override
			protected void update() {
				simpleCameraMovement(0.1f);
				simpleCameraRotation(1);
			}
			
		});
	}

}
