package niles.lwjgl.util;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class Texture {
	
	private int id;
	private int width;
	private int height;
	
	private int spriteWidth;
	private int spriteHeight;
	
	public Texture(String filename) {
		BufferedImage image;
		try {
			image=ImageIO.read(new File(filename));
			width=image.getWidth();
			height=image.getHeight();
			
			int[] pixelsRaw=new int[width*height*4];
			pixelsRaw=image.getRGB(0, 0, width, height, null, 0, width);
			ByteBuffer pixels=BufferUtils.createByteBuffer(width*height*4);
			for(int i=0;i<height;i++) {
				for(int ii=0;ii<width;ii++) {
					int pixel=pixelsRaw[i*width+ii];
					pixels.put((byte)(pixel >> 16 & 0xFF));
					pixels.put((byte)(pixel >> 8 & 0xFF));
					pixels.put((byte)(pixel >> 0 & 0xFF));
					pixels.put((byte)(pixel >> 24 & 0xFF));
				}
			}
			
			pixels.flip();
			
			id=glGenTextures();
			glBindTexture(GL_TEXTURE_2D, id);
			
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
			
			System.gc();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		
		
		
	}
	
	
	
	
	public Texture(String text,int w,int h,Font font) {
		BufferedImage image=new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g=image.createGraphics();
		g.setFont(font);
		g.drawString(text,0, h);
		width=image.getWidth();
		height=image.getHeight();
			
			int[] pixelsRaw=new int[width*height*4];
			pixelsRaw=image.getRGB(0, 0, width, height, null, 0, width);
			ByteBuffer pixels=BufferUtils.createByteBuffer(width*height*4);
			for(int i=0;i<height;i++) {
				for(int ii=0;ii<width;ii++) {
					int pixel=pixelsRaw[i*width+ii];
					pixels.put((byte)(pixel >> 16 & 0xFF));
					pixels.put((byte)(pixel >> 8 & 0xFF));
					pixels.put((byte)(pixel >> 0 & 0xFF));
					pixels.put((byte)(pixel >> 24 & 0xFF));
				}
			}
			
			pixels.flip();
			
			id=glGenTextures();
			
			
			
			glBindTexture(GL_TEXTURE_2D, id);
			
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
			
	}
	
	
	
	
	
	public void bind(int sampler) {
		if(sampler>=0 && sampler<=31) {
			glActiveTexture(GL_TEXTURE0 + sampler);
			glBindTexture(GL_TEXTURE_2D, id);
			
		}
	}
	
	
	
	public void bind2(int sampler,Shader shader) {
		if(sampler>=0 && sampler<=31) {
			glUniform1i(glGetUniformLocation(shader.getProgram(), "sampler1"), 0);
			glActiveTexture(GL_TEXTURE0);
			glBindTexture(GL_TEXTURE_2D, id);
			System.out.println("sampler"+sampler+"   "+GL_TEXTURE0);
			
		}
	}
	public void bind3(int sampler,Shader shader) {
		if(sampler>=0 && sampler<=31) {
			glUniform1i(glGetUniformLocation(shader.getProgram(), "sampler2"), 1);
			glActiveTexture(GL_TEXTURE1);
			glBindTexture(GL_TEXTURE_2D, id);
			System.out.println("sampler"+sampler+"   "+GL_TEXTURE1);
			
		}
	}
	
	public void delete() {
		glDeleteTextures(id);
	}
	
	public static void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	
	public int getSpriteWidth() {
		return spriteWidth;
	}

	public void setSpriteWidth(int spriteWidth) {
		this.spriteWidth = spriteWidth;
	}

	public int getSpriteHeight() {
		return spriteHeight;
	}

	public void setSpriteHeight(int spriteHeight) {
		this.spriteHeight = spriteHeight;
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

}
