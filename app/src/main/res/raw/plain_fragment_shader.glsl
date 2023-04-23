precision mediump float;

uniform vec4 u_Color;
uniform sampler2D u_Texture;

varying vec2 v_TexCoordinate;

float getBrightness(vec4 color){
    return (color.r + color.g + color.b)/3.0;
}

vec4 multiplyBlend(vec4 base, vec4 blend) {
    return vec4(base.rgb * blend.rgb, base.a);
}

vec4 darkenBlend(vec4 base, vec4 blend) {
    return vec4(min(base.rgb, blend.rgb), base.a);
}

vec3 rgb2hsl(vec3 color) {
    float min = min(min(color.r, color.g), color.b);
    float max = max(max(color.r, color.g), color.b);
    float l = (max + min) / 2.0;
    float h;
    float s;
    if (max == min) {
        h = 0.0;
        s = 0.0;
    } else {
        float c = max - min;
        s = c / (1.0 - abs(2.0 * l - 1.0));
        if (max == color.r) { h = mod((color.g - color.b) / c, 6.0); }
        else if (max == color.g) { h = (color.b - color.r) / c + 2.0; }
        else { h = (color.r - color.g) / c + 4.0; }
        h /= 6.0;
    }
    return vec3(h, s, l);
}

vec3 hsl2rgb(vec3 c) {
    vec3 rgb = clamp(abs(mod(c.x * 6.0 + vec3(0.0, 4.0, 2.0), 6.0) - 3.0) - 1.0, 0.0, 1.0);
    rgb = rgb * rgb * (3.0 - 2.0 * rgb);
    return c.z + c.y * (rgb - 0.5) * (1.0 - abs(2.0 * c.z - 1.0));
}

vec4 colorBlend(vec4 base, vec4 blend) {
    vec3 baseHSL = rgb2hsl(base.rgb);
    vec3 blendHSL = rgb2hsl(blend.rgb);
    vec3 colorHSL = vec3(blendHSL.rg, baseHSL.b);
    vec3 colorRGB = hsl2rgb(colorHSL);
    return vec4(colorRGB, base.a);
}

void main() {
    vec4 texture = texture2D(u_Texture, v_TexCoordinate);

    vec4 darken = darkenBlend(texture, u_Color);
    vec4 color = colorBlend(darken, u_Color);

    gl_FragColor = color;
}