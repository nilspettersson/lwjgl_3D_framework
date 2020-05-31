#version 120

uniform sampler2D sampler[20];

in vec2 tex_coords;
in vec4 color;
in float textureId;




void main(){
	vec2 l = gl_FragCoord.xy;
	
		
		
	int id = int(textureId);
	vec4 texture=texture2D(sampler[id], tex_coords);
	gl_FragColor=texture + color;
	
	//gl_FragColor = vec4(1,1,1,1);

	
}