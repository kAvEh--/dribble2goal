package ir.eynakgroup.dribble2goal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.google.firebase.analytics.FirebaseAnalytics;

import ir.eynakgames.dribble2goal.R;
import ir.eynakgroup.dribble2goal.Interfaces.PlayServices;
import ir.tapsell.tapsellvideosdk.developer.DeveloperInterface;
import util.IabHelper;
import util.IabResult;
import util.Inventory;
import util.Purchase;

public class AndroidLauncher extends AndroidApplication
        implements PurchaseInterface.InAppPurchaseInterface, VidAdInterface.VideoAdInterface,
        PlayServices, GoogleApiClient.OnConnectionFailedListener {
    IabHelper mHelper;
    //for eynak group account
//    String base64EncodedPublicKey = "MIHNMA0GCSqGSIb3DQEBAQUAA4G7ADCBtwKBrwDlpY5Y7rXEAKxRpsI3pa6R441qVjJ9zSUbUsSsPiqp3Rxvt59+lU75okcxS7Fh1O7xLcDcg82I/h0cm9HME8v/b1xiVaGcPV8mjX6gg7BlXjceUQmgtya0qLNMSst7iDp+xVwnywtPljg/vikfSAlaY7wOh7DwCP4lGs3Bj0+AQ1yCPPWYaROcMaaUwQTNB3ucS3JoFHdAiBTvI60DxK6qinYhsdGr5Vk9tkAsAu8CAwEAAQ==";
    // for eynak games account
    String base64EncodedPublicKey = "MIHNMA0GCSqGSIb3DQEBAQUAA4G7ADCBtwKBrwDFKGWLDj+b0bkLW0mDbgD+g2e2SumqmiFgrO/ZygPMjskruNw4HZYNLaYdZ2zX408WLFCrO4hZTE8VGeQvunjdIRb+Xc4lisMzEqleWpYXy2nHLYHR5CEA1YYdahzNkas9hId0m7jB0+dWNkIOetKgReoCsG2F2GnL9MjxclZakm8rU+5kb6xwlPhKl7Iq/f9KLlGC5eCJhFl3eiDvT9Y46D8slFkr4kO+1h/2n+UCAwEAAQ==";
    private String tapsellKey = "mjhmhembmgagtonrcesaenfslrngmjinhhoonjomodbmaoekpdahdlsjijctnbthtimsgc";

    //    static final private String SKU_TEST = "test";
    String devVerif = "kAvEh";

    Purchase p;

    private Activity activity;

    private GameHelper gameHelper;
    private final static int requestCode = 1;

    PurchaseInterface.InAppPurchaseFinishHandler finishHandler;
    PurchaseInterface.InAppPurchaseConsumeHandler consumeHandler;
    VidAdInterface.VideoAdFinishHandler finishVideoHandler;
    SignInFinishHandler.LoginFinishHandler loginFininshHandler;

    private static final String TAG = "aa";
    private static final int RC_SIGN_IN = 007;

    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new IabHelper(this, base64EncodedPublicKey);
        activity = this;

        // You can find it in your Bazaar console, in the Dealers section.
        // It is recommended to add more security than just pasting it in your
        // source code;
        DeveloperInterface.getInstance(this).init(this.tapsellKey, this);
        mHelper = new IabHelper(this, base64EncodedPublicKey);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    return;
                }
                // Hooray, IAB is fully set up!
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
        gameHelper.enableDebugLog(true);

        GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener() {
            @Override
            public void onSignInFailed() {
            }

            @Override
            public void onSignInSucceeded() {
            }
        };

//        gameHelper.setMaxAutoSignInAttempts(5);
        gameHelper.setup(gameHelperListener);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useImmersiveMode = true;
        initialize(new MyGame(this, this, this), config);

        // Assume thisActivity is the current activity
