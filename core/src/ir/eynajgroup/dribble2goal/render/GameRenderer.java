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
import ir.eynajgroup.dribble2goal.model.IRenderer;
import ir.eynajgroup.dribble2goal.render.effects.GoalEffect;
import ir.eynajgroup.dribble2goal.render.effects.IEffect;
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
    private final IEffect<Team> mWinEffect;

    MatchStats gameStat;

    TweenManager mTweenManager;
    private GoalEffect mGoalEffect;

    public GameRenderer(OrthographicCamera camera, OrthographicCamera hudCamera, MatchStats stat, TweenManager tmanager) {
        this.gameStat = stat;
        mCamera = camera;
        mMainBatch = new SpriteBatch();
        mPlayerTexture = new PlayerTexture(this.gameStat);
        mBallTexture = new BallTexture();
        mWinEffect = new WinEffect(hudCamera);
        mTweenManager = tmanager;

        mGoalEffect = new GoalEffect(mTweenManager, gameStat);
    }

    @Override
    public void render() {
        if (mField == null) return;

        mMainBatch.setProjectionMatrix(mCamera.combined);
        mMainBatch.setTransformMatrix(mCamera.view);
        mMainBatch.begin();

//        mMainBatch.draw(Assets.getInstance().main_bg, -Constants.SCREEN_WIDTH / 2, -Constants.SCREEN_HEIGHT / 2,
//                Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

//        Box2dWalls.createBatch(mMainBatch);

        if (gameStat.GAME_STATE == Constants.GAME_SHOOTING) {
            if (gameStat.isMeFirst) {
                mPlayerTexture.drawStamina(mMainBatch, mField.getT1Player1(), Gdx.graphics.getDeltaTime());
                mPlayerTexture.drawStamina(mMainBatch, mField.getT1Player2(), Gdx.graphics.getDeltaTime());
                mPlayerTexture.drawStamina(mMainBatch, mField.getT1Player3(), Gdx.graphics.getDeltaTime());
            } else {
                mPlayerTexture.drawStamina(mMainBatch, mField.getT2Player1(), Gdx.graphics.getDeltaTime());
                mPlayerTexture.drawStamina(mMainBatch, mField.getT2Player2(), Gdx.graphics.getDeltaTime());
                mPlayerTexture.drawStamina(mMainBatch, mField.getT2Player3(), Gdx.graphics.getDeltaTime());
            }
        }

        mPlayerTexture.draw(mMainBatch, mField.getT1Player1(), 0);
        mPlayerTexture.draw(mMainBatch, mField.getT1Player2(), 0);
        mPlayerTexture.draw(mMainBatch, mField.getT1Player3(), 0);

        mPlayerTexture.draw(mMainBatch, mField.getT2Player1(), 1);
        mPlayerTexture.draw(mMainBatch, mField.getT2Player2(), 1);
        mPlayerTexture.draw(mMainBatch, mField.getT2Player3(), 1);

        mPlayerTexture.draw(mMainBatch, mField.getGoalKeeper(), 2);
        mBallTexture.draw(mMainBatch, mField.getBall());

        if (mField != null && mField.getT1p1Arrow() != null && gameStat.GAME_STATE == Constants.GAME_SHOOTING) {
            drawLine(mMainBatch, mField.getT1Player1().getPosition().x, mField.getT1Player1().getPosition().y,
                    mField.getT1p1Arrow().x, mField.getT1p1Arrow().y);
        }
        if (mField != null && mField.getT1p2Arrow() != null && gameStat.GAME_STATE == Constants.GAME_SHOOTING) {
            drawLine(mMainBatch, mField.getT1Player2().getPosition().x, mField.getT1Player2().getPosition().y,
                    mField.getT1p2Arrow().x, mField.getT1p2Arrow().y);
        }
        if (mField != null && mField.getT1p3Arrow() != null && gameStat.GAME_STATE == Constants.GAME_SHOOTING) {
            drawLine(mMainBatch, mField.getT1Player3().getPosition().x, mField.getT1Player3().getPosition().y,
                    mField.getT1p3Arrow().x, mField.getT1p3Arrow().y);
        }

        if (mField != null && mField.getT2p1Arrow() != null && gameStat.GAME_STATE == Constants.GAME_SHOOTING) {
            drawLine(mMainBatch, mField.getT2Player1().getPosition().x, mField.getT2Player1().getPosition().y,
                    mField.getT2p1Arrow().x, mField.getT2p1Arrow().y);
        }
        if (mField != null && mField.getT2p2Arrow() != null && gameStat.GAME_STATE == Constants.GAME_SHOOTING) {
            drawLine(mMainBatch, mField.getT2Player2().getPosition().x, mField.getT2Player2().getPosition().y,
                    mField.getT2p2Arrow().x, mField.getT2p2Arrow().y);
        }
        if (mField != null && mField.getT2p3Arrow() != null && gameStat.GAME_STATE == Constants.GAME_SHOOTING) {
            drawLine(mMainBatch, mField.getT2Player3().getPosition().x, mField.getT2Player3().getPosition().y,
                    mField.getT2p3Arrow().x, mField.getT2p3Arrow().y);
        }
//        mMainBatch.draw(Assets.getInstance().goal_sample, -Constants.SCREEN_WIDTH / 2, -Constants.SCREEN_HEIGHT / 2,
//                Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
//        mMainBatch.draw(new Util().getStadiumArc(gameStat.matchLevel), -Constants.SCREEN_WIDTH / 2, -Constants.SCREEN_HEIGHT / 2,
//                Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        if (gameStat.GAME_STATE == Constants.GAME_GOAL) {
            mGoalEffect.draw(mMainBatch, Team.PLAYER1);
        }

        mMainBatch.end();

        mWinEffect.draw(mMainBatch, mField.getLastWinTeam());
        mTweenManager.update(Gdx.graphics.getDeltaTime());
    }

    void drawLine(SpriteBatch batch, float x1, float y1, float x2, float y2) {
        float dx = x2 - x1;
        float dy = y2 - y1;
        float dist = Math.min((float) Math.sqrt(dx * dx + dy * dy), 1f);
//        float dist = (float) Math.sqrt(dx * dx + dy * dy);
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
//        arrow.setColor(.8f,.5f,.1f,1f);
        arrow.draw(batch);
    }

    @Override
    public void updateModel(Field model) {
        mField = model;
    }

    @Override
    public void playWinEffect() {
        mWinEffect.play();
    }

    @Override
    public void stopWinEffect() {
        mWinEffect.stop();
    }

    @Override
    public void dispose() {
        mWinEffect.dispose();
        mMainBatch.dispose();
    }
}
