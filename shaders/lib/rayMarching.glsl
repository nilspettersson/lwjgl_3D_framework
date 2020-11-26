float sdf(vec3 point);

struct Ray{
	vec4 dir;
	float length;
};

vec4 multQuat(vec4 q1, vec4 q2){
	return vec4(
	q1.w * q2.x + q1.x * q2.w + q1.z * q2.y - q1.y * q2.z,
	q1.w * q2.y + q1.y * q2.w + q1.x * q2.z - q1.z * q2.x,
	q1.w * q2.z + q1.z * q2.w + q1.y * q2.x - q1.x * q2.y,
	q1.w * q2.w - q1.x * q2.x - q1.y * q2.y - q1.z * q2.z
	);
}

vec4 rotate_vector( vec4 quat, vec4 vec )
{
	float cosA = vec.z;
	
	vec4 qv = multQuat( quat, vec4(vec.xyz, 0.0) );
	return vec4(multQuat( qv, vec4(-quat.x, -quat.y, -quat.z, quat.w) ).xyz, cosA);
}

Ray getRay(){
	vec3 resolution = vec3(1.77, 1, 0.72);

    vec2 uv2 = uv;
    uv2.x = (uv2.x * 2.0) - 1.0;
    uv2.y = (2.0 * uv2.y) - 1.0;
    if(resolution.x >= resolution.y){
        uv2.x *= resolution.x/resolution.y;
    }else{
        uv2.y *= resolution.y/resolution.x;
    }
    float tan_fov = tan(1.2217/2.0);
    vec2 pxy = uv2 * tan_fov;
    vec3 rayDir = normalize(vec3(pxy, 1));

	vec4 dir = rotate_vector(cameraRotation, vec4(rayDir, rayDir.z));

    return Ray(dir, 0);
}

//gets the distance to the closest object in the sdf function.
Ray rayMarch(Ray ray){
	vec3 rayOrigin = cameraPosition;
	rayOrigin.z *=-1;
	
	float cosA = ray.dir.w;
	float DistOrigin = 0;
	for(int i = 0; i < 200; i++){
		vec3 point = rayOrigin + ray.dir.xyz * DistOrigin;
		
		float dist = sdf(point);
		
		DistOrigin += dist;
		if(dist < 0.01 ){
			break;
		}
		if(DistOrigin * cosA > depth || DistOrigin  > 10000){
			DistOrigin = -1;
			break;
		}
	}

	return Ray(ray.dir, DistOrigin);
}

//gets the normal for a pixel in ray marcher.
vec3 getNormal(vec3 point){
	float dist = sdf(point);
	vec2 e = vec2(0.01, 0);

	vec3 normal = dist - vec3(sdf(point - e.xyy),
		sdf(point - e.yxy),
		sdf(point - e.yyx));
	return normalize(normal);
}

vec4 rayMarchDiffuse(Ray ray, vec3 color, vec3 ambientColor){

	//finding the point of the intersection.
	vec3 rayOrigin = cameraPosition;
	rayOrigin.z *= -1;
	vec3 point = rayOrigin + ray.dir.xyz * ray.length;
	vec3 normal = getNormal(point);


	vec3 allLight = vec3(0, 0, 0);
	for(int i = 0; i < lightCount; i++){
		vec3 pos = lightPositions[i];
		pos.z *= -1;
		vec3 toLight = pos - point;

		float disToLight = length(toLight) / 8;

		float brightness = dot(normal, normalize(toLight));
		brightness = max(brightness, 0);
		float attenuation = lightIntensity[i] / (4.0 + 1 * disToLight + 1 * disToLight * disToLight);
		brightness *= attenuation;
		
		vec3 light = lightColors[i] * brightness;
		allLight += light;
	}
	allLight += ambientColor / (lightCount + 1);
	vec4 diffuse = vec4(color, 1);
	diffuse.xyz *= max(allLight, 0);
	return diffuse;
}

