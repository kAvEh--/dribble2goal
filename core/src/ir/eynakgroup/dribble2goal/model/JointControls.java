package ir.eynakgroup.dribble2goal.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;

import java.util.HashMap;
import java.util.Map;

import ir.eynakgroup.dribble2goal.Constants;
import ir.eynakgroup.dribble2goal.MatchStats;
import ir.eynakgroup.dribble2goal.template.Field;
import ir.eynakgroup.dribble2goal.template.Player;

/**
 * Created by kAvEh on 2/19/2016.
 */
public class JointControls implements ITouchControls {
    private final World mWorld;
    private Map<Integer, MouseJoint> mMouseJoints;
    Field mField;
    public int player = 0;
    Player myPlayer;
    String playerTag;
    MatchStats matchStat;
    private int lastpointer;
    MouseJointDef jointDef;

    JointControls(World world, Field f, MatchStats stat) {
        mWorld = world;
        mMouseJoints = new HashMap<Integer, MouseJoint>();
        mField = f;
        matchStat = stat;
    }

    @Override
    public void sendTouchDown(final float x, final float y, final int pointer) {
        mWorld.QueryAABB(new QueryCallback() {
            @Override
            public boolean reportFixture(Fixture fixture) {
                jointDef = new MouseJointDef();
                jointDef.collideConnected = true;
                jointDef.maxForce = Constants.MAX_MOUSE_JOINT_FORCE;
                jointDef.bodyA = mWorld.createBody(new BodyDef());
                jointDef.bodyB = fixture.getBody();
                jointDef.target.set(x, y);
                if (matchStat.GAME_STATE != Constants.GAME_SHOOTING)
                    return false;
                if (matchStat.half_status == Constants.HALF_PENALTY && matchStat.isMeFirst) {
                    return false;
                }
                if (fixture.getBody().getLinearVelocity().x > 0f || fixture.getBody().getLinearVelocity().y > 0f
                        || fixture.getBody().getUserData() == null) {
                    return false;
                }

                if (fixture.getBody().getUserData().toString().contains("T1")) {
//                    if (myPlayer != null)
//                        if (!fixture.getBody().getUserData().toString().equals(playerTag)) {
//                            if (matchStat.myShootDirection != null && matchStat.myShootDirection.len() != 0) {
//                                return false;
//                            }
//                        }
                    matchStat.myShootDirection = null;
                    matchStat.myPlayerShooting = 0;
                    myPlayer = null;
                    playerTag = "";
                } else {
                    return false;
                }

                if (fixture.getBody().getUserData().toString().equals("T1P1")) {
                    player = 1;
                    myPlayer = mField.getMyPlayer1();
                    playerTag = fixture.getBody().getUserData().toString();
                } else if (fixture.getBody().getUserData().toString().equals("T1P2")) {
                    player = 2;
                    myPlayer = mField.getMyPlayer2();
                    playerTag = fixture.getBody().getUserData().toString();
                } else if (fixture.getBody().getUserData().toString().equals("T1P3")) {
                    player = 3;
                    myPlayer = mField.getMyPlayer3();
                    playerTag = fixture.getBody().getUserData().toString();
                } else if (fixture.getBody().getUserData().toString().equals("T1P4")) {
                    player = 4;
                    myPlayer = mField.getMyPlayer4();
                    playerTag = fixture.getBody().getUserData().toString();
                } else if (fixture.getBody().getUserData().toString().equals("T1P5")) {
                    player = 5;
                    myPlayer = mField.getMyPlayer5();
                    playerTag = fixture.getBody().getUserData().toString();
                }
                matchStat.myPlayerShooting = player;
                mMouseJoints.put(pointer, (MouseJoint) mWorld.createJoint(jointDef));
                lastpointer = pointer;
                return false;
            }
        }, x, y, x, y);
    }

    @Override
    public void sendTouchDragged(float x, float y, int pointer) {
        MouseJoint joint = mMouseJoints.get(pointer);
        lastpointer = pointer;
        if (matchStat.GAME_STATE != Constants.GAME_SHOOTING && joint != null) {
            mWorld.destroyJoint(joint);
            mMouseJoints.remove(pointer);
            return;
        }
        boolean cancelArrowT1 = false;
        if (myPlayer != null && player < 6) {
            float dx = x - myPlayer.getPosition().x;
            float dy = y - myPlayer.getPosition().y;

            float dist = (float) Math.sqrt(dx * dx + dy * dy);
            if (dist < .1f) {
                myPlayer = null;
                playerTag = "";
                cancelArrowT1 = true;
            }
        }

        if (joint != null) {
            if (cancelArrowT1)
                matchStat.myShootDirection = null;
            else
                matchStat.myShootDirection = new Vector2(x, y);
        }
    }

    @Override
    public void sendTouchUp(float x, float y, int pointer) {
        MouseJoint joint = mMouseJoints.get(pointer);
        lastpointer = pointer;
        if (matchStat.GAME_STATE != Constants.GAME_SHOOTING && joint != null) {
            mWorld.destroyJoint(joint);
            mMouseJoints.remove(pointer);
            return;
        }
        boolean cancelArrowT1 = false;
        if (myPlayer != null && player < 4) {
            float dx = x - myPlayer.getPosition().x;
            float dy = y - myPlayer.getPosition().y;

            float dist = (float) Math.sqrt(dx * dx + dy * dy);
            if (dist < .1f) {
                myPlayer = null;
                playerTag = "";
                cancelArrowT1 = true;
            }
        }

        if (joint != null) {
            if (cancelArrowT1)
                matchStat.myShootDirection = null;
            else
                matchStat.myShootDirection = new Vector2(x, y);

            mWorld.destroyJoint(joint);
            mMouseJoints.remove(pointer);
        }
    }

    @Override
    public void destroyTJoint() {
        MouseJoint joint = mMouseJoints.get(lastpointer);
//        System.out.println(mWorld.getJointCount() + "++++++++++++++++++++++++++" + (matchStat.GAME_STATE != Constants.GAME_SHOOTING) +"=="+joint);
        if (joint != null) {
            mWorld.destroyJoint(joint);
            mMouseJoints.remove(lastpointer);
            return;
        }
    }
}