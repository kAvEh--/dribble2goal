package ir.eynakgroup.dribble2goal.model;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
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
import java.util.Random;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import ir.eynakgroup.dribble2goal.Constants;
import ir.eynakgroup.dribble2goal.GamePrefs;
import ir.eynakgroup.dribble2goal.MatchStats;
import ir.eynakgroup.dribble2goal.Server.ServerTool;
import ir.eynakgroup.dribble2goal.Util.Util;
import ir.eynakgroup.dribble2goal.screens.GameScreen;
import ir.eynakgroup.dribble2goal.screens.HelpGameScreen;
import ir.eynakgroup.dribble2goal.template.Ball;
import ir.eynakgroup.dribble2goal.template.Field;
import ir.eynakgroup.dribble2goal.template.Player;
import ir.eynakgroup.dribble2goal.template.Team;

/**
 * Created by Eynak_PC2 on 2/28/2017.
 */

public class HelpPhysicalModel implements IModel {

    private final World mWorld;

    private Box2dBall ball_body;
    private final Box2dPlayer bodyGoaler;
    private final Box2dPlayer[] bodyMyPlayers = new Box2dPlayer[5];
    private final Box2dPlayer[] bodyOppPlayers = new Box2dPlayer[5];
    private final List<IModelListener> mModelListeners;

    private final Json mJson;
    private final Field mField;
    private final Player[] myPlayers = new Player[5];
    private final Player[] oppPlayers = new Player[5];
    private final Player mGoalKeeper;
    private final Ball mBall;
    private final ITouchControls mTouchControls;

    private MatchStats matchStat;
    private TweenManager mTweenManager;
    private GameScreen screen2;
    public HelpGameScreen screen;

    private Box2DDebugRenderer renderer;

    private Util util;

    private float[] myStamina;

