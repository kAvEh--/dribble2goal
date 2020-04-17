package ir.eynakgroup.dribble2goal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import org.json.JSONException;
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
import ir.eynakgroup.dribble2goal.MatchStats;
import ir.eynakgroup.dribble2goal.MyGame;
import ir.eynakgroup.dribble2goal.Server.ServerTool;
import ir.eynakgroup.dribble2goal.Util.Util;

/**
 * Created by Eynak_PC2 on 8/2/2016.
 */
public class ResultScreen implements Screen, InputProcessor {

    TweenManager mTweenManager;
    private OrthographicCamera mMainCamera;
    SpriteBatch mMainBatch;
    Image bg;

    Stage mStage;
    Table mainTable;
    Skin mSkin;

    Image main_image;
    Image my_avatar;
    Image opp_avatar;
    Image my_level_bg;
    Image opp_level_bg;
    Image prize;
    Label my_name;
    Label opp_name;
    Label my_level;
    Label opp_level;
    Label my_result;
    Label opp_result;
    Image back;
    Image game_status;

    Table coinTable;

    MatchStats stat;

    public ResultScreen(MatchStats s) {
        mTweenManager = MyGame.mTweenManager;
        mMainBatch = new SpriteBatch();
        stat = s;

        mSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        mStage = new Stage(new FitViewport(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT), mMainBatch);
        mainTable = new Table();

        bg = new Image(Assets.getInstance().main_bg);
        bg.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        main_image = new Image(Assets.getInstance().result_main);
        main_image.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        my_avatar = new Image(new Util().getAvatar(GamePrefs.getInstance().user.getAvatar()));
        my_avatar.setSize(Constants.HUD_SCREEN_WIDTH * .245f, Constants.HUD_SCREEN_HEIGHT * .454f);
        my_avatar.setPosition(Constants.HUD_SCREEN_WIDTH * .158f, Constants.HUD_SCREEN_HEIGHT * .266f);

        if (stat.isWinner) {
            game_status = new Image(Assets.getInstance().win_image);
            if (GamePrefs.getInstance().isEffectOn() == 1) {
                Assets.getInstance().coins.play();
            }
        } else {
            game_status = new Image(Assets.getInstance().lose_image);
        }
        game_status.setSize(Constants.HUD_SCREEN_WIDTH * .45f, Constants.HUD_SCREEN_HEIGHT * .45f);
        game_status.setPosition(Constants.HUD_SCREEN_WIDTH * .275f, Constants.HUD_SCREEN_HEIGHT * .55f);

        opp_avatar = new Image(new Util().getAvatar(stat.oppAvatar));
        opp_avatar.setSize(Constants.HUD_SCREEN_WIDTH * .245f, Constants.HUD_SCREEN_HEIGHT * .454f);
        opp_avatar.setPosition(Constants.HUD_SCREEN_WIDTH * .598f, Constants.HUD_SCREEN_HEIGHT * .266f);

        my_level_bg = new Image(Assets.getInstance().level_left);
        my_level_bg.setSize(Constants.HUD_SCREEN_WIDTH * .18f, Constants.HUD_SCREEN_HEIGHT * .237f);
        my_level_bg.setPosition(Constants.HUD_SCREEN_WIDTH * .28f, Constants.HUD_SCREEN_HEIGHT * .37f);

        opp_level_bg = new Image(Assets.getInstance().level_right);
        opp_level_bg.setSize(Constants.HUD_SCREEN_WIDTH * .18f, Constants.HUD_SCREEN_HEIGHT * .237f);
        opp_level_bg.setPosition(Constants.HUD_SCREEN_WIDTH * .54f, Constants.HUD_SCREEN_HEIGHT * .37f);

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = mSkin.getFont("default-font");
        style.fontColor = Color.WHITE;

        my_name = new Label(GamePrefs.getInstance().user.getName(), style);
        my_name.setWrap(true);
        my_name.setAlignment(Align.center);
        my_name.setBounds(Constants.HUD_SCREEN_WIDTH * .01f, Constants.HUD_SCREEN_HEIGHT * .4f,
                Constants.HUD_SCREEN_WIDTH * .18f, Constants.HUD_SCREEN_WIDTH * .1f);

        opp_name = new Label(stat.oppName, style);
        opp_name.setWrap(true);
        opp_name.setAlignment(Align.center);
        opp_name.setBounds(Constants.HUD_SCREEN_WIDTH * .81f, Constants.HUD_SCREEN_HEIGHT * .4f,
                Constants.HUD_SCREEN_WIDTH * .18f, Constants.HUD_SCREEN_WIDTH * .1f);

        Label.LabelStyle style2 = new Label.LabelStyle();
        style2.font = mSkin.getFont("default-font");
        style2.fontColor = Color.DARK_GRAY;

        my_level = new Label(GamePrefs.getInstance().user.getLevel() + "", style2);
        my_level.setWrap(true);
        my_level.setAlignment(Align.center);
        my_level.setBounds(Constants.HUD_SCREEN_WIDTH * .306f, Constants.HUD_SCREEN_HEIGHT * .361f,
                Constants.HUD_SCREEN_WIDTH * .18f, Constants.HUD_SCREEN_WIDTH * .1f);

        opp_level = new Label(stat.oppLevel + "", style2);
        opp_level.setWrap(true);
        opp_level.setAlignment(Align.center);
        opp_level.setBounds(Constants.HUD_SCREEN_WIDTH * .515f, Constants.HUD_SCREEN_HEIGHT * .361f,
                Constants.HUD_SCREEN_WIDTH * .18f, Constants.HUD_SCREEN_WIDTH * .1f);

        my_result = new Label(stat.myGoals + "", style);
        my_result.setWrap(true);
        my_result.setAlignment(Align.left);
        my_result.setBounds(Constants.HUD_SCREEN_WIDTH * .1f, Constants.HUD_SCREEN_HEIGHT * .66f,
                Constants.HUD_SCREEN_WIDTH * .1f, Constants.HUD_SCREEN_WIDTH * .1f);

        opp_result = new Label(stat.oppGoals + "", style);
        opp_result.setWrap(true);
        opp_result.setAlignment(Align.right);
        opp_result.setBounds(Constants.HUD_SCREEN_WIDTH * .8f, Constants.HUD_SCREEN_HEIGHT * .66f,
                Constants.HUD_SCREEN_WIDTH * .1f, Constants.HUD_SCREEN_WIDTH * .1f);

        back = new Image(Assets.getInstance().icon_back);
        back.setSize(Constants.HUD_SCREEN_WIDTH * .133f, Constants.HUD_SCREEN_HEIGHT * .186f);
        back.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .04f);

