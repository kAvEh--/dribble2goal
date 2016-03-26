package ir.eynajgroup.dribble2goal.model;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;
import java.util.List;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import ir.eynajgroup.dribble2goal.Constants;
import ir.eynajgroup.dribble2goal.MatchStats;
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
    private final Box2dPlayer mGoaler;
    private final Box2dPlayer bodyT1Player1;
    private final Box2dPlayer bodyT1Player2;
    private final Box2dPlayer bodyT1Player3;
    private final Box2dPlayer bodyT2Player1;
    private final Box2dPlayer bodyT2Player2;
    private final Box2dPlayer bodyT2Player3;
    private final List<IModelListener> mModelListeners;

    private final Json mJson;
    private final Field mField;
    private final Player t1Player1;
    private final Player t1Player2;
    private final Player t1Player3;
    private final Player t2Player1;
    private final Player t2Player2;
    private final Player t2Player3;
    private final Player mGoalKeeper;
    private final Ball mBall;
    private final ITouchControls mTouchControls;

    private final float starting_pos_x = 1.285f;
    private final float starting_pos_y = .84f;

    MatchStats matchStat;
    TweenManager mTweenManager;
    public GameScreen screen;

    public PhysicalModel(MatchStats stat, TweenManager tmanager) {
        this.matchStat = stat;
        matchStat.GAME_STATE = Constants.GAME_PRE;
        mTweenManager = tmanager;
        mWorld = new World(new Vector2(0.0F, 0.0F), true);
        mWorld.setVelocityThreshold(0.0F);

        Box2dWalls.create(mWorld);

        ball_body = new Box2dBall(this.mWorld,
                -Constants.SCREEN_WIDTH / 2 + starting_pos_x + Box2dPlayer.RADIUS, 0);

        mGoaler = new Box2dPlayer(mWorld,
                Constants.SCREEN_WIDTH / 2 - Box2dWalls.GOALER_GAP,
                0,
                "GR", true);
        bodyT1Player1 = new Box2dPlayer(mWorld,
                -Constants.SCREEN_WIDTH / 2 + starting_pos_x + Box2dPlayer.RADIUS,
                Constants.SCREEN_HEIGHT / 2 - starting_pos_y - Box2dPlayer.RADIUS,
                1 + (matchStat.T1players[matchStat.p1formation[0]][1] - 1f) / 20f, "T1P1",
                matchStat.T1players[matchStat.p1formation[0]][0]);
        bodyT1Player2 = new Box2dPlayer(mWorld,
                -Constants.SCREEN_WIDTH / 2 + starting_pos_x + Box2dPlayer.RADIUS,
                Constants.SCREEN_HEIGHT / 2 - starting_pos_y - Box2dPlayer.RADIUS,
                1 + (matchStat.T1players[matchStat.p1formation[1]][1] - 1f) / 20f, "T1P2",
                matchStat.T1players[matchStat.p1formation[1]][0]);
        bodyT1Player3 = new Box2dPlayer(mWorld,
                -Constants.SCREEN_WIDTH / 2 + starting_pos_x + Box2dPlayer.RADIUS,
                Constants.SCREEN_HEIGHT / 2 - starting_pos_y - Box2dPlayer.RADIUS,
                1 + (matchStat.T1players[matchStat.p1formation[2]][1] - 1f) / 20f, "T1P3",
                matchStat.T1players[matchStat.p1formation[2]][0]);
        bodyT2Player1 = new Box2dPlayer(mWorld,
                -Constants.SCREEN_WIDTH / 2 + starting_pos_x + Box2dPlayer.RADIUS,
                -Constants.SCREEN_HEIGHT / 2 + starting_pos_y + Box2dPlayer.RADIUS,
                1 + (matchStat.T2players[matchStat.p2formation[0]][1] - 1f) / 20f, "T2P1",
                matchStat.T2players[matchStat.p2formation[0]][0]);
        bodyT2Player2 = new Box2dPlayer(mWorld,
                -Constants.SCREEN_WIDTH / 2 + starting_pos_x + Box2dPlayer.RADIUS,
                -Constants.SCREEN_HEIGHT / 2 + starting_pos_y + Box2dPlayer.RADIUS,
                1 + (matchStat.T2players[matchStat.p2formation[1]][1] - 1f) / 20f, "T2P2",
                matchStat.T2players[matchStat.p2formation[1]][0]);
        bodyT2Player3 = new Box2dPlayer(mWorld,
                -Constants.SCREEN_WIDTH / 2 + starting_pos_x + Box2dPlayer.RADIUS,
                -Constants.SCREEN_HEIGHT / 2 + starting_pos_y + Box2dPlayer.RADIUS,
                1 + (matchStat.T2players[matchStat.p2formation[2]][1] - 1f) / 20f, "T2P3",
                matchStat.T2players[matchStat.p2formation[2]][0]);

        mModelListeners = new ArrayList<IModelListener>();

        mField = new Field();

        t1Player1 = new Player();
        t1Player1.setRadius(bodyT1Player1.getRadius());
        t1Player2 = new Player();
        t1Player2.setRadius(bodyT1Player2.getRadius());
        t1Player3 = new Player();
        t1Player3.setRadius(bodyT1Player3.getRadius());

        t2Player1 = new Player();
        t2Player1.setRadius(bodyT2Player1.getRadius());
        t2Player2 = new Player();
        t2Player2.setRadius(bodyT2Player2.getRadius());
        t2Player3 = new Player();
        t2Player3.setRadius(bodyT2Player3.getRadius());

        mBall = new Ball();
        mBall.setRadius(ball_body.RADIUS);
        mGoalKeeper = new Player();
        mGoalKeeper.setRadius(Box2dPlayer.RADIUS_GOALER);
        mField.setGoalKeeper(mGoalKeeper);

        mField.setT1Player1(t1Player1);
        mField.setT1Player2(t1Player2);
        mField.setT1Player3(t1Player3);

        mField.setT2Player1(t2Player1);
        mField.setT2Player2(t2Player2);
        mField.setT2Player3(t2Player3);

        mField.setBall(mBall);
        mField.setLeftDragged(false);
        mField.setRightDragged(false);
        mJson = new Json();
        mTouchControls = new JointControls(mWorld, mField, matchStat);

        mWorld.setContactListener(new ContactListener() {
            public void beginContact(Contact paramAnonymousContact) {
                if (paramAnonymousContact.getFixtureA().getBody().getUserData() == "Ball") {
                    if (paramAnonymousContact.getFixtureB().getBody().getUserData() != null &&
                            paramAnonymousContact.getFixtureB().getBody().getUserData().toString().contains("T1")) {
                        matchStat.lastTouch = 1;
                    } else if (paramAnonymousContact.getFixtureB().getBody().getUserData() != null &&
                            paramAnonymousContact.getFixtureB().getBody().getUserData().toString().contains("T2")) {
                        matchStat.lastTouch = 2;
                    }
                }
                if (paramAnonymousContact.getFixtureB().getBody().getUserData() == "Ball") {
                    if (paramAnonymousContact.getFixtureA().getBody().getUserData() != null &&
                            paramAnonymousContact.getFixtureA().getBody().getUserData().toString().contains("T1")) {
                        matchStat.lastTouch = 1;
                    } else if (paramAnonymousContact.getFixtureA().getBody().getUserData() != null &&
                            paramAnonymousContact.getFixtureA().getBody().getUserData().toString().contains("T2")) {
                        matchStat.lastTouch = 2;
                    }
                }
            }

            public void endContact(Contact paramAnonymousContact) {
                if (paramAnonymousContact.getFixtureA().getBody().getUserData() == "Ball") {
                    if (paramAnonymousContact.getFixtureB().getBody().getUserData() != null &&
                            paramAnonymousContact.getFixtureB().getBody().getUserData().toString().contains("T1")) {
                        matchStat.lastTouch = 1;
                    } else if (paramAnonymousContact.getFixtureB().getBody().getUserData() != null &&
                            paramAnonymousContact.getFixtureB().getBody().getUserData().toString().contains("T2")) {
                        matchStat.lastTouch = 2;
                    }
                }
                if (paramAnonymousContact.getFixtureB().getBody().getUserData() == "Ball") {
                    if (paramAnonymousContact.getFixtureA().getBody().getUserData() != null &&
                            paramAnonymousContact.getFixtureA().getBody().getUserData().toString().contains("T1")) {
                        matchStat.lastTouch = 1;
                    } else if (paramAnonymousContact.getFixtureA().getBody().getUserData() != null &&
                            paramAnonymousContact.getFixtureA().getBody().getUserData().toString().contains("T2")) {
                        matchStat.lastTouch = 2;
                    }
                }
            }

            public void postSolve(Contact contact, ContactImpulse paramAnonymousContactImpulse) {
                if (paramAnonymousContactImpulse.getNormalImpulses()[0] > 1.0F) {
                    paramAnonymousContactImpulse.getNormalImpulses()[0] = 1.0F;
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
        mWorld.step(delta, 10, 8);
        checkGoal();
        if (matchStat.GAME_STATE == Constants.GAME_PRE) {
            if (!checkWin()) {
                gotoStartPosition();
                this.screen.endofRound();
            }
        }

        if (matchStat.GAME_STATE == Constants.GAME_RUNNING) {
            if (ball_body.getVelocity().len() == 0f && bodyT1Player1.getVelocity().len() == 0f
                    && bodyT1Player2.getVelocity().len() == 0f && bodyT1Player3.getVelocity().len() == 0f
                    && bodyT2Player1.getVelocity().len() == 0f && bodyT2Player2.getVelocity().len() == 0f
                    && bodyT2Player3.getVelocity().len() == 0f && mGoaler.getVelocity().len() == 0f) {
                matchStat.GAME_STATE = Constants.GAME_SHOOTING;
                this.screen.endofRound();
            }
        }

        ball_body.applyForce();

        bodyT1Player1.applyForce();
        bodyT1Player2.applyForce();
        bodyT1Player3.applyForce();

        bodyT2Player1.applyForce();
        bodyT2Player2.applyForce();
        bodyT2Player3.applyForce();

        mGoaler.applyForceGoaler();

        for (IModelListener modelListener : mModelListeners) {
            modelListener.onModelUpdate(obtainModelState());
        }
    }

    private void gotoStartPosition() {
        matchStat.GAME_STATE = Constants.GAME_POSITIONING;
        if (matchStat.p1SubReady) {

        }
        if (matchStat.p2SubReady) {

        }
        bodyT1Player1.fix();
        Tween.to(bodyT1Player1, 1, .4F).target(matchStat.p1StartPosition[0].x, matchStat.p1StartPosition[0].y)
                .ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(.001F);
        bodyT1Player2.fix();
        Tween.to(bodyT1Player2, 1, .4F).target(matchStat.p1StartPosition[1].x, matchStat.p1StartPosition[1].y)
                .ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(.001F);
        bodyT1Player3.fix();
        Tween.to(bodyT1Player3, 1, .4F).target(matchStat.p1StartPosition[2].x, matchStat.p1StartPosition[2].y)
                .ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(.001F);
        bodyT2Player1.fix();
        Tween.to(bodyT2Player1, 1, .4F).target(matchStat.p2StartPosition[0].x, matchStat.p2StartPosition[0].y)
                .ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(.001F);
        bodyT2Player2.fix();
        Tween.to(bodyT2Player2, 1, .4F).target(matchStat.p2StartPosition[1].x, matchStat.p2StartPosition[1].y)
                .ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(.001F);
        bodyT2Player3.fix();
        Tween.to(bodyT2Player3, 1, .4F).target(matchStat.p2StartPosition[2].x, matchStat.p2StartPosition[2].y)
                .ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(.001F);
        mGoaler.fix();
        Tween.to(mGoaler, 1, .4F).target(Constants.SCREEN_WIDTH / 2 - Box2dWalls.GOALER_GAP, 0)
                .ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(.001F);
        ball_body.fix();
        Tween.to(ball_body, 1, .4F).target(0, 0).ease(TweenEquations.easeInBack).start(mTweenManager).delay(.001F)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                        matchStat.GAME_STATE = Constants.GAME_SHOOTING;
                    }
                });
    }

    private String obtainModelState() {
        t1Player1.setPosition(bodyT1Player1.getPosition());
        t1Player1.setAngularVelocity(bodyT1Player1.getAngularVelocity());
        t1Player1.setAngle(bodyT1Player1.getAngularVelocity());
        t1Player1.setStamina(bodyT1Player1.getStamina());
        t1Player2.setPosition(bodyT1Player2.getPosition());
        t1Player2.setAngularVelocity(bodyT1Player2.getAngularVelocity());
        t1Player2.setAngle(bodyT1Player2.getAngularVelocity());
        t1Player2.setStamina(bodyT1Player2.getStamina());
        t1Player3.setPosition(bodyT1Player3.getPosition());
        t1Player3.setAngularVelocity(bodyT1Player3.getAngularVelocity());
        t1Player3.setAngle(bodyT1Player3.getAngularVelocity());
        t1Player3.setStamina(bodyT1Player3.getStamina());

        t2Player1.setPosition(bodyT2Player1.getPosition());
        t2Player1.setAngularVelocity(bodyT2Player1.getAngularVelocity());
        t2Player1.setAngle(bodyT2Player1.getAngularVelocity());
        t2Player1.setStamina(bodyT2Player1.getStamina());
        t2Player2.setPosition(bodyT2Player2.getPosition());
        t2Player2.setAngularVelocity(bodyT2Player2.getAngularVelocity());
        t2Player2.setAngle(bodyT2Player2.getAngularVelocity());
        t2Player2.setStamina(bodyT2Player2.getStamina());
        t2Player3.setPosition(bodyT2Player3.getPosition());
        t2Player3.setAngularVelocity(bodyT2Player3.getAngularVelocity());
        t2Player3.setAngle(bodyT2Player3.getAngularVelocity());
        t2Player3.setStamina(bodyT2Player3.getStamina());

        mGoalKeeper.setPosition(mGoaler.getPosition());
        mGoalKeeper.setAngularVelocity(mGoaler.getAngularVelocity());
        mGoalKeeper.setAngle(mGoaler.getAngularVelocity());
        mBall.setPosition(ball_body.getPosition());
        mBall.setAngularVelocity(ball_body.getAngularVelocity());
        return mJson.toJson(mField);
    }

    private void checkGoal() {
        if (mField.getBall().getPosition().x > Constants.SCREEN_WIDTH / 2 - 1f && matchStat.GAME_STATE == Constants.GAME_RUNNING) {
            matchStat.GAME_STATE = Constants.GAME_GOAL;
            ball_body.fix();
            screen.goalScored();
        }
    }

    private boolean checkWin() {
        if (matchStat.Team1Goals >= matchStat.goaltoWin) {
            matchStat.GAME_STATE = Constants.GAME_END;
            for (IModelListener modelListener : mModelListeners) {
                modelListener.winEvent();
            }
            screen.winGame(true);
            return true;
        } else if (matchStat.Team1Goals >= matchStat.goaltoWin) {
            matchStat.GAME_STATE = Constants.GAME_END;
            for (IModelListener modelListener : mModelListeners) {
                modelListener.winEvent();
            }
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
            matchStat.GAME_STATE = Constants.GAME_RUNNING;
            float x1;
            float y1;
            double dia;
            if (mField.getT1p1Arrow() != null) {
                x1 = mField.getT1Player1().getPosition().x - mField.getT1p1Arrow().x;
                y1 = mField.getT1Player1().getPosition().y - mField.getT1p1Arrow().y;
                dia = Math.sqrt(x1 * x1 + y1 * y1);
                if (dia > 1.5) {
                    x1 = (float) (x1 * (1.5 / dia));
                    y1 = (float) (y1 * (1.5 / dia));
                }
                if (dia > .1f)
                    bodyT1Player1.applyImpulse(x1 * (3f + ((matchStat.T1players[matchStat.p1formation[0]][2] - 1f)) * .75f),
                            y1 * (3f + ((matchStat.T1players[matchStat.p1formation[0]][2] - 1f)) * .75f));
            }

            if (mField.getT1p2Arrow() != null) {
                x1 = mField.getT1Player2().getPosition().x - mField.getT1p2Arrow().x;
                y1 = mField.getT1Player2().getPosition().y - mField.getT1p2Arrow().y;
                dia = Math.sqrt(x1 * x1 + y1 * y1);
                if (dia > 1.5) {
                    x1 = (float) (x1 * (1.5 / dia));
                    y1 = (float) (y1 * (1.5 / dia));
                }
                if (dia > .1f)
                    bodyT1Player2.applyImpulse(x1 * (3f + ((matchStat.T1players[matchStat.p1formation[1]][2] - 1f)) * .75f),
                            y1 * (3f + ((matchStat.T1players[matchStat.p1formation[1]][2] - 1f)) * .75f));
            }

            if (mField.getT1p3Arrow() != null) {
                x1 = mField.getT1Player3().getPosition().x - mField.getT1p3Arrow().x;
                y1 = mField.getT1Player3().getPosition().y - mField.getT1p3Arrow().y;
                dia = Math.sqrt(x1 * x1 + y1 * y1);
                if (dia > 1.5) {
                    x1 = (float) (x1 * (1.5 / dia));
                    y1 = (float) (y1 * (1.5 / dia));
                }
                if (dia > .1f)
                    bodyT1Player3.applyImpulse(x1 * (3f + ((matchStat.T1players[matchStat.p1formation[2]][2] - 1f)) * .75f),
                            y1 * (3f + ((matchStat.T1players[matchStat.p1formation[2]][2] - 1f)) * .75f));
            }

            if (mField.getT2p1Arrow() != null) {
                x1 = mField.getT2Player1().getPosition().x - mField.getT2p1Arrow().x;
                y1 = mField.getT2Player1().getPosition().y - mField.getT2p1Arrow().y;
                dia = Math.sqrt(x1 * x1 + y1 * y1);
                if (dia > 1.5) {
                    x1 = (float) (x1 * (1.5 / dia));
                    y1 = (float) (y1 * (1.5 / dia));
                }
                if (dia > .1f)
                    bodyT2Player1.applyImpulse(x1 * (3f + ((matchStat.T2players[matchStat.p2formation[0]][2] - 1f)) * .75f),
                            y1 * (3f + ((matchStat.T2players[matchStat.p2formation[0]][2] - 1f)) * .75f));
            }

            if (mField.getT2p2Arrow() != null) {
                x1 = mField.getT2Player2().getPosition().x - mField.getT2p2Arrow().x;
                y1 = mField.getT2Player2().getPosition().y - mField.getT2p2Arrow().y;
                dia = Math.sqrt(x1 * x1 + y1 * y1);
                if (dia > 1.5) {
                    x1 = (float) (x1 * (1.5 / dia));
                    y1 = (float) (y1 * (1.5 / dia));
                }
                if (dia > .1f)
                    bodyT2Player2.applyImpulse(x1 * (3f + ((matchStat.T2players[matchStat.p2formation[1]][2] - 1f)) * .75f),
                            y1 * (3f + ((matchStat.T2players[matchStat.p2formation[1]][2] - 1f)) * .75f));
            }

            if (mField.getT2p3Arrow() != null) {
                x1 = mField.getT2Player3().getPosition().x - mField.getT2p3Arrow().x;
                y1 = mField.getT2Player3().getPosition().y - mField.getT2p3Arrow().y;
                dia = Math.sqrt(x1 * x1 + y1 * y1);
                if (dia > 1.5) {
                    x1 = (float) (x1 * (1.5 / dia));
                    y1 = (float) (y1 * (1.5 / dia));
                }
                if (dia > .1f)
                    bodyT2Player3.applyImpulse(x1 * (3f + ((matchStat.T2players[matchStat.p2formation[2]][2] - 1f)) * .75f),
                            y1 * (3f + ((matchStat.T2players[matchStat.p2formation[2]][2] - 1f)) * .75f));
            }

            matchStat.roundNum = matchStat.roundNum + 1;

            mField.setT1p1Arrow(null);
            mField.setT1p2Arrow(null);
            mField.setT1p3Arrow(null);

            mField.setT2p1Arrow(null);
            mField.setT2p2Arrow(null);
            mField.setT2p3Arrow(null);
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
        mField.setLeftPlayerScore(0);
        mField.setRightPlayerScore(0);
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
}