    public HelpPhysicalModel(MatchStats stat, TweenManager tmanager) {
        renderer = new Box2DDebugRenderer();

        this.matchStat = stat;
        matchStat.GAME_STATE = Constants.GAME_PRE;

        mTweenManager = tmanager;
        mWorld = new World(new Vector2(0.0F, 0.0F), true);
        World.setVelocityThreshold(0.0F);

        myStamina = new float[5];
        for (int i = 0; i < 5; i++) {
            myStamina[i] = 1f;
        }

        Box2dWalls.create(mWorld);

        util = new Util();

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
                    1 + (matchStat.oppPlayers[i][1] - 1f) / 20f, "T2P" + (i + 1),
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

            public void preSolve(Contact contact, Manifold pManifold) {
                if ((contact.getFixtureB().getBody().getUserData() == "Ball" && contact.getFixtureA().getBody().getUserData() == "wall")
                        || (contact.getFixtureA().getBody().getUserData() == "wall" && contact.getFixtureB().getBody().getUserData() == "Ball")) {
                    contact.setEnabled(false);
                } else if ((contact.getFixtureB().getBody().getUserData() == "GR" && contact.getFixtureA().getBody().getUserData() == "wall")
                        || (contact.getFixtureA().getBody().getUserData() == "wall" && contact.getFixtureB().getBody().getUserData() == "GR")) {
                    contact.setEnabled(false);
                }
            }
        });
    }

    @Override
    public void addModelListener(IModelListener listener) {
        mModelListeners.add(listener);
    }

    @Override
    public void setScreen(GameScreen gs) {
        this.screen2 = gs;
    }

    @Override
    public void setScreen(HelpGameScreen gs) {
        this.screen = gs;
    }

    @Override
    public void update(float delta) {
        mWorld.step(delta, 6, 2);

        mWorld.setAutoClearForces(true);

        if (matchStat.GAME_STATE == Constants.GAME_GOTO_RESULT) {
            screen.finishGameScreen();
        }

        checkGoal();

        if (matchStat.GAME_STATE == Constants.GAME_PRE) {
            gotoStartPosition();
        } else if (matchStat.GAME_STATE == Constants.GAME_PRE_PENALTY) {
            gotoPenaltyPosition();
        }

        if (matchStat.GAME_STATE == Constants.GAME_WAITING) {
            matchStat.GAME_STATE = Constants.GAME_SHOOTING;
            startRendering();
        }

        if (matchStat.GAME_STATE == Constants.GAME_WAIT_FOR_AFTER_PENALTY || matchStat.GAME_STATE == Constants.GAME_WAIT_FOR_AFTER) {
            matchStat.GAME_STATE = Constants.GAME_SHOOTING;
            HelpPhysicalModel.this.screen.endofRound();
        } else if (matchStat.GAME_STATE == Constants.GAME_AFTER_PENALTY_GOAL) {
            gotoPenaltyPosition();
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
            if (ball_body.getVelocity().len() < .0001f && bodyMyPlayers[0].getVelocity().len() < .0001f
                    && bodyMyPlayers[1].getVelocity().len() < .0001f && bodyMyPlayers[2].getVelocity().len() < .0001f
                    && bodyMyPlayers[3].getVelocity().len() < .0001f && bodyMyPlayers[4].getVelocity().len() < .0001f
                    && bodyOppPlayers[0].getVelocity().len() < .0001f && bodyOppPlayers[1].getVelocity().len() < .0001f
                    && bodyOppPlayers[2].getVelocity().len() < .0001f && bodyOppPlayers[3].getVelocity().len() < .0001f
                    && bodyOppPlayers[4].getVelocity().len() < .0001f && bodyGoaler.getVelocity().len() < .0001f) {
                for (int i = 0; i < 5; i++) {
                    bodyMyPlayers[i].fix();
                    bodyOppPlayers[i].fix();
                }
                ball_body.fix();
                bodyGoaler.fix();
                if (matchStat.half_status == Constants.HALF_PENALTY) {
                    gotoPenaltyPosition();
                } else {
                    matchStat.GAME_STATE = Constants.GAME_SHOOTING;
                    HelpPhysicalModel.this.screen.endofRound();
                }
            }
        }
    }

    public void setBallPosition(float x, float y) {
        ball_body.setPosition(new Vector2(x, y));
    }

    public void setKeeperPosition(float x, float y) {
        bodyGoaler.setPosition(new Vector2(x, y));
    }

    public void setMyPlayerPosition(int num, float x, float y) {
        bodyMyPlayers[num].setPosition(new Vector2(x, y));
    }

    public void setOppPlayerPosition(int num, float x, float y) {
        bodyOppPlayers[num].setPosition(new Vector2(x, y));
    }

    public void sendBefore() {
        JSONObject data = new JSONObject();
        if (matchStat.half_status != Constants.HALF_PENALTY || !matchStat.isMeFirst) {
            try {
                data.put("playerId", GamePrefs.getInstance().user.getId());
                JSONArray pos = new JSONArray();
                JSONObject tmp = new JSONObject();
                if (matchStat.myShootDirection != null) {
                    String x = Float.toHexString(matchStat.myShootDirection.x);
                    String y = Float.toHexString(matchStat.myShootDirection.y);
                    pos.put(x);
                    pos.put(y);
                    matchStat.myShootDirection.x = Float.parseFloat(x);
                    matchStat.myShootDirection.y = Float.parseFloat(y);
                    tmp.put("arrow", pos);
                    tmp.put("player", matchStat.myPlayerShooting);
                } else {
                    pos.put(Float.toHexString(0));
                    pos.put(Float.toHexString(0));
                    tmp.put("arrow", pos);
                    tmp.put("player", 0);
                }
                data.put("direction", tmp);

                ServerTool.getInstance().socket.emit("before", data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            int tmp = matchStat.goaler_position;
            try {
                data.put("playerId", GamePrefs.getInstance().user.getId());
                data.put("direction", tmp);

                ServerTool.getInstance().socket.emit("beforeGoaler", data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendAfter() {
        JSONObject data = new JSONObject();
        try {
            data.put("playerId", GamePrefs.getInstance().user.getId());
            JSONArray pos = new JSONArray();
            JSONObject tmp = new JSONObject();
            JSONObject staminaData = new JSONObject();
            pos.put(Float.toHexString(bodyMyPlayers[0].getPosition().x));
            pos.put(Float.toHexString(bodyMyPlayers[0].getPosition().y));
            tmp.put("1", pos);
            staminaData.put("1", Float.toHexString(mField.getMyPlayer1().getStamina()));
            pos = new JSONArray();
            pos.put(Float.toHexString(bodyMyPlayers[1].getPosition().x));
            pos.put(Float.toHexString(bodyMyPlayers[1].getPosition().y));
            tmp.put("2", pos);
            staminaData.put("2", Float.toHexString(mField.getMyPlayer2().getStamina()));
            pos = new JSONArray();
            pos.put(Float.toHexString(bodyMyPlayers[2].getPosition().x));
            pos.put(Float.toHexString(bodyMyPlayers[2].getPosition().y));
            tmp.put("3", pos);
            staminaData.put("3", Float.toHexString(mField.getMyPlayer3().getStamina()));
            pos = new JSONArray();
            pos.put(Float.toHexString(bodyMyPlayers[3].getPosition().x));
            pos.put(Float.toHexString(bodyMyPlayers[3].getPosition().y));
            tmp.put("4", pos);
            staminaData.put("4", Float.toHexString(mField.getMyPlayer4().getStamina()));
            pos = new JSONArray();
            pos.put(Float.toHexString(bodyMyPlayers[4].getPosition().x));
            pos.put(Float.toHexString(bodyMyPlayers[4].getPosition().y));
            tmp.put("5", pos);
            staminaData.put("5", Float.toHexString(mField.getMyPlayer5().getStamina()));
            data.put("playersPosition", tmp);
            pos = new JSONArray();
            pos.put(Float.toHexString(ball_body.getPosition().x));
            pos.put(Float.toHexString(ball_body.getPosition().y));
            data.put("ballPosition", pos);
            pos = new JSONArray();
            pos.put(Float.toHexString(bodyGoaler.getPosition().x));
            pos.put(Float.toHexString(bodyGoaler.getPosition().y));
            data.put("keeperPosition", pos);
            data.put("playersStamina", staminaData);

            ServerTool.getInstance().socket.emit("after", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void gotoStartPosition() {
        matchStat.GAME_STATE = Constants.GAME_POSITIONING;
        matchStat.myStartPosition = util.getAbovePlayerPosition(matchStat.myFormation, matchStat.myLineup);
        matchStat.oppStartPosition = util.getBelowPlayerPosition(matchStat.oppFormation, matchStat.myLineup);
        for (int i = 0; i < 5; i++) {
            bodyMyPlayers[i].fix();
            Tween.to(bodyMyPlayers[i], 1, .4F).target(matchStat.myStartPosition[i].x, matchStat.myStartPosition[i].y)
                    .ease(TweenEquations.easeInBack)
                    .start(mTweenManager).delay(.001F);
            bodyOppPlayers[i].fix();
            Tween.to(bodyOppPlayers[i], 1, .4F).target(matchStat.oppStartPosition[i].x, matchStat.oppStartPosition[i].y)
                    .ease(TweenEquations.easeInBack)
                    .start(mTweenManager).delay(.001F);
        }

        bodyGoaler.fix();
        Tween.to(bodyGoaler, 1, .4F).target(Constants.HUD_SCREEN_WIDTH / 2 - Box2dWalls.GOALER_GAP, 0)
                .ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(.001F);
        ball_body.fix();
        Tween.to(ball_body, 1, .4F).target(0, 0).ease(TweenEquations.easeInBack).start(mTweenManager).delay(.001F)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                        matchStat.GAME_STATE = Constants.GAME_WAIT_FOR_AFTER;
                        //TODO check
                    }
                });
    }

    public int penalty_num = 0;
    boolean flag = true;

    private void gotoPenaltyPosition() {
        if (matchStat.isMeFirst) {
            matchStat.myStartPosition = util.getPenaltyPosition1(penalty_num);
            matchStat.oppStartPosition = util.getPenaltyPosition2(penalty_num);
            Random r = new Random();
            matchStat.goaler_position = r.nextInt(4) + 1;
            matchStat.isMeFirst = false;
        } else {
            matchStat.myStartPosition = util.getPenaltyPosition2(penalty_num);
            matchStat.oppStartPosition = util.getPenaltyPosition1(penalty_num);
            matchStat.oppPlayerShooting = penalty_num + 1;
            float x = generateRandom(-6.531f, -6.89f);
            float y = generateRandom(.0938f, -.0781f);
            matchStat.oppShootDirection = new Vector2(x, y);
            matchStat.isMeFirst = true;
        }
        if (!flag) {
            penalty_num++;
            penalty_num = penalty_num % 5;
            flag = true;
        } else {
            flag = false;
        }
        matchStat.GAME_STATE = Constants.GAME_POSITIONING;
        for (int i = 0; i < 5; i++) {
            bodyOppPlayers[i].fix();
            Tween.to(bodyOppPlayers[i], 1, .4F).target(matchStat.oppStartPosition[i].x, matchStat.oppStartPosition[i].y)
                    .ease(TweenEquations.easeInBack)
                    .start(mTweenManager).delay(.001F);
        }
        for (int i = 0; i < 5; i++) {
            bodyMyPlayers[i].fix();
            Tween.to(bodyMyPlayers[i], 1, .4F).target(matchStat.myStartPosition[i].x, matchStat.myStartPosition[i].y)
                    .ease(TweenEquations.easeInBack)
                    .start(mTweenManager).delay(.001F);
        }

        bodyGoaler.fix();
        Tween.to(bodyGoaler, 1, .4F).target(Constants.HUD_SCREEN_WIDTH / 2 - Box2dWalls.GOALER_GAP, 0)
                .ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(.001F);
        ball_body.fix();
        Tween.to(ball_body, 1, .6F).target(-Constants.SCREEN_WIDTH / 7f, 0).ease(TweenEquations.easeInBack).start(mTweenManager).delay(.001F)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                        gotoFinalposition();
                        if (matchStat.isMatchReady) {
                            matchStat.GAME_STATE = Constants.GAME_SHOOTING;
                            HelpPhysicalModel.this.screen.endofRound();
                        } else {
                            matchStat.GAME_STATE = Constants.GAME_WAIT_FOR_AFTER_PENALTY;
                            sendReady();
                        }
                    }
                });
    }

    private float generateRandom(float max, float min) {
        Random r = new Random();
        return (r.nextFloat() * (max - min) + min);
    }

    private void gotoFinalposition() {
        //TODO set final positions of players
        for (int i = 0; i < 5; i++) {
            bodyMyPlayers[i].fix();
            bodyMyPlayers[i].setPosition(new Vector2(matchStat.myStartPosition[i].x, matchStat.myStartPosition[i].y));
            bodyOppPlayers[i].fix();
            bodyOppPlayers[i].setPosition(new Vector2(matchStat.oppStartPosition[i].x, matchStat.oppStartPosition[i].y));
            bodyGoaler.fix();
            bodyGoaler.setPosition(new Vector2(Constants.HUD_SCREEN_WIDTH / 2 - Box2dWalls.GOALER_GAP, 0));
            ball_body.fix();
            ball_body.setPosition(new Vector2(-Constants.SCREEN_WIDTH / 7f, 0));
        }
    }

    private void sendReady() {
        JSONObject data = new JSONObject();
        try {
            data.put("playerId", GamePrefs.getInstance().user.getId());

            ServerTool.getInstance().socket.emit("ready", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void checkPositions(Vector2 keeper, Vector2 ball, Vector2 p1, Vector2 p2,
                               Vector2 p3, Vector2 p4, Vector2 p5) {
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
            float x1;
            float y1;
            double dia;

            if (matchStat.half_status == Constants.HALF_PENALTY && matchStat.goaler_position != 0) {
                if (matchStat.goaler_position == 1) {
                    bodyGoaler.applyImpulse(0, .28f, true);
                } else if (matchStat.goaler_position == 2) {
                    bodyGoaler.applyImpulse(0, .4f, true);
                } else if (matchStat.goaler_position == 3) {
                    bodyGoaler.applyImpulse(0, -.28f, true);
                } else if (matchStat.goaler_position == 4) {
                    bodyGoaler.applyImpulse(0, -.4f, true);
                }
            }

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
                                y1 * (float) (3d + ((matchStat.myPlayers[0][2] - 1d)) * .75d), matchStat.half_status == Constants.HALF_PENALTY);
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
                                y1 * (float) (3d + ((matchStat.myPlayers[1][2] - 1d)) * .75d), matchStat.half_status == Constants.HALF_PENALTY);
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
                                y1 * (float) (3d + ((matchStat.myPlayers[2][2] - 1d)) * .75d), matchStat.half_status == Constants.HALF_PENALTY);
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
                                y1 * (float) (3d + ((matchStat.myPlayers[3][2] - 1d)) * .75d), matchStat.half_status == Constants.HALF_PENALTY);
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
                                y1 * (float) (3d + ((matchStat.myPlayers[4][2] - 1d)) * .75d), matchStat.half_status == Constants.HALF_PENALTY);
                }
            }
            if (matchStat.oppShootDirection != null) {
                System.out.println(matchStat.oppPlayerShooting + "--->" + matchStat.oppShootDirection.x + ":" + matchStat.oppShootDirection.y);
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
                                y1 * (float) (3d + ((matchStat.oppPlayers[0][2] - 1d)) * .75d), matchStat.half_status == Constants.HALF_PENALTY);
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
                                y1 * (float) (3d + ((matchStat.oppPlayers[1][2] - 1d)) * .75d), matchStat.half_status == Constants.HALF_PENALTY);
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
                                y1 * (float) (3d + ((matchStat.oppPlayers[2][2] - 1d)) * .75d), matchStat.half_status == Constants.HALF_PENALTY);
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
                                y1 * (float) (3d + ((matchStat.oppPlayers[3][2] - 1d)) * .75d), matchStat.half_status == Constants.HALF_PENALTY);
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
                                y1 * (float) (3d + ((matchStat.oppPlayers[4][2] - 1d)) * .75d), matchStat.half_status == Constants.HALF_PENALTY);
                }
            }

            for (int i = 0; i < 5; i++) {
                myStamina[i] = bodyMyPlayers[i].getStamina();
            }

            matchStat.myShootDirection = null;
            matchStat.oppShootDirection = null;

//            System.out.println("D2G log ::: " + "end of rendering ************>");

            matchStat.GAME_STATE = Constants.GAME_RUNNING;
        }
    }

    @Override
    public float[] getMyStamina() {
        return this.myStamina;
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
