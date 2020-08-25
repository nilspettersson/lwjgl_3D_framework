#version 330

layout (location = 0) in vec3 vertices;
layout (location = 1) in vec2 textures;

out vec2 tex_coords;


out vec3 toCamera;

void main(){
	tex_coords = textures;
	
	gl_Position = vec4(vertices,1);

}