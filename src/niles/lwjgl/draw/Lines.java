package niles.lwjgl.draw;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import niles.lwjgl.npsl.LineShader;
import niles.lwjgl.npsl.Shader;

public class Lines {
	
	private Shader shader;
	private LineVao vao;
	private FloatBuffer vertices;
	
	private int index;
	private int size;
	
	public Lines(int lines) {
		shader = new LineShader("lineShader.glsl");
		
		vao = new LineVao(lines);
		
		ByteBuffer bb = ByteBuffer.allocateDirect(lines * 6 * 4);
	    bb.order(ByteOrder.nativeOrder());
	    vertices = bb.asFloatBuffer();
	    
	    this.index = 0;
		this.size = 0;
	}
	
	public void addVertice(float x, float y, float z) {
		float[] vert = new float[] {x, y, z};
		for(int i = 0; i < vert.length; i++) {
			vertices.put(index + i, vert[i]);
		}
		index += 3;
		size++;
	}
	
	public void updateVertices() {
		glBindBuffer(GL_ARRAY_BUFFER, vao.getV_id());
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_DYNAMIC_DRAW);
		
	}

	public LineVao getVao() {
		return vao;
	}

	public void setVao(LineVao vao) {
		this.vao = vao;
	}

	public FloatBuffer getVertices() {
		return vertices;
	}

	public void setVertices(FloatBuffer vertices) {
		this.vertices = vertices;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Shader getShader() {
		return shader;
	}

	public void setShader(Shader shader) {
		this.shader = shader;
	}
	
	
	

}
