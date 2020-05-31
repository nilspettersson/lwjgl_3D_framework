package niles.lwjgl.util;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

public class Shader {
	private int program;
	private int vs;
	private int fs;
	
	private static FloatBuffer matrix=BufferUtils.createFloatBuffer(16);  //this is used to store the matrix.
	
	public Shader(String filename) {
		program=glCreateProgram();
		vs=glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vs, readFile(filename+".vs"));
		glCompileShader(vs);
		
		if(glGetShaderi(vs, GL_COMPILE_STATUS)!=1) {
			System.err.println(glGetShaderInfoLog(vs));
			System.exit(1);
		}
		
		
		fs=glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fs, readFile(filename+".fs"));
		glCompileShader(fs);
		if(glGetShaderi(fs, GL_COMPILE_STATUS)!=1) {
			System.err.println(glGetShaderInfoLog(fs));
			System.exit(1);
		}
		
		
		glAttachShader(program, vs);
		glAttachShader(program, fs);
		
		glBindAttribLocation(program, 0, "vertices");
		glBindAttribLocation(program, 1, "textures");
		
		
		glLinkProgram(program);
		if(glGetProgrami(program, GL_LINK_STATUS)!=1) {
			System.err.println(glGetProgramInfoLog(program));
			System.exit(1);
		}
		
		glValidateProgram(program);
		if(glGetProgrami(program, GL_VALIDATE_STATUS)!=1) {
			System.err.println(glGetProgramInfoLog(program));
			System.exit(1);
		}
		
		
		
	}
	
	
	public void setUniform(String name,int value) {
		int location=getLocation(name);
		if(location!=-1) {
			glUniform1i(location, value);
		}
	}
	public void setUniform(String name,float value) {
		int location=getLocation(name);
		if(location!=-1) {
			glUniform1f(location, value);
		}
	}
	
	public void setUniform(String name,Vector4f vec) {
		int location=getLocation(name);
		if(location!=-1) {
			glUniform4f(location, vec.x, vec.y,vec.z,vec.w );
		}
	}
	
	
	
	
	
	
	public void setUniform(String name,Matrix4f value) {
		int location=getLocation(name);
		/*FloatBuffer buffer=BufferUtils.createFloatBuffer(16);
		value.get(buffer);*/
		
		value.get(matrix);
		
		if(location!=-1) {
			glUniformMatrix4fv(location, false, matrix);
		}
		
	}
	
	public void setUniform(String name,ArrayList<Matrix4f> value) {
		int location=getLocation(name);
		/*FloatBuffer buffer=BufferUtils.createFloatBuffer(16);
		value.get(buffer);*/
		/*for(int i=0;i<value.size();i++) {
			value.get(i).get(matrix);
		}*/
		
		
		FloatBuffer buffer=BufferUtils.createFloatBuffer(value.size()*16);
		for(int i=0;i<value.size();i++) {
			buffer.put(value.get(i).m00);
			buffer.put(value.get(i).m01);
			buffer.put(value.get(i).m02);
			buffer.put(value.get(i).m03);
			
			buffer.put(value.get(i).m10);
			buffer.put(value.get(i).m11);
			buffer.put(value.get(i).m12);
			buffer.put(value.get(i).m13);
			
			buffer.put(value.get(i).m20);
			buffer.put(value.get(i).m21);
			buffer.put(value.get(i).m22);
			buffer.put(value.get(i).m23);
			
			buffer.put(value.get(i).m30);
			buffer.put(value.get(i).m31);
			buffer.put(value.get(i).m32);
			buffer.put(value.get(i).m33);
		}
		buffer.flip();
		
		
		if(location!=-1) {
			glUniformMatrix4fv(location, false, buffer);
			
		}
		
	}
	
	
	
	public void setUniform(String name,Vector4f[] vec) {
		int location=getLocation(name);
		FloatBuffer buffer=BufferUtils.createFloatBuffer(vec.length*4);
		for(int i=0;i<vec.length;i++) {
			buffer.put(vec[i].x);
			buffer.put(vec[i].y);
			buffer.put(vec[i].z);
			buffer.put(vec[i].w);
		}
		buffer.flip();
		if(location!=-1) {
			glUniform4fv(location, buffer);
		}
	}
	
	public void setUniform(String name,Vector2f[] vec) {
		int location=getLocation(name);
		FloatBuffer buffer=BufferUtils.createFloatBuffer(vec.length*2);
		for(int i=0;i<vec.length;i++) {
			buffer.put(vec[i].x);
			buffer.put(vec[i].y);
		}
		buffer.flip();
		if(location!=-1) {
			glUniform2fv(location, buffer);
		}
	}
	
	public void setUniform(String name,Vector3f vec) {
		int location=getLocation(name);
		if(location!=-1) {
			glUniform3f(location, vec.x, vec.y,vec.z);
		}
	}
	
	public void setUniform(String name,Vector2f vec) {
		int location=getLocation(name);
		if(location!=-1) {
			glUniform2f(location, vec.x, vec.y);
		}
	}
	
	
	private HashMap<String, Integer>locations=new HashMap<String, Integer>();
	
	public int getLocation(String name) {
		if(!locations.containsKey(name)) {
			locations.put(name, glGetUniformLocation(program, name));
		}
		return locations.get(name);
	}
	
	
	
	
	public void bind() {
		glUseProgram(program);
	}
	
	
	
	private String readFile(String filename) {
		StringBuilder string=new StringBuilder();
		BufferedReader br;
		
		try {
			br=new BufferedReader(new FileReader(new File("./shaders/"+filename)));
			String line;
			while((line=br.readLine())!=null) {
				string.append(line);
				
				string.append("\n");
			}
			br.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return string.toString();
		
	}


	public int getProgram() {
		return program;
	}


	public void setProgram(int program) {
		this.program = program;
	}
	

}
