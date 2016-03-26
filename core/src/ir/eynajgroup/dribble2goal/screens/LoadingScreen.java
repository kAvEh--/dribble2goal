package ir.eynajgroup.dribble2goal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import ir.eynajgroup.dribble2goal.Assets;
import ir.eynajgroup.dribble2goal.Constants;
import ir.eynajgroup.dribble2goal.MyGame;
import ir.eynajgroup.dribble2goal.render.textures.ProgressLine;

/**
 * Created by kAvEh on 3/16/2016.
 */
public class LoadingScreen implements Screen {

    TweenManager mTweenManager;
    private OrthographicCamera mMainCamera;
    SpriteBatch mMainBatch;

    Image bg;
    Image logo;
    Image bar_bg;
    Image bar_mid;
    Image bar_top;

    Stage mStage;
    Table mainTable;
    Skin mSkin;

    ProgressLine progress;

    public LoadingScreen() {
        mTweenManager = MyGame.mTweenManager;
        mMainBatch = new SpriteBatch();

        mSkin = new Skin(Gdx.files.internal("data/uiskin.json"));

        mStage = new Stage(new FitViewport(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT), mMainBatch);
        mainTable = new Table();

        bg = new Image(Assets.getInstance().main_bg_fade);
        bg.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        logo = new Image(Assets.getInstance().logo);
        logo.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        bar_bg = new Image(Assets.getInstance().loading_bar_bg);
        bar_bg.setSize(Constants.HUD_SCREEN_WIDTH * .487f, Constants.HUD_SCREEN_HEIGHT * .056f);
        bar_bg.setPosition(Constants.HUD_SCREEN_WIDTH * .26f, Constants.HUD_SCREEN_HEIGHT * .1f);

        bar_mid = new Image(Assets.getInstance().loading_bar_mid);
        bar_mid.setSize(Constants.HUD_SCREEN_WIDTH * .487f, Constants.HUD_SCREEN_HEIGHT * .056f);
        bar_mid.setPosition(Constants.HUD_SCREEN_WIDTH * .26f, Constants.HUD_SCREEN_HEIGHT * .1f);

        bar_top = new Image(Assets.getInstance().loading_bar_top);
        bar_top.setSize(Constants.HUD_SCREEN_WIDTH * .112f, Constants.HUD_SCREEN_HEIGHT * .056f);
        bar_top.setPosition(Constants.HUD_SCREEN_WIDTH * .26f, Constants.HUD_SCREEN_HEIGHT * .1f);

        Vector2 size = new Vector2(Constants.HUD_SCREEN_WIDTH * .487f, Constants.HUD_SCREEN_HEIGHT * .056f);
        progress = new ProgressLine(Assets.getInstance().loading_bar_bg, new SpriteBatch(), new TextureRegion(), size, false);
        progress.setSize(size.x,size.y);
        progress.setPosition(Constants.HUD_SCREEN_WIDTH * .26f, Constants.HUD_SCREEN_HEIGHT * .1f);
        progress.setPercentage(0);

        mainTable.addActor(bg);
        mainTable.addActor(logo);
        mainTable.addActor(bar_bg);
        mainTable.addActor(progress);
        mainTable.addActor(bar_top);

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
        Tween.to(this.mainTable, 5, .1f)
                .target(1f).ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(0.0F)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {

                    }
                });

        Tween.to(progress, 1, 5f)
                .target(1).ease(TweenEquations.easeInCubic)
                .start(mTweenManager).delay(0F)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
//                        long tt = System.currentTimeMillis();
//                        System.out.println("----->" + ((tt - start) / 1000));
                    }
                });
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