package ir.eynajgroup.dribble2goal.tween;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import aurelienribon.tweenengine.TweenAccessor;
import ir.eynajgroup.dribble2goal.ParticleEffectActor;

/**
 * Created by Eynak_PC2 on 6/26/2016.
 */
public class EffectAccessor implements TweenAccessor<ParticleEffectActor> {
    public static final int FADE_OUT = 1;

    public int getValues(ParticleEffectActor paramImage, int paramInt, float[] paramArrayOfFloat) {
        switch (paramInt) {
            case 1:
                paramArrayOfFloat[0] = paramImage.getX();
                paramArrayOfFloat[1] = paramImage.getY();
                return 2;
        }
        return -1;
    }

    public void setValues(ParticleEffectActor paramImage, int paramInt, float[] paramArrayOfFloat) {
        switch (paramInt) {
            case 1:
                paramImage.setX(paramArrayOfFloat[0]);
                paramImage.setY(paramArrayOfFloat[1]);
                return;
        }
        paramImage.setRotation(paramArrayOfFloat[0]);
    }
}