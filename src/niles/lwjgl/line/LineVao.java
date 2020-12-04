package niles.lwjgl.line;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;

import niles.lwjgl.entity.Vertex;
public class LineVao {
	
	private int draw_count;
	private int v_id;
	private int i_id;
	
	public LineVao(int lines) {
		
		draw_count = lines;
		
		v_id = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, v_id);
		glBufferData(GL_ARRAY_BUFFER, 0, GL_DYNAMIC_DRAW);
		
		
		i_id = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, i_id);
		
		
		int index[] = new int[lines * 2];
		for(int i = 0; i < index.length; i++) {
			index[i] = i;
		}
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, index, GL_DYNAMIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		init();
	}
	
	
	public void render() {
		
		glBindBuffer(GL_ARRAY_BUFFER, v_id);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * 4, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, i_id);
		
		glLineWidth(1);
		glEnable(GL_LINE_SMOOTH);
		glDrawElements(GL_LINES, draw_count * 2, GL_UNSIGNED_INT, 0);
		
	}
	
	public static void init() {
		glEnableVertexAttribArray(0);
	}
	
	public void deleteBuffers() {
		glBindBuffer(GL_ARRAY_BUFFER, v_id);
		glDeleteBuffers(v_id);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, i_id);
		glDeleteBuffers(i_id);
	}
	
	private FloatBuffer createBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
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
	
	
}