package ir.eynakgroup.dribble2goal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import org.json.JSONObject;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import io.socket.emitter.Emitter;
import ir.eynakgroup.dribble2goal.Assets;
import ir.eynakgroup.dribble2goal.Constants;
import ir.eynakgroup.dribble2goal.GamePrefs;
import ir.eynakgroup.dribble2goal.MyGame;
import ir.eynakgroup.dribble2goal.ParticleEffectActor;
import ir.eynakgroup.dribble2goal.Server.ServerTool;
import ir.eynakgroup.dribble2goal.render.textures.ProgressLine;

/**
 * Created by kAvEh on 3/16/2016.
 */
public class LoginScreen implements Screen, InputProcessor {

    TweenManager mTweenManager;
    private OrthographicCamera mMainCamera;
    SpriteBatch mMainBatch;

    Image bg;
    Image email;
    TextField email_txt;
    Image userName;
    TextField userName_txt;
    Image password;
    TextField password_txt;
    Image rePassword;
    TextField rePassword_txt;
    Label error_txt;
    Image login;
    Image back;

    boolean loginlock = false;
    boolean isLogin;

    Stage mStage;
    Table mainTable;
    Skin mSkin;

    int current_item = 1;

    ParticleEffectActor pActor;

    public LoginScreen(final boolean isLogin) {
        mTweenManager = MyGame.mTweenManager;
        mMainBatch = new SpriteBatch();

        this.isLogin = isLogin;

        mSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        mStage = new Stage(new FitViewport(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT), mMainBatch);
        mainTable = new Table();

        mStage.setKeyboardFocus(email_txt);

        bg = new Image(Assets.getInstance().main_bg);
        bg.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        email = new Image(Assets.getInstance().login_email);
        email.setSize(Constants.HUD_SCREEN_WIDTH * .445f, Constants.HUD_SCREEN_HEIGHT * .283f);
        email.setPosition(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * .7f);

        email_txt = new TextField("", mSkin);
        email_txt.setSize(Constants.HUD_SCREEN_WIDTH * .5f, Constants.HUD_SCREEN_HEIGHT * .1f);
        email_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * .8f);
        email_txt.setAlignment(Align.left);
        email_txt.getOnscreenKeyboard().show(true);
        email_txt.setMaxLength(35);

        userName = new Image(Assets.getInstance().login_username);
        userName.setSize(Constants.HUD_SCREEN_WIDTH * .445f, Constants.HUD_SCREEN_HEIGHT * .283f);
        userName.setPosition(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * -.3f);

