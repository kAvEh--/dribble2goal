package ir.eynakgroup.dribble2goal.Util;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.Timer;

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

/**
 * Created by Eynak_PC2 on 6/29/2016.
 */
public class ShopScrollPane extends Table {

    Table mainTable;
    Table[] innerTables;
    int _width;
    int _height;
    int page = 0;

    public ScrollPane mainScroll;
    private TweenManager mTweenManager;

    Image indicator;
    Table dots;

    Popups popup;
    final private String popup_string = "popup";
    Stage mStage;
    Image bg;

    final Table[] items = new Table[24];

    private int[] shirt_coins = {0, 0, 1000, 2000, 9000, 9000, 1000, 9000, 9000, 2000, 2000, 5000, 5000, 1000, 2000, 2000, 2000, 5000, 5000, 5000, 1, 1, 1, 1};

    public ShopScrollPane(Image b, Stage st, int width, int height, Table dd, Image indi) {
        mTweenManager = MyGame.mTweenManager;
        mStage = st;
        bg = b;
        indicator = indi;
        dots = dd;
        this._width = width;
        this._height = height;
        mainTable = new Table();
        innerTables = new Table[4];
        for (int i = 0; i < 4; i++) {
            innerTables[i] = new Table();
        }
        mainTable.add(innerTables[0]);
        mainTable.add(innerTables[1]);
        mainTable.add(innerTables[2]);
        mainTable.add(innerTables[3]);
        innerTables[0].setSize(_width, height);
        innerTables[1].setSize(_width, height);
        innerTables[2].setSize(_width, height);
        innerTables[3].setSize(_width, height);

        this.mainScroll = new ScrollPane(mainTable);
        this.mainScroll.setSize(_width, _height);
        this.mainScroll.setPosition(0, 0);
        this.mainScroll.setCancelTouchFocus(false);
        this.mainScroll.setFlingTime(0.0F);
        this.mainScroll.setForceScroll(false, false);
        this.mainScroll.setSmoothScrolling(true);
        this.mainScroll.addListener(new DragListener() {
            public void drag(InputEvent event, float x, float y, int pointer) {
                step(false);
            }

            public void dragStart(InputEvent event, float x, float y, int pointer) {
            }

            public void dragStop(InputEvent event, float x, float y, int pointer) {
                step(true);
            }
        });
        this.mainScroll.addListener(new ActorGestureListener() {
            public void fling(InputEvent event, float velocityX, float velocityY, int button) {
            }

            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
            }
        });
        addActor(mainScroll);
        Image tmpBg;
        Image tmpShirt;
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 6; i++) {
                items[j * 6 + i] = new Table();
                if (GamePrefs.getInstance().user.shirts[j * 6 + i] == 2) {
                    tmpBg = new Image(Assets.getInstance().shop_shirt_selected);
                } else if (GamePrefs.getInstance().user.shirts[j * 6 + i] == 1) {
                    tmpBg = new Image(Assets.getInstance().shop_shirt_select);
                } else {
                    tmpBg = new Image(Assets.getInstance().shop_shirts_bg[j * 6 + i]);
                }
                final int finalI = j * 6 + i;
                items[j * 6 + i].addListener(new ActorGestureListener() {
                    public void tap(InputEvent event, float x, float y, int count, int button) {
                        selectShirt(finalI);
                    }

                    public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (finalI < 6) {
                            innerTables[0].getCell(items[finalI]).size(Constants.HUD_SCREEN_WIDTH * .253f, Constants.HUD_SCREEN_HEIGHT * .238f * .8f);
                            innerTables[0].invalidate();
                        } else if (finalI < 12) {
                            innerTables[1].getCell(items[finalI]).size(Constants.HUD_SCREEN_WIDTH * .253f, Constants.HUD_SCREEN_HEIGHT * .238f * .8f);
                            innerTables[1].invalidate();
                        } else if (finalI < 18) {
                            innerTables[2].getCell(items[finalI]).size(Constants.HUD_SCREEN_WIDTH * .253f, Constants.HUD_SCREEN_HEIGHT * .238f * .8f);
                            innerTables[2].invalidate();
                        } else {
                            innerTables[3].getCell(items[finalI]).size(Constants.HUD_SCREEN_WIDTH * .253f, Constants.HUD_SCREEN_HEIGHT * .238f * .8f);
                            innerTables[3].invalidate();
                        }
                    }

                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        if (finalI < 6) {
                            innerTables[0].getCell(items[finalI]).size(Constants.HUD_SCREEN_WIDTH * .253f, Constants.HUD_SCREEN_HEIGHT * .238f);
                            innerTables[0].invalidate();
                        } else if (finalI < 12) {
                            innerTables[1].getCell(items[finalI]).size(Constants.HUD_SCREEN_WIDTH * .253f, Constants.HUD_SCREEN_HEIGHT * .238f);
                            innerTables[1].invalidate();
                        } else if (finalI < 18) {
                            innerTables[2].getCell(items[finalI]).size(Constants.HUD_SCREEN_WIDTH * .253f, Constants.HUD_SCREEN_HEIGHT * .238f);
                            innerTables[2].invalidate();
                        } else {
                            innerTables[3].getCell(items[finalI]).size(Constants.HUD_SCREEN_WIDTH * .253f, Constants.HUD_SCREEN_HEIGHT * .238f);
                            innerTables[3].invalidate();
                        }
                    }
                });
                tmpBg.setSize(Constants.HUD_SCREEN_WIDTH * .253f, Constants.HUD_SCREEN_HEIGHT * .238f);
                tmpBg.setName("bg_");
                items[j * 6 + i].addActor(tmpBg);
                tmpShirt = new Image(Assets.getInstance().shop_shirts[j * 6 + i]);
                tmpShirt.setSize(Constants.HUD_SCREEN_WIDTH * .253f, Constants.HUD_SCREEN_HEIGHT * .238f);
                items[j * 6 + i].addActor(tmpShirt);
                if (i == 5) {
                    innerTables[j].add(items[j * 6 + i])
                            .pad(Constants.HUD_SCREEN_HEIGHT * .07f, Constants.HUD_SCREEN_WIDTH * .06f,
                                    Constants.HUD_SCREEN_HEIGHT * .12f, Constants.HUD_SCREEN_WIDTH * .06f)
                            .size(Constants.HUD_SCREEN_WIDTH * .253f, Constants.HUD_SCREEN_HEIGHT * .238f);
                } else if (i == 2) {
                    innerTables[j].add(items[j * 6 + i])
                            .pad(Constants.HUD_SCREEN_HEIGHT * .07f, Constants.HUD_SCREEN_WIDTH * .06f,
                                    0, Constants.HUD_SCREEN_WIDTH * .06f)
                            .size(Constants.HUD_SCREEN_WIDTH * .253f, Constants.HUD_SCREEN_HEIGHT * .238f);

                    innerTables[j].row();
                } else if (i > 2) {
                    innerTables[j].add(items[j * 6 + i])
                            .pad(Constants.HUD_SCREEN_HEIGHT * .07f, Constants.HUD_SCREEN_WIDTH * .06f,
                                    Constants.HUD_SCREEN_HEIGHT * .12f, 0)
                            .size(Constants.HUD_SCREEN_WIDTH * .253f, Constants.HUD_SCREEN_HEIGHT * .238f);
                } else {
                    innerTables[j].add(items[j * 6 + i]).
                            pad(Constants.HUD_SCREEN_HEIGHT * .07f, Constants.HUD_SCREEN_WIDTH * .06f,
                                    0, 0)
                            .size(Constants.HUD_SCREEN_WIDTH * .253f, Constants.HUD_SCREEN_HEIGHT * .238f);
                }
            }
        }

        init();
    }

    private void selectShirt(int finalI) {
        if (GamePrefs.getInstance().user.shirts[finalI] == 2) {
            //TODO keep calm, this shirt is mine
        } else if (GamePrefs.getInstance().user.shirts[finalI] == 1) {
            setSelectedShare(finalI);
        } else {
            if (shirt_coins[finalI] == 1) {
                popup = new Popups(this, Constants.HUD_SCREEN_WIDTH * .61f, Constants.HUD_SCREEN_HEIGHT * .8f,
                        -1, finalI + 1, mStage, bg);
            } else if (shirt_coins[finalI] < GamePrefs.getInstance().user.getCoins_num()) {
                popup = new Popups(this, Constants.HUD_SCREEN_WIDTH * .61f, Constants.HUD_SCREEN_HEIGHT * .8f,
                        shirt_coins[finalI], finalI + 1, mStage, bg);
            } else {
                return;
            }
            popup.setSize(Constants.HUD_SCREEN_WIDTH * .61f, Constants.HUD_SCREEN_HEIGHT * .8f);
            popup.setPosition(Constants.HUD_SCREEN_WIDTH * .195f, Constants.HUD_SCREEN_HEIGHT * .1f);
            popup.setName(popup_string);

            mStage.addActor(popup);
            bg.setColor(bg.getColor().r, bg.getColor().g, bg.getColor().b, 0);
            bg.setPosition(0, 0);
            Tween.to(bg, 3, 3f)
                    .target(1)
                    .ease(TweenEquations.easeOutExpo)
                    .start(mTweenManager).delay(0.0F);
        }
    }

    public void setSelectedShare(int finalI) {
        for (int i = 0; i < 2; i++) {
            items[finalI].removeActor(items[finalI].getChildren().first());
        }
        Image tmpBg = new Image(Assets.getInstance().shop_shirt_selected);
        tmpBg.setSize(Constants.HUD_SCREEN_WIDTH * .253f, Constants.HUD_SCREEN_HEIGHT * .238f);
        tmpBg.setName("bg_");
        Image tmpShirt = new Image(Assets.getInstance().shop_shirts[finalI]);
        tmpShirt.setSize(Constants.HUD_SCREEN_WIDTH * .253f, Constants.HUD_SCREEN_HEIGHT * .238f);
        items[finalI].addActor(tmpBg);
        items[finalI].addActor(tmpShirt);
        for (int i = 0; i < 24; i++) {
            if (GamePrefs.getInstance().user.shirts[i] == 2) {
                for (int j = 0; j < 2; j++) {
                    items[i].removeActor(items[i].getChildren().first());
                }
                tmpBg = new Image(Assets.getInstance().shop_shirt_select);
                tmpBg.setSize(Constants.HUD_SCREEN_WIDTH * .253f, Constants.HUD_SCREEN_HEIGHT * .238f);
                tmpBg.setName("bg_");
                tmpShirt = new Image(Assets.getInstance().shop_shirts[i]);
                tmpShirt.setSize(Constants.HUD_SCREEN_WIDTH * .253f, Constants.HUD_SCREEN_HEIGHT * .238f);
                items[i].addActor(tmpBg);
                items[i].addActor(tmpShirt);
                GamePrefs.getInstance().user.shirts[i] = 1;
                break;
            }
        }
        GamePrefs.getInstance().user.shirts[finalI] = 2;
        GamePrefs.getInstance().user.setShirt(finalI);
        ServerTool.getInstance().sendShop(0, finalI + 1, "", "");
    }

    private void step(boolean endFlag) {
        float scroll = this.mainScroll.getScrollX();
        int pivot = page * _width;
        int tmp = page;
        if (scroll > pivot + _width * .1f) {
            tmp += 1;
        } else if (scroll < pivot - _width * .1f) {
            tmp -= 1;
        }
        if (endFlag) {
            page = tmp;
            this.mainScroll.setScrollX(page * _width);
        }
        switch (tmp) {
            case 0:
                dots.getCell(indicator).padLeft(Constants.HUD_SCREEN_WIDTH * -.21f);
                dots.invalidate();
                break;
            case 1:
                dots.getCell(indicator).padLeft(Constants.HUD_SCREEN_WIDTH * -.15f);
                dots.invalidate();
                break;
            case 2:
                dots.getCell(indicator).padLeft(Constants.HUD_SCREEN_WIDTH * -.088f);
                dots.invalidate();
                break;
            case 3:
                dots.getCell(indicator).padLeft(Constants.HUD_SCREEN_WIDTH * -.03f);
                dots.invalidate();
                break;
        }
    }

    public void gotoPage(boolean isNext) {
        if (isNext) {
            if (page < 3)
                page++;
        } else {
            if (page > 0)
                page--;
        }
        this.mainScroll.setScrollX(page * _width);
        switch (page) {
            case 0:
                dots.getCell(indicator).padLeft(Constants.HUD_SCREEN_WIDTH * -.21f);
                dots.invalidate();
                break;
            case 1:
                dots.getCell(indicator).padLeft(Constants.HUD_SCREEN_WIDTH * -.15f);
                dots.invalidate();
                break;
            case 2:
                dots.getCell(indicator).padLeft(Constants.HUD_SCREEN_WIDTH * -.088f);
                dots.invalidate();
                break;
            case 3:
                dots.getCell(indicator).padLeft(Constants.HUD_SCREEN_WIDTH * -.03f);
                dots.invalidate();
                break;
        }
    }

    private void init() {
        Timer.schedule(new Timer.Task() {
            public void run() {
//                ShopScrollPane.this.setItemSizeByScrollValue();
            }
        }, 0.01F, 0.1F, 1);
    }
}
