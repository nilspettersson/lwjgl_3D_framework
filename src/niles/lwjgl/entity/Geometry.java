package niles.lwjgl.entity;

import static org.lwjgl.opengl.GL15.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import niles.lwjgl.util.Texture;
import niles.lwjgl.util.Vao;

public class Geometry {
	
	private int index;
	
	private ArrayList<Texture>textures;
	
	private Vao vao;
	
	private FloatBuffer vertices;
	private IntBuffer indices;
	private int indexSize;
	
	private int size;
	
	public Geometry(int size) {
		ByteBuffer bb = ByteBuffer.allocateDirect(size * Vertex.size * 4);
	    bb.order(ByteOrder.nativeOrder());
	    vertices = bb.asFloatBuffer();
	    
	    ByteBuffer bb2 = ByteBuffer.allocateDirect(size * Vertex.size * 4);
	    bb2.order(ByteOrder.nativeOrder());
	    indices = bb2.asIntBuffer();
		
		
		vao = new Vao(size);
		textures = new ArrayList<Texture>();
		
		index = 0;
		
		this.size = 0;
		indexSize = 0;
	}
	
	
	
	public void createCube(float x, float y, float z) {
		addVertice(new Vertex(new Vector3f(-1 + x, 1 + y, -1 + z), new Vector4f(1), 0, new Vector2f(0, 0)));
		addVertice(new Vertex(new Vector3f(1 + x, 1 + y, -1 + z), new Vector4f(1), 0, new Vector2f(1, 0)));
		addVertice(new Vertex(new Vector3f(1 + x, -1 + y, -1 + z), new Vector4f(1), 0, new Vector2f(1, 1)));
		addVertice(new Vertex(new Vector3f(-1 + x, -1 + y, -1 + z), new Vector4f(1), 0, new Vector2f(0, 1)));
		
		addVertice(new Vertex(new Vector3f(-1 + x, 1 + y, 1 + z), new Vector4f(1), 0, new Vector2f(0, 0)));
		addVertice(new Vertex(new Vector3f(1 + x, 1 + y, 1 + z), new Vector4f(1), 0, new Vector2f(1, 0)));
		addVertice(new Vertex(new Vector3f(1 + x, -1 + y, 1 + z), new Vector4f(1), 0, new Vector2f(1, 1)));
		addVertice(new Vertex(new Vector3f(-1 + x, -1 + y, 1 + z), new Vector4f(1), 0, new Vector2f(0, 1)));
		
		addVertice(new Vertex(new Vector3f(-1 + x, 1 + y, -1 + z), new Vector4f(1), 0, new Vector2f(0, 0)));
		addVertice(new Vertex(new Vector3f(-1 + x, 1 + y, 1 + z), new Vector4f(1), 0, new Vector2f(1, 0)));
		addVertice(new Vertex(new Vector3f(-1 + x, -1 + y, 1 + z), new Vector4f(1), 0, new Vector2f(1, 1)));
		addVertice(new Vertex(new Vector3f(-1 + x, -1 + y, -1 + z), new Vector4f(1), 0, new Vector2f(0, 1)));
		
		addVertice(new Vertex(new Vector3f(1 + x, 1 + y, -1 + z), new Vector4f(1), 0, new Vector2f(0, 0)));
		addVertice(new Vertex(new Vector3f(1 + x, 1 + y, 1 + z), new Vector4f(1), 0, new Vector2f(1, 0)));
		addVertice(new Vertex(new Vector3f(1 + x, -1 + y, 1 + z), new Vector4f(1), 0, new Vector2f(1, 1)));
		addVertice(new Vertex(new Vector3f(1 + x, -1 + y, -1 + z), new Vector4f(1), 0, new Vector2f(0, 1)));
		
		addVertice(new Vertex(new Vector3f(1 + x, -1 + y, -1 + z), new Vector4f(1), 0, new Vector2f(0, 0)));
		addVertice(new Vertex(new Vector3f(1 + x, -1 + y, 1 + z), new Vector4f(1), 0, new Vector2f(1, 0)));
		addVertice(new Vertex(new Vector3f(-1 + x, -1 + y, 1 + z), new Vector4f(1), 0, new Vector2f(1, 1)));
		addVertice(new Vertex(new Vector3f(-1 + x, -1 + y, -1 + z), new Vector4f(1), 0, new Vector2f(0, 1)));
		
		addVertice(new Vertex(new Vector3f(1 + x, 1 + y, -1 + z), new Vector4f(1), 0, new Vector2f(0, 0)));
		addVertice(new Vertex(new Vector3f(1 + x, 1 + y, 1 + z), new Vector4f(1), 0, new Vector2f(1, 0)));
		addVertice(new Vertex(new Vector3f(-1 + x, 1 + y, 1 + z), new Vector4f(1), 0, new Vector2f(1, 1)));
		addVertice(new Vertex(new Vector3f(-1 + x, 1 + y, -1 + z), new Vector4f(1), 0, new Vector2f(0, 1)));
		
		int location = size - 24;
		
		addIndex(location + 0);
		addIndex(location + 1);
		addIndex(location + 2);
		addIndex(location + 2);
		addIndex(location + 3);
		addIndex(location + 0);
		
		addIndex(location + 0 + 4);
		addIndex(location + 1 + 4);
		addIndex(location + 2 + 4);
		addIndex(location + 2 + 4);
		addIndex(location + 3 + 4);
		addIndex(location + 0 + 4);
		
		addIndex(location + 0 + 4*2);
		addIndex(location + 1 + 4*2);
		addIndex(location + 2 + 4*2);
		addIndex(location + 2 + 4*2);
		addIndex(location + 3 + 4*2);
		addIndex(location + 0 + 4*2);
		
		addIndex(location + 0 + 4*3);
		addIndex(location + 1 + 4*3);
		addIndex(location + 2 + 4*3);
		addIndex(location + 2 + 4*3);
		addIndex(location + 3 + 4*3);
		addIndex(location + 0 + 4*3);
		
		addIndex(location + 0 + 4*4);
		addIndex(location + 1 + 4*4);
		addIndex(location + 2 + 4*4);
		addIndex(location + 2 + 4*4);
		addIndex(location + 3 + 4*4);
		addIndex(location + 0 + 4*4);
		
		addIndex(location + 0 + 4*5);
		addIndex(location + 1 + 4*5);
		addIndex(location + 2 + 4*5);
		addIndex(location + 2 + 4*5);
		addIndex(location + 3 + 4*5);
		addIndex(location + 0 + 4*5);
	}
	
