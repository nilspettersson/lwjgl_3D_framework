#include lib/shaderNp.glsl;

uniforms{

}


fragment{
	vec4 color = vec4(texture + color);
	
	vec4 diffuse = diffuse(texture);
	
	vec4 glossy = glossy(color, 0.2);
	
	
	vec4 output = mix(diffuse, glossy, 0.7);
	return output;
	
}