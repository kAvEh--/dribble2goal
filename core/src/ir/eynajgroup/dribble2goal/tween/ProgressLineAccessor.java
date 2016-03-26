package ir.eynajgroup.dribble2goal.tween;

import aurelienribon.tweenengine.TweenAccessor;
import ir.eynajgroup.dribble2goal.render.textures.ProgressLine;

/**
 * Created by kAvEh on 3/24/2016.
 */
public class ProgressLineAccessor implements TweenAccessor<ProgressLine> {

    public int getValues(ProgressLine paramPlayerClass, int paramInt, float[] paramArrayOfFloat) {
        switch (paramInt) {
            case 1:
                paramArrayOfFloat[0] = paramPlayerClass.getPercent();
                return 1;
        }
        return -1;
    }

    public void setValues(ProgressLine paramPlayerClass, int paramInt, float[] paramArrayOfFloat) {
        switch (paramInt) {
            case 1:
                paramPlayerClass.setPercentage(paramArrayOfFloat[0]);
        }
    }
}
