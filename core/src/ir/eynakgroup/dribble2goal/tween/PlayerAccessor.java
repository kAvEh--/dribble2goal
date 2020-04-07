package ir.eynakgroup.dribble2goal.tween;

import com.badlogic.gdx.math.Vector2;

import aurelienribon.tweenengine.TweenAccessor;
import ir.eynakgroup.dribble2goal.model.Box2dPlayer;

/**
 * Created by kAvEh on 2/27/2016.
 */
public class PlayerAccessor implements TweenAccessor<Box2dPlayer> {

    public int getValues(Box2dPlayer paramPlayerClass, int paramInt, float[] paramArrayOfFloat) {
        switch (paramInt) {
            case 1:
                paramArrayOfFloat[0] = paramPlayerClass.getPosition().x;
                paramArrayOfFloat[1] = paramPlayerClass.getPosition().y;
                return 2;
        }
        return -1;
    }

    public void setValues(Box2dPlayer paramPlayerClass, int paramInt, float[] paramArrayOfFloat) {
        switch (paramInt) {
            case 1:
                paramPlayerClass.setPosition(new Vector2(paramArrayOfFloat[0], paramArrayOfFloat[1]));
        }
    }
}
