#version 120

uniform sampler2D sampler[20];

in vec2 tex_coords;
in vec4 color;
in float textureId;
in vec3 normal;

in vec3 lightDir;

void main(){
	float depth = gl_FragCoord.w*4;
	
		
	int id = int(textureId);
	vec4 texture=texture2D(sampler[id], tex_coords);
	
	
	//vec3 lightDir = vec3(0.4, 1, 0.4);
	lightDir = normalize(lightDir);
	
	float nDot = dot(normalize(normal), lightDir);
	float brightness = max(nDot, 0.3);
	
	vec4 diffuseColor = (texture + color-1);
	diffuseColor.xyz *= brightness;
	
	//gl_FragColor=diffuseColor;
	
	
	gl_FragColor = vec4(brightness, brightness, brightness,1);
	//gl_FragColor = vec4(depth,depth,depth, 1);

	
}