#include lib/post.glsl;

uniforms{
	
}

fragment{
	mat4 scene[20];
	scene[0][0].x = 0;
	scene[0][0].y = -5.5;
	scene[0][0].z = -3;
	scene[0][0].w = 1;
	scene[0][3].w = 0;

	scene[1][0].x = -3;
	scene[1][0].y = -7.5;
	scene[1][0].z = -3;
	scene[1][0].w = 1;
	scene[1][3].w = 0;


	float rayOutput = rayMarch(rayDir, depth, scene, 2);
	vec4 diffuse = rayMarchDiffuse(rayOutput, rayDir, scene, 2);

	/*vec3 rayOrigin = cameraPosition;
	rayOrigin.z *= -1;
	vec3 point = rayOrigin + rayDir.xyz * rayOutput;
	vec4 diffuse = vec4(getNormal(point, scene, 2), 1);*/

	vec4 output = texture;
	if(rayOutput == -1){
		return vec4(output);
	}
	else{
		return vec4(diffuse);
	}

}