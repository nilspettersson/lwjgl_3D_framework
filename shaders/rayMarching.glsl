#include lib/rayMarching.glsl;
#include lib/sdf.glsl;

uniforms{

}



float sdf(vec3 p){

	//p = opRep(p, 4);
	float plane = sdplane(p, -1);

	p = opRepLim(p, 4, vec3(5, 0, 5));
	float box = sdBox(p - vec3(0, 0, 0), vec3(1, 1, 1) );

	return min(box, plane);
}

fragment{

	Ray ray = getRay();
	ray = rayMarch(ray);
	
	if(ray.length != -1){
		return rayMarchDiffuse(ray, vec3(1), vec3(0.1));
	}
	else{
		vec3 col = vec3(0.30, 0.36, 0.60) - ((1 - ray.dir.y) * 0.2);
		return vec4(col, 1);
	}
}