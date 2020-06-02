package niles.lwjgl.light;

import java.util.ArrayList;

import org.joml.Vector3f;

public class Lights {
	
	private ArrayList<Light>lights;
	
	public Lights() {
		lights = new ArrayList<Light>();
	}
	
	public void addLight(Vector3f position, Vector3f color) {
		lights.add(new Light(position, color));
	}

	public ArrayList<Light> getLights() {
		return lights;
	}

	public void setLights(ArrayList<Light> lights) {
		this.lights = lights;
	}
	
	
}
