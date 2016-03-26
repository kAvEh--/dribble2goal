package ir.eynajgroup.dribble2goal.Util;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Timer;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import ir.eynajgroup.dribble2goal.Constants;
import ir.eynajgroup.dribble2goal.MyGame;

/**
 * Created by kAvEh on 3/4/2016.
 */
public class MyScrollPane extends Table {
    public static Table innerTable;
    private float _scrollHeight;
    private int _itemCount;
    private float _itemHeight;
    private float _itemPad;
    private float _itemWidth;
    private float _scrollWidth;
    private float _posX;
    private float _posY;
    private Image[] items;
    public ScrollPane mainScroll;
    private TweenManager mTweenManager;

    private float constant;
    private int focusedItem;
    private float lastScroll;
    private boolean flingFlag = false;

    public MyScrollPane(Image[] buttons, int itemCount, float scrollWidth,
                        float scrollHeight, float posX, float posY, float itemWidth,
                        float itemHeight, float itemPad) {
        mTweenManager = MyGame.mTweenManager;
        this._itemPad = itemPad;
        this._itemWidth = itemWidth;
        this._itemHeight = itemHeight;
        this._itemCount = itemCount;
        this._posX = posX;
        this._posY = posY;
        this._scrollHeight = scrollHeight;
        this._scrollWidth = scrollWidth;
        this.items = buttons;
        constant = 2 * _itemPad + _itemHeight;
        innerTable = new Table();
        this.mainScroll = new ScrollPane(innerTable);
        this.mainScroll.setSize(scrollWidth, scrollHeight);
        this.mainScroll.setPosition(posX, posY);
        this.mainScroll.setCancelTouchFocus(false);
        this.mainScroll.setFlingTime(0.0F);
        this.mainScroll.setForceScroll(false, true);
        this.mainScroll.setSmoothScrolling(false);
        lastScroll = 0f;
        this.mainScroll.addListener(new DragListener() {
            public void drag(InputEvent event, float x, float y, int pointer) {
                MyScrollPane.this.setItemSizeByScrollValue();
            }

            public void dragStart(InputEvent paramAnonymousInputEvent, float paramAnonymousFloat1, float paramAnonymousFloat2, int paramAnonymousInt) {
            }

            public void dragStop(InputEvent paramAnonymousInputEvent, float paramAnonymousFloat1, float paramAnonymousFloat2, int paramAnonymousInt) {
                if (!flingFlag)
                    step();
            }
        });
        this.mainScroll.addListener(new ActorGestureListener() {
            public void fling(InputEvent event, float velocityX, float velocityY, int button) {
//                flingFlag = true;
//                flingScrollPane(velocityY);
            }

            public void pan(InputEvent paramAnonymousInputEvent, float paramAnonymousFloat1, float paramAnonymousFloat2, float paramAnonymousFloat3, float paramAnonymousFloat4) {
            }
        });
        addActor(this.mainScroll);
        for (int i = 0; i < itemCount; i++) {
            if (i == 0) {
                innerTable.add(this.items[i])
                        .pad(Constants.HUD_SCREEN_HEIGHT * .41f, .0f, _itemPad, .0f)
                        .size(itemWidth, itemHeight);
            } else if (i == itemCount - 1) {
                innerTable.add(this.items[i])
                        .pad(_itemPad, .0f, Constants.HUD_SCREEN_HEIGHT * .41f, .0f)
                        .size(itemWidth, itemHeight);
            } else {
                innerTable.add(this.items[i])
                        .pad(_itemPad, .0f, _itemPad, .0f)
                        .size(itemWidth, itemHeight);
            }
            innerTable.row();
        }
        init();
    }

    public float getScrollY() {
        return this.mainScroll.getScrollY();
    }

    public void init() {
        Timer.schedule(new Timer.Task() {
            public void run() {
                MyScrollPane.this.setItemSizeByScrollValue();
            }
        }, 0.01F, 0.1F, 1);
    }

    public void scrollandStep(float scroll) {
        this.mainScroll.setScrollY(scroll);
        this.setItemSizeByScrollValue();
    }

    public void step() {
        Tween.to(this, 1, .5f)
                .target(focusedItem * constant)
                .ease(TweenEquations.easeNone).start(mTweenManager);
    }

    public void setItemSizeByScrollValue() {
        float tmp = (this.mainScroll.getScrollY() / constant) + .5f;
        focusedItem = (int) Math.floor(tmp);

        for (int i = 0; i < _itemCount; i++) {
            if (Math.abs(this.mainScroll.getScrollY() - i * constant) < Constants.HUD_SCREEN_HEIGHT * .6f) {
                resizeItem(i, 1 - (Math.abs(this.mainScroll.getScrollY() - i * constant) / Constants.HUD_SCREEN_HEIGHT * .5f));
            }
        }
    }

    public void resizeItem(int itemNum, float itemSizePercent) {
        float temp = itemSizePercent * 1.8f + 1f;
        itemSizePercent = itemSizePercent + .5f;
        if (itemSizePercent < .5f) {
            itemSizePercent = .5f;
        }
        this.items[itemNum].setWidth(_itemWidth * itemSizePercent);
        this.items[itemNum].setHeight(_itemHeight * itemSizePercent);
        this.items[itemNum].setX(_posX * temp);
    }

    public void flingScrollPane(final float paramFloat) {
        Tween.to(MyScrollPane.this, 1, Math.abs(paramFloat / 2000.0F))
                .target(this.mainScroll.getScrollY() + paramFloat / 100f)
                .ease(TweenEquations.easeNone).start(mTweenManager)
                .setCallback(new TweenCallback() {
                    public void onEvent(int paramAnonymousInt, BaseTween<?> paramAnonymousBaseTween) {
                        System.out.println("$$$$");
                        Tween.to(MyScrollPane.this, 1, Math.abs(paramFloat / 4000.0F))
                                .target(MyScrollPane.this.mainScroll.getScrollY() + paramFloat / 200f).ease(TweenEquations.easeOutSine)
                                .start(mTweenManager).setCallback(new TweenCallback() {
                            public void onEvent(int paramAnonymousInt, BaseTween<?> paramAnonymousBaseTween) {
                                MyScrollPane.this.flingFlag = false;
                            }
                        });
                    }
                });
    }
}
