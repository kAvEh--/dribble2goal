package ir.eynajgroup.dribble2goal.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import aurelienribon.tweenengine.TweenManager;
import ir.eynajgroup.dribble2goal.Assets;
import ir.eynajgroup.dribble2goal.Constants;
import ir.eynajgroup.dribble2goal.MatchStats;
import ir.eynajgroup.dribble2goal.model.Box2dWalls;
import ir.eynajgroup.dribble2goal.model.IRenderer;
import ir.eynajgroup.dribble2goal.render.effects.GoalEffect;
import ir.eynajgroup.dribble2goal.render.effects.LoseEffect;
import ir.eynajgroup.dribble2goal.render.effects.WinEffect;
import ir.eynajgroup.dribble2goal.render.textures.BallTexture;
import ir.eynajgroup.dribble2goal.render.textures.ITexture;
import ir.eynajgroup.dribble2goal.render.textures.PlayerTexture;
import ir.eynajgroup.dribble2goal.template.Ball;
import ir.eynajgroup.dribble2goal.template.Field;
import ir.eynajgroup.dribble2goal.template.Player;
import ir.eynajgroup.dribble2goal.template.Team;

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

    public GameRenderer(OrthographicCamera camera, SpriteBatch batch, MatchStats stat, TweenManager tmanager) {
        this.matchStat = stat;
        mCamera = camera;
        mMainBatch = batch;
        mPlayerTexture = new PlayerTexture(this.matchStat);
        mBallTexture = new BallTexture();
        mTweenManager = tmanager;

        mGoalEffect = new GoalEffect(mTweenManager, matchStat);
        mWinEffect = new WinEffect(mTweenManager, matchStat);
        mLoseEffect = new LoseEffect(mTweenManager, matchStat);
    }

    @Override
    public void render() {
        if (mField == null) return;

        mMainBatch.setProjectionMatrix(mCamera.combined);
        mMainBatch.setTransformMatrix(mCamera.view);
        mMainBatch.begin();

        Box2dWalls.createBatch(mMainBatch);

        if (matchStat.GAME_STATE == Constants.GAME_SHOOTING) {
            mPlayerTexture.drawStamina(mMainBatch, mField.getMyPlayer1(), Gdx.graphics.getDeltaTime());
            mPlayerTexture.drawStamina(mMainBatch, mField.getMyPlayer2(), Gdx.graphics.getDeltaTime());
            mPlayerTexture.drawStamina(mMainBatch, mField.getMyPlayer3(), Gdx.graphics.getDeltaTime());
            mPlayerTexture.drawStamina(mMainBatch, mField.getMyPlayer4(), Gdx.graphics.getDeltaTime());
            mPlayerTexture.drawStamina(mMainBatch, mField.getMyPlayer5(), Gdx.graphics.getDeltaTime());
        }

        mPlayerTexture.draw(mMainBatch, mField.getMyPlayer1(), 0);
        mPlayerTexture.draw(mMainBatch, mField.getMyPlayer2(), 0);
        mPlayerTexture.draw(mMainBatch, mField.getMyPlayer3(), 0);
        mPlayerTexture.draw(mMainBatch, mField.getMyPlayer4(), 0);
        mPlayerTexture.draw(mMainBatch, mField.getMyPlayer5(), 0);

        mPlayerTexture.draw(mMainBatch, mField.getOppPlayer1(), 1);
        mPlayerTexture.draw(mMainBatch, mField.getOppPlayer2(), 1);
        mPlayerTexture.draw(mMainBatch, mField.getOppPlayer3(), 1);
        mPlayerTexture.draw(mMainBatch, mField.getOppPlayer4(), 1);
        mPlayerTexture.draw(mMainBatch, mField.getOppPlayer5(), 1);

        mPlayerTexture.draw(mMainBatch, mField.getGoalKeeper(), 2);
        mBallTexture.draw(mMainBatch, mField.getBall());

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
        }
        if (matchStat.GAME_STATE == Constants.GAME_WINNER) {
            mWinEffect.draw(mMainBatch);
        }
        if (matchStat.GAME_STATE == Constants.GAME_LOSER) {
            mLoseEffect.draw(mMainBatch);
        }

        mMainBatch.end();

//        mWinEffect.draw(mMainBatch, mField.getLastWinTeam());
        mTweenManager.update(Gdx.graphics.getDeltaTime());
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
