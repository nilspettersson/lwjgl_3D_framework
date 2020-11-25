package niles.lwjgl.entity;

import java.util.ArrayList;

import org.joml.Vector3f;
import org.joml.Vector4f;

import niles.lwjgl.npsl.Shader;
import niles.lwjgl.util.Texture;

public class Entity {
	
	private Geometry geometry;
	private Transform transform;
	private ArrayList<Texture> textures;
	private Material material;
	
	public Entity(int geometrySize, Shader shader) {
		geometry = new Geometry(geometrySize);
		transform = new Transform();
		textures = new ArrayList<Texture>();
		
		material = new Material(shader);
	}
	
	public static Entity cube(float x, float y, float z, float scale, Vector3f color, Shader shader) {
		Entity e = new Entity(36, shader);
		e = new Entity(36, shader);
		e.getGeometry().createCube(x, y, z, new Vector4f(color, 1));
		e.getTransform().setScale(new Vector3f(scale));
		e.bindGeometry();
		return e;
	}
	
	public void addTexture(Texture texture) {
		textures.add(texture);
	}
	public void bindTextures() {
		if(textures.size() == 0) {
			Texture.unbind();
		}
		for(int i = 0; i < textures.size(); i++) {
			textures.get(i).bind(i);
		}
	}
	
	//textures should be deleted when not used anymore.
	public void DeleteTextures() {
		for(int i = 0; i < textures.size(); i++) {
			textures.get(i).delete();
		}
	}
	
	public void bindGeometry() {
		geometry.updateVertices();
		geometry.updateIndices();
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public Transform getTransform() {
		return transform;
	}

	public void setTransform(Transform transform) {
		this.transform = transform;
	}

	public ArrayList<Texture> getTextures() {
		return textures;
	}

	public void setTextures(ArrayList<Texture> textures) {
		this.textures = textures;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}
	
	

}
