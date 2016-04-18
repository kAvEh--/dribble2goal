package ir.eynajgroup.dribble2goal.model;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import ir.eynajgroup.dribble2goal.Constants;
import ir.eynajgroup.dribble2goal.GamePrefs;
import ir.eynajgroup.dribble2goal.MatchStats;
import ir.eynajgroup.dribble2goal.Server.ServerTool;
import ir.eynajgroup.dribble2goal.Util.Util;
import ir.eynajgroup.dribble2goal.screens.GameScreen;
import ir.eynajgroup.dribble2goal.template.Ball;
import ir.eynajgroup.dribble2goal.template.Field;
import ir.eynajgroup.dribble2goal.template.Player;
import ir.eynajgroup.dribble2goal.template.Team;

/**
 * Created by kAvEh on 2/19/2016.
 */
public class PhysicalModel implements IModel {

    private final World mWorld;

    public Box2dBall ball_body;
    private final Box2dPlayer bodyGoaler;
    private final Box2dPlayer[] bodyMyPlayers = new Box2dPlayer[5];
    private final Box2dPlayer[] bodyOppPlayers = new Box2dPlayer[5];
    private final List<IModelListener> mModelListeners;

    private final Json mJson;
    public final Field mField;
    private final Player[] myPlayers = new Player[5];
    private final Player[] oppPlayers = new Player[5];
    private final Player mGoalKeeper;
    private final Ball mBall;
    private final ITouchControls mTouchControls;

    MatchStats matchStat;
    TweenManager mTweenManager;
    public GameScreen screen;

    Util util;

