package ir.eynakgroup.dribble2goal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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
import ir.eynakgroup.dribble2goal.Server.ServerTool;
import ir.eynakgroup.dribble2goal.Util.Util;
import ir.eynakgroup.dribble2goal.render.textures.LoadingProgress;

/**
 * Created by Eynak_PC2 on 7/31/2016.
 */
public class SplashScreen implements Screen {

    private TweenManager mTweenManager;
    private OrthographicCamera mMainCamera;

    private LoadingProgress progressbar;

    private Stage mStage;
    private Table mainTable;

    private int login_flag = 0;

    private Util util;

    public SplashScreen() {
        Assets.getInstance().initLoading();
        Assets.getInstance().assetManager.finishLoading();
        Assets.getInstance().initSplashTexture();
        mTweenManager = MyGame.mTweenManager;
        SpriteBatch mMainBatch = new SpriteBatch();

        Skin mSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        util = new Util();

        mStage = new Stage(new FitViewport(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT), mMainBatch);
        mainTable = new Table();

        Image bg = new Image(Assets.getInstance().main_bg_fade);
        bg.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        Image logo = new Image(Assets.getInstance().logo);
        logo.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        Image loading_bar = new Image(Assets.getInstance().loading_bar);
        loading_bar.setSize(Constants.HUD_SCREEN_WIDTH * .5f, Constants.HUD_SCREEN_HEIGHT * .0585f);
        loading_bar.setPosition(Constants.HUD_SCREEN_WIDTH * .25f, Constants.HUD_SCREEN_HEIGHT * .1f);

        progressbar = new LoadingProgress(new TextureRegion(Assets.getInstance().loading_bar), new PolygonSpriteBatch(), this.mStage);
        progressbar.setSize(Constants.HUD_SCREEN_WIDTH * .5f, Constants.HUD_SCREEN_HEIGHT * .0585f);
        progressbar.setPosition(Constants.HUD_SCREEN_WIDTH * .25f, Constants.HUD_SCREEN_HEIGHT * .1f);

        logo = new Image(Assets.getInstance().logo);
        logo.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        mainTable.addActor(bg);
        mainTable.addActor(logo);
        mainTable.addActor(loading_bar);
        mainTable.addActor(progressbar);

        this.mainTable.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);
        Gdx.input.setInputProcessor(mStage);

        mStage.addActor(this.mainTable);

        checkSavedData();

        Assets.getInstance().loadingAssests();
    }

    private void checkSavedData() {
        System.out.println(GamePrefs.getInstance().getUserName() + "^^^@##@^^^" + GamePrefs.getInstance().getPassword());
        if (GamePrefs.getInstance().getUserName() != null &&
                GamePrefs.getInstance().getUserName().length() > 0) {
            ServerTool.getInstance().login(GamePrefs.getInstance().getUserName(), GamePrefs.getInstance().getPassword());

            ServerTool.getInstance().socket.off("loggedInPlayer");
            ServerTool.getInstance().socket.on("loggedInPlayer", onLoginListener);
        } else {
            login_flag = 1;
        }
    }

    private Emitter.Listener onLoginListener = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            JSONObject response = (JSONObject) args[0];
            System.out.println("****" + response + "---------------------");
            try {
                String s = response.getString("err");
                System.out.println(s + "----");
                GamePrefs.getInstance().setUserName("");
                GamePrefs.getInstance().setPassword("");
                login_flag = 1;
            } catch (Exception e) {
                if (util.setData(response)) {
                    login_flag = 2;
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                }
            }
        }

    };

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
    }

    @Override
    public void render(float delta) {
        boolean loading_flag = Assets.assetManager.update();

        // display loading information
        progressbar.setPercentage(Assets.assetManager.getProgress());

        if (loading_flag) {
            Assets.getInstance().init();
            if (login_flag == 2) {
                boolean rc_flag = false;
                if (rc_flag) {
                    MyGame.mainInstance.setReconnectScreen();
                } else {
                    MyGame.mainInstance.setMainScreen();
                }
            } else if (login_flag == 1) {
                MyGame.mainInstance.setEntranceScreen();
            }
        }

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
}