package ir.eynakgroup.dribble2goal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
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
import ir.eynakgroup.dribble2goal.MatchStats;
import ir.eynakgroup.dribble2goal.MyGame;
import ir.eynakgroup.dribble2goal.Server.ServerTool;
import ir.eynakgroup.dribble2goal.Util.Random;
import ir.eynakgroup.dribble2goal.Util.Util;

/**
 * Created by kAvEh on 3/19/2016.
 */
public class FindMatchScreen implements Screen, InputProcessor {

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
    Label my_name;
    Label opp_name;
    Label my_level;
    Label opp_level;
    Image my_level_bg;
    Image opp_level_bg;
    Image opp_avatar_tmp;
    Image prize;
    Table coinTable;
    Image back;

    boolean avatar_flag = true;
    int avatar_tmp = 1;

    boolean isOppReady = false;
    private int stadium;
    private boolean isPenalty;

    MatchStats stat;

    public FindMatchScreen(int s, boolean isP) {
        mTweenManager = MyGame.mTweenManager;
        mMainBatch = new SpriteBatch();

        mSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        this.stadium = s;
        this.isPenalty = isP;

        mStage = new Stage(new FitViewport(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT), mMainBatch);
        mainTable = new Table();

        bg = new Image(Assets.getInstance().main_bg);
        bg.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        main_image = new Image(Assets.getInstance().findmatch_main);
        main_image.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        my_avatar = new Image(new Util().getAvatar(GamePrefs.getInstance().user.getAvatar()));
        my_avatar.setSize(Constants.HUD_SCREEN_WIDTH * .245f, Constants.HUD_SCREEN_HEIGHT * .454f);
        my_avatar.setPosition(Constants.HUD_SCREEN_WIDTH * .158f, Constants.HUD_SCREEN_HEIGHT * .266f);

        opp_avatar = new Image(new Util().getAvatar(5));
        opp_avatar.setSize(Constants.HUD_SCREEN_WIDTH * .245f, Constants.HUD_SCREEN_HEIGHT * .454f);
        opp_avatar.setPosition(Constants.HUD_SCREEN_WIDTH * .598f, Constants.HUD_SCREEN_HEIGHT * .266f);

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = mSkin.getFont("default-font");
        style.fontColor = Color.WHITE;

        my_name = new Label("", style);
        my_name.setWrap(true);
        my_name.setAlignment(Align.center);
        my_name.setBounds(Constants.HUD_SCREEN_WIDTH * .01f, Constants.HUD_SCREEN_HEIGHT * .4f,
                Constants.HUD_SCREEN_WIDTH * .18f, Constants.HUD_SCREEN_WIDTH * .1f);
        my_name.setColor(1, 1, 1, 0);

        opp_name = new Label("", style);
        opp_name.setWrap(true);
        opp_name.setAlignment(Align.center);
        opp_name.setBounds(Constants.HUD_SCREEN_WIDTH * .81f, Constants.HUD_SCREEN_HEIGHT * .4f,
                Constants.HUD_SCREEN_WIDTH * .18f, Constants.HUD_SCREEN_WIDTH * .1f);
        opp_name.setColor(1, 1, 1, 0);

        opp_avatar_tmp = new Image(new Util().getAvatar(6));
        opp_avatar_tmp.setSize(Constants.HUD_SCREEN_WIDTH * .245f, Constants.HUD_SCREEN_HEIGHT * .454f);
        opp_avatar_tmp.setPosition(Constants.HUD_SCREEN_WIDTH * .598f, Constants.HUD_SCREEN_HEIGHT * .266f);
        opp_avatar_tmp.setColor(1, 1, 1, 0);

        Label.LabelStyle style2 = new Label.LabelStyle();
        style2.font = mSkin.getFont("default-font");
        style2.fontColor = Color.DARK_GRAY;

        my_level = new Label("", style2);
        my_level.setWrap(true);
        my_level.setAlignment(Align.center);
        my_level.setBounds(Constants.HUD_SCREEN_WIDTH * .306f, Constants.HUD_SCREEN_HEIGHT * .361f,
                Constants.HUD_SCREEN_WIDTH * .18f, Constants.HUD_SCREEN_WIDTH * .1f);
        my_level.setColor(1, 1, 1, 0);

        opp_level = new Label("", style2);
        opp_level.setWrap(true);
        opp_level.setAlignment(Align.center);
        opp_level.setBounds(Constants.HUD_SCREEN_WIDTH * .515f, Constants.HUD_SCREEN_HEIGHT * .361f,
                Constants.HUD_SCREEN_WIDTH * .18f, Constants.HUD_SCREEN_WIDTH * .1f);
        opp_level.setColor(1, 1, 1, 0);

        my_level_bg = new Image(Assets.getInstance().level_left);
        my_level_bg.setSize(Constants.HUD_SCREEN_WIDTH * .18f, Constants.HUD_SCREEN_HEIGHT * .237f);
        my_level_bg.setPosition(Constants.HUD_SCREEN_WIDTH * .17f, Constants.HUD_SCREEN_HEIGHT * .37f);

        opp_level_bg = new Image(Assets.getInstance().level_right);
        opp_level_bg.setSize(Constants.HUD_SCREEN_WIDTH * .18f, Constants.HUD_SCREEN_HEIGHT * .237f);
        opp_level_bg.setPosition(Constants.HUD_SCREEN_WIDTH * .62f, Constants.HUD_SCREEN_HEIGHT * .37f);

        back = new Image(Assets.getInstance().icon_back);
        back.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
        back.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .04f);

