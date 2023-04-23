precision mediump float;

uniform vec4 u_Color;
uniform vec4 u_AmbientColor;

varying float diff;
varying float spec;

vec4 desaturate(vec4 color, float degree) {
    float gray = dot(color.rgb, vec3(0.2126, 0.7152, 0.0722));
    vec3 desaturated = mix(color.rgb, vec3(gray), degree);
    return vec4(desaturated, color.a);
}


void main(){

    float ambientStrength = 1.4;
    float specularStrength = 1.0;
    float diffuseStrength = 1.5;
    vec4 ambientColor = u_AmbientColor * ambientStrength;
    vec4 specColor = u_Color;

    vec4 diffuse = diffuseStrength * diff       *  u_Color;
    vec4 ambient = ambientStrength              *  ambientColor;
    vec4 specular = specularStrength * spec     *  specColor;

    gl_FragColor = ambient + diffuse + specular;
}                          