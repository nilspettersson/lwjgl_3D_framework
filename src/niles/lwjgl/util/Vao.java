package niles.lwjgl.util;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL40.*;


import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import niles.lwjgl.entity.Vertex;
public class Vao {
	
	private int draw_count;
	private int v_id;
	private int i_id;
	
	public Vao(int maxVertices) {
		float[] tex_coords=new float[] {
				0,0,
				1,0,
				1,1,
				0,1
		};
		
		
		draw_count = maxVertices;
		
		v_id=glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, v_id);
		glBufferData(GL_ARRAY_BUFFER, 4 * maxVertices, GL_DYNAMIC_DRAW);
		
		
		i_id=glGenBuffers();
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, i_id);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, 40 * maxVertices, GL_DYNAMIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		init();
	}
	
	
	public void render() {
		
		glBindBuffer(GL_ARRAY_BUFFER,v_id);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.size * 4, 0);
		
		glVertexAttribPointer(1, 4, GL_FLOAT, false, Vertex.size * 4, 12);
		
		glVertexAttribPointer(2, 1, GL_FLOAT, false, Vertex.size * 4, 28);
		
		glVertexAttribPointer(3, 2, GL_FLOAT, false, Vertex.size * 4, 32);
		
		glVertexAttribPointer(4, 3, GL_FLOAT, false, Vertex.size * 4, 40);
		
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, i_id);
		
		glDrawElements(GL_TRIANGLES, draw_count,GL_UNSIGNED_INT,0);
	}
	
	public static void init() {
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		glEnableVertexAttribArray(3);
		glEnableVertexAttribArray(4);
	}
	
	public void deleteBuffers() {
		glBindBuffer(GL_ARRAY_BUFFER, v_id);
		glDeleteBuffers(v_id);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, i_id);
		glDeleteBuffers(i_id);
	}
	
	private FloatBuffer createBuffer(float[] data) {
		FloatBuffer buffer=BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}


	public int getV_id() {
		return v_id;
	}


	public void setV_id(int v_id) {
		this.v_id = v_id;
	}


	public int getI_id() {
		return i_id;
	}


	public void setI_id(int i_id) {
		this.i_id = i_id;
	}
	
	
	
	
}

