package ir.eynakgroup.dribble2goal.tween;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Created by kAvEh on 3/5/2016.
 */
public class TableAccessor implements TweenAccessor<Table> {
    public static final int FADE = 5;
    public static final int ORIGIN = 7;
    public static final int POPUP = 6;
    public static final int POS_XY = 1;
    public static final int POS_XY_ALFA = 3;
    public static final int POS_XY_COLOR = 4;
    public static final int SET_WIDTH_HEIGHT = 2;

    public int getValues(Table paramTable, int paramInt, float[] paramArrayOfFloat) {
        switch (paramInt) {
            case 1:
                paramArrayOfFloat[0] = paramTable.getX();
                paramArrayOfFloat[1] = paramTable.getY();
                return 2;
            case 2:
                paramArrayOfFloat[0] = paramTable.getWidth();
                paramArrayOfFloat[1] = paramTable.getHeight();
                return 2;
            case 3:
                paramArrayOfFloat[0] = paramTable.getX();
                paramArrayOfFloat[1] = paramTable.getY();
                paramArrayOfFloat[2] = paramTable.getColor().a;
                return 3;
            case 4:
                paramArrayOfFloat[0] = paramTable.getX();
                paramArrayOfFloat[1] = paramTable.getY();
                paramArrayOfFloat[2] = paramTable.getColor().r;
                return 3;
            case 5:
                paramArrayOfFloat[0] = paramTable.getColor().a;
                return 1;
            case 6:
                paramArrayOfFloat[0] = paramTable.getX();
                paramArrayOfFloat[1] = paramTable.getY();
                paramArrayOfFloat[2] = paramTable.getWidth();
                paramArrayOfFloat[3] = paramTable.getHeight();
                return 4;
            case 7:
                paramArrayOfFloat[0] = paramTable.getOriginX();
                paramArrayOfFloat[1] = paramTable.getOriginY();
                return 6;
        }
        return -1;
    }

    public void setValues(Table paramTable, int paramInt, float[] paramArrayOfFloat) {
        switch (paramInt) {
            case 1:
                paramTable.setX(paramArrayOfFloat[0]);
                paramTable.setY(paramArrayOfFloat[1]);
                return;
            case 2:
                paramTable.setWidth(paramArrayOfFloat[0]);
                paramTable.setHeight(paramArrayOfFloat[1]);
                return;
            case 3:
                paramTable.setX(paramArrayOfFloat[0]);
                paramTable.setY(paramArrayOfFloat[1]);
                paramTable.setColor(1.0F, 1.0F, 1.0F, paramArrayOfFloat[2]);
                return;
            case 4:
                paramTable.setX(paramArrayOfFloat[0]);
                paramTable.setY(paramArrayOfFloat[1]);
                paramTable.setColor(paramArrayOfFloat[2], paramArrayOfFloat[2], paramArrayOfFloat[2], 1.0F);
                return;
            case 5:
                paramTable.setColor(paramTable.getColor().r,
                        paramTable.getColor().g,
                        paramTable.getColor().b,
                        paramArrayOfFloat[0]);
                return;
            case 6:
                paramTable.setX(paramArrayOfFloat[0]);
                paramTable.setY(paramArrayOfFloat[1]);
                paramTable.setWidth(paramArrayOfFloat[2]);
                paramTable.setHeight(paramArrayOfFloat[3]);
                paramTable.setOriginX(paramArrayOfFloat[2] / 2.0F);
                paramTable.setOriginY(paramArrayOfFloat[3] / 2.0F);
                return;
        }
        paramTable.setOriginX(paramArrayOfFloat[0]);
        paramTable.setOriginY(paramArrayOfFloat[1]);
    }
}