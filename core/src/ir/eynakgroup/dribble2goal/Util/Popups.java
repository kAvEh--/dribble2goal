package ir.eynakgroup.dribble2goal.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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

import ir.eynakgroup.dribble2goal.Assets;
import ir.eynakgroup.dribble2goal.Constants;
import ir.eynakgroup.dribble2goal.GamePrefs;
import ir.eynakgroup.dribble2goal.MyGame;
import ir.eynakgroup.dribble2goal.Server.ServerTool;
import ir.eynakgroup.dribble2goal.screens.CoachScreen;
import ir.eynakgroup.dribble2goal.screens.GameScreen;
import ir.eynakgroup.dribble2goal.screens.MainMenuScreen;
import ir.eynakgroup.dribble2goal.screens.SettingScreen;

/**
 * Created by Eynak_PC2 on 7/11/2016.
 *
 */
public class Popups extends Table {

    private Image back;
    private Image confirm;
    private Label text;
    private Skin mSkin;
    private float width;
    private float height;
    private Stage mStage;
    final private String popup_string = "popup";

    Popups(final ShopScrollPane shopPane, float w, float h, final int price, final int shirtNum, Stage st, final Image dark_bg) {
        width = w;
        height = h;
        mStage = st;

        Image bg = new Image(Assets.getInstance().popup_bg);
        bg.setSize(width, height);

        back = new Image(Assets.getInstance().icon_back);
        back.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
        back.setPosition(width * .5f, height * .2f);

        confirm = new Image(Assets.getInstance().icon_ok);
        confirm.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
        confirm.setPosition(width * .265f, height * .2f);

        mSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = mSkin.getFont("default-font");
        style.fontColor = Color.WHITE;

        text = new Label("", style);
        text.setWrap(true);
        text.setAlignment(Align.center);
        text.setBounds(Constants.HUD_SCREEN_WIDTH * .05f, Constants.HUD_SCREEN_HEIGHT * .3f,
                width * .8f, height * .3f);

        if (price == -1) {
            text.setText("Do you want to buy this shirt for 2000 tomans?");
        } else {
            text.setText("Do you want to buy this shirt for " + price + " coins?");
        }

        back.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                for (Actor actor : mStage.getActors()) {
                    if (actor.getName() != null) {
                        if (actor.getName().equals(popup_string)) {
                            actor.remove();
                        }
                    }
                }
                dark_bg.setPosition(0, Constants.HUD_SCREEN_HEIGHT * -1);
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                back.setSize(Constants.HUD_SCREEN_WIDTH * .133f * .8f, Constants.HUD_SCREEN_HEIGHT * .186f * .8f);
                back.setPosition(width * .5f + Constants.HUD_SCREEN_WIDTH * .0133f, height * .2f + Constants.HUD_SCREEN_HEIGHT * .0186f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                back.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
                back.setPosition(width * .5f, height * .2f);
            }
        });

        confirm.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                if (price == -1) {
                    if (shirtNum == 21) {
                        MyGame.purchaseFinish.shopholder = shopPane;
                        MyGame.purchaseFlow.StartPurchaseFlow(Constants.SKU_SHIRT_PERS, MyGame.purchaseFinish);
                    } else if (shirtNum == 22) {
                        MyGame.purchaseFinish.shopholder = shopPane;
                        MyGame.purchaseFlow.StartPurchaseFlow(Constants.SKU_SHIRT_EST, MyGame.purchaseFinish);
                    } else if (shirtNum == 23) {
                        MyGame.purchaseFinish.shopholder = shopPane;
                        MyGame.purchaseFlow.StartPurchaseFlow(Constants.SKU_SHIRT_BARCA, MyGame.purchaseFinish);
                    } else {
                        MyGame.purchaseFinish.shopholder = shopPane;
                        MyGame.purchaseFlow.StartPurchaseFlow(Constants.SKU_SHIRT_REAL, MyGame.purchaseFinish);
                    }
                } else {
                    shopPane.setSelectedShare(shirtNum - 1);
                    ServerTool.getInstance().sendShop(price, shirtNum, "", "");
                    GamePrefs.getInstance().coins_num -= price;
                    GamePrefs.getInstance().shirt = shirtNum;
                    ServerTool.getInstance().getCoin();
                }
                for (Actor actor : mStage.getActors()) {
                    if (actor.getName() != null) {
                        if (actor.getName().equals(popup_string)) {
                            actor.remove();
                        }
                    }
                }
                dark_bg.setPosition(0, Constants.HUD_SCREEN_HEIGHT * -1);
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                confirm.setSize(Constants.HUD_SCREEN_WIDTH * .133f * .8f, Constants.HUD_SCREEN_HEIGHT * .186f * .8f);
                confirm.setPosition(width * .265f + Constants.HUD_SCREEN_WIDTH * .0133f, height * .2f + Constants.HUD_SCREEN_HEIGHT * .0186f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                confirm.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
                confirm.setPosition(width * .265f, height * .2f);
            }
        });

        addActor(bg);
        addActor(text);
        addActor(back);
        addActor(confirm);
    }

    public Popups(float w, float h, MainMenuScreen scr, String txt) {
        width = w;
        height = h;
        final MainMenuScreen screen = scr;

        Image bg = new Image(Assets.getInstance().popup_bg);
        bg.setSize(width, height);

        back = new Image(Assets.getInstance().icon_back);
        back.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
        back.setPosition(width * .5f, height * .2f);

        confirm = new Image(Assets.getInstance().icon_ok);
        confirm.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
        confirm.setPosition(width * .265f, height * .2f);

        mSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = mSkin.getFont("default-font");
        style.fontColor = Color.WHITE;

        text = new Label("", style);
        text.setWrap(true);
        text.setAlignment(Align.center);
        text.setBounds(Constants.HUD_SCREEN_WIDTH * .05f, Constants.HUD_SCREEN_HEIGHT * .3f,
                width * .8f, height * .3f);

        text.setText(txt);

        back.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                screen.removePopups();
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                back.setSize(Constants.HUD_SCREEN_WIDTH * .133f * .8f, Constants.HUD_SCREEN_HEIGHT * .186f * .8f);
                back.setPosition(width * .5f + Constants.HUD_SCREEN_WIDTH * .0133f, height * .2f + Constants.HUD_SCREEN_HEIGHT * .0186f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                back.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
                back.setPosition(width * .5f, height * .2f);
            }
        });

        confirm.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                Gdx.app.exit();
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                confirm.setSize(Constants.HUD_SCREEN_WIDTH * .133f * .8f, Constants.HUD_SCREEN_HEIGHT * .186f * .8f);
                confirm.setPosition(width * .265f + Constants.HUD_SCREEN_WIDTH * .0133f, height * .2f + Constants.HUD_SCREEN_HEIGHT * .0186f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                confirm.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
                confirm.setPosition(width * .265f, height * .2f);
            }
        });

        addActor(bg);
        addActor(text);
        addActor(back);
        addActor(confirm);
    }

    public Popups(float w, float h, GameScreen scr, String txt) {
        width = w;
        height = h;
        final GameScreen screen = scr;

        Image bg = new Image(Assets.getInstance().popup_bg);
        bg.setSize(width, height);

        back = new Image(Assets.getInstance().icon_back);
        back.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
        back.setPosition(width * .5f, height * .2f);

        confirm = new Image(Assets.getInstance().icon_ok);
        confirm.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
        confirm.setPosition(width * .265f, height * .2f);

        mSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = mSkin.getFont("default-font");
        style.fontColor = Color.WHITE;

        text = new Label("", style);
        text.setWrap(true);
        text.setAlignment(Align.center);
        text.setBounds(Constants.HUD_SCREEN_WIDTH * .05f, Constants.HUD_SCREEN_HEIGHT * .3f,
                width * .8f, height * .3f);

        text.setText(txt);

        back.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                screen.removePopups();
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                back.setSize(Constants.HUD_SCREEN_WIDTH * .133f * .8f, Constants.HUD_SCREEN_HEIGHT * .186f * .8f);
                back.setPosition(width * .5f + Constants.HUD_SCREEN_WIDTH * .0133f, height * .2f + Constants.HUD_SCREEN_HEIGHT * .0186f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                back.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
                back.setPosition(width * .5f, height * .2f);
            }
        });

        confirm.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                GamePrefs.getInstance().game_played += 1;
                GamePrefs.getInstance().win_percent = GamePrefs.getInstance().game_won / GamePrefs.getInstance().game_played;
                ServerTool.getInstance().resignMatch();
                Assets.getInstance().stadium.stop();
                MyGame.mainInstance.setMainScreen();
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                confirm.setSize(Constants.HUD_SCREEN_WIDTH * .133f * .8f, Constants.HUD_SCREEN_HEIGHT * .186f * .8f);
                confirm.setPosition(width * .265f + Constants.HUD_SCREEN_WIDTH * .0133f, height * .2f + Constants.HUD_SCREEN_HEIGHT * .0186f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                confirm.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
                confirm.setPosition(width * .265f, height * .2f);
            }
        });

        addActor(bg);
        addActor(text);
        addActor(back);
        addActor(confirm);
    }

    public Popups(float w, float h, SettingScreen scr, final Stage mStage) {
        width = w;
        height = h;
        final SettingScreen screen = scr;

        Image bg = new Image(Assets.getInstance().popup_bg);
        bg.setSize(width, height);

        back = new Image(Assets.getInstance().icon_back);
        back.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
        back.setPosition(width * .05f, height * .3f);

        confirm = new Image(Assets.getInstance().icon_ok);
        confirm.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
        confirm.setPosition(width * .81f, height * .3f);

        Image nameBg = new Image(Assets.getInstance().login_username);
        nameBg.setSize(Constants.HUD_SCREEN_WIDTH * .445f, Constants.HUD_SCREEN_HEIGHT * .283f);
        nameBg.setPosition(width * .2775f, height * .15f);

        mSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = mSkin.getFont("default-font");
        style.fontColor = Color.WHITE;

        final TextField userName_txt = new TextField(GamePrefs.getInstance().name, mSkin);
        userName_txt.setSize(Constants.HUD_SCREEN_WIDTH * .5f, Constants.HUD_SCREEN_HEIGHT * .1f);
        userName_txt.setPosition(width * .42f, height * .42f);
        userName_txt.setAlignment(Align.left);
        userName_txt.getOnscreenKeyboard().show(true);
        userName_txt.setMaxLength(35);

        back.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                mStage.unfocusAll();
                Gdx.input.setOnscreenKeyboardVisible(false);
                screen.removePopups();
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                back.setSize(Constants.HUD_SCREEN_WIDTH * .133f * .8f, Constants.HUD_SCREEN_HEIGHT * .186f * .8f);
                back.setPosition(width * .05f + Constants.HUD_SCREEN_WIDTH * .0133f, height * .3f + Constants.HUD_SCREEN_HEIGHT * .0186f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                back.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
                back.setPosition(width * .05f, height * .3f);
            }
        });

        confirm.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                GamePrefs.getInstance().name = userName_txt.getText();
                ServerTool.getInstance().setNickName(userName_txt.getText());
                mStage.unfocusAll();
                Gdx.input.setOnscreenKeyboardVisible(false);
                screen.removePopups();
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                confirm.setSize(Constants.HUD_SCREEN_WIDTH * .133f * .8f, Constants.HUD_SCREEN_HEIGHT * .186f * .8f);
                confirm.setPosition(width * .81f + Constants.HUD_SCREEN_WIDTH * .0133f, height * .3f + Constants.HUD_SCREEN_HEIGHT * .0186f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                confirm.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
                confirm.setPosition(width * .81f, height * .3f);
            }
        });

        addActor(nameBg);
        addActor(userName_txt);
        addActor(back);
        addActor(confirm);
        mStage.setKeyboardFocus(userName_txt);
    }

    public Popups(float w, float h, CoachScreen scr, final int selected_item, final String type, final int upgradeTo) {
        width = w;
        height = h;
        final CoachScreen screen = scr;

        Image bg = new Image(Assets.getInstance().popup_bg);
        bg.setSize(width, height);

        back = new Image(Assets.getInstance().icon_back);
        back.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
        back.setPosition(width * .5f, height * .2f);

        confirm = new Image(Assets.getInstance().icon_ok);
        confirm.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
        confirm.setPosition(width * .265f, height * .2f);

        mSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = mSkin.getFont("default-font");
        style.fontColor = Color.WHITE;

        text = new Label("", style);
        text.setWrap(true);
        text.setAlignment(Align.center);
        text.setBounds(Constants.HUD_SCREEN_WIDTH * .05f, Constants.HUD_SCREEN_HEIGHT * .3f,
                width * .8f, height * .3f);

        int price = 500;
        switch (upgradeTo) {
            case 1:
                price = 500;
                break;
            case 2:
                price = 1000;
                break;
            case 3:
                price = 2000;
                break;
            case 4:
                price = 4000;
                break;
            case 5:
                price = 8000;
                break;
        }
        text.setText("Do you want to buy " + type + " upgrade for " + price + " coins?");

        back.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                screen.removePopups(false);
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                back.setSize(Constants.HUD_SCREEN_WIDTH * .133f * .8f, Constants.HUD_SCREEN_HEIGHT * .186f * .8f);
                back.setPosition(width * .5f + Constants.HUD_SCREEN_WIDTH * .0133f, height * .2f + Constants.HUD_SCREEN_HEIGHT * .0186f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                back.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
                back.setPosition(width * .5f, height * .2f);
            }
        });

        final int finalPrice = price;
        confirm.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                if (finalPrice <= GamePrefs.getInstance().coins_num) {
                    if (GamePrefs.getInstance().isEffectOn() == 1) {
                        Assets.getInstance().upgrade.setVolume(.7f);
                        Assets.getInstance().upgrade.play();
                    }
                    ServerTool.getInstance().sendUpgrade(selected_item, type, upgradeTo);
                    if (type.equals("stamina"))
                        GamePrefs.getInstance().players[selected_item - 1][0] = upgradeTo;
                    else if (type.equals("size"))
                        GamePrefs.getInstance().players[selected_item - 1][1] = upgradeTo;
                    else if (type.equals("speed"))
                        GamePrefs.getInstance().players[selected_item - 1][2] = upgradeTo;
                }
                screen.removePopups(true);
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                confirm.setSize(Constants.HUD_SCREEN_WIDTH * .133f * .8f, Constants.HUD_SCREEN_HEIGHT * .186f * .8f);
                confirm.setPosition(width * .265f + Constants.HUD_SCREEN_WIDTH * .0133f, height * .2f + Constants.HUD_SCREEN_HEIGHT * .0186f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                confirm.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
                confirm.setPosition(width * .265f, height * .2f);
            }
        });

        addActor(bg);
        addActor(text);
        addActor(back);
        addActor(confirm);
    }
}
