#version 120

uniform sampler2D sampler[20];

uniform vec3 lightColors[128];
uniform vec3 lightPositions[128];
uniform float lightIntensity[128];
uniform int lightCount;

in vec2 tex_coords;
in vec4 color;
in float textureId;
in vec3 normal;


in vec3 toCamera;
in vec4 worldPosition;



void main(){
	
	tex_coords.y = 1.0 - tex_coords.y;
	
	
	vec4 texture=texture2D(sampler[9], tex_coords);
	
	float z = (0.01 * 10000) / (10000 - texture.x * (10000 - 0.01));
	z *= 0.03;
	
	gl_FragColor = vec4(z, z, z, 1);
	//gl_FragColor = texture;

}