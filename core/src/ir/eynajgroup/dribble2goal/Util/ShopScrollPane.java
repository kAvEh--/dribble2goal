package ir.eynajgroup.dribble2goal.Util;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Timer;

import aurelienribon.tweenengine.TweenManager;
import ir.eynajgroup.dribble2goal.Assets;
import ir.eynajgroup.dribble2goal.Constants;

/**
 * Created by Eynak_PC2 on 6/29/2016.
 */
public class ShopScrollPane extends Table {

    Table mainTable;
    Table innerTable1;
    Table innerTable2;
    Table innerTable3;
    Table innerTable4;
    int _width;
    int _height;
    int page = 0;

    public ScrollPane mainScroll;
    private TweenManager mTweenManager;

    Image indicator;

    public ShopScrollPane(int width, int height, Image indi) {
        indicator = indi;
        this._width = width;
        this._height = height;
        mainTable = new Table();
        innerTable1 = new Table();
        innerTable2 = new Table();
        innerTable3 = new Table();
        innerTable4 = new Table();
        mainTable.add(innerTable1);
        mainTable.add(innerTable2);
        mainTable.add(innerTable3);
        mainTable.add(innerTable4);
        innerTable1.setSize(_width, height);
//        innerTable1.setBackground(new TextureRegionDrawable(new TextureRegion(Assets.getInstance().shadow, _width, 0)));
        innerTable2.setSize(_width, height);
//        innerTable2.setBackground(new TextureRegionDrawable(new TextureRegion(Assets.getInstance().shadow, _width, 0)));
        innerTable3.setSize(_width, height);
//        innerTable3.setBackground(new TextureRegionDrawable(new TextureRegion(Assets.getInstance().shadow, _width, 0)));
        innerTable4.setSize(_width, height);
//        innerTable4.setBackground(new TextureRegionDrawable(new TextureRegion(Assets.getInstance().shadow, _width, 0)));

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
        for (int i = 0; i < 6; i++) {
            if (i == 5)
                innerTable1.add(new Image(Assets.getInstance().shop_coin_1))
                        .pad(Constants.HUD_SCREEN_HEIGHT * .12f, Constants.HUD_SCREEN_WIDTH * .025f,
                                Constants.HUD_SCREEN_HEIGHT * .3f, Constants.HUD_SCREEN_WIDTH * .025f)
                        .size(Constants.HUD_SCREEN_WIDTH * .3f, Constants.HUD_SCREEN_HEIGHT * .2f);
            else if (i== 2) {
                innerTable1.add(new Image(Assets.getInstance().shop_coin_1))
                        .pad(Constants.HUD_SCREEN_HEIGHT * .12f, Constants.HUD_SCREEN_WIDTH * .025f,
                                0, Constants.HUD_SCREEN_WIDTH * .025f)
                        .size(Constants.HUD_SCREEN_WIDTH * .3f, Constants.HUD_SCREEN_HEIGHT * .2f);

                innerTable1.row();
            } else if (i > 2)
                innerTable1.add(new Image(Assets.getInstance().shop_coin_1))
                        .pad(Constants.HUD_SCREEN_HEIGHT * .12f, Constants.HUD_SCREEN_WIDTH * .025f,
                                Constants.HUD_SCREEN_HEIGHT * .3f, 0)
                        .size(Constants.HUD_SCREEN_WIDTH * .3f, Constants.HUD_SCREEN_HEIGHT * .2f);
            else
                innerTable1.add(new Image(Assets.getInstance().shop_coin_1))
                        .pad(Constants.HUD_SCREEN_HEIGHT * .12f, Constants.HUD_SCREEN_WIDTH * .025f,
                                0, 0)
                        .size(Constants.HUD_SCREEN_WIDTH * .3f, Constants.HUD_SCREEN_HEIGHT * .2f);
        }
        for (int i = 0; i < 6; i++) {
            if (i == 2 || i == 5)
                innerTable2.add(new Image(Assets.getInstance().shop_coin_2))
                        .pad(Constants.HUD_SCREEN_HEIGHT * .12f, Constants.HUD_SCREEN_WIDTH * .025f,
                                0, Constants.HUD_SCREEN_WIDTH * .025f)
                        .size(Constants.HUD_SCREEN_WIDTH * .3f, Constants.HUD_SCREEN_HEIGHT * .2f);
            else
                innerTable2.add(new Image(Assets.getInstance().shop_coin_2))
                        .pad(Constants.HUD_SCREEN_HEIGHT * .12f, Constants.HUD_SCREEN_WIDTH * .025f,
                                0, 0)
                        .size(Constants.HUD_SCREEN_WIDTH * .3f, Constants.HUD_SCREEN_HEIGHT * .2f);
            if (i == 2)
                innerTable2.row();
        }
        for (int i = 0; i < 6; i++) {
            if (i == 2 || i == 5)
                innerTable3.add(new Image(Assets.getInstance().shop_coin_1))
                        .pad(Constants.HUD_SCREEN_HEIGHT * .12f, Constants.HUD_SCREEN_WIDTH * .025f,
                                0, Constants.HUD_SCREEN_WIDTH * .025f)
                        .size(Constants.HUD_SCREEN_WIDTH * .3f, Constants.HUD_SCREEN_HEIGHT * .2f);
            else
                innerTable3.add(new Image(Assets.getInstance().shop_coin_1))
                        .pad(Constants.HUD_SCREEN_HEIGHT * .12f, Constants.HUD_SCREEN_WIDTH * .025f,
                                0, 0)
                        .size(Constants.HUD_SCREEN_WIDTH * .3f, Constants.HUD_SCREEN_HEIGHT * .2f);
            if (i == 2)
                innerTable3.row();
        }

        init();
    }

    public void step(boolean endFlag) {
        float scroll = this.mainScroll.getScrollX();
        int pivot = page * _width;
        int tmp = page;
        if (scroll > pivot + _width * .3f) {
            tmp += 1;
        } else if (scroll < pivot - _width * .3f) {
            tmp -= 1;
        }
        if (endFlag) {
            page = tmp;
            this.mainScroll.setScrollX(page * _width);
        }
    }

    public void init() {
        Timer.schedule(new Timer.Task() {
            public void run() {
//                ShopScrollPane.this.setItemSizeByScrollValue();
            }
        }, 0.01F, 0.1F, 1);
    }
}
