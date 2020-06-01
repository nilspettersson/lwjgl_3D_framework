package niles.lwjgl.entity;

import java.util.ArrayList;

import niles.lwjgl.util.Texture;

public class Entity {
	
	private Geometry geometry;
	private Transform transform;
	private ArrayList<Texture> textures;
	
	public Entity(int geometrySize) {
		geometry = new Geometry(geometrySize);
		transform = new Transform();
		textures = new ArrayList<Texture>();
	}
	
	public void addTexture(Texture texture) {
		textures.add(texture);
	}
	public void bindTextures() {
		for(int i = 0; i < textures.size(); i++) {
			textures.get(i).bind(i);
		}
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
	
	

}
