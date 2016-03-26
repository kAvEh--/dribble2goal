package ir.eynajgroup.dribble2goal.render.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * Created by kAvEh on 2/19/2016.
 */
public class ShaderHelper {
    public static ShaderProgram loadShader(String name) {
        FileHandle vert = Gdx.files.internal("shader/" + name + ".vert");
        FileHandle frag = Gdx.files.internal("shader/" + name + ".frag");
        ShaderProgram shader = new ShaderProgram(vert, frag);
        if (!shader.isCompiled()) {
            throw new GdxRuntimeException("Could not compile shader: " + shader.getLog());
        }
        return shader;
    }
}
