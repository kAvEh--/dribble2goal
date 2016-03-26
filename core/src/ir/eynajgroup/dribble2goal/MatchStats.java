package ir.eynajgroup.dribble2goal;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by kAvEh on 2/21/2016.
 */
public class MatchStats {

    public int matchLevel;
    public int lastTouch;
    public int Team1Goals;
    public int Team2Goals;
    public int roundNum;
    public int GAME_STATE;
    public boolean isMeFirst;
    public int p1Arrange;
    public int p2Arrange;
    public int goaltoWin;
    public int p1Shirt;
    public int p2Shirt;
    public int oppAvatar;
    public String p1Name;
    public String p2Name;
    public boolean p1SubReady;
    public boolean p2SubReady;
    public int[] p1subs;
    public int[] p2subs;

    public int[] p1formation;
    public int[] p2formation;

    public Vector2[] p1StartPosition;
    public Vector2[] p2StartPosition;

    public int[][] T1players;
    public int[][] T2players;

    public void reset(int level, boolean is, int p1, int p2) {
        matchLevel = level;
        switch (level) {
            case 1:
                goaltoWin = 3;
                break;
            case 2:
                goaltoWin = 4;
                break;
            default:
                goaltoWin = 3;
                break;
        }
        lastTouch = 0;
        Team1Goals = 0;
        Team2Goals = 0;
        roundNum = 0;
        GAME_STATE = 0;
        p1StartPosition = new Vector2[3];
        p2StartPosition = new Vector2[3];
        p1formation = new int[5];
        p2formation = new int[5];
        isMeFirst = is;
        p1Arrange = p1;
        p2Arrange = p2;
        p1SubReady = false;
        p1subs = new int[2];
        p2SubReady = false;
        p2subs = new int[2];
        oppAvatar = 3;
        T1players = new int[5][3];
        T2players = new int[5][3];
        T1players[0][0] = 1;
        T1players[0][1] = 3;
        T1players[0][2] = 1;
        T1players[1][0] = 5;
        T1players[1][1] = 1;
        T1players[1][2] = 1;
        T1players[2][0] = 3;
        T1players[2][1] = 1;
        T1players[2][2] = 1;
        T1players[3][0] = 2;
        T1players[3][1] = 5;
        T1players[3][2] = 1;
        T1players[4][0] = 4;
        T1players[4][1] = 5;
        T1players[4][2] = 2;

        p1formation[0] = 2;
        p1formation[1] = 0;
        p1formation[2] = 4;
        p1formation[3] = 1;
        p1formation[4] = 3;

        p2formation[0] = 1;
        p2formation[1] = 4;
        p2formation[2] = 3;
        p2formation[3] = 2;
        p2formation[4] = 0;
        p1Name = "Hatefi";
        p2Name = "kAvEh";
    }
}
