#version 120

uniform sampler2D sampler[20];
uniform vec3 cameraPosition;
uniform vec4 cameraRotation;

in vec2 tex_coords;


vec4 multQuat(vec4 q1, vec4 q2){
	return vec4(
	q1.w * q2.x + q1.x * q2.w + q1.z * q2.y - q1.y * q2.z,
	q1.w * q2.y + q1.y * q2.w + q1.x * q2.z - q1.z * q2.x,
	q1.w * q2.z + q1.z * q2.w + q1.y * q2.x - q1.x * q2.y,
	q1.w * q2.w - q1.x * q2.x - q1.y * q2.y - q1.z * q2.z
	);
}

vec3 rotate_vector( vec4 quat, vec3 vec )
{
	vec4 qv = multQuat( quat, vec4(vec, 0.0) );
	return multQuat( qv, vec4(-quat.x, -quat.y, -quat.z, quat.w) ).xyz;
}



float getDist(vec3 point){
	vec4 ball = vec4(0, 1, 0, 6);
	float ballDist = length(point - ball.xyz) - ball.w;
	return ballDist;
}

float getDepth(vec3 point){
	vec4 ball = vec4(0, 1, 0, 6);
	float ballDist = ball.w - length(point - ball.xyz);
	return ballDist;
}

vec2 rayMarch(vec3 rayOrigin, vec3 rayDir, float maxDepth){
	float DistOrigin = 0;
	
	for(int i = 0; i < 100; i++){
		vec3 point = rayOrigin + rayDir * DistOrigin;
		float dist = getDist(point);
		DistOrigin += dist;
		if(DistOrigin > 1000 || dist < 0.01 ){
			break;
		}
		if(DistOrigin > maxDepth){
			DistOrigin = 1000;
			break;
		}
	}
	
	float depth = DistOrigin + 0.1;
	
	for(int i = 0; i < 100; i++){
		vec3 point = rayOrigin + rayDir * depth;
		float dist = getDepth(point);
		depth += dist;
		if(depth > 1000 || dist < 0.01 ){
			break;
		}
	}
	
	return vec2(DistOrigin, max((depth - DistOrigin), 0));
}


void main(){
	tex_coords.y = 1.0 - tex_coords.y;
	
	//gets the color texture.
	vec4 texture=texture2D(sampler[9], tex_coords);
	
	//gets the depth texture
	vec4 depth=texture2D(sampler[10], tex_coords);
	float z = (0.01 * 10000) / (10000 - depth.x * (10000 - 0.01));
	//z *= 0.01;
	
	
	vec2 res = vec2(1, 1);
	vec2 uv = (tex_coords - 0.5 * res.xy) / res.y;
	vec3 rayOrigin = cameraPosition;
	rayOrigin.z *=-1;
	vec3 rayDir = (vec3(uv.x * 1.77, uv.y, 0.72));
	
	
	rayDir = rotate_vector(cameraRotation, rayDir);
	rayDir = normalize(rayDir);
	
	
	vec2 rayDis = rayMarch(rayOrigin, rayDir, z);
	float dis = rayDis.x;
	float rayDepth = rayDis.y;
	
	if(dis * 0.01 >= 1){
		gl_FragColor = texture;
	}
	else{
		float disToObject = z - dis;
		float minDis = min(disToObject, rayDepth);
		minDis *= 0.1;
		vec3 output = texture.xyz * (1 - minDis) + vec3(1, 1, 1) * minDis;
		gl_FragColor = vec4(output, 1);
	}
	
	
	
	
}