package ir.eynakgroup.dribble2goal.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import ir.eynakgroup.dribble2goal.Assets;
import ir.eynakgroup.dribble2goal.Constants;

/**
 * Created by kAvEh on 2/19/2016.
 */
public class Box2dWalls {
    public static final float HORIZONTAL_GAP = 1.185f;
    public static final float GOALER_GAP = 1.25f;
    public static final float VERTICAL_GAP = .84f;
    public static final float ADD_GOAL_GAP = .09f;
    public static final float GOAL_HEIGHT = 3.06f;
    public static final float GOAL_GAP = 2.37f;
    private static final float WALL_WIDTH = .1f;
    private static final float DENSITY = 1000f;
    private static final float FRICTION = 100f;
    private static final float RESTITUTION = 0f;
    static Texture mTexture;

    static void create(World world) {
        // ---- Bottom Horizontal Line
        new Box2dRect(world,
                -Constants.SCREEN_WIDTH / 2 + HORIZONTAL_GAP, -Constants.SCREEN_HEIGHT / 2 + VERTICAL_GAP,
                Constants.SCREEN_WIDTH - HORIZONTAL_GAP * 2f, WALL_WIDTH,
                BodyDef.BodyType.StaticBody, DENSITY, FRICTION, RESTITUTION);

        // ---- Top Horizontal Line
        new Box2dRect(world,
                -Constants.SCREEN_WIDTH / 2 + HORIZONTAL_GAP, Constants.SCREEN_HEIGHT / 2 - VERTICAL_GAP,
                Constants.SCREEN_WIDTH - HORIZONTAL_GAP * 2f, WALL_WIDTH,
                BodyDef.BodyType.StaticBody, DENSITY, FRICTION, RESTITUTION);

        // ---- LEFT Vertical Line
        new Box2dRect(world,
                -Constants.SCREEN_WIDTH / 2 + HORIZONTAL_GAP, -Constants.SCREEN_HEIGHT / 2 + VERTICAL_GAP,
                WALL_WIDTH, Constants.SCREEN_HEIGHT - VERTICAL_GAP * 2,
                BodyDef.BodyType.StaticBody, DENSITY, FRICTION, RESTITUTION);

        // ----- RIGHT Top Vertical Line
        new Box2dRect(world,
                Constants.SCREEN_WIDTH / 2 - HORIZONTAL_GAP - ADD_GOAL_GAP, 1.48f,
                WALL_WIDTH, GOAL_GAP,
                BodyDef.BodyType.StaticBody, DENSITY, FRICTION, RESTITUTION);

        // ----- RIGHT Bottom Vertical Line
        new Box2dRect(world,
                Constants.SCREEN_WIDTH / 2 - HORIZONTAL_GAP - ADD_GOAL_GAP, -Constants.SCREEN_HEIGHT / 2 + VERTICAL_GAP,
                WALL_WIDTH, Constants.SCREEN_HEIGHT / 2 - VERTICAL_GAP - GOAL_HEIGHT / 2,
                BodyDef.BodyType.StaticBody, DENSITY, FRICTION, RESTITUTION);

        // ----- RIGHT Middle Vertical Line
        new Box2dRect(world,
                Constants.SCREEN_WIDTH / 2 - WALL_WIDTH, -GOAL_HEIGHT / 2,
                WALL_WIDTH, GOAL_HEIGHT,
                BodyDef.BodyType.StaticBody, DENSITY, FRICTION, RESTITUTION);

        // ----- Top Horizontal Goal Line
        new Box2dRect(world,
                Constants.SCREEN_WIDTH / 2 - HORIZONTAL_GAP - ADD_GOAL_GAP, 1.48f,
                HORIZONTAL_GAP + ADD_GOAL_GAP, WALL_WIDTH,
                BodyDef.BodyType.StaticBody, DENSITY, FRICTION, RESTITUTION);

        // ----- Bottom Horizontal Goal Line
        new Box2dRect(world,
                Constants.SCREEN_WIDTH / 2 - HORIZONTAL_GAP - ADD_GOAL_GAP, -GOAL_HEIGHT / 2,
                HORIZONTAL_GAP + WALL_WIDTH + ADD_GOAL_GAP, WALL_WIDTH,
                BodyDef.BodyType.StaticBody, DENSITY, FRICTION, RESTITUTION);

        // ---- Vertical 5.5m wall
        new Box2dRect(world,
                Constants.SCREEN_WIDTH / 2 - 1.25f,  -(GOAL_GAP * 1f)/2,
                WALL_WIDTH, GOAL_GAP * 1f,
                BodyDef.BodyType.StaticBody, DENSITY, FRICTION, RESTITUTION, true);

        // ---- Top Horizontal 5.5m wall
//        new Box2dRect(world,
//                Constants.SCREEN_WIDTH / 2 - 2.77f, (GOAL_GAP * 1.5f)/2,
//                2.77f, WALL_WIDTH,
//                BodyDef.BodyType.StaticBody, DENSITY, FRICTION, RESTITUTION, true);
//
//        // ---- Bottom Horizontal 5.5m wall
//        new Box2dRect(world,
//                Constants.SCREEN_WIDTH / 2 - 2.77f, -(GOAL_GAP * 1.5f)/2,
//                2.77f, WALL_WIDTH,
//                BodyDef.BodyType.StaticBody, DENSITY, FRICTION, RESTITUTION, true);
    }

