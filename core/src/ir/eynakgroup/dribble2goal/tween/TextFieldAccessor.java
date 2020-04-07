package ir.eynakgroup.dribble2goal.tween;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Created by kAvEh on 3/15/2016.
 */
public class TextFieldAccessor implements TweenAccessor<TextField> {
    public static final int SUMMER = 1;

    public int getValues(TextField paramScrollPane, int paramInt, float[] paramArrayOfFloat) {
        switch (paramInt) {

            case 1:
                paramArrayOfFloat[0] = Integer.parseInt(paramScrollPane.getText());
                return 1;
        }
        return -1;
    }

    public void setValues(TextField paramScrollPane, int paramInt, float[] paramArrayOfFloat) {
        switch (paramInt) {

            case 1:
                paramScrollPane.setText((int) paramArrayOfFloat[0] + "");
                return;

        }
    }
}
