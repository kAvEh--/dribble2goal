package ir.eynakgroup.dribble2goal.render.effects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import ir.eynakgroup.dribble2goal.Assets;
import ir.eynakgroup.dribble2goal.Constants;
import ir.eynakgroup.dribble2goal.MatchStats;
import ir.eynakgroup.dribble2goal.template.Team;

/**
 * Created by kAvEh on 2/26/2016.
 */
public class PenaltyEffect {

    Sprite mSprite1;
    TweenManager mTweenManager;
    MatchStats matchStat;
    boolean flag = true;

    public PenaltyEffect(TweenManager manager, MatchStats stat) {
        mSprite1 = new Sprite(Assets.getInstance().penaltyImage);
        mSprite1.setPosition(Constants.SCREEN_WIDTH * -.5f, Constants.SCREEN_HEIGHT * -2f);
        mSprite1.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        mTweenManager = manager;
        matchStat = stat;
    }

    public void draw(SpriteBatch batch) {
        if (flag) {
            flag = false;
            startTween();
        }
        mSprite1.draw(batch);
    }

    private void startTween() {
        mSprite1.setSize(0, 0);
        mSprite1.setColor(mSprite1.getColor().r, mSprite1.getColor().g, mSprite1.getColor().b, 0.2f);
        mSprite1.setPosition(0, 0);
        Tween.to(mSprite1, 4, 2F).target(Constants.SCREEN_WIDTH * -.5f, Constants.SCREEN_HEIGHT * -.5f,
                Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT)
                .ease(TweenEquations.easeInCubic)
                .start(mTweenManager).delay(.01F);
        Tween.to(mSprite1, 3, 1.5F)
                .target(1)
                .ease(TweenEquations.easeInCubic)
                .start(mTweenManager).delay(0F)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                        Tween.to(mSprite1, 3, 4F)
                                .target(1)
                                .ease(TweenEquations.easeInCubic)
                                .start(mTweenManager).delay(0F)
                                .setCallback(new TweenCallback() {
                                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                                        matchStat.GAME_STATE = Constants.GAME_PRE_PENALTY;
                                        flag = true;
                                        mSprite1.setColor(mSprite1.getColor().r, mSprite1.getColor().g, mSprite1.getColor().b, 0.2f);
                                        mSprite1.setPosition(Constants.SCREEN_WIDTH * -.5f, Constants.SCREEN_HEIGHT * -2f);
                                    }
                                });
                    }
                });
    }
}
