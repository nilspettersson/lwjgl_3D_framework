#include lib/post.glsl;

uniforms{
	
}


float scene(vec3 point){
	vec4 ball = vec4(4, -6, 2, 3);
	float ballDist = length(point - ball.xyz) - ball.w;
	return ballDist;
}

fragment{
	float rayOutput = rayMarch(rayDir, depth);
	vec4 diffuse = rayMarchDiffuse(rayOutput, rayDir);

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