#include lib/post.glsl;
#include lib/rayMarching.glsl;

uniforms{

}


fragment{
	vec4 rayDir = getRay();
	vec2 rayDis = rayMarchVolume(rayDir, depth);
	float dis = rayDis.x;
	float rayDepth = rayDis.y;
	
	if(dis >= 1000){
		vec3 toLight = lightPositions[0] - cameraPosition;
		toLight = normalize(toLight);
		toLight.z *= -1;
		
		float lightDot = dot(rayDir.xyz, toLight);
		
		vec3 output = vec3(texture.xyz + max(lightDot, 0));
		return texture;
	}
	
	else{
		//z: distance to object.
		//dis: dis is the distance to ray hit.
		float disToObject;
		if(dis > 0){
			disToObject = depth - dis * rayDir.w;
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
			float lightDot = dot(rayDir.xyz, toLight);
			
			float light = pow(max(lightDot, 0), 8);
			
			lightColor += vec3(lightColors[i] * light);
		}
		lightColor /= lightCount;
		lightColor *= 2;
		lightColor = vec3(min(lightColor.x, 1), min(lightColor.y, 1), min(lightColor.z, 1));
		lightColor = max(lightColor, 0.05);
		
		vec3 output = mix(lightColor, texture.xyz, 1 - min(minDis, 0.9));
		return vec4(output, 1);
	}
	
}