package ir.eynakgroup.dribble2goal;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import org.json.JSONObject;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import io.socket.emitter.Emitter;
import ir.eynakgroup.dribble2goal.Interfaces.PlayServices;
import ir.eynakgroup.dribble2goal.Server.ServerTool;
import ir.eynakgroup.dribble2goal.Util.SelectGameScrollPane;
import ir.eynakgroup.dribble2goal.model.Box2dBall;
import ir.eynakgroup.dribble2goal.model.Box2dPlayer;
import ir.eynakgroup.dribble2goal.model.IModel;
import ir.eynakgroup.dribble2goal.render.textures.ProgressCircle;
import ir.eynakgroup.dribble2goal.render.textures.ProgressLine;
import ir.eynakgroup.dribble2goal.screens.CoachScreen;
import ir.eynakgroup.dribble2goal.screens.EntranceScreen;
import ir.eynakgroup.dribble2goal.screens.FindMatchScreen;
import ir.eynakgroup.dribble2goal.screens.GameScreen;
import ir.eynakgroup.dribble2goal.screens.HelpGameScreen;
import ir.eynakgroup.dribble2goal.screens.HelpSelectGameScreen;
import ir.eynakgroup.dribble2goal.screens.LoadingScreen;
import ir.eynakgroup.dribble2goal.screens.LoginScreen;
import ir.eynakgroup.dribble2goal.screens.MainMenuScreen;
import ir.eynakgroup.dribble2goal.screens.ProfileScreen;
import ir.eynakgroup.dribble2goal.screens.ReconnectScreen;
import ir.eynakgroup.dribble2goal.screens.ResultScreen;
import ir.eynakgroup.dribble2goal.screens.SelectGameScreen;
import ir.eynakgroup.dribble2goal.screens.SettingScreen;
import ir.eynakgroup.dribble2goal.screens.ShopScreen;
import ir.eynakgroup.dribble2goal.screens.SplashScreen;
import ir.eynakgroup.dribble2goal.tween.BallAccessor;
import ir.eynakgroup.dribble2goal.tween.EffectAccessor;
import ir.eynakgroup.dribble2goal.tween.ImageAccessor;
import ir.eynakgroup.dribble2goal.tween.LabelAccessor;
import ir.eynakgroup.dribble2goal.tween.ModelAccessor;
import ir.eynakgroup.dribble2goal.tween.PlayerAccessor;
import ir.eynakgroup.dribble2goal.tween.ProgressCircleAccessor;
import ir.eynakgroup.dribble2goal.tween.ProgressLineAccessor;
import ir.eynakgroup.dribble2goal.tween.ScrollAccessor;
import ir.eynakgroup.dribble2goal.tween.SpriteAccessor;
import ir.eynakgroup.dribble2goal.tween.SteperAccessor;
import ir.eynakgroup.dribble2goal.tween.TableAccessor;
import ir.eynakgroup.dribble2goal.tween.TextFieldAccessor;

public class MyGame extends Game {

    private IModel mModel;
    public static TweenManager mTweenManager;
    public static MyGame mainInstance;
    private Emitter.Listener onShopListener;
    public static boolean isVideoFinished = false;

    public static PurchaseInterface.InAppPurchaseFinishHandler purchaseFinish;
    public static PurchaseInterface.InAppPurchaseConsumeHandler consumeHandler;
    public static PurchaseInterface.InAppPurchaseInterface purchaseFlow;
    public static VidAdInterface.VideoAdInterface vidAdFlow;
    public static VidAdInterface.VideoAdFinishHandler videoAdFinishHandler;
    public static SignInFinishHandler.LoginFinishHandler loginFinishHandler;
    public static PlayServices mPlayServices;

