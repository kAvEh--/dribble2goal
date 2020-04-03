package ir.eynakgroup.dribble2goal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
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
import ir.eynakgroup.dribble2goal.MatchStats;
import ir.eynakgroup.dribble2goal.MyGame;
import ir.eynakgroup.dribble2goal.Server.ServerTool;

/**
 * Created by Eynak_PC2 on 8/20/2016.
 */
public class ReconnectScreen implements Screen {

    TweenManager mTweenManager;
    private OrthographicCamera mMainCamera;
    SpriteBatch mMainBatch;

    Image bg;
    Image login;
    Image register;

    Stage mStage;
    Table mainTable;
    Skin mSkin;

    MatchStats stat;

    public ReconnectScreen() {
        mTweenManager = MyGame.mTweenManager;
        mMainBatch = new SpriteBatch();

        mSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        mStage = new Stage(new FitViewport(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT), mMainBatch);
        mainTable = new Table();

        bg = new Image(Assets.getInstance().main_bg);
        bg.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        login = new Image(Assets.getInstance().login);
        login.setSize(Constants.HUD_SCREEN_WIDTH * .5f, Constants.HUD_SCREEN_HEIGHT * .3f);
        login.setPosition(Constants.HUD_SCREEN_WIDTH * .25f, Constants.HUD_SCREEN_HEIGHT * .45f);

        register = new Image(Assets.getInstance().register);
        register.setSize(Constants.HUD_SCREEN_WIDTH * .5f, Constants.HUD_SCREEN_HEIGHT * .3f);
        register.setPosition(Constants.HUD_SCREEN_WIDTH * .25f, Constants.HUD_SCREEN_HEIGHT * .15f);

        login.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                ServerTool.getInstance().reConnect();
                ServerTool.getInstance().socket.on("reconnectData", onReconnectListener);
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                login.setSize(Constants.HUD_SCREEN_WIDTH * .5f * .8f, Constants.HUD_SCREEN_HEIGHT * .3f * .8f);
                login.setPosition(Constants.HUD_SCREEN_WIDTH * .3f, Constants.HUD_SCREEN_HEIGHT * .48f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                login.setSize(Constants.HUD_SCREEN_WIDTH * .5f, Constants.HUD_SCREEN_HEIGHT * .3f);
                login.setPosition(Constants.HUD_SCREEN_WIDTH * .25f, Constants.HUD_SCREEN_HEIGHT * .45f);
            }
        });

        register.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                register.setSize(Constants.HUD_SCREEN_WIDTH * .5f * .8f, Constants.HUD_SCREEN_HEIGHT * .3f * .8f);
                register.setPosition(Constants.HUD_SCREEN_WIDTH * .3f, Constants.HUD_SCREEN_HEIGHT * .18f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                register.setSize(Constants.HUD_SCREEN_WIDTH * .5f, Constants.HUD_SCREEN_HEIGHT * .3f);
                register.setPosition(Constants.HUD_SCREEN_WIDTH * .25f, Constants.HUD_SCREEN_HEIGHT * .15f);
            }
        });

        mainTable.addActor(bg);
        mainTable.addActor(login);
        mainTable.addActor(register);

        this.mainTable.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);
        Gdx.input.setInputProcessor(mStage);

        mStage.addActor(this.mainTable);