        int tmp = stat.matchLevel;
        if (stat.matchLevel > 6)
            tmp = stat.matchLevel - 6;
        switch (tmp) {
            case 1:
                prize = new Image(Assets.getInstance().prize_1);
                break;
            case 2:
                prize = new Image(Assets.getInstance().prize_2);
                break;
            case 3:
                prize = new Image(Assets.getInstance().prize_3);
                break;
            case 4:
                prize = new Image(Assets.getInstance().prize_4);
                break;
            case 5:
                prize = new Image(Assets.getInstance().prize_5);
                break;
            default:
                prize = new Image(Assets.getInstance().prize_5);
                break;
        }
        prize.setSize(Constants.HUD_SCREEN_WIDTH * .27f, Constants.HUD_SCREEN_HEIGHT * .087f);
        prize.setPosition(Constants.HUD_SCREEN_WIDTH * .365f, Constants.HUD_SCREEN_HEIGHT * .04f);

        coinTable = new Table();

        this.mainTable.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(mStage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        mSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        mainTable.addActor(bg);
        mainTable.addActor(opp_level_bg);
        mainTable.addActor(my_level_bg);
        mainTable.addActor(main_image);
        mainTable.addActor(prize);
        mainTable.addActor(coinTable);
        mainTable.addActor(game_status);
        mainTable.addActor(my_avatar);
        mainTable.addActor(opp_avatar);
        mainTable.addActor(my_name);
        mainTable.addActor(opp_name);
        mainTable.addActor(my_level);
        mainTable.addActor(opp_level);
        mainTable.addActor(my_result);
        mainTable.addActor(opp_result);
        mainTable.addActor(back);

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

        this.mainTable.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        mStage.addActor(this.mainTable);

        ServerTool.getInstance().socket.on("coin", onCoinListener);
        //TODO change this when rematch added :: IMPORTANT
        ServerTool.getInstance().matchDone();
    }

