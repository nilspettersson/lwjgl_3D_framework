package niles.lwjgl.line;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import niles.lwjgl.entity.Transform;
import niles.lwjgl.npsl.LineShader;
import niles.lwjgl.npsl.Shader;

public class LineEntity {
	
	private static Shader shader = new LineShader("lineShader.glsl");;
	private LineVao vao;
	private FloatBuffer vertices;
	private Transform transform;
	
	private int index;
	private int size;
	
	public LineEntity(int lines) {
		transform = new Transform();
		
		vao = new LineVao(lines);
		
		ByteBuffer bb = ByteBuffer.allocateDirect(lines * 6 * 4);
	    bb.order(ByteOrder.nativeOrder());
	    vertices = bb.asFloatBuffer();
	    
	    this.index = 0;
		this.size = 0;
	}
	
	public void addLine(float x1, float y1, float z1, float x2, float y2, float z2) {
		float[] line = new float[] {x1, y1, z1, x2, y2, z2};
		for(int i = 0; i < line.length; i++) {
			vertices.put(index + i, line[i]);
		}
		index += 3 * 2;
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

	public Shader getShader() {
		return shader;
	}
	
	public Transform getTransform() {
		return transform;
	}

	public void setTransform(Transform transform) {
		this.transform = transform;
	}

}
