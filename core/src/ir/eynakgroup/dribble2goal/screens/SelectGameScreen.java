package ir.eynakgroup.dribble2goal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Random;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import ir.eynakgroup.dribble2goal.Assets;
import ir.eynakgroup.dribble2goal.Constants;
import ir.eynakgroup.dribble2goal.GamePrefs;
import ir.eynakgroup.dribble2goal.MyGame;
import ir.eynakgroup.dribble2goal.Util.SelectGameScrollPane;
import ir.eynakgroup.dribble2goal.Util.Util;
import ir.eynakgroup.dribble2goal.render.textures.ProgressCircle;

/**
 * Created by kAvEh on 3/10/2016.
 */

public class SelectGameScreen implements Screen, InputProcessor {

    TweenManager mTweenManager;
    private OrthographicCamera mMainCamera;
    SpriteBatch mMainBatch;
    Image bg;
    Image back;
    private TextField coins_txt;
    private Image profile_coin;

    Stage mStage;
    Table mainTable;
    Skin mSkin;

    private ProgressCircle sprite;

    private int gameType;

    public SelectGameScreen(int type) {
        mTweenManager = MyGame.mTweenManager;
        mMainBatch = new SpriteBatch();

        this.gameType = type;

        mSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        mStage = new Stage(new FitViewport(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT), mMainBatch);
        mainTable = new Table();

        bg = new Image(Assets.getInstance().main_bg);
        bg.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        Image[] stadiums = new Image[6];
        stadiums[0] = new Image(Assets.getInstance().select_game[0]);
        stadiums[0].setSize(Constants.HUD_SCREEN_WIDTH * .309f, Constants.HUD_SCREEN_HEIGHT);
        stadiums[1] = new Image(Assets.getInstance().select_game[1]);
        stadiums[1].setSize(Constants.HUD_SCREEN_WIDTH * .309f, Constants.HUD_SCREEN_HEIGHT);
        stadiums[1].setColor(1f, 1f, 1f, 0f);
        stadiums[2] = new Image(Assets.getInstance().select_game[2]);
        stadiums[2].setSize(Constants.HUD_SCREEN_WIDTH * .309f, Constants.HUD_SCREEN_HEIGHT);
        stadiums[2].setColor(1f, 1f, 1f, 0f);
        stadiums[3] = new Image(Assets.getInstance().select_game[3]);
        stadiums[3].setSize(Constants.HUD_SCREEN_WIDTH * .309f, Constants.HUD_SCREEN_HEIGHT);
        stadiums[3].setColor(1f, 1f, 1f, 0f);
        stadiums[4] = new Image(Assets.getInstance().select_game[4]);
        stadiums[4].setSize(Constants.HUD_SCREEN_WIDTH * .309f, Constants.HUD_SCREEN_HEIGHT);
        stadiums[4].setColor(1f, 1f, 1f, 0f);
        stadiums[5] = new Image(Assets.getInstance().select_game[5]);
        stadiums[5].setSize(Constants.HUD_SCREEN_WIDTH * .309f, Constants.HUD_SCREEN_HEIGHT);
        stadiums[5].setColor(1f, 1f, 1f, 0f);
        Image arc = new Image(Assets.getInstance().select_game_arc);
        arc.setSize(Constants.HUD_SCREEN_WIDTH * .184f, Constants.HUD_SCREEN_HEIGHT);
        arc.setPosition(Constants.HUD_SCREEN_WIDTH * .203f, 0);

        Image profile_bg = new Image(Assets.getInstance().profile_background);
        profile_bg.setSize(Constants.HUD_SCREEN_WIDTH * .158f, Constants.HUD_SCREEN_WIDTH * .158f);
        profile_bg.setPosition(Constants.HUD_SCREEN_WIDTH * .83f,
                Constants.HUD_SCREEN_HEIGHT * .97f - Constants.HUD_SCREEN_WIDTH * .158f);

        TextureRegion region = new TextureRegion(Assets.getInstance().progress_circle);
        PolygonSpriteBatch pbatch = new PolygonSpriteBatch();
        sprite = new ProgressCircle(region, pbatch, new Vector2(Constants.HUD_SCREEN_WIDTH * .158f, Constants.HUD_SCREEN_HEIGHT * .158f), mStage);
        sprite.setSize(Constants.HUD_SCREEN_WIDTH * .158f, Constants.HUD_SCREEN_WIDTH * .158f);
        sprite.setPosition(Constants.HUD_SCREEN_WIDTH * .83f,
                Constants.HUD_SCREEN_HEIGHT * .97f - Constants.HUD_SCREEN_WIDTH * .158f);

        Image avatar = new Image(new Util().getAvatar(GamePrefs.getInstance().user.getAvatar()));
        avatar.setSize(Constants.HUD_SCREEN_WIDTH * .158f, Constants.HUD_SCREEN_WIDTH * .158f);
        avatar.setPosition(Constants.HUD_SCREEN_WIDTH * .83f,
                Constants.HUD_SCREEN_HEIGHT * .97f - Constants.HUD_SCREEN_WIDTH * .158f);

        profile_coin = new Image(Assets.getInstance().profile_coin_right);
        profile_coin.setSize(Constants.HUD_SCREEN_WIDTH * .228f, Constants.HUD_SCREEN_HEIGHT * .175f);
        profile_coin.setPosition(Constants.HUD_SCREEN_WIDTH * .67f, Constants.HUD_SCREEN_HEIGHT * .734f);

        profile_coin.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                MyGame.mainInstance.setShopScreen();
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        coins_txt = new TextField(new Util().coinsStyle() + "", mSkin);
        coins_txt.setDisabled(true);
        coins_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .775f, Constants.HUD_SCREEN_HEIGHT * .79f);

