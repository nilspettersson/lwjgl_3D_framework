#include lib/rayMarching.glsl;

uniforms{
	float time
}


float sdBox( vec3 p, vec3 b )
{
  vec3 q = abs(p) - b;
  return length(max(q,0.0)) + min(max(q.x,max(q.y,q.z)),0.0);
}


float scene(vec3 point){
	vec4 ball = vec4(4, -7, 2, 1);
	float ballDist = length(point - ball.xyz) - ball.w;

	vec3 transform1 = vec3(0, -7.5, -2);
	float box = sdBox(point - transform1, vec3(0.5, 0.5, 0.5));

    return min(ballDist, box);
}

fragment{
	vec4 rayDir = getRay();
	float rayOutput = rayMarch(rayDir, depth);
	vec4 diffuse = rayMarchDiffuse(rayOutput, rayDir, vec4(1, 0, 0, 1));


	vec4 output = texture;
	if(rayOutput == -1){
		return vec4(output);
	}
	else{
		return vec4(diffuse);
	}

}