        switch (stadium) {
            case 1:
                prize = new Image(Assets.getInstance().prize_1);
                break;
            case 2:
                prize = new Image(Assets.getInstance().prize_2);
                break;
            case 3:
                prize = new Image(Assets.getInstance().prize_3);
                break;
            case 4:
                prize = new Image(Assets.getInstance().prize_4);
                break;
            case 5:
                prize = new Image(Assets.getInstance().prize_5);
                break;
            default:
                prize = new Image(Assets.getInstance().prize_5);
                break;
        }
        prize.setSize(Constants.HUD_SCREEN_WIDTH * .27f, Constants.HUD_SCREEN_HEIGHT * .087f);
        prize.setPosition(Constants.HUD_SCREEN_WIDTH * .365f, Constants.HUD_SCREEN_HEIGHT * .04f);

        coinTable = new Table();

        loading();

        this.mainTable.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(mStage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        mainTable.addActor(bg);
        mainTable.addActor(coinTable);
        mainTable.addActor(opp_level_bg);
        mainTable.addActor(my_level_bg);
        mainTable.addActor(main_image);
        mainTable.addActor(prize);
        mainTable.addActor(my_name);
        mainTable.addActor(opp_name);
        mainTable.addActor(my_avatar);
        mainTable.addActor(opp_avatar_tmp);
        mainTable.addActor(opp_avatar);
        mainTable.addActor(my_level);
        mainTable.addActor(opp_level);
        mainTable.addActor(back);

        back.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                int tmp = stadium;
                if (isPenalty) {
                    tmp += 6;
                }
                ServerTool.getInstance().sendBackFind(tmp);
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

        this.mainTable.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        mStage.addActor(this.mainTable);

        ServerTool.getInstance().socket.on("match-found", onMatchFindListener);
    }

