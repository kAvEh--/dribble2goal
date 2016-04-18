package ir.eynajgroup.dribble2goal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import ir.eynajgroup.dribble2goal.render.textures.ProgressCircle;

/**
 * Created by kAvEh on 3/4/2016.
 */
public class MainMenuScreen implements Screen {

    TweenManager mTweenManager;
    private OrthographicCamera mMainCamera;
    SpriteBatch mMainBatch;
    Image arc;
    Image bg;
    Image item_bg_2p;
    Image item_bg_coach;
    Image item_2p;
    Image item_light_2p;
    Image item_light_coach;
    Image item_coach;
    Image profile_bg;
    Image profile_coin;
    ProgressCircle sprite;
    TextureRegion region;
    PolygonSpriteBatch pbatch;
    Image menu_settings;
    Image menu_shop;
    Image avatar;
    Stage mStage;
    Table mainTable;
    Skin mSkin;
    Random random;
    TextField coins_txt;

    public MainMenuScreen() {
        mTweenManager = MyGame.mTweenManager;
        mMainBatch = new SpriteBatch();

        random = new Random();

        mStage = new Stage(new FitViewport(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT), mMainBatch);
        mainTable = new Table();

        bg = new Image(Assets.getInstance().main_bg);
        arc = new Image(Assets.getInstance().main_arc);
        bg.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);
        arc.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_WIDTH * .257f);
        arc.setColor(1f, 1f, 1f, 0f);

        item_bg_2p = new Image(Assets.getInstance().main_item_bg);
        item_bg_2p.setSize(Constants.HUD_SCREEN_WIDTH * .28f, Constants.HUD_SCREEN_WIDTH * .28f);
        item_bg_2p.setPosition(Constants.HUD_SCREEN_WIDTH * .17f, Constants.HUD_SCREEN_HEIGHT * .15f);
        item_light_2p = new Image(Assets.getInstance().main_item_light);
        item_light_2p.setSize(Constants.HUD_SCREEN_WIDTH * .28f, Constants.HUD_SCREEN_WIDTH * .28f);
        item_light_2p.setPosition(Constants.HUD_SCREEN_WIDTH * .17f, Constants.HUD_SCREEN_HEIGHT * .15f);
        item_light_2p.setOrigin(item_light_2p.getWidth() / 2, item_light_2p.getHeight() * .425f);
        item_2p = new Image(Assets.getInstance().main_item_online);
        item_2p.setSize(Constants.HUD_SCREEN_WIDTH * .28f, Constants.HUD_SCREEN_WIDTH * .28f);
        item_2p.setPosition(Constants.HUD_SCREEN_WIDTH * .17f, Constants.HUD_SCREEN_HEIGHT * .15f);

        item_coach = new Image(Assets.getInstance().main_item_coach);
        item_coach.setSize(Constants.HUD_SCREEN_WIDTH * .28f, Constants.HUD_SCREEN_WIDTH * .28f);
        item_coach.setPosition(Constants.HUD_SCREEN_WIDTH * .59f, Constants.HUD_SCREEN_HEIGHT * .15f);
        item_bg_coach = new Image(Assets.getInstance().main_item_bg);
        item_bg_coach.setSize(Constants.HUD_SCREEN_WIDTH * .28f, Constants.HUD_SCREEN_WIDTH * .28f);
        item_bg_coach.setPosition(Constants.HUD_SCREEN_WIDTH * .59f, Constants.HUD_SCREEN_HEIGHT * .15f);
        item_light_coach = new Image(Assets.getInstance().main_item_light);
        item_light_coach.setSize(Constants.HUD_SCREEN_WIDTH * .28f, Constants.HUD_SCREEN_WIDTH * .28f);
        item_light_coach.setPosition(Constants.HUD_SCREEN_WIDTH * .59f, Constants.HUD_SCREEN_HEIGHT * .15f);
        item_light_coach.setOrigin(item_light_coach.getWidth() / 2, item_light_coach.getHeight() * .425f);

        menu_shop = new Image(Assets.getInstance().main_icon_shop);
        menu_shop.setSize(Constants.HUD_SCREEN_WIDTH * .11f, Constants.HUD_SCREEN_WIDTH * .11f);
        menu_shop.setPosition(Constants.HUD_SCREEN_WIDTH * .72f, Constants.HUD_SCREEN_HEIGHT * .745f);
        menu_settings = new Image(Assets.getInstance().main_icon_settings);
        menu_settings.setSize(Constants.HUD_SCREEN_WIDTH * .11f, Constants.HUD_SCREEN_WIDTH * .11f);
        menu_settings.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .745f);

        profile_bg = new Image(Assets.getInstance().profile_background);
        profile_bg.setSize(Constants.HUD_SCREEN_WIDTH * .158f, Constants.HUD_SCREEN_WIDTH * .158f);
        profile_bg.setPosition(Constants.HUD_SCREEN_WIDTH * .03f,
                Constants.HUD_SCREEN_HEIGHT * .97f - Constants.HUD_SCREEN_WIDTH * .158f);

        region = new TextureRegion(Assets.getInstance().progress_circle);
        pbatch = new PolygonSpriteBatch();
        sprite = new ProgressCircle(region, pbatch, new Vector2(Constants.HUD_SCREEN_WIDTH * .158f, Constants.HUD_SCREEN_HEIGHT * .158f));
        sprite.setSize(Gdx.graphics.getWidth() * .158f, Gdx.graphics.getWidth() * .158f);
        sprite.setPosition(Gdx.graphics.getWidth() * .03f,
                Gdx.graphics.getHeight() * .97f - Gdx.graphics.getWidth() * .158f);

        avatar = new Image(new Util().getAvatar(GamePrefs.getInstance().avatar));
        avatar.setSize(Constants.HUD_SCREEN_WIDTH * .158f, Constants.HUD_SCREEN_WIDTH * .158f);
        avatar.setPosition(Constants.HUD_SCREEN_WIDTH * .03f,
                Constants.HUD_SCREEN_HEIGHT * .97f - Constants.HUD_SCREEN_WIDTH * .158f);

        profile_coin = new Image(Assets.getInstance().profile_coin);
        profile_coin.setSize(Constants.HUD_SCREEN_WIDTH * .228f, Constants.HUD_SCREEN_HEIGHT * .175f);
        profile_coin.setPosition(Constants.HUD_SCREEN_WIDTH * .12f, Constants.HUD_SCREEN_HEIGHT * .734f);

        this.mainTable.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);
        Gdx.input.setInputProcessor(mStage);
        mSkin = new Skin(Gdx.files.internal("data/uiskin.json"));

        coins_txt = new TextField(GamePrefs.getInstance().coins_num + "", mSkin);
        coins_txt.setDisabled(true);
        coins_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .228f, Constants.HUD_SCREEN_HEIGHT * .783f);

        mainTable.addActor(bg);
        mainTable.addActor(arc);
        mainTable.addActor(item_bg_2p);
        mainTable.addActor(item_light_2p);
        mainTable.addActor(item_2p);
        mainTable.addActor(item_bg_coach);
        mainTable.addActor(item_light_coach);
        mainTable.addActor(item_coach);
        mainTable.addActor(menu_shop);
        mainTable.addActor(menu_settings);
        mainTable.addActor(profile_coin);
        mainTable.addActor(profile_bg);
        mainTable.addActor(sprite);
        mainTable.addActor(avatar);
        mainTable.addActor(coins_txt);

        mStage.addActor(this.mainTable);

        item_2p.addListener(new ActorGestureListener() {
            public void tap(InputEvent paramAnonymousInputEvent, float paramAnonymousFloat1, float paramAnonymousFloat2, int paramAnonymousInt1, int paramAnonymousInt2) {
                MyGame.mainInstance.setGameSelectScreen();
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        item_coach.addListener(new ActorGestureListener() {
            public void tap(InputEvent paramAnonymousInputEvent, float paramAnonymousFloat1, float paramAnonymousFloat2, int paramAnonymousInt1, int paramAnonymousInt2) {
                MyGame.mainInstance.setCoachingScreen();
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        menu_shop.addListener(new ActorGestureListener() {
            public void tap(InputEvent paramAnonymousInputEvent, float paramAnonymousFloat1, float paramAnonymousFloat2, int paramAnonymousInt1, int paramAnonymousInt2) {
                System.out.println("tapppppp");
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                menu_shop.setSize(Constants.HUD_SCREEN_WIDTH * .09f, Constants.HUD_SCREEN_WIDTH * .09f);
                menu_shop.setPosition(Constants.HUD_SCREEN_WIDTH * .73f,
                        Constants.HUD_SCREEN_HEIGHT * .745f + Constants.HUD_SCREEN_WIDTH * .01f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                menu_shop.setSize(Constants.HUD_SCREEN_WIDTH * .11f, Constants.HUD_SCREEN_WIDTH * .11f);
                menu_shop.setPosition(Constants.HUD_SCREEN_WIDTH * .72f, Constants.HUD_SCREEN_HEIGHT * .745f);
            }
        });

        menu_settings.addListener(new ActorGestureListener() {
            public void tap(InputEvent paramAnonymousInputEvent, float paramAnonymousFloat1, float paramAnonymousFloat2, int paramAnonymousInt1, int paramAnonymousInt2) {
                MyGame.mainInstance.setSettingScreen();
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                menu_settings.setSize(Constants.HUD_SCREEN_WIDTH * .09f, Constants.HUD_SCREEN_WIDTH * .09f);
                menu_settings.setPosition(Constants.HUD_SCREEN_WIDTH * .86f, Constants.HUD_SCREEN_HEIGHT * .745f + Constants.HUD_SCREEN_WIDTH * .01f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                menu_settings.setSize(Constants.HUD_SCREEN_WIDTH * .11f, Constants.HUD_SCREEN_WIDTH * .11f);
                menu_settings.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .745f);
            }
        });

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
                        Tween.to(arc, 3, 1.1f)
                                .target(1f).ease(TweenEquations.easeOutQuad)
                                .start(mTweenManager).delay(0.0F);
                    }
                });

        int percent = (int) ((float) GamePrefs.getInstance().xp / ((float) GamePrefs.getInstance().level * 1000f) * 100f);
        Tween.to(sprite, 1, 10f)
                .target(percent).ease(TweenEquations.easeInExpo)
                .start(mTweenManager).delay(0.3F)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {

                    }
                });

        profile_coin.setWidth(0f);
        coins_txt.setText("0");
        coins_txt.setColor(1f, 1f, 1f, 0f);
        Tween.to(profile_coin, 4, 1.3f)
                .target(profile_coin.getX(), profile_coin.getY(),
                        Constants.HUD_SCREEN_WIDTH * .228f, Constants.HUD_SCREEN_HEIGHT * .175f)
                .ease(TweenEquations.easeInExpo)
                .start(mTweenManager).delay(0.0F)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                        coins_txt.setColor(1f, 1f, 1f, 1f);
                        Tween.to(coins_txt, 1, 1.1f)
                                .target(GamePrefs.getInstance().coins_num).ease(TweenEquations.easeOutQuad)
                                .start(mTweenManager).delay(0.0F);
                    }
                });

        repeatLightCoach();
        repeatLightGame();
    }

    public void repeatLightCoach() {
        item_light_2p.setRotation(item_light_coach.getRotation() % 360);
        Tween.to(item_light_coach, 6, 1.25f)
                .target(item_light_coach.getRotation() + 360).ease(TweenEquations.easeInOutExpo)
                .start(mTweenManager).delay(0f)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                        repeatLightCoach();
                    }
                });
    }

    public void repeatLightGame() {
        item_light_2p.setRotation(item_light_2p.getRotation() % 360);
        Tween.to(item_light_2p, 6, 1.45f)
                .target(item_light_2p.getRotation() + 360).ease(TweenEquations.easeInOutExpo)
                .start(mTweenManager).delay(0f)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                        repeatLightGame();
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
//        sprite.drawMe();

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