//        checkSavedData();
    }

    private Emitter.Listener onReconnectListener = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            final JSONObject response = (JSONObject) args[0];
            System.out.println(response + "---------------------");
            try {
                String s = response.getString("err");
                System.out.println(s + "----");
                GamePrefs.getInstance().setUserName("");
                GamePrefs.getInstance().setPassword("");
            } catch (Exception e) {
                try {
                    if (setData(response.getJSONObject("findMatch"))) {
                        final JSONObject tmp = response.getJSONObject("game");
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                MyGame.mainInstance.resumeGame(stat, tmp);
                            }
                        });
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        }
    };

    public boolean setData(JSONObject data) {
        JSONObject tmp;
        try {
            stat = new MatchStats();
            stat.roomNum = data.getInt("roomNum");
            if (data.getString("playerId1") == GamePrefs.getInstance().playerId) {
                stat.reset();
                stat.myName = data.getString("username1");
                stat.oppName = data.getString("username2");
                stat.myWinRate = data.getDouble("winrate1");
                stat.oppWinRate = data.getDouble("winrate2");
                stat.myShirt = data.getInt("shirt1");
                stat.oppShirt = data.getInt("shirt2");
                tmp = data.getJSONObject("level1");
                stat.myLevel = tmp.getInt("lvl");
                stat.myXp = tmp.getInt("xp");
                tmp = data.getJSONObject("level2");
                stat.oppLevel = tmp.getInt("lvl");
                stat.oppXp = tmp.getInt("xp");
                stat.myFormation = data.getInt("formation1");
                stat.oppFormation = data.getInt("formation2");
                stat.myAvatar = data.getInt("avatarId1");
                stat.oppAvatar = data.getInt("avatarId2");

                tmp = data.getJSONObject("lineup1");
                boolean flag = true;
                for (int i = 1; i < 6; i++) {
                    if (tmp.getInt(i + "") == -1) {
                        if (flag) {
                            stat.myLineup[3] = (i - 1);
                            flag = false;
                        } else {
                            stat.myLineup[4] = (i - 1);
                        }
                    } else {
                        stat.myLineup[tmp.getInt(i + "") - 1] = (i - 1);
                    }
                }

                tmp = data.getJSONObject("players1");
                JSONObject t1 = tmp.getJSONObject("1");
                stat.myPlayers[0][0] = t1.getInt("stamina");
                stat.myPlayers[0][1] = t1.getInt("size");
                stat.myPlayers[0][2] = t1.getInt("speed");
                t1 = tmp.getJSONObject("2");
                stat.myPlayers[1][0] = t1.getInt("stamina");
                stat.myPlayers[1][1] = t1.getInt("size");
                stat.myPlayers[1][2] = t1.getInt("speed");
                t1 = tmp.getJSONObject("3");
                stat.myPlayers[2][0] = t1.getInt("stamina");
                stat.myPlayers[2][1] = t1.getInt("size");
                stat.myPlayers[2][2] = t1.getInt("speed");
                t1 = tmp.getJSONObject("4");
                stat.myPlayers[3][0] = t1.getInt("stamina");
                stat.myPlayers[3][1] = t1.getInt("size");
                stat.myPlayers[3][2] = t1.getInt("speed");
                t1 = tmp.getJSONObject("5");
                stat.myPlayers[4][0] = t1.getInt("stamina");
                stat.myPlayers[4][1] = t1.getInt("size");
                stat.myPlayers[4][2] = t1.getInt("speed");
                tmp = data.getJSONObject("players2");
                t1 = tmp.getJSONObject("1");
                stat.oppPlayers[0][0] = t1.getInt("stamina");
                stat.oppPlayers[0][1] = t1.getInt("size");
                stat.oppPlayers[0][2] = t1.getInt("speed");
                t1 = tmp.getJSONObject("2");
                stat.oppPlayers[1][0] = t1.getInt("stamina");
                stat.oppPlayers[1][1] = t1.getInt("size");
                stat.oppPlayers[1][2] = t1.getInt("speed");
                t1 = tmp.getJSONObject("3");
                stat.oppPlayers[2][0] = t1.getInt("stamina");
                stat.oppPlayers[2][1] = t1.getInt("size");
                stat.oppPlayers[2][2] = t1.getInt("speed");
                t1 = tmp.getJSONObject("4");
                stat.oppPlayers[3][0] = t1.getInt("stamina");
                stat.oppPlayers[3][1] = t1.getInt("size");
                stat.oppPlayers[3][2] = t1.getInt("speed");
                t1 = tmp.getJSONObject("5");
                stat.oppPlayers[4][0] = t1.getInt("stamina");
                stat.oppPlayers[4][1] = t1.getInt("size");
                stat.oppPlayers[4][2] = t1.getInt("speed");
            } else {
                stat.reset();
                stat.myName = data.getString("username2");
                stat.oppName = data.getString("username1");
                stat.myWinRate = data.getDouble("winrate2");
                stat.oppWinRate = data.getDouble("winrate1");
                stat.myShirt = data.getInt("shirt2");
                stat.oppShirt = data.getInt("shirt1");
                tmp = data.getJSONObject("level2");
                stat.myLevel = tmp.getInt("lvl");
                stat.myXp = tmp.getInt("xp");
                tmp = data.getJSONObject("level1");
                stat.oppLevel = tmp.getInt("lvl");
                stat.oppXp = tmp.getInt("xp");
                stat.myFormation = data.getInt("formation2");
                stat.oppFormation = data.getInt("formation1");
                stat.oppAvatar = data.getInt("avatarId1");
                stat.myAvatar = data.getInt("avatarId2");

                tmp = data.getJSONObject("lineup2");
                boolean flag = true;
                for (int i = 1; i < 6; i++) {
                    if (tmp.getInt(i + "") == -1) {
                        if (flag) {
                            stat.myLineup[3] = (i - 1);
                            flag = false;
                        } else {
                            stat.myLineup[4] = (i - 1);
                        }
                    } else {
                        stat.myLineup[tmp.getInt(i + "") - 1] = (i - 1);
                    }
                }

                tmp = data.getJSONObject("players2");
                JSONObject t1 = tmp.getJSONObject("1");
                stat.myPlayers[0][0] = t1.getInt("stamina");
                stat.myPlayers[0][1] = t1.getInt("size");
                stat.myPlayers[0][2] = t1.getInt("speed");
                t1 = tmp.getJSONObject("2");
                stat.myPlayers[1][0] = t1.getInt("stamina");
                stat.myPlayers[1][1] = t1.getInt("size");
                stat.myPlayers[1][2] = t1.getInt("speed");
                t1 = tmp.getJSONObject("3");
                stat.myPlayers[2][0] = t1.getInt("stamina");
                stat.myPlayers[2][1] = t1.getInt("size");
                stat.myPlayers[2][2] = t1.getInt("speed");
                t1 = tmp.getJSONObject("4");
                stat.myPlayers[3][0] = t1.getInt("stamina");
                stat.myPlayers[3][1] = t1.getInt("size");
                stat.myPlayers[3][2] = t1.getInt("speed");
                t1 = tmp.getJSONObject("5");
                stat.myPlayers[4][0] = t1.getInt("stamina");
                stat.myPlayers[4][1] = t1.getInt("size");
                stat.myPlayers[4][2] = t1.getInt("speed");
                tmp = data.getJSONObject("players1");
                t1 = tmp.getJSONObject("1");
                stat.oppPlayers[0][0] = t1.getInt("stamina");
                stat.oppPlayers[0][1] = t1.getInt("size");
                stat.oppPlayers[0][2] = t1.getInt("speed");
                t1 = tmp.getJSONObject("2");
                stat.oppPlayers[1][0] = t1.getInt("stamina");
                stat.oppPlayers[1][1] = t1.getInt("size");
                stat.oppPlayers[1][2] = t1.getInt("speed");
                t1 = tmp.getJSONObject("3");
                stat.oppPlayers[2][0] = t1.getInt("stamina");
                stat.oppPlayers[2][1] = t1.getInt("size");
                stat.oppPlayers[2][2] = t1.getInt("speed");
                t1 = tmp.getJSONObject("4");
                stat.oppPlayers[3][0] = t1.getInt("stamina");
                stat.oppPlayers[3][1] = t1.getInt("size");
                stat.oppPlayers[3][2] = t1.getInt("speed");
                t1 = tmp.getJSONObject("5");
                stat.oppPlayers[4][0] = t1.getInt("stamina");
                stat.oppPlayers[4][1] = t1.getInt("size");
                stat.oppPlayers[4][2] = t1.getInt("speed");
            }
            if (stat.myShirt == stat.oppShirt) {
                if (stat.myShirt == 1) {
                    stat.oppShirt = 2;
                } else if (stat.myShirt == 2) {
                    stat.oppShirt = 1;
                } else {
                    stat.oppShirt = 2;
                }
            }

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
        ServerTool.getInstance().socket.off("reconnectData");
    }
}