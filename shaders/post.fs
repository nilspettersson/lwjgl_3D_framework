#version 120

uniform sampler2D sampler;

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
	float depth = gl_FragCoord.w;
	
	tex_coords.y = tex_coords.y * -1;
		
	int id = int(textureId);
	vec4 texture=texture2D(sampler, tex_coords);
	
	gl_FragColor = texture;
	//gl_FragColor = vec4(depth,depth,depth, 1);

}