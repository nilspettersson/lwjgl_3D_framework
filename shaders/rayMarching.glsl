#include lib/rayMarching.glsl;

uniforms{

}

float scene(vec3 point){
	float dis = length(point - vec3(0, 0, 0)) + 20;
	vec4 ball = vec4(0, 0, 0, 1);
	float ballDist = length(point) - 1;
	return ballDist;
}

fragment{

	vec4 rayDir = getRay();
	float ray = rayMarch(rayDir);
	
	if(ray != -1){
		return rayMarchDiffuse(ray, rayDir, vec4(1));
	}
	else{
		return vec4(0);
	}
}