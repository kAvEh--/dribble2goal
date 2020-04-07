package ir.eynakgroup.dribble2goal.render.effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by kAvEh on 2/19/2016.
 */
public interface IEffect<T> extends Disposable {
    void draw(SpriteBatch batch, T domainObj);
    void play();
    void stop();
}
