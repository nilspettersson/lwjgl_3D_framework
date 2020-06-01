package niles.lwjgl.world;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.DoubleBuffer;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

public class Mouse {
	
     static double newX = 1920/4;
     static double newY = 1080/4;

     static double prevX = 0;
     static double prevY = 0;

     
     public static float myX=0;
     public static float myY=0;
     
     public Mouse() {
    	 
	 }
     
     
     
     public static void isVisible(Window win, boolean visible) {
    	 if(!visible) {
    		 glfwSetInputMode(win.getWindow(), GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
    	 }
    	 else {
    		 glfwSetInputMode(win.getWindow(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
    	 }
     }
     
     
     public static void setMouseLocation(Window win, float x,float y) {
    	 
    	 glfwSetCursorPos(win.getWindow(), x, y);
     }
     

     //this function does all the hard work to make an the mouse control the camera 
     public static void moveCamera(Window win,Camera camera,float sensitivity ) {
    	 
    	 Vector2f temp=getMousePosition(win,sensitivity);
    	 myX+=temp.x;
    	 myY+=temp.y;
    	 
    	 camera.setPosition(new Vector3f(-(float)myX,(float)myY,0));
    	 
    	 setMouseLocation(win, win.getWidth()/2, win.getHeight()/2);
     }
     
     
     public static Vector2f getMousePosition(Window win,float sensitivity) {
    	 DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
         DoubleBuffer y = BufferUtils.createDoubleBuffer(1);

         glfwGetCursorPos(win.getWindow(), x, y);
         x.rewind();
         y.rewind();

         newX = x.get();
         newY = y.get();

         double deltaX = newX - win.getWidth()/2;
         double deltaY = newY - win.getHeight()/2;



         prevX = newX;
         prevY = newY;

         myX = (float) deltaX;
         myY = (float) deltaY;

         
         Mouse.setMouseLocation(win, 1920/2, 1080/2);
         
         return new Vector2f((float)deltaX*sensitivity,(float)deltaY*sensitivity);
     }
     
     
     public static void moveMouse(Window win,float sensitivity) {
    	 DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
         DoubleBuffer y = BufferUtils.createDoubleBuffer(1);

         glfwGetCursorPos(win.getWindow(), x, y);
         x.rewind();
         y.rewind();


         double deltaX = x.get() - win.getWidth()/2;
         double deltaY = y.get() - win.getHeight()/2;


         myX += (float) deltaX * sensitivity / 1920;
         myY += (float) deltaY * sensitivity / 1080;
         
         setMouseLocation(win, win.getWidth()/2, win.getHeight()/2);
     }


     
     

}