    public MyGame(PurchaseInterface.InAppPurchaseInterface purchase, VidAdInterface.VideoAdInterface videoAds,
                  PlayServices ps) {
        purchaseFlow = purchase;
        vidAdFlow = videoAds;
        mPlayServices = ps;

        videoAdFinishHandler = new VidAdInterface.VideoAdFinishHandler() {
            @Override
            public void onError(int paramInt) {

            }

            @Override
            public void onFinish(VideoAds mVideoAds) {
                //TODO send data to server
                System.out.println("Video Finish ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
                isVideoFinished = true;
            }
        };

        loginFinishHandler = new SignInFinishHandler.LoginFinishHandler() {
            @Override
            public void onError(int paramInt) {

            }

            @Override
            public void onFinish(String email, String id, String imageUrl, String nickName) {
                System.out.println("Login success ================>" + email + id + nickName + imageUrl);
                GamePrefs.getInstance().setUserName(email);
                GamePrefs.getInstance().setPassword(id);
                ServerTool.getInstance().loginGoogle(email, id, nickName);
            }
        };

        purchaseFinish = new PurchaseInterface.InAppPurchaseFinishHandler() {
            @Override
            public void onError(int paramInt) {

            }

            @Override
            public void onFinish(ir.eynakgroup.dribble2goal.Purchase purchase) {
                if (purchase.sku.equals(Constants.SKU_SHIRT_PERS)) {
                    shopholder.setSelectedShare(20);
                    GamePrefs.getInstance().shirt = 20;
                    ServerTool.getInstance().sendShop(0, 21, purchase.token, purchase.developerPayload);
                } else if (purchase.sku.equals(Constants.SKU_SHIRT_EST)) {
                    shopholder.setSelectedShare(21);
                    GamePrefs.getInstance().shirt = 21;
                    ServerTool.getInstance().sendShop(0, 22, purchase.token, purchase.developerPayload);
                } else if (purchase.sku.equals(Constants.SKU_SHIRT_BARCA)) {
                    shopholder.setSelectedShare(22);
                    GamePrefs.getInstance().shirt = 22;
                    ServerTool.getInstance().sendShop(0, 23, purchase.token, purchase.developerPayload);
                } else if (purchase.sku.equals(Constants.SKU_SHIRT_REAL)) {
                    shopholder.setSelectedShare(23);
                    GamePrefs.getInstance().shirt = 23;
                    ServerTool.getInstance().sendShop(0, 24, purchase.token, purchase.developerPayload);
                } else if (purchase.sku.equals(Constants.SKU_500_COINS)) {
                    ServerTool.getInstance().sendShop(1, 0, purchase.token, purchase.developerPayload);
                    GamePrefs.getInstance().coins_num += 500;
                    purchaseFlow.ConsumePurchase("test", consumeHandler);
                } else if (purchase.sku.equals(Constants.SKU_1500_COINS)) {
                    ServerTool.getInstance().sendShop(2, 0, purchase.token, purchase.developerPayload);
                    GamePrefs.getInstance().coins_num += 1500;
                    purchaseFlow.ConsumePurchase("test", consumeHandler);
                } else if (purchase.sku.equals(Constants.SKU_4000_COINS)) {
                    ServerTool.getInstance().sendShop(3, 0, purchase.token, purchase.developerPayload);
                    GamePrefs.getInstance().coins_num += 4000;
                    purchaseFlow.ConsumePurchase("test", consumeHandler);
                } else if (purchase.sku.equals(Constants.SKU_10000_COINS)) {
                    ServerTool.getInstance().sendShop(4, 0, purchase.token, purchase.developerPayload);
                    GamePrefs.getInstance().coins_num += 10000;
                    purchaseFlow.ConsumePurchase("test", consumeHandler);
                }
                ServerTool.getInstance().getCoin();
            }
        };

        consumeHandler = new PurchaseInterface.InAppPurchaseConsumeHandler() {
            @Override
            public void onError(int paramInt) {

            }

            @Override
            public void onFinish(Purchase paramPurchase) {
                System.out.println("consume done------------------------------");
            }
        };


//        ServerTool.getInstance().socket.on("shopDone", onShopListener);
    }

    @Override
    public void create() {
//        Assets.getInstance().init();
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
        Tween.registerAccessor(SelectGameScrollPane.class, new SteperAccessor());
        Tween.registerAccessor(TextField.class, new TextFieldAccessor());
        Tween.registerAccessor(ProgressLine[].class, new ProgressLineAccessor());
        Tween.registerAccessor(ParticleEffectActor.class, new EffectAccessor());
        Tween.registerAccessor(Label.class, new LabelAccessor());
        Tween.registerAccessor(ModelInstance.class, new ModelAccessor());

//        float f = -0.6009111404418945f;
//        System.out.println(Float.floatToIntBits(f) + "***" + Float.toHexString(f) + "***" + (Float.parseFloat(Float.toHexString(f)) == f));

        setSplashScreen();

        Gdx.app.setLogLevel(Application.LOG_ERROR);
    }

    public void setLoginScreen(boolean isLogin) {
        setScreen(new LoginScreen(isLogin));
    }

    public void setFindMatchScreen(int stadium, boolean isPenalty) {
        setScreen(new FindMatchScreen(stadium, isPenalty));
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

    public void setShopScreen() {
        setScreen(new ShopScreen());
    }

    public void setEntranceScreen() {
        setScreen(new EntranceScreen());
    }

    private void setSplashScreen() {
        setScreen(new SplashScreen());
    }

    public void setCoachScreen() {
        setScreen(new CoachScreen());
    }

    public void setProfileScreen() {
        setScreen(new ProfileScreen());
    }

    public void setReconnectScreen() {
        setScreen(new ReconnectScreen());
    }

    public void setGameSelectScreen() {
        setScreen(new SelectGameScreen(false));
    }

    public void setHelpGameSelectScreen() {
        setScreen(new HelpSelectGameScreen());
    }

    public void setPenaltySelectScreen() {
        setScreen(new SelectGameScreen(true));
    }

    public void setResultScreen(MatchStats stat) {
        setScreen(new ResultScreen(stat));
    }

    public void createGame(MatchStats matchStat) {
        setScreen(new GameScreen(matchStat, null));
    }

    public void createHelpGame() {
        setScreen(new HelpGameScreen());
    }

    public void resumeGame(MatchStats matchStat, JSONObject obj) {
        setScreen(new GameScreen(matchStat, obj));
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mTweenManager.update(Gdx.graphics.getDeltaTime());
        super.render();
    }

    public void setEmitter() {
        onShopListener = new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                JSONObject response = (JSONObject) args[0];
                try {
                    //TODO
                    String s = response.getString("err");
                    System.out.println(s + "----");
                } catch (Exception e) {
                    try {
                        String s = response.getString("coinPackage");
                    } catch (Exception e2) {

                    }
                }
            }

        };
    }

    private boolean isScreenOn = false;

    @Override
    public void dispose() {
        super.dispose();

        ServerTool.getInstance().socket.off("finalResult");
    }

    @Override
    public void resume() {
        super.resume();
        System.out.println("resume *************************************");
        isScreenOn = false;
    }

    @Override
    public void pause() {
        super.pause();
        System.out.println("pause *************************************");
        double minute = System.nanoTime() / 1E9;

        while (isScreenOn && !isScreenOn) {
            if (System.nanoTime() / 1E9 - minute > 5) {
                System.exit(0);
                Gdx.app.exit();
                this.dispose();
                isScreenOn = true;
            }
        }
    }
}
