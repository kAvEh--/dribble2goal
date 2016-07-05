package ir.eynajgroup.dribble2goal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
import ir.eynajgroup.dribble2goal.Assets;
import ir.eynajgroup.dribble2goal.Constants;
import ir.eynajgroup.dribble2goal.GamePrefs;
import ir.eynajgroup.dribble2goal.MyGame;
import ir.eynajgroup.dribble2goal.ParticleEffectActor;
import ir.eynajgroup.dribble2goal.Server.ServerTool;
import ir.eynajgroup.dribble2goal.render.textures.ProgressLine;

/**
 * Created by kAvEh on 3/16/2016.
 */
public class LoginScreen implements Screen {

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

    Image login;

    Stage mStage;
    Table mainTable;
    Skin mSkin;

    ProgressLine progress;
    ParticleEffectActor pActor;

    public LoginScreen() {
        mTweenManager = MyGame.mTweenManager;
        mMainBatch = new SpriteBatch();

        mSkin = new Skin(Gdx.files.internal("data/uiskin.json"));

        mStage = new Stage(new FitViewport(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT), mMainBatch);
        mainTable = new Table();

        mStage.setKeyboardFocus(email_txt);

        bg = new Image(Assets.getInstance().main_bg);
        bg.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        email = new Image(Assets.getInstance().login_email);
        email.setSize(Constants.HUD_SCREEN_WIDTH * .445f, Constants.HUD_SCREEN_HEIGHT * .283f);
        email.setPosition(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * .7f);

        email_txt = new TextField("", mSkin);
        email_txt.setSize(Constants.HUD_SCREEN_WIDTH * .59f, Constants.HUD_SCREEN_HEIGHT * .12f);
        email_txt.setPosition(0, Constants.HUD_SCREEN_HEIGHT * .81f);
        email_txt.setAlignment(Align.right);
        email_txt.getOnscreenKeyboard().show(true);
        email_txt.setMaxLength(35);

        userName = new Image(Assets.getInstance().login_username);
        userName.setSize(Constants.HUD_SCREEN_WIDTH * .445f, Constants.HUD_SCREEN_HEIGHT * .283f);

        password = new Image(Assets.getInstance().login_pass);
        password.setSize(Constants.HUD_SCREEN_WIDTH * .445f, Constants.HUD_SCREEN_HEIGHT * .283f);
        password.setPosition(Constants.HUD_SCREEN_WIDTH * .2775f, Constants.HUD_SCREEN_HEIGHT * .42f);

        password_txt = new TextField("", mSkin);
        password_txt.setSize(Constants.HUD_SCREEN_WIDTH * .59f, Constants.HUD_SCREEN_HEIGHT * .12f);
        password_txt.setPosition(0, Constants.HUD_SCREEN_HEIGHT * .53f);
        password_txt.setAlignment(Align.right);

        rePassword = new Image(Assets.getInstance().login_pass_re);
        rePassword.setSize(Constants.HUD_SCREEN_WIDTH * .445f, Constants.HUD_SCREEN_HEIGHT * .283f);

        login = new Image(Assets.getInstance().setting_game_icon);
        login.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
        login.setPosition(Constants.HUD_SCREEN_WIDTH * .8f, Constants.HUD_SCREEN_HEIGHT * .75f);

//        ServerTool.getInstance().register("omid", "123", "Mid Tagh");

        login.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                ServerTool.getInstance().login(email_txt.getText(), password_txt.getText());
                GamePrefs.getInstance().setUserName(email_txt.getText());
                GamePrefs.getInstance().setPassword(password_txt.getText());

                ServerTool.getInstance().socket.on("loggedInPlayer", onLoginListener);
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        ParticleEffect p = new ParticleEffect();
        p.load(Gdx.files.internal("effects/test.p"), Gdx.files.internal("effects"));
//        p.setPosition(Constants.HUD_SCREEN_WIDTH * .01f, Constants.HUD_SCREEN_HEIGHT * .01f);
        p.allowCompletion();

        pActor = new ParticleEffectActor(p);
        pActor.setPosition(Constants.HUD_SCREEN_WIDTH * .9f, Constants.HUD_SCREEN_HEIGHT * .27f);

        mainTable.addActor(bg);
        mainTable.addActor(email);
        mainTable.addActor(email_txt);
        mainTable.addActor(password);
        mainTable.addActor(password_txt);
        mainTable.addActor(login);
        mainTable.addActor(pActor);

        this.mainTable.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);
        Gdx.input.setInputProcessor(mStage);

        mStage.addActor(this.mainTable);
    }

    private Emitter.Listener onLoginListener = new Emitter.Listener() {

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
                            MyGame.mainInstance.setMainScreen();
                        }
                    });
                }
            }
        }

    };

    private boolean setData(JSONObject data) {
        try {
            if (data.getBoolean("rc")) {

            } else {

            }
            System.out.println(data);
            GamePrefs.getInstance().isDailyAvailable = data.getBoolean("dailyCoin");
            JSONObject player = data.getJSONObject("player");
            GamePrefs.getInstance().game_won = player.getInt("winCount");
            GamePrefs.getInstance().game_played = player.getInt("gameCount");
            GamePrefs.getInstance().winRate = player.getInt("winrate");
            GamePrefs.getInstance().cleanSheet = player.getInt("cleanSheet");
            GamePrefs.getInstance().shirt = player.getInt("shirt") - 1;
            GamePrefs.getInstance().name = player.getString("nickname");
            GamePrefs.getInstance().setUserName(player.getString("username"));
            GamePrefs.getInstance().playerId = player.getInt("playerId");
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
            GamePrefs.getInstance().players[0][1] = tmpPlayer.getInt("speed");
            GamePrefs.getInstance().players[0][2] = tmpPlayer.getInt("size");
            //Second Player Data
            tmpPlayer = player.getJSONObject("players").getJSONObject("2");
            GamePrefs.getInstance().players[1][0] = tmpPlayer.getInt("stamina");
            GamePrefs.getInstance().players[1][1] = tmpPlayer.getInt("speed");
            GamePrefs.getInstance().players[1][2] = tmpPlayer.getInt("size");
            //Third Player Data
            tmpPlayer = player.getJSONObject("players").getJSONObject("3");
            GamePrefs.getInstance().players[2][0] = tmpPlayer.getInt("stamina");
            GamePrefs.getInstance().players[2][1] = tmpPlayer.getInt("speed");
            GamePrefs.getInstance().players[2][2] = tmpPlayer.getInt("size");
            //Fourth Player Data
            tmpPlayer = player.getJSONObject("players").getJSONObject("4");
            GamePrefs.getInstance().players[3][0] = tmpPlayer.getInt("stamina");
            GamePrefs.getInstance().players[3][1] = tmpPlayer.getInt("speed");
            GamePrefs.getInstance().players[3][2] = tmpPlayer.getInt("size");
            //Fifth Player Data
            tmpPlayer = player.getJSONObject("players").getJSONObject("5");
            GamePrefs.getInstance().players[4][0] = tmpPlayer.getInt("stamina");
            GamePrefs.getInstance().players[4][1] = tmpPlayer.getInt("speed");
            GamePrefs.getInstance().players[4][2] = tmpPlayer.getInt("size");
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

        Tween.to(pActor, 1, 13f)
                .target(Constants.HUD_SCREEN_WIDTH * .2f, Constants.HUD_SCREEN_HEIGHT * .9f).ease(TweenEquations.easeInExpo)
                .start(mTweenManager).delay(0.0F)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {

                    }
                });
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