        userName_txt = new TextField("", mSkin);
        userName_txt.setSize(Constants.HUD_SCREEN_WIDTH * .5f, Constants.HUD_SCREEN_HEIGHT * .1f);
        userName_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * -.1f);
        userName_txt.setAlignment(Align.left);
        userName_txt.getOnscreenKeyboard().show(true);
        userName_txt.setMaxLength(35);

        password = new Image(Assets.getInstance().login_pass);
        password.setSize(Constants.HUD_SCREEN_WIDTH * .445f, Constants.HUD_SCREEN_HEIGHT * .283f);
        password.setPosition(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * -.3f);

        password_txt = new TextField("", mSkin);
        password_txt.setSize(Constants.HUD_SCREEN_WIDTH * .5f, Constants.HUD_SCREEN_HEIGHT * .1f);
        password_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * -.1f);
        password_txt.setAlignment(Align.left);
        password_txt.setPasswordMode(true);
        password_txt.setPasswordCharacter('•');
        password_txt.getOnscreenKeyboard().show(true);
        password_txt.setMaxLength(35);

        rePassword = new Image(Assets.getInstance().login_pass_re);
        rePassword.setSize(Constants.HUD_SCREEN_WIDTH * .445f, Constants.HUD_SCREEN_HEIGHT * .283f);
        rePassword.setPosition(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * -.3f);

        rePassword_txt = new TextField("", mSkin);
        rePassword_txt.setSize(Constants.HUD_SCREEN_WIDTH * .5f, Constants.HUD_SCREEN_HEIGHT * .1f);
        rePassword_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * -.1f);
        rePassword_txt.setAlignment(Align.left);
        rePassword_txt.getOnscreenKeyboard().show(true);
        rePassword_txt.setPasswordCharacter('•');
        rePassword_txt.setPasswordMode(true);
        rePassword_txt.setMaxLength(35);

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = mSkin.getFont("font21");
        style.fontColor = Color.WHITE;
        error_txt = new Label("", style);
        error_txt.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT * .1f);
        error_txt.setPosition(0, Constants.HUD_SCREEN_HEIGHT * .65f);
        error_txt.setAlignment(Align.center);
        error_txt.setVisible(false);

        login = new Image(Assets.getInstance().icon_ok);
        login.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
        login.setPosition(Constants.HUD_SCREEN_WIDTH * .03f, Constants.HUD_SCREEN_HEIGHT * .75f);

        back = new Image(Assets.getInstance().icon_back);
        back.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
        back.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .75f);

        login.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                if (isLogin) {
                    checkLogin(email_txt.getText(), password_txt.getText());
                } else {
                    checkRegister(email_txt.getText(), userName_txt.getText(), password_txt.getText(), rePassword_txt.getText());
                }
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                login.setSize(Constants.HUD_SCREEN_WIDTH * .133f * .8f, Constants.HUD_SCREEN_HEIGHT * .186f * .8f);
                login.setPosition(Constants.HUD_SCREEN_WIDTH * .0433f, Constants.HUD_SCREEN_HEIGHT * .7686f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                login.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
                login.setPosition(Constants.HUD_SCREEN_WIDTH * .03f, Constants.HUD_SCREEN_HEIGHT * .75f);
            }
        });

        back.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                mStage.setKeyboardFocus(null);
                Gdx.input.setOnscreenKeyboardVisible(false);
                MyGame.mainInstance.setEntranceScreen();
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                back.setSize(Constants.HUD_SCREEN_WIDTH * .133f * .8f, Constants.HUD_SCREEN_HEIGHT * .186f * .8f);
                back.setPosition(Constants.HUD_SCREEN_WIDTH * .8633f, Constants.HUD_SCREEN_HEIGHT * .7686f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                back.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
                back.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .75f);
            }
        });

        ParticleEffect p = new ParticleEffect();
        p.load(Gdx.files.internal("effects/projector.p"), Gdx.files.internal("effects"));
