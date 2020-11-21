package niles.lwjgl.entity;

import java.util.HashMap;
import java.util.Map.Entry;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import niles.lwjgl.npsl.Shader;

public class Material {
	
	private Shader shader;
	
	private HashMap<String, Object> properties;
	
	public Material(Shader shader) {
		this.shader = shader;
		
		properties = new HashMap<String, Object>();
		
	}
	
	public void setProperty(String name, Object value) {
		properties.put(name, value);
	}
	
	public void useShader() {
		shader.bind();
		
		for (Entry<String, Object> property : properties.entrySet()) {
		    if(property.getValue().getClass() == Integer.class){
		    	shader.setUniform(property.getKey(), (int) property.getValue());
		    }
		    else if(property.getValue().getClass() == Float.class){
		    	shader.setUniform(property.getKey(), (float) property.getValue());
		    }
		    else if(property.getValue().getClass() == Vector2f.class){
		    	shader.setUniform(property.getKey(), (Vector2f) property.getValue());
		    }
		    else if(property.getValue().getClass() == Vector3f.class){
		    	shader.setUniform(property.getKey(), (Vector3f) property.getValue());
		    }
		    else if(property.getValue().getClass() == Vector4f.class){
		    	shader.setUniform(property.getKey(), (Vector4f) property.getValue());
		    }
		    else {
		    	System.err.print("uniform type is not a valid property: " + property.getValue().getClass());
		    	System.exit(0);
		    }
		}
	}

	public Shader getShader() {
		return shader;
	}

	public void setShader(Shader shader) {
		this.shader = shader;
	}
	
	

}
