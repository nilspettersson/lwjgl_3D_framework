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

out vec2 tex_coords;
out vec4 color;
out float textureId;
out vec3 normal;

out vec3 lightDir;

void main(){
	color = a_color;
	tex_coords = textures;
	textureId = a_textureId;
	
	vec4 worldPosition = objectTransform * vec4(vertices,1);
	
	normal = (objectTransform * vec4(a_normal, 0)).xyz;
	
	//vec4 cameraPosition = vec4(projection[1].xyz, 1);
	lightDir = cameraPosition - worldPosition.xyz;
	
	
	gl_Position = projection * transform * worldPosition;

}