package testing;

import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;
import org.joml.Vector3f;
import org.joml.Vector4f;

import niles.lwjgl.entity.Entity;
import niles.lwjgl.entity.Geometry;
import niles.lwjgl.loop.Game;
import niles.lwjgl.npsl.MeshShader;
import niles.lwjgl.npsl.PostProcessingShader;
import niles.lwjgl.npsl.Shader;
import niles.lwjgl.util.Texture;

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
	ArrayList<Entity> entites;
   // Fbo fbo = new Fbo();
    Shader post;
    Shader shader;
    
	@Override
	public void setup() {
		post = new PostProcessingShader("postShader.glsl");
		shader = new MeshShader("test.glsl");
		
		getLights().addLight(new Vector3f(-44, 12, 12), new Vector3f(0f, 0.6f, 1f), 10);
		for(int i = 0; i < 20; i++) {
			getLights().addLight(new Vector3f((float) (Math.random() * 200) - 100, (float) (Math.random() * 40) + 6, (float) (Math.random() * 200) - 100), new Vector3f((float) Math.random(), (float) Math.random(), (float) Math.random()), 10);
		}
		
		//testing many cubes in one buffer. one cube has 36 vertices.
		//this method does not allow separate movement but is faster to render.
		g = new Geometry(36 * 20 * 20 * 20);
		for(int x = 0; x < 16; x++) {
			for(int y = 0; y < 16; y++) {
				for(int z = 0; z < 16; z++) {
					g.createCube(x * 6 - 100, y * 6, z * 6 - 100, new Vector4f(0));
				}
			}
		}
		
		g.updateVertices();
		g.updateIndices();
		
		//one entity has one geometry. one geometry can have many separate objects.
		//this method does allow separate movement.
		entites = new ArrayList<Entity>();
		int index = 0;
		for(int x = 0; x < 1; x++) {
			for(int y = 0; y < 1; y++) {
				for(int z = 0; z < 1; z++) {
					entites.add(new Entity(48, shader));
					entites.get(index).setGeometry(g);
					
					entites.get(index).getTransform().setScale(new Vector3f(1, 1, 1));
					entites.get(index).getTransform().getPosition().x += x * 3;
					entites.get(index).getTransform().getPosition().y += y * 3;
					entites.get(index).getTransform().getPosition().z += z * 3;
					
					index++;
				}
			}
		}
		entites.add(new Entity(48, shader));
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
		getWindow().setVSync(false);
	}
	
	@Override
	public void update() {
		simpleCameraRotation(1f);
		simpleCameraMovement(0.3f);
		
		//entites rendered between bindFbo and unbindFbo will be put in texture for post processing
		bindFbo();
		for(int i = 0; i < entites.size(); i++) {
			render(entites.get(i));
		}
		unbindFbo();
		
		//renders the post processed fbo texture.
		renderFbo(post);
		
		System.out.println(getFps());
		setFpsCap(120);
	}
}
