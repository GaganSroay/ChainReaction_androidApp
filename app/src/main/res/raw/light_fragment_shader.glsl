precision mediump float;

uniform vec4 a_Color;

varying float diff;
varying float spec;


void main(){

    float ambientStrength = 0.2;
    float specularStrength = 1.0;
    float diffuseStrength = 1.5;
    vec4 ambientColor = vec4(0.7, 0.2, 0.7, 1.0);
    vec4 specColor = vec4(1.0, 1.0, 1.0, 1.0);

    vec4 diffuse = diffuseStrength * diff       *  a_Color;
    vec4 ambient = ambientStrength              *  ambientColor;
    vec4 specular = specularStrength * spec     *  specColor;

    gl_FragColor = ambient + diffuse;
}                          