package niles.lwjgl.world;

import static org.lwjgl.glfw.GLFW.*;

import static org.lwjgl.opengl.GL11.*;

import org.joml.Vector4f;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Window {
	
	private long window;
	
	private int width;
	private int height;
	
	
	private Input input;
	
	public Window(int width,int height, boolean fullScreen) {
		this.width=width;
		this.height=height;
		
		if(!glfwInit()) {
			throw new IllegalStateException("Failed ti initialze GLFW!");
		}
		
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		
		if(fullScreen) {
			window=glfwCreateWindow(width, height, "", glfwGetPrimaryMonitor(), 0);
		}
		else {
			window=glfwCreateWindow(width, height, "", 0, 0);
			
			
			GLFWVidMode videoMode=glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(window, (videoMode.width()-width)/2, (videoMode.height()-height)/2);
		}
		
		
		
		
		if(window==0) {
			throw new IllegalStateException("failed to create window");
		}
		
		
		
		
		
		
		
		
		
		glfwMakeContextCurrent(window);
		GL.createCapabilities();
		
		
		glEnable(GL_TEXTURE_2D);
		
		
		
		input=new Input(this);
		
		glfwShowWindow(window);
	}
	
	
	public void clean() {
		
		glfwSwapBuffers(window);
		
		
		input.update();
		
		if(input.isDown(GLFW_KEY_F12)) {
			glfwSetWindowShouldClose(getWindow(), true);
		}
		
		end=getTime();
	}
	
	
	public boolean shouldUpdate(){
		return !glfwWindowShouldClose(getWindow());
	}
	
	public void close() {
		glfwSetWindowShouldClose(getWindow(), true);
	}
	
	
	
	public void setVSync(boolean on) {
		if(on) {
			glfwSwapInterval(1);
		}
		else {
			glfwSwapInterval(0);
		}
		
	}
	
	
	
	public Input getInput() {
		return input;
	}




	public long getWindow() {
		return window;
	}
	

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	
	
	public void drawInit(Vector4f clearColor) {
		start=getTime();
		
		glfwPollEvents();
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glClearColor(clearColor.x, clearColor.y, clearColor.z, clearColor.w);
		glClear(GL_COLOR_BUFFER_BIT);
		
		
	}
	
	
	public long getTime() {
		return System.nanoTime()/1000000;
	}
	
	
	private long start=0;
	private long end=0;
	
	private int fps=0;
	public void update(int fpsCap) {
			
		//setting up the fps cap.
		double cap = (1.0/fpsCap)*1000;
		
		//finding the difference in time. cap-difference. 
		double dif=end-start;
		//if the difference is to much then don't do more. if more then error in Thread.sleep.
		if(dif>=cap){
		cap-=dif;
		}
		
		if(cap<=0){
			cap=1;
		}
		try {
			Thread.sleep((long) cap);
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// checking fps
		if(dif<(1.0/fpsCap)*1000) {
			cap=(1000/cap);
			fps=(int) cap;
			//System.out.println(fps);
		}
		else if(dif>=(1.0/fpsCap)*1000) {
			dif=(1000/dif);
			fps=(int) dif;
			//System.out.println(dif);
		}
	}


	public int getFps() {
		return fps;
	}


	
	
	

}
