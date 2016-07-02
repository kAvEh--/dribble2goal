package ir.eynajgroup.dribble2goal.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.Random;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import io.socket.emitter.Emitter;
import ir.eynajgroup.dribble2goal.Assets;
import ir.eynajgroup.dribble2goal.Constants;
import ir.eynajgroup.dribble2goal.GamePrefs;
import ir.eynajgroup.dribble2goal.MyGame;
import ir.eynajgroup.dribble2goal.Server.ServerTool;
import ir.eynajgroup.dribble2goal.Util.Util;

/**
 * Created by kAvEh on 3/8/2016.
 */
public class CoachScreen implements Screen {

    TweenManager mTweenManager;
    private OrthographicCamera mMainCamera;
    SpriteBatch mMainBatch;
    Image bg;
    Image setting_formation;
    Image position_left;
    Image position_right;
    Image[] players;
    Image selected;
    Image p_stamina;
    Image p_size;
    Image p_speed;
    Image stamina_shop;
    Image size_shop;
    Image speed_shop;
    Image coins;
    Image confirm;
    Image back;
    TextField coins_txt;
    Image[][] abilities;
    int[] lineup;

    int selected_item;

    int new_position;

    Image item_bg;
    Stage mStage;
    Table mainTable;
    Skin mSkin;
    Random random;

    public CoachScreen() {
        mTweenManager = MyGame.mTweenManager;
        mMainBatch = new SpriteBatch();

        random = new Random();

        mSkin = new Skin(Gdx.files.internal("data/uiskin.json"));

        mStage = new Stage(new FitViewport(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT), mMainBatch);
        mainTable = new Table();

        bg = new Image(Assets.getInstance().main_bg);
        bg.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

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
                Vector2[] position = new Util().getSettingPosition(new_position);
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
                Vector2[] position = new Util().getSettingPosition(new_position);
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
        players[0] = new Image(new Util().getShirt(GamePrefs.getInstance().shirt));
        players[1] = new Image(new Util().getShirt(GamePrefs.getInstance().shirt));
        players[2] = new Image(new Util().getShirt(GamePrefs.getInstance().shirt));
        players[3] = new Image(new Util().getShirt(GamePrefs.getInstance().shirt));
        players[4] = new Image(new Util().getShirt(GamePrefs.getInstance().shirt));

        selected = new Image(Assets.getInstance().selected_player);
        selected.setSize(Constants.HUD_SCREEN_WIDTH * .06f, Constants.HUD_SCREEN_HEIGHT * .107f);

        final Vector2[] position = new Util().getSettingPosition(GamePrefs.getInstance().position);
        setPosition(position);
        lineup = GamePrefs.getInstance().lineup.clone();

        players[0].setSize(Constants.HUD_SCREEN_WIDTH * .09f, Constants.HUD_SCREEN_HEIGHT * .16f);
        players[1].setSize(Constants.HUD_SCREEN_WIDTH * .09f, Constants.HUD_SCREEN_HEIGHT * .16f);
        players[2].setSize(Constants.HUD_SCREEN_WIDTH * .09f, Constants.HUD_SCREEN_HEIGHT * .16f);
        players[3].setSize(Constants.HUD_SCREEN_WIDTH * .09f, Constants.HUD_SCREEN_HEIGHT * .16f);
        players[4].setSize(Constants.HUD_SCREEN_WIDTH * .09f, Constants.HUD_SCREEN_HEIGHT * .16f);

        players[0].addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                Tween.to(selected, 1, .3f)
                        .target(players[0].getX() + Constants.HUD_SCREEN_WIDTH * .015f,
                                players[0].getY() + Constants.HUD_SCREEN_HEIGHT * .032f)
                        .ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F);

