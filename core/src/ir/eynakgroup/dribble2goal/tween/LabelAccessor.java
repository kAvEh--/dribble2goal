package ir.eynakgroup.dribble2goal.tween;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Created by Eynak_PC2 on 8/7/2016.
 */
public class LabelAccessor implements TweenAccessor<Label> {
    public static final int FADE = 5;
    public static final int ROTATE = 6;
    public static final int SET_SCALE = 2;
    public static final int POS_X = 7;

    public int getValues(Label paramTable, int paramInt, float[] paramArrayOfFloat) {
        switch (paramInt) {
            case 7:
                paramArrayOfFloat[0] = paramTable.getX();
                return 1;
            case 5:
                paramArrayOfFloat[0] = paramTable.getColor().a;
                return 1;
            case 6:
                paramArrayOfFloat[0] = paramTable.getRotation();
                return 1;
            case 2:
                paramArrayOfFloat[0] = paramTable.getWidth();
                paramArrayOfFloat[1] = paramTable.getHeight();
                paramArrayOfFloat[2] = paramTable.getOriginX();
                paramArrayOfFloat[3] = paramTable.getOriginY();
                return 4;
        }
        return -1;
    }

    public void setValues(Label paramTable, int paramInt, float[] paramArrayOfFloat) {
        switch (paramInt) {
            case 7:
                paramTable.setX(paramArrayOfFloat[0]);
                return;
            case 5:
                paramTable.setColor(paramTable.getColor().r,
                        paramTable.getColor().g,
                        paramTable.getColor().b,
                        paramArrayOfFloat[0]);
                return;
            case 6:
                paramTable.setOrigin(paramTable.getWidth() / 2, paramTable.getHeight() / 2);
                paramTable.setRotation(paramArrayOfFloat[0]);
                return;
            case 2:
                paramTable.setWidth(paramArrayOfFloat[0]);
                paramTable.setHeight(paramArrayOfFloat[1]);
                paramTable.setOriginX(paramArrayOfFloat[2]);
                paramTable.setOriginY(paramArrayOfFloat[3]);
                return;
        }
//        paramTable.setOriginX(paramArrayOfFloat[0]);
//        paramTable.setOriginY(paramArrayOfFloat[1]);
    }
}