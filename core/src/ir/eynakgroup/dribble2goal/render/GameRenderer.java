package ir.eynakgroup.dribble2goal.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import aurelienribon.tweenengine.TweenManager;
import ir.eynakgroup.dribble2goal.Assets;
import ir.eynakgroup.dribble2goal.Constants;
import ir.eynakgroup.dribble2goal.MatchStats;
import ir.eynakgroup.dribble2goal.model.Box2dWalls;
import ir.eynakgroup.dribble2goal.model.IRenderer;
import ir.eynakgroup.dribble2goal.render.effects.GoalEffect;
import ir.eynakgroup.dribble2goal.render.effects.LoseEffect;
import ir.eynakgroup.dribble2goal.render.effects.PenaltyEffect;
import ir.eynakgroup.dribble2goal.render.effects.SecondHalfEffect;
import ir.eynakgroup.dribble2goal.render.effects.WinEffect;
import ir.eynakgroup.dribble2goal.render.textures.BallTexture;
import ir.eynakgroup.dribble2goal.render.textures.ITexture;
import ir.eynakgroup.dribble2goal.render.textures.PlayerTexture;
import ir.eynakgroup.dribble2goal.render.textures.ProgressCircle;
import ir.eynakgroup.dribble2goal.template.Ball;
import ir.eynakgroup.dribble2goal.template.Field;
import ir.eynakgroup.dribble2goal.template.Player;
import ir.eynakgroup.dribble2goal.template.Team;

/**
 * Created by kAvEh on 2/19/2016.
 */
public class GameRenderer implements IRenderer {
    private final OrthographicCamera mCamera;
    private final SpriteBatch mMainBatch;

    private Field mField;
    private final ITexture<Player> mPlayerTexture;
    private final ITexture<Ball> mBallTexture;

    MatchStats matchStat;

    TweenManager mTweenManager;
    private GoalEffect mGoalEffect;
    private WinEffect mWinEffect;
    private LoseEffect mLoseEffect;
    private PenaltyEffect mPenaltyEffect;
    private SecondHalfEffect m2ndHalfEffect;
    PolygonSpriteBatch pBatch;
    Stage mStage;
    //    ProgressCircle[] myStamina;
//    ProgressCircle[] oppStamina;
    Image[] myPlayers;
    Image[] myPlayersOn;
    Image[] oppPlayers;
    Image[] myStable;
    Image[] oppStable;
    Image[] balls;
    Image[] keeper;

    Vector2 tmp_position;
    float ratio;

    public GameRenderer(OrthographicCamera camera, SpriteBatch batch, MatchStats stat, TweenManager tmanager,
                        Stage stage, Image[] mplayers, Image[] oppplayers,
                        Image[] mStables, Image[] oStables, Image[] mPlayerson, Image[] ball, Image[] kpr) {
        this.matchStat = stat;
        mCamera = camera;
        mMainBatch = batch;
        pBatch = new PolygonSpriteBatch();
        mStage = stage;
        mPlayerTexture = new PlayerTexture(this.matchStat, pBatch, mStage);
        mBallTexture = new BallTexture();
        mTweenManager = tmanager;

        this.myPlayers = mplayers;
        this.myPlayersOn = mPlayerson;
        this.oppPlayers = oppplayers;
        this.myStable = mStables;
        this.oppStable = oStables;
        this.balls = ball;
        this.keeper = kpr;

        mGoalEffect = new GoalEffect(mTweenManager, matchStat);
        mWinEffect = new WinEffect(mTweenManager, matchStat);
        mLoseEffect = new LoseEffect(mTweenManager, matchStat);
        mPenaltyEffect = new PenaltyEffect(mTweenManager, matchStat);
        m2ndHalfEffect = new SecondHalfEffect(mTweenManager, matchStat);

        ratio = Constants.HUD_SCREEN_WIDTH / Constants.SCREEN_WIDTH;
    }

