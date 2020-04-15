package ir.eynakgroup.dribble2goal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import ir.eynakgroup.dribble2goal.Assets;
import ir.eynakgroup.dribble2goal.Constants;
import ir.eynakgroup.dribble2goal.GamePrefs;
import ir.eynakgroup.dribble2goal.MyGame;
import ir.eynakgroup.dribble2goal.Server.ServerTool;
import ir.eynakgroup.dribble2goal.Util.Util;
import ir.eynakgroup.dribble2goal.render.textures.ProgressCircle;

/**
 * Created by kAvEh on 3/15/2016.
 */
public class ProfileScreen implements Screen, InputProcessor {

    TweenManager mTweenManager;
    private OrthographicCamera mMainCamera;
    SpriteBatch mMainBatch;
    Image bg;

    Image item_bg;
    Stage mStage;
    Table mainTable;
    Skin mSkin;

    Image profile_bg;
    Image profile_coin;
    Image profile_level;
    ProgressCircle sprite;
    TextureRegion region;
    PolygonSpriteBatch pbatch;

    Label coins_txt;
    Label level_txt;
    Label name;
    Image avatar;
    Image ach_on_header;
    Image ach_off_header;
    Image stat_on_header;
    Image stat_off_header;

    Table stat_container;
    Table achs_container;

    Image stat_gp;
    Image stat_gw;
    Image stat_wp;
    Image stat_coins;

    Image back;

    private int tmp_avatar;

    public ProfileScreen() {
        mTweenManager = MyGame.mTweenManager;
        mMainBatch = new SpriteBatch();

        mSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        mStage = new Stage(new FitViewport(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT), mMainBatch);
        mainTable = new Table();

        bg = new Image(Assets.getInstance().main_bg);
        bg.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        item_bg = new Image(Assets.getInstance().main_item_bg);
        item_bg.setSize(Constants.HUD_SCREEN_WIDTH * .28f, Constants.HUD_SCREEN_WIDTH * .28f);
        item_bg.setPosition(Constants.HUD_SCREEN_WIDTH * .17f, Constants.HUD_SCREEN_HEIGHT * .15f);

        profile_bg = new Image(Assets.getInstance().profile_background);
        profile_bg.setSize(Constants.HUD_SCREEN_WIDTH * .191f, Constants.HUD_SCREEN_HEIGHT * .34f);
        profile_bg.setPosition(Constants.HUD_SCREEN_WIDTH * .405f, Constants.HUD_SCREEN_HEIGHT * .63f);

        region = new TextureRegion(Assets.getInstance().progress_circle);
        pbatch = new PolygonSpriteBatch();
        sprite = new ProgressCircle(region, pbatch, new Vector2(Constants.HUD_SCREEN_WIDTH * .191f, Constants.HUD_SCREEN_HEIGHT * .34f), mStage);
        sprite.setSize(Constants.HUD_SCREEN_WIDTH * .191f, Constants.HUD_SCREEN_HEIGHT * .34f);
        sprite.setPosition(Constants.HUD_SCREEN_WIDTH * .405f, Constants.HUD_SCREEN_HEIGHT * .63f);

        tmp_avatar = GamePrefs.getInstance().user.getAvatar();
        avatar = new Image(new Util().getAvatar(GamePrefs.getInstance().user.getAvatar()));
        avatar.setSize(Constants.HUD_SCREEN_WIDTH * .191f, Constants.HUD_SCREEN_HEIGHT * .34f);
        avatar.setPosition(Constants.HUD_SCREEN_WIDTH * .405f, Constants.HUD_SCREEN_HEIGHT * .63f);

        profile_coin = new Image(Assets.getInstance().profile_page_coins);
        profile_coin.setSize(Constants.HUD_SCREEN_WIDTH * .1775f, Constants.HUD_SCREEN_HEIGHT * .2275f);
        profile_coin.setPosition(Constants.HUD_SCREEN_WIDTH * .49f, Constants.HUD_SCREEN_HEIGHT * .682f);

        profile_level = new Image(Assets.getInstance().level_right);
        profile_level.setSize(Constants.HUD_SCREEN_WIDTH * .1775f, Constants.HUD_SCREEN_HEIGHT * .2275f);
        profile_level.setPosition(Constants.HUD_SCREEN_WIDTH * .3425f, Constants.HUD_SCREEN_HEIGHT * .682f);

        this.mainTable.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(mStage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        mSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = mSkin.getFont("font21");
        style.fontColor = Color.DARK_GRAY;
        coins_txt = new Label(new Util().coinsStyle() + "", style);
        coins_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .578f, Constants.HUD_SCREEN_HEIGHT * .738f);

        level_txt = new Label(GamePrefs.getInstance().user.getLevel() + "", style);
        level_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .395f, Constants.HUD_SCREEN_HEIGHT * .738f);

