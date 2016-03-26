package ir.eynajgroup.dribble2goal.template;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by kAvEh on 2/19/2016.
 */
public class Ball {
    private float mRadius;
    private Vector2 mPosition;
    private float angular;

    public Ball() {
        mPosition = new Vector2();
    }

    public Vector2 getPosition() {
        return mPosition;
    }

    public void setPosition(Vector2 position) {
        mPosition = position;
    }

    public float getAngularVelocity() {
        return angular;
    }

    public void setAngularVelocity(float ang) {
        angular = ang;
    }

    public float getRadius() {
        return mRadius;
    }

    public void setRadius(float radius) {
        mRadius = radius;
    }
}
