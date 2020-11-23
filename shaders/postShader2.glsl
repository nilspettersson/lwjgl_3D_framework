#include lib/rayMarching.glsl;

uniforms{
	
}


float scene(vec3 point){
	vec4 ball = vec4(4, -6, 2, 3);
	float ballDist = length(point - ball.xyz) - ball.w;
	return ballDist;
}

fragment{
	vec4 rayDir = getRay();
	float rayOutput = rayMarch(rayDir, depth);
	vec4 diffuse = rayMarchDiffuse(rayOutput, rayDir);


	vec4 output = texture;
	if(rayOutput == -1){
		return vec4(output);
	}
	else{
		return vec4(diffuse);
	}

}