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
public class Box2dPlayer {
    public static float RADIUS = 0.4f;
    public static final float RADIUS_GOALER = 0.3f;
    private static final float DENSITY = .43f;
    private static float FRICTION = .5f;
    private static final float RESTITUTION = .3f;
    private final Body mBody;
    private static float goalerX = .9f;
    private float stamina = 1f;
    private double distance;

    private float bodyRadius;
    private float staminaCo;

    public String name = "";

    Box2dPlayer(World world,
                float x, float y,
                float radius,
                BodyDef.BodyType type,
                float density,
                float friction,
                float restitution,
                String tag,
                float st) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = type;
        bodyDef.bullet = true;
        bodyDef.allowSleep = false;
        bodyDef.awake = true;

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(radius);

        this.bodyRadius = radius;
        this.staminaCo = st;

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;

        mBody = world.createBody(bodyDef);
        mBody.setUserData(tag);

        mBody.setLinearDamping(1.4f);
        mBody.setAngularDamping(3.0f);

        name = tag;
        mBody.createFixture(fixtureDef);

        circleShape.dispose();
    }

    public float getRadius() {
        return this.bodyRadius;
    }

    Box2dPlayer(World world, float x, float y, float size, String tag, float st) {
        this(world, x, y, RADIUS * size, BodyDef.BodyType.DynamicBody, DENSITY, FRICTION, RESTITUTION, tag, st);
    }

    Box2dPlayer(World world, float x, float y, String tag, boolean isGoaler) {
        this(world, x, y, RADIUS_GOALER, BodyDef.BodyType.DynamicBody, DENSITY, FRICTION, RESTITUTION, tag, 1f);
        this.goalerX = x;
    }

    void applyImpulse(float x, float y) {
        Vector2 center = mBody.getWorldCenter();
        mBody.applyLinearImpulse(x * stamina, y * stamina, center.x, center.y, true);

        stamina = Math.max(stamina - ((1.1f - stamina) * .4f) / (1f + (staminaCo - 1) / 10), .3f);
    }

    void applyForce() {
        if ((Math.abs(this.mBody.getLinearVelocity().x) + Math.abs(this.mBody.getLinearVelocity().y)) / 2.0F < 0.1F) {
            this.mBody.setLinearVelocity(this.mBody.getLinearVelocity().x * 0.8F, this.mBody.getLinearVelocity().y * 0.8F);
        }
    }

    void applyForceGoaler() {
        Vector2 vel = mBody.getLinearVelocity();
        vel.x = 0f;
        mBody.setLinearVelocity(vel);
        vel = mBody.getLinearVelocity();
        vel.x = goalerX;
        vel.y = mBody.getPosition().y;
        mBody.setTransform(vel, mBody.getAngle());
    }

    public double getDiameter() {
        double x = mBody.getLinearVelocity().x;
        double y = mBody.getLinearVelocity().y;
        return Math.sqrt(x * x + y * y);
    }

    float getAngularVelocity() {
        return mBody.getAngularVelocity();
    }

    public Vector2 getPosition() {
        return mBody.getPosition();
    }

    public void setPosition(Vector2 pos) {
        mBody.setTransform(pos.x, pos.y, 0f);
    }

    float getStamina() {
        return stamina;
    }

    double getDistance() {
        return distance;
    }

    public void setDistance(double d) {
        distance = d;
    }

    Vector2 getVelocity() {
        return mBody.getLinearVelocity();
    }

    void fix() {
        mBody.setLinearVelocity(0f, 0f);
        mBody.setAngularVelocity(0f);
    }
}