        style = new Label.LabelStyle();
        style.font = mSkin.getFont("default-font");
        style.fontColor = Color.WHITE;
        name = new Label(GamePrefs.getInstance().user.getName() + "", style);
        name.setColor(Color.WHITE);
        name.setPosition(Constants.HUD_SCREEN_WIDTH * .5f - (name.getWidth() / 2), Constants.HUD_SCREEN_HEIGHT * .59f);

        stat_on_header = new Image(Assets.getInstance().profile_stat_on);
        stat_on_header.setSize(Constants.HUD_SCREEN_WIDTH * .245f, Constants.HUD_SCREEN_HEIGHT * .095f);
        stat_on_header.setPosition(Constants.HUD_SCREEN_WIDTH * .255f, Constants.HUD_SCREEN_HEIGHT * .47f);

        stat_off_header = new Image(Assets.getInstance().profile_stat_off);
        stat_off_header.setSize(Constants.HUD_SCREEN_WIDTH * .245f, Constants.HUD_SCREEN_HEIGHT * .095f);
        stat_off_header.setPosition(Constants.HUD_SCREEN_WIDTH * .255f, Constants.HUD_SCREEN_HEIGHT * .47f);
        stat_off_header.setVisible(false);
        stat_off_header.setColor(1f, 1f, 1f, 0f);

        ach_on_header = new Image(Assets.getInstance().profile_ach_on);
        ach_on_header.setSize(Constants.HUD_SCREEN_WIDTH * .245f, Constants.HUD_SCREEN_HEIGHT * .095f);
        ach_on_header.setPosition(Constants.HUD_SCREEN_WIDTH * .5f, Constants.HUD_SCREEN_HEIGHT * .47f);
        ach_on_header.setVisible(false);
        ach_on_header.setColor(1f, 1f, 1f, 0f);

        ach_off_header = new Image(Assets.getInstance().profile_ach_off);
        ach_off_header.setSize(Constants.HUD_SCREEN_WIDTH * .245f, Constants.HUD_SCREEN_HEIGHT * .095f);
        ach_off_header.setPosition(Constants.HUD_SCREEN_WIDTH * .5f, Constants.HUD_SCREEN_HEIGHT * .47f);

        stat_container = new Table();
        Table profile_stat = new Table();
        ScrollPane pane = new ScrollPane(profile_stat);
        pane.setForceScroll(false, true);
        stat_container.add(pane).width(Constants.HUD_SCREEN_WIDTH * .66f).height(Constants.HUD_SCREEN_HEIGHT * .44f);
        stat_container.row();
        stat_container.setBounds(0, 0, Constants.HUD_SCREEN_WIDTH * .66f, Constants.HUD_SCREEN_HEIGHT * .44f);

        stat_container.setPosition(Constants.HUD_SCREEN_WIDTH * .17f, Constants.HUD_SCREEN_HEIGHT * .01f);