        coins_txt.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                MyGame.mainInstance.setShopScreen();
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        back = new Image(Assets.getInstance().icon_back);
        back.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
        back.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .04f);

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

        Image[] scrollImg = new Image[6];
        scrollImg[0] = new Image(Assets.getInstance().stadium_button_1);
        scrollImg[0].setSize(Constants.HUD_SCREEN_WIDTH * .3125f, Constants.HUD_SCREEN_HEIGHT * .18f);
        if (GamePrefs.getInstance().user.getLevel() > 1) {
            scrollImg[1] = new Image(Assets.getInstance().stadium_button_2);
        } else {
            scrollImg[1] = new Image(Assets.getInstance().stadium_button_2_lock);
        }
        scrollImg[1].setSize(Constants.HUD_SCREEN_WIDTH * .3125f, Constants.HUD_SCREEN_HEIGHT * .18f);
        if (GamePrefs.getInstance().user.getLevel() > 3) {
            scrollImg[2] = new Image(Assets.getInstance().stadium_button_3);
        } else {
            scrollImg[2] = new Image(Assets.getInstance().stadium_button_3_lock);
        }
        scrollImg[2].setSize(Constants.HUD_SCREEN_WIDTH * .3125f, Constants.HUD_SCREEN_HEIGHT * .18f);
        if (GamePrefs.getInstance().user.getLevel() > 5) {
            scrollImg[3] = new Image(Assets.getInstance().stadium_button_4);
        } else {
            scrollImg[3] = new Image(Assets.getInstance().stadium_button_4_lock);
        }
        scrollImg[3].setSize(Constants.HUD_SCREEN_WIDTH * .3125f, Constants.HUD_SCREEN_HEIGHT * .18f);
        if (GamePrefs.getInstance().user.getLevel() > 7) {
            scrollImg[4] = new Image(Assets.getInstance().stadium_button_5);
        } else {
            scrollImg[4] = new Image(Assets.getInstance().stadium_button_5_lock);
        }
        scrollImg[4].setSize(Constants.HUD_SCREEN_WIDTH * .3125f, Constants.HUD_SCREEN_HEIGHT * .18f);
        if (GamePrefs.getInstance().user.getLevel() > 7) {
            scrollImg[5] = new Image(Assets.getInstance().stadium_button_6);
        } else {
            scrollImg[5] = new Image(Assets.getInstance().stadium_button_6_lock);
        }
        scrollImg[5].setSize(Constants.HUD_SCREEN_WIDTH * .3125f, Constants.HUD_SCREEN_HEIGHT * .18f);

        scrollImg[0].addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                if (GamePrefs.getInstance().isEffectOn() == 1) {
                    Assets.getInstance().click.play();
                }
                MyGame.mainInstance.setFindMatchScreen(1, gameType);
            }
        });
        if (GamePrefs.getInstance().user.getLevel() > 1) {
            scrollImg[1].addListener(new ActorGestureListener() {
                public void tap(InputEvent event, float x, float y, int count, int button) {
                    if (GamePrefs.getInstance().isEffectOn() == 1) {
                        Assets.getInstance().click.play();
                    }
                    MyGame.mainInstance.setFindMatchScreen(2, gameType);
                }
            });
        }
        if (GamePrefs.getInstance().user.getLevel() > 3) {
            scrollImg[2].addListener(new ActorGestureListener() {
                public void tap(InputEvent event, float x, float y, int count, int button) {
                    if (GamePrefs.getInstance().isEffectOn() == 1) {
                        Assets.getInstance().click.play();
                    }
                    MyGame.mainInstance.setFindMatchScreen(3, gameType);
                }
            });
        }
        if (GamePrefs.getInstance().user.getLevel() > 5) {
            scrollImg[3].addListener(new ActorGestureListener() {
                public void tap(InputEvent event, float x, float y, int count, int button) {
                    if (GamePrefs.getInstance().isEffectOn() == 1) {
                        Assets.getInstance().click.play();
                    }
                    MyGame.mainInstance.setFindMatchScreen(4, gameType);
                }
            });
        }
        if (GamePrefs.getInstance().user.getLevel() > 7) {
            scrollImg[4].addListener(new ActorGestureListener() {
                public void tap(InputEvent event, float x, float y, int count, int button) {
                    if (GamePrefs.getInstance().isEffectOn() == 1) {
                        Assets.getInstance().click.play();
                    }
                    MyGame.mainInstance.setFindMatchScreen(5, gameType);
                }
            });

            scrollImg[5].addListener(new ActorGestureListener() {
                public void tap(InputEvent event, float x, float y, int count, int button) {
                    if (GamePrefs.getInstance().isEffectOn() == 1) {
                        Assets.getInstance().click.play();
                    }
                    MyGame.mainInstance.setFindMatchScreen(6, gameType);
                }
            });
        }

        SelectGameScrollPane mainmenuScroll = new SelectGameScrollPane(scrollImg, 6,
                Constants.HUD_SCREEN_WIDTH, (int) Constants.HUD_SCREEN_HEIGHT,
                (int) (Constants.HUD_SCREEN_WIDTH * .07f), 0,
                Constants.HUD_SCREEN_WIDTH * .3125f, Constants.HUD_SCREEN_HEIGHT * .18f,
                Constants.HUD_SCREEN_HEIGHT * .12f, stadiums);
        mainmenuScroll.setVisible(true);

        mainTable.addActor(bg);
        mainTable.addActor(stadiums[0]);
        mainTable.addActor(stadiums[1]);
        mainTable.addActor(stadiums[2]);
        mainTable.addActor(stadiums[3]);
        mainTable.addActor(stadiums[4]);
        mainTable.addActor(stadiums[5]);
        mainTable.addActor(arc);
        mainTable.addActor(mainmenuScroll);
        mainTable.addActor(profile_coin);
        mainTable.addActor(profile_bg);
        mainTable.addActor(sprite);
        mainTable.addActor(avatar);
        mainTable.addActor(coins_txt);
        mainTable.addActor(back);

        this.mainTable.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(mStage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        mStage.addActor(this.mainTable);
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

        profile_coin.setWidth(0f);
        profile_coin.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .734f);
        coins_txt.setText("0");
        coins_txt.setColor(1f, 1f, 1f, 0f);
        Tween.to(profile_coin, 4, 1.3f)
                .target(Constants.HUD_SCREEN_WIDTH * .67f, Constants.HUD_SCREEN_HEIGHT * .734f,
                        Constants.HUD_SCREEN_WIDTH * .228f, Constants.HUD_SCREEN_HEIGHT * .175f)
                .ease(TweenEquations.easeInExpo)
                .start(mTweenManager).delay(0.0F)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                        coins_txt.setColor(1f, 1f, 1f, 1f);
                        Tween.to(coins_txt, 1, 1.1f)
                                .target(Math.min(GamePrefs.getInstance().user.getCoins_num(), 1000)).ease(TweenEquations.easeOutQuad)
                                .start(mTweenManager).delay(0.0F)
                                .setCallback(new TweenCallback() {
                                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                                        coins_txt.setText(new Util().coinsStyle());
                                    }
                                });
                    }
                });

        int percent = (int) ((float) GamePrefs.getInstance().user.getXp() / ((float) GamePrefs.getInstance().user.getLevel() * 1000f) * 100f);
        Tween.to(sprite, 1, .8f)
                .target(percent).ease(TweenEquations.easeInExpo)
                .start(mTweenManager).delay(0.2F);
    }

    @Override
    public void render(float delta) {
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