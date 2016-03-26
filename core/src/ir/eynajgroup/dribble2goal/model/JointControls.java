package ir.eynajgroup.dribble2goal.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;

import java.util.HashMap;
import java.util.Map;

import ir.eynajgroup.dribble2goal.Constants;
import ir.eynajgroup.dribble2goal.MatchStats;
import ir.eynajgroup.dribble2goal.template.Field;
import ir.eynajgroup.dribble2goal.template.Player;

/**
 * Created by kAvEh on 2/19/2016.
 */
public class JointControls implements ITouchControls {
    private final World mWorld;
    private Map<Integer, MouseJoint> mMouseJoints;
    Field mField;
    public int player = 0;
    Player myPlayer;
    Player otherPlayer;
    String playerTag;
    String otherTag;
    MatchStats gameStat;

    JointControls(World world, Field f, MatchStats stat) {
        mWorld = world;
        mMouseJoints = new HashMap<Integer, MouseJoint>();
        mField = f;
        gameStat = stat;
    }

    @Override
    public void sendTouchDown(final float x, final float y, final int pointer) {
        System.out.println("hereeee");
        mWorld.QueryAABB(new QueryCallback() {
            @Override
            public boolean reportFixture(Fixture fixture) {
                MouseJointDef jointDef = new MouseJointDef();
                jointDef.collideConnected = true;
                jointDef.maxForce = Constants.MAX_MOUSE_JOINT_FORCE;
                jointDef.bodyA = mWorld.createBody(new BodyDef());
                jointDef.bodyB = fixture.getBody();
                jointDef.target.set(x, y);
                if (gameStat.GAME_STATE != Constants.GAME_SHOOTING)
                    return false;
                if (fixture.getBody().getLinearVelocity().x > 0f || fixture.getBody().getLinearVelocity().y > 0f
                        || fixture.getBody().getUserData() == null) {
                    return false;
                }

                System.out.println(fixture.getBody().getUserData().toString().contains("T1") + "===");
                if (fixture.getBody().getUserData().toString().contains("T1")) {
                    if (myPlayer != null)
                        if (fixture.getBody().getUserData().toString() != playerTag) {
                            if (playerTag == "T1P1") {
                                if (mField.getT1p1Arrow() != null && mField.getT1p1Arrow().len() != 0) {
                                    return false;
                                }
                            } else if (playerTag == "T1P2") {
                                if (mField.getT1p2Arrow() != null && mField.getT1p2Arrow().len() != 0) {
                                    return false;
                                }
                            } else if (playerTag == "T1P3") {
                                if (mField.getT1p3Arrow() != null && mField.getT1p3Arrow().len() != 0) {
                                    return false;
                                }
                            }
                        }
                    myPlayer = null;
                    playerTag = "";
                } else {
                    if (otherPlayer != null)
                        if (fixture.getBody().getUserData().toString() != otherTag) {
                            if (playerTag == "T2P1") {
                                if (mField.getT2p1Arrow() != null && mField.getT2p1Arrow().len() != 0) {
                                    return false;
                                }
                            } else if (playerTag == "T2P2") {
                                if (mField.getT2p2Arrow() != null && mField.getT2p2Arrow().len() != 0) {
                                    return false;
                                }
                            } else if (playerTag == "T2P3") {
                                if (mField.getT2p3Arrow() != null && mField.getT2p3Arrow().len() != 0) {
                                    return false;
                                }
                            }
                        }
                    otherPlayer = null;
                    otherTag = "";
                }

                if (fixture.getBody().getUserData().toString() == "T1P1") {
                    mField.setLeftDragged(true);
                    player = 1;
                    myPlayer = mField.getT1Player1();
                    playerTag = fixture.getBody().getUserData().toString();
                } else if (fixture.getBody().getUserData().toString() == "T1P2") {
                    mField.setLeftDragged(true);
                    player = 2;
                    myPlayer = mField.getT1Player2();
                    playerTag = fixture.getBody().getUserData().toString();
                } else if (fixture.getBody().getUserData().toString() == "T1P3") {
                    mField.setLeftDragged(true);
                    player = 3;
                    myPlayer = mField.getT1Player3();
                    playerTag = fixture.getBody().getUserData().toString();
                } else if (fixture.getBody().getUserData().toString() == "T2P1") {
                    mField.setRightDragged(true);
                    player = 4;
                    otherPlayer = mField.getT2Player1();
                    otherTag = fixture.getBody().getUserData().toString();
                } else if (fixture.getBody().getUserData().toString() == "T2P2") {
                    mField.setRightDragged(true);
                    player = 5;
                    otherPlayer = mField.getT2Player2();
                    otherTag = fixture.getBody().getUserData().toString();
                } else if (fixture.getBody().getUserData().toString() == "T2P3") {
                    mField.setRightDragged(true);
                    player = 6;
                    otherPlayer = mField.getT2Player3();
                    otherTag = fixture.getBody().getUserData().toString();
                }
                mMouseJoints.put(pointer, (MouseJoint) mWorld.createJoint(jointDef));
                return false;
            }
        }, x, y, x, y);
    }

