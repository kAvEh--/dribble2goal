package ir.eynakgroup.dribble2goal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
import ir.eynakgroup.dribble2goal.Assets;
import ir.eynakgroup.dribble2goal.Constants;
import ir.eynakgroup.dribble2goal.GamePrefs;
import ir.eynakgroup.dribble2goal.MyGame;
import ir.eynakgroup.dribble2goal.Server.ServerTool;
import ir.eynakgroup.dribble2goal.Util.Popups;

/**
 * Created by kAvEh on 3/19/2016.
 *
 */
public class SettingScreen implements Screen, InputProcessor {

    TweenManager mTweenManager;
    private OrthographicCamera mMainCamera;
    SpriteBatch mMainBatch;
    Image bg;

    Stage mStage;
    Table mainTable;
    Skin mSkin;

    private Popups popup;

    SettingScreen screen;

    Image back;
    private Image dark_bg;

    final private String popup_string = "popup";

    public SettingScreen() {
        mTweenManager = MyGame.mTweenManager;
        mMainBatch = new SpriteBatch();
        screen = this;

        mSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        mStage = new Stage(new FitViewport(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT), mMainBatch);
        mainTable = new Table();

        bg = new Image(Assets.getInstance().main_bg);
        bg.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        Image music_icon = new Image(Assets.getInstance().setting_icon);
        music_icon.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
        music_icon.setPosition(Constants.HUD_SCREEN_WIDTH * .4335f, Constants.HUD_SCREEN_HEIGHT * .78f);

        Table setting_holder = new Table();
        Table scroll_holder = new Table();
        ScrollPane pane = new ScrollPane(scroll_holder);
        pane.setForceScroll(false, true);
        setting_holder.add(pane).width(Constants.HUD_SCREEN_WIDTH * .66f).height(Constants.HUD_SCREEN_HEIGHT * .75f);
        setting_holder.row();
        setting_holder.setBounds(0, 0, Constants.HUD_SCREEN_WIDTH * .66f, Constants.HUD_SCREEN_HEIGHT * .75f);

        setting_holder.setPosition(Constants.HUD_SCREEN_WIDTH * .17f, Constants.HUD_SCREEN_HEIGHT * .01f);

        Table table1 = new Table();
        Image music = new Image(Assets.getInstance().setting_music_bg);
        music.setSize(Constants.HUD_SCREEN_WIDTH * .66f, Constants.HUD_SCREEN_HEIGHT * .127f);
        table1.addActor(music);
        final Image music_bg = new Image(Assets.getInstance().setting_button_bg);
        music_bg.setSize(Constants.HUD_SCREEN_WIDTH * .068f, Constants.HUD_SCREEN_HEIGHT * .077f);
        music_bg.setPosition(Constants.HUD_SCREEN_WIDTH * .58f, Constants.HUD_SCREEN_HEIGHT * .025f);
        final Image music_button = new Image(Assets.getInstance().setting_button);
        music_button.setSize(Constants.HUD_SCREEN_WIDTH * .06f, Constants.HUD_SCREEN_HEIGHT * .108f);
        if (GamePrefs.getInstance().isMusicOn() == 1) {
            music_button.setPosition(Constants.HUD_SCREEN_WIDTH * .6f, Constants.HUD_SCREEN_HEIGHT * .003f);
        } else {
            music_button.setPosition(Constants.HUD_SCREEN_WIDTH * .57f, Constants.HUD_SCREEN_HEIGHT * .003f);
            music_bg.setColor(1f, 1f, 1f, .5f);
            music_button.setColor(1f, 1f, 1f, .9f);
        }
        table1.addActor(music_bg);
        table1.addActor(music_button);
        scroll_holder.add(table1).width(Constants.HUD_SCREEN_WIDTH * .66f).height(Constants.HUD_SCREEN_HEIGHT * .127f)
                .pad(Constants.HUD_SCREEN_HEIGHT * .02f, 0f, Constants.HUD_SCREEN_HEIGHT * .01f, 0f);
        scroll_holder.row();

        Table table2 = new Table();
        Image effects = new Image(Assets.getInstance().setting_sound_bg);
        effects.setSize(Constants.HUD_SCREEN_WIDTH * .66f, Constants.HUD_SCREEN_HEIGHT * .127f);
        table2.addActor(effects);
        final Image effect_bg = new Image(Assets.getInstance().setting_button_bg);
        effect_bg.setSize(Constants.HUD_SCREEN_WIDTH * .068f, Constants.HUD_SCREEN_HEIGHT * .077f);
        effect_bg.setPosition(Constants.HUD_SCREEN_WIDTH * .58f, Constants.HUD_SCREEN_HEIGHT * .025f);
        final Image effect_button = new Image(Assets.getInstance().setting_button);
        effect_button.setSize(Constants.HUD_SCREEN_WIDTH * .06f, Constants.HUD_SCREEN_HEIGHT * .108f);
        if (GamePrefs.getInstance().isEffectOn() == 1) {
            effect_button.setPosition(Constants.HUD_SCREEN_WIDTH * .6f, Constants.HUD_SCREEN_HEIGHT * .003f);
        } else {
            effect_button.setPosition(Constants.HUD_SCREEN_WIDTH * .57f, Constants.HUD_SCREEN_HEIGHT * .003f);
            effect_bg.setColor(1f, 1f, 1f, .5f);
            effect_button.setColor(1f, 1f, 1f, .9f);
        }
        table2.addActor(effect_bg);
        table2.addActor(effect_button);
        scroll_holder.add(table2).width(Constants.HUD_SCREEN_WIDTH * .66f).height(Constants.HUD_SCREEN_HEIGHT * .127f)
                .pad(Constants.HUD_SCREEN_HEIGHT * .02f, 0f, Constants.HUD_SCREEN_HEIGHT * .01f, 0f);
        scroll_holder.row();

        Table table3 = new Table();
        Image vibrate = new Image(Assets.getInstance().setting_vibe_bg);
        vibrate.setSize(Constants.HUD_SCREEN_WIDTH * .66f, Constants.HUD_SCREEN_HEIGHT * .127f);
        table3.addActor(vibrate);
        final Image vibrate_bg = new Image(Assets.getInstance().setting_button_bg);
        vibrate_bg.setSize(Constants.HUD_SCREEN_WIDTH * .068f, Constants.HUD_SCREEN_HEIGHT * .077f);
        vibrate_bg.setPosition(Constants.HUD_SCREEN_WIDTH * .58f, Constants.HUD_SCREEN_HEIGHT * .025f);
        final Image vibrate_button = new Image(Assets.getInstance().setting_button);
        vibrate_button.setSize(Constants.HUD_SCREEN_WIDTH * .06f, Constants.HUD_SCREEN_HEIGHT * .108f);
        if (GamePrefs.getInstance().isVibrateOn() == 1) {
            vibrate_button.setPosition(Constants.HUD_SCREEN_WIDTH * .6f, Constants.HUD_SCREEN_HEIGHT * .003f);
        } else {
            vibrate_button.setPosition(Constants.HUD_SCREEN_WIDTH * .57f, Constants.HUD_SCREEN_HEIGHT * .003f);
            vibrate_bg.setColor(1f, 1f, 1f, .5f);
            vibrate_button.setColor(1f, 1f, 1f, .9f);
        }
        table3.addActor(vibrate_bg);
        table3.addActor(vibrate_button);
        scroll_holder.add(table3).width(Constants.HUD_SCREEN_WIDTH * .66f).height(Constants.HUD_SCREEN_HEIGHT * .127f)
                .pad(Constants.HUD_SCREEN_HEIGHT * .02f, 0f, Constants.HUD_SCREEN_HEIGHT * .01f, 0f);
        scroll_holder.row();

        Image changeName = new Image(Assets.getInstance().setting_logout);
        music.setSize(Constants.HUD_SCREEN_WIDTH * .66f, Constants.HUD_SCREEN_HEIGHT * .127f);

        /*scroll_holder.add(changeName).width(Constants.HUD_SCREEN_WIDTH * .66f).height(Constants.HUD_SCREEN_HEIGHT * .127f)
                .pad(Constants.HUD_SCREEN_HEIGHT * .02f, 0f, Constants.HUD_SCREEN_HEIGHT * .01f, 0f);
        scroll_holder.row();*/

        Image changePass = new Image(Assets.getInstance().setting_logout);
        music.setSize(Constants.HUD_SCREEN_WIDTH * .66f, Constants.HUD_SCREEN_HEIGHT * .127f);

        /*scroll_holder.add(changePass).width(Constants.HUD_SCREEN_WIDTH * .66f).height(Constants.HUD_SCREEN_HEIGHT * .127f)
                .pad(Constants.HUD_SCREEN_HEIGHT * .02f, 0f, Constants.HUD_SCREEN_HEIGHT * .01f, 0f);
        scroll_holder.row();*/

        Image signout = new Image(Assets.getInstance().setting_logout);
        music.setSize(Constants.HUD_SCREEN_WIDTH * .66f, Constants.HUD_SCREEN_HEIGHT * .127f);

        scroll_holder.add(signout).width(Constants.HUD_SCREEN_WIDTH * .66f).height(Constants.HUD_SCREEN_HEIGHT * .127f)
                .pad(Constants.HUD_SCREEN_HEIGHT * .02f, 0f, Constants.HUD_SCREEN_HEIGHT * .01f, 0f);
        scroll_holder.row();

        this.mainTable.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);
        Gdx.input.setInputProcessor(mStage);
        mSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        back = new Image(Assets.getInstance().icon_back);
        back.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
        back.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .04f);

        dark_bg = new Image(Assets.getInstance().dark_bg);
        dark_bg.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);
        dark_bg.setPosition(0, Constants.HUD_SCREEN_HEIGHT * -1);

        mainTable.addActor(bg);
        mainTable.addActor(music_icon);
        mainTable.addActor(back);
        mainTable.addActor(setting_holder);

        back.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
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
            public void tap(InputEvent event, float x, float y, int count, int button) {
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                float target;
                float opacity1;
                float opacity2;
                if (GamePrefs.getInstance().isMusicOn() == 1) {
                    target = Constants.HUD_SCREEN_WIDTH * .57f;
                    opacity1 = .5f;
                    opacity2 = .9f;
                    GamePrefs.getInstance().setMusicState(0);
                    Assets.getInstance().main_theme.stop();
                    Assets.getInstance().stadium.stop();
                } else {
                    target = Constants.HUD_SCREEN_WIDTH * .6f;
                    opacity1 = 1f;
                    opacity2 = 1f;
                    GamePrefs.getInstance().setMusicState(1);
                    Assets.getInstance().main_theme.play();
                    Assets.getInstance().stadium.play();
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
            public void tap(InputEvent event, float x, float y, int count, int button) {
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                float target;
                float opacity1;
                float opacity2;
                if (GamePrefs.getInstance().isEffectOn() == 1) {
                    target = Constants.HUD_SCREEN_WIDTH * .57f;
                    opacity1 = .5f;
                    opacity2 = .9f;
                    GamePrefs.getInstance().setEffectState(0);
                } else {
                    target = Constants.HUD_SCREEN_WIDTH * .6f;
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
            public void tap(InputEvent event, float x, float y, int count, int button) {
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                float target;
                float opacity1;
                float opacity2;
                if (GamePrefs.getInstance().isVibrateOn() == 1) {
                    target = Constants.HUD_SCREEN_WIDTH * .57f;
                    opacity1 = .5f;
                    opacity2 = .9f;
                    GamePrefs.getInstance().setVibrateState(0);
                } else {
                    target = Constants.HUD_SCREEN_WIDTH * .6f;
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

        changeName.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                popup = new Popups(Constants.HUD_SCREEN_WIDTH * .99f, Constants.HUD_SCREEN_HEIGHT * .35f,
                        screen, mStage);
                popup.setSize(Constants.HUD_SCREEN_WIDTH * .1f, Constants.HUD_SCREEN_HEIGHT * .35f);
                popup.setPosition(Constants.HUD_SCREEN_WIDTH * .02f, Constants.HUD_SCREEN_HEIGHT * .65f);
                popup.setName(popup_string);
                mStage.addActor(popup);
                popup.setColor(popup.getColor().r, popup.getColor().g, popup.getColor().b, 0);
                Tween.to(popup, 5, .1f)
                        .target(1).ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F)
                        .setCallback(new TweenCallback() {
                            public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {

                            }
                        });
                dark_bg.setColor(bg.getColor().r, bg.getColor().g, bg.getColor().b, 0);
                dark_bg.setPosition(0, 0);
                Tween.to(dark_bg, 3, 1.5f)
                        .target(1)
                        .ease(TweenEquations.easeOutExpo)
                        .start(mTweenManager).delay(0.0F);
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        changePass.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        signout.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                GamePrefs.getInstance().setUserName("");
                GamePrefs.getInstance().setPassword("");
                ServerTool.getInstance().signOut();
                MyGame.mainInstance.setEntranceScreen();
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        this.mainTable.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(mStage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        mStage.addActor(this.mainTable);
        mStage.addActor(dark_bg);
    }

    public void removePopups() {
        for (Actor actor : mStage.getActors()) {
            if (actor.getName() != null) {
                if (actor.getName().equals(this.popup_string)) {
                    actor.remove();
                }
            }
        }
        dark_bg.setPosition(0, Constants.HUD_SCREEN_HEIGHT * -1);
    }

    @Override
    public void show() {
        mMainCamera = new OrthographicCamera(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);
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
        mMainCamera.viewportWidth = Constants.HUD_SCREEN_HEIGHT * aspectRatio;
        mMainCamera.update();

        mStage.getViewport().update(width, (int) (Constants.HUD_SCREEN_HEIGHT * aspectRatio), true);
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

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK) {
            MyGame.mainInstance.setMainScreen();
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}