    public PhysicalModel(MatchStats stat, TweenManager tmanager) {
        this.matchStat = stat;
        matchStat.GAME_STATE = Constants.GAME_PRE;
        mTweenManager = tmanager;
        mWorld = new World(new Vector2(0.0F, 0.0F), true);
        World.setVelocityThreshold(0.0F);

        Box2dWalls.create(mWorld);

        util = new Util();
//
//        if (matchStat.isMeFirst) {
//            matchStat.myStartPosition = util.getAbovePlayerPosition(matchStat.myFormation);
//        } else {
//            matchStat.myStartPosition = util.getBelowPlayerPosition(matchStat.myFormation);
//        }

        float starting_pos_x = 1.285f;
        float starting_pos_y = .84f;
        ball_body = new Box2dBall(this.mWorld,
                -Constants.SCREEN_WIDTH / 2 + starting_pos_x + Box2dPlayer.RADIUS, 0);

        bodyGoaler = new Box2dPlayer(mWorld,
                Constants.SCREEN_WIDTH / 2 - Box2dWalls.GOALER_GAP,
                0,
                "GR", true);
        for (int i = 0; i < 5; i++) {
            bodyMyPlayers[i] = new Box2dPlayer(mWorld,
                    -Constants.SCREEN_WIDTH / 2 + starting_pos_x + Box2dPlayer.RADIUS,
                    Constants.SCREEN_HEIGHT / 2 - starting_pos_y - Box2dPlayer.RADIUS,
                    1 + (matchStat.myPlayers[i][1] - 1f) / 20f, "T1P" + (i + 1),
                    matchStat.myPlayers[i][0]);
            bodyOppPlayers[i] = new Box2dPlayer(mWorld,
                    -Constants.SCREEN_WIDTH / 2 + starting_pos_x + Box2dPlayer.RADIUS,
                    -Constants.SCREEN_HEIGHT / 2 + starting_pos_y + Box2dPlayer.RADIUS,
                    1 + (matchStat.oppPlayers[i][1] - 1f) / 20f, "T2P" + +(i + 1),
                    matchStat.oppPlayers[i][0]);
        }

        mModelListeners = new ArrayList<IModelListener>();

        mField = new Field();

        for (int i = 0; i < 5; i++) {
            myPlayers[i] = new Player();
            myPlayers[i].setRadius(bodyMyPlayers[i].getRadius());
            oppPlayers[i] = new Player();
            oppPlayers[i].setRadius(bodyOppPlayers[i].getRadius());
        }

        mBall = new Ball();
        mBall.setRadius(Box2dBall.RADIUS);
        mGoalKeeper = new Player();
        mGoalKeeper.setRadius(Box2dPlayer.RADIUS_GOALER);
        mField.setGoalKeeper(mGoalKeeper);

        mField.setMyPlayer1(myPlayers[0]);
        mField.setMyPlayer2(myPlayers[1]);
        mField.setMyPlayer3(myPlayers[2]);
        mField.setMyPlayer4(myPlayers[3]);
        mField.setMyPlayer5(myPlayers[4]);

        mField.setOppPlayer1(oppPlayers[0]);
        mField.setOppPlayer2(oppPlayers[1]);
        mField.setOppPlayer3(oppPlayers[2]);
        mField.setOppPlayer4(oppPlayers[3]);
        mField.setOppPlayer5(oppPlayers[4]);

        mField.setBall(mBall);
        mJson = new Json();
        mTouchControls = new JointControls(mWorld, mField, matchStat);

        mWorld.setContactListener(new ContactListener() {
            public void beginContact(Contact contact) {
                if (contact.getFixtureA().getBody().getUserData() == "Ball") {
                    if (contact.getFixtureB().getBody().getUserData() != null &&
                            contact.getFixtureB().getBody().getUserData().toString().contains("T1")) {
                        matchStat.lastTouch = 1;
                    } else if (contact.getFixtureB().getBody().getUserData() != null &&
                            contact.getFixtureB().getBody().getUserData().toString().contains("T2")) {
                        matchStat.lastTouch = 2;
                    }
                }
                if (contact.getFixtureB().getBody().getUserData() == "Ball") {
                    if (contact.getFixtureA().getBody().getUserData() != null &&
                            contact.getFixtureA().getBody().getUserData().toString().contains("T1")) {
                        matchStat.lastTouch = 1;
                    } else if (contact.getFixtureA().getBody().getUserData() != null &&
                            contact.getFixtureA().getBody().getUserData().toString().contains("T2")) {
                        matchStat.lastTouch = 2;
                    }
                }
            }

            public void endContact(Contact contact) {
                if (contact.getFixtureA().getBody().getUserData() == "Ball") {
                    if (contact.getFixtureB().getBody().getUserData() != null &&
                            contact.getFixtureB().getBody().getUserData().toString().contains("T1")) {
                        matchStat.lastTouch = 1;
                    } else if (contact.getFixtureB().getBody().getUserData() != null &&
                            contact.getFixtureB().getBody().getUserData().toString().contains("T2")) {
                        matchStat.lastTouch = 2;
                    }
                }
                if (contact.getFixtureB().getBody().getUserData() == "Ball") {
                    if (contact.getFixtureA().getBody().getUserData() != null &&
                            contact.getFixtureA().getBody().getUserData().toString().contains("T1")) {
                        matchStat.lastTouch = 1;
                    } else if (contact.getFixtureA().getBody().getUserData() != null &&
                            contact.getFixtureA().getBody().getUserData().toString().contains("T2")) {
                        matchStat.lastTouch = 2;
                    }
                }
            }

            public void postSolve(Contact contact, ContactImpulse contactImpulse) {
                if (contactImpulse.getNormalImpulses()[0] > 1.0F) {
                    contactImpulse.getNormalImpulses()[0] = 1.0F;
                }
            }

            public void preSolve(Contact mContact, Manifold pManifold) {
            }
        });
    }

    @Override
    public void addModelListener(IModelListener listener) {
        mModelListeners.add(listener);
    }

    @Override
    public void setScreen(GameScreen gs) {
        this.screen = gs;
    }