        Table table4 = new Table();
        stat_coins = new Image(Assets.getInstance().profile_last5);
        stat_coins.setSize(Constants.HUD_SCREEN_WIDTH * .66f, Constants.HUD_SCREEN_HEIGHT * .127f);
        table4.addActor(stat_coins);
        Label t4 = new Label(GamePrefs.getInstance().user.getCoins_num() + "", style);
        t4.setPosition(Constants.HUD_SCREEN_WIDTH * .64f - t4.getWidth(),
                (Constants.HUD_SCREEN_HEIGHT * .127f - t4.getHeight()) / 2 - Constants.HUD_SCREEN_HEIGHT * .001f);
        table4.addActor(t4);
        profile_stat.add(table4).width(Constants.HUD_SCREEN_WIDTH * .66f).height(Constants.HUD_SCREEN_HEIGHT * .127f)
                .pad(Constants.HUD_SCREEN_HEIGHT * .01f, 0f, Constants.HUD_SCREEN_HEIGHT * .01f, 0f);
        profile_stat.row();

        Table table1 = new Table();
        stat_gp = new Image(Assets.getInstance().profile_gp);
        stat_gp.setSize(Constants.HUD_SCREEN_WIDTH * .66f, Constants.HUD_SCREEN_HEIGHT * .127f);
        table1.addActor(stat_gp);
        Label t1 = new Label(GamePrefs.getInstance().user.getGame_played() + "", style);
        t1.setPosition(Constants.HUD_SCREEN_WIDTH * .64f - t1.getWidth(),
                (Constants.HUD_SCREEN_HEIGHT * .127f - t1.getHeight()) / 2 - Constants.HUD_SCREEN_HEIGHT * .001f);
        table1.addActor(t1);
        profile_stat.add(table1).width(Constants.HUD_SCREEN_WIDTH * .66f).height(Constants.HUD_SCREEN_HEIGHT * .127f)
                .pad(Constants.HUD_SCREEN_HEIGHT * .02f, 0f, Constants.HUD_SCREEN_HEIGHT * .01f, 0f);
        profile_stat.row();

        Table table2 = new Table();
        stat_gw = new Image(Assets.getInstance().profile_gw);
        stat_gw.setSize(Constants.HUD_SCREEN_WIDTH * .66f, Constants.HUD_SCREEN_HEIGHT * .127f);
        table2.addActor(stat_gw);
        Label t2 = new Label(GamePrefs.getInstance().user.getGame_won() + "", style);
        t2.setPosition(Constants.HUD_SCREEN_WIDTH * .64f - t2.getWidth(),
                (Constants.HUD_SCREEN_HEIGHT * .127f - t2.getHeight()) / 2 - Constants.HUD_SCREEN_HEIGHT * .001f);
        table2.addActor(t2);
        profile_stat.add(table2).width(Constants.HUD_SCREEN_WIDTH * .66f).height(Constants.HUD_SCREEN_HEIGHT * .127f)
                .pad(Constants.HUD_SCREEN_HEIGHT * .01f, 0f, Constants.HUD_SCREEN_HEIGHT * .01f, 0f);
        profile_stat.row();

        Table table3 = new Table();
        stat_wp = new Image(Assets.getInstance().profile_wp);
        stat_wp.setSize(Constants.HUD_SCREEN_WIDTH * .66f, Constants.HUD_SCREEN_HEIGHT * .127f);
        table3.addActor(stat_wp);
        Label t3 = new Label(GamePrefs.getInstance().user.getWinRate() + "%", style);
        t3.setPosition(Constants.HUD_SCREEN_WIDTH * .64f - t3.getWidth(),
                (Constants.HUD_SCREEN_HEIGHT * .127f - t3.getHeight()) / 2 - Constants.HUD_SCREEN_HEIGHT * .001f);
        table3.addActor(t3);
        profile_stat.add(table3).width(Constants.HUD_SCREEN_WIDTH * .66f).height(Constants.HUD_SCREEN_HEIGHT * .127f)
                .pad(Constants.HUD_SCREEN_HEIGHT * .01f, 0f, Constants.HUD_SCREEN_HEIGHT * .01f, 0f);
        profile_stat.row();

