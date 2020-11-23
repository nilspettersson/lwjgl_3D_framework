package niles.lwjgl.npsl;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1fv;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform2fv;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform3fv;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniform4fv;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

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

public abstract class Shader {
	private int program;
	private int vs;
	private int fs;
	
	protected String filename;
	
	private static FloatBuffer matrix=BufferUtils.createFloatBuffer(16);  //this is used to store the matrix.
	
	abstract String createVertexShader();
	
	abstract String createFragmentShader();
	
	public Shader(String filename) {
		this.filename = filename;
		
		program=glCreateProgram();
		vs=glCreateShader(GL_VERTEX_SHADER);
		
		
		
		glShaderSource(vs, createVertexShader());
		
		glCompileShader(vs);
		
		if(glGetShaderi(vs, GL_COMPILE_STATUS)!=1) {
			System.err.println(glGetShaderInfoLog(vs));
			System.exit(1);
		}
		
		
		
		
		fs=glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fs, createFragmentShader());
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
	
	protected String getIncludedFiles(String[] npsl) {
		String[] includes = npsl[0].split(",");
		String libText = "";
		for(int i = 0; i < includes.length; i++) {
			libText += readFile("/" + includes[i]);
		}
		return libText;
	}
	
	protected String[] readNpsl(String filename) {
		String text = readFile(filename);
		
		String includes = "";
		int start = text.indexOf("#include ") + 9;
		while(true) {
			int end = text.indexOf(";", start);
			String include = text.substring(start, end);
			includes += include + ",";
			
			start = text.indexOf("#include ", end);
			
			if(start == -1) {
				break;
			}
			start += 9;
		}
		
		
		//get the uniforms from file.
		start = text.indexOf("uniforms{") + 10;
		int end = -1;
		String uniforms = "";
		boolean done = false;
		int loopCount = 0;
		while(!done) {
			end = text.indexOf(",", start);
			if(end == -1 || text.indexOf("}", start) < end) {
				end = text.indexOf("}", start) - 1;
				done = true;
			}
			String uniform = "uniform " + text.substring(start, end) + ";";
			uniforms += uniform.trim() + "\r\n";
			start = end + 2;
			if(loopCount == 0 && done == true) {
				uniforms = "";
			}
		}
		
		
		//gets all functions created between uniforms and fragment.
		//these functions will be created above included functions.
		String functions = text.substring(end + 2, text.indexOf("fragment{"));
		
		
		//get the fragment code from file.
		start = text.indexOf("fragment{") + 9;
		int openIndex = text.indexOf("{", start);
		int closeIndex = text.indexOf("}", start);
		int openCount = 1;
		int closeCount = 0;
		while(true) {
			if(closeIndex < openIndex || openIndex == -1) {
				closeCount++;
				if(openCount - closeCount == 0) {
					break;
				}
				int newStart = closeIndex;
				openIndex = text.indexOf("{", newStart + 1);
				closeIndex = text.indexOf("}", newStart + 1);
			}
			else {
				openCount++;
				if(openCount - closeCount == 0) {
					break;
				}
				int newStart = openIndex;
				openIndex = text.indexOf("{", newStart + 1);
				closeIndex = text.indexOf("}", newStart + 1);
			}
		}
		
		
		end = closeIndex;
		String fragment = text.substring(start, end);
		fragment = fragment.replace("return ", "gl_FragColor = ");
		
		
		return new String[]{includes, uniforms, fragment, functions};
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
	
	public void setUniform(String name,Vector3f[] vec) {
		int location=getLocation(name);
		FloatBuffer buffer=BufferUtils.createFloatBuffer(vec.length*3);
		for(int i=0;i<vec.length;i++) {
			buffer.put(vec[i].x);
			buffer.put(vec[i].y);
			buffer.put(vec[i].z);
		}
		buffer.flip();
		if(location!=-1) {
			glUniform3fv(location, buffer);
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
	
	public void setUniform(String name,float[] values) {
		int location=getLocation(name);
		if(location!=-1) {
			glUniform1fv(location, values);
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
	
	
	
	protected String readFile(String filename) {
		StringBuilder string=new StringBuilder();
		BufferedReader br;
		
		try {
			br=new BufferedReader(new FileReader(new File("./shaders/" + filename)));
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
