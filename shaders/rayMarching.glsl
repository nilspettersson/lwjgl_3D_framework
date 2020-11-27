#include lib/rayMarching.glsl;
#include lib/sdf.glsl;

uniforms{
	int x
}

float sdf(vec3 p){
	float plane = sdplane(p, -1);

	p = opRepLim(p, 4, vec3(500, 0, 500));
	float box = sdBox(p - vec3(0, 0, 0), vec3(1, 1, 1) );

	return min(box, plane);
}

fragment{

	Ray ray = getRay();
	ray = rayMarch(ray);
	
	if(ray.length != -1){
		return rayMarchDiffuse(ray, vec3(1)) + rayMarchAmbient(ray, vec3(0.2, 0.3, 0.4), 1);
	}
	else{
		vec3 col = vec3(0.40, 0.46, 0.60) - ((1 - ray.dir.y) * 0.3);
		return vec4(col, 1);
	}
	
}