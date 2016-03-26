package ir.eynajgroup.dribble2goal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import ir.eynajgroup.dribble2goal.Assets;
import ir.eynajgroup.dribble2goal.Constants;
import ir.eynajgroup.dribble2goal.GamePrefs;
import ir.eynajgroup.dribble2goal.MyGame;
import ir.eynajgroup.dribble2goal.Util.Util;

/**
 * Created by kAvEh on 3/19/2016.
 */
public class FindMatchScreen implements Screen {

    TweenManager mTweenManager;
    private OrthographicCamera mMainCamera;
    SpriteBatch mMainBatch;
    Image bg;

    Stage mStage;
    Table mainTable;
    Skin mSkin;

    Image main_image;
    Image my_avatar;
    Image opp_avatar;

    int counter = 0;
    int avatar_tmp = 1;

    boolean isOppReady = false;

    public FindMatchScreen() {
        mTweenManager = MyGame.mTweenManager;
        mMainBatch = new SpriteBatch();

        mSkin = new Skin(Gdx.files.internal("data/uiskin.json"));

        mStage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), mMainBatch);
        mainTable = new Table();

        bg = new Image(Assets.getInstance().main_bg);
        bg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        main_image = new Image(Assets.getInstance().findmatch_main);
        main_image.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        my_avatar = new Image(new Util().getAvatar(GamePrefs.getInstance().avatar));
        my_avatar.setSize(Gdx.graphics.getWidth() * .245f, Gdx.graphics.getHeight() * .454f);
        my_avatar.setPosition(Gdx.graphics.getWidth() * .158f, Gdx.graphics.getHeight() * .266f);

        opp_avatar = new Image(new Util().getAvatar(5));
        opp_avatar.setSize(Gdx.graphics.getWidth() * .245f, Gdx.graphics.getHeight() * .454f);
        opp_avatar.setPosition(Gdx.graphics.getWidth() * .598f, Gdx.graphics.getHeight() * .266f);

        loading();

        this.mainTable.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(mStage);
        mSkin = new Skin(Gdx.files.internal("data/uiskin.json"));

        mSkin.getFont("english").getData().scale(-.2f);

        mainTable.addActor(bg);
        mainTable.addActor(main_image);
        mainTable.addActor(my_avatar);
        mainTable.addActor(opp_avatar);

//        back.addListener(new ActorGestureListener() {
//            public void tap(InputEvent paramAnonymousInputEvent, float paramAnonymousFloat1, float paramAnonymousFloat2, int paramAnonymousInt1, int paramAnonymousInt2) {
//                MyGame.mainInstance.setMainScreen();
//            }
//
//            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                back.setSize(Gdx.graphics.getWidth() * .133f * .8f, Gdx.graphics.getHeight() * .186f * .8f);
//                back.setPosition(Gdx.graphics.getWidth() * .863f, Gdx.graphics.getHeight() * .06f);
//            }
//
//            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                back.setSize(Gdx.graphics.getWidth() * .133f, Gdx.graphics.getHeight() * .186f);
//                back.setPosition(Gdx.graphics.getWidth() * .85f, Gdx.graphics.getHeight() * .04f);
//            }
//        });

        Timer.schedule(new Timer.Task() {
            public void run() {
                counter++;
                System.out.println(counter + "========");
                if (counter > 10) {
                    isOppReady = true;
                }
            }
        }, 0.0F, .95F, 11);

        this.mainTable.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(mStage);

        mStage.addActor(this.mainTable);
    }

    public void loading() {
        if (!isOppReady) {
            Tween.to(opp_avatar, 3, .2f)
                    .target(0f).ease(TweenEquations.easeInBack)
                    .start(mTweenManager).delay(0.0F)
                    .setCallback(new TweenCallback() {
                        public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                            Tween.to(opp_avatar, 3, .2f)
                                    .target(1f).ease(TweenEquations.easeInBack)
                                    .start(mTweenManager).delay(0.0F)
                                    .setCallback(new TweenCallback() {
                                        public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                                            avatar_tmp++;
                                            if (avatar_tmp == 7)
                                                avatar_tmp = 1;
                                            opp_avatar.setDrawable(new SpriteDrawable(new Sprite(new Util().getAvatar(avatar_tmp))));
                                            loading();
                                        }
                                    });
                        }
                    });
        }
    }

    @Override
    public void show() {
        mMainCamera = new OrthographicCamera(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        mMainCamera.update();

        this.mainTable.setPosition(0f, 0f);
        this.mainTable.setVisible(true);
        this.mainTable.setColor(1.0F, 1.0F, 1.0F, 0F);
        Tween.to(this.mainTable, 5, .3f)
                .target(1f).ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(0.0F)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {

                    }
                });
    }

    @Override
    public void render(float delta) {
        mStage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        mMainBatch.setTransformMatrix(mMainCamera.view);
        mMainBatch.setProjectionMatrix(mMainCamera.projection);

        mStage.act(delta);
        mStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        float aspectRatio = (float) width / height;
        mMainCamera.viewportWidth = Constants.SCREEN_HEIGHT * aspectRatio;
        mMainCamera.update();

        mStage.getViewport().update(width, (int) (Constants.SCREEN_HEIGHT * aspectRatio), true);
//        mStage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        mStage.dispose();
    }
}