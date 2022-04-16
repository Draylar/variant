#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

in float vertexDistance;
in vec4 vertexColor;
in vec4 lightMapColor;
in vec4 overlayColor;
in vec2 texCoord0;
in vec4 normal;

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0) * vertexColor * ColorModulator;
    if (color.a < 0.1) {
        discard;
    }
    vec4 beforeOpacity = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);

    // post-processing after vanilla
    float y = 0.21 * beforeOpacity.x + 0.71 * beforeOpacity.y + 0.07 * beforeOpacity.z;

    // 0.0 is "lack" of color - 0.5 is half color, 1.0 is full color
    vec3 z = beforeOpacity.xyz * (0.0) + y;
    fragColor = vec4(z, 0.5);
}
