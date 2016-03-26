package ir.eynajgroup.dribble2goal.template;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by kAvEh on 2/19/2016.
 */
public class Field {
    private Player t1Player1;
    private Player t1Player2;
    private Player t1Player3;
    private Player t2Player1;
    private Player t2Player2;
    private Player t2Player3;
    private Player mGoalKeeper;
    private Ball mBall;
    private int mLeftPlayerScore;
    private int mRightPlayerScore;
    private Team mLastWinTeam;
    private boolean leftDragged = false;
    private boolean rightDragged = false;

    private Vector2 t1p1Arrow;
    private Vector2 t1p2Arrow;
    private Vector2 t1p3Arrow;

    private Vector2 t2p1Arrow;
    private Vector2 t2p2Arrow;
    private Vector2 t2p3Arrow;

    public Player getT1Player1() {
        return t1Player1;
    }

    public void setT1Player1(Player t1Player1) {
        this.t1Player1 = t1Player1;
    }

    public Player getT1Player2() {
        return t1Player2;
    }

    public void setT1Player2(Player t1Player2) {
        this.t1Player2 = t1Player2;
    }

    public Player getT1Player3() {
        return t1Player3;
    }

    public void setT1Player3(Player t1Player3) {
        this.t1Player3 = t1Player3;
    }

    public Player getT2Player1() {
        return t2Player1;
    }

    public void setT2Player1(Player t2Player1) {
        this.t2Player1 = t2Player1;
    }

    public Player getT2Player2() {
        return t2Player2;
    }

    public void setT2Player2(Player t2Player2) {
        this.t2Player2 = t2Player2;
    }

    public Player getT2Player3() {
        return t2Player3;
    }

    public void setT2Player3(Player t2Player3) {
        this.t2Player3 = t2Player3;
    }

    public void setGoalKeeper(Player leftPlayer) {
        mGoalKeeper = leftPlayer;
    }

    public Player getGoalKeeper() {
        return mGoalKeeper;
    }

    public Ball getBall() {
        return mBall;
    }

    public void setBall(Ball ball) {
        mBall = ball;
    }

    public int getLeftPlayerScore() {
        return mLeftPlayerScore;
    }

    public void setLeftPlayerScore(int leftPlayerScore) {
        mLeftPlayerScore = leftPlayerScore;
    }

    public int getRightPlayerScore() {
        return mRightPlayerScore;
    }

    public void setRightPlayerScore(int rightPlayerScore) {
        mRightPlayerScore = rightPlayerScore;
    }

    public Team getLastWinTeam() {
        return mLastWinTeam;
    }

    public void setLastWinTeam(Team lastWinTeam) {
        mLastWinTeam = lastWinTeam;
    }

    public boolean isLeftDragged() {
        return leftDragged;
    }

    public void setLeftDragged(boolean leftDragged) {
        this.leftDragged = leftDragged;
    }

    public boolean isRightDragged() {
        return rightDragged;
    }

    public void setRightDragged(boolean rightDragged) {
        this.rightDragged = rightDragged;
    }

    public Vector2 getT1p1Arrow() {
        return t1p1Arrow;
    }

    public void setT1p1Arrow(Vector2 t1p1Arrow) {
        this.t1p1Arrow = t1p1Arrow;
    }

    public Vector2 getT1p2Arrow() {
        return t1p2Arrow;
    }

    public void setT1p2Arrow(Vector2 t1p2Arrow) {
        this.t1p2Arrow = t1p2Arrow;
    }

    public Vector2 getT1p3Arrow() {
        return t1p3Arrow;
    }

    public void setT1p3Arrow(Vector2 t1p3Arrow) {
        this.t1p3Arrow = t1p3Arrow;
    }

    public Vector2 getT2p1Arrow() {
        return t2p1Arrow;
    }

    public void setT2p1Arrow(Vector2 t2p1Arrow) {
        this.t2p1Arrow = t2p1Arrow;
    }

    public Vector2 getT2p2Arrow() {
        return t2p2Arrow;
    }

    public void setT2p2Arrow(Vector2 t2p2Arrow) {
        this.t2p2Arrow = t2p2Arrow;
    }

    public Vector2 getT2p3Arrow() {
        return t2p3Arrow;
    }

    public void setT2p3Arrow(Vector2 t2p3Arrow) {
        this.t2p3Arrow = t2p3Arrow;
    }
}
