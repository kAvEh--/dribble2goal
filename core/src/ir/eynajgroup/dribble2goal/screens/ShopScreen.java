package ir.eynajgroup.dribble2goal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
import ir.eynajgroup.dribble2goal.Util.ShopScrollPane;

/**
 * Created by Eynak_PC2 on 6/27/2016.
 */
public class ShopScreen implements Screen {

    TweenManager mTweenManager;
    private OrthographicCamera mMainCamera;
    SpriteBatch mMainBatch;
    Image bg;

    Stage mStage;
    Table mainTable;
    Skin mSkin;

    ShopScrollPane scrollPane;
    Table dots;
    Table coinsTable;
    Image next_page;
    Image prev_page;

    Image coins;
    TextField coins_txt;

    Image back;
    Image indicator;

    Image tab_coins;
    Image tab_app;
    boolean tab_flag = true;

    public ShopScreen() {
        mTweenManager = MyGame.mTweenManager;
        mMainBatch = new SpriteBatch();

        mSkin = new Skin(Gdx.files.internal("data/uiskin.json"));

        mStage = new Stage(new FitViewport(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT), mMainBatch);
        mainTable = new Table();

        bg = new Image(Assets.getInstance().main_bg);
        bg.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        tab_coins = new Image(Assets.getInstance().shop_tab_coins_on);
        tab_coins.setSize(Constants.HUD_SCREEN_WIDTH * .255f, Constants.HUD_SCREEN_HEIGHT * .1f);
        tab_coins.setPosition(Constants.HUD_SCREEN_WIDTH * .245f, Constants.HUD_SCREEN_HEIGHT * .85f);
        tab_app = new Image(Assets.getInstance().shop_tab_app);
        tab_app.setSize(Constants.HUD_SCREEN_WIDTH * .255f, Constants.HUD_SCREEN_HEIGHT * .1f);
        tab_app.setPosition(Constants.HUD_SCREEN_WIDTH * .5f, Constants.HUD_SCREEN_HEIGHT * .85f);

        dots = new Table();
        dots.setPosition(Constants.HUD_SCREEN_WIDTH * .5f, Constants.HUD_SCREEN_HEIGHT * -.12f);
        for (int i = 0; i < 4; i++) {
            dots.add(new Image(Assets.getInstance().page_indicator_dot))
                    .pad(0, 0, 0, 0)
                    .size(Constants.HUD_SCREEN_WIDTH * .03f, Constants.HUD_SCREEN_WIDTH * .03f);
        }
        indicator = new Image(Assets.getInstance().setting_dot);
        dots.add(indicator).pad(0, Constants.HUD_SCREEN_WIDTH * -.21f, 0, 0)
                .size(Constants.HUD_SCREEN_WIDTH * .03f, Constants.HUD_SCREEN_WIDTH * .03f);

        scrollPane = new ShopScrollPane((int) Constants.HUD_SCREEN_WIDTH, (int) Constants.HUD_SCREEN_HEIGHT, dots, indicator);
        scrollPane.setPosition(Constants.HUD_SCREEN_WIDTH, 0f);

        coinsTable = new Table();
        coinsTable.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);
        coinsTable.setPosition(0, 0);

        setCoinsTable();

