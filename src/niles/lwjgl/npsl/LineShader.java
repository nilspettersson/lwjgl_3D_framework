package niles.lwjgl.npsl;

public class LineShader extends Shader{

	public LineShader(String filename) {
		super(filename);
		// TODO Auto-generated constructor stub
	}

	@Override
	String createVertexShader() {
		return "#version 330\r\n" + 
				"\r\n" + 
				"layout (location = 0) in vec3 vertices;\r\n" + 
				"\r\n" + 
				"uniform mat4 projection;\r\n" + 
				"uniform mat4 transform;\r\n" + 
				"uniform mat4 objectTransform;\r\n" + 
				"uniform vec3 cameraPosition;\r\n" + 
				"out vec4 worldPosition;\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"out vec3 toCamera;\r\n" + 
				"\r\n" + 
				"void main(){\r\n" + 
				"	worldPosition = objectTransform * vec4(vertices,1);\r\n" + 
				"	gl_Position = projection * transform * worldPosition;\r\n" + 
				"\r\n" + 
				"}";
	}

	@Override
	String createFragmentShader() {
		
		String fs = "#version 130\r\n" + 
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
				"\r\n" + 
				"void main(){\r\n" + 
				"	gl_FragColor = vec4(1);" +
				"}";
		return fs;
	}

}
