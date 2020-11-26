package niles.lwjgl.npsl;

public class PostProcessingShader extends Shader{

	public PostProcessingShader(String filename) {
		super(filename);
		// TODO Auto-generated constructor stub
	}

	@Override
	String createVertexShader() {
		// TODO Auto-generated method stub
		return "#version 330\r\n" + 
				"\r\n" + 
				"layout (location = 0) in vec3 vertices;\r\n" + 
				"layout (location = 1) in vec2 textures;\r\n" + 
				"\r\n" + 
				"out vec2 tex_coords;\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"out vec3 toCamera;\r\n" + 
				"\r\n" + 
				"void main(){\r\n" + 
				"	tex_coords = textures;\r\n" + 
				"	\r\n" + 
				"	gl_Position = vec4(vertices,1);\r\n" + 
				"\r\n" + 
				"}";
	}

	@Override
	String createFragmentShader() {
		String[] npsl = readNpsl(filename); 
		
		String libText = getIncludedFiles(npsl);
		
		return "#version 130\r\n" + 
				"\r\n" + 
				"uniform vec3 lightColors[128];\r\n" + 
				"uniform vec3 lightPositions[128];\r\n" + 
				"uniform float lightIntensity[128];\r\n" + 
				"uniform int lightCount;\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"uniform sampler2D sampler[20];\r\n" + 
				"uniform vec3 cameraPosition;\r\n" + 
				"uniform vec4 cameraRotation;\r\n" + 
				npsl[1] +
				"\r\n" + 
				"in vec2 tex_coords;\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"	vec4 rawDepth = texture2D(sampler[10], tex_coords);\r\n" + 
				"	float depth = (0.1 * 1000.0) / (1000 - rawDepth.z * (1000 - 0.1));"+
				"vec2 uv;" +
				libText +
				npsl[3] +
				"\r\n" + 
				"\r\n" + 
				"void main(){\r\n" + 
				"	uv = tex_coords;" +
				"	uv.y = 1.0 - uv.y;" +
				"	\r\n" + 
				"	//gets the color texture.\r\n" + 
				"	vec4 texture=texture2D(sampler[9], uv);\r\n" + 
				"	\r\n" + 
				"	\r\n" + 
				"	//gets the depth texture\r\n" + 
				
				npsl[2] +
				"	\r\n" + 
				"}";
	}

}
