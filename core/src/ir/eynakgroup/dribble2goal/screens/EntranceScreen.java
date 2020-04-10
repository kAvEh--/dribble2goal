package ir.eynakgroup.dribble2goal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import org.json.JSONException;
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
 * Created by Eynak_PC2 on 7/9/2016.
 */
public class EntranceScreen implements Screen, InputProcessor {

    TweenManager mTweenManager;
    private OrthographicCamera mMainCamera;
    SpriteBatch mMainBatch;

    Image bg;
    Image login;
    Image register;
    Image googleLogin;

    Stage mStage;
    Table mainTable;
    Skin mSkin;

    public EntranceScreen() {
        mTweenManager = MyGame.mTweenManager;
        mMainBatch = new SpriteBatch();

        mSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        mStage = new Stage(new FitViewport(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT), mMainBatch);
        mainTable = new Table();

        bg = new Image(Assets.getInstance().main_bg);
        bg.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        googleLogin = new Image(Assets.getInstance().login_google);
        googleLogin.setSize(Constants.HUD_SCREEN_WIDTH * .48f, Constants.HUD_SCREEN_HEIGHT * .29f);
        googleLogin.setPosition(Constants.HUD_SCREEN_WIDTH * .25f, Constants.HUD_SCREEN_HEIGHT * .65f);

        login = new Image(Assets.getInstance().login);
        login.setSize(Constants.HUD_SCREEN_WIDTH * .48f, Constants.HUD_SCREEN_HEIGHT * .29f);
        login.setPosition(Constants.HUD_SCREEN_WIDTH * .25f, Constants.HUD_SCREEN_HEIGHT * .0f);

        register = new Image(Assets.getInstance().register);
        register.setSize(Constants.HUD_SCREEN_WIDTH * .48f, Constants.HUD_SCREEN_HEIGHT * .29f);
        register.setPosition(Constants.HUD_SCREEN_WIDTH * .25f, Constants.HUD_SCREEN_HEIGHT * .33f);

        login.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                MyGame.mainInstance.setLoginScreen(true);
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                login.setSize(Constants.HUD_SCREEN_WIDTH * .48f, Constants.HUD_SCREEN_HEIGHT * .29f);
                login.setPosition(Constants.HUD_SCREEN_WIDTH * .25f, Constants.HUD_SCREEN_HEIGHT * .0f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                login.setSize(Constants.HUD_SCREEN_WIDTH * .48f, Constants.HUD_SCREEN_HEIGHT * .29f);
                login.setPosition(Constants.HUD_SCREEN_WIDTH * .25f, Constants.HUD_SCREEN_HEIGHT * .0f);
            }
        });

        register.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                MyGame.mainInstance.setLoginScreen(false);
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                register.setSize(Constants.HUD_SCREEN_WIDTH * .48f * .8f, Constants.HUD_SCREEN_HEIGHT * .29f * .8f);
                register.setPosition(Constants.HUD_SCREEN_WIDTH * .298f, Constants.HUD_SCREEN_HEIGHT * .029f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                register.setSize(Constants.HUD_SCREEN_WIDTH * .48f, Constants.HUD_SCREEN_HEIGHT * .29f);
                register.setPosition(Constants.HUD_SCREEN_WIDTH * .25f, Constants.HUD_SCREEN_HEIGHT * .0f);
            }
        });

        googleLogin.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                MyGame.mPlayServices.signIn(MyGame.loginFinishHandler);
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                googleLogin.setSize(Constants.HUD_SCREEN_WIDTH * .48f * .8f, Constants.HUD_SCREEN_HEIGHT * .29f * .8f);
                googleLogin.setPosition(Constants.HUD_SCREEN_WIDTH * .298f, Constants.HUD_SCREEN_HEIGHT * .679f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                googleLogin.setSize(Constants.HUD_SCREEN_WIDTH * .48f, Constants.HUD_SCREEN_HEIGHT * .29f);
                googleLogin.setPosition(Constants.HUD_SCREEN_WIDTH * .25f, Constants.HUD_SCREEN_HEIGHT * .65f);
            }
        });

        mainTable.addActor(bg);
        mainTable.addActor(login);
        mainTable.addActor(register);
        mainTable.addActor(googleLogin);

        this.mainTable.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(mStage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        mStage.addActor(this.mainTable);

        ServerTool.getInstance().socket.on("loggedInPlayer", onLoginListener);

//        checkSavedData();
    }

    private void checkSavedData() {
        System.out.println(GamePrefs.getInstance().getUserName() + "^^^^^^" + GamePrefs.getInstance().getPassword());
        if (GamePrefs.getInstance().getUserName() != null &&
                GamePrefs.getInstance().getUserName().length() > 0) {
            ServerTool.getInstance().login(GamePrefs.getInstance().getUserName(), GamePrefs.getInstance().getPassword());

        }
    }

    private Emitter.Listener onLoginListener = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            JSONObject response = null;
            try {
                response = new JSONObject((String) args[0]);
                try {
                    String s = response.getString("err");
                    System.out.println(s + "---- ERR LOGIN D2G ------------------->>>>>>>>>>>>>>>>>>");
                    GamePrefs.getInstance().setUserName("");
                    GamePrefs.getInstance().setPassword("");
                } catch (Exception e) {
                    if (setData(response)) {
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                MyGame.mainInstance.setMainScreen();
                            }
                        });
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    };

    private boolean setData(JSONObject data) {
        try {
//            if (data.getBoolean("rc")) {
//
//            } else {
//
//            }
            System.out.println(data);
            GamePrefs.getInstance().isDailyAvailable = data.getBoolean("dailyCoin");
            JSONObject player = data.getJSONObject("player");
            GamePrefs.getInstance().game_won = player.getInt("winCount");
            GamePrefs.getInstance().game_played = player.getInt("gameCount");
            GamePrefs.getInstance().winRate = player.getInt("winRate");
            GamePrefs.getInstance().cleanSheet = player.getInt("cleanSheet");
            GamePrefs.getInstance().shirt = player.getInt("shirt") - 1;
            GamePrefs.getInstance().name = player.getString("nickname");
            GamePrefs.getInstance().avatar = player.getInt("avatarId");
            GamePrefs.getInstance().setUserName(player.getString("username"));
            GamePrefs.getInstance().playerId = player.getString("id");
            GamePrefs.getInstance().goals = player.getInt("goals");
            GamePrefs.getInstance().winInaRaw = player.getInt("winInaRow");
            GamePrefs.getInstance().coins_num = player.getInt("coin");
            JSONObject level = player.getJSONObject("level");
            GamePrefs.getInstance().level = level.getInt("lvl");
            GamePrefs.getInstance().xp = level.getInt("xp");
//            JSONObject achs = player.getJSONObject("achievements");
//            GamePrefs.getInstance().achieve_goal = achs.getInt("goal");
//            GamePrefs.getInstance().achieve_cleanSheet = achs.getInt("cleanSheet");
//            GamePrefs.getInstance().achieve_win = achs.getInt("win");
//            GamePrefs.getInstance().achieve_winInaRow = achs.getInt("winInaRow");
//            //First Player Data
//            JSONObject tmpPlayer = player.getJSONObject("players").getJSONObject("1");
//            GamePrefs.getInstance().players[0][0] = tmpPlayer.getInt("stamina");
//            GamePrefs.getInstance().players[0][1] = tmpPlayer.getInt("size");
//            GamePrefs.getInstance().players[0][2] = tmpPlayer.getInt("speed");
//            //Second Player Data
//            tmpPlayer = player.getJSONObject("players").getJSONObject("2");
//            GamePrefs.getInstance().players[1][0] = tmpPlayer.getInt("stamina");
//            GamePrefs.getInstance().players[1][1] = tmpPlayer.getInt("size");
//            GamePrefs.getInstance().players[1][2] = tmpPlayer.getInt("speed");
//            //Third Player Data
//            tmpPlayer = player.getJSONObject("players").getJSONObject("3");
//            GamePrefs.getInstance().players[2][0] = tmpPlayer.getInt("stamina");
//            GamePrefs.getInstance().players[2][1] = tmpPlayer.getInt("size");
//            GamePrefs.getInstance().players[2][2] = tmpPlayer.getInt("speed");
//            //Fourth Player Data
//            tmpPlayer = player.getJSONObject("players").getJSONObject("4");
//            GamePrefs.getInstance().players[3][0] = tmpPlayer.getInt("stamina");
//            GamePrefs.getInstance().players[3][1] = tmpPlayer.getInt("size");
//            GamePrefs.getInstance().players[3][2] = tmpPlayer.getInt("speed");
//            //Fifth Player Data
//            tmpPlayer = player.getJSONObject("players").getJSONObject("5");
//            GamePrefs.getInstance().players[4][0] = tmpPlayer.getInt("stamina");
//            GamePrefs.getInstance().players[4][1] = tmpPlayer.getInt("size");
//            GamePrefs.getInstance().players[4][2] = tmpPlayer.getInt("speed");
            //Lineup
            String tmpLineUp = player.getString("lineup");
            if (tmpLineUp.equals("A")) {
                GamePrefs.getInstance().lineup = new int[]{0, 1, 2, 3, 4};
            } else if (tmpLineUp.equals("B")) {
                GamePrefs.getInstance().lineup = new int[]{0, 1, 3, 2, 4};
            } else {
                GamePrefs.getInstance().lineup = new int[]{0, 1, 4, 2, 3};
            }
            //Shirts
//            tmp = player.getJSONObject("shirts");
//            for (int i = 0; i < 24; i++) {
//                GamePrefs.getInstance().shirts[i] = tmp.getInt((i + 1) + "");
//            }
//            GamePrefs.getInstance().shirts[GamePrefs.getInstance().shirt] = 2;

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
            Gdx.app.exit();
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