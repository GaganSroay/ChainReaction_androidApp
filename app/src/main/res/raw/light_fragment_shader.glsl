precision mediump float;

uniform vec4 u_Color;
uniform vec4 u_AmbientColor;

varying float diff;
varying float spec;


void main(){

    float ambientStrength = 1.2;
    float specularStrength = 1.0;
    float diffuseStrength = 1.5;
    //vec4 ambientColor = vec4(0.7, 0.2, 0.7, 1.0);
    vec4 ambientColor = u_AmbientColor;
    vec4 specColor = u_Color;

    vec4 diffuse = diffuseStrength * diff       *  u_Color;
    vec4 ambient = ambientStrength              *  ambientColor;
    vec4 specular = specularStrength * spec     *  specColor;

    gl_FragColor = ambient + diffuse + specular;
}                          