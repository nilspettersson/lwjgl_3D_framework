package niles.lwjgl.world;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
	private Vector3f position;
	private Matrix4f projection;
	
	private float width;
	private float height;
	
	private float scale=1;
	
	public Camera(float width,float height) {
		this.width=width;
		this.height=height;
		position=new Vector3f(0,0,0);
		projection=new Matrix4f().setOrtho2D(-width/2, width/2, -height/2, height/2);
	}
	
	public void setPosition(Vector3f position) {
		this.position=position;
	}
	
	
	public Vector3f getPosition() {
		return position;
	}
	public Matrix4f getProjection() {
		Matrix4f target=new Matrix4f();
		Matrix4f pos=new Matrix4f().setTranslation(position).scale(scale);
		
		target=projection.mul(pos,target);
		return target;
	}
	
	
	
	

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
	
	

}
