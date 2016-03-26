package ir.eynajgroup.dribble2goal.model;

import com.badlogic.gdx.utils.Disposable;

import ir.eynajgroup.dribble2goal.template.Field;

/**
 * Created by kAvEh on 2/19/2016.
 */
public interface IRenderer extends Disposable {
    void render();
    void updateModel(Field model);
    void playWinEffect();
    void stopWinEffect();
}
