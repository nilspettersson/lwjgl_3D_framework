package testing;

import static org.lwjgl.glfw.GLFW.*;
import org.joml.Vector3f;
import org.joml.Vector4f;
import niles.lwjgl.entity.Entity;
import niles.lwjgl.light.Lights;
import niles.lwjgl.loop.Game;
import niles.lwjgl.npsl.ShaderNp;
import niles.lwjgl.util.Texture;

public class test2 extends Game {
	public static void main(String[] args) {
		new test2();
	}
	
	Entity e;
	Lights lights;
	ShaderNp shader;
	
	@Override
	public void setup() {
		shader = new ShaderNp("test.glsl");
		getCamera().setPosition(new Vector3f(0, 0, 10));
		
		e = new Entity(36, shader);
		e.getGeometry().createCube(0, -2, 0, new Vector4f(1));
		e.getTransform().setScale(new Vector3f(8));
		e.bindGeometry();
		e.addTexture(new Texture("res/rock.jpg"));
		e.getMaterial().setProperty("c", 3);
		
		lights = new Lights();
		lights.addLight(new Vector3f(4,0,4), new Vector3f(1), 8);
	}
	
	@Override
	public void update() {
		simpleCameraRotation(1f);
		simpleCameraMovement(0.02f);
		
		render(e, lights);
		
		setFpsCap(120);
	}

}
