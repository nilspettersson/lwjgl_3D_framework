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


float getDist(vec3 point){
	vec4 ball = vec4(0, 1, 6, 1);
	float ballDist = length(point - ball.xyz) - ball.w;
	return ballDist;
}

float rayMarch(vec3 rayOrigin, vec3 rayDir){
	float DistOrigin = 0;
	
	for(int i = 0; i < 100; i++){
		vec3 point = rayOrigin + rayDir * DistOrigin;
		float dist = getDist(point);
		DistOrigin += dist;
		if(DistOrigin > 1000 || dist < 0.01 ){
			break;
		}
	}
	return DistOrigin;
}


void main(){
	
	tex_coords.y = 1.0 - tex_coords.y;
	
	
	//gets the color texture.
	vec4 texture=texture2D(sampler[9], tex_coords);
	
	//gets the depth texture
	vec4 depth=texture2D(sampler[10], tex_coords);
	float z = (0.01 * 10000) / (10000 - depth.x * (10000 - 0.01));
	z *= 0.01;
	
	
	vec2 res = vec2(1, 1);
	vec2 uv = (tex_coords - 0.5 * res.xy) / res.y;
	
	vec3 rayOrigin = vec3(0, 1, 0);
	vec3 rayDir = normalize(vec3(uv.x * 1.77, uv.y, 1));
	
	float dis = rayMarch(rayOrigin, rayDir);
	
	dis /= 22;
	if(dis >= 1){
		gl_FragColor = texture;
	}
	else{
		gl_FragColor = vec4(dis, dis, dis,1);
	}
	
	
	
}