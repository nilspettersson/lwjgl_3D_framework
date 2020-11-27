#include lib/rayMarching.glsl;
#include lib/sdf.glsl;

uniforms{
	float time
}



float sdf(vec3 point){
	vec4 ball = vec4(4, -7, 2, 1);
	float ballDist = length(point - ball.xyz) - ball.w;

	vec3 transform1 = vec3(4, -7.5, 2);
	float box = sdBox(point - transform1, vec3(0.5, 0.5, 0.5));
	transform1 = vec3(4, -8, 2);
	float box2 = sdBox(point - transform1, vec3(2, 0.2, 2));

    return min(box, box2);
}

fragment{
	Ray rayDir = getRay();
	rayDir = rayMarch(rayDir);
	vec4 diffuse = rayMarchDiffuse(rayDir, vec3(1, 0.5, 0.5));


	vec4 output = texture;
	if(rayDir.length == -1){
		return vec4(output);
	}
	else{
		return vec4(diffuse);
	}

}