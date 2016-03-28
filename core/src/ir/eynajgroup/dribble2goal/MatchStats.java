package ir.eynajgroup.dribble2goal;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by kAvEh on 2/21/2016.
 */
public class MatchStats {

    public int roomNum;
    public int matchLevel;
    public int lastTouch;
    public int myGoals;
    public int oppGoals;
    public int roundNum;
    public int GAME_STATE;
    public int myLevel;
    public int oppLevel;
    public int myXp;
    public int oppXp;
    public boolean isMeFirst;
    public int goaltoWin;
    public int myShirt;
    public int oppShirt;
    public int oppAvatar;
    public String myName;
    public String oppName;
    public double myWinRate;
    public double oppWinRate;
    public boolean p1SubReady;
    public boolean p2SubReady;
    public int myFormation;
    public int oppFormation;

    public int[] p1subs;
    public int[] p2subs;

    public int[] myLineup;
    public int[] oppLineup;

    public Vector2[] myStartPosition;
    public Vector2[] oppStartPosition;

    public int[][] myPlayers;
    public int[][] oppPlayers;

    public void reset(int level, boolean is) {
        matchLevel = level;
        goaltoWin = 3;
        lastTouch = 0;
        myGoals = 0;
        oppGoals = 0;
        roundNum = 0;
        GAME_STATE = 0;
        myStartPosition = new Vector2[3];
        oppStartPosition = new Vector2[3];
        myLineup = new int[5];
        oppLineup = new int[5];
        isMeFirst = is;
        p1SubReady = false;
        p1subs = new int[2];
        p2SubReady = false;
        p2subs = new int[2];
        myPlayers = new int[5][3];
        oppPlayers = new int[5][3];
    }
}
