package ir.eynakgroup.dribble2goal.tween;

import aurelienribon.tweenengine.TweenAccessor;
import ir.eynakgroup.dribble2goal.render.textures.ProgressCircle;

/**
 * Created by kAvEh on 3/13/2016.
 */
public class ProgressCircleAccessor implements TweenAccessor<ProgressCircle> {

    public int getValues(ProgressCircle paramPlayerClass, int paramInt, float[] paramArrayOfFloat) {
        switch (paramInt) {
            case 1:
                paramArrayOfFloat[0] = paramPlayerClass.getPercent();
                return 1;
        }
        return -1;
    }

    public void setValues(ProgressCircle paramPlayerClass, int paramInt, float[] paramArrayOfFloat) {
        switch (paramInt) {
            case 1:
                paramPlayerClass.setPercentage(paramArrayOfFloat[0]);
        }
    }
}
