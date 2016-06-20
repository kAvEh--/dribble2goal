package ir.eynajgroup.dribble2goal;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import ir.eynajgroup.dribble2goal.Util.MyScrollPane;
import ir.eynajgroup.dribble2goal.model.Box2dBall;
import ir.eynajgroup.dribble2goal.model.Box2dPlayer;
import ir.eynajgroup.dribble2goal.model.IModel;
import ir.eynajgroup.dribble2goal.render.textures.ProgressCircle;
import ir.eynajgroup.dribble2goal.render.textures.ProgressLine;
import ir.eynajgroup.dribble2goal.screens.CoachScreen;
import ir.eynajgroup.dribble2goal.screens.FindMatchScreen;
import ir.eynajgroup.dribble2goal.screens.GameScreen;
import ir.eynajgroup.dribble2goal.screens.LoadingScreen;
import ir.eynajgroup.dribble2goal.screens.LoginScreen;
import ir.eynajgroup.dribble2goal.screens.MainMenuScreen;
import ir.eynajgroup.dribble2goal.screens.ProfileScreen;
import ir.eynajgroup.dribble2goal.screens.SelectGameScreen;
import ir.eynajgroup.dribble2goal.screens.SettingScreen;
import ir.eynajgroup.dribble2goal.tween.BallAccessor;
import ir.eynajgroup.dribble2goal.tween.ImageAccessor;
import ir.eynajgroup.dribble2goal.tween.PlayerAccessor;
import ir.eynajgroup.dribble2goal.tween.ProgressCircleAccessor;
import ir.eynajgroup.dribble2goal.tween.ProgressLineAccessor;
import ir.eynajgroup.dribble2goal.tween.ScrollAccessor;
import ir.eynajgroup.dribble2goal.tween.SpriteAccessor;
import ir.eynajgroup.dribble2goal.tween.SteperAccessor;
import ir.eynajgroup.dribble2goal.tween.TableAccessor;
import ir.eynajgroup.dribble2goal.tween.TextFieldAccessor;

public class MyGame extends Game {

    private IModel mModel;
    public static TweenManager mTweenManager;
    public static MyGame mainInstance;

    @Override
    public void create() {
        Assets.getInstance().init();
        mTweenManager = new TweenManager();
        Tween.setCombinedAttributesLimit(10);
        mainInstance = this;

        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        Tween.registerAccessor(Box2dPlayer.class, new PlayerAccessor());
        Tween.registerAccessor(Box2dBall.class, new BallAccessor());
        Tween.registerAccessor(Image.class, new ImageAccessor());
        Tween.registerAccessor(Table.class, new TableAccessor());
        Tween.registerAccessor(ScrollPane.class, new ScrollAccessor());
        Tween.registerAccessor(ProgressCircle.class, new ProgressCircleAccessor());
        Tween.registerAccessor(MyScrollPane.class, new SteperAccessor());
        Tween.registerAccessor(TextField.class, new TextFieldAccessor());
        Tween.registerAccessor(ProgressLine.class, new ProgressLineAccessor());

        float f = -0.6009111404418945f;
        System.out.println(Float.floatToIntBits(f) + "***" + Float.toHexString(f) + "***" + (Float.parseFloat(Float.toHexString(f)) == f));

//        setProfileScreen();
//        setLoadingScreen();
//        setMainScreen();
//        setFindMatchScreen();
//        createGame();
        setLoginScreen();

//        createGame();
        Gdx.app.setLogLevel(Application.LOG_ERROR);
    }

    public void setLoginScreen() {
        setScreen(new LoginScreen());
    }

    public void setLoadingScreen() {
        setScreen(new LoadingScreen());
    }

    public void setFindMatchScreen(int stadium) {
        setScreen(new FindMatchScreen(stadium));
    }

    public void setMainScreen() {
        setScreen(new MainMenuScreen());
    }

    public void setSettingScreen() {
        setScreen(new SettingScreen());
    }

    public void setCoachingScreen() {
        setScreen(new CoachScreen());
    }

    public void setCoachScreen() {
        setScreen(new CoachScreen());
    }

    public void setProfileScreen() {
        setScreen(new ProfileScreen());
    }

    public void setGameSelectScreen() {
        setScreen(new SelectGameScreen());
    }

    public void createGame(MatchStats matchStat) {
//        MatchStats matchStat = new MatchStats();

        //TODO : get data from server
//        matchStat.reset(3, true, 1, 1);

//        MatchConstants temp = new MatchConstants();
//        matchStat.myStartPosition = temp.getP1Arrange(matchStat.p1Arrange);
//        matchStat.oppStartPosition = temp.getP2Arrange(matchStat.p2Arrange);
        //Test for GIT
        setScreen(new GameScreen(matchStat));
    }

    @Override
    public void render() {
//        Gdx.gl.glClearColor(0.4f, 0.62f, 0.82f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mTweenManager.update(Gdx.graphics.getDeltaTime());
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