        achs_container = new Table();
        Table profile_achs = new Table();
        ScrollPane pane2 = new ScrollPane(profile_achs);
        pane2.setForceScroll(false, true);
        achs_container.add(pane2).width(Constants.HUD_SCREEN_WIDTH * .66f).height(Constants.HUD_SCREEN_HEIGHT * .45f);
        achs_container.row();
        achs_container.setBounds(0, 0, Constants.HUD_SCREEN_WIDTH * .66f, Constants.HUD_SCREEN_HEIGHT * .45f);

        achs_container.setPosition(Constants.HUD_SCREEN_WIDTH * 1.17f, Constants.HUD_SCREEN_HEIGHT * .01f);

        achievementGenerator(profile_achs, Assets.getInstance().achiev_a1, GamePrefs.getInstance().user.getAchieve_win() > 0, false);
        achievementGenerator(profile_achs, Assets.getInstance().achiev_a2, GamePrefs.getInstance().user.getAchieve_win() > 1, true);
        achievementGenerator(profile_achs, Assets.getInstance().achiev_a3, GamePrefs.getInstance().user.getAchieve_win() > 2, false);
        achievementGenerator(profile_achs, Assets.getInstance().achiev_a4, GamePrefs.getInstance().user.getAchieve_win() > 3, true);
        achievementGenerator(profile_achs, Assets.getInstance().achiev_a5, GamePrefs.getInstance().user.getAchieve_win() > 4, false);

        achievementGenerator(profile_achs, Assets.getInstance().achiev_b1, GamePrefs.getInstance().user.getAchieve_goal() > 0, true);
        achievementGenerator(profile_achs, Assets.getInstance().achiev_b2, GamePrefs.getInstance().user.getAchieve_goal() > 1, false);
        achievementGenerator(profile_achs, Assets.getInstance().achiev_b3, GamePrefs.getInstance().user.getAchieve_goal() > 2, true);
        achievementGenerator(profile_achs, Assets.getInstance().achiev_b4, GamePrefs.getInstance().user.getAchieve_goal() > 3, false);
        achievementGenerator(profile_achs, Assets.getInstance().achiev_b5, GamePrefs.getInstance().user.getAchieve_goal() > 4, true);
        achievementGenerator(profile_achs, Assets.getInstance().achiev_b6, GamePrefs.getInstance().user.getAchieve_goal() > 5, false);

        achievementGenerator(profile_achs, Assets.getInstance().achiev_c1, GamePrefs.getInstance().user.getAchieve_cleanSheet() > 0, true);
        achievementGenerator(profile_achs, Assets.getInstance().achiev_c2, GamePrefs.getInstance().user.getAchieve_cleanSheet() > 1, false);
        achievementGenerator(profile_achs, Assets.getInstance().achiev_c3, GamePrefs.getInstance().user.getAchieve_cleanSheet() > 2, true);
        achievementGenerator(profile_achs, Assets.getInstance().achiev_c4, GamePrefs.getInstance().user.getAchieve_cleanSheet() > 3, false);
        achievementGenerator(profile_achs, Assets.getInstance().achiev_c5, GamePrefs.getInstance().user.getAchieve_cleanSheet() > 4, true);
        achievementGenerator(profile_achs, Assets.getInstance().achiev_c6, GamePrefs.getInstance().user.getAchieve_cleanSheet() > 5, false);

        achievementGenerator(profile_achs, Assets.getInstance().achiev_d1, GamePrefs.getInstance().user.getAchieve_winInaRow() > 0, true);
        achievementGenerator(profile_achs, Assets.getInstance().achiev_d2, GamePrefs.getInstance().user.getAchieve_winInaRow() > 1, false);
        achievementGenerator(profile_achs, Assets.getInstance().achiev_d3, GamePrefs.getInstance().user.getAchieve_winInaRow() > 2, true);
        achievementGenerator(profile_achs, Assets.getInstance().achiev_d4, GamePrefs.getInstance().user.getAchieve_winInaRow() > 3, false);

