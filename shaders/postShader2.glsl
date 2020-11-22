#include lib/post.glsl;

uniforms{

}

fragment{

	mat4 scene[1];
	scene[0][0].x = 0;
	scene[0][0].y = -7.5;
	scene[0][0].z = -3;
	scene[0][0].w = 1;
	scene[0][3].w = 0;

	vec4 rayDir = calculateFragementRay(tex_coords);
	rayDir = rotate_vector(cameraRotation, rayDir);

	float distance = rayMarch(rayDir, depth, scene);
	
	vec4 output = texture;
	if(distance == -1){
		return vec4(output);
	}
	else{
		return vec4(distance);
	}
	

	/*depth /= 10;
	texture.x = 1;
	return vec4(texture.x / depth, texture.y / depth, texture.z / depth, 1);*/
}