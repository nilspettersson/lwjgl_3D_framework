package niles.lwjgl.npsl;

import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

public class ShaderNp {
	private int program;
	private int vs;
	private int fs;
	
	private static FloatBuffer matrix=BufferUtils.createFloatBuffer(16);  //this is used to store the matrix.
	
	private HashMap<String, Object> properties;
	
	public ShaderNp(String filename) {
		properties = new HashMap<String, Object>();
		
		program=glCreateProgram();
		vs=glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vs, "#version 330\r\n" + 
				"\r\n" + 
				"layout (location = 0) in vec3 vertices;\r\n" + 
				"layout (location = 1) in vec4 a_color;\r\n" + 
				"layout (location = 2) in float a_textureId;\r\n" + 
				"layout (location = 3) in vec2 textures;\r\n" + 
				"layout (location = 4) in vec3 a_normal;\r\n" + 
				"\r\n" + 
				"uniform mat4 projection;\r\n" + 
				"uniform mat4 transform;\r\n" + 
				"uniform mat4 objectTransform;\r\n" + 
				"\r\n" + 
				"uniform vec3 cameraPosition;\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"//object\r\n" + 
				"out vec2 tex_coords;\r\n" + 
				"out vec4 color;\r\n" + 
				"out float textureId;\r\n" + 
				"out vec3 normal;\r\n" + 
				"out vec4 worldPosition;\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"out vec3 toCamera;\r\n" + 
				"\r\n" + 
				"void main(){\r\n" + 
				"	color = a_color;\r\n" + 
				"	tex_coords = textures;\r\n" + 
				"	textureId = a_textureId;\r\n" + 
				"	\r\n" + 
				"	worldPosition = objectTransform * vec4(vertices,1);\r\n" + 
				"	\r\n" + 
				"	normal = (objectTransform * vec4(a_normal, 0)).xyz;\r\n" + 
				"	\r\n" + 
				"	//solidViewlight\r\n" + 
				"	toCamera =worldPosition.xyz - cameraPosition;\r\n" + 
				"	\r\n" + 
				"	\r\n" + 
				"	gl_Position = projection * transform * worldPosition;\r\n" + 
				"\r\n" + 
				"}");
		glCompileShader(vs);
		
		if(glGetShaderi(vs, GL_COMPILE_STATUS)!=1) {
			System.err.println(glGetShaderInfoLog(vs));
			System.exit(1);
		}
		
		
		String[] npsl = readNpsl(filename); 
		
		String[] includes = npsl[0].split(",");
		String libText = "";
		for(int i = 0; i < includes.length; i++) {
			libText += readFile("/" + includes[i]);
		}
		
		fs=glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fs,"#version 130\r\n" + 
				"\r\n" + 
				"uniform sampler2D sampler[20];\r\n" + 
				"\r\n" + 
				"uniform vec3 lightColors[128];\r\n" + 
				"uniform vec3 lightPositions[128];\r\n" + 
				"uniform float lightIntensity[128];\r\n" + 
				"uniform int lightCount;\r\n" + 
				npsl[1],
				"\r\n" + 
				"in vec2 tex_coords;\r\n" + 
				"in vec4 color;\r\n" + 
				"in float textureId;\r\n" + 
				"in vec3 normal;\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"in vec3 toCamera;\r\n" + 
				"in vec4 worldPosition;\r\n" + 
				"\r\n" + 
				libText+
				"\r\n" + 
				"void main(){\r\n" + 
				"\r\n" + 
				"	float depth = gl_FragCoord.w;\r\n" + 
				"	\r\n" + 
				"	int id = int(textureId);\r\n" + 
				"	vec4 texture=texture2D(sampler[id], tex_coords);\r\n" + 
				"	\r\n" + 
				"	\r\n" + 
				"	//solidView lighting\r\n" + 
				"	float SolidBrightness = dot((-normal), normalize(toCamera));\r\n" + 
				"	SolidBrightness = max(SolidBrightness, 0.3);\r\n" + 
				"	\r\n" + 
				"	\r\n" + 
				npsl[2],
				"	\r\n" + 
				"	\r\n" + 
				"}");
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
	
	public String[] readNpsl(String filename) {
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
		String uniforms = "";
		boolean done = false;
		int loopCount = 0;
		while(!done) {
			int end = text.indexOf(",", start);
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
		
		//get the fragment code from file.
		start = text.indexOf("fragment{") + 9;
		int openIndex = text.indexOf("{", start);
		int closeIndex = text.indexOf("}", start);
		done = false;
		while(true) {
			if(closeIndex < openIndex || openIndex == -1) {
				break;
			}
			openIndex = text.indexOf("{", closeIndex + 1);
			if(openIndex == -1) {
				closeIndex = text.indexOf("}", closeIndex + 1);
			}
			else {
				closeIndex = text.indexOf("}", openIndex + 1);
			}
			
		}
		int end = closeIndex;
		String fragment = text.substring(start, end);
		fragment = fragment.replace("return ", "gl_FragColor = ");
		
		
		return new String[]{includes, uniforms, fragment};
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
	
	
	
	private String readFile(String filename) {
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

