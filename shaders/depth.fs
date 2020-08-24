#version 120

uniform sampler2D sampler[20];

uniform vec3 lightColors[128];
uniform vec3 lightPositions[128];
uniform float lightIntensity[128];
uniform int lightCount;

in vec2 tex_coords;
in vec4 color;
in float textureId;
in vec3 normal;


in vec3 toCamera;
in vec4 worldPosition;

vec4 diffuse(vec4 color){
	vec3 allLight = vec3(0, 0, 0);
	for(int i = 0; i < lightCount; i++){
		vec3 toLight = lightPositions[i] - worldPosition.xyz;
		float disToLight = length(toLight)/8;
		
		float brightness = dot(normalize(normal), normalize(toLight));
		brightness = max(brightness, 0);
		float attenuation = lightIntensity[i] / (4.0 + 1*disToLight + 1 * disToLight * disToLight);
		//brightness /= disToLight;
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

void main(){

	float depth = gl_FragCoord.w*4;
	
		
	int id = int(textureId);
	vec4 texture=texture2D(sampler[id], tex_coords);
	
	
	//solidView lighting
	float SolidBrightness = dot((-normal), normalize(toCamera));
	SolidBrightness = max(SolidBrightness, 0.3);
	

	
	
	//diffuse color
	vec4 color = vec4(texture);
	//vec4 color = color;
	vec4 diffuse = diffuse(color);
	
	//glossy
	vec4 glossy = glossy(color, 0.1);
	
	
	vec4 output = mix(diffuse, glossy, 0.7);
	//gl_FragColor = output;
	
	
	//gl_FragColor = vec4(brightness, brightness, brightness,1);
	gl_FragColor = vec4(depth,depth,depth, 1);

	
}