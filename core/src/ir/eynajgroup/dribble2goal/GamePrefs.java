package ir.eynajgroup.dribble2goal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by kAvEh on 2/19/2016.
 */
public class GamePrefs {

    private static final String PREFS_NAME = "ir.eynakgroup.dribble2goal";

    private final String key_music = "setting_music";
    private final String key_effect = "setting_effect";
    private final String key_vibrate = "setting_vibrate";
    private final String key_username = "login_username";
    private final String key_password = "login_password";

    private static GamePrefs sInstance;
    private Preferences mPreferences;
    public String name = "kAvEh";
    public int playerId = 0;
    public int position = 1;
    public int position_num = 3;
    public int shirt = 1;
    public int avatar = 1;
    public int coins_num = 500;
    public int level= 1;
    public int xp = 105;
    public int game_played = 0;
    public int game_won = 0;
    public int goals = 0;
    public int winInaRaw = 0;
    public int win_percent = 100;
    public int last5 = 100;
    public boolean[] achievements = new boolean[26];
    public int achieve_goal = 0;
    public int achieve_cleanSheet = 0;
    public int achieve_win = 0;
    public int achieve_winInaRow = 0;
    public boolean isDailyAvailable = false;
    public int winRate = 0;
    public int cleanSheet = 0;
    public int[][] players = new int[5][3];
    public int[] lineup = new int[5];

    private GamePrefs() {}

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
        mPreferences.putString(key_username, password);
        mPreferences.flush();
    }

}
