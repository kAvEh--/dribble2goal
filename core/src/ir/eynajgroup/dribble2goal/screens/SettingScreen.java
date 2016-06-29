package ir.eynajgroup.dribble2goal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
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

/**
 * Created by kAvEh on 3/19/2016.
 */
public class SettingScreen implements Screen {

    TweenManager mTweenManager;
    private OrthographicCamera mMainCamera;
    SpriteBatch mMainBatch;
    Image bg;

    Stage mStage;
    Table mainTable;
    Skin mSkin;

    Image music_icon;
    Image music_bg;
    Image music_button;
    Image effect_bg;
    Image effect_button;
    Image vibrate_bg;
    Image vibrate_button;

    Table setting_holder;

    Image back;

    public SettingScreen() {
        mTweenManager = MyGame.mTweenManager;
        mMainBatch = new SpriteBatch();

        mSkin = new Skin(Gdx.files.internal("data/uiskin.json"));

        mStage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), mMainBatch);
        mainTable = new Table();

        bg = new Image(Assets.getInstance().main_bg);
        bg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        music_icon = new Image(Assets.getInstance().setting_music_icon);
        music_icon.setSize(Gdx.graphics.getWidth() * .133f, Gdx.graphics.getHeight() * .186f);
        music_icon.setPosition(Gdx.graphics.getWidth() * .4335f, Gdx.graphics.getHeight() * .78f);

        setting_holder = new Table();
        Table music_inside = new Table();
        ScrollPane pane = new ScrollPane(music_inside);
        pane.setForceScroll(false, true);
        setting_holder.add(pane).width(Gdx.graphics.getWidth() * .66f).height(Gdx.graphics.getHeight() * .75f);
        setting_holder.row();
        setting_holder.setBounds(0, 0, Gdx.graphics.getWidth() * .66f, Gdx.graphics.getHeight() * .75f);

        setting_holder.setPosition(Gdx.graphics.getWidth() * .17f, Gdx.graphics.getHeight() * .01f);

        Table table1 = new Table();
        Image music = new Image(Assets.getInstance().setting_music_bg);
        music.setSize(Gdx.graphics.getWidth() * .66f, Gdx.graphics.getHeight() * .127f);
        table1.addActor(music);
        music_bg = new Image(Assets.getInstance().setting_button_bg);
        music_bg.setSize(Gdx.graphics.getWidth() * .068f, Gdx.graphics.getHeight() * .077f);
        music_bg.setPosition(Gdx.graphics.getWidth() * .58f, Gdx.graphics.getHeight() * .025f);
        music_button = new Image(Assets.getInstance().setting_button);
        music_button.setSize(Gdx.graphics.getWidth() * .06f, Gdx.graphics.getHeight() * .108f);
        if (GamePrefs.getInstance().isMusicOn() == 1) {
            music_button.setPosition(Gdx.graphics.getWidth() * .6f, Gdx.graphics.getHeight() * .003f);
        } else {
            music_button.setPosition(Gdx.graphics.getWidth() * .57f, Gdx.graphics.getHeight() * .003f);
            music_bg.setColor(1f, 1f, 1f, .5f);
            music_button.setColor(1f, 1f, 1f, .9f);
        }
        table1.addActor(music_bg);
        table1.addActor(music_button);
        music_inside.add(table1).width(Gdx.graphics.getWidth() * .66f).height(Gdx.graphics.getHeight() * .127f)
                .pad(Gdx.graphics.getHeight() * .02f, 0f, Gdx.graphics.getHeight() * .01f, 0f);
        music_inside.row();

        Table table2 = new Table();
        Image effects = new Image(Assets.getInstance().setting_sound_bg);
        effects.setSize(Gdx.graphics.getWidth() * .66f, Gdx.graphics.getHeight() * .127f);
        table2.addActor(effects);
        effect_bg = new Image(Assets.getInstance().setting_button_bg);
        effect_bg.setSize(Gdx.graphics.getWidth() * .068f, Gdx.graphics.getHeight() * .077f);
        effect_bg.setPosition(Gdx.graphics.getWidth() * .58f, Gdx.graphics.getHeight() * .025f);
        effect_button = new Image(Assets.getInstance().setting_button);
        effect_button.setSize(Gdx.graphics.getWidth() * .06f, Gdx.graphics.getHeight() * .108f);
        if (GamePrefs.getInstance().isEffectOn() == 1) {
            effect_button.setPosition(Gdx.graphics.getWidth() * .6f, Gdx.graphics.getHeight() * .003f);
        } else {
            effect_button.setPosition(Gdx.graphics.getWidth() * .57f, Gdx.graphics.getHeight() * .003f);
            effect_bg.setColor(1f, 1f, 1f, .5f);
            effect_button.setColor(1f, 1f, 1f, .9f);
        }
        table2.addActor(effect_bg);
        table2.addActor(effect_button);
        music_inside.add(table2).width(Gdx.graphics.getWidth() * .66f).height(Gdx.graphics.getHeight() * .127f)
                .pad(Gdx.graphics.getHeight() * .02f, 0f, Gdx.graphics.getHeight() * .01f, 0f);
        music_inside.row();

        Table table3 = new Table();
        Image vibrate = new Image(Assets.getInstance().setting_vibe_bg);
        vibrate.setSize(Gdx.graphics.getWidth() * .66f, Gdx.graphics.getHeight() * .127f);
        table3.addActor(vibrate);
        vibrate_bg = new Image(Assets.getInstance().setting_button_bg);
        vibrate_bg.setSize(Gdx.graphics.getWidth() * .068f, Gdx.graphics.getHeight() * .077f);
        vibrate_bg.setPosition(Gdx.graphics.getWidth() * .58f, Gdx.graphics.getHeight() * .025f);
        vibrate_button = new Image(Assets.getInstance().setting_button);
        vibrate_button.setSize(Gdx.graphics.getWidth() * .06f, Gdx.graphics.getHeight() * .108f);
        if (GamePrefs.getInstance().isVibrateOn() == 1) {
            vibrate_button.setPosition(Gdx.graphics.getWidth() * .6f, Gdx.graphics.getHeight() * .003f);
        } else {
            vibrate_button.setPosition(Gdx.graphics.getWidth() * .57f, Gdx.graphics.getHeight() * .003f);
            vibrate_bg.setColor(1f, 1f, 1f, .5f);
            vibrate_button.setColor(1f, 1f, 1f, .9f);
        }
        table3.addActor(vibrate_bg);
        table3.addActor(vibrate_button);
        music_inside.add(table3).width(Gdx.graphics.getWidth() * .66f).height(Gdx.graphics.getHeight() * .127f)
                .pad(Gdx.graphics.getHeight() * .02f, 0f, Gdx.graphics.getHeight() * .01f, 0f);
        music_inside.row();

        this.mainTable.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(mStage);
        mSkin = new Skin(Gdx.files.internal("data/uiskin.json"));

        mSkin.getFont("english").getData().scale(-.2f);

        back = new Image(Assets.getInstance().icon_back);
        back.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
        back.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .04f);

        mainTable.addActor(bg);
        mainTable.addActor(music_icon);
        mainTable.addActor(back);
        mainTable.addActor(setting_holder);

        back.addListener(new ActorGestureListener() {
            public void tap(InputEvent paramAnonymousInputEvent, float paramAnonymousFloat1, float paramAnonymousFloat2, int paramAnonymousInt1, int paramAnonymousInt2) {
                MyGame.mainInstance.setMainScreen();
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                back.setSize(Constants.HUD_SCREEN_WIDTH * .133f * .8f, Constants.HUD_SCREEN_HEIGHT * .186f * .8f);
                back.setPosition(Constants.HUD_SCREEN_WIDTH * .863f, Constants.HUD_SCREEN_HEIGHT * .06f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                back.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
                back.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .04f);
            }
        });

        music_button.addListener(new ActorGestureListener() {
            public void tap(InputEvent paramAnonymousInputEvent, float paramAnonymousFloat1, float paramAnonymousFloat2, int paramAnonymousInt1, int paramAnonymousInt2) {
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                float target;
                float opacity1;
                float opacity2;
                if (GamePrefs.getInstance().isMusicOn() == 1) {
                    target = Gdx.graphics.getWidth() * .57f;
                    opacity1 = .5f;
                    opacity2 = .9f;
                    GamePrefs.getInstance().setMusicState(0);
                } else {
                    target = Gdx.graphics.getWidth() * .6f;
                    opacity1 = 1f;
                    opacity2 = 1f;
                    GamePrefs.getInstance().setMusicState(1);
                }
                Tween.to(music_button, 7, .3f)
                        .target(target).ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F);
                Tween.to(music_button, 3, .3f)
                        .target(opacity2).ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F);
                Tween.to(music_bg, 3, .3f)
                        .target(opacity1).ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        effect_button.addListener(new ActorGestureListener() {
            public void tap(InputEvent paramAnonymousInputEvent, float paramAnonymousFloat1, float paramAnonymousFloat2, int paramAnonymousInt1, int paramAnonymousInt2) {
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                float target;
                float opacity1;
                float opacity2;
                if (GamePrefs.getInstance().isEffectOn() == 1) {
                    target = Gdx.graphics.getWidth() * .57f;
                    opacity1 = .5f;
                    opacity2 = .9f;
                    GamePrefs.getInstance().setEffectState(0);
                } else {
                    target = Gdx.graphics.getWidth() * .6f;
                    opacity1 = 1f;
                    opacity2 = 1f;
                    GamePrefs.getInstance().setEffectState(1);
                }
                Tween.to(effect_button, 7, .3f)
                        .target(target).ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F);
                Tween.to(effect_button, 3, .3f)
                        .target(opacity2).ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F);
                Tween.to(effect_bg, 3, .3f)
                        .target(opacity1).ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        vibrate_button.addListener(new ActorGestureListener() {
            public void tap(InputEvent paramAnonymousInputEvent, float paramAnonymousFloat1, float paramAnonymousFloat2, int paramAnonymousInt1, int paramAnonymousInt2) {
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                float target;
                float opacity1;
                float opacity2;
                if (GamePrefs.getInstance().isVibrateOn() == 1) {
                    target = Gdx.graphics.getWidth() * .57f;
                    opacity1 = .5f;
                    opacity2 = .9f;
                    GamePrefs.getInstance().setVibrateState(0);
                } else {
                    target = Gdx.graphics.getWidth() * .6f;
                    opacity1 = 1f;
                    opacity2 = 1f;
                    GamePrefs.getInstance().setVibrateState(1);
                }
                Tween.to(vibrate_button, 7, .3f)
                        .target(target).ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F);
                Tween.to(vibrate_button, 3, .3f)
                        .target(opacity2).ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F);
                Tween.to(vibrate_bg, 3, .3f)
                        .target(opacity1).ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        this.mainTable.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(mStage);

        mStage.addActor(this.mainTable);
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