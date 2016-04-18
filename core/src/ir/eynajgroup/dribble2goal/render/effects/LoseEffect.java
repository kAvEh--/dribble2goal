package ir.eynajgroup.dribble2goal.render.effects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import ir.eynajgroup.dribble2goal.Assets;
import ir.eynajgroup.dribble2goal.Constants;
import ir.eynajgroup.dribble2goal.MatchStats;

/**
 * Created by kAvEh on 4/13/2016.
 */
public class LoseEffect {
    Sprite mSprite1;
    Sprite mSprite2;
    TweenManager mTweenManager;
    MatchStats matchStat;
    boolean flag = true;

    public LoseEffect(TweenManager manager, MatchStats stat) {
        mSprite1 = new Sprite(Assets.getInstance().lose_image);
        mSprite1.setPosition(Constants.SCREEN_WIDTH / 2, -mSprite1.getHeight() / 2);
        mSprite1.setSize(3f, 2f);

        mSprite2 = new Sprite(Assets.getInstance().lose_image);
        mSprite2.setPosition(Constants.SCREEN_WIDTH / 2, -mSprite2.getHeight() / 2);
        mSprite2.setSize(3f, 2f);
        mTweenManager = manager;
        matchStat = stat;
    }

    public void draw(SpriteBatch batch) {
        if (flag) {
            flag = false;
            startTween();
        }
        mSprite1.draw(batch);
        mSprite2.draw(batch);
    }

    private void startTween() {
        mSprite1.setPosition(Constants.SCREEN_WIDTH / 2, -mSprite1.getHeight() / 2);
        Tween.to(mSprite1, 1, 2F).target(-mSprite1.getWidth() / 2, -mSprite1.getHeight() / 2)
                .ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(.01F);

        mSprite2.setPosition(-Constants.SCREEN_WIDTH / 2 - mSprite2.getWidth(), -mSprite2.getHeight() / 2);
        ((Tween) ((Tween) Tween.to(mSprite2, 1, 2F).target(-mSprite2.getWidth() / 2, -mSprite2.getHeight() / 2)
                .ease(TweenEquations.easeInBack)
                .start(mTweenManager)).delay(.01F))
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {

                        Tween.to(mSprite2, 1, 3F).target(-mSprite2.getWidth() / 2, -mSprite2.getHeight() / 2)
                                .ease(TweenEquations.easeInBack)
                                .start(mTweenManager).setCallback(new TweenCallback() {
                            public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                                matchStat.GAME_STATE = Constants.GAME_END;
                                flag = true;
                            }
                        });

                    }
                });
    }
}
