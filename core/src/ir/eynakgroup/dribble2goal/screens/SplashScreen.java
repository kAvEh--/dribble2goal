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
import ir.eynakgroup.dribble2goal.render.textures.LoadingProgress;

/**
 * Created by Eynak_PC2 on 7/31/2016.
 */
public class SplashScreen implements Screen {

    TweenManager mTweenManager;
    private OrthographicCamera mMainCamera;
    SpriteBatch mMainBatch;

    Image bg;
    Image logo;
    Image loading_bar;
    LoadingProgress progressbar;

    Stage mStage;
    Table mainTable;
    Skin mSkin;

    int login_flag = 0;
    boolean loading_flag = false;

    boolean rc_flag = false;

    public SplashScreen() {
        Assets.getInstance().initLoading();
        Assets.getInstance().assetManager.finishLoading();
        Assets.getInstance().initSplashTexture();
        mTweenManager = MyGame.mTweenManager;
        mMainBatch = new SpriteBatch();

        mSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        mStage = new Stage(new FitViewport(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT), mMainBatch);
        mainTable = new Table();

        bg = new Image(Assets.getInstance().main_bg_fade);
        bg.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        logo = new Image(Assets.getInstance().logo);
        logo.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        loading_bar = new Image(Assets.getInstance().loading_bar);
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
        System.out.println(GamePrefs.getInstance().getUserName() + "^^^^^^" + GamePrefs.getInstance().getPassword());
        if (GamePrefs.getInstance().getUserName() != null &&
                GamePrefs.getInstance().getUserName().length() > 0) {
            ServerTool.getInstance().login(GamePrefs.getInstance().getUserName(), GamePrefs.getInstance().getPassword());

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
                if (setData(response)) {
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

    private boolean setData(JSONObject data) {
        try {
            if (data.getBoolean("rc")) {
                rc_flag = true;
            } else {
                rc_flag = false;
            }
            GamePrefs.getInstance().isDailyAvailable = data.getBoolean("dailyCoin");
            JSONObject player = data.getJSONObject("player");
            GamePrefs.getInstance().game_won = player.getInt("winCount");
            GamePrefs.getInstance().game_played = player.getInt("gameCount");
            GamePrefs.getInstance().winRate = (int) (player.getDouble("winrate") * 100f);
            GamePrefs.getInstance().cleanSheet = player.getInt("cleanSheet");
            GamePrefs.getInstance().position = player.getInt("formation");
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
    }

    @Override
    public void render(float delta) {
        loading_flag = Assets.assetManager.update();

        // display loading information
        progressbar.setPercentage(Assets.assetManager.getProgress());

        if (loading_flag) {
            Assets.getInstance().init();
            if (login_flag == 2) {
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