//        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
//        int permissionCheck = ContextCompat.checkSelfPermission(this,
//                Manifest.permission.READ_PHONE_STATE);
        if (getContext().checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") == PackageManager.PERMISSION_GRANTED) {
            Gdx.app.log("INFO", "PERMISSION GRANTED");
        } else {
            Gdx.app.log("ERROR", "PERMISSION DENIED");
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_PHONE_STATE}, 1);
        }
        if (getContext().checkCallingOrSelfPermission("android.permission.ACCESS_COARSE_LOCATION") == PackageManager.PERMISSION_GRANTED) {
            Gdx.app.log("INFO", "PERMISSION GRANTED");
        } else {
            Gdx.app.log("ERROR", "PERMISSION DENIED");
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    System.out.println("siktiiiiiiiiiiiiiiiiiiiiiiiiiiiiiir");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            case 2: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    System.out.println("siktiiiiiiiiiiiiiiiiiiiiiiiiiiiiiir");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    // //////// bazar methods //////////
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result,
                                             Inventory inventory) {
            Log.d(TAG, "Query inventory finished.");
            if (result.isFailure()) {
                Log.d(TAG, "Failed to query inventory: " + result);
                return;
            }
            Log.d(TAG, "Query inventory was successful.");
            /*
             * Check for items we own. Notice that for each purchase, we check
			 * the developer payload to see if it's correct! See
			 * verifyDeveloperPayload().
			 */
            Purchase gasPurchase2 = inventory.getPurchase(Constants.SKU_500_COINS);
            if (gasPurchase2 != null) {
                mHelper.consumeAsync(inventory.getPurchase(Constants.SKU_500_COINS),
                        mConsumeFinishedListener);
                return;
            }
            gasPurchase2 = inventory.getPurchase(Constants.SKU_1500_COINS);
            if (gasPurchase2 != null) {
                mHelper.consumeAsync(inventory.getPurchase(Constants.SKU_1500_COINS),
                        mConsumeFinishedListener);
                return;
            }
            gasPurchase2 = inventory.getPurchase(Constants.SKU_4000_COINS);
            if (gasPurchase2 != null) {
                mHelper.consumeAsync(inventory.getPurchase(Constants.SKU_4000_COINS),
                        mConsumeFinishedListener);
                return;
            }
            gasPurchase2 = inventory.getPurchase(Constants.SKU_10000_COINS);
            if (gasPurchase2 != null) {
                mHelper.consumeAsync(inventory.getPurchase(Constants.SKU_10000_COINS),
                        mConsumeFinishedListener);
                return;
            }

            Log.d(TAG, "Initial inventory query finished; enabling main UI.");
        }
    };

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            p = purchase;
            if (result.isFailure()) {
                Log.d(TAG, "Error purchasing: " + result);
                return;
            } else if (purchase.getSku().equals(Constants.SKU_500_COINS) ||
                    purchase.getSku().equals(Constants.SKU_1500_COINS) ||
                    purchase.getSku().equals(Constants.SKU_4000_COINS) ||
                    purchase.getSku().equals(Constants.SKU_10000_COINS) ||
                    purchase.getSku().equals(Constants.SKU_SHIRT_BARCA) ||
                    purchase.getSku().equals(Constants.SKU_SHIRT_EST) ||
                    purchase.getSku().equals(Constants.SKU_SHIRT_PERS) ||
                    purchase.getSku().equals(Constants.SKU_SHIRT_REAL)) {
                ir.eynakgroup.dribble2goal.Purchase p2 = new ir.eynakgroup.dribble2goal.Purchase();
                //TODO set purchase params
                p2.itemType = purchase.getItemType();
                p2.token = purchase.getToken();
                p2.packageName = purchase.getPackageName();
                p2.developerPayload = purchase.getDeveloperPayload();
                p2.sku = purchase.getSku();
                finishHandler.onFinish(p2);
            }
        }
    };

    @Override
    public void StartPurchaseFlow(String sku, PurchaseInterface.InAppPurchaseFinishHandler fHandler) {
        finishHandler = fHandler;
        try {
            mHelper.launchPurchaseFlow(this, sku, requestCode,
                    mPurchaseFinishedListener, devVerif);
        } catch (Exception e) {
            System.out.println("exeption");
        }
    }

    @Override
    public void ConsumePurchase(final String sku, PurchaseInterface.InAppPurchaseConsumeHandler cHandler) {
        consumeHandler = cHandler;

        this.runOnUiThread(new Runnable() {
            public void run() {
                mHelper.consumeAsync(p,
                        mConsumeFinishedListener);
            }
        });
    }

    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
            new IabHelper.OnConsumeFinishedListener() {
                public void onConsumeFinished(Purchase purchase, IabResult result) {
                    if (result.isSuccess()) {
                        // provision the in-app purchase to the user
                        // (for example, credit 50 gold coins to player's character)
                        ir.eynakgroup.dribble2goal.Purchase p2 = new ir.eynakgroup.dribble2goal.Purchase();
                        //TODO set purchase params
                        p2.itemType = purchase.getItemType();
                        p2.developerPayload = purchase.getDeveloperPayload();
                        p2.sku = purchase.getSku();
                        p2.token = purchase.getToken();
                        consumeHandler.onFinish(p2);
                    } else {
                        // handle error
                    }
                }
            };

    @Override
    public void LoadVideoAd(VidAdInterface.VideoAdFinishHandler finishHandler) {
        this.finishVideoHandler = finishHandler;

        this.runOnUiThread(new Runnable() {
            public void run() {
                DeveloperInterface.getInstance(activity)
                        .showNewVideo(activity,
                                DeveloperInterface.TAPSELL_DIRECT_ADD_REQUEST_CODE,
                                DeveloperInterface.DEFAULT_MIN_AWARD,
                                DeveloperInterface.VideoPlay_TYPE_NON_SKIPPABLE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        gameHelper.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + ","
                + data);

        try {
            if (requestCode == DeveloperInterface.TAPSELL_DIRECT_ADD_REQUEST_CODE) {
                VideoAds tmp = new VideoAds();
                tmp.result = true;
                finishVideoHandler.onFinish(tmp);
            } else if (requestCode == RC_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleSignInResult(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        } else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        gameHelper.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) {
            mHelper.dispose();
        }
        mHelper = null;
        android.os.Process.killProcess(android.os.Process.myPid());
    }

//    @Override
//    public void signIn() {
//        try {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    gameHelper.beginUserInitiatedSignIn();
//                }
//            });
//        } catch (Exception e) {
//            Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
//        }
//    }
//
//    @Override
//    public void signOut() {
//        try {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    gameHelper.signOut();
//                }
//            });
//        } catch (Exception e) {
//            Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
//        }
//    }

    @Override
    public void rateGame() {
        String str = "Your PlayStore Link";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
    }

    @Override
    public void unlockAchievement(int achs) {
        switch (achs) {
            case 1:
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_win_10));
                break;
            case 2:
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_win_50));
                break;
            case 3:
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_win_200));
                break;
            case 4:
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_win_500));
                break;
            case 5:
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_win_1000));
                break;
            case 6:
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_score_10));
                break;
            case 7:
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_score_50));
                break;
            case 8:
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_score_200));
                break;
            case 9:
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_score_500));
                break;
            case 10:
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_score_1000));
                break;
            case 11:
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_score_5000));
                break;
            case 12:
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_keep_5));
                break;
            case 13:
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_keep_20));
                break;
            case 14:
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_keep_50));
                break;
            case 15:
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_keep_100));
                break;
            case 16:
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_keep_200));
                break;
            case 17:
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_keep_500));
                break;
            case 18:
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_inraw_2));
                break;
            case 19:
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_inraw_5));
                break;
            case 20:
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_inraw_10));
                break;
            case 21:
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_inraw_20));
                break;
        }
    }

    @Override
    public void submitScore(int highScore) {
//        if (isSignedIn() == true) {
//            Games.Leaderboards.submitScore(gameHelper.getApiClient(),
//                    getString(R.string.leaderboard_highest), highScore);
//        }
    }

    @Override
    public void showAchievement() {
        if (isSignedIn() == true) {
            startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), requestCode);
        } else {
//            signIn();
        }
    }

    @Override
    public void showScore() {
//        if (isSignedIn() == true) {
//            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
//                    getString(R.string.leaderboard_highest)), requestCode);
//        } else {
//            signIn();
//        }
    }

    public void signIn(SignInFinishHandler.LoginFinishHandler handler) {
        loginFininshHandler = handler;
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    public void signOut(SignInFinishHandler.LoginFinishHandler handler) {
        loginFininshHandler = handler;
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                    }
                });
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                    }
                });
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());
            System.out.println(acct.getId() + "-----" + acct.getIdToken() + "-----" + acct.getServerAuthCode());

            String personName = acct.getDisplayName();
//            String personPhotoUrl = acct.getPhotoUrl().toString();
//            String email = acct.getEmail();

            GamePrefs.getInstance().setUserName(acct.getEmail());
            GamePrefs.getInstance().setPassword(acct.getId());

            if (loginFininshHandler != null)
                loginFininshHandler.onFinish(acct.getEmail(), acct.getId(), acct.getPhotoUrl().toString(), acct.getDisplayName());

//            Log.e(TAG, "Name: " + personName + ", email: " + email
//                    + ", Image: " + personPhotoUrl);
        } else {
//            GamePrefs.getInstance().setUserName("");
//            GamePrefs.getInstance().setPassword("");
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        gameHelper.onStart(this);

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void showProgressDialog() {
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
        } else {
        }
    }

    @Override
    public boolean isSignedIn() {
//        mGoogleApiClient.isConnected();
        return gameHelper.isSignedIn();
    }
}
