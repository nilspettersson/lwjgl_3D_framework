package niles.lwjgl.entity;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Vertex {
	
	public static int  size = 13;
	
	private Vector3f position;
	private Vector4f color;
	private float textureId;
	private Vector2f textureCords;
	
	private Vector3f normal;
	
	public Vertex(Vector3f position, Vector4f color, float textureId, Vector2f textureCords, Vector3f normal) {
		this.position = position;
		this.color = color;
		this.textureId = textureId;
		
		this.textureCords = textureCords; 
		
		this.normal = normal;
	}
	
	public float[] toArray() {
		return new float[] {position.x, position.y, position.z, color.x, color.y, color.z, color.w, textureId, textureCords.x, textureCords.y, normal.x, normal.y, normal.z};
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector4f getColor() {
		return color;
	}

	public void setColor(Vector4f color) {
		this.color = color;
	}

	public float getTextureId() {
		return textureId;
	}

	public void setTextureId(float textureId) {
		this.textureId = textureId;
	}

	public Vector2f getTextureCords() {
		return textureCords;
	}

	public void setTextureCords(Vector2f textureCords) {
		this.textureCords = textureCords;
	}

	public Vector3f getNormal() {
		return normal;
	}

	public void setNormal(Vector3f normal) {
		this.normal = normal;
	}
	
	
	
	

}
