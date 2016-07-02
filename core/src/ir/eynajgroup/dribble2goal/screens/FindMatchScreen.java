package ir.eynajgroup.dribble2goal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
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
import ir.eynajgroup.dribble2goal.MatchStats;
import ir.eynajgroup.dribble2goal.MyGame;
import ir.eynajgroup.dribble2goal.Server.ServerTool;
import ir.eynajgroup.dribble2goal.Util.Util;

/**
 * Created by kAvEh on 3/19/2016.
 */
public class FindMatchScreen implements Screen {

    TweenManager mTweenManager;
    private OrthographicCamera mMainCamera;
    SpriteBatch mMainBatch;
    Image bg;

    Stage mStage;
    Table mainTable;
    Skin mSkin;

    Image main_image;
    Image my_avatar;
    Image opp_avatar;

    int counter = 0;
    int avatar_tmp = 1;

    boolean isOppReady = false;
    private int stadium;

    MatchStats stat;

    public FindMatchScreen(int s) {
        mTweenManager = MyGame.mTweenManager;
        mMainBatch = new SpriteBatch();

        mSkin = new Skin(Gdx.files.internal("data/uiskin.json"));

        this.stadium = s;

        mStage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), mMainBatch);
        mainTable = new Table();

        bg = new Image(Assets.getInstance().main_bg);
        bg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        main_image = new Image(Assets.getInstance().findmatch_main);
        main_image.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        my_avatar = new Image(new Util().getAvatar(GamePrefs.getInstance().avatar));
        my_avatar.setSize(Gdx.graphics.getWidth() * .245f, Gdx.graphics.getHeight() * .454f);
        my_avatar.setPosition(Gdx.graphics.getWidth() * .158f, Gdx.graphics.getHeight() * .266f);

        opp_avatar = new Image(new Util().getAvatar(5));
        opp_avatar.setSize(Gdx.graphics.getWidth() * .245f, Gdx.graphics.getHeight() * .454f);
        opp_avatar.setPosition(Gdx.graphics.getWidth() * .598f, Gdx.graphics.getHeight() * .266f);

        loading();

        this.mainTable.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(mStage);
        mSkin = new Skin(Gdx.files.internal("data/uiskin.json"));

        mSkin.getFont("english").getData().scale(-.2f);

        mainTable.addActor(bg);
        mainTable.addActor(main_image);
        mainTable.addActor(my_avatar);
        mainTable.addActor(opp_avatar);

//        back.addListener(new ActorGestureListener() {
//            public void tap(InputEvent event, float x, float y, int count, int button) {
//                MyGame.mainInstance.setMainScreen();
//            }
//
//            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                back.setSize(Gdx.graphics.getWidth() * .133f * .8f, Gdx.graphics.getHeight() * .186f * .8f);
//                back.setPosition(Gdx.graphics.getWidth() * .863f, Gdx.graphics.getHeight() * .06f);
//            }
//
//            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                back.setSize(Gdx.graphics.getWidth() * .133f, Gdx.graphics.getHeight() * .186f);
//                back.setPosition(Gdx.graphics.getWidth() * .85f, Gdx.graphics.getHeight() * .04f);
//            }
//        });

//        Timer.schedule(new Timer.Task() {
//            public void run() {
//                counter++;
//                System.out.println(counter + "========");
//                if (counter > 10) {
//                    isOppReady = true;
//                }
//            }
//        }, 0.0F, .95F, 11);

        this.mainTable.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(mStage);

        mStage.addActor(this.mainTable);

        ServerTool.getInstance().socket.on("match-found", onMatchFindListener);
    }

    public void loading() {
        if (!isOppReady) {
            Tween.to(opp_avatar, 3, .2f)
                    .target(0f).ease(TweenEquations.easeInBack)
                    .start(mTweenManager).delay(0.0F)
                    .setCallback(new TweenCallback() {
                        public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                            Tween.to(opp_avatar, 3, .2f)
                                    .target(1f).ease(TweenEquations.easeInBack)
                                    .start(mTweenManager).delay(0.0F)
                                    .setCallback(new TweenCallback() {
                                        public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                                            avatar_tmp++;
                                            if (avatar_tmp == 7)
                                                avatar_tmp = 1;
                                            opp_avatar.setDrawable(new SpriteDrawable(new Sprite(new Util().getAvatar(avatar_tmp))));
                                            loading();
                                        }
                                    });
                        }
                    });
        }
    }

    private Emitter.Listener onMatchFindListener = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            System.out.println(args[0]);
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
                            MyGame.mainInstance.createGame(stat);
                        }
                    });
                }
            }
        }
    };

    public boolean setData(JSONObject data) {
        JSONObject tmp;
        try {
            stat = new MatchStats();
            stat.roomNum = data.getInt("roomNum");
            if (data.getInt("playerId1") == GamePrefs.getInstance().playerId) {
                stat.reset(this.stadium, true);
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

                tmp = data.getJSONObject("lineup1");
                boolean flag = true;
                for (int i = 1; i < 6; i++) {
                    if (tmp.getInt(i + "") == -1) {
                        if (flag) {
                            stat.myLineup[3] = (i-1);
                            flag = false;
                        } else {
                            stat.myLineup[4] = (i-1);
                        }
                    } else {
                        stat.myLineup[tmp.getInt(i + "") - 1] = (i-1);
                    }
                }

//                tmp = data.getJSONObject("lineup2");
//                flag = true;
//                for (int i = 1; i < 6; i++) {
//                    if (tmp.getInt(i + "") == -1) {
//                        if (flag) {
//                            stat.oppLineup[3] = (i-1);
//                            flag = false;
//                        } else {
//                            stat.oppLineup[4] = (i-1);
//                        }
//                    } else {
//                        stat.oppLineup[tmp.getInt(i + "") - 1] = (i-1);
//                    }
//                }

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
                stat.reset(this.stadium, false);
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

                tmp = data.getJSONObject("lineup2");
                boolean flag = true;
                for (int i = 1; i < 6; i++) {
                    if (tmp.getInt(i + "") == -1) {
                        if (flag) {
                            stat.myLineup[3] = (i-1);
                            flag = false;
                        } else {
                            stat.myLineup[4] = (i-1);
                        }
                    } else {
                        stat.myLineup[tmp.getInt(i + "") - 1] = (i-1);
                    }
                }

//                tmp = data.getJSONObject("lineup1");
//                flag = true;
//                for (int i = 1; i < 6; i++) {
//                    if (tmp.getInt(i + "") == -1) {
//                        if (flag) {
//                            stat.oppLineup[3] = (i-1);
//                            flag = false;
//                        } else {
//                            stat.oppLineup[4] = (i-1);
//                        }
//                    } else {
//                        stat.oppLineup[tmp.getInt(i + "") - 1] = (i-1);
//                    }
//                }

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
        ServerTool.getInstance().findMatch(this.stadium);
    }

    @Override
    public void render(float delta) {
        mStage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        mMainBatch.setTransformMatrix(mMainCamera.view);
        mMainBatch.setProjectionMatrix(mMainCamera.projection);

        mStage.act(delta);
        mStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        float aspectRatio = (float) width / height;
        mMainCamera.viewportWidth = Constants.SCREEN_HEIGHT * aspectRatio;
        mMainCamera.update();

        mStage.getViewport().update(width, (int) (Constants.SCREEN_HEIGHT * aspectRatio), true);
//        mStage.getViewport().update(width, height, true);
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