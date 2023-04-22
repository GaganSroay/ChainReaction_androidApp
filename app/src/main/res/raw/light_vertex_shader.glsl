uniform mat4 u_MVPMatrix;
uniform mat4 u_MVMatrix;
uniform vec3 u_LightPos;

attribute vec4 a_Position;
//attribute vec4 a_Color;
attribute vec3 a_Normal;
//attribute vec2 a_TexCoordinate;

varying vec4 col;
//varying vec4 v_Color;
//varying vec2 v_TexCoordinate;
varying vec3 lightVector;

varying float diff;
varying float spec;



void main(){

    vec3 modelViewVertex = vec3(u_MVMatrix * a_Position);
    vec3 modelViewNormal = vec3(u_MVMatrix * vec4(a_Normal, 0.0));

    lightVector = normalize(u_LightPos - modelViewVertex);
    vec3 diffLightDir = vec3(1.0, 1.0, 3.0);

    float distance = length(u_LightPos - modelViewVertex);
    diff = max(dot(modelViewNormal, normalize(diffLightDir)), 0.05);
    //diff = diff * (1.0 / (1.0 + (0.25 * distance * distance)));

    vec3 specDirection = normalize(vec3(1.0, 1.0, 0.3));
    vec3 reflectDir = reflect(-lightVector, modelViewNormal);
    spec = pow(max(dot(specDirection, reflectDir), 0.05), 10.0);


    gl_Position = u_MVPMatrix * a_Position;
}