    @Override
    public void render() {
        if (mField == null) return;

        mMainBatch.setProjectionMatrix(mCamera.combined);
        mMainBatch.setTransformMatrix(mCamera.view);
        mMainBatch.begin();

//        Box2dWalls.createBatch(mMainBatch);

//        mMainBatch.draw(Assets.getInstance().stamina, mField.getMyPlayer1().getPosition().x - mField.getMyPlayer1().getRadius() * 1f,
//                mField.getMyPlayer1().getPosition().y - mField.getMyPlayer1().getRadius() * 1f,
//                mField.getMyPlayer1().getRadius() * 2f, mField.getMyPlayer1().getRadius() * 2f);
//
//        mMainBatch.draw(Assets.getInstance().stamina, mField.getMyPlayer2().getPosition().x - mField.getMyPlayer2().getRadius() * 1f,
//                mField.getMyPlayer2().getPosition().y - mField.getMyPlayer2().getRadius() * 1f,
//                mField.getMyPlayer2().getRadius() * 2f, mField.getMyPlayer2().getRadius() * 2f);
//
//        mMainBatch.draw(Assets.getInstance().stamina, mField.getMyPlayer3().getPosition().x - mField.getMyPlayer3().getRadius() * 1f,
//                mField.getMyPlayer3().getPosition().y - mField.getMyPlayer3().getRadius() * 1f,
//                mField.getMyPlayer3().getRadius() * 2f, mField.getMyPlayer3().getRadius() * 2f);
//
//        mMainBatch.draw(Assets.getInstance().stamina, mField.getOppPlayer1().getPosition().x - mField.getOppPlayer1().getRadius() * 1f,
//                mField.getOppPlayer1().getPosition().y - mField.getOppPlayer1().getRadius() * 1f,
//                mField.getOppPlayer1().getRadius() * 2f, mField.getOppPlayer1().getRadius() * 2f);
//
//        mMainBatch.draw(Assets.getInstance().stamina, mField.getOppPlayer2().getPosition().x - mField.getOppPlayer2().getRadius() * 1f,
//                mField.getOppPlayer2().getPosition().y - mField.getOppPlayer2().getRadius() * 1f,
//                mField.getOppPlayer2().getRadius() * 2f, mField.getOppPlayer2().getRadius() * 2f);
//
//        mMainBatch.draw(Assets.getInstance().stamina, mField.getOppPlayer3().getPosition().x - mField.getOppPlayer3().getRadius() * 1f,
//                mField.getOppPlayer3().getPosition().y - mField.getOppPlayer3().getRadius() * 1f,
//                mField.getOppPlayer3().getRadius() * 2f, mField.getOppPlayer3().getRadius() * 2f);
//
//        mMainBatch.draw(Assets.getInstance().stamina, mField.getGoalKeeper().getPosition().x - mField.getGoalKeeper().getRadius() * 1f,
//                mField.getGoalKeeper().getPosition().y - mField.getGoalKeeper().getRadius() * 1f,
//                mField.getGoalKeeper().getRadius() * 2f, mField.getGoalKeeper().getRadius() * 2f);

        myPlayers[0].setSize(mField.getMyPlayer1().getRadius() * ratio * 3.2f, mField.getMyPlayer1().getRadius() * ratio * 3.2f);
        myPlayersOn[0].setSize(mField.getMyPlayer1().getRadius() * ratio * 3.2f, mField.getMyPlayer1().getRadius() * ratio * 3.2f);
        myStable[0].setSize(mField.getMyPlayer1().getRadius() * ratio * 3.2f, mField.getMyPlayer1().getRadius() * ratio * 3.2f);
        myPlayers[1].setSize(mField.getMyPlayer2().getRadius() * ratio * 3.2f, mField.getMyPlayer2().getRadius() * ratio * 3.2f);
        myPlayersOn[1].setSize(mField.getMyPlayer2().getRadius() * ratio * 3.2f, mField.getMyPlayer2().getRadius() * ratio * 3.2f);
        myStable[1].setSize(mField.getMyPlayer2().getRadius() * ratio * 3.2f, mField.getMyPlayer2().getRadius() * ratio * 3.2f);
        myPlayers[2].setSize(mField.getMyPlayer3().getRadius() * ratio * 3.2f, mField.getMyPlayer3().getRadius() * ratio * 3.2f);
        myPlayersOn[2].setSize(mField.getMyPlayer3().getRadius() * ratio * 3.2f, mField.getMyPlayer3().getRadius() * ratio * 3.2f);
        myStable[2].setSize(mField.getMyPlayer3().getRadius() * ratio * 3.2f, mField.getMyPlayer3().getRadius() * ratio * 3.2f);
        myPlayers[3].setSize(mField.getMyPlayer4().getRadius() * ratio * 3.2f, mField.getMyPlayer4().getRadius() * ratio * 3.2f);
        myPlayersOn[3].setSize(mField.getMyPlayer4().getRadius() * ratio * 3.2f, mField.getMyPlayer4().getRadius() * ratio * 3.2f);
        myStable[3].setSize(mField.getMyPlayer4().getRadius() * ratio * 3.2f, mField.getMyPlayer4().getRadius() * ratio * 3.2f);
        myPlayers[4].setSize(mField.getMyPlayer5().getRadius() * ratio * 3.2f, mField.getMyPlayer5().getRadius() * ratio * 3.2f);
        myPlayersOn[4].setSize(mField.getMyPlayer5().getRadius() * ratio * 3.2f, mField.getMyPlayer5().getRadius() * ratio * 3.2f);
        myStable[4].setSize(mField.getMyPlayer5().getRadius() * ratio * 3.2f, mField.getMyPlayer5().getRadius() * ratio * 3.2f);

        oppPlayers[0].setSize(mField.getOppPlayer1().getRadius() * ratio * 3.2f, mField.getOppPlayer1().getRadius() * ratio * 3.2f);
        oppStable[0].setSize(mField.getOppPlayer1().getRadius() * ratio * 3.2f, mField.getOppPlayer1().getRadius() * ratio * 3.2f);
        oppPlayers[1].setSize(mField.getOppPlayer2().getRadius() * ratio * 3.2f, mField.getOppPlayer2().getRadius() * ratio * 3.2f);
        oppStable[1].setSize(mField.getOppPlayer2().getRadius() * ratio * 3.2f, mField.getOppPlayer2().getRadius() * ratio * 3.2f);
        oppPlayers[2].setSize(mField.getOppPlayer3().getRadius() * ratio * 3.2f, mField.getOppPlayer3().getRadius() * ratio * 3.2f);
        oppStable[2].setSize(mField.getOppPlayer3().getRadius() * ratio * 3.2f, mField.getOppPlayer3().getRadius() * ratio * 3.2f);
        oppPlayers[3].setSize(mField.getOppPlayer4().getRadius() * ratio * 3.2f, mField.getOppPlayer4().getRadius() * ratio * 3.2f);
        oppStable[3].setSize(mField.getOppPlayer4().getRadius() * ratio * 3.2f, mField.getOppPlayer4().getRadius() * ratio * 3.2f);
        oppPlayers[4].setSize(mField.getOppPlayer5().getRadius() * ratio * 3.2f, mField.getOppPlayer5().getRadius() * ratio * 3.2f);
        oppStable[4].setSize(mField.getOppPlayer5().getRadius() * ratio * 3.2f, mField.getOppPlayer5().getRadius() * ratio * 3.2f);


        tmp_position = convertScale(mField.getMyPlayer1().getPosition().x - mField.getMyPlayer1().getRadius() * 1.6f, mField.getMyPlayer1().getPosition().y - mField.getMyPlayer1().getRadius() * 1.6f);
        myPlayers[0].setPosition(tmp_position.x, tmp_position.y);
        myPlayersOn[0].setPosition(tmp_position.x, tmp_position.y);
        myStable[0].setPosition(tmp_position.x, tmp_position.y);
        tmp_position = convertScale(mField.getMyPlayer2().getPosition().x - mField.getMyPlayer2().getRadius() * 1.6f, mField.getMyPlayer2().getPosition().y - mField.getMyPlayer2().getRadius() * 1.6f);
        myPlayers[1].setPosition(tmp_position.x, tmp_position.y);
        myPlayersOn[1].setPosition(tmp_position.x, tmp_position.y);
        myStable[1].setPosition(tmp_position.x, tmp_position.y);
        tmp_position = convertScale(mField.getMyPlayer3().getPosition().x - mField.getMyPlayer3().getRadius() * 1.6f, mField.getMyPlayer3().getPosition().y - mField.getMyPlayer3().getRadius() * 1.6f);
        myPlayers[2].setPosition(tmp_position.x, tmp_position.y);
        myPlayersOn[2].setPosition(tmp_position.x, tmp_position.y);
        myStable[2].setPosition(tmp_position.x, tmp_position.y);
        tmp_position = convertScale(mField.getMyPlayer4().getPosition().x - mField.getMyPlayer4().getRadius() * 1.6f, mField.getMyPlayer4().getPosition().y - mField.getMyPlayer4().getRadius() * 1.6f);
        myPlayers[3].setPosition(tmp_position.x, tmp_position.y);
        myPlayersOn[3].setPosition(tmp_position.x, tmp_position.y);
        myStable[3].setPosition(tmp_position.x, tmp_position.y);
        tmp_position = convertScale(mField.getMyPlayer5().getPosition().x - mField.getMyPlayer5().getRadius() * 1.6f, mField.getMyPlayer5().getPosition().y - mField.getMyPlayer5().getRadius() * 1.6f);
        myPlayers[4].setPosition(tmp_position.x, tmp_position.y);
        myPlayersOn[4].setPosition(tmp_position.x, tmp_position.y);
        myStable[4].setPosition(tmp_position.x, tmp_position.y);

        tmp_position = convertScale(mField.getOppPlayer1().getPosition().x - mField.getOppPlayer1().getRadius() * 1.6f, mField.getOppPlayer1().getPosition().y - mField.getOppPlayer1().getRadius() * 1.6f);
        oppPlayers[0].setPosition(tmp_position.x, tmp_position.y);
        oppStable[0].setPosition(tmp_position.x, tmp_position.y);
        tmp_position = convertScale(mField.getOppPlayer2().getPosition().x - mField.getOppPlayer2().getRadius() * 1.6f, mField.getOppPlayer2().getPosition().y - mField.getOppPlayer2().getRadius() * 1.6f);
        oppPlayers[1].setPosition(tmp_position.x, tmp_position.y);
        oppStable[1].setPosition(tmp_position.x, tmp_position.y);
        tmp_position = convertScale(mField.getOppPlayer3().getPosition().x - mField.getOppPlayer3().getRadius() * 1.6f, mField.getOppPlayer3().getPosition().y - mField.getOppPlayer3().getRadius() * 1.6f);
        oppPlayers[2].setPosition(tmp_position.x, tmp_position.y);
        oppStable[2].setPosition(tmp_position.x, tmp_position.y);
        tmp_position = convertScale(mField.getOppPlayer4().getPosition().x - mField.getOppPlayer4().getRadius() * 1.6f, mField.getOppPlayer4().getPosition().y - mField.getOppPlayer4().getRadius() * 1.6f);
        oppPlayers[3].setPosition(tmp_position.x, tmp_position.y);
        oppStable[3].setPosition(tmp_position.x, tmp_position.y);
        tmp_position = convertScale(mField.getOppPlayer5().getPosition().x - mField.getOppPlayer5().getRadius() * 1.6f, mField.getOppPlayer5().getPosition().y - mField.getOppPlayer5().getRadius() * 1.6f);
        oppPlayers[4].setPosition(tmp_position.x, tmp_position.y);
        oppStable[4].setPosition(tmp_position.x, tmp_position.y);

        keeper[0].setSize(mField.getGoalKeeper().getRadius() * ratio * 3.2f, mField.getGoalKeeper().getRadius() * ratio * 3.2f);
        keeper[1].setSize(mField.getGoalKeeper().getRadius() * ratio * 3.2f, mField.getGoalKeeper().getRadius() * ratio * 3.2f);

        tmp_position = convertScale(mField.getGoalKeeper().getPosition().x - mField.getGoalKeeper().getRadius() * 1.6f, mField.getGoalKeeper().getPosition().y - mField.getGoalKeeper().getRadius() * 1.6f);
        keeper[0].setPosition(tmp_position.x, tmp_position.y);
        keeper[1].setPosition(tmp_position.x, tmp_position.y);

        balls[0].setSize(mField.getBall().getRadius() * ratio * 3f, mField.getBall().getRadius() * ratio * 3f);
        balls[1].setSize(mField.getBall().getRadius() * ratio * 2f, mField.getBall().getRadius() * ratio * 2f);
        balls[2].setSize(mField.getBall().getRadius() * ratio * 2f, mField.getBall().getRadius() * ratio * 2f);

        tmp_position = convertScale(mField.getBall().getPosition().x - mField.getBall().getRadius() * 1.5f, mField.getBall().getPosition().y - mField.getBall().getRadius() * 1.5f);
        balls[0].setPosition(tmp_position.x, tmp_position.y);
        tmp_position = convertScale(mField.getBall().getPosition().x - mField.getBall().getRadius(), mField.getBall().getPosition().y - mField.getBall().getRadius());
        balls[1].setOrigin(mField.getBall().getRadius() * ratio, mField.getBall().getRadius() * ratio);
        balls[1].setRotation(balls[1].getRotation() + mField.getBall().getAngularVelocity());
        balls[1].setPosition(tmp_position.x, tmp_position.y);
        balls[2].setPosition(tmp_position.x, tmp_position.y);

        if (mField != null && matchStat.myShootDirection != null && matchStat.GAME_STATE == Constants.GAME_SHOOTING) {
            if (matchStat.myPlayerShooting == 1) {
                drawLine(mMainBatch, mField.getMyPlayer1().getPosition().x, mField.getMyPlayer1().getPosition().y,
                        matchStat.myShootDirection.x, matchStat.myShootDirection.y);
            } else if (matchStat.myPlayerShooting == 2) {
                drawLine(mMainBatch, mField.getMyPlayer2().getPosition().x, mField.getMyPlayer2().getPosition().y,
                        matchStat.myShootDirection.x, matchStat.myShootDirection.y);
            } else if (matchStat.myPlayerShooting == 3) {
                drawLine(mMainBatch, mField.getMyPlayer3().getPosition().x, mField.getMyPlayer3().getPosition().y,
                        matchStat.myShootDirection.x, matchStat.myShootDirection.y);
            } else if (matchStat.myPlayerShooting == 4) {
                drawLine(mMainBatch, mField.getMyPlayer4().getPosition().x, mField.getMyPlayer4().getPosition().y,
                        matchStat.myShootDirection.x, matchStat.myShootDirection.y);
            } else if (matchStat.myPlayerShooting == 5) {
                drawLine(mMainBatch, mField.getMyPlayer5().getPosition().x, mField.getMyPlayer5().getPosition().y,
                        matchStat.myShootDirection.x, matchStat.myShootDirection.y);
            }
        }

        if (matchStat.GAME_STATE == Constants.GAME_GOAL) {
            mGoalEffect.draw(mMainBatch, Team.PLAYER1);
        } else if (matchStat.GAME_STATE == Constants.GAME_WINNER) {
            mWinEffect.draw(mMainBatch);
        } else if (matchStat.GAME_STATE == Constants.GAME_LOSER) {
            mLoseEffect.draw(mMainBatch);
        } else if (matchStat.GAME_STATE == Constants.GAME_PENALTY) {
            mPenaltyEffect.draw(mMainBatch);
        } else if (matchStat.GAME_STATE == Constants.GAME_2ND_HALF) {
            m2ndHalfEffect.draw(mMainBatch);
        }

        mMainBatch.end();

        mTweenManager.update(Gdx.graphics.getDeltaTime());
    }