        back = new Image(Assets.getInstance().icon_back);
        back.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
        back.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .04f);

        next_page = new Image(Assets.getInstance().arrow_right);
        next_page.setSize(Constants.HUD_SCREEN_WIDTH * .085f, Constants.HUD_SCREEN_HEIGHT * .127f);
        next_page.setPosition(Constants.HUD_SCREEN_WIDTH * .56f, Constants.HUD_SCREEN_HEIGHT * -.12f);

        prev_page = new Image(Assets.getInstance().arrow_left);
        prev_page.setSize(Constants.HUD_SCREEN_WIDTH * .085f, Constants.HUD_SCREEN_HEIGHT * .127f);
        prev_page.setPosition(Constants.HUD_SCREEN_WIDTH * .354f, Constants.HUD_SCREEN_HEIGHT * -.12f);

        coins = new Image(Assets.getInstance().setting_coin);
        coins.setSize(Constants.HUD_SCREEN_WIDTH * .25f, Constants.HUD_SCREEN_HEIGHT * .186f);
        coins.setPosition(Constants.HUD_SCREEN_WIDTH * .05f, Constants.HUD_SCREEN_HEIGHT * .04f);

        coins_txt = new TextField("0", mSkin);
        coins_txt.setColor(.6f, .3f, .5f, 1f);
        coins_txt.setDisabled(true);
        coins_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .155f, Constants.HUD_SCREEN_HEIGHT * .087f);

        back.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
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

        next_page.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                scrollPane.gotoPage(true);
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                next_page.setSize(Constants.HUD_SCREEN_WIDTH * .085f * .8f, Constants.HUD_SCREEN_HEIGHT * .127f * .8f);
                next_page.setPosition(Constants.HUD_SCREEN_WIDTH * .5685f, Constants.HUD_SCREEN_HEIGHT * .0724f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                next_page.setSize(Constants.HUD_SCREEN_WIDTH * .085f, Constants.HUD_SCREEN_HEIGHT * .127f);
                next_page.setPosition(Constants.HUD_SCREEN_WIDTH * .56f, Constants.HUD_SCREEN_HEIGHT * .06f);
            }
        });

        prev_page.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                scrollPane.gotoPage(false);
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                prev_page.setSize(Constants.HUD_SCREEN_WIDTH * .085f * .8f, Constants.HUD_SCREEN_HEIGHT * .127f * .8f);
                prev_page.setPosition(Constants.HUD_SCREEN_WIDTH * .3625f, Constants.HUD_SCREEN_HEIGHT * .06127f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                prev_page.setSize(Constants.HUD_SCREEN_WIDTH * .085f, Constants.HUD_SCREEN_HEIGHT * .127f);
                prev_page.setPosition(Constants.HUD_SCREEN_WIDTH * .354f, Constants.HUD_SCREEN_HEIGHT * .06f);
            }
        });

        tab_coins.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                if (!tab_flag) {
                    tab_coins.setDrawable(new TextureRegionDrawable(new TextureRegion(Assets.getInstance().shop_tab_coins_on)));
                    tab_app.setDrawable(new TextureRegionDrawable(new TextureRegion(Assets.getInstance().shop_tab_app)));
                    Tween.to(coinsTable, 1, .3f)
                            .target(0, 0)
                            .ease(TweenEquations.easeOutExpo)
                            .start(mTweenManager).delay(0.0F);
                    Tween.to(scrollPane, 1, .3f)
                            .target(Constants.HUD_SCREEN_WIDTH, 0)
                            .ease(TweenEquations.easeOutExpo)
                            .start(mTweenManager).delay(0.0F);
                    Tween.to(dots, 1, .3f)
                            .target(Constants.HUD_SCREEN_WIDTH * .5f, Constants.HUD_SCREEN_HEIGHT * -.12f)
                            .ease(TweenEquations.easeInExpo)
                            .start(mTweenManager).delay(0.0F);
                    Tween.to(dots, 5, .3f)
                            .target(0)
                            .ease(TweenEquations.easeInExpo)
                            .start(mTweenManager).delay(0.0F);
                    Tween.to(prev_page, 1, .3f)
                            .target(Constants.HUD_SCREEN_WIDTH * .354f, Constants.HUD_SCREEN_HEIGHT * -.12f)
                            .ease(TweenEquations.easeInExpo)
                            .start(mTweenManager).delay(0.0F);
                    Tween.to(prev_page, 3, .3f)
                            .target(0)
                            .ease(TweenEquations.easeInExpo)
                            .start(mTweenManager).delay(0.0F);
                    Tween.to(next_page, 1, .3f)
                            .target(Constants.HUD_SCREEN_WIDTH * .56f, Constants.HUD_SCREEN_HEIGHT * -.12f)
                            .ease(TweenEquations.easeInExpo)
                            .start(mTweenManager).delay(0.0F);
                    Tween.to(next_page, 3, .3f)
                            .target(0)
                            .ease(TweenEquations.easeInExpo)
                            .start(mTweenManager).delay(0.0F);
                    tab_flag = true;
                }
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        tab_app.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                if (tab_flag) {
                    tab_coins.setDrawable(new TextureRegionDrawable(new TextureRegion(Assets.getInstance().shop_tab_coins)));
                    tab_app.setDrawable(new TextureRegionDrawable(new TextureRegion(Assets.getInstance().shop_tab_app_on)));
                    Tween.to(coinsTable, 1, .3f)
                            .target(Constants.HUD_SCREEN_WIDTH * -1f, 0)
                            .ease(TweenEquations.easeOutExpo)
                            .start(mTweenManager).delay(0.0F);
                    Tween.to(scrollPane, 1, .3f)
                            .target(0, 0)
                            .ease(TweenEquations.easeOutExpo)
                            .start(mTweenManager).delay(0.0F);
                    Tween.to(dots, 1, .3f)
                            .target(Constants.HUD_SCREEN_WIDTH * .5f, Constants.HUD_SCREEN_HEIGHT * .12f)
                            .ease(TweenEquations.easeOutExpo)
                            .start(mTweenManager).delay(0.0F);
                    Tween.to(dots, 5, .3f)
                            .target(1)
                            .ease(TweenEquations.easeOutCubic)
                            .start(mTweenManager).delay(0.0F);
                    Tween.to(prev_page, 1, .3f)
                            .target(Constants.HUD_SCREEN_WIDTH * .354f, Constants.HUD_SCREEN_HEIGHT * .06f)
                            .ease(TweenEquations.easeOutExpo)
                            .start(mTweenManager).delay(0.0F);
                    Tween.to(prev_page, 3, .3f)
                            .target(1)
                            .ease(TweenEquations.easeOutCubic)
                            .start(mTweenManager).delay(0.0F);
                    Tween.to(next_page, 1, .3f)
                            .target(Constants.HUD_SCREEN_WIDTH * .56f, Constants.HUD_SCREEN_HEIGHT * .06f)
                            .ease(TweenEquations.easeOutExpo)
                            .start(mTweenManager).delay(0.0F);
                    Tween.to(next_page, 3, .3f)
                            .target(1)
                            .ease(TweenEquations.easeOutCubic)
                            .start(mTweenManager).delay(0.0F);
                    tab_flag = false;
                }
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        this.mainTable.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);
        Gdx.input.setInputProcessor(mStage);
        mSkin = new Skin(Gdx.files.internal("data/uiskin.json"));

        mSkin.getFont("english").getData();

        mainTable.addActor(bg);
        mainTable.addActor(dots);
        mainTable.addActor(coins);
        mainTable.addActor(coins_txt);
        mainTable.addActor(scrollPane);
        mainTable.addActor(coinsTable);
        mainTable.addActor(prev_page);
        mainTable.addActor(next_page);
        mainTable.addActor(tab_coins);
        mainTable.addActor(tab_app);
        mainTable.addActor(back);

        back.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
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
        Gdx.input.setInputProcessor(mStage);

        mStage.addActor(this.mainTable);
    }

    private void setCoinsTable() {
        //TODO
        // buy 500 coin --> 800 toman
        final Table coins500 = new Table();
        Image tmp = new Image(Assets.getInstance().shop_coin_500);
        tmp.setSize(Constants.HUD_SCREEN_WIDTH * .253f, Constants.HUD_SCREEN_HEIGHT * .238f);
        coins500.addActor(tmp);
        coinsTable.add(coins500)
                .pad(Constants.HUD_SCREEN_HEIGHT * .07f, 0,
                        0, 0)
                .size(Constants.HUD_SCREEN_WIDTH * .253f, Constants.HUD_SCREEN_HEIGHT * .238f);

        // buy 1500 coin --> 2000 Toman
        final Table coins1500 = new Table();
        tmp = new Image(Assets.getInstance().shop_coin_1500);
        tmp.setSize(Constants.HUD_SCREEN_WIDTH * .253f, Constants.HUD_SCREEN_HEIGHT * .238f);
        coins1500.addActor(tmp);
        coinsTable.add(coins1500)
                .pad(Constants.HUD_SCREEN_HEIGHT * .07f, Constants.HUD_SCREEN_WIDTH * .09f,
                        0, 0)
                .size(Constants.HUD_SCREEN_WIDTH * .253f, Constants.HUD_SCREEN_HEIGHT * .238f);

        coinsTable.row();

        // buy 4000 coin --> 4000 Toman
        final Table coins4k = new Table();
        tmp = new Image(Assets.getInstance().shop_coin_4k);
        tmp.setSize(Constants.HUD_SCREEN_WIDTH * .253f, Constants.HUD_SCREEN_HEIGHT * .238f);
        coins4k.addActor(tmp);
        coinsTable.add(coins4k)
                .pad(Constants.HUD_SCREEN_HEIGHT * .07f, 0,
                        Constants.HUD_SCREEN_HEIGHT * .12f, 0)
                .size(Constants.HUD_SCREEN_WIDTH * .253f, Constants.HUD_SCREEN_HEIGHT * .238f);

        // buy 10000 coin --> 8000 Toman
        final Table coins10k = new Table();
        tmp = new Image(Assets.getInstance().shop_coin_10k);
        tmp.setSize(Constants.HUD_SCREEN_WIDTH * .253f, Constants.HUD_SCREEN_HEIGHT * .238f);
        coins10k.addActor(tmp);
        coinsTable.add(coins10k)
                .pad(Constants.HUD_SCREEN_HEIGHT * .07f, Constants.HUD_SCREEN_WIDTH * .09f,
                        Constants.HUD_SCREEN_HEIGHT * .12f, 0)
                .size(Constants.HUD_SCREEN_WIDTH * .253f, Constants.HUD_SCREEN_HEIGHT * .238f);

        coins500.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                coinsTable.getCell(coins500).size(Constants.HUD_SCREEN_WIDTH * .253f, Constants.HUD_SCREEN_HEIGHT * .238f * .8f);
                coinsTable.invalidate();
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                coinsTable.getCell(coins500).size(Constants.HUD_SCREEN_WIDTH * .253f, Constants.HUD_SCREEN_HEIGHT * .238f);
                coinsTable.invalidate();
            }
        });

        coins1500.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                coinsTable.getCell(coins1500).size(Constants.HUD_SCREEN_WIDTH * .253f, Constants.HUD_SCREEN_HEIGHT * .238f * .8f);
                coinsTable.invalidate();
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                coinsTable.getCell(coins1500).size(Constants.HUD_SCREEN_WIDTH * .253f, Constants.HUD_SCREEN_HEIGHT * .238f);
                coinsTable.invalidate();
            }
        });

        coins4k.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                coinsTable.getCell(coins4k).size(Constants.HUD_SCREEN_WIDTH * .253f, Constants.HUD_SCREEN_HEIGHT * .238f * .8f);
                coinsTable.invalidate();
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                coinsTable.getCell(coins4k).size(Constants.HUD_SCREEN_WIDTH * .253f, Constants.HUD_SCREEN_HEIGHT * .238f);
                coinsTable.invalidate();
            }
        });

        coins10k.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                coinsTable.getCell(coins10k).size(Constants.HUD_SCREEN_WIDTH * .253f, Constants.HUD_SCREEN_HEIGHT * .238f * .8f);
                coinsTable.invalidate();
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                coinsTable.getCell(coins10k).size(Constants.HUD_SCREEN_WIDTH * .253f, Constants.HUD_SCREEN_HEIGHT * .238f);
                coinsTable.invalidate();
            }
        });
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

        coins_txt.setText("0");
        Tween.to(coins_txt, 1, 1.1f)
                .target(GamePrefs.getInstance().coins_num).ease(TweenEquations.easeOutQuad)
                .start(mTweenManager).delay(0.0F);
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