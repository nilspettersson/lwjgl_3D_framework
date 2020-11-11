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
	vec4 ball = vec4(-30, 30, 0, 50);
	float ballDist = length(point - ball.xyz) - ball.w;
	return ballDist;
}

float getDepth(vec3 point){
	vec4 ball = vec4(-30, 30, 0, 50);
	float ballDist = ball.w - length(point - ball.xyz);
	return ballDist;
}

vec2 rayMarch(vec3 rayOrigin, vec3 rayDir, float maxDepth, float cosA){
	float DistOrigin = 0;
	
	for(int i = 0; i < 100; i++){
		vec3 point = rayOrigin + rayDir * DistOrigin;
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
		vec3 point = rayOrigin + rayDir * depth;
		float dist = getDepth(point);
		depth += dist;
		if(depth > 1000 || dist < 0.01 ){
			break;
		}
	}
	
	return vec2(DistOrigin, max((depth - DistOrigin), 0));
}

vec3 calculateFragementRay(in vec2 fragCoord, in vec3 resolution){
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
    return ray_dir;
}

vec3 mix(vec3 color1, vec3 color2, float mixValue){
	return color1 * mixValue + color2 * (1 - mixValue);
}


void main(){
	tex_coords.y = 1.0 - tex_coords.y;
	
	//gets the color texture.
	vec4 texture=texture2D(sampler[9], tex_coords);
	
	//gets the depth texture
	vec4 depth=texture2D(sampler[10], tex_coords);
	float z = (0.1 * 1000.0) / (1000 - depth.z * (1000 - 0.1));
	
	

	vec3 rayOrigin = cameraPosition;
	rayOrigin.z *=-1;
	
	
	vec3 rayDir = calculateFragementRay(tex_coords, vec3(1.77, 1, 0.72));
	float cosA = rayDir.z;
	
	rayDir = rotate_vector(cameraRotation, rayDir);
	
	
	vec2 rayDis = rayMarch(rayOrigin, rayDir, z, cosA);
	float dis = rayDis.x;
	float rayDepth = rayDis.y;
	
	
	
	if(dis >= 1000){
		gl_FragColor = texture;
	}
	else{
		//z: distance to object.
		//dis: dis is the distance to ray hit.
		float disToObject;
		if(dis > 0){
			disToObject = z - dis * cosA;
		}
		else{
			disToObject = z;
		}
		
		float minDis = min(disToObject, rayDepth);

		minDis = pow(minDis,2);
		minDis *= 0.0004;
		vec3 output = mix(vec3(0, 0, 0), texture.xyz, minDis);
		gl_FragColor = vec4(output, 1);
	}
	
}