    public Vector2 convertScale(float x, float y) {
        float tmpX = (x + (Constants.SCREEN_WIDTH / 2f)) * (Constants.HUD_SCREEN_WIDTH / Constants.SCREEN_WIDTH);
        float tmpY = (y + (Constants.SCREEN_HEIGHT / 2f)) * (Constants.HUD_SCREEN_HEIGHT / Constants.SCREEN_HEIGHT);
        return new Vector2(tmpX, tmpY);
    }

    void drawLine(SpriteBatch batch, float x1, float y1, float x2, float y2) {
        float dx = x2 - x1;
        float dy = y2 - y1;
        float dist = Math.min((float) Math.sqrt(dx * dx + dy * dy), 1f);
        float rad = (float) Math.atan2(dy, dx);
        Texture mTexture = Assets.getInstance().arrow;

        if (dist < .1f) {
            return;
        }

        dist += .7f;
        Sprite arrow = new Sprite(mTexture);
        arrow.setSize(.4f, .4f);
        arrow.setPosition(x1 + (dist - .4f), y1 - .2f);
        arrow.setOrigin(-(dist - .4f), .2f);
        arrow.setRotation((float) Math.toDegrees(rad) + 180);
        arrow.draw(batch);
    }

    @Override
    public void updateModel(Field model) {
        mField = model;
    }

    @Override
    public void playWinEffect() {

    }

    @Override
    public void stopWinEffect() {

    }

    @Override
    public void dispose() {
        mMainBatch.dispose();
    }
}
