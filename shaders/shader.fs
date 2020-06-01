#version 120

uniform sampler2D sampler[20];

in vec2 tex_coords;
in vec4 color;
in float textureId;



void main(){
	float depth = gl_FragCoord.w*4;
	
		
	int id = int(textureId);
	vec4 texture=texture2D(sampler[id], tex_coords);
	//gl_FragColor=texture + color;
	
	gl_FragColor = vec4(depth,depth,depth, 1);

	
}