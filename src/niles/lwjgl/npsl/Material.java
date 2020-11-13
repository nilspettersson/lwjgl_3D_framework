package niles.lwjgl.npsl;

import java.util.HashMap;
import java.util.Map.Entry;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Material {
	
	private ShaderNp shader;
	
	private HashMap<String, Object> properties;
	
	public Material(ShaderNp shader) {
		this.shader = shader;
		
		properties = new HashMap<String, Object>();
		
	}
	
	public void setProperty(String name, Object value) {
		properties.put(name, value);
	}
	
	public void useShader() {
		shader.bind();
		
		for (Entry<String, Object> property : properties.entrySet()) {
		    System.out.println("Key = " + property.getKey() + ", Value = " + property.getValue());
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
		}
	}

	public ShaderNp getShader() {
		return shader;
	}

	public void setShader(ShaderNp shader) {
		this.shader = shader;
	}
	
	

}
