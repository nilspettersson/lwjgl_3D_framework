float sceneDistance(vec3 point);



vec4 multQuat(vec4 q1, vec4 q2){
	return vec4(
	q1.w * q2.x + q1.x * q2.w + q1.z * q2.y - q1.y * q2.z,
	q1.w * q2.y + q1.y * q2.w + q1.x * q2.z - q1.z * q2.x,
	q1.w * q2.z + q1.z * q2.w + q1.y * q2.x - q1.x * q2.y,
	q1.w * q2.w - q1.x * q2.x - q1.y * q2.y - q1.z * q2.z
	);
}

/*vec3 rotate_vector( vec4 quat, vec3 vec )
{
	vec4 qv = multQuat( quat, vec4(vec, 0.0) );
	return multQuat( qv, vec4(-quat.x, -quat.y, -quat.z, quat.w) ).xyz;
}*/

vec4 rotate_vector( vec4 quat, vec4 vec )
{
	float cosA = vec.z;
	
	vec4 qv = multQuat( quat, vec4(vec.xyz, 0.0) );
	return vec4(multQuat( qv, vec4(-quat.x, -quat.y, -quat.z, quat.w) ).xyz, cosA);
}

vec4 calculateFragementRay(vec2 fragCoord){
	vec3 resolution = vec3(1.77, 1, 0.72);

    vec2 uv = fragCoord;
    uv.x = (uv.x * 2.0) - 1.0;
    uv.y = (2.0 * uv.y) - 1.0;
    if(resolution.x >= resolution.y){
        uv.x *= resolution.x/resolution.y;
    }else{
        uv.y *= resolution.y/resolution.x;
    }
    float tan_fov = tan(1.2217/2.0);
    vec2 pxy = uv * tan_fov;
    vec3 ray_dir = normalize(vec3(pxy, 1));
    return vec4(ray_dir, ray_dir.z);
}




float getDist(vec3 point){
	vec4 ball = vec4(0, 0, 8, 10);
	float ballDist = length(point - ball.xyz) - ball.w;
	return ballDist;
}

float getDepth(vec3 point){
	vec4 ball = vec4(0, 0, 8, 10);
	float ballDist = ball.w - length(point - ball.xyz);
	return ballDist;
}

vec2 rayMarchVolume(vec4 rayDir, float maxDepth){
	vec3 rayOrigin = cameraPosition;
	rayOrigin.z *=-1;
	
	float cosA = rayDir.w;
	float DistOrigin = 0;
	for(int i = 0; i < 100; i++){
		vec3 point = rayOrigin + rayDir.xyz * DistOrigin;
		float dist = getDist(point);
		DistOrigin += dist;
		if(DistOrigin  > 1000 || dist < 0.01 ){
			break;
		}
		if(DistOrigin * cosA > maxDepth){
			DistOrigin = 1000;
			break;
		}
	}
	
	float depth = DistOrigin  + 0.1;
	
	for(int i = 0; i < 100; i++){
		vec3 point = rayOrigin + rayDir.xyz * depth;
		float dist = getDepth(point);
		depth += dist;
		if(depth > 1000 || dist < 0.01 ){
			break;
		}
	}
	
	return vec2(DistOrigin, max((depth - DistOrigin), 0));
}




float getDistToScene(vec3 point, mat4[20] scene, int sceneSize){
	float minDistance = 10000;
	for(int i = 0; i < sceneSize; i++){
		if(scene[0][3].w == 0){
			vec4 ball = vec4(scene[i][0]);
			float ballDist = length(point - ball.xyz) - ball.w;
			
			if(ballDist < minDistance){
				minDistance = ballDist;
			}
		}
	}
	
	return minDistance;
	
}

float rayMarch(vec4 rayDir, float maxDepth, mat4[20] scene, int sceneSize){
	vec3 rayOrigin = cameraPosition;
	rayOrigin.z *=-1;
	
	float cosA = rayDir.w;
	float DistOrigin = 0;
	for(int i = 0; i < 100; i++){
		vec3 point = rayOrigin + rayDir.xyz * DistOrigin;
		//float dist = getDistToScene(point, scene, sceneSize);
		float dist = sceneDistance(point);
		DistOrigin += dist;
		if(dist < 0.01 ){
			break;
		}
		if(DistOrigin * cosA > maxDepth || DistOrigin  > 10000){
			DistOrigin = -1;
			break;
		}
	}

	return DistOrigin;
}

vec3 getNormal(vec3 point, mat4[20] scene, int sceneSize){
	float dist = getDistToScene(point, scene, sceneSize);
	vec2 e = vec2(0.01, 0);

	vec3 normal = dist - vec3(getDistToScene(point - e.xyy, scene, 2),
		getDistToScene(point - e.yxy, scene, 2),
		getDistToScene(point - e.yyx, scene, 2));
	return normalize(normal);
}

vec4 rayMarchDiffuse(float rayOutput, vec4 rayDir, mat4[20] scene, int sceneSize){
	vec4 color = vec4(1);

	//finding the point of the intersection.
	vec3 rayOrigin = cameraPosition;
	rayOrigin.z *= -1;
	vec3 point = rayOrigin + rayDir.xyz * rayOutput;
	vec3 normal = getNormal(point, scene, sceneSize);


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
	diffuse.xyz *= allLight;
	return diffuse;
}




