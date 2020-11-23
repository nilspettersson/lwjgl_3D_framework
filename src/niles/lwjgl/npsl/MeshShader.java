package niles.lwjgl.npsl;

public class MeshShader extends Shader {
	public MeshShader(String filename) {
		super(filename);
		// TODO Auto-generated constructor stub
	}

	@Override
	String createVertexShader() {
		return "#version 330\r\n" + 
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
				"}";
	}

	@Override
	String createFragmentShader() {
		
		String[] npsl = readNpsl(filename); 
		
		String libText = getIncludedFiles(npsl);
		
		String fs = "#version 130\r\n" + 
				"\r\n" + 
				"uniform sampler2D sampler[20];\r\n" + 
				"\r\n" + 
				"uniform vec3 lightColors[128];\r\n" + 
				"uniform vec3 lightPositions[128];\r\n" + 
				"uniform float lightIntensity[128];\r\n" + 
				"uniform int lightCount;\r\n" + 
				npsl[1] +
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
				libText +
				npsl[3] +
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
				npsl[2] +
				"	\r\n" + 
				"	\r\n" + 
				"}";
		
		
		return fs;
	}

}
