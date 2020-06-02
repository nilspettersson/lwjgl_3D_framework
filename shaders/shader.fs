#version 120

uniform sampler2D sampler[20];

in vec2 tex_coords;
in vec4 color;
in float textureId;
in vec3 normal;

in vec3 toLight;

void main(){
	float depth = gl_FragCoord.w*4;
	
		
	int id = int(textureId);
	vec4 texture=texture2D(sampler[id], tex_coords);
	
	
	toLight = normalize(toLight);
	
	float nDot = dot(normalize(normal), toLight);
	float brightness = max(nDot, 0.3);
	
	vec4 diffuseColor = (texture + color);
	diffuseColor.xyz *= brightness;
	
	vec4 output = diffuseColor;
	gl_FragColor=output;
	
	
	//gl_FragColor = vec4(brightness, brightness, brightness,1);
	//gl_FragColor = vec4(depth,depth,depth, 1);

	
}