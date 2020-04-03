package ir.eynakgroup.dribble2goal.tween;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Created by Eynak_PC2 on 8/7/2016.
 */
public class ModelAccessor implements TweenAccessor<ModelInstance> {
    public static final int rotate = 1;

    public int getValues(ModelInstance paramTable, int paramInt, float[] paramArrayOfFloat) {
        switch (paramInt) {
            case 1:
                paramArrayOfFloat[0] = paramTable.transform.getScaleY();
                return 1;
            case 2:
                paramArrayOfFloat[0] = paramTable.transform.getScaleY();
                return 1;
        }
        return -1;
    }

    public void setValues(ModelInstance paramTable, int paramInt, float[] paramArrayOfFloat) {
        switch (paramInt) {
            case 1:
//                paramTable.transform.setToTranslation(0f, paramArrayOfFloat[0], 4f);
                paramTable.transform.setToRotation(Vector3.X, paramArrayOfFloat[0]);
                return;
            case 2:
//                paramTable.transform.setToTranslation(0f, paramArrayOfFloat[0], 4f);
                paramTable.transform.setToRotation(Vector3.Y, paramArrayOfFloat[0]);
                return;
        }
    }
}