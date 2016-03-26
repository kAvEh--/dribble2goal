package ir.eynajgroup.dribble2goal.tween;

import com.badlogic.gdx.graphics.g2d.Sprite;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Created by kAvEh on 2/26/2016.
 */
public class SpriteAccessor implements TweenAccessor<Sprite> {
    public static final int FADE_OUT = 3;
    public static final int POS_X = 7;
    public static final int POS_XY = 1;
    public static final int POS_Y = 8;
    public static final int RESIZE = 4;
    public static final int ROTATE = 6;
    public static final int SET_SCALE = 2;
    public static final int SPIN = 5;

    public int getValues(Sprite paramImage, int paramInt, float[] paramArrayOfFloat) {
        switch (paramInt) {
            case 7:
                paramArrayOfFloat[0] = paramImage.getX();
                return 1;
            case 8:
                paramArrayOfFloat[0] = paramImage.getY();
                return 1;
            case 1:
                paramArrayOfFloat[0] = paramImage.getX();
                paramArrayOfFloat[1] = paramImage.getY();
                return 2;
            case 2:
                paramArrayOfFloat[0] = paramImage.getWidth();
                paramArrayOfFloat[1] = paramImage.getHeight();
                paramArrayOfFloat[2] = paramImage.getOriginX();
                paramArrayOfFloat[3] = paramImage.getOriginY();
                return 4;
            case 3:
                paramArrayOfFloat[0] = paramImage.getColor().a;
                return 1;
            case 4:
                paramArrayOfFloat[0] = paramImage.getX();
                paramArrayOfFloat[1] = paramImage.getY();
                paramArrayOfFloat[2] = paramImage.getWidth();
                paramArrayOfFloat[3] = paramImage.getHeight();
                return 4;
            case 5:
                paramArrayOfFloat[0] = paramImage.getWidth();
                paramArrayOfFloat[1] = paramImage.getHeight();
                paramImage.setOrigin(paramImage.getWidth() / 2.0F, paramImage.getHeight() / 2.0F);
                paramArrayOfFloat[2] = paramImage.getRotation();
                return 3;
            case 6:
                paramArrayOfFloat[0] = paramImage.getRotation();
                return 1;
        }
        return -1;
    }

    public void setValues(Sprite paramImage, int paramInt, float[] paramArrayOfFloat) {
        switch (paramInt) {
            case 7:
                paramImage.setX(paramArrayOfFloat[0]);
                return;
            case 8:
                paramImage.setY(paramArrayOfFloat[0]);
                return;
            case 1:
                paramImage.setX(paramArrayOfFloat[0]);
                paramImage.setY(paramArrayOfFloat[1]);
                return;
            case 2:
                paramImage.setSize(paramArrayOfFloat[0], paramArrayOfFloat[1]);
                paramImage.setOrigin(paramArrayOfFloat[2], paramArrayOfFloat[3]);
                return;
            case 3:
                paramImage.setColor(paramImage.getColor().r, paramImage.getColor().g, paramImage.getColor().b, paramArrayOfFloat[0]);
                return;
            case 4:
                paramImage.setX(paramArrayOfFloat[0]);
                paramImage.setY(paramArrayOfFloat[1]);
                paramImage.setSize(paramArrayOfFloat[2], paramArrayOfFloat[3]);
                return;
            case 5:
                paramImage.setSize(paramArrayOfFloat[0], paramArrayOfFloat[1]);
                paramImage.setOrigin(paramImage.getWidth() / 2.0F, paramImage.getHeight() / 2.0F);
                paramImage.setRotation(paramArrayOfFloat[2]);
                return;
        }
        paramImage.setRotation(paramArrayOfFloat[0]);
    }
}
