package niles.lwjgl.util;

import org.joml.Vector2f;

public class Hitbox {
	
	private float x;
	private float y;
	private float width;
	private float height;
	
	
	public Hitbox(float x, float y, float width, float height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	
	public boolean intersect(Hitbox hitbox) {
		if(x<hitbox.x+hitbox.width && y>hitbox.y-hitbox.height) {
			if(x+width>hitbox.x && y-height<hitbox.y) {
				return true;
			}
		}
		return false;
	}
	
	
	
	
	//this will return the amount that the rect needs to be moved to not intersect.
	public Vector2f intersectFixOverlap(Hitbox hitbox) {
		float dxEB=(x+width)-(hitbox.x);
		float dyEB=(y-height)-(hitbox.y);
		
		float dxBE=(x)-(hitbox.x+hitbox.width);
		float dyBE=y-(hitbox.y-hitbox.height);
		
		
		if(x<hitbox.x+hitbox.width && y>hitbox.y-hitbox.height) {
			if(x+width>hitbox.x && y-height<hitbox.y) {
				float dx=x-hitbox.x;
				float dy=y-hitbox.y;
				
				
				
				if(Math.abs(dxEB)<Math.abs(dyEB) && Math.abs(dxEB)<Math.abs(dxBE) && Math.abs(dxEB)<Math.abs(dyBE)) {
					return new Vector2f(-dxEB/width,0);
				}
				else if(Math.abs(dyEB)<Math.abs(dxEB) && Math.abs(dyEB)<Math.abs(dxBE) && Math.abs(dyEB)<Math.abs(dyBE)){
					return new Vector2f(0,-dyEB/width);
				}
				else if(Math.abs(dxBE)<Math.abs(dxEB) && Math.abs(dxBE)<Math.abs(dyEB) && Math.abs(dxBE)<Math.abs(dyBE)){
					return new Vector2f(-dxBE/width,0);
				}
				else if(Math.abs(dyBE)<Math.abs(dxEB) && Math.abs(dyBE)<Math.abs(dyEB) && Math.abs(dyBE)<Math.abs(dxBE)){
					return new Vector2f(0,-dyBE/width);
				}
				
				
			}
		}
		return new Vector2f(0,0);
	}
	
	
	


	public float getX() {
		return x;
	}


	public void setX(float x) {
		this.x = x;
	}


	public float getY() {
		return y;
	}


	public void setY(float y) {
		this.y = y;
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
	
	
	
	

}
