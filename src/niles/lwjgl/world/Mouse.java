package niles.lwjgl.world;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.DoubleBuffer;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

public class Mouse {
	
     private static double newX = 1920 / 4;
     private static double newY = 1080 / 4;

     private static double prevX = 0;
     private static double prevY = 0;

     
     public static float x = 0;
     public static float y = 0;
     
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
    	 x += temp.x;
    	 y += temp.y;
    	 
    	 camera.setPosition(new Vector3f(-(float)x, (float)y, 0));
    	 
    	 setMouseLocation(win, win.getWidth() / 2, win.getHeight() / 2);
     }
     
     
     public static Vector2f getMousePosition(Window win,float sensitivity) {
    	 DoubleBuffer xb = BufferUtils.createDoubleBuffer(1);
         DoubleBuffer yb = BufferUtils.createDoubleBuffer(1);

         glfwGetCursorPos(win.getWindow(), xb, yb);
         xb.rewind();
         yb.rewind();

         newX = xb.get();
         newY = yb.get();

         double deltaX = newX - win.getWidth() / 2;
         double deltaY = newY - win.getHeight() / 2;



         prevX = newX;
         prevY = newY;

         x = (float) deltaX;
         y = (float) deltaY;

         
         Mouse.setMouseLocation(win, 1920 / 2, 1080 / 2);
         
         return new Vector2f((float)deltaX*sensitivity,(float)deltaY*sensitivity);
     }
     
     
     public static void moveMouse(Window win,float sensitivity) {
    	 DoubleBuffer xb = BufferUtils.createDoubleBuffer(1);
         DoubleBuffer yb = BufferUtils.createDoubleBuffer(1);

         glfwGetCursorPos(win.getWindow(), xb, yb);
         xb.rewind();
         yb.rewind();


         double deltaX = xb.get() - win.getWidth() / 2.0;
         double deltaY = yb.get() - win.getHeight() / 2.0;


         x += (float) deltaX * sensitivity / win.getWidth();
         y += (float) deltaY * sensitivity / win.getHeight();
         
         setMouseLocation(win, win.getWidth()/2, win.getHeight()/2);
     }


     
     

}
