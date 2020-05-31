package niles.lwjgl.world;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;

public class Input {
	private Window window;
	
	private boolean[] keys=new boolean[GLFW_KEY_LAST];
	
	public Input(Window window) {
		this.window=window;
		
		
		for(int i=0;i<keys.length;i++) {
			keys[i]=false;
		}
	}
	
	public void update() {
		for(int i=0;i<keys.length;i++) {
			keys[i]=isDown(i);
		}
	}
	
	public boolean isPressed(int GLFW_KEY) {
		if(glfwGetKey(window.getWindow(), GLFW_KEY)==GL_TRUE && !keys[GLFW_KEY]) {
			return true;
		}
		return false;
	}
	public boolean isReleased(int GLFW_KEY) {
		if(glfwGetKey(window.getWindow(), GLFW_KEY)==GLFW_FALSE && keys[GLFW_KEY]) {
			return true;
		}
		return false;
	}
	
	public boolean isDown(int GLFW_KEY) {
		if(glfwGetKey(window.getWindow(), GLFW_KEY)==GL_TRUE) {
			return true;
		}
		return false;
	}

}
