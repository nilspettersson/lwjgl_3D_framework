package testing;

import org.joml.Vector3f;
import niles.lwjgl.entity.Entity;
import niles.lwjgl.loop.Game;
import niles.lwjgl.npsl.ShaderNp;
import niles.lwjgl.util.Texture;

public class test2 extends Game {
	public static void main(String[] args) {
		new test2();
	}
	
	Entity e;
	Entity e2;
	ShaderNp shader;
	
	@Override
	public void setup() {
		shader = new ShaderNp("test.glsl");
		getCamera().setPosition(new Vector3f(0, 0, 10));
		
		e = Entity.cube(0, -2, 0, 8, new Vector3f(1), shader);
		e.addTexture(new Texture("res/rock.jpg"));
		
		e2 = Entity.cube(0, -15, 0, 8, new Vector3f(1), shader);
		e2.addTexture(new Texture("res/rock.jpg"));
		e2.getTransform().setScale(new Vector3f(0.5f));
		
		getLights().addLight(new Vector3f(4,0,4), new Vector3f(1), 8);
	}
	
	@Override
	public void update() {
		simpleCameraRotation(1f);
		simpleCameraMovement(0.03f);
		
		render(e);
		render(e2);
		
		setFpsCap(120);
	}
}