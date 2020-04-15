package ir.eynakgroup.dribble2goal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
import ir.eynakgroup.dribble2goal.Util.PersianDecoder;
import ir.eynakgroup.dribble2goal.Util.Popups;
import ir.eynakgroup.dribble2goal.Util.Util;
import ir.eynakgroup.dribble2goal.render.textures.ProgressCircle;

/**
 * Created by kAvEh on 3/4/2016.
 */
public class MainMenuScreen implements Screen, InputProcessor {

    TweenManager mTweenManager;
    private OrthographicCamera mMainCamera;
    private Image arc;
    private Image bg;
    private Image item_penalty_light;
    private Image item_light_2p;
    private Image item_light_coach;
    private Image profile_coin;
    private ProgressCircle sprite;
    private Image menu_settings;
    private Image menu_shop;
    private Image gift;
    Stage mStage;
    Table mainTable;
    Skin mSkin;
    private TextField coins_txt;

    private String[] help_texts = new String[]{"به دریبل تو گل خوش اومدی.", "بیا روش بازی کردن رو باهم مرور کنیم.", "هر بازی دو تا نیمه داره که توی یه نیمه باید دفاع کنی تو یه نیمه حمله.", "اگه آخر بازی تعداد گل\u200Cهای تو با حریفت مساوی باشه، بازی به پنالتی کشیده می\u200Cشه.", "موقع پنالتی\u200Cها هم هر کسی که بیشتر پنالتی\u200Cهاشو گل کنه، برنده می\u200Cشه.", "راستی یادت نره که می\u200Cتونی علاوه بر بازی معمولی، پنالتی رو هم امتحان کنی.", "بیا بریم تا یه ورزشگاه رو انتخاب کنیم."};
    private int help_state = 0;

    private PersianDecoder persian = new PersianDecoder();

    final private String popup_string = "popup";
    private Image dark_bg;
    MainMenuScreen screen;

    private ParticleEffectActor[] pActor;