    private void loading() {
        if (isOppReady)
            return;

        if (avatar_flag) {
            Tween.to(opp_avatar, 3, .3f)
                    .target(0f).ease(TweenEquations.easeInBack)
                    .start(mTweenManager).delay(0.0F);
            Tween.to(opp_avatar_tmp, 3, .3f)
                    .target(1f).ease(TweenEquations.easeInBack)
                    .start(mTweenManager).delay(.05F)
                    .setCallback(new TweenCallback() {
                        public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                            avatar_tmp++;
                            if (avatar_tmp == 7)
                                avatar_tmp = 1;
                            opp_avatar.setDrawable(new SpriteDrawable(new Sprite(new Util().getAvatar(avatar_tmp))));
                            avatar_flag = !avatar_flag;
                            loading();
                        }
                    });
        } else {
            Tween.to(opp_avatar_tmp, 3, .3f)
                    .target(0f).ease(TweenEquations.easeInBack)
                    .start(mTweenManager).delay(0.0F);
            Tween.to(opp_avatar, 3, .3f)
                    .target(1f).ease(TweenEquations.easeInBack)
                    .start(mTweenManager).delay(.05F)
                    .setCallback(new TweenCallback() {
                        public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                            avatar_tmp++;
                            if (avatar_tmp == 7) {
                                avatar_tmp = 1;
                            }
                            opp_avatar_tmp.setDrawable(new SpriteDrawable(new Sprite(new Util().getAvatar(avatar_tmp))));
                            avatar_flag = !avatar_flag;
                            loading();
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
            } catch (Exception e) {
                if (setData((JSONObject) args[0])) {
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            gotoGame(stat);
                        }
                    });
                }
            }
        }
    };

    private void gotoGame(final MatchStats stat) {
        isOppReady = true;
        Assets.getInstance().main_theme.stop();
        if (GamePrefs.getInstance().isEffectOn() == 1) {
            Assets.getInstance().match_found.play();
        }
        if (GamePrefs.getInstance().isVibrateOn() == 1) {
            Gdx.input.vibrate(800);
        }
        Tween.to(opp_avatar, 3, .3f)
                .target(0f).ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(0.0F);
        Tween.to(opp_avatar_tmp, 3, .3f)
                .target(1f).ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(.05F)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                        opp_avatar.setDrawable(new SpriteDrawable(new Sprite(new Util().getAvatar(stat.oppAvatar))));
                    }
                });
        my_name.setText(stat.myName);
        opp_name.setText(stat.oppName);
        Tween.to(my_name, 5, 2f)
                .target(1f).ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(.05F);
        Tween.to(opp_name, 5, 2f)
                .target(1f).ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(.05F);
        Tween.to(my_level_bg, 7, 1f)
                .target(Constants.HUD_SCREEN_WIDTH * .28f).ease(TweenEquations.easeInBounce)
                .start(mTweenManager).delay(.05F)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                        my_level.setText(stat.myLevel + "");
                        my_level.setColor(1, 1, 1, 1);
                    }
                });
        Tween.to(opp_level_bg, 7, 1f)
                .target(Constants.HUD_SCREEN_WIDTH * .54f).ease(TweenEquations.easeInBounce)
                .start(mTweenManager).delay(.05F)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                        opp_level.setText(stat.oppLevel + "");
                        opp_level.setColor(1, 1, 1, 1);
                    }
                });
        final Image[] coins = new Image[20];
        for (int i = 0; i < 20; i++) {
            coins[i] = new Image(Assets.getInstance().coins_sample);
            coins[i].setSize(Constants.HUD_SCREEN_WIDTH * .052f, Constants.HUD_SCREEN_HEIGHT * .056f);
            coins[i].setPosition(Constants.HUD_SCREEN_WIDTH * .3f, Constants.HUD_SCREEN_HEIGHT * .46f);
            coins[i].setColor(1, 1, 1, .4f);
            coinTable.addActor(coins[i]);
            Tween.to(coins[i], 1, ((float) Math.random() / 8f) + .1f)
                    .target(Constants.HUD_SCREEN_WIDTH * .48f, Constants.HUD_SCREEN_HEIGHT * .17f)
                    .ease(TweenEquations.easeInCubic)
                    .start(mTweenManager).delay(.1F * i);
            Tween.to(coins[i], 3, ((float) Math.random() / 8f) + .1f)
                    .target(1)
                    .ease(TweenEquations.easeInCubic)
                    .start(mTweenManager).delay(.1F * i);
        }

        final Image[] coins2 = new Image[20];
        for (int i = 0; i < 19; i++) {
            coins2[i] = new Image(Assets.getInstance().coins_sample);
            coins2[i].setSize(Constants.HUD_SCREEN_WIDTH * .052f, Constants.HUD_SCREEN_HEIGHT * .056f);
            coins2[i].setPosition(Constants.HUD_SCREEN_WIDTH * .65f, Constants.HUD_SCREEN_HEIGHT * .46f);
            coins2[i].setColor(1, 1, 1, .4f);
            coinTable.addActor(coins2[i]);
            Tween.to(coins2[i], 1, ((float) Math.random() / 8f) + .1f)
                    .target(Constants.HUD_SCREEN_WIDTH * .48f, Constants.HUD_SCREEN_HEIGHT * .17f)
                    .ease(TweenEquations.easeInCubic)
                    .start(mTweenManager).delay(.1F * i);
            Tween.to(coins2[i], 3, ((float) Math.random() / 8f) + .1f)
                    .target(1)
                    .ease(TweenEquations.easeInCubic)
                    .start(mTweenManager).delay(.1F * i);
        }

        /*
           Last piece of coins animation
           in the end of animation loading game screen
        */
        coins2[19] = new Image(Assets.getInstance().coins_sample);
        coins2[19].setSize(Constants.HUD_SCREEN_WIDTH * .052f, Constants.HUD_SCREEN_HEIGHT * .056f);
        coins2[19].setPosition(Constants.HUD_SCREEN_WIDTH * .58f, Constants.HUD_SCREEN_HEIGHT * .52f);
        coins2[19].setColor(1, 1, 1, 0);
        mStage.addActor(coins2[19]);
        Tween.to(coins2[19], 1, .225f)
                .target(Constants.HUD_SCREEN_WIDTH * .48f, Constants.HUD_SCREEN_HEIGHT * .17f)
                .ease(TweenEquations.easeInCubic)
                .start(mTweenManager).delay(1.9F);
        Tween.to(coins2[19], 3, 1.5f)
                .target(1)
                .ease(TweenEquations.easeInCubic)
                .start(mTweenManager).delay(1.9F)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                        MyGame.mainInstance.createGame(stat);
                    }
                });
    }

    private boolean setData(JSONObject data) {
        JSONObject tmp;
        try {
            stat = new MatchStats();
            stat.reset();
            stat.roomNum = data.getInt("roomNum");
            if (stat.roomNum > 6) {
                stat.roomNum -= 6;
                stat.isPenaltymode = true;
            } else {
                stat.isPenaltymode = false;
            }
            stat.matchLevel = data.getInt("roomNum");
            stat.turn = data.getString("turn");
            if (data.getString("playerId1").matches(GamePrefs.getInstance().user.getId())) {
                stat.isMeFirst = true;
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
                stat.isMeFirst = false;
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
//            if (isPenalty) {
//                stat.roundNum = 22;
//                stat.GAME_STATE = Constants.GAME_PRE_PENALTY;
//            }
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
        Tween.to(this.mainTable, 5, .3f)
                .target(1f).ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(0.0F)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {

                    }
                });
        ServerTool.getInstance().findMatch(this.stadium, isPenalty);
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
        mMainCamera.viewportWidth = Constants.HUD_SCREEN_HEIGHT * aspectRatio;
        mMainCamera.update();

        mStage.getViewport().update(width, (int) (Constants.HUD_SCREEN_HEIGHT * aspectRatio), true);
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

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK) {
            MyGame.mainInstance.setMainScreen();
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