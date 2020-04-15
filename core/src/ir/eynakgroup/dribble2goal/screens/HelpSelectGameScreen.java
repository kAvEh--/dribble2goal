package ir.eynakgroup.dribble2goal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;
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
import ir.eynakgroup.dribble2goal.Util.PersianDecoder;
import ir.eynakgroup.dribble2goal.Util.SelectGameScrollPane;
import ir.eynakgroup.dribble2goal.Util.Util;
import ir.eynakgroup.dribble2goal.render.textures.ProgressCircle;

/**
 * Created by Eynak_PC2 on 2/27/2017.
 *
 */

public class HelpSelectGameScreen implements Screen, InputProcessor {

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

    private String[] help_texts = new String[]{"برای وارد شدن به هر کدوم از استادیوم\u200Cها باید سکه\u200Cی ورودی اون رو پرداخت کنی.", "برنده دو برابر سکه\u200Cهای ورودی رو بدست میاره.", "بیا بریم تو استادیوم آزادی بازی کنیم."};
    private int help_state = 0;
    private PersianDecoder persian = new PersianDecoder();

    public HelpSelectGameScreen() {
        mTweenManager = MyGame.mTweenManager;
        mMainBatch = new SpriteBatch();

        mSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        mStage = new Stage(new FitViewport(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT), mMainBatch);
        mainTable = new Table();

        bg = new Image(Assets.getInstance().main_bg);
        bg.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        Image[] stadiums = new Image[1];
        stadiums[0] = new Image(Assets.getInstance().select_game[0]);
        stadiums[0].setSize(Constants.HUD_SCREEN_WIDTH * .309f, Constants.HUD_SCREEN_HEIGHT);
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
                GamePrefs.getInstance().setHintState(1, 1);
                GamePrefs.getInstance().setHintState(2, 1);
                GamePrefs.getInstance().setHintState(3, 1);
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

        Image[] scrollImg = new Image[1];
        scrollImg[0] = new Image(Assets.getInstance().stadium_button_1);
        scrollImg[0].setSize(Constants.HUD_SCREEN_WIDTH * .3125f, Constants.HUD_SCREEN_HEIGHT * .18f);

        scrollImg[0].addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                if (GamePrefs.getInstance().isEffectOn() == 1) {
                    Assets.getInstance().click.play();
                }
                MyGame.mainInstance.createHelpGame();
            }
        });

        SelectGameScrollPane mainmenuScroll = new SelectGameScrollPane(scrollImg, 1,
                Constants.HUD_SCREEN_WIDTH, (int) Constants.HUD_SCREEN_HEIGHT,
                (int) (Constants.HUD_SCREEN_WIDTH * .07f), 0,
                Constants.HUD_SCREEN_WIDTH * .3125f, Constants.HUD_SCREEN_HEIGHT * .18f,
                Constants.HUD_SCREEN_HEIGHT * .12f, stadiums);
        mainmenuScroll.setVisible(true);

        final Image help_bg = new Image(Assets.getInstance().help_bg);
        help_bg.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);
        final Image help_box = new Image(Assets.getInstance().help_box);
        help_box.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT * .22f);
        help_box.setPosition(0f, Constants.HUD_SCREEN_HEIGHT * .197f);

        final Image confirm = new Image(Assets.getInstance().help_ic_next);
        confirm.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
        confirm.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .00f);

        final Image help_skip = new Image(Assets.getInstance().help_ic_skip);
        help_skip.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
        help_skip.setPosition(Constants.HUD_SCREEN_WIDTH * .017f, Constants.HUD_SCREEN_HEIGHT * .00f);

        Label.LabelStyle style = new Label.LabelStyle();
        style.fontColor = Color.WHITE;
        style.font = mSkin.getFont("yekan");

        String converted = persian.TransformText(help_texts[help_state]);
        final Label text = new Label(converted, style);
        text.setWrap(true);
        text.setAlignment(Align.center);
        text.setBounds(0, Constants.HUD_SCREEN_HEIGHT * .21f,
                Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT * .22f);

        confirm.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                help_state++;
                if (help_state > 2) {
                    mainTable.removeActor(help_bg);
                    mainTable.removeActor(help_box);
                    mainTable.removeActor(text);
                    mainTable.removeActor(confirm);
                    mainTable.removeActor(help_skip);
                    return;
                }
                changeTextHelp(text, persian.TransformText(help_texts[help_state]));
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                confirm.setSize(Constants.HUD_SCREEN_WIDTH * .133f * .8f, Constants.HUD_SCREEN_HEIGHT * .186f * .8f);
                confirm.setPosition(Constants.HUD_SCREEN_WIDTH * .8633f, Constants.HUD_SCREEN_HEIGHT * .0186f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                confirm.setSize(Constants.HUD_SCREEN_WIDTH * .1345f, Constants.HUD_SCREEN_HEIGHT * .186f);
                confirm.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .00f);
            }
        });

        help_skip.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                GamePrefs.getInstance().setHintState(1, 1);
                GamePrefs.getInstance().setHintState(2, 1);
                GamePrefs.getInstance().setHintState(3, 1);
                MyGame.mainInstance.setMainScreen();
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                help_skip.setSize(Constants.HUD_SCREEN_WIDTH * .133f * .8f, Constants.HUD_SCREEN_HEIGHT * .186f * .8f);
                help_skip.setPosition(Constants.HUD_SCREEN_WIDTH * .0303f, Constants.HUD_SCREEN_HEIGHT * .0186f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                help_skip.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
                help_skip.setPosition(Constants.HUD_SCREEN_WIDTH * .017f, Constants.HUD_SCREEN_HEIGHT * .00f);
            }
        });

        mainTable.addActor(bg);
        mainTable.addActor(stadiums[0]);
        mainTable.addActor(arc);
        mainTable.addActor(mainmenuScroll);
        mainTable.addActor(profile_coin);
        mainTable.addActor(profile_bg);
        mainTable.addActor(sprite);
        mainTable.addActor(avatar);
        mainTable.addActor(coins_txt);
        mainTable.addActor(back);
        mainTable.addActor(help_bg);
        mainTable.addActor(help_box);
        mainTable.addActor(text);
        mainTable.addActor(confirm);
        mainTable.addActor(help_skip);

        this.mainTable.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(mStage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        mStage.addActor(this.mainTable);
    }

    private void changeTextHelp(final Label label, final String s) {
        Tween.to(label, 7, .5f)
                .target(Constants.HUD_SCREEN_WIDTH * -1f).ease(TweenEquations.easeInOutExpo)
                .start(mTweenManager).delay(0f)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                        label.setText(s);
                        label.setPosition(Constants.HUD_SCREEN_WIDTH * 1f, label.getY());
                        Tween.to(label, 7, .2f)
                                .target(0f).ease(TweenEquations.easeInBack)
                                .start(mTweenManager).delay(0f);
                    }
                });
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