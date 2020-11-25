float scene(vec3 point);


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

vec4 getRay(){
	vec3 resolution = vec3(1.77, 1, 0.72);

    vec2 uv = tex_coords;
    uv.x = (uv.x * 2.0) - 1.0;
    uv.y = (2.0 * uv.y) - 1.0;
    if(resolution.x >= resolution.y){
        uv.x *= resolution.x/resolution.y;
    }else{
        uv.y *= resolution.y/resolution.x;
    }
    float tan_fov = tan(1.2217/2.0);
    vec2 pxy = uv * tan_fov;
    vec3 rayDir = normalize(vec3(pxy, 1));

	vec4 dir = rotate_vector(cameraRotation, vec4(rayDir, rayDir.z));

    return dir;
}

//gets the distance to the closest object in the scene.
float rayMarch(vec4 rayDir){
	vec3 rayOrigin = cameraPosition;
	rayOrigin.z *=-1;
	
	float cosA = rayDir.w;
	float DistOrigin = 0;
	for(int i = 0; i < 100; i++){
		vec3 point = rayOrigin + rayDir.xyz * DistOrigin;
		
		float dist = scene(point);
		
		DistOrigin += dist;
		if(dist < 0.01 ){
			break;
		}
		if(DistOrigin * cosA > depth || DistOrigin  > 10000){
			DistOrigin = -1;
			break;
		}
	}

	return (DistOrigin);
}

//gets the normal for a pixel in ray marcher.
vec3 getNormal(vec3 point){
	float dist = scene(point);
	vec2 e = vec2(0.01, 0);

	vec3 normal = dist - vec3(scene(point - e.xyy),
		scene(point - e.yxy),
		scene(point - e.yyx));
	return normalize(normal);
}

vec4 rayMarchDiffuse(float rayOutput, vec4 rayDir, vec4 color){

	//finding the point of the intersection.
	vec3 rayOrigin = cameraPosition;
	rayOrigin.z *= -1;
	vec3 point = rayOrigin + rayDir.xyz * rayOutput;
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

	vec4 diffuse = color;
	diffuse.xyz *= max(allLight, 0);
	return diffuse;
}