        back = new Image(Assets.getInstance().icon_back);
        back.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
        back.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .04f);

        mainTable.addActor(bg);
        mainTable.addActor(profile_coin);
        mainTable.addActor(profile_level);
        mainTable.addActor(profile_bg);
        mainTable.addActor(sprite);
        mainTable.addActor(avatar);
        mainTable.addActor(coins_txt);
        mainTable.addActor(level_txt);
        mainTable.addActor(name);
        mainTable.addActor(stat_on_header);
        mainTable.addActor(stat_off_header);
        mainTable.addActor(ach_on_header);
        mainTable.addActor(ach_off_header);
        mainTable.addActor(stat_container);
        mainTable.addActor(achs_container);
        mainTable.addActor(back);

        back.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                if (tmp_avatar != GamePrefs.getInstance().user.getAvatar()) {
                    ServerTool.getInstance().editAvatar(tmp_avatar);
                    GamePrefs.getInstance().user.setAvatar(tmp_avatar);
                }
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

        profile_coin.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                MyGame.mainInstance.setShopScreen();
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        avatar.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                //TODO change avatar
                tmp_avatar += 1;
                if (tmp_avatar > 6)
                    tmp_avatar = 1;
                avatar.setDrawable(new TextureRegionDrawable(new TextureRegion(new Util().getAvatar(tmp_avatar))));
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        stat_off_header.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                Tween.to(stat_on_header, 3, .2f)
                        .target(1f).ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F)
                        .setCallback(new TweenCallback() {
                            public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                                stat_on_header.setVisible(true);
                            }
                        });
                Tween.to(stat_off_header, 3, .2f)
                        .target(0f).ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F)
                        .setCallback(new TweenCallback() {
                            public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                                stat_off_header.setVisible(false);
                            }
                        });
                Tween.to(ach_on_header, 3, .2f)
                        .target(0f).ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F)
                        .setCallback(new TweenCallback() {
                            public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                                ach_on_header.setVisible(false);
                            }
                        });
                Tween.to(ach_off_header, 3, .2f)
                        .target(1f).ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F)
                        .setCallback(new TweenCallback() {
                            public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                                ach_off_header.setVisible(true);
                            }
                        });

                Tween.to(stat_container, 1, .5f)
                        .target(Constants.HUD_SCREEN_WIDTH * .17f, Constants.HUD_SCREEN_HEIGHT * .01f)
                        .ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F).delay(.1f);
                Tween.to(achs_container, 1, .5f)
                        .target(Constants.HUD_SCREEN_WIDTH * 1.17f, Constants.HUD_SCREEN_HEIGHT * .01f)
                        .ease(TweenEquations.easeOutBack)
                        .start(mTweenManager).delay(0.0F);
            }
        });

        ach_off_header.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                Tween.to(stat_on_header, 3, .2f)
                        .target(0f).ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F)
                        .setCallback(new TweenCallback() {
                            public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                                stat_on_header.setVisible(false);
                            }
                        });
                Tween.to(stat_off_header, 3, .2f)
                        .target(1f).ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F)
                        .setCallback(new TweenCallback() {
                            public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                                stat_off_header.setVisible(true);
                            }
                        });
                Tween.to(ach_on_header, 3, .2f)
                        .target(1f).ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F)
                        .setCallback(new TweenCallback() {
                            public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                                ach_on_header.setVisible(true);
                            }
                        });
                Tween.to(ach_off_header, 3, .2f)
                        .target(0f).ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F)
                        .setCallback(new TweenCallback() {
                            public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                                ach_off_header.setVisible(false);
                            }
                        });

                Tween.to(stat_container, 1, .5f)
                        .target(Constants.HUD_SCREEN_WIDTH * 1.17f, Constants.HUD_SCREEN_HEIGHT * .01f)
                        .ease(TweenEquations.easeOutBack)
                        .start(mTweenManager).delay(0.0F);
                Tween.to(achs_container, 1, .2f)
                        .target(Constants.HUD_SCREEN_WIDTH * .17f, Constants.HUD_SCREEN_HEIGHT * .01f)
                        .ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F).delay(.1f);
            }
        });

        this.mainTable.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        mStage.addActor(this.mainTable);
    }

    public void achievementGenerator(Table container, Texture image, boolean flag, boolean isOdd) {
        Table table = new Table();
        Image ach_image = new Image(image);
        ach_image.setSize(Constants.HUD_SCREEN_WIDTH * .32f, Constants.HUD_SCREEN_HEIGHT * .127f);
        table.addActor(ach_image);
        if (!flag) {
            ach_image.setColor(1f, 1f, 1f, .5f);
        }
        if (isOdd) {
            container.add(table).width(Constants.HUD_SCREEN_WIDTH * .32f).height(Constants.HUD_SCREEN_HEIGHT * .127f)
                    .pad(Constants.HUD_SCREEN_HEIGHT * .02f, Constants.HUD_SCREEN_WIDTH * .03f, 0f, 0f);
            container.row();
        } else {
            container.add(table).width(Constants.HUD_SCREEN_WIDTH * .3f).height(Constants.HUD_SCREEN_HEIGHT * .127f)
                    .pad(Constants.HUD_SCREEN_HEIGHT * .02f, 0f, 0f, 0f);
        }
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

        int percent = (int) ((float) GamePrefs.getInstance().user.getXp() / ((float) GamePrefs.getInstance().user.getLevel() * 1000f) * 100f);
        Tween.to(sprite, 1, .8f)
                .target(percent).ease(TweenEquations.easeInExpo)
                .start(mTweenManager).delay(0.3F)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {

                    }
                });

        profile_coin.setX(Constants.HUD_SCREEN_WIDTH * .415f);