    private Emitter.Listener onCoinListener = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            JSONObject response = (JSONObject) args[0];
            try {
                GamePrefs.getInstance().user.setCoins_num(response.getInt("coin"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    };

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

        if (stat.isWinner) {
            final Image[] coins = new Image[30];
            for (int i = 0; i < 30; i++) {
                coins[i] = new Image(Assets.getInstance().coins_sample);
                coins[i].setSize(Constants.HUD_SCREEN_WIDTH * .052f, Constants.HUD_SCREEN_HEIGHT * .056f);
                coins[i].setPosition(Constants.HUD_SCREEN_WIDTH * .48f, Constants.HUD_SCREEN_HEIGHT * .17f);
                coins[i].setColor(1, 1, 1, .4f);
                coinTable.addActor(coins[i]);
                Tween.to(coins[i], 1, ((float) Math.random() / 8f) + .1f)
                        .target(Constants.HUD_SCREEN_WIDTH * .3f, Constants.HUD_SCREEN_HEIGHT * .46f)
                        .ease(TweenEquations.easeInCubic)
                        .start(mTweenManager).delay(.1F * i);
                Tween.to(coins[i], 3, ((float) Math.random() / 8f) + .1f)
                        .target(1)
                        .ease(TweenEquations.easeInCubic)
                        .start(mTweenManager).delay(.1F * i);
            }
        } else {
            final Image[] coins2 = new Image[30];
            for (int i = 0; i < 30; i++) {
                coins2[i] = new Image(Assets.getInstance().coins_sample);
                coins2[i].setSize(Constants.HUD_SCREEN_WIDTH * .052f, Constants.HUD_SCREEN_HEIGHT * .056f);
                coins2[i].setPosition(Constants.HUD_SCREEN_WIDTH * .48f, Constants.HUD_SCREEN_HEIGHT * .17f);
                coins2[i].setColor(1, 1, 1, .4f);
                coinTable.addActor(coins2[i]);
                Tween.to(coins2[i], 1, ((float) Math.random() / 8f) + .1f)
                        .target(Constants.HUD_SCREEN_WIDTH * .65f, Constants.HUD_SCREEN_HEIGHT * .46f)
                        .ease(TweenEquations.easeInCubic)
                        .start(mTweenManager).delay(.1F * i);
                Tween.to(coins2[i], 3, ((float) Math.random() / 8f) + .1f)
                        .target(1)
                        .ease(TweenEquations.easeInCubic)
                        .start(mTweenManager).delay(.1F * i);
            }
        }
    }

    @Override
    public void render(float delta) {
        mStage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        mMainBatch.setTransformMatrix(mMainCamera.view);
        mMainBatch.setProjectionMatrix(mMainCamera.projection);

        mStage.act(delta);
        mStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        float aspectRatio = (float) width / height;
        mMainCamera.viewportWidth = Constants.HUD_SCREEN_HEIGHT * aspectRatio;
        mMainCamera.update();

        mStage.getViewport().update(width, (int) (Constants.HUD_SCREEN_HEIGHT * aspectRatio), true);
//        mStage.getViewport().update(width, height, true);
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
        ServerTool.getInstance().socket.off("profileResult");
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