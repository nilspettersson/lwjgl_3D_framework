uniforms{

}


fragment{
	//diffuse color
	vec4 color = vec4(texture + color);
	vec4 diffuse = diffuse(color);
	
	//glossy
	vec4 glossy = glossy(color, 0.01);
	
	
	vec4 output = mix(diffuse, glossy, 0.7);
	return output;
	
}