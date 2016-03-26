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
import ir.eynajgroup.dribble2goal.MyGame;
import ir.eynajgroup.dribble2goal.Util.MyScrollPane;
import ir.eynajgroup.dribble2goal.render.textures.ProgressCircle;

/**
 * Created by kAvEh on 3/10/2016.
 */
public class SelectGameScreen implements Screen {

    TweenManager mTweenManager;
    private OrthographicCamera mMainCamera;
    SpriteBatch mMainBatch;
    Image bg;
    Image back;
    TextField coins_txt;
    Image stadium;
    Image arc;

    Image[] scrollImg;
    MyScrollPane mainmenuScroll;

    Stage mStage;
    Table mainTable;
    Skin mSkin;
    Random random;

    ProgressCircle sprite;
    TextureRegion region;
    PolygonSpriteBatch pbatch;

    public SelectGameScreen() {
        mTweenManager = MyGame.mTweenManager;
        mMainBatch = new SpriteBatch();

        random = new Random();

        mSkin = new Skin(Gdx.files.internal("data/uiskin.json"));

        mStage = new Stage(new FitViewport(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT), mMainBatch);
        mainTable = new Table();

        bg = new Image(Assets.getInstance().main_bg);
        bg.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        stadium = new Image(Assets.getInstance().select_game_1);
        stadium.setSize(Constants.HUD_SCREEN_WIDTH * .309f, Constants.HUD_SCREEN_HEIGHT);
        arc = new Image(Assets.getInstance().select_game_arc);
        arc.setSize(Constants.HUD_SCREEN_WIDTH * .184f, Constants.HUD_SCREEN_HEIGHT);
        arc.setPosition(Constants.HUD_SCREEN_WIDTH * .203f, 0);

        coins_txt = new TextField("120077", mSkin);
        coins_txt.setColor(.6f, .3f, .5f, 1f);
        coins_txt.setDisabled(true);
//        coins_txt.setSize(Constants.HUD_SCREEN_WIDTH * .15f, Constants.HUD_SCREEN_HEIGHT * .186f);
        coins_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .58f, Constants.HUD_SCREEN_HEIGHT * .055f);

        back = new Image(Assets.getInstance().icon_back);
        back.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
        back.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .04f);

        back.addListener(new ActorGestureListener() {
            public void tap(InputEvent paramAnonymousInputEvent, float paramAnonymousFloat1, float paramAnonymousFloat2, int paramAnonymousInt1, int paramAnonymousInt2) {
                MyGame.mainInstance.setMainScreen();
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        scrollImg = new Image[6];
        scrollImg[0] = new Image(Assets.getInstance().stadium_button_1);
        scrollImg[0].setSize(Constants.HUD_SCREEN_WIDTH * .3125f, Constants.HUD_SCREEN_HEIGHT * .18f);
        scrollImg[1] = new Image(Assets.getInstance().stadium_button_2);
        scrollImg[1].setSize(Constants.HUD_SCREEN_WIDTH * .3125f, Constants.HUD_SCREEN_HEIGHT * .18f);
        scrollImg[2] = new Image(Assets.getInstance().stadium_button_3);
        scrollImg[2].setSize(Constants.HUD_SCREEN_WIDTH * .3125f, Constants.HUD_SCREEN_HEIGHT * .18f);
        scrollImg[3] = new Image(Assets.getInstance().stadium_button_4);
        scrollImg[3].setSize(Constants.HUD_SCREEN_WIDTH * .3125f, Constants.HUD_SCREEN_HEIGHT * .18f);
        scrollImg[4] = new Image(Assets.getInstance().stadium_button_5);
        scrollImg[4].setSize(Constants.HUD_SCREEN_WIDTH * .3125f, Constants.HUD_SCREEN_HEIGHT * .18f);
        scrollImg[5] = new Image(Assets.getInstance().stadium_button_6);
        scrollImg[5].setSize(Constants.HUD_SCREEN_WIDTH * .3125f, Constants.HUD_SCREEN_HEIGHT * .18f);

        scrollImg[0].addListener(new ActorGestureListener() {
            public void tap(InputEvent paramAnonymousInputEvent, float paramAnonymousFloat1, float paramAnonymousFloat2, int paramAnonymousInt1, int paramAnonymousInt2) {
            }
        });
        scrollImg[1].addListener(new ActorGestureListener() {
            public void tap(InputEvent paramAnonymousInputEvent, float paramAnonymousFloat1, float paramAnonymousFloat2, int paramAnonymousInt1, int paramAnonymousInt2) {
            }
        });
        scrollImg[2].addListener(new ActorGestureListener() {
            public void tap(InputEvent paramAnonymousInputEvent, float paramAnonymousFloat1, float paramAnonymousFloat2, int paramAnonymousInt1, int paramAnonymousInt2) {
            }
        });
        scrollImg[3].addListener(new ActorGestureListener() {
            public void tap(InputEvent paramAnonymousInputEvent, float paramAnonymousFloat1, float paramAnonymousFloat2, int paramAnonymousInt1, int paramAnonymousInt2) {
            }
        });
        scrollImg[4].addListener(new ActorGestureListener() {
            public void tap(InputEvent paramAnonymousInputEvent, float paramAnonymousFloat1, float paramAnonymousFloat2, int paramAnonymousInt1, int paramAnonymousInt2) {
            }
        });
        scrollImg[5].addListener(new ActorGestureListener() {
            public void tap(InputEvent paramAnonymousInputEvent, float paramAnonymousFloat1, float paramAnonymousFloat2, int paramAnonymousInt1, int paramAnonymousInt2) {
            }
        });

        mainmenuScroll = new MyScrollPane(this.scrollImg, 6,
                Constants.HUD_SCREEN_WIDTH, (int) Constants.HUD_SCREEN_HEIGHT,
                (int) (Constants.HUD_SCREEN_WIDTH * .07f), 0,
                Constants.HUD_SCREEN_WIDTH * .3125f, Constants.HUD_SCREEN_HEIGHT * .18f,
                Constants.HUD_SCREEN_HEIGHT * .12f);
        mainmenuScroll.setVisible(true);

        region = new TextureRegion(Assets.getInstance().progress_circle);
        pbatch = new PolygonSpriteBatch();
        sprite = new ProgressCircle(region, pbatch, new Vector2(Constants.HUD_SCREEN_WIDTH * .2f, Constants.HUD_SCREEN_HEIGHT * .2f));
        sprite.setSize(Constants.HUD_SCREEN_WIDTH * .2f, Constants.HUD_SCREEN_HEIGHT * .2f);
        sprite.setPosition(Constants.HUD_SCREEN_WIDTH * .3f, Constants.HUD_SCREEN_HEIGHT * .6f);

        mainTable.addActor(bg);
        mainTable.addActor(back);
        mainTable.addActor(stadium);
        mainTable.addActor(arc);
        mainTable.addActor(coins_txt);
        mainTable.addActor(mainmenuScroll);
        mainTable.addActor(sprite);

        this.mainTable.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);
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

        Tween.to(sprite, 1, 1f)
                .target(80).ease(TweenEquations.easeInExpo)
                .start(mTweenManager).delay(0.0F)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {

                    }
                });
    }

    @Override
    public void render(float delta) {
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