                setSelection(0);
            }
            
            public boolean longPress(Actor actor, float x, float y) {
                if (selected_item != 0) {
                    boolean isSelectedonBench;
                    boolean isClickedonBench;
                    isSelectedonBench = lineup[3] == selected_item ||
                            lineup[4] == selected_item;
                    isClickedonBench = lineup[3] == 0 ||
                            lineup[4] == 0;
                    if (isSelectedonBench) {
                        if (isClickedonBench) {
                            return false;
                        } else {
                            changeMyPlayer(0, selected_item);
                        }
                    } else {
                        if (isClickedonBench) {
                            changeMyPlayer(selected_item, 0);
                        } else {
                            return false;
                        }
                    }
                }
                return true;
            }
        });

        players[1].addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                Tween.to(selected, 1, .3f)
                        .target(players[1].getX() + Constants.HUD_SCREEN_WIDTH * .015f,
                                players[1].getY() + Constants.HUD_SCREEN_HEIGHT * .032f)
                        .ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F);

                setSelection(1);
            }

            public boolean longPress(Actor actor, float x, float y) {
                if (selected_item != 1) {
                    boolean isSelectedonBench;
                    boolean isClickedonBench;
                    isSelectedonBench = lineup[3] == selected_item ||
                            lineup[4] == selected_item;
                    isClickedonBench = lineup[3] == 1 ||
                            lineup[4] == 1;
                    if (isSelectedonBench) {
                        if (isClickedonBench) {
                            return false;
                        } else {
                            changeMyPlayer(1, selected_item);
                        }
                    } else {
                        if (isClickedonBench) {
                            changeMyPlayer(selected_item, 1);
                        } else {
                            return false;
                        }
                    }
                }
                return true;
            }
        });

        players[2].addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                Tween.to(selected, 1, .3f)
                        .target(players[2].getX() + Constants.HUD_SCREEN_WIDTH * .015f,
                                players[2].getY() + Constants.HUD_SCREEN_HEIGHT * .032f)
                        .ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F);

                setSelection(2);
            }

            public boolean longPress(Actor actor, float x, float y) {
                if (selected_item != 2) {
                    boolean isSelectedonBench;
                    boolean isClickedonBench;
                    isSelectedonBench = lineup[3] == selected_item ||
                            lineup[4] == selected_item;
                    isClickedonBench = lineup[3] == 2 ||
                            lineup[4] == 2;
                    if (isSelectedonBench) {
                        if (isClickedonBench) {
                            return false;
                        } else {
                            changeMyPlayer(2, selected_item);
                        }
                    } else {
                        if (isClickedonBench) {
                            changeMyPlayer(selected_item, 2);
                        } else {
                            return false;
                        }
                    }
                }
                return true;
            }
        });

        players[3].addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                Tween.to(selected, 1, .3f)
                        .target(players[3].getX() + Constants.HUD_SCREEN_WIDTH * .015f,
                                players[3].getY() + Constants.HUD_SCREEN_HEIGHT * .032f)
                        .ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F);

                setSelection(3);
            }
            
            public boolean longPress(Actor actor, float x, float y) {
                if (selected_item != 3) {
                    boolean isSelectedonBench;
                    boolean isClickedonBench;
                    isSelectedonBench = lineup[3] == selected_item ||
                            lineup[4] == selected_item;
                    isClickedonBench = lineup[3] == 3 ||
                            lineup[4] == 3;
                    if (isSelectedonBench) {
                        if (isClickedonBench) {
                            return false;
                        } else {
                            changeMyPlayer(3, selected_item);
                        }
                    } else {
                        if (isClickedonBench) {
                            changeMyPlayer(selected_item, 3);
                        } else {
                            return false;
                        }
                    }
                }
                return true;
            }
        });

        players[4].addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                Tween.to(selected, 1, .3f)
                        .target(players[4].getX() + Constants.HUD_SCREEN_WIDTH * .015f,
                                players[4].getY() + Constants.HUD_SCREEN_HEIGHT * .032f)
                        .ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F);

                setSelection(4);
            }

            public boolean longPress(Actor actor, float x, float y) {
                if (selected_item != 4) {
                    boolean isSelectedonBench;
                    boolean isClickedonBench;
                    isSelectedonBench = lineup[3] == selected_item ||
                            lineup[4] == selected_item;
                    isClickedonBench = lineup[3] == 4 ||
                            lineup[4] == 4;
                    if (isSelectedonBench) {
                        if (isClickedonBench) {
                            return false;
                        } else {
                            changeMyPlayer(4, selected_item);
                        }
                    } else {
                        if (isClickedonBench) {
                            changeMyPlayer(selected_item, 4);
                        } else {
                            return false;
                        }
                    }
                }
                return true;
            }
        });

        abilities = new Image[3][5];

        p_stamina = new Image(Assets.getInstance().player_stamina);
        p_stamina.setSize(Constants.HUD_SCREEN_WIDTH * .418f, Constants.HUD_SCREEN_HEIGHT * .140f);
        p_stamina.setPosition(Constants.HUD_SCREEN_WIDTH * .53f, Constants.HUD_SCREEN_HEIGHT * .8f);
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
                ServerTool.getInstance().sendUpgrade(selected_item + 1, "stamina", GamePrefs.getInstance().players[selected_item][0] + 1);
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
                ServerTool.getInstance().sendUpgrade(selected_item + 1, "size", GamePrefs.getInstance().players[selected_item][1] + 1);
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
                ServerTool.getInstance().sendUpgrade(selected_item + 1, "speed", GamePrefs.getInstance().players[selected_item][2] + 1);
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
        coins.setPosition(Constants.HUD_SCREEN_WIDTH * .465f, Constants.HUD_SCREEN_HEIGHT * .04f);

        confirm = new Image(Assets.getInstance().icon_ok);
        confirm.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
        confirm.setPosition(Constants.HUD_SCREEN_WIDTH * .73f, Constants.HUD_SCREEN_HEIGHT * .04f);

        back = new Image(Assets.getInstance().icon_back);
        back.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
        back.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .04f);

        coins_txt = new TextField("0", mSkin);
        coins_txt.setColor(.6f, .3f, .5f, 1f);
        coins_txt.setDisabled(true);
        coins_txt.setPosition(Constants.HUD_SCREEN_WIDTH * .57f, Constants.HUD_SCREEN_HEIGHT * .087f);

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
                //TODO set new settings
                if (new_position != GamePrefs.getInstance().position)
                    GamePrefs.getInstance().position = new_position;

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
        mainTable.addActor(players[0]);
        mainTable.addActor(players[1]);
        mainTable.addActor(players[2]);
        mainTable.addActor(players[3]);
        mainTable.addActor(players[4]);
        mainTable.addActor(selected);
        mainTable.addActor(p_stamina);
        mainTable.addActor(p_size);
        mainTable.addActor(p_speed);
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

        this.mainTable.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);
        Gdx.input.setInputProcessor(mStage);

        mStage.addActor(this.mainTable);

        ServerTool.getInstance().socket.on("notEnoughCoin", onNotEnoughCoinListener);
        ServerTool.getInstance().socket.on("upgradeOk", onUpgradeOKListener);
    }

    void setSelection(int selected) {
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
                break;
            case 2:
                setAttrs(GamePrefs.getInstance().players[2][0], GamePrefs.getInstance().players[2][1], GamePrefs.getInstance().players[2][2]);
                break;
            case 3:
                setAttrs(GamePrefs.getInstance().players[3][0], GamePrefs.getInstance().players[3][1], GamePrefs.getInstance().players[3][2]);
                break;
            case 4:
                setAttrs(GamePrefs.getInstance().players[4][0], GamePrefs.getInstance().players[4][1], GamePrefs.getInstance().players[4][2]);
                break;
        }
    }

    private void setShopButtons(int stamina, int speed, int size) {
        switch (stamina) {
            case 0:
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
        }
        switch (size) {
            case 0:
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
        }
        switch (speed) {
            case 0:
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
        }
    }

    void setAttrs(int stamina, int speed, int size) {
        if (stamina > 0) {
            for (int i = 0; i < stamina; i++) {
                Tween.to(abilities[0][i], 3, .2f)
                        .target(1)
                        .ease(TweenEquations.easeInCubic)
                        .start(mTweenManager).delay(.2F * i);
            }
        }
        if (speed > 0) {
            for (int i = 0; i < speed; i++) {
                Tween.to(abilities[1][i], 3, .2f)
                        .target(1)
                        .ease(TweenEquations.easeInBounce)
                        .start(mTweenManager).delay(.2F * i);
            }
        }
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                Tween.to(abilities[2][i], 3, .2f)
                        .target(1)
                        .ease(TweenEquations.easeInBounce)
                        .start(mTweenManager).delay(.2F * i);
            }
        }
    }

    void setPosition(Vector2[] position) {
        Tween.to(selected, 1, .3f)
                .target(position[selected_item].x + Constants.HUD_SCREEN_WIDTH * .015f,
                        position[selected_item].y + Constants.HUD_SCREEN_HEIGHT * .032f)
                .ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(0.0F);

        Tween.to(players[0], 1, .3f)
                .target(position[0].x, position[0].y)
                .ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(0.0F);

        Tween.to(players[1], 1, .3f)
                .target(position[1].x, position[1].y)
                .ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(0.0F);

        Tween.to(players[2], 1, .3f)
                .target(position[2].x, position[2].y)
                .ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(0.0F);

        Tween.to(players[3], 1, .3f)
                .target(position[3].x, position[3].y)
                .ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(0.0F);

        Tween.to(players[4], 1, .3f)
                .target(position[4].x, position[4].y)
                .ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(0.0F);
    }

    private void changeMyPlayer(int out, int in) {
        //TODO play animation of substitution
        Vector2 p_out = new Vector2(players[out].getX(), players[out].getY());
        Vector2 p_in = new Vector2(players[in].getX(), players[in].getY());

        Tween.to(players[out], 1, .3f)
                .target(p_in.x,
                        p_in.y)
                .ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(0.0F);

        Tween.to(players[in], 1, .3f)
                .target(p_out.x,
                        p_out.y)
                .ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(0.0F)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                        Tween.to(selected, 1, .3f)
                                .target(players[selected_item].getX() + Constants.HUD_SCREEN_WIDTH * .015f,
                                        players[selected_item].getY() + Constants.HUD_SCREEN_HEIGHT * .032f)
                                .ease(TweenEquations.easeInBack)
                                .start(mTweenManager).delay(0.0F);
                    }
                });

        int out_index, in_index;
        if (lineup[0] == out) {
            out_index = 0;
        }
        else if (lineup[1] == out) {
            out_index = 1;
        }
        else {
            out_index = 2;
        }
        if (lineup[3] == in) {
            in_index = 3;
        }
        else {
            in_index = 4;
        }
        lineup[out_index] = in;
        lineup[in_index] = out;
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
        setSelection(0);
    }

    @Override
    public void render(float delta) {
        // Set the viewport to the whole screen.
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

//        mMainBatch.setProjectionMatrix(mMainCamera.combined);
//
//        mMainBatch.begin();
//
//        mMainBatch.draw(background, -Constants.SCREEN_WIDTH / 2, -Constants.SCREEN_HEIGHT / 2,
//                Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
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

        ServerTool.getInstance().socket.off("notEnoughCoin");
        ServerTool.getInstance().socket.off("upgradeOk");
    }

    private Emitter.Listener onNotEnoughCoinListener = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            JSONObject response = (JSONObject) args[0];
            try {
                System.out.println(response + "^^^^^^^^^^^^^^^^");
            } catch (Exception e) {

            }
        }

    };

    private Emitter.Listener onUpgradeOKListener = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            JSONObject response = (JSONObject) args[0];
            try {
                System.out.println(response + "^^^^^^^^^^^^^^^^");
            } catch (Exception e) {

            }
        }

    };
}

