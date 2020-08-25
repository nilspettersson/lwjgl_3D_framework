package niles.lwjgl.fbo;

import static org.lwjgl.opengl.EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.GL_FRAMEBUFFER_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.GL_RENDERBUFFER_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glBindFramebufferEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glBindRenderbufferEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glFramebufferRenderbufferEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glFramebufferTexture2DEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glGenFramebuffersEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glGenRenderbuffersEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glRenderbufferStorageEXT;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_INT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT24;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;

import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static org.lwjgl.opengl.GL20.*;

import niles.lwjgl.util.Model;
import niles.lwjgl.util.Shader;
import niles.lwjgl.world.Camera;

public class Fbo {
	
	private int colorTextureID;
	private int depthTextureID;
	private int framebufferID;
	private int depthRenderBufferID;
	
	public Fbo() {
		framebufferID = glGenFramebuffersEXT();
        colorTextureID = glGenTextures();
        depthTextureID = glGenTextures();
        depthRenderBufferID = glGenRenderbuffersEXT();
 
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, framebufferID);
 
        // initialize color texture
        glBindTexture(GL_TEXTURE_2D, colorTextureID);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, 1920, 1080, 0,GL_RGBA, GL_INT, (java.nio.ByteBuffer) null);
        glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT,GL_COLOR_ATTACHMENT0_EXT,GL_TEXTURE_2D, colorTextureID, 0);
        
        
        
        
        // initialize depth renderbuffer
        glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, depthRenderBufferID);
        glRenderbufferStorageEXT(GL_RENDERBUFFER_EXT, GL_DEPTH_COMPONENT24, 1920, 1080);
        glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT,GL_DEPTH_ATTACHMENT_EXT,GL_RENDERBUFFER_EXT, depthRenderBufferID);
        
        glBindTexture(GL_TEXTURE_2D, depthTextureID);
        glTexImage2D(GL_TEXTURE_2D, 0,GL_DEPTH_COMPONENT24, 1920, 1080, 0,GL_DEPTH_COMPONENT, GL_FLOAT, 0);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST); 
    	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
    	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        
        glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT,GL_DEPTH_ATTACHMENT_EXT,GL_TEXTURE_2D, depthTextureID, 0);
        
 
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
	}
	
	public void bind() {
		
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, framebufferID);
        glClearColor (0.0f, 0.0f, 0.0f, 1f);
        glClear (GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public void unBind() {
		//glEnable(GL_TEXTURE_2D);
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
        
        glClearColor (0.0f, 0.0f, 0.0f, 1f);
        glClear (GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	
	public void render(Shader shader, Camera camera) {
        shader.bind();
        shader.setUniform("cameraPosition", camera.getPosition());
        shader.setUniform("cameraTransform", camera.getTransformation());
        shader.setUniform("cameraProjection", camera.getProjection());
        //shader.setUniform("cameraRotation", new Vector3f(camera.getRotation().x, camera.getRotation().y, camera.getRotation().z));
        Quaternionf rotation = new Quaternionf(0, 0, 0);
        camera.getTransformation().getNormalizedRotation(rotation);
        
        
        shader.setUniform("cameraRotation", new Vector4f(-rotation.x, -rotation.y, rotation.z, rotation.w));
        System.out.println(rotation.y);
        glUniform1iv(glGetUniformLocation(shader.getProgram(), "sampler"), new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
		Model m = Model.CreateModel(true);
		m.render();
	}
	
	public void bindTexture(int sampler) {
		if(sampler>=0 && sampler<=31) {
			glActiveTexture(GL_TEXTURE0 + sampler);
			glBindTexture(GL_TEXTURE_2D, getColorTextureID());
			
		}
	}
	
	public void bindDepthTexture(int sampler) {
		if(sampler>=0 && sampler<=31) {
			glActiveTexture(GL_TEXTURE0 + sampler);
			glBindTexture(GL_TEXTURE_2D, depthTextureID);
			
		}
	}
	
	public int getColorTextureID() {
		return colorTextureID;
	}

	public void setColorTextureID(int colorTextureID) {
		this.colorTextureID = colorTextureID;
	}
	
	

}
