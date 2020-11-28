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

//creates a normal map from the height of a texture.
vec4 bump(float textureId, float strength){
	const vec2 size = vec2(2.0,0.0);
	const ivec3 off = ivec3(-1,0,1);

    vec4 texture = texture2D(sampler[int(textureId)], tex_coords);
    vec3 s11 = texture.xyz;
    vec3 s01 = textureOffset(sampler[int(textureId)], tex_coords, off.xy).xyz;
    vec3 s21 = textureOffset(sampler[int(textureId)], tex_coords, off.zy).xyz;
    vec3 s10 = textureOffset(sampler[int(textureId)], tex_coords, off.yx).xyz;
    vec3 s12 = textureOffset(sampler[int(textureId)], tex_coords, off.yz).xyz;
	
	float s11f = (s11.x + s11.y + s11.z) / 3;
    float s01f = (s01.x + s01.y + s01.z) / 3;
    float s21f = (s21.x + s21.y + s21.z) / 3;
    float s10f = (s10.x + s10.y + s10.z) / 3;
    float s12f = (s12.x + s12.y + s12.z) / 3;

	vec3 va = normalize(vec3(size.xy,s21f - s01f));
    vec3 vb = normalize(vec3(size.yx,s12f - s10f));

	vec4 bump = vec4( cross(va,vb), strength);
	bump.x *= -1;

	return bump;
}

vec4 diffuse(vec4 color, vec4 normalMap){
	vec3 bumpDif = normalMap.xyz - vec3(0, 0, 1);
	if(normal.x < 0){
		bumpDif.x *= -1;
		//return vec4(1, 0, 0, 1);
	}
	if(normal.y < 0){
		bumpDif.x *= -1;
		//return vec4(1, 0, 0, 1);
	}
	if(abs(normal.y) > abs(normal.x)){
		bumpDif.xy = bumpDif.yx;
		//return vec4(1, 0, 0, 1);
	}

	vec3 newNormal = normalize(normalize(normal) + bumpDif  * normalMap.w);

	vec3 allLight = vec3(0, 0, 0);
	for(int i = 0; i < lightCount; i++){
		vec3 toLight = lightPositions[i] - worldPosition.xyz;
		float disToLight = length(toLight)/8;
		
		float brightness = dot(newNormal, normalize(toLight));
		brightness = max(brightness, 0);
		float attenuation = lightIntensity[i] / (4.0 + 1 * disToLight + 1 * disToLight * disToLight);
		brightness *= attenuation;
		
		vec3 light = lightColors[i] * brightness;
		allLight += light;
	}
	
	vec4 diffuse = color;
	diffuse.xyz *= allLight;
	//return vec4(normal.xyz * 0.5 + 0.5, 1);
	return vec4(diffuse);
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

vec4 glossy(vec4 color, float roughness, vec4 normalMap){
	vec3 bumpDif = normalMap.xyz - vec3(0, 0, 1);
	if(normal.x < 0){
		bumpDif.x *= -1;
	}
	if(normal.y < 0){
		bumpDif.x *= -1;
	}
	if(abs(normal.y) > abs(normal.x)){
		bumpDif.xy = bumpDif.yx;
	}

	vec3 newNormal = normalize(normalize(normal) + bumpDif * normalMap.w);

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