    @Override
    public void sendTouchDragged(float x, float y, int pointer) {
        boolean cancelArrowT1 = false;
        boolean cancelArrowT2 = false;
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
        if (otherPlayer != null && player > 3) {
            float dx = x - otherPlayer.getPosition().x;
            float dy = y - otherPlayer.getPosition().y;

            float dist = (float) Math.sqrt(dx * dx + dy * dy);
            if (dist < .1f) {
                otherPlayer = null;
                otherTag = "";
                cancelArrowT2 = true;
            }
        }

        MouseJoint joint = mMouseJoints.get(pointer);
        if (joint != null) {
            switch (player) {
                case 1:
                    if (cancelArrowT1)
                        mField.setT1p1Arrow(null);
                    else
                        mField.setT1p1Arrow(new Vector2(x, y));
                    break;
                case 2:
                    if (cancelArrowT1)
                        mField.setT1p2Arrow(null);
                    else
                        mField.setT1p2Arrow(new Vector2(x, y));
                    break;
                case 3:
                    if (cancelArrowT1)
                        mField.setT1p3Arrow(null);
                    else
                        mField.setT1p3Arrow(new Vector2(x, y));
                    break;
                case 4:
                    if (cancelArrowT2)
                        mField.setT2p1Arrow(null);
                    else
                        mField.setT2p1Arrow(new Vector2(x, y));
                    break;
                case 5:
                    if (cancelArrowT2)
                        mField.setT2p2Arrow(null);
                    else
                        mField.setT2p2Arrow(new Vector2(x, y));
                    break;
                case 6:
                    if (cancelArrowT2)
                        mField.setT2p3Arrow(null);
                    else
                        mField.setT2p3Arrow(new Vector2(x, y));
                    break;
            }
        }
    }

    @Override
    public void sendTouchUp(float x, float y, int pointer) {
        boolean cancelArrowT1 = false;
        boolean cancelArrowT2 = false;
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
        if (otherPlayer != null && player > 3) {
            float dx = x - otherPlayer.getPosition().x;
            float dy = y - otherPlayer.getPosition().y;

            float dist = (float) Math.sqrt(dx * dx + dy * dy);
            if (dist < .1f) {
                otherPlayer = null;
                otherTag = "";
                cancelArrowT2 = true;
            }
        }

        MouseJoint joint = mMouseJoints.get(pointer);

        if (joint != null) {
            switch (player) {
                case 1:
                    if (cancelArrowT1)
                        mField.setT1p1Arrow(null);
                    else
                        mField.setT1p1Arrow(new Vector2(x, y));
                    break;
                case 2:
                    if (cancelArrowT1)
                        mField.setT1p2Arrow(null);
                    else
                        mField.setT1p2Arrow(new Vector2(x, y));
                    break;
                case 3:
                    if (cancelArrowT1)
                        mField.setT1p3Arrow(null);
                    else
                        mField.setT1p3Arrow(new Vector2(x, y));
                    break;
                case 4:
                    if (cancelArrowT2)
                        mField.setT2p1Arrow(null);
                    else
                        mField.setT2p1Arrow(new Vector2(x, y));
                    break;
                case 5:
                    if (cancelArrowT2)
                        mField.setT2p2Arrow(null);
                    else
                        mField.setT2p2Arrow(new Vector2(x, y));
                    break;
                case 6:
                    if (cancelArrowT2)
                        mField.setT2p3Arrow(null);
                    else
                        mField.setT2p3Arrow(new Vector2(x, y));
                    break;
            }
            mWorld.destroyJoint(joint);
            mMouseJoints.remove(pointer);
        }
    }
}