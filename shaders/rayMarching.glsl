#include lib/rayMarching.glsl;
#include lib/sdf.glsl;

uniforms{

}



float sdf(vec3 p){

	//p = opRepLim(p, 4, vec3(20, 20, 20));
	//p = opRep(p, 4);

	float box = sdBox(p - vec3(0, 2 , 0), vec3(1, 1, 1) );
	float plane = sdplane(p, 0);
	//float test = length(abs(cos(p)) - s * 0.01);
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