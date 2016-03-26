package ir.eynajgroup.dribble2goal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.utils.viewport.FitViewport;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import ir.eynajgroup.dribble2goal.Assets;
import ir.eynajgroup.dribble2goal.Constants;
import ir.eynajgroup.dribble2goal.GamePrefs;
import ir.eynajgroup.dribble2goal.MyGame;
import ir.eynajgroup.dribble2goal.Util.Util;
import ir.eynajgroup.dribble2goal.render.textures.ProgressCircle;

/**
 * Created by kAvEh on 3/15/2016.
 */
public class ProfileScreen implements Screen {

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
    Image stat_last5;

    Image back;

    boolean menuSwitch = true;

    public ProfileScreen() {
        mTweenManager = MyGame.mTweenManager;
        mMainBatch = new SpriteBatch();

        mSkin = new Skin(Gdx.files.internal("data/uiskin.json"));

        mStage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), mMainBatch);
        mainTable = new Table();

        bg = new Image(Assets.getInstance().main_bg);
        bg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        item_bg = new Image(Assets.getInstance().main_item_bg);
        item_bg.setSize(Gdx.graphics.getWidth() * .28f, Gdx.graphics.getWidth() * .28f);
        item_bg.setPosition(Gdx.graphics.getWidth() * .17f, Gdx.graphics.getHeight() * .15f);

        profile_bg = new Image(Assets.getInstance().profile_background);
        profile_bg.setSize(Gdx.graphics.getWidth() * .191f, Gdx.graphics.getHeight() * .34f);
        profile_bg.setPosition(Gdx.graphics.getWidth() * .405f, Gdx.graphics.getHeight() * .63f);

        region = new TextureRegion(Assets.getInstance().progress_circle);
        pbatch = new PolygonSpriteBatch();
        sprite = new ProgressCircle(region, pbatch, new Vector2(Gdx.graphics.getWidth() * .191f, Gdx.graphics.getHeight() * .34f));
        sprite.setSize(Gdx.graphics.getWidth() * .191f, Gdx.graphics.getHeight() * .34f);
        sprite.setPosition(Gdx.graphics.getWidth() * .405f, Gdx.graphics.getHeight() * .63f);

        avatar = new Image(new Util().getAvatar(GamePrefs.getInstance().avatar));
        avatar.setSize(Gdx.graphics.getWidth() * .191f, Gdx.graphics.getHeight() * .34f);
        avatar.setPosition(Gdx.graphics.getWidth() * .405f, Gdx.graphics.getHeight() * .63f);

        profile_coin = new Image(Assets.getInstance().profile_page_coins);
        profile_coin.setSize(Gdx.graphics.getWidth() * .1775f, Gdx.graphics.getHeight() * .2275f);
        profile_coin.setPosition(Gdx.graphics.getWidth() * .48f, Gdx.graphics.getHeight() * .682f);

        profile_level = new Image(Assets.getInstance().level_right);
        profile_level.setSize(Gdx.graphics.getWidth() * .1775f, Gdx.graphics.getHeight() * .2275f);
        profile_level.setPosition(Gdx.graphics.getWidth() * .3425f, Gdx.graphics.getHeight() * .682f);

        this.mainTable.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(mStage);
        mSkin = new Skin(Gdx.files.internal("data/uiskin.json"));

        mSkin.getFont("english").getData().scale(-.2f);
        coins_txt = new Label(GamePrefs.getInstance().coins_num + "", mSkin);
        coins_txt.setPosition(Gdx.graphics.getWidth() * .575f, Gdx.graphics.getHeight() * .738f);

        level_txt = new Label(GamePrefs.getInstance().level + "", mSkin);
        level_txt.setPosition(Gdx.graphics.getWidth() * .395f, Gdx.graphics.getHeight() * .738f);