	public void createFace(float x, float y, float z) {
		addVertice(new Vertex(new Vector3f(-1 + x, 1 + y, -1 + z), new Vector4f(1), 0, new Vector2f(0, 0)));
		addVertice(new Vertex(new Vector3f(1 + x, 1 + y, -1 + z), new Vector4f(1), 0, new Vector2f(1, 0)));
		addVertice(new Vertex(new Vector3f(1 + x, -1 + y, -1 + z), new Vector4f(1), 0, new Vector2f(1, 1)));
		addVertice(new Vertex(new Vector3f(-1 + x, -1 + y, -1 + z), new Vector4f(1), 0, new Vector2f(0, 1)));
		
		int location = size - 4;
		addIndex(location + 0);
		addIndex(location + 1);
		addIndex(location + 2);
		addIndex(location + 2);
		addIndex(location + 3);
		addIndex(location + 0);
	}
	
	public void extrudeFace(float length) {
		
		int index = size - 4;
		
		addVertice(new Vertex(new Vector3f(getX(index + 0), getY(index + 0), getZ(index + 0) + length), new Vector4f(1), 0, new Vector2f(0, 0)));
		addVertice(new Vertex(new Vector3f(getX(index + 1), getY(index + 1), getZ(index + 1) + length), new Vector4f(1), 0, new Vector2f(1, 0)));
		addVertice(new Vertex(new Vector3f(getX(index + 2), getY(index + 2), getZ(index + 2) + length), new Vector4f(1), 0, new Vector2f(1, 1)));
		addVertice(new Vertex(new Vector3f(getX(index + 3), getY(index + 3), getZ(index + 3) + length), new Vector4f(1), 0, new Vector2f(0, 1)));
		
		int location = size - 8;
		addIndex(location + 0 + 4);
		addIndex(location + 1 + 4);
		addIndex(location + 2 + 4);
		addIndex(location + 2 + 4);
		addIndex(location + 3 + 4);
		addIndex(location + 0 + 4);
		
	}
	
	public float getX(int index) {
		return vertices.get(index * Vertex.size);
	}
	public float getY(int index) {
		return vertices.get(index * Vertex.size + 1);
	}
	public float getZ(int index) {
		return vertices.get(index * Vertex.size + 2);
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
		indices.put(indexSize, index);
		indexSize++;
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
		/*for(int i = 0; i < indices.capacity(); i++) {
			System.out.println(indices.get(i));
		}*/
		
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
