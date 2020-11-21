#include post.glsl;

uniforms{

}

fragment{
	depth /= 10;
	texture.x = 1;
	return vec4(texture.x / depth, texture.y / depth, texture.z / depth, 1);
}