#version 330

layout (location = 0) in vec3 vertices;
layout (location = 1) in vec4 a_color;
layout (location = 2) in float a_textureId;
layout (location = 3) in vec2 textures;
layout (location = 4) in vec3 a_normal;

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
	color = a_color;
	tex_coords = textures;
	textureId = a_textureId;
	
	worldPosition = objectTransform * vec4(vertices,1);
	
	normal = (objectTransform * vec4(a_normal, 0)).xyz;
	
	//solidViewlight
	toCamera =worldPosition.xyz - cameraPosition;
	
	
	gl_Position = projection * transform * worldPosition;

}