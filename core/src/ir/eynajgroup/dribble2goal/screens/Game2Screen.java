package ir.eynajgroup.dribble2goal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Json;

import aurelienribon.tweenengine.TweenManager;
import ir.eynajgroup.dribble2goal.Constants;
import ir.eynajgroup.dribble2goal.MatchStats;
import ir.eynajgroup.dribble2goal.MyGame;
import ir.eynajgroup.dribble2goal.input.Controls;
import ir.eynajgroup.dribble2goal.input.State;
import ir.eynajgroup.dribble2goal.model.IModel;
import ir.eynajgroup.dribble2goal.model.IModelListener;
import ir.eynajgroup.dribble2goal.model.IRenderer;
import ir.eynajgroup.dribble2goal.render.GameRenderer;
import ir.eynajgroup.dribble2goal.template.Field;

/**
 * Created by kAvEh on 2/19/2016.
 */
public class Game2Screen implements Screen, IModelListener {

    private IModel mModel;
    private IRenderer mGameRenderer;
    private OrthographicCamera mMainCamera;
    private OrthographicCamera mHUDCamera;
    private Json mJson;
    private Controls mControls;

    public int frameCount = 0;
    private long now;
    private final static int FPSupdateIntervall = 1;
    private long lastRender;
    public int lastFPS = 0;

    float dt;
    float accumulator;

    private final static int logic_FPSupdateIntervall = 1;  //--- display FPS alle x sekunden
    private long logic_lastRender;
    private long logic_now;
    public int logic_frameCount = 0;
    public int logic_lastFPS = 0;

    public static final long RENDERER_SLEEP_MS = 0; // 34 -> 30 fps, 30 -> 34 fps, 22 gives ~46 FPS, 20 = 100, 10 = 50
    private long now2, diff, start;

    MatchStats gameStat;
    TweenManager mTweenManager;

    public Game2Screen(MatchStats stat,IModel model) {
        mModel = model;
        mTweenManager = MyGame.mTweenManager;
        mModel.addModelListener(this);

        this.gameStat = stat;

        dt = 0.0133f;
    }

    @Override
    public void show() {
        mMainCamera = new OrthographicCamera(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        mMainCamera.update();
        mHUDCamera = new OrthographicCamera(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);
        mHUDCamera.update();
        mGameRenderer = new GameRenderer(mMainCamera, mHUDCamera, this.gameStat, mTweenManager);
        mControls = new Controls(mModel, mMainCamera);
        mControls.setState(State.IDLE);
        Gdx.input.setInputProcessor(mControls);
        mJson = new Json();
    }

    @Override
    public void render(float delta) {
        //---------- FPS check ----------------------------
        frameCount++;
        now = System.nanoTime();    // zeit loggen

        if ((now - lastRender) >= FPSupdateIntervall * 1000000000) {

            lastFPS = frameCount / FPSupdateIntervall;

            frameCount = 0;
            lastRender = System.nanoTime();
        }
        //--------------------------------------------------------------
        renderFIXEDTIMESTEP(delta);


//        mControls.process();
    }

    //====================================================================================
    //	http://gafferongames.com/game-physics/fix-your-timestep/
    public void renderFIXEDTIMESTEP(float delta) {

        if (delta > 0.25f) delta = 0.25f;      // note: max frame time to avoid spiral of death

        accumulator += delta;

        while (accumulator >= dt) {

//            game.getGameObjectManager().copyCurrentPosition();

            updating(dt);
            accumulator -= dt;

//            game.getGameObjectManager().interpolateCurrentPosition(accumulator / dt);

            //---------- FPS check -----------------------------
            logic_frameCount++;
            logic_now = System.nanoTime();    // zeit loggen

            if ((logic_now - logic_lastRender) >= logic_FPSupdateIntervall * 1000000000) {

                logic_lastFPS = logic_frameCount / logic_FPSupdateIntervall;
                logic_frameCount = 0;
                logic_lastRender = System.nanoTime();
            }
            //--------------------------------------------------------------
        }

        rendering(delta);
    }


    //====================================================================================
    public void updating(float delta) {

        mModel.update(delta);
    }


    //====================================================================================
    public void rendering(float delta) {

        mGameRenderer.render();
        mModel.debugRender(mMainCamera);

        //------------- to limit fps ------------------------
        if (RENDERER_SLEEP_MS > 0) {

            now2 = System.currentTimeMillis();
            diff = now2 - start;

            if (diff < RENDERER_SLEEP_MS) {
                try {
                    Thread.sleep(RENDERER_SLEEP_MS - diff);
                } catch (InterruptedException e) {
                }
            }

            start = System.currentTimeMillis();
        }
        //-----------------------------------------------------
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void resize(int width, int height) {
        float scale_h;
        float scale;
        float newWidth;
        float newHeight;

        scale = Constants.SCREEN_WIDTH / (float) width;
        scale_h = Constants.SCREEN_HEIGHT / (float) height;

        if (scale_h > scale) {
            scale = scale_h;
        }

        newWidth = (float) Math.ceil(scale * width);
        newHeight = (float) Math.ceil(scale * height);

        mMainCamera.viewportWidth = newWidth;
        mMainCamera.viewportHeight = newHeight;

        mMainCamera.update();

        scale = Constants.HUD_SCREEN_WIDTH / (float) width;
        scale_h = Constants.HUD_SCREEN_HEIGHT / (float) height;

        if (scale_h > scale) {
            scale = scale_h;
        }

        newWidth = (float) Math.ceil(scale * width);
        newHeight = (float) Math.ceil(scale * height);

        mHUDCamera.viewportWidth = newWidth;
        mHUDCamera.viewportHeight = newHeight;
    }

    @Override
    public void dispose() {
        mGameRenderer.dispose();
    }

    @Override
    public void onModelUpdate(String modelState) {
        mGameRenderer.updateModel(mJson.fromJson(Field.class, modelState));
    }

    @Override
    public void goalEvent() {
        mControls.setState(State.IDLE);
    }

    @Override
    public void winEvent() {
        mControls.setState(State.END_GAME);
        mGameRenderer.playWinEffect();
    }
}
