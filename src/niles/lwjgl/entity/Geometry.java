package niles.lwjgl.entity;

import static org.lwjgl.opengl.GL15.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.joml.Quaternionf;
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
	
	public static Geometry loadModel(String fileName) {
		Geometry geometry = new Geometry(3000000);
		
		
		FileReader fr = null;
		
		try {
			fr = new FileReader(fileName + ".obj");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
		BufferedReader reader = new BufferedReader(fr);
		
		ArrayList<Vector3f> positions = new ArrayList<Vector3f>();
		ArrayList<Vector2f> textures = new ArrayList<Vector2f>();
		ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
		
		try {
			
			while(true) {
				String line = reader.readLine();
				String[] values = line.split(" ");
				if(line.startsWith("v ")) {
					positions.add(new Vector3f(Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3])));
				}
				else if(line.startsWith("vt ")) {
					textures.add(new Vector2f(Float.parseFloat(values[1]), Float.parseFloat(values[2])));
				}
				else if(line.startsWith("vn ")) {
					normals.add(new Vector3f(Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3])));
				}
				
				else if(line.startsWith("f ")) {
					for(int i = 1;i <= 3; i++) {
						String[] vert = values[i].split("/");
						int positionIndex = Integer.parseInt(vert[0]);
						int textureIndex = Integer.parseInt(vert[1]);
						int normalIndex = Integer.parseInt(vert[2]);
						
						geometry.addVertice(new Vertex(positions.get(positionIndex - 1), new Vector4f(1), 0, textures.get(textureIndex - 1), normals.get(normalIndex - 1)));
						geometry.addIndex(geometry.getSize() - 1);
						
					}
					
				}
			}
			
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		finally {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		geometry.updateVertices();
		geometry.updateIndices();
		
		return geometry;
	}
	
	
	
	public void createCube(float x, float y, float z, Vector4f color) {
		
		Vector3f normal1 = new Vector3f(0, 0, -1);
		addVertice(new Vertex(new Vector3f(-1 + x, 1 + y, -1 + z), color, 0, new Vector2f(0, 0), normal1));
		addVertice(new Vertex(new Vector3f(1 + x, 1 + y, -1 + z), color, 0, new Vector2f(1, 0), normal1));
		addVertice(new Vertex(new Vector3f(1 + x, -1 + y, -1 + z), color, 0, new Vector2f(1, 1), normal1));
		addVertice(new Vertex(new Vector3f(-1 + x, -1 + y, -1 + z), color, 0, new Vector2f(0, 1), normal1));
		
		Vector3f normal2 = new Vector3f(0, 0, 1);
		addVertice(new Vertex(new Vector3f(-1 + x, 1 + y, 1 + z), color, 0, new Vector2f(0, 0), normal2));
		addVertice(new Vertex(new Vector3f(1 + x, 1 + y, 1 + z), color, 0, new Vector2f(1, 0), normal2));
		addVertice(new Vertex(new Vector3f(1 + x, -1 + y, 1 + z), color, 0, new Vector2f(1, 1), normal2));
		addVertice(new Vertex(new Vector3f(-1 + x, -1 + y, 1 + z), color, 0, new Vector2f(0, 1), normal2));
		
		Vector3f normal3 = new Vector3f(-1, 0, 0);
		addVertice(new Vertex(new Vector3f(-1 + x, 1 + y, -1 + z), color, 0, new Vector2f(0, 0), normal3));
		addVertice(new Vertex(new Vector3f(-1 + x, 1 + y, 1 + z), color, 0, new Vector2f(1, 0), normal3));
		addVertice(new Vertex(new Vector3f(-1 + x, -1 + y, 1 + z), color, 0, new Vector2f(1, 1), normal3));
		addVertice(new Vertex(new Vector3f(-1 + x, -1 + y, -1 + z), color, 0, new Vector2f(0, 1), normal3));
		
		Vector3f normal4 = new Vector3f(1, 0, 0);
		addVertice(new Vertex(new Vector3f(1 + x, 1 + y, -1 + z), color, 0, new Vector2f(0, 0), normal4));
		addVertice(new Vertex(new Vector3f(1 + x, 1 + y, 1 + z), color, 0, new Vector2f(1, 0), normal4));
		addVertice(new Vertex(new Vector3f(1 + x, -1 + y, 1 + z), color, 0, new Vector2f(1, 1), normal4));
		addVertice(new Vertex(new Vector3f(1 + x, -1 + y, -1 + z), color, 0, new Vector2f(0, 1), normal4));
		
		Vector3f normal5 = new Vector3f(0, -1, 0);
		addVertice(new Vertex(new Vector3f(1 + x, -1 + y, -1 + z), color, 0, new Vector2f(0, 0), normal5));
		addVertice(new Vertex(new Vector3f(1 + x, -1 + y, 1 + z), color, 0, new Vector2f(1, 0), normal5));
		addVertice(new Vertex(new Vector3f(-1 + x, -1 + y, 1 + z), color, 0, new Vector2f(1, 1), normal5));
		addVertice(new Vertex(new Vector3f(-1 + x, -1 + y, -1 + z), color, 0, new Vector2f(0, 1), normal5));
		
		Vector3f normal6 = new Vector3f(0, 1, 0);
		addVertice(new Vertex(new Vector3f(1 + x, 1 + y, -1 + z), color, 0, new Vector2f(0, 0), normal6));
		addVertice(new Vertex(new Vector3f(1 + x, 1 + y, 1 + z), color, 0, new Vector2f(1, 0), normal6));
		addVertice(new Vertex(new Vector3f(-1 + x, 1 + y, 1 + z), color, 0, new Vector2f(1, 1), normal6));
		addVertice(new Vertex(new Vector3f(-1 + x, 1 + y, -1 + z), color, 0, new Vector2f(0, 1), normal6));
		
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
	
	public void createFace(float x, float y, float z ) {
		Vector3f normal = new  Vector3f(0, 0, 1);
		
		Vector3f pos1 = new Vector3f(-1 + x, 1 + y, -1 + z);
		Vector3f pos2 = new Vector3f(1 + x, 1 + y, -1 + z);
		Vector3f pos3 = new Vector3f(1 + x, -1 + y, -1 + z);
		Vector3f pos4 = new Vector3f(-1 + x, -1 + y, -1 + z);
		
		addVertice(new Vertex(pos1, new Vector4f(1), 0, new Vector2f(0, 0), normal));
		addVertice(new Vertex(pos2, new Vector4f(1), 0, new Vector2f(1, 0), normal));
		addVertice(new Vertex(pos3, new Vector4f(1), 0, new Vector2f(1, 1), normal));
		addVertice(new Vertex(pos4, new Vector4f(1), 0, new Vector2f(0, 1), normal));
		
		int location = size - 4;
		addIndex(location + 0);
		addIndex(location + 1);
		addIndex(location + 2);
		addIndex(location + 2);
		addIndex(location + 3);
		addIndex(location + 0);
	}
	
	//creates a face with sprite from a texture atlas.
	//Texture need to set spriteWidth and spriteHeight.
	public void createFaceFront(float x, float y, float z, Texture textureAtlas, int spriteX, int spriteY) {
		Vector3f normal = new  Vector3f(0, 0, 1);
		
		Vector3f pos1 = new Vector3f(-1 + x, 1 + y, 1 + z);
		Vector3f pos2 = new Vector3f(1 + x, 1 + y, 1 + z);
		Vector3f pos3 = new Vector3f(1 + x, -1 + y, 1 + z);
		Vector3f pos4 = new Vector3f(-1 + x, -1 + y, 1 + z);
		
		float xStart = ((float)spriteX * (float)textureAtlas.getSpriteWidth()) / (float)textureAtlas.getWidth();
		float yStart = ((float)spriteY * (float)textureAtlas.getSpriteHeight()) / (float)textureAtlas.getHeight();
		float xEnd = xStart + (float)textureAtlas.getSpriteWidth() / (float)textureAtlas.getWidth();
		float yEnd = yStart + (float)textureAtlas.getSpriteHeight() / (float)textureAtlas.getHeight();
		
		addVertice(new Vertex(pos1, new Vector4f(1), 0, new Vector2f(xStart, yStart), normal));
		addVertice(new Vertex(pos2, new Vector4f(1), 0, new Vector2f(xEnd, yStart), normal));
		addVertice(new Vertex(pos3, new Vector4f(1), 0, new Vector2f(xEnd, yEnd), normal));
		addVertice(new Vertex(pos4, new Vector4f(1), 0, new Vector2f(xStart, yEnd), normal));
		
		int location = size - 4;
		addIndex(location + 0);
		addIndex(location + 1);
		addIndex(location + 2);
		addIndex(location + 2);
		addIndex(location + 3);
		addIndex(location + 0);
	}
	
	public void createFaceBack(float x, float y, float z, Texture textureAtlas, int spriteX, int spriteY) {
		Vector3f normal = new  Vector3f(0, 0, -1);
		
		Vector3f pos1 = new Vector3f(-1 + x, 1 + y, -1 + z);
		Vector3f pos2 = new Vector3f(1 + x, 1 + y, -1 + z);
		Vector3f pos3 = new Vector3f(1 + x, -1 + y, -1 + z);
		Vector3f pos4 = new Vector3f(-1 + x, -1 + y, -1 + z);
		
		float xStart = ((float)spriteX * (float)textureAtlas.getSpriteWidth()) / (float)textureAtlas.getWidth();
		float yStart = ((float)spriteY * (float)textureAtlas.getSpriteHeight()) / (float)textureAtlas.getHeight();
		float xEnd = xStart + (float)textureAtlas.getSpriteWidth() / (float)textureAtlas.getWidth();
		float yEnd = yStart + (float)textureAtlas.getSpriteHeight() / (float)textureAtlas.getHeight();
		
		addVertice(new Vertex(pos1, new Vector4f(1), 0, new Vector2f(xStart, yStart), normal));
		addVertice(new Vertex(pos2, new Vector4f(1), 0, new Vector2f(xEnd, yStart), normal));
		addVertice(new Vertex(pos3, new Vector4f(1), 0, new Vector2f(xEnd, yEnd), normal));
		addVertice(new Vertex(pos4, new Vector4f(1), 0, new Vector2f(xStart, yEnd), normal));
		
		int location = size - 4;
		addIndex(location + 0);
		addIndex(location + 1);
		addIndex(location + 2);
		addIndex(location + 2);
		addIndex(location + 3);
		addIndex(location + 0);
	}
	
	public void createFaceLeft(float x, float y, float z, Texture textureAtlas, int spriteX, int spriteY) {
		Vector3f normal = new  Vector3f(-1, 0, 0);
		
		Vector3f pos1 = new Vector3f(-1 + x, 1 + y, -1 + z);
		Vector3f pos2 = new Vector3f(-1 + x, 1 + y, 1 + z);
		Vector3f pos3 = new Vector3f(-1 + x, -1 + y, 1 + z);
		Vector3f pos4 = new Vector3f(-1 + x, -1 + y, -1 + z);
		
		float xStart = ((float)spriteX * (float)textureAtlas.getSpriteWidth()) / (float)textureAtlas.getWidth();
		float yStart = ((float)spriteY * (float)textureAtlas.getSpriteHeight()) / (float)textureAtlas.getHeight();
		float xEnd = xStart + (float)textureAtlas.getSpriteWidth() / (float)textureAtlas.getWidth();
		float yEnd = yStart + (float)textureAtlas.getSpriteHeight() / (float)textureAtlas.getHeight();
		
		addVertice(new Vertex(pos1, new Vector4f(1), 0, new Vector2f(xStart, yStart), normal));
		addVertice(new Vertex(pos2, new Vector4f(1), 0, new Vector2f(xEnd, yStart), normal));
		addVertice(new Vertex(pos3, new Vector4f(1), 0, new Vector2f(xEnd, yEnd), normal));
		addVertice(new Vertex(pos4, new Vector4f(1), 0, new Vector2f(xStart, yEnd), normal));
		
		int location = size - 4;
		addIndex(location + 0);
		addIndex(location + 1);
		addIndex(location + 2);
		addIndex(location + 2);
		addIndex(location + 3);
		addIndex(location + 0);
	}
	
	public void createFaceRight(float x, float y, float z, Texture textureAtlas, int spriteX, int spriteY) {
		Vector3f normal = new  Vector3f(1, 0, 0);
		
		Vector3f pos1 = new Vector3f(1 + x, 1 + y, -1 + z);
		Vector3f pos2 = new Vector3f(1 + x, 1 + y, 1 + z);
		Vector3f pos3 = new Vector3f(1 + x, -1 + y, 1 + z);
		Vector3f pos4 = new Vector3f(1 + x, -1 + y, -1 + z);
		
		float xStart = ((float)spriteX * (float)textureAtlas.getSpriteWidth()) / (float)textureAtlas.getWidth();
		float yStart = ((float)spriteY * (float)textureAtlas.getSpriteHeight()) / (float)textureAtlas.getHeight();
		float xEnd = xStart + (float)textureAtlas.getSpriteWidth() / (float)textureAtlas.getWidth();
		float yEnd = yStart + (float)textureAtlas.getSpriteHeight() / (float)textureAtlas.getHeight();
		
		addVertice(new Vertex(pos1, new Vector4f(1), 0, new Vector2f(xStart, yStart), normal));
		addVertice(new Vertex(pos2, new Vector4f(1), 0, new Vector2f(xEnd, yStart), normal));
		addVertice(new Vertex(pos3, new Vector4f(1), 0, new Vector2f(xEnd, yEnd), normal));
		addVertice(new Vertex(pos4, new Vector4f(1), 0, new Vector2f(xStart, yEnd), normal));
		
		int location = size - 4;
		addIndex(location + 0);
		addIndex(location + 1);
		addIndex(location + 2);
		addIndex(location + 2);
		addIndex(location + 3);
		addIndex(location + 0);
	}
	
	public void createFaceUp(float x, float y, float z, Texture textureAtlas, int spriteX, int spriteY) {
		Vector3f normal = new  Vector3f(0, 1, 0);
		
		Vector3f pos1 = new Vector3f(-1 + x, 1 + y, -1 + z);
		Vector3f pos2 = new Vector3f(1 + x, 1 + y, -1 + z);
		Vector3f pos3 = new Vector3f(1 + x, 1 + y, 1 + z);
		Vector3f pos4 = new Vector3f(-1 + x, 1 + y, 1 + z);
		
		float xStart = ((float)spriteX * (float)textureAtlas.getSpriteWidth()) / (float)textureAtlas.getWidth();
		float yStart = ((float)spriteY * (float)textureAtlas.getSpriteHeight()) / (float)textureAtlas.getHeight();
		float xEnd = xStart + (float)textureAtlas.getSpriteWidth() / (float)textureAtlas.getWidth();
		float yEnd = yStart + (float)textureAtlas.getSpriteHeight() / (float)textureAtlas.getHeight();
		
		addVertice(new Vertex(pos1, new Vector4f(1), 0, new Vector2f(xStart, yStart), normal));
		addVertice(new Vertex(pos2, new Vector4f(1), 0, new Vector2f(xEnd, yStart), normal));
		addVertice(new Vertex(pos3, new Vector4f(1), 0, new Vector2f(xEnd, yEnd), normal));
		addVertice(new Vertex(pos4, new Vector4f(1), 0, new Vector2f(xStart, yEnd), normal));
		
		int location = size - 4;
		addIndex(location + 0);
		addIndex(location + 1);
		addIndex(location + 2);
		addIndex(location + 2);
		addIndex(location + 3);
		addIndex(location + 0);
	}
	
	public void createFaceDown(float x, float y, float z, Texture textureAtlas, int spriteX, int spriteY) {
		Vector3f normal = new  Vector3f(0, -1, 0);
		
		Vector3f pos1 = new Vector3f(-1 + x, -1 + y, -1 + z);
		Vector3f pos2 = new Vector3f(1 + x, -1 + y, -1 + z);
		Vector3f pos3 = new Vector3f(1 + x, -1 + y, 1 + z);
		Vector3f pos4 = new Vector3f(-1 + x, -1 + y, 1 + z);
		
		float xStart = ((float)spriteX * (float)textureAtlas.getSpriteWidth()) / (float)textureAtlas.getWidth();
		float yStart = ((float)spriteY * (float)textureAtlas.getSpriteHeight()) / (float)textureAtlas.getHeight();
		float xEnd = xStart + (float)textureAtlas.getSpriteWidth() / (float)textureAtlas.getWidth();
		float yEnd = xStart + (float)textureAtlas.getSpriteHeight() / (float)textureAtlas.getHeight();
		
		addVertice(new Vertex(pos1, new Vector4f(1), 0, new Vector2f(xStart, yStart), normal));
		addVertice(new Vertex(pos2, new Vector4f(1), 0, new Vector2f(xEnd, yStart), normal));
		addVertice(new Vertex(pos3, new Vector4f(1), 0, new Vector2f(xEnd, yEnd), normal));
		addVertice(new Vertex(pos4, new Vector4f(1), 0, new Vector2f(xStart, yEnd), normal));
		
		int location = size - 4;
		addIndex(location + 0);
		addIndex(location + 1);
		addIndex(location + 2);
		addIndex(location + 2);
		addIndex(location + 3);
		addIndex(location + 0);
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
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vao.getI_id());
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_DYNAMIC_DRAW);
	}
	
	public void deleteBuffers() {
		vao.deleteBuffers();
	}

	public Vao getVao() {
		return vao;
	}

	public void setVao(Vao vao) {
		this.vao = vao;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	
	
	

}