//        mSkin.getFont("english").getData().scale(1f);
        name = new Label(GamePrefs.getInstance().name + "", mSkin);
        name.setPosition(Gdx.graphics.getWidth() * .5f - (name.getWidth() / 2), Gdx.graphics.getHeight() * .6f);

        stat_on_header = new Image(Assets.getInstance().profile_stat_on);
        stat_on_header.setSize(Gdx.graphics.getWidth() * .245f, Gdx.graphics.getHeight() * .095f);
        stat_on_header.setPosition(Gdx.graphics.getWidth() * .255f, Gdx.graphics.getHeight() * .5f);

        stat_off_header = new Image(Assets.getInstance().profile_stat_off);
        stat_off_header.setSize(Gdx.graphics.getWidth() * .245f, Gdx.graphics.getHeight() * .095f);
        stat_off_header.setPosition(Gdx.graphics.getWidth() * .255f, Gdx.graphics.getHeight() * .5f);
        stat_off_header.setVisible(false);
        stat_off_header.setColor(1f, 1f, 1f, 0f);

        ach_on_header = new Image(Assets.getInstance().profile_ach_on);
        ach_on_header.setSize(Gdx.graphics.getWidth() * .245f, Gdx.graphics.getHeight() * .095f);
        ach_on_header.setPosition(Gdx.graphics.getWidth() * .5f, Gdx.graphics.getHeight() * .5f);
        ach_on_header.setVisible(false);
        ach_on_header.setColor(1f, 1f, 1f, 0f);

        ach_off_header = new Image(Assets.getInstance().profile_ach_off);
        ach_off_header.setSize(Gdx.graphics.getWidth() * .245f, Gdx.graphics.getHeight() * .095f);
        ach_off_header.setPosition(Gdx.graphics.getWidth() * .5f, Gdx.graphics.getHeight() * .5f);

        stat_container = new Table();
        Table profile_stat = new Table();
        ScrollPane pane = new ScrollPane(profile_stat);
        pane.setForceScroll(false, true);
        stat_container.add(pane).width(Gdx.graphics.getWidth() * .66f).height(Gdx.graphics.getHeight() * .49f);
        stat_container.row();
        stat_container.setBounds(0, 0, Gdx.graphics.getWidth() * .66f, Gdx.graphics.getHeight() * .49f);

        stat_container.setPosition(Gdx.graphics.getWidth() * .17f, Gdx.graphics.getHeight() * .01f);

        Table table1 = new Table();
        stat_gp = new Image(Assets.getInstance().profile_gp);
        stat_gp.setSize(Gdx.graphics.getWidth() * .66f, Gdx.graphics.getHeight() * .127f);
        table1.addActor(stat_gp);
        Label t1 = new Label(GamePrefs.getInstance().game_played + "", mSkin);
        t1.setPosition(Gdx.graphics.getWidth() * .64f - t1.getWidth(),
                (Gdx.graphics.getHeight() * .127f - t1.getHeight()) / 2 + Gdx.graphics.getHeight() * .01f);
        table1.addActor(t1);
        profile_stat.add(table1).width(Gdx.graphics.getWidth() * .66f).height(Gdx.graphics.getHeight() * .127f)
                .pad(Gdx.graphics.getHeight() * .02f, 0f, Gdx.graphics.getHeight() * .01f, 0f);
        profile_stat.row();

        Table table2 = new Table();
        stat_gw = new Image(Assets.getInstance().profile_gw);
        stat_gw.setSize(Gdx.graphics.getWidth() * .66f, Gdx.graphics.getHeight() * .127f);
        table2.addActor(stat_gw);
        Label t2 = new Label(GamePrefs.getInstance().game_won + "", mSkin);
        t2.setPosition(Gdx.graphics.getWidth() * .64f - t2.getWidth(),
                (Gdx.graphics.getHeight() * .127f - t2.getHeight()) / 2 + Gdx.graphics.getHeight() * .01f);
        table2.addActor(t2);
        profile_stat.add(table2).width(Gdx.graphics.getWidth() * .66f).height(Gdx.graphics.getHeight() * .127f)
                .pad(Gdx.graphics.getHeight() * .01f, 0f, Gdx.graphics.getHeight() * .01f, 0f);
        profile_stat.row();

        Table table3 = new Table();
        stat_wp = new Image(Assets.getInstance().profile_wp);
        stat_wp.setSize(Gdx.graphics.getWidth() * .66f, Gdx.graphics.getHeight() * .127f);
        table3.addActor(stat_wp);
        Label t3 = new Label(GamePrefs.getInstance().win_percent + "", mSkin);
        t3.setPosition(Gdx.graphics.getWidth() * .64f - t3.getWidth(),
                (Gdx.graphics.getHeight() * .127f - t3.getHeight()) / 2 + Gdx.graphics.getHeight() * .01f);
        table3.addActor(t3);
        profile_stat.add(table3).width(Gdx.graphics.getWidth() * .66f).height(Gdx.graphics.getHeight() * .127f)
                .pad(Gdx.graphics.getHeight() * .01f, 0f, Gdx.graphics.getHeight() * .01f, 0f);
        profile_stat.row();

        Table table4 = new Table();
        stat_last5 = new Image(Assets.getInstance().profile_last5);
        stat_last5.setSize(Gdx.graphics.getWidth() * .66f, Gdx.graphics.getHeight() * .127f);
        table4.addActor(stat_last5);
        Label t4 = new Label(GamePrefs.getInstance().last5 + "", mSkin);
        t4.setPosition(Gdx.graphics.getWidth() * .64f - t4.getWidth(),
                (Gdx.graphics.getHeight() * .127f - t4.getHeight()) / 2 + Gdx.graphics.getHeight() * .01f);
        table4.addActor(t4);
        profile_stat.add(table4).width(Gdx.graphics.getWidth() * .66f).height(Gdx.graphics.getHeight() * .127f)
                .pad(Gdx.graphics.getHeight() * .01f, 0f, Gdx.graphics.getHeight() * .01f, 0f);
        profile_stat.row();

        achs_container = new Table();
        Table profile_achs = new Table();
        ScrollPane pane2 = new ScrollPane(profile_achs);
        pane2.setForceScroll(false, true);
        achs_container.add(pane2).width(Gdx.graphics.getWidth() * .66f).height(Gdx.graphics.getHeight() * .49f);
        achs_container.row();
        achs_container.setBounds(0, 0, Gdx.graphics.getWidth() * .66f, Gdx.graphics.getHeight() * .49f);

        achs_container.setPosition(Gdx.graphics.getWidth() * .17f, Gdx.graphics.getHeight() * .01f);
        achs_container.setVisible(false);

        achievementGenerator(profile_achs, Assets.getInstance().achiev_a1, GamePrefs.getInstance().achievements[0], false);
        achievementGenerator(profile_achs, Assets.getInstance().achiev_a2, GamePrefs.getInstance().achievements[1], true);
        achievementGenerator(profile_achs, Assets.getInstance().achiev_a3, GamePrefs.getInstance().achievements[2], false);
        achievementGenerator(profile_achs, Assets.getInstance().achiev_a4, GamePrefs.getInstance().achievements[3], true);
        achievementGenerator(profile_achs, Assets.getInstance().achiev_a5, GamePrefs.getInstance().achievements[4], false);

        achievementGenerator(profile_achs, Assets.getInstance().achiev_b1, GamePrefs.getInstance().achievements[5], true);
        achievementGenerator(profile_achs, Assets.getInstance().achiev_b2, GamePrefs.getInstance().achievements[6], false);
        achievementGenerator(profile_achs, Assets.getInstance().achiev_b3, GamePrefs.getInstance().achievements[7], true);
        achievementGenerator(profile_achs, Assets.getInstance().achiev_b4, GamePrefs.getInstance().achievements[8], false);
        achievementGenerator(profile_achs, Assets.getInstance().achiev_b5, GamePrefs.getInstance().achievements[9], true);
        achievementGenerator(profile_achs, Assets.getInstance().achiev_b6, GamePrefs.getInstance().achievements[10], false);

        achievementGenerator(profile_achs, Assets.getInstance().achiev_c1, GamePrefs.getInstance().achievements[11], true);
        achievementGenerator(profile_achs, Assets.getInstance().achiev_c2, GamePrefs.getInstance().achievements[12], false);
        achievementGenerator(profile_achs, Assets.getInstance().achiev_c3, GamePrefs.getInstance().achievements[13], true);
        achievementGenerator(profile_achs, Assets.getInstance().achiev_c4, GamePrefs.getInstance().achievements[14], false);
        achievementGenerator(profile_achs, Assets.getInstance().achiev_c5, GamePrefs.getInstance().achievements[15], true);
        achievementGenerator(profile_achs, Assets.getInstance().achiev_c6, GamePrefs.getInstance().achievements[16], false);

        achievementGenerator(profile_achs, Assets.getInstance().achiev_d1, GamePrefs.getInstance().achievements[17], true);
        achievementGenerator(profile_achs, Assets.getInstance().achiev_d2, GamePrefs.getInstance().achievements[18], false);
        achievementGenerator(profile_achs, Assets.getInstance().achiev_d3, GamePrefs.getInstance().achievements[19], true);
        achievementGenerator(profile_achs, Assets.getInstance().achiev_d4, GamePrefs.getInstance().achievements[20], false);

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
            public void tap(InputEvent paramAnonymousInputEvent, float paramAnonymousFloat1, float paramAnonymousFloat2, int paramAnonymousInt1, int paramAnonymousInt2) {
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

        stat_off_header.addListener(new ActorGestureListener() {
            public void tap(InputEvent paramAnonymousInputEvent, float paramAnonymousFloat1, float paramAnonymousFloat2, int paramAnonymousInt1, int paramAnonymousInt2) {
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

                stat_container.setVisible(true);
                achs_container.setVisible(false);
            }
        });

        ach_off_header.addListener(new ActorGestureListener() {
            public void tap(InputEvent paramAnonymousInputEvent, float paramAnonymousFloat1, float paramAnonymousFloat2, int paramAnonymousInt1, int paramAnonymousInt2) {
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

                stat_container.setVisible(false);
                achs_container.setVisible(true);
            }
        });

        this.mainTable.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(mStage);

        mStage.addActor(this.mainTable);
    }

    public void achievementGenerator(Table container, Texture image, boolean flag, boolean isOdd) {
        Table table = new Table();
        Image ach_image = new Image(image);
        ach_image.setSize(Gdx.graphics.getWidth() * .32f, Gdx.graphics.getHeight() * .127f);
        table.addActor(ach_image);
        if (!flag) {
            ach_image.setColor(1f, 1f, 1f, .5f);
        }
        if (isOdd) {
            container.add(table).width(Gdx.graphics.getWidth() * .32f).height(Gdx.graphics.getHeight() * .127f)
                    .pad(Gdx.graphics.getHeight() * .02f, Gdx.graphics.getWidth() * .03f, 0f, 0f);
            container.row();
        } else {
            container.add(table).width(Gdx.graphics.getWidth() * .3f).height(Gdx.graphics.getHeight() * .127f)
                    .pad(Gdx.graphics.getHeight() * .02f, 0f, 0f, 0f);
        }
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

        int percent = (int) ((float) GamePrefs.getInstance().xp / ((float) GamePrefs.getInstance().level * 1000f) * 100f);
        Tween.to(sprite, 1, 1f)
                .target(percent).ease(TweenEquations.easeInExpo)
                .start(mTweenManager).delay(0.3F)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {

                    }
                });

        profile_coin.setX(Gdx.graphics.getWidth() * .415f);
//        coins_txt.setText("0");
//        coins_txt.setColor(1f, 1f, 1f, 0f);
        Tween.to(profile_coin, 7, .3f)
                .target(Gdx.graphics.getWidth() * .48f)
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

        profile_level.setX(Gdx.graphics.getWidth() * .415f);
//        coins_txt.setText("0");
//        coins_txt.setColor(1f, 1f, 1f, 0f);
        Tween.to(profile_level, 7, .3f)
                .target(Gdx.graphics.getWidth() * .3425f)
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

