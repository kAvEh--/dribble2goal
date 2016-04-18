package ir.eynajgroup.dribble2goal;

/**
 * Created by kAvEh on 2/19/2016.
 */
public class Constants {
    public static final float SCREEN_WIDTH = 16;
    public static final float SCREEN_HEIGHT = 9;
    public static final float HUD_SCREEN_WIDTH = 1024;
    public static final float HUD_SCREEN_HEIGHT = 576;
    public static final int MAX_MOUSE_JOINT_FORCE = 4;

    // --- GAME STATES
    public static final int GAME_PRE = 0;
    public static final int GAME_SHOOTING = 1;
    public static final int GAME_RUNNING = 2;
    public static final int GAME_GOAL = 3;
    public static final int GAME_POSITIONING = 4;
    public static final int GAME_END = 5;
    public static final int GAME_WAITING = 6;
    public static final int GAME_WAIT_FOR_AFTER = 7;
    public static final int GAME_WINNER = 11;
    public static final int GAME_LOSER = 12;
}