    public MainMenuScreen() {
        mTweenManager = MyGame.mTweenManager;
        SpriteBatch mMainBatch = new SpriteBatch();

        screen = this;

        mStage = new Stage(new FitViewport(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT), mMainBatch);
        mainTable = new Table();

        ServerTool.getInstance().getCoin();

        Gdx.input.setCatchBackKey(true);

        bg = new Image(Assets.getInstance().main_bg);
        arc = new Image(Assets.getInstance().main_arc);
        bg.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);
        arc.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_WIDTH * .257f);
        arc.setColor(1f, 1f, 1f, 0f);

        Image item_bg_2p = new Image(Assets.getInstance().main_item_bg);
        item_bg_2p.setSize(Constants.HUD_SCREEN_WIDTH * .28f, Constants.HUD_SCREEN_WIDTH * .28f);
        item_bg_2p.setPosition(Constants.HUD_SCREEN_WIDTH * .36f, Constants.HUD_SCREEN_HEIGHT * .197f);
        item_light_2p = new Image(Assets.getInstance().main_item_light);
        item_light_2p.setSize(Constants.HUD_SCREEN_WIDTH * .28f, Constants.HUD_SCREEN_WIDTH * .28f);
        item_light_2p.setPosition(Constants.HUD_SCREEN_WIDTH * .36f, Constants.HUD_SCREEN_HEIGHT * .197f);
        item_light_2p.setOrigin(item_light_2p.getWidth() / 2, item_light_2p.getHeight() * .425f);
        Image item_2p = new Image(Assets.getInstance().main_item_online);
        item_2p.setSize(Constants.HUD_SCREEN_WIDTH * .28f, Constants.HUD_SCREEN_WIDTH * .28f);
        item_2p.setPosition(Constants.HUD_SCREEN_WIDTH * .36f, Constants.HUD_SCREEN_HEIGHT * .197f);

        Image onevone_label = new Image(Assets.getInstance().onevonelabel);
        onevone_label.setSize(Constants.HUD_SCREEN_WIDTH * .168f, Constants.HUD_SCREEN_HEIGHT * .132f);
        onevone_label.setPosition(Constants.HUD_SCREEN_WIDTH * .416f, Constants.HUD_SCREEN_HEIGHT * .05f);

        dark_bg = new Image(Assets.getInstance().dark_bg);
        dark_bg.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);
        dark_bg.setPosition(0, Constants.HUD_SCREEN_HEIGHT * -1);

        Image item_penalty = new Image(Assets.getInstance().penalty_icon);
        item_penalty.setSize(Constants.HUD_SCREEN_WIDTH * .24f, Constants.HUD_SCREEN_WIDTH * .24f);
        item_penalty.setPosition(Constants.HUD_SCREEN_WIDTH * .06f, Constants.HUD_SCREEN_HEIGHT * .11f);
        item_penalty_light = new Image(Assets.getInstance().main_item_light);
        item_penalty_light.setSize(Constants.HUD_SCREEN_WIDTH * .24f, Constants.HUD_SCREEN_WIDTH * .24f);
        item_penalty_light.setPosition(Constants.HUD_SCREEN_WIDTH * .06f, Constants.HUD_SCREEN_HEIGHT * .11f);
        item_penalty_light.setOrigin(item_penalty_light.getWidth() / 2, item_penalty_light.getHeight() * .425f);

        Image item_penalty_text = new Image(Assets.getInstance().penalty_text);
        item_penalty_text.setSize(Constants.HUD_SCREEN_WIDTH * .1413f, Constants.HUD_SCREEN_HEIGHT * .091f);
        item_penalty_text.setPosition(Constants.HUD_SCREEN_WIDTH * .105f, Constants.HUD_SCREEN_HEIGHT * .01f);

        Image item_coach = new Image(Assets.getInstance().main_item_coach);
        item_coach.setSize(Constants.HUD_SCREEN_WIDTH * .24f, Constants.HUD_SCREEN_WIDTH * .24f);
        item_coach.setPosition(Constants.HUD_SCREEN_WIDTH * .7f, Constants.HUD_SCREEN_HEIGHT * .11f);
        Image item_bg_coach = new Image(Assets.getInstance().main_item_bg);
        item_bg_coach.setSize(Constants.HUD_SCREEN_WIDTH * .24f, Constants.HUD_SCREEN_WIDTH * .24f);
        item_bg_coach.setPosition(Constants.HUD_SCREEN_WIDTH * .7f, Constants.HUD_SCREEN_HEIGHT * .11f);
        item_light_coach = new Image(Assets.getInstance().main_item_light);
        item_light_coach.setSize(Constants.HUD_SCREEN_WIDTH * .24f, Constants.HUD_SCREEN_WIDTH * .24f);
        item_light_coach.setPosition(Constants.HUD_SCREEN_WIDTH * .7f, Constants.HUD_SCREEN_HEIGHT * .11f);
        item_light_coach.setOrigin(item_light_coach.getWidth() / 2, item_light_coach.getHeight() * .425f);

        Image squadlabel = new Image(Assets.getInstance().squadlabel);
        squadlabel.setSize(Constants.HUD_SCREEN_WIDTH * .118f, Constants.HUD_SCREEN_HEIGHT * .092f);
        squadlabel.setPosition(Constants.HUD_SCREEN_WIDTH * .761f, Constants.HUD_SCREEN_HEIGHT * .01f);

        menu_shop = new Image(Assets.getInstance().main_icon_shop);
        menu_shop.setSize(Constants.HUD_SCREEN_WIDTH * .11f, Constants.HUD_SCREEN_WIDTH * .11f);
        menu_shop.setPosition(Constants.HUD_SCREEN_WIDTH * .72f, Constants.HUD_SCREEN_HEIGHT * .745f);

        gift = new Image(Assets.getInstance().main_icon_gift);
        gift.setSize(Constants.HUD_SCREEN_WIDTH * .11f, Constants.HUD_SCREEN_WIDTH * .11f);
        gift.setPosition(Constants.HUD_SCREEN_WIDTH * .58f, Constants.HUD_SCREEN_HEIGHT * .745f);

        menu_settings = new Image(Assets.getInstance().main_icon_settings);
        menu_settings.setSize(Constants.HUD_SCREEN_WIDTH * .11f, Constants.HUD_SCREEN_WIDTH * .11f);
        menu_settings.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .745f);

        Image profile_bg = new Image(Assets.getInstance().profile_background);
        profile_bg.setSize(Constants.HUD_SCREEN_WIDTH * .158f, Constants.HUD_SCREEN_WIDTH * .158f);
        profile_bg.setPosition(Constants.HUD_SCREEN_WIDTH * .03f,
                Constants.HUD_SCREEN_HEIGHT * .97f - Constants.HUD_SCREEN_WIDTH * .158f);

        TextureRegion region = new TextureRegion(Assets.getInstance().progress_circle);
        PolygonSpriteBatch pbatch = new PolygonSpriteBatch();
        sprite = new ProgressCircle(region, pbatch, new Vector2(Constants.HUD_SCREEN_WIDTH * .158f, Constants.HUD_SCREEN_HEIGHT * .158f), mStage);
        sprite.setSize(Constants.HUD_SCREEN_WIDTH * .158f, Constants.HUD_SCREEN_WIDTH * .158f);
        sprite.setPosition(Constants.HUD_SCREEN_WIDTH * .03f,
                Constants.HUD_SCREEN_HEIGHT * .97f - Constants.HUD_SCREEN_WIDTH * .158f);

        Image avatar = new Image(new Util().getAvatar(GamePrefs.getInstance().user.getAvatar()));
        avatar.setSize(Constants.HUD_SCREEN_WIDTH * .158f, Constants.HUD_SCREEN_WIDTH * .158f);
        avatar.setPosition(Constants.HUD_SCREEN_WIDTH * .03f,
                Constants.HUD_SCREEN_HEIGHT * .97f - Constants.HUD_SCREEN_WIDTH * .158f);

        avatar.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                MyGame.mainInstance.setProfileScreen();
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        profile_coin = new Image(Assets.getInstance().profile_coin);
        profile_coin.setSize(Constants.HUD_SCREEN_WIDTH * .228f, Constants.HUD_SCREEN_HEIGHT * .175f);
        profile_coin.setPosition(Constants.HUD_SCREEN_WIDTH * .12f, Constants.HUD_SCREEN_HEIGHT * .734f);

        profile_coin.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                MyGame.mainInstance.setShopScreen();
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        this.mainTable.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(mStage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        mSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        coins_txt = new TextField(new Util().coinsStyle() + "", mSkin);
        coins_txt.setDisabled(true);
        coins_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .228f, Constants.HUD_SCREEN_HEIGHT * .79f);

        coins_txt.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                MyGame.mainInstance.setShopScreen();
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = mSkin.getFont("font21");
        style.fontColor = Color.WHITE;

        Label level_txt = new Label("LEVEL", style);
        level_txt.setWrap(true);
        level_txt.setAlignment(Align.left);
        level_txt.setBounds(Constants.HUD_SCREEN_WIDTH * .066f, Constants.HUD_SCREEN_HEIGHT * .6f,
                Constants.HUD_SCREEN_WIDTH * .1f, Constants.HUD_SCREEN_WIDTH * .1f);

        style.font = mSkin.getFont("default-font");
        Label level = new Label(GamePrefs.getInstance().user.getLevel() + "", style);
        level.setWrap(true);
        level.setAlignment(Align.left);
        level.setBounds(Constants.HUD_SCREEN_WIDTH * .125f, Constants.HUD_SCREEN_HEIGHT * .597f,
                Constants.HUD_SCREEN_WIDTH * .1f, Constants.HUD_SCREEN_WIDTH * .1f);

        ParticleEffect p = new ParticleEffect();
        p.load(Gdx.files.internal("effects/projector.p"), Gdx.files.internal("effects"));
        p.allowCompletion();

        pActor = new ParticleEffectActor[10];

        pActor[0] = new ParticleEffectActor(p);
        pActor[0].setPosition(Constants.HUD_SCREEN_WIDTH * .935f, Constants.HUD_SCREEN_HEIGHT * .253f);

        pActor[1] = new ParticleEffectActor(p);
        pActor[1].setPosition(Constants.HUD_SCREEN_WIDTH * .896f, Constants.HUD_SCREEN_HEIGHT * .585f);

        pActor[2] = new ParticleEffectActor(p);
        pActor[2].setPosition(Constants.HUD_SCREEN_WIDTH * .1962f, Constants.HUD_SCREEN_HEIGHT * .5042f);

        pActor[3] = new ParticleEffectActor(p);
        pActor[3].setPosition(Constants.HUD_SCREEN_WIDTH * .055f, Constants.HUD_SCREEN_HEIGHT * .427f);

        pActor[4] = new ParticleEffectActor(p);
        pActor[4].setPosition(Constants.HUD_SCREEN_WIDTH * .139f, Constants.HUD_SCREEN_HEIGHT * .439f);

        pActor[5] = new ParticleEffectActor(p);
        pActor[5].setPosition(Constants.HUD_SCREEN_WIDTH * .274f, Constants.HUD_SCREEN_HEIGHT * .5f);

        pActor[6] = new ParticleEffectActor(p);
        pActor[6].setPosition(Constants.HUD_SCREEN_WIDTH * .312f, Constants.HUD_SCREEN_HEIGHT * .34f);

        pActor[7] = new ParticleEffectActor(p);
        pActor[7].setPosition(Constants.HUD_SCREEN_WIDTH * .391f, Constants.HUD_SCREEN_HEIGHT * .287f);

        pActor[8] = new ParticleEffectActor(p);
        pActor[8].setPosition(Constants.HUD_SCREEN_WIDTH * .723f, Constants.HUD_SCREEN_HEIGHT * .303f);

        pActor[9] = new ParticleEffectActor(p);
        pActor[9].setPosition(Constants.HUD_SCREEN_WIDTH * .754f, Constants.HUD_SCREEN_HEIGHT * .488f);

        mainTable.addActor(bg);
        mainTable.addActor(arc);
        mainTable.addActor(squadlabel);
        mainTable.addActor(onevone_label);
        mainTable.addActor(item_penalty_light);
        mainTable.addActor(item_penalty_text);
        mainTable.addActor(item_penalty);
        mainTable.addActor(item_bg_2p);
        mainTable.addActor(item_light_2p);
        mainTable.addActor(item_2p);
        mainTable.addActor(item_bg_coach);
        mainTable.addActor(item_light_coach);
        mainTable.addActor(item_coach);
        mainTable.addActor(menu_shop);
        mainTable.addActor(gift);
        mainTable.addActor(menu_settings);
        mainTable.addActor(profile_coin);
        mainTable.addActor(profile_bg);
        mainTable.addActor(sprite);
        mainTable.addActor(avatar);
        mainTable.addActor(coins_txt);
        mainTable.addActor(level_txt);
        mainTable.addActor(level);
        mainTable.addActor(dark_bg);
        for (int i = 0; i < 10; i++) {
            mainTable.addActor(pActor[i]);
        }

        mStage.addActor(this.mainTable);

        item_2p.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                turnLightGame();
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        item_penalty.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                turnLightPenalty();
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        item_coach.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                turnLightCoach();
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        menu_shop.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                MyGame.mainInstance.setShopScreen();
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                menu_shop.setSize(Constants.HUD_SCREEN_WIDTH * .09f, Constants.HUD_SCREEN_WIDTH * .09f);
                menu_shop.setPosition(Constants.HUD_SCREEN_WIDTH * .73f,
                        Constants.HUD_SCREEN_HEIGHT * .745f + Constants.HUD_SCREEN_WIDTH * .01f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                menu_shop.setSize(Constants.HUD_SCREEN_WIDTH * .11f, Constants.HUD_SCREEN_WIDTH * .11f);
                menu_shop.setPosition(Constants.HUD_SCREEN_WIDTH * .72f, Constants.HUD_SCREEN_HEIGHT * .745f);
            }
        });

        gift.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                MyGame.vidAdFlow.LoadVideoAd(MyGame.videoAdFinishHandler);
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gift.setSize(Constants.HUD_SCREEN_WIDTH * .11f * .8f, Constants.HUD_SCREEN_WIDTH * .11f * .8f);
                gift.setPosition(Constants.HUD_SCREEN_WIDTH * .591f, Constants.HUD_SCREEN_HEIGHT * .756f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gift.setSize(Constants.HUD_SCREEN_WIDTH * .11f, Constants.HUD_SCREEN_WIDTH * .11f);
                gift.setPosition(Constants.HUD_SCREEN_WIDTH * .58f, Constants.HUD_SCREEN_HEIGHT * .745f);
            }
        });

        menu_settings.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                MyGame.mainInstance.setSettingScreen();
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                menu_settings.setSize(Constants.HUD_SCREEN_WIDTH * .09f, Constants.HUD_SCREEN_WIDTH * .09f);
                menu_settings.setPosition(Constants.HUD_SCREEN_WIDTH * .86f, Constants.HUD_SCREEN_HEIGHT * .745f + Constants.HUD_SCREEN_WIDTH * .01f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                menu_settings.setSize(Constants.HUD_SCREEN_WIDTH * .11f, Constants.HUD_SCREEN_WIDTH * .11f);
                menu_settings.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .745f);
            }
        });

        if (GamePrefs.getInstance().isMusicOn() == 1) {
            Assets.getInstance().main_theme.play();
            Assets.getInstance().stadium.setVolume(.2f);
            Assets.getInstance().stadium.play();
            Assets.getInstance().main_theme.setLooping(true);
            Assets.getInstance().stadium.setLooping(true);
        }

        Emitter.Listener onCoinListener = new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                JSONObject response = (JSONObject) args[0];
                try {
                    GamePrefs.getInstance().user.setCoins_num(response.getInt("coin"));
                    coins_txt.setText("0");
                    Tween.to(coins_txt, 1, 1.1f)
                            .target(Math.min(GamePrefs.getInstance().user.getCoins_num(), 1000)).ease(TweenEquations.easeOutQuad)
                            .start(mTweenManager).delay(0.0F)
                            .setCallback(new TweenCallback() {
                                public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                                    coins_txt.setText(new Util().coinsStyle());
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };
        ServerTool.getInstance().socket.on("coin", onCoinListener);
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

        Tween.to(arc, 3, 1.1f)
                .target(1f).ease(TweenEquations.easeOutQuad)
                .start(mTweenManager).delay(0.0F);

        int percent = (int) ((float) GamePrefs.getInstance().user.getXp() / ((float) GamePrefs.getInstance().user.getLevel() * 1000f) * 100f);
        Tween.to(sprite, 1, .8f)
                .target(percent).ease(TweenEquations.easeInExpo)
                .start(mTweenManager).delay(0.2F);

        profile_coin.setWidth(0f);
        coins_txt.setText("0");
        coins_txt.setColor(1f, 1f, 1f, 0f);
        Tween.to(profile_coin, 4, 1.3f)
                .target(profile_coin.getX(), profile_coin.getY(),
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

        for (int i = 0; i < 10; i++) {
            pActor[i].start();
        }
        if (GamePrefs.getInstance().getHintStateMain() == 0) {
            showHint();
        }
    }

    private void showHint() {
        final Image help_bg = new Image(Assets.getInstance().help_bg);
        help_bg.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);
        final Image help_box = new Image(Assets.getInstance().help_box);
        help_box.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT * .22f);
        help_box.setPosition(0f, Constants.HUD_SCREEN_HEIGHT * .197f);

        final Image confirm = new Image(Assets.getInstance().help_ic_next);
        confirm.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
        confirm.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .00f);

        final Image skip = new Image(Assets.getInstance().help_ic_skip);
        skip.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
        skip.setPosition(Constants.HUD_SCREEN_WIDTH * .017f, Constants.HUD_SCREEN_HEIGHT * .00f);

        Label.LabelStyle style = new Label.LabelStyle();
        style.fontColor = Color.WHITE;
        style.font = mSkin.getFont("yekan");

        String converted = persian.TransformText(help_texts[help_state]);
        final Label text = new Label(converted, style);
        text.setWrap(true);
        text.setAlignment(Align.center);
        text.setBounds(0, Constants.HUD_SCREEN_HEIGHT * .21f,
                Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT * .22f);

        mainTable.addActor(help_bg);
        mainTable.addActor(help_box);
        mainTable.addActor(text);
        mainTable.addActor(confirm);
        mainTable.addActor(skip);

        confirm.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                help_state++;
                if (help_state > 6) {
                    GamePrefs.getInstance().setHintState(1, 1);
                    mainTable.removeActor(help_bg);
                    mainTable.removeActor(help_box);
                    mainTable.removeActor(text);
                    mainTable.removeActor(confirm);
                    mainTable.removeActor(skip);
                    item_light_2p.setRotation(item_light_2p.getRotation() % 360);
                    if (GamePrefs.getInstance().isEffectOn() == 1) {
                        Assets.getInstance().click.play();
                    }
                    Tween.to(item_light_2p, 6, .5f)
                            .target(item_light_2p.getRotation() + 360).ease(TweenEquations.easeInOutExpo)
                            .start(mTweenManager).delay(0f)
                            .setCallback(new TweenCallback() {
                                public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                                    MyGame.mainInstance.setHelpGameSelectScreen();
                                }
                            });
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

        skip.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                GamePrefs.getInstance().setHintState(1, 1);
                GamePrefs.getInstance().setHintState(2, 1);
                GamePrefs.getInstance().setHintState(3, 1);
                mainTable.removeActor(help_bg);
                mainTable.removeActor(help_box);
                mainTable.removeActor(text);
                mainTable.removeActor(confirm);
                mainTable.removeActor(skip);
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                skip.setSize(Constants.HUD_SCREEN_WIDTH * .133f * .8f, Constants.HUD_SCREEN_HEIGHT * .186f * .8f);
                skip.setPosition(Constants.HUD_SCREEN_WIDTH * .0303f, Constants.HUD_SCREEN_HEIGHT * .0186f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                skip.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
                skip.setPosition(Constants.HUD_SCREEN_WIDTH * .017f, Constants.HUD_SCREEN_HEIGHT * .00f);
            }
        });
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

    private void turnLightCoach() {
        item_light_2p.setRotation(item_light_coach.getRotation() % 360);
        if (GamePrefs.getInstance().isEffectOn() == 1) {
            Assets.getInstance().click.play();
        }
        Tween.to(item_light_coach, 6, .5f)
                .target(item_light_coach.getRotation() + 360).ease(TweenEquations.easeInOutExpo)
                .start(mTweenManager).delay(0f)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                        MyGame.mainInstance.setCoachingScreen();
                    }
                });
    }

    private void turnLightPenalty() {
        item_penalty_light.setRotation(item_penalty_light.getRotation() % 360);
        if (GamePrefs.getInstance().isEffectOn() == 1) {
            Assets.getInstance().click.play();
        }
        Tween.to(item_penalty_light, 6, .5f)
                .target(item_penalty_light.getRotation() + 360).ease(TweenEquations.easeInOutExpo)
                .start(mTweenManager).delay(0f)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                        MyGame.mainInstance.setPenaltySelectScreen();
                    }
                });
    }

    private void turnLightGame() {
        item_light_2p.setRotation(item_light_2p.getRotation() % 360);
        if (GamePrefs.getInstance().isEffectOn() == 1) {
            Assets.getInstance().click.play();
        }
        Tween.to(item_light_2p, 6, .5f)
                .target(item_light_2p.getRotation() + 360).ease(TweenEquations.easeInOutExpo)
                .start(mTweenManager).delay(0f)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                        MyGame.mainInstance.setGameSelectScreen();
                    }
                });
    }

    @Override
    public void render(float delta) {
        // Set the viewport to the whole screen.
        if (MyGame.isVideoFinished) {
            MyGame.isVideoFinished = false;
            videoFinished();
        }
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Restore the stage's viewport.
        mStage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        mStage.act(delta);
        mStage.draw();
    }

    private void videoFinished() {
        ServerTool.getInstance().freeCoin();
        ServerTool.getInstance().getCoin();
    }

    @Override
    public void resize(int width, int height) {
        float aspectRatio = (float) width / height;
        mMainCamera.viewportWidth = Constants.HUD_SCREEN_HEIGHT * aspectRatio;
        mMainCamera.update();
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
        ServerTool.getInstance().socket.off("coin");
    }

    public void removePopups() {
        for (Actor actor : mStage.getActors()) {
            if (actor.getName() != null) {
                if (actor.getName().equals(this.popup_string)) {
                    actor.remove();
                }
            }
        }
        dark_bg.setPosition(0, Constants.HUD_SCREEN_HEIGHT * -1);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK) {
            Popups popup = new Popups(Constants.HUD_SCREEN_WIDTH * .61f, Constants.HUD_SCREEN_HEIGHT * .8f,
                    screen, "Are you sure you want to exit?");
            popup.setSize(Constants.HUD_SCREEN_WIDTH * .61f, Constants.HUD_SCREEN_HEIGHT * .8f);
            popup.setPosition(Constants.HUD_SCREEN_WIDTH * .195f, Constants.HUD_SCREEN_HEIGHT * .1f);
            popup.setName(popup_string);
            mStage.addActor(popup);
            popup.setColor(popup.getColor().r, popup.getColor().g, popup.getColor().b, 0);
            Tween.to(popup, 5, .1f)
                    .target(1).ease(TweenEquations.easeInBack)
                    .start(mTweenManager).delay(0.0F)
                    .setCallback(new TweenCallback() {
                        public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {

                        }
                    });
            dark_bg.setColor(bg.getColor().r, bg.getColor().g, bg.getColor().b, 0);
            dark_bg.setPosition(0, 0);
            Tween.to(dark_bg, 3, 1.5f)
                    .target(1)
                    .ease(TweenEquations.easeOutExpo)
                    .start(mTweenManager).delay(0.0F);
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
