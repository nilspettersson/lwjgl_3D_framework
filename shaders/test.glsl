#include lib/mesh.glsl;

uniforms{

}

fragment{
	vec4 normalMap = bump(textureId, 3); 
	vec4 diffuse = diffuse(mix(texture, color, 0), normalMap);
	vec4 glossy = glossy(vec4(1), 0.10, normalMap);
	vec4 output = mix(diffuse, glossy, 0);
	return output;
}