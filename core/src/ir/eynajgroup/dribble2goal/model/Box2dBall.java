package ir.eynajgroup.dribble2goal.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by kAvEh on 2/19/2016.
 */
public class Box2dBall {
    public static final float RADIUS = .3f;
    private static final float DENSITY = .15f;
    private static final float FRICTION = .1f;
    private static final float RESTITUTION = .8f;
    private final Body mBody;

    Box2dBall(World world,
              float x, float y,
              float radius,
              BodyDef.BodyType type,
              float density,
              float friction,
              float restitution) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = type;
        bodyDef.bullet = true;
        bodyDef.allowSleep = false;
        bodyDef.awake = true;

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;

        mBody = world.createBody(bodyDef);
        mBody.setUserData("Ball");

        mBody.createFixture(fixtureDef);

        mBody.setLinearDamping(1.0f);
        mBody.setAngularDamping(0.3f);

        circleShape.dispose();
    }

    Box2dBall(World world, float x, float y) {
        this(world, x, y, RADIUS, BodyDef.BodyType.DynamicBody, DENSITY, FRICTION, RESTITUTION);
    }

    void applyImpulse(float x, float y) {
        Vector2 center = mBody.getWorldCenter();
        mBody.applyLinearImpulse(x, y, center.x, center.y, true);
    }

    void applyForce() {
        if ((Math.abs(this.mBody.getLinearVelocity().x) + Math.abs(this.mBody.getLinearVelocity().y)) / 2.0F < 0.1F) {
            this.mBody.setLinearVelocity(this.mBody.getLinearVelocity().x * 0.8F, this.mBody.getLinearVelocity().y * 0.8F);
        }

//        mBody.setAngularDamping(1f);
    }

    float getAngularVelocity() {
        return mBody.getAngularVelocity();
    }

    public Vector2 getPosition() {
        return mBody.getPosition();
    }

    public void setPosition(Vector2 pos) {
        mBody.setTransform(pos.x, pos.y , 0f);
    }

    Vector2 getVelocity() {
        return mBody.getLinearVelocity();
    }

    void fix() {
        mBody.setLinearVelocity(0, 0);
        mBody.setAngularVelocity(0);
    }
}
