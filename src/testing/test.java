package testing;

import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;
import org.joml.Vector3f;
import org.joml.Vector4f;

import niles.lwjgl.entity.Entity;
import niles.lwjgl.entity.Geometry;
import niles.lwjgl.fbo.Fbo;
import niles.lwjgl.light.Lights;
import niles.lwjgl.loop.Game;
import niles.lwjgl.util.Shader;
import niles.lwjgl.util.Texture;
import niles.lwjgl.world.Input;
import niles.lwjgl.world.Mouse;

public class test extends Game {
	/*implement later.
	float maxAngle = 0.4f;
	if(getCamera().getRotation().x > maxAngle && Mouse.myY < 0) {
		Mouse.myY = 0;
	}
	else if(getCamera().getRotation().x < -maxAngle && Mouse.myY > 0) {
		Mouse.myY = 0;
	}*/

	public static void main(String[] args) {
		new test();
	}
	
	Geometry g;
	
	Geometry loaded;
	
	
	ArrayList<Entity> entites;
	
	Input input;
	
	Lights lights;
	
    
    Fbo fbo = new Fbo();

    Shader shader;
    
	@Override
	public void setup() {
		shader = new Shader("post");
		
		lights = new Lights();
		lights.addLight(new Vector3f(8, 6, 4), new Vector3f(1f, 1f, 1f), 10);
		for(int i = 0; i < 20; i++) {
			lights.addLight(new Vector3f((float) (Math.random() * 200) - 100, (float) (Math.random() * 40) + 6, (float) (Math.random() * 200) - 100), new Vector3f((float) Math.random(), (float) Math.random(), (float) Math.random()), 50);
		}
		
		
		//testing many cubes in one buffer. one cube has 36 vertices.
		//this method does not allow separate movement.
		g = new Geometry(36 * 20 * 20 * 20);
		//g.createCube(0, 0, 0, new Vector4f(1));
		for(int x = 0; x < 20; x++) {
			for(int y = 0; y < 20; y++) {
				for(int z = 0; z < 20; z++) {
					g.createCube(x * 6 - 100, y * 6, z * 6 - 100, new Vector4f(1));
				}
			}
		}
		
		
		//example of how to load an obj file.
		//loaded = Geometry.loadModel("res/terain");
		
		
		g.updateVertices();
		g.updateIndices();
		
		//one entity has one geometry. one geometry can have many separate objects.
		//this method does allow separate movement.
		entites = new ArrayList<Entity>();
		int index = 0;
		for(int x = 0; x < 1; x++) {
			for(int y = 0; y < 1; y++) {
				for(int z = 0; z < 1; z++) {
					entites.add(new Entity(48));
					entites.get(index).setGeometry(g);
					
					entites.get(index).getTransform().setScale(new Vector3f(1, 1, 1));
					entites.get(index).getTransform().getPosition().x += x * 6;
					entites.get(index).getTransform().getPosition().y += y * 6;
					entites.get(index).getTransform().getPosition().z += z * 6;
					
					
					index++;
				}
			}
		}
		entites.add(new Entity(480));
		entites.get(entites.size()-1).getGeometry().createFace(0, 0, 1);
		
		entites.get(entites.size()-1).getTransform().setScale(new Vector3f(100, 100f, 1));
		entites.get(entites.size()-1).getTransform().getRotation().rotateAxis((float) (-Math.PI/2), 1, 0, 0);
		entites.get(entites.size()-1).getTransform().getPosition().x += 0 * 6;
		entites.get(entites.size()-1).getTransform().getPosition().y += 0 - 1;
		entites.get(entites.size()-1).getTransform().getPosition().z += 0 * 6;
		entites.get(entites.size()-1).getGeometry().updateVertices();
		entites.get(entites.size()-1).getGeometry().updateIndices();
		
		entites.get(0).addTexture(new Texture("res/wood.jpg"));
		entites.get(0).bindTextures();
		
		
		getCamera().setPosition(new Vector3f(0, 0, 10));
		
		
		
		input = new Input(getWindow());
		
		getWindow().setVSync(false);
		
		
		
		//depth = new Fbo();
		fbo = new Fbo();
		
	}
	

	@Override
	public void update() {
		Mouse.isVisible(getWindow(), false);
		
		
		//camera rotation
		Mouse.moveMouse(getWindow(), 1f);
		rotateCamera(-Mouse.myY, -Mouse.myX);
		
		float speed = 0.2f;
		if(input.isDown(GLFW_KEY_W)) {
			moveCameraForward(speed);
		}
		if(input.isDown(GLFW_KEY_S)) {
			moveCameraBackward(speed);
		}
		if(input.isDown(GLFW_KEY_A)) {
			moveCameraLeft(speed);
		}
		if(input.isDown(GLFW_KEY_D)) {
			moveCameraRight(speed);
		}
		if(input.isDown(GLFW_KEY_Q)) {
			moveCameraDown(speed);
		}
		if(input.isDown(GLFW_KEY_E)) {
			moveCameraUp(speed);
		}
		
		getRenderer().bindShader();
		getRenderer().useLights(lights);
		
		fbo.bind();
		getRenderer().setShader(new Shader("shader"));
		entites.get(0).bindTextures();
		for(int i = 0; i < entites.size(); i++) {
			render(entites.get(i));
		}
		fbo.unBind();
		
		
		
		
		fbo.bindTexture();
		fbo.bindDepthTexture();
		
		fbo.render(shader, getCamera());
		
		
		
		
		System.out.println(getFps());
		setFpsCap(120);
	}

}
