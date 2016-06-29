package ir.eynajgroup.dribble2goal.tween;

import aurelienribon.tweenengine.TweenAccessor;
import ir.eynajgroup.dribble2goal.Util.SelectGameScrollPane;

/**
 * Created by kAvEh on 3/6/2016.
 */
public class SteperAccessor implements TweenAccessor<SelectGameScrollPane> {
    public static final int SCROLL_Y = 1;

    public int getValues(SelectGameScrollPane paramScrollPane, int paramInt, float[] paramArrayOfFloat) {
        switch (paramInt) {

            case 1:
                paramArrayOfFloat[0] = paramScrollPane.getScrollY();
                return 1;
        }
        return -1;
    }

    public void setValues(SelectGameScrollPane paramScrollPane, int paramInt, float[] paramArrayOfFloat) {
        switch (paramInt) {

            case 1:
                paramScrollPane.scrollandStep(paramArrayOfFloat[0]);
                return;

        }
    }
}
