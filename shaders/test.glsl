#include lib/shaderNp.glsl;

uniforms{

}

fragment{
	vec4 diffuse = diffuse(mix(texture, color, 0), texture.xyz * 4);
	vec4 glossy = glossy(vec4(1), 0.08, texture.xyz * 4);
	vec4 output = mix(diffuse, glossy, 0);
	return output;
}