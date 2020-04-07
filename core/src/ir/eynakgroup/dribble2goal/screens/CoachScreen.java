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
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Random;

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
import ir.eynakgroup.dribble2goal.Server.ServerTool;
import ir.eynakgroup.dribble2goal.Util.PersianDecoder;
import ir.eynakgroup.dribble2goal.Util.Popups;
import ir.eynakgroup.dribble2goal.Util.Util;

/**
 * Created by kAvEh on 3/8/2016.
 */
public class CoachScreen implements Screen, InputProcessor {

    TweenManager mTweenManager;
    private OrthographicCamera mMainCamera;
    SpriteBatch mMainBatch;
    CoachScreen screen;

    Image bg;
    Image setting_formation;
    Image position_left;
    Image position_right;
    Image[] players;
    Image[] players_bg;
    Image selected;
    Image p_stamina;
    Image p_size;
    Image p_speed;
    Image p_stamina_text;
    Image p_speed_text;
    Image p_size_text;
    Image stamina_shop;
    Image size_shop;
    Image speed_shop;
    Image coins;
    Image confirm;
    Image back;
    TextField coins_txt;
    Image[][] abilities;
    int[] lineup;
    Image dark_bg;

    int selected_item;

    int new_position;
    Vector2[] position;

    Image item_bg;
    Stage mStage;
    Table mainTable;
    Skin mSkin;
    Random random;

    int tmp_z_index;
    int tmp_z_index_bg;

    Popups popup;

    final private String popup_string = "popup";

    private String[] help_texts = new String[]{"اینجا می\u200Cتونی ترکیب تیمت رو عوض کنی.", "اینجا می\u200Cتونی استقامت بازیکنات رو زیاد کنی.", "اینجا می\u200Cتونی قدرت بازیکنات رو زیاد کنی.", "اینجا می\u200Cتونی سرعت بازیکنات رو زیاد کنی."};
    private int help_state = 0;

    public CoachScreen() {
        mTweenManager = MyGame.mTweenManager;
        mMainBatch = new SpriteBatch();
        screen = this;

        random = new Random();

        mSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        mStage = new Stage(new FitViewport(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT), mMainBatch);
        mainTable = new Table();

        bg = new Image(Assets.getInstance().main_bg);
        bg.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        dark_bg = new Image(Assets.getInstance().dark_bg);
        dark_bg.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);
        dark_bg.setPosition(0, Constants.HUD_SCREEN_HEIGHT * -1);

        item_bg = new Image(Assets.getInstance().main_item_bg);
        item_bg.setSize(Constants.HUD_SCREEN_WIDTH * .28f, Constants.HUD_SCREEN_WIDTH * .28f);
        item_bg.setPosition(Constants.HUD_SCREEN_WIDTH * .17f, Constants.HUD_SCREEN_HEIGHT * .15f);

        setting_formation = new Image(Assets.getInstance().setting_formation);
        setting_formation.setSize(Constants.HUD_SCREEN_WIDTH * .336f, Constants.HUD_SCREEN_HEIGHT);
        setting_formation.setPosition(Constants.HUD_SCREEN_WIDTH * .06f, 0);

        position_left = new Image(Assets.getInstance().arrow_left);
        position_left.setSize(Constants.HUD_SCREEN_WIDTH * .085f, Constants.HUD_SCREEN_HEIGHT * .119f);
        position_left.setPosition(Constants.HUD_SCREEN_WIDTH * .02f, Constants.HUD_SCREEN_HEIGHT * .465f);

        position_right = new Image(Assets.getInstance().arrow_right);
        position_right.setSize(Constants.HUD_SCREEN_WIDTH * .085f, Constants.HUD_SCREEN_HEIGHT * .119f);
        position_right.setPosition(Constants.HUD_SCREEN_WIDTH * .355f, Constants.HUD_SCREEN_HEIGHT * .465f);

        new_position = GamePrefs.getInstance().position;

