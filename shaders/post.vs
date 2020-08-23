#version 330

layout (location = 0) in vec3 vertices;
layout (location = 1) in vec2 textures;

uniform mat4 projection;
uniform mat4 transform;
uniform mat4 objectTransform;

uniform vec3 cameraPosition;




//object
out vec2 tex_coords;
out vec4 color;
out float textureId;
out vec3 normal;
out vec4 worldPosition;



out vec3 toCamera;

void main(){
	tex_coords = textures;
	
	worldPosition = objectTransform * vec4(vertices,1);
	
	
	//solidViewlight
	toCamera =worldPosition.xyz - cameraPosition;
	
	
	gl_Position = vec4(vertices,1);

}