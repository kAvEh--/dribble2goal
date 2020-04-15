package ir.eynakgroup.dribble2goal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import ir.eynakgroup.dribble2goal.Server.ServerTool;
import ir.eynakgroup.dribble2goal.model.User;

/**
 * Created by kAvEh on 2/19/2016.
 */
public class GamePrefs {

    private static final String PREFS_NAME = "com.kaveh.dribble2goal";

    private final String key_music = "setting_music";
    private final String key_effect = "setting_effect";
    private final String key_vibrate = "setting_vibrate";
    private final String key_username = "login_username";
    private final String key_password = "login_password";
    private final String key_hint_available_1 = "setting_hint_1";
    private final String key_hint_available_2 = "setting_hint_2";
    private final String key_hint_available_3 = "setting_hint_3";

    private static GamePrefs sInstance;
    private Preferences mPreferences;
    public User user = new User();
    public boolean isDailyAvailable = false;

    private GamePrefs() {
    }

    public static GamePrefs getInstance() {
        if (sInstance == null) {
            sInstance = new GamePrefs();
            sInstance.mPreferences = Gdx.app.getPreferences(PREFS_NAME);
        }
        return sInstance;
    }

    public int isMusicOn() {
        return mPreferences.getInteger(key_music, 1);
    }

    public void setMusicState(int state) {
        mPreferences.putInteger(key_music, state);
        mPreferences.flush();
    }

    public int isEffectOn() {
        return mPreferences.getInteger(key_effect, 1);
    }

    public void setEffectState(int state) {
        mPreferences.putInteger(key_effect, state);
        mPreferences.flush();
    }

    public int isVibrateOn() {
        return mPreferences.getInteger(key_vibrate, 0);
    }

    public void setVibrateState(int state) {
        mPreferences.putInteger(key_vibrate, state);
        mPreferences.flush();
    }

    public String getUserName() {
        return mPreferences.getString(key_username);
    }

    public void setUserName(String username) {
        mPreferences.putString(key_username, username);
        mPreferences.flush();
    }

    public String getPassword() {
        return mPreferences.getString(key_password);
    }

    public void setPassword(String password) {
        mPreferences.putString(key_password, password);
        mPreferences.flush();
    }

    public int getHintStateMain() {
        return mPreferences.getInteger(key_hint_available_1, 0);
    }

    public int getHintStateFind() {
        return mPreferences.getInteger(key_hint_available_2, 0);
    }

    public int getHintStateSquad() {
        return mPreferences.getInteger(key_hint_available_3, 0);
    }

    public void setHintState(int page, int state) {
        switch (page) {
            case 1:
                mPreferences.putInteger(key_hint_available_1, state);
                mPreferences.flush();
                break;
            case 2:
                mPreferences.putInteger(key_hint_available_2, state);
                mPreferences.flush();
                break;
            case 3:
                mPreferences.putInteger(key_hint_available_3, state);
                mPreferences.flush();
                break;
        }
    }

}
