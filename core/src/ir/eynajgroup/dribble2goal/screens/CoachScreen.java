package ir.eynajgroup.dribble2goal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Random;

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
 * Created by kAvEh on 3/8/2016.
 */
public class CoachScreen implements Screen {

    TweenManager mTweenManager;
    private OrthographicCamera mMainCamera;
    SpriteBatch mMainBatch;
    Image bg;
    Image setting_formation;
    Image position_left;
    Image position_right;
    Image player_1;
    Image player_2;
    Image player_3;
    Image player_4;
    Image player_5;
    Image selected;
    Image p_stamina;
    Image p_size;
    Image p_speed;
    Image coins;
    Image confirm;
    Image back;
    TextField coins_txt;

    int selected_item = 0;

    Image item_bg;
    Stage mStage;
    Table mainTable;
    Skin mSkin;
    Random random;

    public CoachScreen() {
        mTweenManager = MyGame.mTweenManager;
        mMainBatch = new SpriteBatch();

        random = new Random();

        mSkin = new Skin(Gdx.files.internal("data/uiskin.json"));

        mStage = new Stage(new FitViewport(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT), mMainBatch);
        mainTable = new Table();

        bg = new Image(Assets.getInstance().main_bg);
        bg.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        item_bg = new Image(Assets.getInstance().main_item_bg);
        item_bg.setSize(Constants.HUD_SCREEN_WIDTH * .28f, Constants.HUD_SCREEN_WIDTH * .28f);
        item_bg.setPosition(Constants.HUD_SCREEN_WIDTH * .17f, Constants.HUD_SCREEN_HEIGHT * .15f);

        setting_formation = new Image(Assets.getInstance().setting_formation);
        setting_formation.setSize(Constants.HUD_SCREEN_WIDTH * .336f, Constants.HUD_SCREEN_HEIGHT);
        setting_formation.setPosition(Constants.HUD_SCREEN_WIDTH * .06f, 0);

        position_left = new Image(Assets.getInstance().arrow_left);
        position_left.setSize(Constants.HUD_SCREEN_WIDTH * .085f, Constants.HUD_SCREEN_HEIGHT * .119f);
        position_left.setPosition(Constants.HUD_SCREEN_WIDTH * .02f, Constants.HUD_SCREEN_HEIGHT * .465f);

        position_right = new Image(Assets.getInstance().arrow_right);
        position_right.setSize(Constants.HUD_SCREEN_WIDTH * .085f, Constants.HUD_SCREEN_HEIGHT * .119f);
        position_right.setPosition(Constants.HUD_SCREEN_WIDTH * .355f, Constants.HUD_SCREEN_HEIGHT * .465f);

        position_left.addListener(new ActorGestureListener() {
            public void tap(InputEvent paramAnonymousInputEvent, float paramAnonymousFloat1, float paramAnonymousFloat2, int paramAnonymousInt1, int paramAnonymousInt2) {
                if (GamePrefs.getInstance().position == 1) {
                    GamePrefs.getInstance().position = GamePrefs.getInstance().position_num;
                } else {
                    GamePrefs.getInstance().position = GamePrefs.getInstance().position - 1;
                }
                Vector2[] position = new Util().getSettingPosition(GamePrefs.getInstance().position);
                setPosition(position);
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                position_left.setSize(Constants.HUD_SCREEN_WIDTH * .069f, Constants.HUD_SCREEN_HEIGHT * .0966f);
                position_left.setPosition(Constants.HUD_SCREEN_WIDTH * .028f, Constants.HUD_SCREEN_HEIGHT * .4762f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                position_left.setSize(Constants.HUD_SCREEN_WIDTH * .085f, Constants.HUD_SCREEN_HEIGHT * .119f);
                position_left.setPosition(Constants.HUD_SCREEN_WIDTH * .02f, Constants.HUD_SCREEN_HEIGHT * .465f);
            }
        });

        position_right.addListener(new ActorGestureListener() {
            public void tap(InputEvent paramAnonymousInputEvent, float paramAnonymousFloat1, float paramAnonymousFloat2, int paramAnonymousInt1, int paramAnonymousInt2) {
                if (GamePrefs.getInstance().position == GamePrefs.getInstance().position_num) {
                    GamePrefs.getInstance().position = 1;
                } else {
                    GamePrefs.getInstance().position = GamePrefs.getInstance().position + 1;
                }
                Vector2[] position = new Util().getSettingPosition(GamePrefs.getInstance().position);
                setPosition(position);
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                position_right.setSize(Constants.HUD_SCREEN_WIDTH * .069f, Constants.HUD_SCREEN_HEIGHT * .0966f);
                position_right.setPosition(Constants.HUD_SCREEN_WIDTH * .363f, Constants.HUD_SCREEN_HEIGHT * .4762f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                position_right.setSize(Constants.HUD_SCREEN_WIDTH * .085f, Constants.HUD_SCREEN_HEIGHT * .119f);
                position_right.setPosition(Constants.HUD_SCREEN_WIDTH * .355f, Constants.HUD_SCREEN_HEIGHT * .465f);
            }
        });

        player_1 = new Image(new Util().getShirt(GamePrefs.getInstance().shirt));
        player_2 = new Image(new Util().getShirt(GamePrefs.getInstance().shirt));
        player_3 = new Image(new Util().getShirt(GamePrefs.getInstance().shirt));
        player_4 = new Image(new Util().getShirt(GamePrefs.getInstance().shirt));
        player_5 = new Image(new Util().getShirt(GamePrefs.getInstance().shirt));

        selected = new Image(Assets.getInstance().selected_player);
        selected.setSize(Constants.HUD_SCREEN_WIDTH * .06f, Constants.HUD_SCREEN_HEIGHT * .107f);

        Vector2[] position = new Util().getSettingPosition(GamePrefs.getInstance().position);
        setPosition(position);

        player_1.setSize(Constants.HUD_SCREEN_WIDTH * .09f, Constants.HUD_SCREEN_HEIGHT * .16f);
        player_2.setSize(Constants.HUD_SCREEN_WIDTH * .09f, Constants.HUD_SCREEN_HEIGHT * .16f);
        player_3.setSize(Constants.HUD_SCREEN_WIDTH * .09f, Constants.HUD_SCREEN_HEIGHT * .16f);
        player_4.setSize(Constants.HUD_SCREEN_WIDTH * .09f, Constants.HUD_SCREEN_HEIGHT * .16f);
        player_5.setSize(Constants.HUD_SCREEN_WIDTH * .09f, Constants.HUD_SCREEN_HEIGHT * .16f);

        player_1.addListener(new ActorGestureListener() {
            public void tap(InputEvent paramAnonymousInputEvent, float paramAnonymousFloat1, float paramAnonymousFloat2, int paramAnonymousInt1, int paramAnonymousInt2) {
                Tween.to(selected, 1, .3f)
                        .target(player_1.getX() + Constants.HUD_SCREEN_WIDTH * .015f,
                                player_1.getY() + Constants.HUD_SCREEN_HEIGHT * .032f)
                        .ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F);

                selected_item = 0;
            }
        });

        player_2.addListener(new ActorGestureListener() {
            public void tap(InputEvent paramAnonymousInputEvent, float paramAnonymousFloat1, float paramAnonymousFloat2, int paramAnonymousInt1, int paramAnonymousInt2) {
                Tween.to(selected, 1, .3f)
                        .target(player_2.getX() + Constants.HUD_SCREEN_WIDTH * .015f,
                                player_2.getY() + Constants.HUD_SCREEN_HEIGHT * .032f)
                        .ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F);

                selected_item = 1;
            }
        });

        player_3.addListener(new ActorGestureListener() {
            public void tap(InputEvent paramAnonymousInputEvent, float paramAnonymousFloat1, float paramAnonymousFloat2, int paramAnonymousInt1, int paramAnonymousInt2) {
                Tween.to(selected, 1, .3f)
                        .target(player_3.getX() + Constants.HUD_SCREEN_WIDTH * .015f,
                                player_3.getY() + Constants.HUD_SCREEN_HEIGHT * .032f)
                        .ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F);

                selected_item = 2;
            }
        });

