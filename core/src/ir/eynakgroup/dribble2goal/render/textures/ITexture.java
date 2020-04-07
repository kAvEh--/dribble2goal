package ir.eynakgroup.dribble2goal.render.textures;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by kAvEh on 2/19/2016.
 */
public interface ITexture<T> {
    void draw(SpriteBatch batch, T domainObj);

    void draw(SpriteBatch batch, T domainObj, int type, int num);

    void drawStamina(SpriteBatch batch, int num, T domainObj, Camera camera);
}
