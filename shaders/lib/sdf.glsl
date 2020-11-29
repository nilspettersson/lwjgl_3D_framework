float sdSphere( vec3 p, float s )
{
  return length(p)-s;
}

float sdplane( vec3 p, float y)
{
  return p.y - y;
}

float sdBox( vec3 p, vec3 b )
{
  vec3 q = abs(p) - b;
  return length(max(q,0.0)) + min(max(q.x,max(q.y,q.z)),0.0);
}



vec3 opRepLim(vec3 p, float c, vec3 l)
{
    p = p - c * clamp(round(p / c), -l, l);
    return p;
}

vec3 opRep( vec3 p, float c )
{
    p = mod( p + 0.5 * c, c) - 0.5 * c;
    return p;
}

float opSmoothUnion( float d1, float d2, float k ) {
    float h = clamp( 0.5 + 0.5 * (d2 - d1) / k, 0.0, 1.0 );
    return mix( d2, d1, h ) - k * h * (1.0 - h);
}