    @Override
    public void update(float delta) {
        mWorld.step(delta, 6, 2);

        mWorld.setAutoClearForces(true);

        checkGoal();
        if (matchStat.GAME_STATE == Constants.GAME_PRE) {
            if (!checkWin()) {
                gotoStartPosition();
            }
        }

        if (matchStat.GAME_STATE == Constants.GAME_WAITING && matchStat.isOppReady) {
            matchStat.GAME_STATE = Constants.GAME_SHOOTING;
            System.out.println("start rendering *********************");
            startRendering();
        }

        if (matchStat.GAME_STATE == Constants.GAME_WAIT_FOR_AFTER) {
            if (matchStat.isOppDataRecieved) {
                System.out.println("After recieved -------------------------");
                matchStat.isOppDataRecieved = false;
                matchStat.GAME_STATE = Constants.GAME_SHOOTING;
                PhysicalModel.this.screen.endofRound();
            }
        }

        ball_body.applyForce();

        for (int i = 0; i < 5; i++) {
            bodyMyPlayers[i].applyForce();
            bodyOppPlayers[i].applyForce();
        }

        bodyGoaler.applyForceGoaler();

        for (IModelListener modelListener : mModelListeners) {
            modelListener.onModelUpdate(obtainModelState());
        }

        if (matchStat.GAME_STATE == Constants.GAME_RUNNING) {
            if (ball_body.getVelocity().len() < .00001f && bodyMyPlayers[0].getVelocity().len() < .00001f
                    && bodyMyPlayers[1].getVelocity().len() < .00001f && bodyMyPlayers[2].getVelocity().len() < .00001f
                    && bodyMyPlayers[3].getVelocity().len() < .00001f && bodyMyPlayers[4].getVelocity().len() < .00001f
                    && bodyOppPlayers[0].getVelocity().len() < .00001f && bodyOppPlayers[1].getVelocity().len() < .00001f
                    && bodyOppPlayers[2].getVelocity().len() < .00001f && bodyOppPlayers[3].getVelocity().len() < .00001f
                    && bodyOppPlayers[4].getVelocity().len() < .00001f && bodyGoaler.getVelocity().len() < .00001f) {
                matchStat.GAME_STATE = Constants.GAME_SHOOTING;
                for (int i = 0; i < 5; i++) {
                    bodyMyPlayers[i].fix();
                    bodyOppPlayers[i].fix();
                }
                ball_body.fix();
                bodyGoaler.fix();
                matchStat.GAME_STATE = Constants.GAME_WAIT_FOR_AFTER;
                sendAfter();
            }
        }
    }

