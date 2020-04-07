package ir.eynakgroup.dribble2goal.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by kAvEh on 2/19/2016.
 */
public class Box2dRect {

    protected final Body mBody;

    Box2dRect(World world,
              float x, float y,
              float width, float height,
              BodyDef.BodyType type,
              float density,
              float friction,
              float restitution) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x + width / 2, y + height / 2);
        bodyDef.type = type;

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width / 2, height / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;

        mBody = world.createBody(bodyDef);
        mBody.createFixture(fixtureDef);

        polygonShape.dispose();
    }

    Box2dRect(World world,
              float x, float y,
              float width, float height,
              BodyDef.BodyType type,
              float density,
              float friction,
              float restitution,
              boolean is5dot5) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x + width / 2, y + height / 2);
        bodyDef.type = type;

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width / 2, height / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;

        mBody = world.createBody(bodyDef);
        mBody.createFixture(fixtureDef);
        mBody.setUserData("wall");

        polygonShape.dispose();
    }

    public Vector2 getPosition() {
        return mBody.getPosition();
    }
}