    public static void createBatch(SpriteBatch batch) {
        mTexture = Assets.getInstance().test_image;

        // ---- Vertical 5.5m wall
        batch.draw(mTexture,
                Constants.SCREEN_WIDTH / 2 - 1.25f,  -(GOAL_GAP * 1f)/2,
                WALL_WIDTH, GOAL_GAP * 1f);

        // ---- Top Horizontal 5.5m wall
//        batch.draw(mTexture,
//                Constants.SCREEN_WIDTH / 2 - 2f, (GOAL_GAP * 1.2f)/2,
//                2f, WALL_WIDTH);
//
//        // ---- Bottom Horizontal 5.5m wall
//        batch.draw(mTexture,
//                Constants.SCREEN_WIDTH / 2 - 2f, -(GOAL_GAP * 1.2f)/2,
//                2f, WALL_WIDTH);

        /* ----- Right Top Vertical Line
        batch.draw(mTexture,
                Constants.SCREEN_WIDTH / 2 - HORIZONTAL_GAP - ADD_GOAL_GAP, 1.48f,
                WALL_WIDTH, GOAL_GAP);

        // ----- Right Bottom Vertical Line
        batch.draw(mTexture,
                Constants.SCREEN_WIDTH / 2 - HORIZONTAL_GAP - ADD_GOAL_GAP, -Constants.SCREEN_HEIGHT / 2 + VERTICAL_GAP,
                WALL_WIDTH, Constants.SCREEN_HEIGHT / 2 - VERTICAL_GAP - GOAL_HEIGHT / 2);

        // ----- Right Middle Vertical Line
        batch.draw(mTexture,
                Constants.SCREEN_WIDTH / 2 - WALL_WIDTH, -GOAL_HEIGHT / 2,
                WALL_WIDTH, GOAL_HEIGHT);

        // ----- Top Horizontal Goal Line
        batch.draw(mTexture,
                Constants.SCREEN_WIDTH / 2 - HORIZONTAL_GAP - ADD_GOAL_GAP, 1.48f,
                HORIZONTAL_GAP + ADD_GOAL_GAP, WALL_WIDTH);

        // ----- Bottom Horizontal Goal Line
        batch.draw(mTexture,
                Constants.SCREEN_WIDTH / 2 - HORIZONTAL_GAP - ADD_GOAL_GAP, -GOAL_HEIGHT / 2,
                HORIZONTAL_GAP + WALL_WIDTH + ADD_GOAL_GAP, WALL_WIDTH);

                */
    }
}
