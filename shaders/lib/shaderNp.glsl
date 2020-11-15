vec4 diffuse(vec4 color){
	vec3 allLight = vec3(0, 0, 0);
	for(int i = 0; i < lightCount; i++){
		vec3 toLight = lightPositions[i] - worldPosition.xyz;
		float disToLight = length(toLight)/8;
		
		float brightness = dot(normalize(normal), normalize(toLight));
		brightness = max(brightness, 0);
		float attenuation = lightIntensity[i] / (4.0 + 1*disToLight + 1 * disToLight * disToLight);
		brightness *= attenuation;
		
		vec3 light = lightColors[i] * brightness;
		allLight += light;
	}
	
	vec4 diffuse = color;
	diffuse.xyz *= allLight;
	return diffuse;
}

vec4 diffuse(vec4 color, vec3 normalColor){
	vec3 newNormal = normalize(normal);
	vec3 allLight = vec3(0, 0, 0);
	for(int i = 0; i < lightCount; i++){
		vec3 toLight = lightPositions[i] - worldPosition.xyz;
		float disToLight = length(toLight)/8;
		
		float brightness = dot(newNormal, normalize(toLight));
		brightness = max(brightness * ((normalColor.x + normalColor.y + normalColor.z) / 3), 0);
		float attenuation = lightIntensity[i] / (4.0 + 1*disToLight + 1 * disToLight * disToLight);
		brightness *= attenuation;
		
		vec3 light = lightColors[i] * brightness;
		allLight += light;
	}
	
	vec4 diffuse = color;
	diffuse.xyz *= allLight;
	return diffuse;
}

vec4 glossy(vec4 color, float roughness){
	vec3 allLight = vec3(0, 0, 0);
	for(int i = 0; i < lightCount; i++){
		vec3 toLight = lightPositions[i] - worldPosition.xyz;
		float disToLight = length(toLight);
		
		
		vec3 lightDir = normalize(toLight);
		vec3 viewDir = normalize(toCamera);
		vec3 halfwayDir = normalize(lightDir - viewDir);
		float brightness = pow(max(dot(normalize(normal), halfwayDir), 0), 1 + (1 / roughness));
		float attenuation = ((lightIntensity[i] / roughness)) / (4.0 + 1*disToLight + 0.1 * disToLight * disToLight);
		brightness *= attenuation;
		
		//makes it less bright when right over object.
		float cameraDot = dot(normalize(toCamera), normalize(normal));
		brightness *= smoothstep(-1.4, 1,cameraDot);
		
		
		if(dot(normalize(normal), normalize(toLight)) <= 0){
			brightness = 0;
		}
		brightness = max(brightness, 0.01);
		
		vec3 light = lightColors[i] * brightness;
		allLight += light;
	}
	float col = (color.x + color.y + color.z) / 3;
	vec4 glossy = vec4(col, col, col, 1);
	glossy.xyz *= allLight;
	return glossy;
}

vec4 glossy(vec4 color, float roughness, vec3 normalColor){
	vec3 newNormal = normal + normalColor;
	vec3 allLight = vec3(0, 0, 0);
	for(int i = 0; i < lightCount; i++){
		vec3 toLight = lightPositions[i] - worldPosition.xyz;
		float disToLight = length(toLight);
		
		
		vec3 lightDir = normalize(toLight);
		vec3 viewDir = normalize(toCamera);
		vec3 halfwayDir = normalize(lightDir - viewDir);
		float brightness = pow(max(dot(normalize(newNormal), halfwayDir), 0), 1 + (1 / roughness));
		float attenuation = ((lightIntensity[i] / roughness)) / (4.0 + 1*disToLight + 0.1 * disToLight * disToLight);
		brightness *= attenuation;
		
		//makes it less bright when right over object.
		float cameraDot = dot(normalize(toCamera), normalize(newNormal));
		brightness *= smoothstep(-1.4, 1,cameraDot);
		
		
		if(dot(normalize(newNormal), normalize(toLight)) <= 0){
			brightness = 0;
		}
		brightness = max(brightness, 0.01);
		
		vec3 light = lightColors[i] * brightness;
		allLight += light;
	}
	float col = (color.x + color.y + color.z) / 3;
	vec4 glossy = vec4(col, col, col, 1);
	glossy.xyz *= allLight;
	return glossy;
}

vec4 add(vec4 color1, vec4 color2){
	return vec4(color1 + color2);
}