//        p.setPosition(Constants.HUD_SCREEN_WIDTH * .01f, Constants.HUD_SCREEN_HEIGHT * .01f);
        p.allowCompletion();

        pActor = new ParticleEffectActor(p);
        pActor.setPosition(Constants.HUD_SCREEN_WIDTH * .9f, Constants.HUD_SCREEN_HEIGHT * .27f);

        mStage.addListener(new ActorGestureListener() {
            public void fling(InputEvent event, float velocityX, float velocityY, int button) {
                if (velocityY > 300) {
                    switch (current_item) {
                        case 1:
                            if (isLogin) {
                                Tween.to(password, 1, .3f)
                                        .target(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * .7f)
                                        .ease(TweenEquations.easeInExpo)
                                        .start(mTweenManager).delay(0.0F);
                                Tween.to(password, 3, .3f)
                                        .target(1)
                                        .ease(TweenEquations.easeInExpo)
                                        .start(mTweenManager).delay(0.0F)
                                        .setCallback(new TweenCallback() {
                                            public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                                                password_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * .8f);
                                            }
                                        });

                                mStage.setKeyboardFocus(password_txt);
                                current_item = 2;

                                Tween.to(email, 1, .3f)
                                        .target(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * 1.1f)
                                        .ease(TweenEquations.easeInExpo)
                                        .start(mTweenManager).delay(0.0F);
                                Tween.to(email, 3, .3f)
                                        .target(0)
                                        .ease(TweenEquations.easeInExpo)
                                        .start(mTweenManager).delay(0.0F);
                                email_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * 1.1f);
                            } else {
                                Tween.to(userName, 1, .3f)
                                        .target(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * .7f)
                                        .ease(TweenEquations.easeInExpo)
                                        .start(mTweenManager).delay(0.0F);
                                Tween.to(userName, 3, .3f)
                                        .target(1)
                                        .ease(TweenEquations.easeInExpo)
                                        .start(mTweenManager).delay(0.0F)
                                        .setCallback(new TweenCallback() {
                                            public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                                                userName_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * .8f);
                                            }
                                        });

                                mStage.setKeyboardFocus(userName_txt);
                                current_item = 3;

                                Tween.to(email, 1, .3f)
                                        .target(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * 1.1f)
                                        .ease(TweenEquations.easeInExpo)
                                        .start(mTweenManager).delay(0.0F);
                                Tween.to(email, 3, .3f)
                                        .target(0)
                                        .ease(TweenEquations.easeInExpo)
                                        .start(mTweenManager).delay(0.0F);
                                email_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * 1.1f);
                            }
                            break;
                        case 2:
                            if (!isLogin) {
                                Tween.to(rePassword, 1, .3f)
                                        .target(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * .7f)
                                        .ease(TweenEquations.easeInExpo)
                                        .start(mTweenManager).delay(0.0F);
                                Tween.to(rePassword, 3, .3f)
                                        .target(1)
                                        .ease(TweenEquations.easeInExpo)
                                        .start(mTweenManager).delay(0.0F)
                                        .setCallback(new TweenCallback() {
                                            public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                                                rePassword_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * .8f);
                                            }
                                        });

                                mStage.setKeyboardFocus(rePassword_txt);
                                current_item = 4;

                                Tween.to(password, 1, .3f)
                                        .target(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * 1.1f)
                                        .ease(TweenEquations.easeInExpo)
                                        .start(mTweenManager).delay(0.0F);
                                Tween.to(password, 3, .3f)
                                        .target(0)
                                        .ease(TweenEquations.easeInExpo)
                                        .start(mTweenManager).delay(0.0F);
                                password_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * 1.1f);
                            }
                            break;
                        case 3:
                            Tween.to(password, 1, .3f)
                                    .target(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * .7f)
                                    .ease(TweenEquations.easeInExpo)
                                    .start(mTweenManager).delay(0.0F);
                            Tween.to(password, 3, .3f)
                                    .target(1)
                                    .ease(TweenEquations.easeInExpo)
                                    .start(mTweenManager).delay(0.0F)
                                    .setCallback(new TweenCallback() {
                                        public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                                            password_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * .8f);
                                        }
                                    });

                            mStage.setKeyboardFocus(password_txt);
                            current_item = 2;

                            Tween.to(userName, 1, .3f)
                                    .target(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * 1.1f)
                                    .ease(TweenEquations.easeInExpo)
                                    .start(mTweenManager).delay(0.0F);
                            Tween.to(userName, 3, .3f)
                                    .target(0)
                                    .ease(TweenEquations.easeInExpo)
                                    .start(mTweenManager).delay(0.0F);
                            userName_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * 1.1f);
                            break;
                    }
                } else if (velocityY < -300) {
                    switch (current_item) {
                        case 2:
                            if (isLogin) {
                                Tween.to(email, 1, .3f)
                                        .target(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * .7f)
                                        .ease(TweenEquations.easeInExpo)
                                        .start(mTweenManager).delay(0.0F);
                                Tween.to(email, 3, .3f)
                                        .target(1)
                                        .ease(TweenEquations.easeInExpo)
                                        .start(mTweenManager).delay(0.0F)
                                        .setCallback(new TweenCallback() {
                                            public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                                                email_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * .8f);
                                            }
                                        });

                                mStage.setKeyboardFocus(email_txt);
                                current_item = 1;

                                Tween.to(password, 1, .3f)
                                        .target(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * -.3f)
                                        .ease(TweenEquations.easeInExpo)
                                        .start(mTweenManager).delay(0.0F);
                                Tween.to(password, 3, .3f)
                                        .target(0)
                                        .ease(TweenEquations.easeInExpo)
                                        .start(mTweenManager).delay(0.0F);
                                password_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * -.3f);
                            } else {
                                Tween.to(userName, 1, .3f)
                                        .target(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * .7f)
                                        .ease(TweenEquations.easeInExpo)
                                        .start(mTweenManager).delay(0.0F);
                                Tween.to(userName, 3, .3f)
                                        .target(1)
                                        .ease(TweenEquations.easeInExpo)
                                        .start(mTweenManager).delay(0.0F)
                                        .setCallback(new TweenCallback() {
                                            public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                                                userName_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * .8f);
                                            }
                                        });

                                mStage.setKeyboardFocus(userName_txt);
                                current_item = 3;

                                Tween.to(password, 1, .3f)
                                        .target(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * -.3f)
                                        .ease(TweenEquations.easeInExpo)
                                        .start(mTweenManager).delay(0.0F);
                                Tween.to(password, 3, .3f)
                                        .target(0)
                                        .ease(TweenEquations.easeInExpo)
                                        .start(mTweenManager).delay(0.0F);
                                password_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * -.3f);
                            }
                            break;
                        case 3:
                            Tween.to(email, 1, .3f)
                                    .target(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * .7f)
                                    .ease(TweenEquations.easeInExpo)
                                    .start(mTweenManager).delay(0.0F);
                            Tween.to(email, 3, .3f)
                                    .target(1)
                                    .ease(TweenEquations.easeInExpo)
                                    .start(mTweenManager).delay(0.0F)
                                    .setCallback(new TweenCallback() {
                                        public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                                            email_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * .8f);
                                        }
                                    });

                            mStage.setKeyboardFocus(email_txt);
                            current_item = 1;

                            Tween.to(userName, 1, .3f)
                                    .target(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * -.3f)
                                    .ease(TweenEquations.easeInExpo)
                                    .start(mTweenManager).delay(0.0F);
                            Tween.to(userName, 3, .3f)
                                    .target(0)
                                    .ease(TweenEquations.easeInExpo)
                                    .start(mTweenManager).delay(0.0F);
                            userName_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * -.3f);
                            break;
                        case 4:
                            Tween.to(password, 1, .3f)
                                    .target(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * .7f)
                                    .ease(TweenEquations.easeInExpo)
                                    .start(mTweenManager).delay(0.0F);
                            Tween.to(password, 3, .3f)
                                    .target(1)
                                    .ease(TweenEquations.easeInExpo)
                                    .start(mTweenManager).delay(0.0F)
                                    .setCallback(new TweenCallback() {
                                        public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                                            password_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * .8f);
                                        }
                                    });

                            mStage.setKeyboardFocus(password_txt);
                            current_item = 2;

                            Tween.to(rePassword, 1, .3f)
                                    .target(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * -.3f)
                                    .ease(TweenEquations.easeInExpo)
                                    .start(mTweenManager).delay(0.0F);
                            Tween.to(rePassword, 3, .3f)
                                    .target(0)
                                    .ease(TweenEquations.easeInExpo)
                                    .start(mTweenManager).delay(0.0F);
                            rePassword_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * -.3f);
                            break;
                    }
                }
            }
        });

        mainTable.addActor(bg);
        mainTable.addActor(error_txt);
        mainTable.addActor(email);
        mainTable.addActor(email_txt);
        mainTable.addActor(password);
        mainTable.addActor(password_txt);
        mainTable.addActor(userName);
        mainTable.addActor(userName_txt);
        mainTable.addActor(rePassword);
        mainTable.addActor(rePassword_txt);
        mainTable.addActor(login);
        mainTable.addActor(back);
        mainTable.addActor(pActor);

        this.mainTable.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(mStage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        mStage.addActor(this.mainTable);

        checkSavedData();

        email_txt.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char key) {
                if (key == '\r' || key == '\n' || key == '\t') {
                    Tween.to(email, 1, .3f)
                            .target(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * 1.1f)
                            .ease(TweenEquations.easeInExpo)
                            .start(mTweenManager).delay(0.0F);
                    Tween.to(email, 3, .3f)
                            .target(0)
                            .ease(TweenEquations.easeInExpo)
                            .start(mTweenManager).delay(0.0F);
                    email_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * 1.1f);

                    if (isLogin) {
                        Tween.to(password, 1, .3f)
                                .target(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * .7f)
                                .ease(TweenEquations.easeInExpo)
                                .start(mTweenManager).delay(0.0F);
                        Tween.to(password, 3, .3f)
                                .target(1)
                                .ease(TweenEquations.easeInExpo)
                                .start(mTweenManager).delay(0.0F);
                        password_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * .8f);

                        mStage.setKeyboardFocus(password_txt);
                        current_item = 2;
                    } else {
                        Tween.to(userName, 1, .3f)
                                .target(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * .7f)
                                .ease(TweenEquations.easeInExpo)
                                .start(mTweenManager).delay(0.0F);
                        Tween.to(userName, 3, .3f)
                                .target(1)
                                .ease(TweenEquations.easeInExpo)
                                .start(mTweenManager).delay(0.0F);
                        userName_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * .8f);

                        mStage.setKeyboardFocus(userName_txt);
                        current_item = 3;
                    }
                }
            }
        });

        password_txt.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char key) {
                if (key == '\r' || key == '\n' || key == '\t') {
                    if (isLogin) {
                        checkLogin(email_txt.getText(), password_txt.getText());
                    } else {
                        Tween.to(password, 1, .3f)
                                .target(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * 1.1f)
                                .ease(TweenEquations.easeInExpo)
                                .start(mTweenManager).delay(0.0F);
                        Tween.to(password, 3, .3f)
                                .target(0)
                                .ease(TweenEquations.easeInExpo)
                                .start(mTweenManager).delay(0.0F);
                        password_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * 1.1f);

                        Tween.to(rePassword, 1, .3f)
                                .target(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * .7f)
                                .ease(TweenEquations.easeInExpo)
                                .start(mTweenManager).delay(0.0F);
                        Tween.to(rePassword, 3, .3f)
                                .target(1)
                                .ease(TweenEquations.easeInExpo)
                                .start(mTweenManager).delay(0.0F);
                        rePassword_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * .8f);

                        mStage.setKeyboardFocus(rePassword_txt);
                        current_item = 4;
                    }
                }
            }
        });

        userName_txt.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char key) {
                if (key == '\r' || key == '\n' || key == '\t') {
                    Tween.to(userName, 1, .3f)
                            .target(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * 1.1f)
                            .ease(TweenEquations.easeInExpo)
                            .start(mTweenManager).delay(0.0F);
                    Tween.to(userName, 3, .3f)
                            .target(0)
                            .ease(TweenEquations.easeInExpo)
                            .start(mTweenManager).delay(0.0F);
                    userName_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * 1.1f);

                    Tween.to(password, 1, .3f)
                            .target(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * .7f)
                            .ease(TweenEquations.easeInExpo)
                            .start(mTweenManager).delay(0.0F);
                    Tween.to(password, 3, .3f)
                            .target(1)
                            .ease(TweenEquations.easeInExpo)
                            .start(mTweenManager).delay(0.0F);
                    password_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * .8f);

                    mStage.setKeyboardFocus(password_txt);
                    current_item = 2;
                }
            }
        });

        rePassword_txt.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char key) {
                if (key == '\r' || key == '\n' || key == '\t') {
//                    Tween.to(rePassword, 1, .3f)
//                            .target(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * 1.1f)
//                            .ease(TweenEquations.easeInExpo)
//                            .start(mTweenManager).delay(0.0F);
//                    Tween.to(rePassword, 3, .3f)
//                            .target(0)
//                            .ease(TweenEquations.easeInExpo)
//                            .start(mTweenManager).delay(0.0F);
//                    rePassword_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * 1.1f);

                    checkRegister(email_txt.getText(), userName_txt.getText(), password_txt.getText(), rePassword_txt.getText());
                }
            }
        });
    }

    private void checkSavedData() {
        System.out.println(GamePrefs.getInstance().getUserName() + "^^^^^^" + GamePrefs.getInstance().getPassword());
        if (GamePrefs.getInstance().getUserName() != null &&
                GamePrefs.getInstance().getUserName().length() > 0) {
            checkLogin(GamePrefs.getInstance().getUserName(), GamePrefs.getInstance().getPassword());
            email_txt.setText(GamePrefs.getInstance().getUserName());
            password_txt.setText(GamePrefs.getInstance().getPassword());
        }
    }

    private void checkRegister(String mail, String username, String pass, String rePass) {
        //TODO check validity of fields
        if (!ServerTool.getInstance().socket.connected())
            ServerTool.getInstance().socket.connect();
        if (!loginlock) {
            loginlock = true;
            if (!(mail.contains("@") && mail.contains("."))) {
                email.setPosition(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * .7f);
                email.setColor(1, 1, 1, 1);
                email_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * .8f);

                userName.setPosition(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * -.3f);
                userName.setColor(1, 1, 1, 0);
                userName_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * -.3f);
                password.setPosition(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * -.3f);
                password.setColor(1, 1, 1, 0);
                password_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * -.3f);
                rePassword.setPosition(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * -.3f);
                rePassword.setColor(1, 1, 1, 0);
                rePassword_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * -.3f);

                mStage.setKeyboardFocus(email_txt);
                current_item = 1;

                error_txt.setText("Invalid email address");
                error_txt.setVisible(true);

                loginlock = false;

                return;
            }
            if (pass.length() < 3) {
                password.setPosition(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * .7f);
                password.setColor(1, 1, 1, 1);
                password_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * .8f);

                email.setPosition(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * -.3f);
                email.setColor(1, 1, 1, 0);
                email_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * -.3f);
                userName.setPosition(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * -.3f);
                userName.setColor(1, 1, 1, 0);
                userName_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * -.3f);
                rePassword.setPosition(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * -.3f);
                rePassword.setColor(1, 1, 1, 0);
                rePassword_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * -.3f);

                mStage.setKeyboardFocus(password_txt);
                current_item = 2;

                error_txt.setText("Password is too short.");
                error_txt.setVisible(true);

                loginlock = false;

                return;
            }
            if (!pass.equals(rePass)) {
                rePassword.setPosition(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * .7f);
                rePassword.setColor(1, 1, 1, 1);
                rePassword_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * .8f);

                email.setPosition(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * -.3f);
                email.setColor(1, 1, 1, 0);
                email_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * -.3f);
                userName.setPosition(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * -.3f);
                userName.setColor(1, 1, 1, 0);
                userName_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * -.3f);
                password.setPosition(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * -.3f);
                password.setColor(1, 1, 1, 0);
                password_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * -.3f);

                mStage.setKeyboardFocus(rePassword_txt);
                current_item = 4;

                error_txt.setText("Password does not match");
                error_txt.setVisible(true);

                loginlock = false;

                return;
            }
            if (username.length() < 5 || username.length() > 12) {
                userName.setPosition(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * .7f);
                userName.setColor(1, 1, 1, 1);
                userName_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * .8f);

                email.setPosition(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * -.3f);
                email.setColor(1, 1, 1, 0);
                email_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * -.3f);
                password.setPosition(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * -.3f);
                password.setColor(1, 1, 1, 0);
                password_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * -.3f);
                rePassword.setPosition(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * -.3f);
                rePassword.setColor(1, 1, 1, 0);
                rePassword_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * -.3f);

                mStage.setKeyboardFocus(userName_txt);
                current_item = 3;

                error_txt.setText("Username must be between 5 and 12");
                error_txt.setVisible(true);

                loginlock = false;

                return;
            }
            ServerTool.getInstance().register(mail, pass, username);
            GamePrefs.getInstance().setUserName(email_txt.getText());
            GamePrefs.getInstance().setPassword(password_txt.getText());

            ServerTool.getInstance().socket.off("loggedInPlayer");
            ServerTool.getInstance().socket.on("loggedInPlayer", onRegisterListener);
        }
    }

    private void checkLogin(String mail, String pass) {
        if (!ServerTool.getInstance().socket.connected())
            ServerTool.getInstance().socket.connect();
        //TODO
        if (!loginlock) {
            loginlock = true;
            if (!(mail.contains("@") && mail.contains("."))) {
                email.setPosition(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * .7f);
                email.setColor(1, 1, 1, 1);
                email_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * .8f);

                password.setPosition(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * -.3f);
                password.setColor(1, 1, 1, 0);
                password_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * -.3f);

                mStage.setKeyboardFocus(email_txt);
                current_item = 1;

                error_txt.setText("Invalid email address");
                error_txt.setVisible(true);

                loginlock = false;

                return;
            }
            if (pass.length() < 3) {
                password.setPosition(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * .7f);
                password.setColor(1, 1, 1, 1);
                password_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * .8f);

                email.setPosition(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * -.3f);
                email.setColor(1, 1, 1, 0);
                email_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * -.3f);

                mStage.setKeyboardFocus(password_txt);
                current_item = 2;

                error_txt.setText("Password is too short.");
                error_txt.setVisible(true);

                loginlock = false;

                return;
            }
            ServerTool.getInstance().login(mail, pass);
            GamePrefs.getInstance().setUserName(email_txt.getText());
            GamePrefs.getInstance().setPassword(password_txt.getText());

            error_txt.setText("logging in ....");

            ServerTool.getInstance().socket.off("loggedInPlayer");
            ServerTool.getInstance().socket.on("loggedInPlayer", onLoginListener);
        }
    }

    private Emitter.Listener onRegisterListener = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            JSONObject response = (JSONObject) args[0];
            try {
                String s = response.getString("err");
                GamePrefs.getInstance().setUserName("");
                GamePrefs.getInstance().setPassword("");
            } catch (Exception e) {
                if (setData((JSONObject) args[0])) {
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            rePassword_txt.getOnscreenKeyboard().show(false);
                            MyGame.mainInstance.setMainScreen();
                        }
                    });
                }
            }
            loginlock = false;
        }

    };

    private Emitter.Listener onLoginListener = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            JSONObject response = (JSONObject) args[0];
            try {
                String s = response.getString("err");
                GamePrefs.getInstance().setUserName("");
                GamePrefs.getInstance().setPassword("");
                error_txt.setText("Username or Password is not correct");
                error_txt.setVisible(true);

                email.setPosition(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * .7f);
                email.setColor(1, 1, 1, 1);
                email_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * .8f);

                password.setPosition(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * -.3f);
                password.setColor(1, 1, 1, 0);
                password_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .42f, Constants.HUD_SCREEN_HEIGHT * -.3f);

                mStage.setKeyboardFocus(email_txt);
                current_item = 1;
            } catch (Exception e) {
                if (setData((JSONObject) args[0])) {
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            password_txt.getOnscreenKeyboard().show(false);
                            MyGame.mainInstance.setMainScreen();
                        }
                    });
                }
            }
            loginlock = false;
        }

    };

    private boolean setData(JSONObject data) {
        try {
            if (data.getBoolean("rc")) {

            } else {

            }
            GamePrefs.getInstance().isDailyAvailable = data.getBoolean("dailyCoin");
            JSONObject player = data.getJSONObject("player");
            GamePrefs.getInstance().game_won = player.getInt("winCount");
            GamePrefs.getInstance().position = player.getInt("formation");
            GamePrefs.getInstance().game_played = player.getInt("gameCount");
            GamePrefs.getInstance().winRate = (int) (player.getDouble("winrate") * 100f);
            GamePrefs.getInstance().cleanSheet = player.getInt("cleanSheet");
            GamePrefs.getInstance().shirt = player.getInt("shirt") - 1;
            GamePrefs.getInstance().name = player.getString("nickname");
            GamePrefs.getInstance().avatar = player.getInt("avatarId");
            GamePrefs.getInstance().setUserName(player.getString("username"));
            GamePrefs.getInstance().playerId = player.getString("_id");
            GamePrefs.getInstance().goals = player.getInt("goals");
            GamePrefs.getInstance().winInaRaw = player.getInt("winInaRow");
            GamePrefs.getInstance().coins_num = player.getInt("coin");
            JSONObject level = player.getJSONObject("level");
            GamePrefs.getInstance().level = level.getInt("lvl");
            GamePrefs.getInstance().xp = level.getInt("xp");
            JSONObject achs = player.getJSONObject("achievements");
            GamePrefs.getInstance().achieve_goal = achs.getInt("goal");
            GamePrefs.getInstance().achieve_cleanSheet = achs.getInt("cleanSheet");
            GamePrefs.getInstance().achieve_win = achs.getInt("win");
            GamePrefs.getInstance().achieve_winInaRow = achs.getInt("winInaRow");
            //First Player Data
            JSONObject tmpPlayer = player.getJSONObject("players").getJSONObject("1");
            GamePrefs.getInstance().players[0][0] = tmpPlayer.getInt("stamina");
            GamePrefs.getInstance().players[0][1] = tmpPlayer.getInt("size");
            GamePrefs.getInstance().players[0][2] = tmpPlayer.getInt("speed");
            //Second Player Data
            tmpPlayer = player.getJSONObject("players").getJSONObject("2");
            GamePrefs.getInstance().players[1][0] = tmpPlayer.getInt("stamina");
            GamePrefs.getInstance().players[1][1] = tmpPlayer.getInt("size");
            GamePrefs.getInstance().players[1][2] = tmpPlayer.getInt("speed");
            //Third Player Data
            tmpPlayer = player.getJSONObject("players").getJSONObject("3");
            GamePrefs.getInstance().players[2][0] = tmpPlayer.getInt("stamina");
            GamePrefs.getInstance().players[2][1] = tmpPlayer.getInt("size");
            GamePrefs.getInstance().players[2][2] = tmpPlayer.getInt("speed");
            //Fourth Player Data
            tmpPlayer = player.getJSONObject("players").getJSONObject("4");
            GamePrefs.getInstance().players[3][0] = tmpPlayer.getInt("stamina");
            GamePrefs.getInstance().players[3][1] = tmpPlayer.getInt("size");
            GamePrefs.getInstance().players[3][2] = tmpPlayer.getInt("speed");
            //Fifth Player Data
            tmpPlayer = player.getJSONObject("players").getJSONObject("5");
            GamePrefs.getInstance().players[4][0] = tmpPlayer.getInt("stamina");
            GamePrefs.getInstance().players[4][1] = tmpPlayer.getInt("size");
            GamePrefs.getInstance().players[4][2] = tmpPlayer.getInt("speed");
            //Lineup
            JSONObject tmp = player.getJSONObject("lineup");
            boolean flag = true;
            for (int i = 1; i < 6; i++) {
                if (tmp.getInt(i + "") == -1) {
                    if (flag) {
                        GamePrefs.getInstance().lineup[3] = (i - 1);
                        flag = false;
                    } else {
                        GamePrefs.getInstance().lineup[4] = (i - 1);
                    }
                } else {
                    GamePrefs.getInstance().lineup[tmp.getInt(i + "") - 1] = (i - 1);
                }
            }
            //Shirts
            tmp = player.getJSONObject("shirts");
            for (int i = 0; i < 24; i++) {
                GamePrefs.getInstance().shirts[i] = tmp.getInt((i + 1) + "");
            }
            GamePrefs.getInstance().shirts[GamePrefs.getInstance().shirt] = 2;

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void show() {
        mMainCamera = new OrthographicCamera(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);
        mMainCamera.update();

        this.mainTable.setPosition(0f, 0f);
        this.mainTable.setVisible(true);
        this.mainTable.setColor(1.0F, 1.0F, 1.0F, 0F);
        Tween.to(this.mainTable, 5, .1f)
                .target(1f).ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(0.0F)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {

                    }
                });
        mStage.setKeyboardFocus(email_txt);

//        Tween.to(pActor, 1, 13f)
//                .target(Constants.HUD_SCREEN_WIDTH * .2f, Constants.HUD_SCREEN_HEIGHT * .9f)
//                .ease(TweenEquations.easeInExpo)
//                .start(mTweenManager).delay(0.0F)
//                .setCallback(new TweenCallback() {
//                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
//
//                    }
//                });
        pActor.start();
    }

    @Override
    public void render(float delta) {
        mStage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        mStage.act(delta);
        mStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        float aspectRatio = (float) width / height;
        mMainCamera.viewportWidth = Constants.HUD_SCREEN_HEIGHT * aspectRatio;
        mMainCamera.update();

        mStage.getViewport().update(width, (int) (Constants.HUD_SCREEN_HEIGHT * aspectRatio), true);
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

        ServerTool.getInstance().socket.off("loggedInPlayer");
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK) {
            MyGame.mainInstance.setEntranceScreen();
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