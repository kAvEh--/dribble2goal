package ir.eynakgroup.dribble2goal.tween;

import aurelienribon.tweenengine.TweenAccessor;
import ir.eynakgroup.dribble2goal.render.textures.ProgressLine;

/**
 * Created by kAvEh on 3/24/2016.
 */
public class ProgressLineAccessor implements TweenAccessor<ProgressLine[]> {

    public int getValues(ProgressLine[] paramPlayerClass, int paramInt, float[] paramArrayOfFloat) {
        switch (paramInt) {
            case 1:
                paramArrayOfFloat[0] = paramPlayerClass[0].getPercent();
                return 1;
        }
        return -1;
    }

    public void setValues(ProgressLine[] paramPlayerClass, int paramInt, float[] paramArrayOfFloat) {
        switch (paramInt) {
            case 1:
                paramPlayerClass[0].setPercentage(paramArrayOfFloat[0]);
                paramPlayerClass[1].setPercentage(paramArrayOfFloat[0]);
                paramPlayerClass[0].setColor(1, 1, 1, paramArrayOfFloat[0] / 3);
//                paramPlayerClass[1].setColor(.78f, .29f, .29f, (1 - paramArrayOfFloat[0]) * 1f);
                paramPlayerClass[1].setColor(.78f, .29f, .29f, 1f);
        }
    }
}
