#include lib/post.glsl;

uniforms{

}


fragment{
	vec3 rayOrigin = cameraPosition;
	rayOrigin.z *=-1;
	
	
	vec3 rayDir = calculateFragementRay(tex_coords, vec3(1.77, 1, 0.72));
	float cosA = rayDir.z;
	
	rayDir = rotate_vector(cameraRotation, rayDir);
	
	
	vec2 rayDis = rayMarch(rayOrigin, rayDir, depth, cosA);
	float dis = rayDis.x;
	float rayDepth = rayDis.y;
	
	
	
	if(dis >= 1000){
		vec3 toLight = lightPositions[0] - cameraPosition;
		toLight = normalize(toLight);
		toLight.z *= -1;
		
		float lightDot = dot(rayDir, toLight);
		
		vec3 output = vec3(texture.xyz + max(lightDot, 0));
		return texture;
	}
	
	else{
		//z: distance to object.
		//dis: dis is the distance to ray hit.
		float disToObject;
		if(dis > 0){
			disToObject = depth - dis * cosA;
		}
		else{
			disToObject = depth;
		}
		
		float minDis = min(disToObject, rayDepth);

		minDis = sqrt(minDis);
		minDis *= 0.2;
		
		vec3 lightColor = vec3(0, 0, 0);
		for(int i = 0; i < lightCount; i++){
			vec3 toLight = lightPositions[i] - cameraPosition;
			toLight = normalize(toLight);
			toLight.z *= -1;
			float lightDot = dot(rayDir, toLight);
			
			float light = pow(max(lightDot, 0), 8);
			
			lightColor += vec3(lightColors[i] * light);
		}
		lightColor /= lightCount;
		lightColor *= 2;
		lightColor = vec3(min(lightColor.x, 1), min(lightColor.y, 1), min(lightColor.z, 1));
		lightColor = max(lightColor, 0.05);
		
		vec3 output = mix(lightColor, texture.xyz, min(minDis, 0.9));
		return vec4(output, 1);
	}
	
}