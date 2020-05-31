#version 330

layout (location = 0) in vec3 vertices;
layout (location = 1) in vec4 a_color;
layout (location = 2) in float a_textureId;
layout (location = 3) in vec2 textures;

uniform mat4 projection;

out vec2 tex_coords;
out vec4 color;
out float textureId;

void main(){
	color = a_color;
	tex_coords = textures;
	textureId = a_textureId;
	
	gl_Position = projection * vec4(vertices,1);

}