//        coins_txt.setText("0");
//        coins_txt.setColor(1f, 1f, 1f, 0f);
        Tween.to(profile_coin, 7, .3f)
                .target(Constants.HUD_SCREEN_WIDTH * .48f)
                .ease(TweenEquations.easeInExpo)
                .start(mTweenManager).delay(0.0F)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
//                        coins_txt.setColor(1f, 1f, 1f, 1f);
//                        Tween.to(coins_txt, 1, 1.1f)
//                                .target(GamePrefs.getInstance().coins_num).ease(TweenEquations.easeOutQuad)
//                                .start(mTweenManager).delay(0.0F);
                    }
                });

        profile_level.setX(Constants.HUD_SCREEN_WIDTH * .415f);
//        coins_txt.setText("0");
//        coins_txt.setColor(1f, 1f, 1f, 0f);
        Tween.to(profile_level, 7, .3f)
                .target(Constants.HUD_SCREEN_WIDTH * .3425f)
                .ease(TweenEquations.easeInExpo)
                .start(mTweenManager).delay(0.0F)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
//                        coins_txt.setColor(1f, 1f, 1f, 1f);
//                        Tween.to(coins_txt, 1, 1.1f)
//                                .target(GamePrefs.getInstance().coins_num).ease(TweenEquations.easeOutQuad)
//                                .start(mTweenManager).delay(0.0F);
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
//        mMainBatch.draw(background, -Constants.HUD_SCREEN_WIDTH / 2, -Constants.HUD_SCREEN_HEIGHT / 2,
//                Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);
//
//        mMainBatch.end();

        // Restore the stage's viewport.
        mStage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        mStage.act(delta);
        mStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        float aspectRatio = (float) width / height;
        mMainCamera.viewportWidth = Constants.HUD_SCREEN_HEIGHT * aspectRatio;
        mMainCamera.update();

//        mStage.getViewport().update(width, (int) (Constants.HUD_SCREEN_HEIGHT * aspectRatio), true);
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
            if (tmp_avatar != GamePrefs.getInstance().user.getAvatar()) {
                ServerTool.getInstance().editAvatar(tmp_avatar);
                GamePrefs.getInstance().user.setAvatar(tmp_avatar);
            }
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

