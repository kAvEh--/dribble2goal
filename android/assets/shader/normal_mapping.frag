#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

//attributes from vertex shader
varying LOWP vec4 vColor;
varying vec2 vTexCoord;

//our texture samplers
uniform sampler2D u_texture;   //diffuse map
uniform sampler2D u_normals;   //normal map

//values used for shading algorithm...
uniform vec2 resolution;         //resolution of screen
uniform vec3 lightPos;           //light position, normalized
uniform LOWP vec4 lightColor;    //light RGBA -- alpha is intensity
uniform LOWP vec4 ambientColor;  //ambient RGBA -- alpha is intensity
uniform vec3 falloff;            //attenuation coefficients

void main() {
    //RGBA of our diffuse color
    vec4 diffuseColor = texture2D(u_texture, vTexCoord);

    //RGB of our normal map
    vec3 normalMap = texture2D(u_normals, vTexCoord).rgb;

    //The delta position of light
    vec3 lightDir = vec3(lightPos.xy - (gl_FragCoord.xy / resolution.xy), lightPos.z);

    //Correct for aspect ratio
    lightDir.x *= resolution.x / resolution.y;

    //Determine distance (used for attenuation) BEFORE we normalize our LightDir
    float d = length(lightDir);

    //normalize our vectors
    vec3 n = normalize(normalMap * 2.0 - 1.0);
    vec3 l = normalize(lightDir);

    //Pre-multiply light color with intensity
    //Then perform "n dot l" to determine our diffuse term
    vec3 diffuse = (lightColor.rgb * lightColor.a) * max(dot(n, l), 0.0);

    //pre-multiply ambient color with intensity
    vec3 ambient = ambientColor.rgb * ambientColor.a;

    //calculate attenuation
    float attenuation = 1.0 / (falloff.x + (falloff.y * d) + (falloff.z * d * d));

    //the calculation which brings it all together
    vec3 intensity = ambient + diffuse * attenuation;
    vec3 finalColor = diffuseColor.rgb * intensity;
    gl_FragColor = vColor * vec4(finalColor, diffuseColor.a);
}