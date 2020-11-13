package niles.lwjgl.npsl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.joml.Vector4f;

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
		    	
		    }
		}
	}

}