        player_4.addListener(new ActorGestureListener() {
            public void tap(InputEvent paramAnonymousInputEvent, float paramAnonymousFloat1, float paramAnonymousFloat2, int paramAnonymousInt1, int paramAnonymousInt2) {
                Tween.to(selected, 1, .3f)
                        .target(player_4.getX() + Constants.HUD_SCREEN_WIDTH * .015f,
                                player_4.getY() + Constants.HUD_SCREEN_HEIGHT * .032f)
                        .ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F);

                selected_item = 3;
            }
        });

        player_5.addListener(new ActorGestureListener() {
            public void tap(InputEvent paramAnonymousInputEvent, float paramAnonymousFloat1, float paramAnonymousFloat2, int paramAnonymousInt1, int paramAnonymousInt2) {
                Tween.to(selected, 1, .3f)
                        .target(player_5.getX() + Constants.HUD_SCREEN_WIDTH * .015f,
                                player_5.getY() + Constants.HUD_SCREEN_HEIGHT * .032f)
                        .ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F);

                selected_item = 4;
            }
        });

        p_stamina = new Image(Assets.getInstance().player_stamina);
        p_stamina.setSize(Constants.HUD_SCREEN_WIDTH * .418f, Constants.HUD_SCREEN_HEIGHT * .140f);
        p_stamina.setPosition(Constants.HUD_SCREEN_WIDTH * .53f, Constants.HUD_SCREEN_HEIGHT * .8f);

        p_size = new Image(Assets.getInstance().player_size);
        p_size.setSize(Constants.HUD_SCREEN_WIDTH * .418f, Constants.HUD_SCREEN_HEIGHT * .140f);
        p_size.setPosition(Constants.HUD_SCREEN_WIDTH * .53f, Constants.HUD_SCREEN_HEIGHT * .56f);

        p_speed = new Image(Assets.getInstance().player_speed);
        p_speed.setSize(Constants.HUD_SCREEN_WIDTH * .418f, Constants.HUD_SCREEN_HEIGHT * .140f);
        p_speed.setPosition(Constants.HUD_SCREEN_WIDTH * .53f, Constants.HUD_SCREEN_HEIGHT * .32f);

        coins = new Image(Assets.getInstance().setting_coin);
        coins.setSize(Constants.HUD_SCREEN_WIDTH * .25f, Constants.HUD_SCREEN_HEIGHT * .186f);
        coins.setPosition(Constants.HUD_SCREEN_WIDTH * .465f, Constants.HUD_SCREEN_HEIGHT * .04f);

        confirm = new Image(Assets.getInstance().icon_ok);
        confirm.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
        confirm.setPosition(Constants.HUD_SCREEN_WIDTH * .73f, Constants.HUD_SCREEN_HEIGHT * .04f);

        back = new Image(Assets.getInstance().icon_back);
        back.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
        back.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .04f);

        coins_txt = new TextField("120077", mSkin);
        coins_txt.setColor(.6f, .3f, .5f, 1f);
        coins_txt.setDisabled(true);
