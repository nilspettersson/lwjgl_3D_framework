
//will be removed at some point.
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








