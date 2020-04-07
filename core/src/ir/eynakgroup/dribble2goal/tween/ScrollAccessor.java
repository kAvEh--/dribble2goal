package ir.eynakgroup.dribble2goal.tween;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Created by kAvEh on 3/15/2016.
 */
public class ScrollAccessor implements TweenAccessor<ScrollPane> {
    public static final int SCROLL_X = 1;
    public static final int SCROLL_Y = 3;
    public static final int SCROLL_X_PERCENT = 2;

    public int getValues(ScrollPane paramScrollPane, int paramInt, float[] paramArrayOfFloat) {
        switch (paramInt) {

            case 1:
                paramArrayOfFloat[0] = paramScrollPane.getScrollX();
                return 1;
            case 2:
                paramArrayOfFloat[0] = paramScrollPane.getScrollPercentX();
                return 1;
            case 3:
                paramArrayOfFloat[0] = paramScrollPane.getScrollY();
                return 1;
        }
        return -1;
    }

    public void setValues(ScrollPane paramScrollPane, int paramInt, float[] paramArrayOfFloat) {
        switch (paramInt) {

            case 1:
                paramScrollPane.setScrollX(paramArrayOfFloat[0]);
                return;

            case 3:
                paramScrollPane.setScrollY(paramArrayOfFloat[0]);
                return;

        }
        paramScrollPane.setScrollPercentX(paramArrayOfFloat[0]);
    }
}
