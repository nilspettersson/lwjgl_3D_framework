package niles.lwjgl.entity;

import static org.lwjgl.opengl.GL15.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import niles.lwjgl.util.Texture;
import niles.lwjgl.util.Vao;

public class Geometry {
	
	private int index;
	
	private ArrayList<Texture>textures;
	
	private Vao vao;
	
	private FloatBuffer vertices;
	private FloatBuffer indices;
	private int indexSize;
	
	private int size;
	
	public Geometry(int size) {
		ByteBuffer bb = ByteBuffer.allocateDirect(size * Vertex.size * 4);
	    bb.order(ByteOrder.nativeOrder());
	    vertices = bb.asFloatBuffer();
		
		
		vao = new Vao(size);
		textures = new ArrayList<Texture>();
		
		index = 0;
		
		size = 0;
		indexSize = 0;
	}
	
	public void addVertice(Vertex vertex) {
		float[] vert = vertex.toArray();
		for(int i = 0; i < vert.length; i++) {
			vertices.put(index + i, vert[i]);
		}
		index += Vertex.size;
		size++;
	}
	
	public void addIndex(int index) {
		indices.put(index);
	}
	
	
	public void addTexture(Texture texture) {
		textures.add(texture);
	}
	public void bindTextures() {
		for(int i = 0; i < textures.size(); i++) {
			textures.get(i).bind(i);
		}
	}
	
	
	
	public int size() {
		return size;
	}

	
	public void updateVertices() {
		glBindBuffer(GL_ARRAY_BUFFER, vao.getV_id());
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_DYNAMIC_DRAW);
		
	}
	
	public void updateIndices() {
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vao.getI_id());
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_DYNAMIC_DRAW);
	}
	
	
	/*public void updateAllValues() {
		glBindBuffer(GL_ARRAY_BUFFER, vao.getV_id());
		float[] subArray = new float[index];
		System.arraycopy(vertices, 0, subArray, 0, subArray.length);
		
		glBufferSubData(GL_ARRAY_BUFFER, 0, subArray);
		
	}*/

	public Vao getVao() {
		return vao;
	}

	public void setVao(Vao vao) {
		this.vao = vao;
	}
	
	
	
	

}
