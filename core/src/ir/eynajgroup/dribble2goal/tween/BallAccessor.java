package ir.eynajgroup.dribble2goal.tween;

import com.badlogic.gdx.math.Vector2;

import aurelienribon.tweenengine.TweenAccessor;
import ir.eynajgroup.dribble2goal.model.Box2dBall;

/**
 * Created by kAvEh on 2/27/2016.
 */
public class BallAccessor implements TweenAccessor<Box2dBall> {

    public int getValues(Box2dBall paramPlayerClass, int paramInt, float[] paramArrayOfFloat) {
        switch (paramInt) {
            case 1:
                paramArrayOfFloat[0] = paramPlayerClass.getPosition().x;
                paramArrayOfFloat[1] = paramPlayerClass.getPosition().y;
                return 2;
        }
        return -1;
    }

    public void setValues(Box2dBall paramPlayerClass, int paramInt, float[] paramArrayOfFloat) {
        switch (paramInt) {
            case 1:
                paramPlayerClass.setPosition(new Vector2(paramArrayOfFloat[0], paramArrayOfFloat[1]));
        }
    }
}
