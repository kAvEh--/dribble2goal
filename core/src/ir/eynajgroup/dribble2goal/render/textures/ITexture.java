package ir.eynajgroup.dribble2goal.render.textures;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by kAvEh on 2/19/2016.
 */
public interface ITexture<T> {
    void draw(SpriteBatch batch, T domainObj);

    void draw(SpriteBatch batch, T domainObj, int type);

    void drawStamina(SpriteBatch batch, T domainObj, float delta);
}