    public void sendBefore() {
        JSONObject data = new JSONObject();
        try {
            data.put("playerId", GamePrefs.getInstance().playerId);
            JSONArray pos = new JSONArray();
            JSONObject tmp = new JSONObject();
            if (matchStat.myShootDirection != null) {
                String x = String.valueOf(matchStat.myShootDirection.x);
                String y = String.valueOf(matchStat.myShootDirection.y);
                pos.put(x);
                pos.put(y);
                matchStat.myShootDirection.x = Float.parseFloat(x);
                matchStat.myShootDirection.y = Float.parseFloat(y);
                tmp.put("arrow", pos);
                tmp.put("player", matchStat.myPlayerShooting);
            } else {
                pos.put("0");
                pos.put("0");
                tmp.put("arrow", pos);
                tmp.put("player", 0);
            }
            data.put("direction", tmp);

            ServerTool.getInstance().socket.emit("before", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendAfter() {
        JSONObject data = new JSONObject();
        try {
            data.put("playerId", GamePrefs.getInstance().playerId);
            JSONArray pos = new JSONArray();
            JSONObject tmp = new JSONObject();
            JSONObject staminaData = new JSONObject();
            pos.put(bodyMyPlayers[0].getPosition().x);
            pos.put(bodyMyPlayers[0].getPosition().y);
            tmp.put("1", pos);
            staminaData.put("1", mField.getMyPlayer1().getStamina());
            pos = new JSONArray();
            pos.put(bodyMyPlayers[1].getPosition().x);
            pos.put(bodyMyPlayers[1].getPosition().y);
            tmp.put("2", pos);
            staminaData.put("2", mField.getMyPlayer2().getStamina());
            pos = new JSONArray();
            pos.put(bodyMyPlayers[2].getPosition().x);
            pos.put(bodyMyPlayers[2].getPosition().y);
            tmp.put("3", pos);
            staminaData.put("3", mField.getMyPlayer3().getStamina());
            pos = new JSONArray();
            pos.put(bodyMyPlayers[3].getPosition().x);
            pos.put(bodyMyPlayers[3].getPosition().y);
            tmp.put("4", pos);
            staminaData.put("4", mField.getMyPlayer4().getStamina());
            pos = new JSONArray();
            pos.put(bodyMyPlayers[4].getPosition().x);
            pos.put(bodyMyPlayers[4].getPosition().y);
            tmp.put("5", pos);
            staminaData.put("5", mField.getMyPlayer5().getStamina());
            /*
            //Sendind Opponent Data to server
            pos = new JSONArray();
            pos.put(bodyOppPlayers[1.getPosition().x);
            pos.put(bodyOppPlayers[1.getPosition().y);
            tmp.put(0] + "", pos);
            pos = new JSONArray();
            pos.put(bodyOppPlayers[2.getPosition().x);
            pos.put(bodyOppPlayers[2.getPosition().y);
            tmp.put(1] + "", pos);
            pos.put(bodyOppPlayers[3.getPosition().x);
            pos.put(bodyOppPlayers[3.getPosition().y);
            tmp.put(2] + "", pos);
            */
            data.put("playersPosition", tmp);
            pos = new JSONArray();
            pos.put(ball_body.getPosition().x);
            pos.put(ball_body.getPosition().y);
            data.put("ballPosition", pos);
            pos = new JSONArray();
            pos.put(bodyGoaler.getPosition().x);
            pos.put(bodyGoaler.getPosition().y);
            data.put("keeperPosition", pos);

            System.out.println("sending after ...........");

            ServerTool.getInstance().socket.emit("after", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void gotoStartPosition() {
        if (matchStat.isMeFirst) {
            matchStat.myStartPosition = util.getAbovePlayerPosition(matchStat.myFormation, matchStat.myLineup);
        } else {
            matchStat.myStartPosition = util.getBelowPlayerPosition(matchStat.myFormation, matchStat.myLineup);
        }
        matchStat.GAME_STATE = Constants.GAME_POSITIONING;
        for (int i = 0; i < 5; i++) {
            bodyMyPlayers[i].fix();
            Tween.to(bodyMyPlayers[i], 1, .4F).target(matchStat.myStartPosition[i].x, matchStat.myStartPosition[i].y)
                    .ease(TweenEquations.easeInBack)
                    .start(mTweenManager).delay(.001F);
//            bodyOppPlayers[i].fix();
//            Tween.to(bodyOppPlayers[i], 1, .4F).target(matchStat.oppStartPosition[i].x, matchStat.oppStartPosition[i].y)
//                    .ease(TweenEquations.easeInBack)
//                    .start(mTweenManager).delay(.001F);
        }

        bodyGoaler.fix();
        Tween.to(bodyGoaler, 1, .4F).target(Constants.SCREEN_WIDTH / 2 - Box2dWalls.GOALER_GAP, 0)
                .ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(.001F);
        ball_body.fix();
        Tween.to(ball_body, 1, .4F).target(0, 0).ease(TweenEquations.easeInBack).start(mTweenManager).delay(.001F)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                        matchStat.GAME_STATE = Constants.GAME_WAIT_FOR_AFTER;
                        if (matchStat.isMatchReady)
                            sendAfter();
                        else
                            sendReady();
                    }
                });
    }

    private void sendReady() {
        JSONObject data = new JSONObject();
        try {
            data.put("playerId", GamePrefs.getInstance().playerId);

            ServerTool.getInstance().socket.emit("ready", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void checkPositions(Vector2 keeper, Vector2 ball, Vector2 p1, Vector2 p2,
                               Vector2 p3, Vector2 p4, Vector2 p5) {
        if (keeper.x != bodyGoaler.getPosition().x || keeper.y != bodyGoaler.getPosition().y) {
            if (!matchStat.isMeFirst) {
                bodyGoaler.fix();
                bodyGoaler.setPosition(keeper);
            }
        }
        if (ball.x != ball_body.getPosition().x || ball.y != ball_body.getPosition().y) {
            if (!matchStat.isMeFirst) {
                ball_body.fix();
                ball_body.setPosition(ball);
            }
        }
        if (p1.x != bodyOppPlayers[0].getPosition().x || p1.y != bodyOppPlayers[0].getPosition().y) {
            bodyOppPlayers[0].fix();
            bodyOppPlayers[0].setPosition(p1);
        }
        if (p2.x != bodyOppPlayers[1].getPosition().x || p2.y != bodyOppPlayers[1].getPosition().y) {
            bodyOppPlayers[1].fix();
            bodyOppPlayers[1].setPosition(p2);
        }
        if (p3.x != bodyOppPlayers[2].getPosition().x || p1.y != bodyOppPlayers[3].getPosition().y) {
            bodyOppPlayers[2].fix();
            bodyOppPlayers[2].setPosition(p3);
        }
        if (p4.x != bodyOppPlayers[3].getPosition().x || p4.y != bodyOppPlayers[3].getPosition().y) {
            bodyOppPlayers[3].fix();
            bodyOppPlayers[3].setPosition(p4);
        }
        if (p5.x != bodyOppPlayers[4].getPosition().x || p5.y != bodyOppPlayers[4].getPosition().y) {
            bodyOppPlayers[4].fix();
            bodyOppPlayers[4].setPosition(p5);
        }

        matchStat.isOppDataRecieved = true;
    }

    private String obtainModelState() {
        for (int i = 0; i < 5; i++) {
            myPlayers[i].setPosition(bodyMyPlayers[i].getPosition());
            myPlayers[i].setAngularVelocity(bodyMyPlayers[i].getAngularVelocity());
            myPlayers[i].setAngle(bodyMyPlayers[i].getAngularVelocity());
            myPlayers[i].setStamina(bodyMyPlayers[i].getStamina());

            oppPlayers[i].setPosition(bodyOppPlayers[i].getPosition());
            oppPlayers[i].setAngularVelocity(bodyOppPlayers[i].getAngularVelocity());
            oppPlayers[i].setAngle(bodyOppPlayers[i].getAngularVelocity());
            oppPlayers[i].setStamina(bodyOppPlayers[i].getStamina());
        }

        mGoalKeeper.setPosition(bodyGoaler.getPosition());
        mGoalKeeper.setAngularVelocity(bodyGoaler.getAngularVelocity());
        mGoalKeeper.setAngle(bodyGoaler.getAngularVelocity());
        mBall.setPosition(ball_body.getPosition());
        mBall.setAngularVelocity(ball_body.getAngularVelocity());
        return mJson.toJson(mField);
    }

    private void checkGoal() {
        if (mField.getBall().getPosition().x > Constants.SCREEN_WIDTH / 2 - 1f && matchStat.GAME_STATE == Constants.GAME_RUNNING) {
            matchStat.GAME_STATE = Constants.GAME_GOAL;
            screen.goalScored();
        }
    }

    private boolean checkWin() {
        if (matchStat.myGoals >= matchStat.goaltoWin) {
//            matchStat.GAME_STATE = Constants.GAME_END;
//            for (IModelListener modelListener : mModelListeners) {
//                modelListener.winEvent();
//            }
            screen.winGame(true);
            return true;
        } else if (matchStat.oppGoals >= matchStat.goaltoWin) {
//            matchStat.GAME_STATE = Constants.GAME_END;
//            for (IModelListener modelListener : mModelListeners) {
//                modelListener.winEvent();
//            }
            screen.winGame(false);
            return true;
        }
        return false;
    }

    @Override
    public void dispose() {
        mWorld.dispose();
    }

    @Override
    public void sendMoveUp(Team team) {

    }

    @Override
    public void startRendering() {
        if (matchStat.GAME_STATE == Constants.GAME_SHOOTING) {
            mTouchControls.destroyTJoint();
            matchStat.isOppReady = false;
            float x1;
            float y1;
            double dia;

            if (matchStat.myShootDirection != null) {
                if (matchStat.myPlayerShooting == 1) {
                    x1 = mField.getMyPlayer1().getPosition().x - matchStat.myShootDirection.x;
                    y1 = mField.getMyPlayer1().getPosition().y - matchStat.myShootDirection.y;
                    dia = Math.sqrt(x1 * x1 + y1 * y1);
                    if (dia > 1.5) {
                        x1 = (float) (x1 * (1.5 / dia));
                        y1 = (float) (y1 * (1.5 / dia));
                    }
                    if (dia > .1f)
                        bodyMyPlayers[0].applyImpulse(x1 * (float) (3d + ((matchStat.myPlayers[0][2] - 1d)) * .75d),
                                y1 * (float) (3d + ((matchStat.myPlayers[0][2] - 1d)) * .75d));
                } else if (matchStat.myPlayerShooting == 2) {
                    x1 = mField.getMyPlayer2().getPosition().x - matchStat.myShootDirection.x;
                    y1 = mField.getMyPlayer2().getPosition().y - matchStat.myShootDirection.y;
                    dia = Math.sqrt(x1 * x1 + y1 * y1);
                    if (dia > 1.5) {
                        x1 = (float) (x1 * (1.5 / dia));
                        y1 = (float) (y1 * (1.5 / dia));
                    }
                    if (dia > .1f)
                        bodyMyPlayers[1].applyImpulse(x1 * (float) (3d + ((matchStat.myPlayers[1][2] - 1d)) * .75d),
                                y1 * (float) (3d + ((matchStat.myPlayers[1][2] - 1d)) * .75d));
                } else if (matchStat.myPlayerShooting == 3) {
                    x1 = mField.getMyPlayer3().getPosition().x - matchStat.myShootDirection.x;
                    y1 = mField.getMyPlayer3().getPosition().y - matchStat.myShootDirection.y;
                    dia = Math.sqrt(x1 * x1 + y1 * y1);
                    if (dia > 1.5) {
                        x1 = (float) (x1 * (1.5 / dia));
                        y1 = (float) (y1 * (1.5 / dia));
                    }
                    if (dia > .1f)
                        bodyMyPlayers[2].applyImpulse(x1 * (float) (3d + ((matchStat.myPlayers[2][2] - 1d)) * .75d),
                                y1 * (float) (3d + ((matchStat.myPlayers[2][2] - 1d)) * .75d));
                } else if (matchStat.myPlayerShooting == 4) {
                    x1 = mField.getMyPlayer4().getPosition().x - matchStat.myShootDirection.x;
                    y1 = mField.getMyPlayer4().getPosition().y - matchStat.myShootDirection.y;
                    dia = Math.sqrt(x1 * x1 + y1 * y1);
                    if (dia > 1.5) {
                        x1 = (float) (x1 * (1.5 / dia));
                        y1 = (float) (y1 * (1.5 / dia));
                    }
                    if (dia > .1f)
                        bodyMyPlayers[3].applyImpulse(x1 * (float) (3d + ((matchStat.myPlayers[3][2] - 1d)) * .75d),
                                y1 * (float) (3d + ((matchStat.myPlayers[3][2] - 1d)) * .75d));
                } else if (matchStat.myPlayerShooting == 5) {
                    x1 = mField.getMyPlayer5().getPosition().x - matchStat.myShootDirection.x;
                    y1 = mField.getMyPlayer5().getPosition().y - matchStat.myShootDirection.y;
                    dia = Math.sqrt(x1 * x1 + y1 * y1);
                    if (dia > 1.5) {
                        x1 = (float) (x1 * (1.5 / dia));
                        y1 = (float) (y1 * (1.5 / dia));
                    }
                    if (dia > .1f)
                        bodyMyPlayers[4].applyImpulse(x1 * (float) (3d + ((matchStat.myPlayers[4][2] - 1d)) * .75d),
                                y1 * (float) (3d + ((matchStat.myPlayers[4][2] - 1d)) * .75d));
                }
            }
            if (matchStat.oppShootDirection != null) {
                if (matchStat.oppPlayerShooting == 1) {
                    x1 = mField.getOppPlayer1().getPosition().x - matchStat.oppShootDirection.x;
                    y1 = mField.getOppPlayer1().getPosition().y - matchStat.oppShootDirection.y;
                    dia = Math.sqrt(x1 * x1 + y1 * y1);
                    if (dia > 1.5) {
                        x1 = (float) (x1 * (1.5 / dia));
                        y1 = (float) (y1 * (1.5 / dia));
                    }
                    if (dia > .1f)
                        bodyOppPlayers[0].applyImpulse(x1 * (float) (3d + ((matchStat.oppPlayers[0][2] - 1d)) * .75d),
                                y1 * (float) (3d + ((matchStat.oppPlayers[0][2] - 1d)) * .75d));
                } else if (matchStat.oppPlayerShooting == 2) {
                    x1 = mField.getOppPlayer2().getPosition().x - matchStat.oppShootDirection.x;
                    y1 = mField.getOppPlayer2().getPosition().y - matchStat.oppShootDirection.y;
                    dia = Math.sqrt(x1 * x1 + y1 * y1);
                    if (dia > 1.5) {
                        x1 = (float) (x1 * (1.5 / dia));
                        y1 = (float) (y1 * (1.5 / dia));
                    }
                    if (dia > .1f)
                        bodyOppPlayers[1].applyImpulse(x1 * (float) (3d + ((matchStat.oppPlayers[1][2] - 1d)) * .75d),
                                y1 * (float) (3d + ((matchStat.oppPlayers[1][2] - 1d)) * .75d));
                } else if (matchStat.oppPlayerShooting == 3) {
                    x1 = mField.getOppPlayer3().getPosition().x - matchStat.oppShootDirection.x;
                    y1 = mField.getOppPlayer3().getPosition().y - matchStat.oppShootDirection.y;
                    dia = Math.sqrt(x1 * x1 + y1 * y1);
                    if (dia > 1.5) {
                        x1 = (float) (x1 * (1.5 / dia));
                        y1 = (float) (y1 * (1.5 / dia));
                    }
                    if (dia > .1f)
                        bodyOppPlayers[2].applyImpulse(x1 * (float) (3d + ((matchStat.oppPlayers[2][2] - 1d)) * .75d),
                                y1 * (float) (3d + ((matchStat.oppPlayers[2][2] - 1d)) * .75d));
                } else if (matchStat.oppPlayerShooting == 4) {
                    x1 = mField.getOppPlayer4().getPosition().x - matchStat.oppShootDirection.x;
                    y1 = mField.getOppPlayer4().getPosition().y - matchStat.oppShootDirection.y;
                    dia = Math.sqrt(x1 * x1 + y1 * y1);
                    if (dia > 1.5) {
                        x1 = (float) (x1 * (1.5 / dia));
                        y1 = (float) (y1 * (1.5 / dia));
                    }
                    if (dia > .1f)
                        bodyOppPlayers[3].applyImpulse(x1 * (float) (3d + ((matchStat.oppPlayers[3][2] - 1d)) * .75d),
                                y1 * (float) (3d + ((matchStat.oppPlayers[3][2] - 1d)) * .75d));
                } else if (matchStat.oppPlayerShooting == 5) {
                    x1 = mField.getOppPlayer5().getPosition().x - matchStat.oppShootDirection.x;
                    y1 = mField.getOppPlayer5().getPosition().y - matchStat.oppShootDirection.y;
                    dia = Math.sqrt(x1 * x1 + y1 * y1);
                    if (dia > 1.5) {
                        x1 = (float) (x1 * (1.5 / dia));
                        y1 = (float) (y1 * (1.5 / dia));
                    }
                    if (dia > .1f)
                        bodyOppPlayers[4].applyImpulse(x1 * (float) (3d + ((matchStat.oppPlayers[4][2] - 1d)) * .75d),
                                y1 * (float) (3d + ((matchStat.oppPlayers[4][2] - 1d)) * .75d));
                }
            }

            matchStat.myShootDirection = null;
            matchStat.oppShootDirection = null;

            System.out.println("end of rendering ************>" + matchStat.myShootDirection);

            matchStat.GAME_STATE = Constants.GAME_RUNNING;
        }
    }

    @Override
    public void sendMoveDown(Team team) {
//        if (matchStat.isShooting) {
//            ball_body.applyImpulse(2, -.1f);
//            matchStat.isShooting = false;
//        }
    }

    @Override
    public void powerUp(Team team, Vector2 v) {
//        if (team == Team.LEFT) {
//            v.set(10, 2);
//            mBox2dStickLeft.applyForce(v);
//        } else {
//            v.set(10, 2);
//            mBox2dStickRight.applyForce(v);
//        }
    }

    @Override
    public void sendStartRound() {

    }

    @Override
    public void sendResetRound() {
//        mLeftPlayer.setPosition(new Vector2(0, 0));
//        mRightPlayer.setPosition(new Vector2(0, 0));
    }

    @Override
    public void debugRender(OrthographicCamera camera) {
//        if (mDebugRenderer != null) {
//            mDebugRenderer.render(mWorld, camera);
//        }
    }

    @Override
    public void sendTouchDown(float x, float y, int pointer) {
        mTouchControls.sendTouchDown(x, y, pointer);
    }

    @Override
    public void sendTouchDragged(float x, float y, int pointer) {
        mTouchControls.sendTouchDragged(x, y, pointer);
    }

    @Override
    public void sendTouchUp(float x, float y, int pointer) {
        mTouchControls.sendTouchUp(x, y, pointer);
    }

    @Override
    public void destroyTJoint() {

    }
}