        position_left.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                if (new_position == 1) {
                    new_position = GamePrefs.getInstance().position_num;
                } else {
                    new_position -= 1;
                }
                position = new Util().getSettingPosition(new_position);
                setPosition(position);
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                position_left.setSize(Constants.HUD_SCREEN_WIDTH * .069f, Constants.HUD_SCREEN_HEIGHT * .0966f);
                position_left.setPosition(Constants.HUD_SCREEN_WIDTH * .028f, Constants.HUD_SCREEN_HEIGHT * .4762f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                position_left.setSize(Constants.HUD_SCREEN_WIDTH * .085f, Constants.HUD_SCREEN_HEIGHT * .119f);
                position_left.setPosition(Constants.HUD_SCREEN_WIDTH * .02f, Constants.HUD_SCREEN_HEIGHT * .465f);
            }
        });

        position_right.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                if (new_position == GamePrefs.getInstance().position_num) {
                    new_position = 1;
                } else {
                    new_position += 1;
                }
                position = new Util().getSettingPosition(new_position);
                setPosition(position);
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                position_right.setSize(Constants.HUD_SCREEN_WIDTH * .069f, Constants.HUD_SCREEN_HEIGHT * .0966f);
                position_right.setPosition(Constants.HUD_SCREEN_WIDTH * .363f, Constants.HUD_SCREEN_HEIGHT * .4762f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                position_right.setSize(Constants.HUD_SCREEN_WIDTH * .085f, Constants.HUD_SCREEN_HEIGHT * .119f);
                position_right.setPosition(Constants.HUD_SCREEN_WIDTH * .355f, Constants.HUD_SCREEN_HEIGHT * .465f);
            }
        });

        players = new Image[5];
        players[0] = new Image(new Util().getShirt(GamePrefs.getInstance().shirt + 1));
        players[1] = new Image(new Util().getShirt(GamePrefs.getInstance().shirt + 1));
        players[2] = new Image(new Util().getShirt(GamePrefs.getInstance().shirt + 1));
        players[3] = new Image(new Util().getShirt(GamePrefs.getInstance().shirt + 1));
        players[4] = new Image(new Util().getShirt(GamePrefs.getInstance().shirt + 1));

        players_bg = new Image[5];
        for (int i = 0; i < 5; i++) {
            players_bg[i] = new Image(Assets.getInstance().player_stable[i]);
        }

        selected = new Image(Assets.getInstance().selected_player);
        selected.setSize(Constants.HUD_SCREEN_WIDTH * .06f * 1.3f, Constants.HUD_SCREEN_HEIGHT * .107f * 1.3f);

        position = new Util().getSettingPosition(GamePrefs.getInstance().position);
        lineup = GamePrefs.getInstance().lineup.clone();
        setPosition(position);

        setSubsListeners();

        for (int i = 0; i < 5; i++) {
            players[i].setSize(Constants.HUD_SCREEN_WIDTH * .09f, Constants.HUD_SCREEN_HEIGHT * .16f);
            players_bg[i].setSize(Constants.HUD_SCREEN_WIDTH * .09f, Constants.HUD_SCREEN_HEIGHT * .16f);
        }

        players[0].addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                Tween.to(selected, 1, .3f)
                        .target(players[0].getX() + Constants.HUD_SCREEN_WIDTH * .0055f,
                                players[0].getY() + Constants.HUD_SCREEN_HEIGHT * .0128f)
                        .ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F);

                setSelection(0);
            }
        });

        players[1].addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                Tween.to(selected, 1, .3f)
                        .target(players[1].getX() + Constants.HUD_SCREEN_WIDTH * .0055f,
                                players[1].getY() + Constants.HUD_SCREEN_HEIGHT * .0128f)
                        .ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F);

                setSelection(1);
            }
        });

        players[2].addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                Tween.to(selected, 1, .3f)
                        .target(players[2].getX() + Constants.HUD_SCREEN_WIDTH * .0055f,
                                players[2].getY() + Constants.HUD_SCREEN_HEIGHT * .0128f)
                        .ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F);

                setSelection(2);
            }
        });

        players[3].addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                Tween.to(selected, 1, .3f)
                        .target(players[3].getX() + Constants.HUD_SCREEN_WIDTH * .0055f,
                                players[3].getY() + Constants.HUD_SCREEN_HEIGHT * .0128f)
                        .ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F);

                setSelection(3);
            }
        });

        players[4].addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                Tween.to(selected, 1, .3f)
                        .target(players[4].getX() + Constants.HUD_SCREEN_WIDTH * .0055f,
                                players[4].getY() + Constants.HUD_SCREEN_HEIGHT * .0128f)
                        .ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F);

                setSelection(4);
            }
        });

        abilities = new Image[3][5];

        p_stamina = new Image(Assets.getInstance().player_stamina);
        p_stamina.setSize(Constants.HUD_SCREEN_WIDTH * .418f, Constants.HUD_SCREEN_HEIGHT * .140f);
        p_stamina.setPosition(Constants.HUD_SCREEN_WIDTH * .53f, Constants.HUD_SCREEN_HEIGHT * .8f);

        p_stamina_text = new Image(Assets.getInstance().player_stamina_text);
        p_stamina_text.setSize(Constants.HUD_SCREEN_WIDTH * .1625f, Constants.HUD_SCREEN_HEIGHT * .063f);
        p_stamina_text.setPosition(Constants.HUD_SCREEN_WIDTH * .55f, Constants.HUD_SCREEN_HEIGHT * .73f);

        for (int i = 0; i < 5; i++) {
            abilities[0][i] = new Image(Assets.getInstance().setting_dot);
            abilities[0][i].setSize(Constants.HUD_SCREEN_HEIGHT * .07f, Constants.HUD_SCREEN_HEIGHT * .07f);
            abilities[0][i].setPosition(Constants.HUD_SCREEN_WIDTH * .639f + (i * Constants.HUD_SCREEN_WIDTH * .0343f),
                    Constants.HUD_SCREEN_HEIGHT * .835f);
        }
        stamina_shop = new Image(Assets.getInstance().setting_shop_1);
        stamina_shop.setSize(Constants.HUD_SCREEN_WIDTH * .12f, Constants.HUD_SCREEN_HEIGHT * .140f);
        stamina_shop.setPosition(Constants.HUD_SCREEN_WIDTH * .835f, Constants.HUD_SCREEN_HEIGHT * .792f);
        stamina_shop.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                popup = new Popups(Constants.HUD_SCREEN_WIDTH * .61f, Constants.HUD_SCREEN_HEIGHT * .8f,
                        screen, selected_item + 1, "stamina", GamePrefs.getInstance().players[selected_item][0] + 1);
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

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                stamina_shop.setSize(Constants.HUD_SCREEN_WIDTH * .12f * .8f, Constants.HUD_SCREEN_HEIGHT * .140f * .8f);
                stamina_shop.setPosition(Constants.HUD_SCREEN_WIDTH * .847f, Constants.HUD_SCREEN_HEIGHT * .806f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                stamina_shop.setSize(Constants.HUD_SCREEN_WIDTH * .12f, Constants.HUD_SCREEN_HEIGHT * .140f);
                stamina_shop.setPosition(Constants.HUD_SCREEN_WIDTH * .835f, Constants.HUD_SCREEN_HEIGHT * .792f);
            }
        });

        p_size = new Image(Assets.getInstance().player_size);
        p_size.setSize(Constants.HUD_SCREEN_WIDTH * .418f, Constants.HUD_SCREEN_HEIGHT * .140f);
        p_size.setPosition(Constants.HUD_SCREEN_WIDTH * .53f, Constants.HUD_SCREEN_HEIGHT * .56f);

        p_size_text = new Image(Assets.getInstance().player_size_text);
        p_size_text.setSize(Constants.HUD_SCREEN_WIDTH * .1536f, Constants.HUD_SCREEN_HEIGHT * .063f);
        p_size_text.setPosition(Constants.HUD_SCREEN_WIDTH * .55f, Constants.HUD_SCREEN_HEIGHT * .49f);

        for (int i = 0; i < 5; i++) {
            abilities[1][i] = new Image(Assets.getInstance().setting_dot);
            abilities[1][i].setSize(Constants.HUD_SCREEN_HEIGHT * .07f, Constants.HUD_SCREEN_HEIGHT * .07f);
            abilities[1][i].setPosition(Constants.HUD_SCREEN_WIDTH * .639f + (i * Constants.HUD_SCREEN_WIDTH * .0343f),
                    Constants.HUD_SCREEN_HEIGHT * .595f);
        }
        size_shop = new Image(Assets.getInstance().setting_shop_1);
        size_shop.setSize(Constants.HUD_SCREEN_WIDTH * .12f, Constants.HUD_SCREEN_HEIGHT * .140f);
        size_shop.setPosition(Constants.HUD_SCREEN_WIDTH * .835f, Constants.HUD_SCREEN_HEIGHT * .552f);
        size_shop.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                popup = new Popups(Constants.HUD_SCREEN_WIDTH * .61f, Constants.HUD_SCREEN_HEIGHT * .8f,
                        screen, selected_item + 1, "size", GamePrefs.getInstance().players[selected_item][1] + 1);
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

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                size_shop.setSize(Constants.HUD_SCREEN_WIDTH * .12f * .8f, Constants.HUD_SCREEN_HEIGHT * .140f * .8f);
                size_shop.setPosition(Constants.HUD_SCREEN_WIDTH * .847f, Constants.HUD_SCREEN_HEIGHT * .566f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                size_shop.setSize(Constants.HUD_SCREEN_WIDTH * .12f, Constants.HUD_SCREEN_HEIGHT * .140f);
                size_shop.setPosition(Constants.HUD_SCREEN_WIDTH * .835f, Constants.HUD_SCREEN_HEIGHT * .552f);
            }
        });

        p_speed = new Image(Assets.getInstance().player_speed);
        p_speed.setSize(Constants.HUD_SCREEN_WIDTH * .418f, Constants.HUD_SCREEN_HEIGHT * .140f);
        p_speed.setPosition(Constants.HUD_SCREEN_WIDTH * .53f, Constants.HUD_SCREEN_HEIGHT * .32f);

        p_speed_text = new Image(Assets.getInstance().player_speed_text);
        p_speed_text.setSize(Constants.HUD_SCREEN_WIDTH * .139f, Constants.HUD_SCREEN_HEIGHT * .063f);
        p_speed_text.setPosition(Constants.HUD_SCREEN_WIDTH * .55f, Constants.HUD_SCREEN_HEIGHT * .25f);

        for (int i = 0; i < 5; i++) {
            abilities[2][i] = new Image(Assets.getInstance().setting_dot);
            abilities[2][i].setSize(Constants.HUD_SCREEN_HEIGHT * .07f, Constants.HUD_SCREEN_HEIGHT * .07f);
            abilities[2][i].setPosition(Constants.HUD_SCREEN_WIDTH * .639f + (i * Constants.HUD_SCREEN_WIDTH * .0343f),
                    Constants.HUD_SCREEN_HEIGHT * .355f);
        }
        speed_shop = new Image(Assets.getInstance().setting_shop_1);
        speed_shop.setSize(Constants.HUD_SCREEN_WIDTH * .12f, Constants.HUD_SCREEN_HEIGHT * .140f);
        speed_shop.setPosition(Constants.HUD_SCREEN_WIDTH * .835f, Constants.HUD_SCREEN_HEIGHT * .312f);
        speed_shop.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                popup = new Popups(Constants.HUD_SCREEN_WIDTH * .61f, Constants.HUD_SCREEN_HEIGHT * .8f,
                        screen, selected_item + 1, "speed", GamePrefs.getInstance().players[selected_item][2] + 1);
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

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                speed_shop.setSize(Constants.HUD_SCREEN_WIDTH * .12f * .8f, Constants.HUD_SCREEN_HEIGHT * .140f * .8f);
                speed_shop.setPosition(Constants.HUD_SCREEN_WIDTH * .847f, Constants.HUD_SCREEN_HEIGHT * .326f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                speed_shop.setSize(Constants.HUD_SCREEN_WIDTH * .12f, Constants.HUD_SCREEN_HEIGHT * .140f);
                speed_shop.setPosition(Constants.HUD_SCREEN_WIDTH * .835f, Constants.HUD_SCREEN_HEIGHT * .312f);
            }
        });

        coins = new Image(Assets.getInstance().setting_coin);
        coins.setSize(Constants.HUD_SCREEN_WIDTH * .25f, Constants.HUD_SCREEN_HEIGHT * .186f);
        coins.setPosition(Constants.HUD_SCREEN_WIDTH * .5f, Constants.HUD_SCREEN_HEIGHT * .04f);

        confirm = new Image(Assets.getInstance().icon_ok);
        confirm.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
        confirm.setPosition(Constants.HUD_SCREEN_WIDTH * .73f, Constants.HUD_SCREEN_HEIGHT * .04f);

        back = new Image(Assets.getInstance().icon_back);
        back.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
        back.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .04f);

        coins_txt = new TextField("0", mSkin);
        coins_txt.setColor(.6f, .3f, .5f, 1f);
        coins_txt.setDisabled(true);
        coins_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .62f, Constants.HUD_SCREEN_HEIGHT * .095f);

        coins.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                MyGame.mainInstance.setShopScreen();
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        coins_txt.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                MyGame.mainInstance.setShopScreen();
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

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

        confirm.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                if (!Arrays.equals(GamePrefs.getInstance().lineup, lineup) || new_position != GamePrefs.getInstance().position) {
                    GamePrefs.getInstance().lineup = lineup;
                    GamePrefs.getInstance().position = new_position;
                    ServerTool.getInstance().sendLineUp(GamePrefs.getInstance().position, GamePrefs.getInstance().lineup);
                }

                MyGame.mainInstance.setMainScreen();
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                confirm.setSize(Constants.HUD_SCREEN_WIDTH * .133f * .8f, Constants.HUD_SCREEN_HEIGHT * .186f * .8f);
                confirm.setPosition(Constants.HUD_SCREEN_WIDTH * .743f, Constants.HUD_SCREEN_HEIGHT * .06f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                confirm.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
                confirm.setPosition(Constants.HUD_SCREEN_WIDTH * .73f, Constants.HUD_SCREEN_HEIGHT * .04f);
            }
        });

        mainTable.addActor(bg);
        mainTable.addActor(setting_formation);
        mainTable.addActor(position_left);
        mainTable.addActor(position_right);
        mainTable.addActor(selected);
        for (int i = 0; i < 5; i++) {
            mainTable.addActor(players_bg[i]);
        }
        for (int i = 0; i < 5; i++) {
            mainTable.addActor(players[i]);
        }
        mainTable.addActor(p_stamina);
        mainTable.addActor(p_size);
        mainTable.addActor(p_speed);
        mainTable.addActor(p_stamina_text);
        mainTable.addActor(p_size_text);
        mainTable.addActor(p_speed_text);
        mainTable.addActor(coins);
        mainTable.addActor(confirm);
        mainTable.addActor(back);
        mainTable.addActor(coins_txt);
        mainTable.addActor(stamina_shop);
        mainTable.addActor(size_shop);
        mainTable.addActor(speed_shop);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                abilities[i][j].setColor(255, 255, 255, 0);
                mainTable.addActor(abilities[i][j]);
            }
        }
        mainTable.addActor(dark_bg);

        this.mainTable.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(mStage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        mStage.addActor(this.mainTable);

        Emitter.Listener onNotEnoughCoinListener = new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                JSONObject response = (JSONObject) args[0];
                System.out.println(response + "^^^^^^^^^^^^^^^^");
                try {
                    response.getString("err");
                    setAttrs(GamePrefs.getInstance().players[selected_item][0], GamePrefs.getInstance().players[selected_item][1], GamePrefs.getInstance().players[selected_item][2]);
                    setShopButtons(GamePrefs.getInstance().players[selected_item][0], GamePrefs.getInstance().players[selected_item][1], GamePrefs.getInstance().players[selected_item][2]);

                } catch (Exception ignored) {

                }
            }

        };
        ServerTool.getInstance().socket.on("notEnoughCoin", onNotEnoughCoinListener);
        Emitter.Listener onUpgradeOKListener = new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                JSONObject response = (JSONObject) args[0];
                try {
                    System.out.println(response + "^^^^^^^^^^^^^^^^");
                } catch (Exception ignored) {

                }
            }

        };
        ServerTool.getInstance().socket.on("upgradeOk", onUpgradeOKListener);
        Emitter.Listener onCoinListener = new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                final JSONObject response = (JSONObject) args[0];
                try {
                    String s = response.getString("err");
                    System.out.println(s);
                } catch (Exception e) {
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
//                        MyGame.mainInstance.setMainScreen();
                            try {
                                GamePrefs.getInstance().coins_num = response.getInt("coin");
                                if (coinFlag) {
                                    coinFlag = false;
                                    coins_txt.setText(0 + "");
                                    Tween.to(coins_txt, 1, .7f)
                                            .target(Math.min(GamePrefs.getInstance().coins_num, 1000)).ease(TweenEquations.easeOutQuad)
                                            .start(mTweenManager).delay(0.0F)
                                            .setCallback(new TweenCallback() {
                                                public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                                                    coins_txt.setText(new Util().coinsStyle());
                                                    coinFlag = true;
                                                }
                                            });
                                }
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
                }
            }
        };
        ServerTool.getInstance().socket.on("coin", onCoinListener);
    }

    public void removePopups(boolean isOK) {
        for (Actor actor : mStage.getActors()) {
            if (actor.getName() != null) {
                if (actor.getName().equals(this.popup_string)) {
                    actor.remove();
                }
            }
        }
        dark_bg.setPosition(0, Constants.HUD_SCREEN_HEIGHT * -1);
        if (isOK) {
            setSelection(selected_item);
            ServerTool.getInstance().getCoin();
        }
    }

    private void setSelection(int selected) {
        selected_item = selected;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                abilities[i][j].setColor(255, 255, 255, 0);
            }
        }
        switch (selected_item) {
            case 0:
                setAttrs(GamePrefs.getInstance().players[0][0], GamePrefs.getInstance().players[0][1], GamePrefs.getInstance().players[0][2]);
                setShopButtons(GamePrefs.getInstance().players[0][0], GamePrefs.getInstance().players[0][1], GamePrefs.getInstance().players[0][2]);
                break;
            case 1:
                setAttrs(GamePrefs.getInstance().players[1][0], GamePrefs.getInstance().players[1][1], GamePrefs.getInstance().players[1][2]);
                setShopButtons(GamePrefs.getInstance().players[1][0], GamePrefs.getInstance().players[1][1], GamePrefs.getInstance().players[1][2]);
                break;
            case 2:
                setAttrs(GamePrefs.getInstance().players[2][0], GamePrefs.getInstance().players[2][1], GamePrefs.getInstance().players[2][2]);
                setShopButtons(GamePrefs.getInstance().players[2][0], GamePrefs.getInstance().players[2][1], GamePrefs.getInstance().players[2][2]);
                break;
            case 3:
                setAttrs(GamePrefs.getInstance().players[3][0], GamePrefs.getInstance().players[3][1], GamePrefs.getInstance().players[3][2]);
                setShopButtons(GamePrefs.getInstance().players[3][0], GamePrefs.getInstance().players[3][1], GamePrefs.getInstance().players[3][2]);
                break;
            case 4:
                setAttrs(GamePrefs.getInstance().players[4][0], GamePrefs.getInstance().players[4][1], GamePrefs.getInstance().players[4][2]);
                setShopButtons(GamePrefs.getInstance().players[4][0], GamePrefs.getInstance().players[4][1], GamePrefs.getInstance().players[4][2]);
                break;
        }
    }

    private void setShopButtons(int stamina, int size, int speed) {
        stamina_shop.setVisible(true);
        size_shop.setVisible(true);
        speed_shop.setVisible(true);
        switch (stamina) {
            case 0:
                stamina_shop.setDrawable(new SpriteDrawable(new Sprite(Assets.getInstance().setting_shop_1)));
                break;
            case 1:
                stamina_shop.setDrawable(new SpriteDrawable(new Sprite(Assets.getInstance().setting_shop_2)));
                break;
            case 2:
                stamina_shop.setDrawable(new SpriteDrawable(new Sprite(Assets.getInstance().setting_shop_3)));
                break;
            case 3:
                stamina_shop.setDrawable(new SpriteDrawable(new Sprite(Assets.getInstance().setting_shop_4)));
                break;
            case 4:
                stamina_shop.setDrawable(new SpriteDrawable(new Sprite(Assets.getInstance().setting_shop_5)));
                break;
            default:
                stamina_shop.setVisible(false);
                break;
        }
        switch (size) {
            case 0:
                size_shop.setDrawable(new SpriteDrawable(new Sprite(Assets.getInstance().setting_shop_1)));
                break;
            case 1:
                size_shop.setDrawable(new SpriteDrawable(new Sprite(Assets.getInstance().setting_shop_2)));
                break;
            case 2:
                size_shop.setDrawable(new SpriteDrawable(new Sprite(Assets.getInstance().setting_shop_3)));
                break;
            case 3:
                size_shop.setDrawable(new SpriteDrawable(new Sprite(Assets.getInstance().setting_shop_4)));
                break;
            case 4:
                size_shop.setDrawable(new SpriteDrawable(new Sprite(Assets.getInstance().setting_shop_5)));
                break;
            default:
                size_shop.setVisible(false);
                break;
        }
        switch (speed) {
            case 0:
                speed_shop.setDrawable(new SpriteDrawable(new Sprite(Assets.getInstance().setting_shop_1)));
                break;
            case 1:
                speed_shop.setDrawable(new SpriteDrawable(new Sprite(Assets.getInstance().setting_shop_2)));
                break;
            case 2:
                speed_shop.setDrawable(new SpriteDrawable(new Sprite(Assets.getInstance().setting_shop_3)));
                break;
            case 3:
                speed_shop.setDrawable(new SpriteDrawable(new Sprite(Assets.getInstance().setting_shop_4)));
                break;
            case 4:
                speed_shop.setDrawable(new SpriteDrawable(new Sprite(Assets.getInstance().setting_shop_5)));
                break;
            default:
                speed_shop.setVisible(false);
                break;
        }
    }

    private void setAttrs(int stamina, int speed, int size) {
        for (int i = 0; i < stamina; i++) {
            Tween.to(abilities[0][i], 3, .2f)
                    .target(1)
                    .ease(TweenEquations.easeInCubic)
                    .start(mTweenManager).delay(.2F * i);
        }
        for (int i = stamina; i < 5; i++) {
            Tween.to(abilities[0][i], 3, .2f)
                    .target(0)
                    .ease(TweenEquations.easeInCubic)
                    .start(mTweenManager).delay(.2F * i);
        }
        for (int i = 0; i < speed; i++) {
            Tween.to(abilities[1][i], 3, .2f)
                    .target(1)
                    .ease(TweenEquations.easeInBounce)
                    .start(mTweenManager).delay(.2F * i);
        }
        for (int i = speed; i < 5; i++) {
            Tween.to(abilities[1][i], 3, .2f)
                    .target(0)
                    .ease(TweenEquations.easeInBounce)
                    .start(mTweenManager).delay(.2F * i);
        }
        for (int i = 0; i < size; i++) {
            Tween.to(abilities[2][i], 3, .2f)
                    .target(1)
                    .ease(TweenEquations.easeInBounce)
                    .start(mTweenManager).delay(.2F * i);
        }
        for (int i = size; i < 5; i++) {
            Tween.to(abilities[2][i], 3, .2f)
                    .target(0)
                    .ease(TweenEquations.easeInBounce)
                    .start(mTweenManager).delay(.2F * i);
        }
    }

    void setPosition(final Vector2[] position) {
        for (int i = 0; i < 4; i++) {
            Tween.to(players[lineup[i]], 1, .3f)
                    .target(position[i].x, position[i].y)
                    .ease(TweenEquations.easeInBack)
                    .start(mTweenManager).delay(0.0F);

            Tween.to(players_bg[lineup[i]], 1, .3f)
                    .target(position[i].x, position[i].y)
                    .ease(TweenEquations.easeInBack)
                    .start(mTweenManager).delay(0.0F);
        }

        Tween.to(players[lineup[4]], 1, .3f)
                .target(position[4].x, position[4].y)
                .ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(0.0F)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                        Tween.to(selected, 1, .3f)
                                .target(players[selected_item].getX() + Constants.HUD_SCREEN_WIDTH * .0055f,
                                        players[selected_item].getY() + Constants.HUD_SCREEN_HEIGHT * .0128f)
                                .ease(TweenEquations.easeInBack)
                                .start(mTweenManager).delay(0.0F);
                    }
                });

        Tween.to(players_bg[lineup[4]], 1, .3f)
                .target(position[4].x, position[4].y)
                .ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(0.0F);
    }

    private void changeMyPlayer(int out, int in) {
        int out_index = 0, in_index = 0;
        for (int i = 0; i < 5; i++) {
            if (lineup[i] == out)
                out_index = i;
            else if (lineup[i] == in)
                in_index = i;
        }
        lineup[out_index] = in;
        lineup[in_index] = out;

        setPosition(position);
    }

    private void setSubsListeners() {
        players[0].addListener(new DragListener() {
            public void drag(InputEvent event, float x, float y, int pointer) {
                tmp_z_index = players[0].getZIndex();
                tmp_z_index_bg = players_bg[0].getZIndex();
                players_bg[0].setZIndex(900);
                players[0].setZIndex(1000);
                players_bg[0].moveBy(x - players_bg[0].getWidth() / 2, y - players_bg[0].getHeight() / 2);
                players[0].moveBy(x - players[0].getWidth() / 2, y - players[0].getHeight() / 2);
            }

            public void dragStop(InputEvent event, float x, float y, int pointer) {
                if (players[0].getX() > players[1].getX() - players[1].getWidth() / 4 &&
                        players[0].getX() < players[1].getX() + players[1].getWidth() / 4 &&
                        players[0].getY() > players[1].getY() - players[1].getHeight() / 4 &&
                        players[0].getY() < players[1].getY() + players[1].getHeight() / 4) {
                    changeMyPlayer(0, 1);
                } else if (players[0].getX() > players[2].getX() - players[2].getWidth() / 4 &&
                        players[0].getX() < players[2].getX() + players[2].getWidth() / 4 &&
                        players[0].getY() > players[2].getY() - players[2].getHeight() / 4 &&
                        players[0].getY() < players[2].getY() + players[2].getHeight() / 4) {
                    changeMyPlayer(0, 2);
                } else if (players[0].getX() > players[3].getX() - players[3].getWidth() / 4 &&
                        players[0].getX() < players[3].getX() + players[3].getWidth() / 4 &&
                        players[0].getY() > players[3].getY() - players[3].getHeight() / 4 &&
                        players[0].getY() < players[3].getY() + players[3].getHeight() / 4) {
                    changeMyPlayer(0, 3);
                } else if (players[0].getX() > players[4].getX() - players[4].getWidth() / 4 &&
                        players[0].getX() < players[4].getX() + players[4].getWidth() / 4 &&
                        players[0].getY() > players[4].getY() - players[4].getHeight() / 4 &&
                        players[0].getY() < players[4].getY() + players[4].getHeight() / 4) {
                    changeMyPlayer(0, 4);
                } else {
                    setPosition(position);
                }
                players_bg[0].setZIndex(tmp_z_index_bg);
                players[0].setZIndex(tmp_z_index);
            }
        });
        players[1].addListener(new DragListener() {
            public void drag(InputEvent event, float x, float y, int pointer) {
                tmp_z_index = players[1].getZIndex();
                tmp_z_index_bg = players_bg[1].getZIndex();
                players_bg[1].setZIndex(900);
                players[1].setZIndex(1000);
                players_bg[1].moveBy(x - players_bg[1].getWidth() / 2, y - players_bg[1].getHeight() / 2);
                players[1].moveBy(x - players[1].getWidth() / 2, y - players[1].getHeight() / 2);
            }

            public void dragStop(InputEvent event, float x, float y, int pointer) {
                if (players[1].getX() > players[0].getX() - players[0].getWidth() / 4 &&
                        players[1].getX() < players[0].getX() + players[0].getWidth() / 4 &&
                        players[1].getY() > players[0].getY() - players[0].getHeight() / 4 &&
                        players[1].getY() < players[0].getY() + players[0].getHeight() / 4) {
                    changeMyPlayer(1, 0);
                } else if (players[1].getX() > players[2].getX() - players[2].getWidth() / 4 &&
                        players[1].getX() < players[2].getX() + players[2].getWidth() / 4 &&
                        players[1].getY() > players[2].getY() - players[2].getHeight() / 4 &&
                        players[1].getY() < players[2].getY() + players[2].getHeight() / 4) {
                    changeMyPlayer(1, 2);
                } else if (players[1].getX() > players[3].getX() - players[3].getWidth() / 4 &&
                        players[1].getX() < players[3].getX() + players[3].getWidth() / 4 &&
                        players[1].getY() > players[3].getY() - players[3].getHeight() / 4 &&
                        players[1].getY() < players[3].getY() + players[3].getHeight() / 4) {
                    changeMyPlayer(1, 3);
                } else if (players[1].getX() > players[4].getX() - players[4].getWidth() / 4 &&
                        players[1].getX() < players[4].getX() + players[4].getWidth() / 4 &&
                        players[1].getY() > players[4].getY() - players[4].getHeight() / 4 &&
                        players[1].getY() < players[4].getY() + players[4].getHeight() / 4) {
                    changeMyPlayer(1, 4);
                } else {
                    setPosition(position);
                }
                players_bg[1].setZIndex(tmp_z_index_bg);
                players[1].setZIndex(tmp_z_index);
            }
        });
        players[2].addListener(new DragListener() {
            public void drag(InputEvent event, float x, float y, int pointer) {
                tmp_z_index = players[2].getZIndex();
                tmp_z_index_bg = players_bg[2].getZIndex();
                players_bg[2].setZIndex(900);
                players[2].setZIndex(1000);
                players_bg[2].moveBy(x - players_bg[2].getWidth() / 2, y - players_bg[2].getHeight() / 2);
                players[2].moveBy(x - players[2].getWidth() / 2, y - players[2].getHeight() / 2);
            }

            public void dragStop(InputEvent event, float x, float y, int pointer) {
                if (players[2].getX() > players[1].getX() - players[1].getWidth() / 4 &&
                        players[2].getX() < players[1].getX() + players[1].getWidth() / 4 &&
                        players[2].getY() > players[1].getY() - players[1].getHeight() / 4 &&
                        players[2].getY() < players[1].getY() + players[1].getHeight() / 4) {
                    changeMyPlayer(2, 1);
                } else if (players[2].getX() > players[0].getX() - players[0].getWidth() / 4 &&
                        players[2].getX() < players[0].getX() + players[0].getWidth() / 4 &&
                        players[2].getY() > players[0].getY() - players[0].getHeight() / 4 &&
                        players[2].getY() < players[0].getY() + players[0].getHeight() / 4) {
                    changeMyPlayer(2, 0);
                } else if (players[2].getX() > players[3].getX() - players[3].getWidth() / 4 &&
                        players[2].getX() < players[3].getX() + players[3].getWidth() / 4 &&
                        players[2].getY() > players[3].getY() - players[3].getHeight() / 4 &&
                        players[2].getY() < players[3].getY() + players[3].getHeight() / 4) {
                    changeMyPlayer(2, 3);
                } else if (players[2].getX() > players[4].getX() - players[4].getWidth() / 4 &&
                        players[2].getX() < players[4].getX() + players[4].getWidth() / 4 &&
                        players[2].getY() > players[4].getY() - players[4].getHeight() / 4 &&
                        players[2].getY() < players[4].getY() + players[4].getHeight() / 4) {
                    changeMyPlayer(2, 4);
                } else {
                    setPosition(position);
                }
                players_bg[2].setZIndex(tmp_z_index_bg);
                players[2].setZIndex(tmp_z_index);
            }
        });
        players[3].addListener(new DragListener() {
            public void drag(InputEvent event, float x, float y, int pointer) {
                tmp_z_index = players[3].getZIndex();
                tmp_z_index_bg = players_bg[3].getZIndex();
                players_bg[3].setZIndex(900);
                players[3].setZIndex(1000);
                players_bg[3].moveBy(x - players_bg[3].getWidth() / 2, y - players_bg[3].getHeight() / 2);
                players[3].moveBy(x - players[3].getWidth() / 2, y - players[3].getHeight() / 2);
            }

            public void dragStop(InputEvent event, float x, float y, int pointer) {
                if (players[3].getX() > players[1].getX() - players[1].getWidth() / 4 &&
                        players[3].getX() < players[1].getX() + players[1].getWidth() / 4 &&
                        players[3].getY() > players[1].getY() - players[1].getHeight() / 4 &&
                        players[3].getY() < players[1].getY() + players[1].getHeight() / 4) {
                    changeMyPlayer(3, 1);
                } else if (players[3].getX() > players[2].getX() - players[2].getWidth() / 4 &&
                        players[3].getX() < players[2].getX() + players[2].getWidth() / 4 &&
                        players[3].getY() > players[2].getY() - players[2].getHeight() / 4 &&
                        players[3].getY() < players[2].getY() + players[2].getHeight() / 4) {
                    changeMyPlayer(3, 2);
                } else if (players[3].getX() > players[0].getX() - players[0].getWidth() / 4 &&
                        players[3].getX() < players[0].getX() + players[0].getWidth() / 4 &&
                        players[3].getY() > players[0].getY() - players[0].getHeight() / 4 &&
                        players[3].getY() < players[0].getY() + players[0].getHeight() / 4) {
                    changeMyPlayer(3, 0);
                } else if (players[3].getX() > players[4].getX() - players[4].getWidth() / 4 &&
                        players[3].getX() < players[4].getX() + players[4].getWidth() / 4 &&
                        players[3].getY() > players[4].getY() - players[4].getHeight() / 4 &&
                        players[3].getY() < players[4].getY() + players[4].getHeight() / 4) {
                    changeMyPlayer(3, 4);
                } else {
                    setPosition(position);
                }
                players_bg[3].setZIndex(tmp_z_index_bg);
                players[3].setZIndex(tmp_z_index);
            }
        });
        players[4].addListener(new DragListener() {
            public void drag(InputEvent event, float x, float y, int pointer) {
                tmp_z_index = players[4].getZIndex();
                tmp_z_index_bg = players_bg[4].getZIndex();
                players_bg[4].setZIndex(900);
                players[4].setZIndex(1000);
                players_bg[4].moveBy(x - players_bg[4].getWidth() / 2, y - players_bg[4].getHeight() / 2);
                players[4].moveBy(x - players[4].getWidth() / 2, y - players[4].getHeight() / 2);
            }

            public void dragStop(InputEvent event, float x, float y, int pointer) {
                if (players[4].getX() > players[1].getX() - players[1].getWidth() / 4 &&
                        players[4].getX() < players[1].getX() + players[1].getWidth() / 4 &&
                        players[4].getY() > players[1].getY() - players[1].getHeight() / 4 &&
                        players[4].getY() < players[1].getY() + players[1].getHeight() / 4) {
                    changeMyPlayer(4, 1);
                } else if (players[4].getX() > players[2].getX() - players[2].getWidth() / 4 &&
                        players[4].getX() < players[2].getX() + players[2].getWidth() / 4 &&
                        players[4].getY() > players[2].getY() - players[2].getHeight() / 4 &&
                        players[4].getY() < players[2].getY() + players[2].getHeight() / 4) {
                    changeMyPlayer(4, 2);
                } else if (players[4].getX() > players[3].getX() - players[3].getWidth() / 4 &&
                        players[4].getX() < players[3].getX() + players[3].getWidth() / 4 &&
                        players[4].getY() > players[3].getY() - players[3].getHeight() / 4 &&
                        players[4].getY() < players[3].getY() + players[3].getHeight() / 4) {
                    changeMyPlayer(4, 3);
                } else if (players[4].getX() > players[0].getX() - players[0].getWidth() / 4 &&
                        players[4].getX() < players[0].getX() + players[0].getWidth() / 4 &&
                        players[4].getY() > players[0].getY() - players[0].getHeight() / 4 &&
                        players[4].getY() < players[0].getY() + players[0].getHeight() / 4) {
                    changeMyPlayer(4, 0);
                } else {
                    setPosition(position);
                }
                players_bg[4].setZIndex(tmp_z_index_bg);
                players[4].setZIndex(tmp_z_index);
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

        coins_txt.setText("0");
        Tween.to(coins_txt, 1, .7f)
                .target(Math.min(GamePrefs.getInstance().coins_num, 1000)).ease(TweenEquations.easeOutQuad)
                .start(mTweenManager).delay(0.0F)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                        coins_txt.setText(new Util().coinsStyle());
                    }
                });
        setSelection(0);
        //TODO
//        GamePrefs.getInstance().setHintState(1);
//        if (GamePrefs.getInstance().getHintState() == 1) {
//            showHint();
//        }
    }

    private void showHint() {
        final Image help_bg = new Image(Assets.getInstance().help_bg);
        help_bg.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);
        help_bg.setPosition(Constants.HUD_SCREEN_WIDTH * .5f, 0f);
        Image help_box = new Image(Assets.getInstance().help_box);
        help_box.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT * .22f);

        final Image confirm = new Image(Assets.getInstance().help_ic_next);
        confirm.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
        confirm.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .00f);

        final Image skip = new Image(Assets.getInstance().help_ic_skip);
        skip.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
        skip.setPosition(Constants.HUD_SCREEN_WIDTH * .017f, Constants.HUD_SCREEN_HEIGHT * .00f);

        Label.LabelStyle style = new Label.LabelStyle();
        style.fontColor = Color.WHITE;
        style.font = mSkin.getFont("yekan");

        final PersianDecoder persian = new PersianDecoder();
        String converted = persian.TransformText(help_texts[help_state]);
        final Label text = new Label(converted, style);

        text.setWrap(true);
        text.setAlignment(Align.center);
        text.setBounds(0, Constants.HUD_SCREEN_HEIGHT * .02f,
                Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT * .22f);

        mainTable.addActor(help_bg);
        mainTable.addActor(help_box);
        mainTable.addActor(text);
        mainTable.addActor(confirm);
        mainTable.addActor(skip);

        confirm.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                help_state++;
                if (help_state > 3) {
                    //TODO
//                    GamePrefs.getInstance().setHintState(1);
                    MyGame.mainInstance.setHelpGameSelectScreen();
                    return;
                }
                changeTextHelp(text, persian.TransformText(help_texts[help_state]));
                switch (help_state) {
                    case 1:
                        help_bg.setPosition(0f, 0f);
                        help_bg.setDrawable(new TextureRegionDrawable(new TextureRegion(Assets.getInstance().help_bg_5)));
                        break;
                    case 2:
                        help_bg.setDrawable(new TextureRegionDrawable(new TextureRegion(Assets.getInstance().help_bg_6)));
                        break;
                    default:
                        help_bg.setDrawable(new TextureRegionDrawable(new TextureRegion(Assets.getInstance().help_bg_7)));
                        break;
                }
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
                //TODO
//                GamePrefs.getInstance().setHintState(1);

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

    private boolean coinFlag = true;

    @Override
    public void render(float delta) {
        // Set the viewport to the whole screen.
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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

        ServerTool.getInstance().socket.off("notEnoughCoin");
        ServerTool.getInstance().socket.off("upgradeOk");
        ServerTool.getInstance().socket.off("coin");
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

