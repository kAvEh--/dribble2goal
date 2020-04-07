package ir.eynakgroup.dribble2goal;

/**
 * Created by kAvEh on 2/19/2016.
 */
public class Constants {
    public static final float SCREEN_WIDTH = 16;
    public static final float SCREEN_HEIGHT = 9;
    public static final float HUD_SCREEN_WIDTH = 1024;
    public static final float HUD_SCREEN_HEIGHT = 576;
    public static final int MAX_MOUSE_JOINT_FORCE = 4;

    // --- HALF STATUS
    public static final int HALF_FIRST = 1;
    public static final int HALF_SECOND = 2;
    public static final int HALF_END = 3;
    public static final int HALF_PENALTY = 4;

    // --- GAME STATES
    public static final int GAME_PRE = 0;
    public static final int GAME_PRE_PENALTY = 20;
    public static final int GAME_PENALTY = 21;
    public static final int GAME_SHOOTING = 1;
    public static final int GAME_RUNNING = 2;
    public static final int GAME_GOAL = 3;
    public static final int GAME_2ND_HALF = 30;
    public static final int GAME_POSITIONING = 4;
    public static final int GAME_WAITING = 6;
    public static final int GAME_WAIT_FOR_AFTER = 7;
    public static final int GAME_WAIT_FOR_AFTER_PENALTY = 27;
    public static final int GAME_AFTER_PENALTY_GOAL = 24;
    public static final int GAME_WINNER = 11;
    public static final int GAME_LOSER = 12;
    public static final int GAME_GOTO_RESULT = 13;
    public static final int GAME_GOTO_PENALTY = 14;

    // ----- Shop Constants
    public static final String SKU_500_COINS = "D2G_500_coins";
    public static final String SKU_1500_COINS = "D2G_1500_coins";
    public static final String SKU_4000_COINS = "D2G_4000_coins";
    public static final String SKU_10000_COINS = "D2G_10000_coins";
    public static final String SKU_SHIRT_PERS = "D2G_shirt_pers";
    public static final String SKU_SHIRT_EST = "D2G_shirt_est";
    public static final String SKU_SHIRT_BARCA = "D2G_shirt_barca";
    public static final String SKU_SHIRT_REAL = "D2G_shirt_real";
}
