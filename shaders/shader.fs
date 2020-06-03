#version 120

uniform sampler2D sampler[20];

uniform vec3 lightColors[128];
uniform vec3 lightPositions[128];

in vec2 tex_coords;
in vec4 color;
in float textureId;
in vec3 normal;


in vec3 toCamera;
in vec4 worldPosition;

vec4 diffuse(vec4 color){
	vec3 allLight = vec3(0, 0, 0);
	for(int i = 0; i < 128; i++){
		vec3 toLight = lightPositions[i] - worldPosition.xyz;
		float disToLight = length(toLight)/10;
		
		float brightness = dot(normalize(normal), normalize(toLight));
		brightness = max(brightness, 0.01);
		brightness /= disToLight;
		
		vec3 light = lightColors[i] * brightness;
		allLight += light;
	}
	
	vec4 diffuse = color;
	diffuse.xyz *= allLight;
	return diffuse;
}

vec4 glossy(vec4 color){
	vec3 allLight = vec3(0, 0, 0);
	for(int i = 0; i < 128; i++){
		vec3 toLight = lightPositions[i] - worldPosition.xyz;
		float disToLight = length(toLight)/10;
		
		vec3 reflectedLight = reflect(normalize(toLight), normalize(normal));
		
		float reflectedDot = dot(normalize(toCamera), reflectedLight);
		reflectedDot = smoothstep(-2, 1, reflectedDot);
		
		float roughness = 0.001;
		float brightness = pow(reflectedDot, 1 / roughness) / normalize(roughness);
		//brightness = max(brightness, 0.05);
		//brightness /= disToLight;
		
		vec3 light = lightColors[i] * brightness;
		allLight += light;
	}
	
	
	vec4 glossy = vec4(1);
	glossy.xyz *= allLight;
	return glossy;
}

void main(){
	float depth = gl_FragCoord.w*4;
	
		
	int id = int(textureId);
	vec4 texture=texture2D(sampler[id], tex_coords);
	
	
	//solidView lighting
	float SolidBrightness = dot(normalize(normal), normalize(toCamera));
	SolidBrightness = max(SolidBrightness, 0.3);
	
	//array of all vectors pointing to lights.
	
	
	
	
	
	
	
	
	//diffuse color
	vec4 color = (texture + color);
	vec4 diffuseColor = diffuse(color);
	
	//glossy
	vec4 glossy = glossy(color);
	
	
	vec4 output = glossy;
	gl_FragColor=output;
	
	
	//gl_FragColor = vec4(brightness, brightness, brightness,1);
	//gl_FragColor = vec4(depth,depth,depth, 1);

	
}