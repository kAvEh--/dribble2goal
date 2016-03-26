package ir.eynajgroup.dribble2goal.template;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by kAvEh on 2/19/2016.
 */
public class Player {
    private float mRadius;
    private Vector2 mPosition;
    private float angular;
    private float angle;
    private float stamina;

    public Player() {
        mPosition = new Vector2();
        angle = 0.0f;
        stamina = 1f;
    }

    public Vector2 getPosition() {
        return mPosition;
    }

    public float getAngularVelocity() {
        return angular;
    }

    public void setAngularVelocity(float ang) {
        angular = ang;
    }

    public void setPosition(Vector2 position) {
        mPosition = position;
    }

    public float getRadius() {
        return mRadius;
    }

    public void setRadius(float radius) {
        mRadius = radius;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float ang) {
        angle = (angle + ang) % 360;
    }

    public float getStamina() {
        return stamina;
    }

    public void setStamina(float stamina) {
        this.stamina = stamina;
    }
}
