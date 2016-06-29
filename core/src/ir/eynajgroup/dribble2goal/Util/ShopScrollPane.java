package ir.eynajgroup.dribble2goal.Util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;

import aurelienribon.tweenengine.TweenManager;
import ir.eynajgroup.dribble2goal.Assets;
import ir.eynajgroup.dribble2goal.Constants;

/**
 * Created by Eynak_PC2 on 6/29/2016.
 */
public class ShopScrollPane extends Table {

    Table holderTable;
    Table mainTable;
    Table innerTable1;
    Table innerTable2;
    Table innerTable3;
    Table innerTable4;
    int _width;
    int _height;

    int current = 0;
    public ScrollPane mainScroll;
    private TweenManager mTweenManager;

    public ShopScrollPane(int width, int height) {
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
        innerTable1.setBackground(new TextureRegionDrawable(new TextureRegion(Assets.getInstance().main_bg, _width, _height)));
        innerTable2.setSize(_width, height);
        innerTable3.setSize(_width, height);
        innerTable4.setSize(_width, height);
        holderTable = new Table();
        holderTable.setSize(_width, _height);
        holderTable.setPosition(0, 0);
        holderTable.add(mainTable);
        holderTable.addListener(new ActorGestureListener() {
            public void fling(InputEvent event, float velocityX, float velocityY, int button) {
                if (velocityX > 70) {
                    System.out.println("Prev Page");
                    current--;
                    if (current < 0) {
                        current = 0;
                    }
                }
                if (velocityX < -70) {
                    System.out.println("Next Page");
                    current++;
                    if (current > 4) {
                        current = 4;
                    }
                }
                System.out.println("--->" + current);
                holderTable.setPosition(current * _width, 0);
            }

            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
            }
        });
        this.mainScroll = new ScrollPane(mainTable);
        this.mainScroll.setSize(_width, _height);
        this.mainScroll.setPosition(0, 0);
        this.mainScroll.setCancelTouchFocus(false);
        this.mainScroll.setFlingTime(0.0F);
        this.mainScroll.setForceScroll(false, false);
        this.mainScroll.setSmoothScrolling(false);
        this.mainScroll.addListener(new DragListener() {
            public void drag(InputEvent event, float x, float y, int pointer) {
                step();
            }

            public void dragStart(InputEvent paramAnonymousInputEvent, float paramAnonymousFloat1, float paramAnonymousFloat2, int paramAnonymousInt) {
            }

            public void dragStop(InputEvent paramAnonymousInputEvent, float paramAnonymousFloat1, float paramAnonymousFloat2, int paramAnonymousInt) {
            }
        });
        this.mainScroll.addListener(new ActorGestureListener() {
            public void fling(InputEvent event, float velocityX, float velocityY, int button) {
            }

            public void pan(InputEvent paramAnonymousInputEvent, float paramAnonymousFloat1, float paramAnonymousFloat2, float paramAnonymousFloat3, float paramAnonymousFloat4) {
            }
        });
        addActor(mainScroll);
        for (int i = 0; i < 6; i++) {
            innerTable1.add(new Image(Assets.getInstance().shop_coin_1))
                    .pad(Constants.HUD_SCREEN_HEIGHT * .06f, Constants.HUD_SCREEN_WIDTH * .03f,
                            0, 0)
                    .size(_width / 4f, _height / 4f);
            if (i == 2)
                innerTable1.row();
        }
        for (int i = 0; i < 6; i++) {
            innerTable2.add(new Image(Assets.getInstance().shop_coin_2))
                    .pad(Constants.HUD_SCREEN_HEIGHT * .03f, Constants.HUD_SCREEN_WIDTH * .03f,
                            Constants.HUD_SCREEN_HEIGHT * .03f, Constants.HUD_SCREEN_WIDTH * .03f)
                    .size(_width / 4f, _height / 4f);
            if (i == 2)
                innerTable2.row();
        }
        for (int i = 0; i < 6; i++) {
            innerTable3.add(new Image(Assets.getInstance().shop_coin_1))
                    .pad(Constants.HUD_SCREEN_HEIGHT * .03f, Constants.HUD_SCREEN_WIDTH * .03f,
                            Constants.HUD_SCREEN_HEIGHT * .03f, Constants.HUD_SCREEN_WIDTH * .03f)
                    .size(_width / 4f, _height / 4f);
            if (i == 2)
                innerTable3.row();
        }

        init();
    }

    public void step() {
        System.out.println(this.mainScroll.getScrollX() + "=====");
    }

    public void init() {
        Timer.schedule(new Timer.Task() {
            public void run() {
//                ShopScrollPane.this.setItemSizeByScrollValue();
            }
        }, 0.01F, 0.1F, 1);
    }
}
