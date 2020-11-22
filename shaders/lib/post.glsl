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



float getDist(vec3 point){
	vec4 ball = vec4(-30, 30, 0, 70);
	float ballDist = length(point - ball.xyz) - ball.w;
	return ballDist;
}

float getDepth(vec3 point){
	vec4 ball = vec4(-30, 30, 0, 70);
	float ballDist = ball.w - length(point - ball.xyz);
	return ballDist;
}

vec2 rayMarch(vec4 rayDir, float maxDepth){
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