//        coins_txt.setSize(Constants.HUD_SCREEN_WIDTH * .15f, Constants.HUD_SCREEN_HEIGHT * .186f);
        coins_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .58f, Constants.HUD_SCREEN_HEIGHT * .055f);

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

        confirm.addListener(new ActorGestureListener() {
            public void tap(InputEvent paramAnonymousInputEvent, float paramAnonymousFloat1, float paramAnonymousFloat2, int paramAnonymousInt1, int paramAnonymousInt2) {
                //TODO set new settings
                MyGame.mainInstance.setMainScreen();
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        mainTable.addActor(bg);
        mainTable.addActor(setting_formation);
        mainTable.addActor(position_left);
        mainTable.addActor(position_right);
        mainTable.addActor(player_1);
        mainTable.addActor(player_2);
        mainTable.addActor(player_3);
        mainTable.addActor(player_4);
        mainTable.addActor(player_5);
        mainTable.addActor(selected);
        mainTable.addActor(p_stamina);
        mainTable.addActor(p_size);
        mainTable.addActor(p_speed);
        mainTable.addActor(coins);
        mainTable.addActor(confirm);
        mainTable.addActor(back);
        mainTable.addActor(coins_txt);

        this.mainTable.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);
        Gdx.input.setInputProcessor(mStage);

        mStage.addActor(this.mainTable);
    }

    void setPosition(Vector2[] position) {
        Tween.to(selected, 1, .3f)
                .target(position[selected_item].x + Constants.HUD_SCREEN_WIDTH * .015f,
                        position[selected_item].y + Constants.HUD_SCREEN_HEIGHT * .032f)
                .ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(0.0F);

        Tween.to(player_1, 1, .3f)
                .target(position[0].x, position[0].y)
                .ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(0.0F);

        Tween.to(player_2, 1, .3f)
                .target(position[1].x, position[1].y)
                .ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(0.0F);

        Tween.to(player_3, 1, .3f)
                .target(position[2].x, position[2].y)
                .ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(0.0F);

        Tween.to(player_4, 1, .3f)
                .target(position[3].x, position[3].y)
                .ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(0.0F);

        Tween.to(player_5, 1, .3f)
                .target(position[4].x, position[4].y)
                .ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(0.0F);
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
        // Set the viewport to the whole screen.
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

//        mMainBatch.setProjectionMatrix(mMainCamera.combined);
//
//        mMainBatch.begin();
//
//        mMainBatch.draw(background, -Constants.SCREEN_WIDTH / 2, -Constants.SCREEN_HEIGHT / 2,
//                Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
//
//        mMainBatch.end();

        // Restore the stage's viewport.
        mStage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        mStage.act(delta);
        mStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        float aspectRatio = (float) width / height;
        mMainCamera.viewportWidth = Constants.SCREEN_HEIGHT * aspectRatio;
        mMainCamera.update();

        mStage.getViewport().update(width, (int) (Constants.SCREEN_HEIGHT * aspectRatio), true);
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

