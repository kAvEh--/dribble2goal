package ir.eynakgroup.dribble2goal.template;

/**
 * Created by kAvEh on 2/19/2016.
 */
public class Field {
    private Player myPlayer1;
    private Player myPlayer2;
    private Player myPlayer3;
    private Player myPlayer4;
    private Player myPlayer5;
    private Player oppPlayer1;
    private Player oppPlayer2;
    private Player oppPlayer3;
    private Player oppPlayer4;
    private Player oppPlayer5;
    private Player mGoalKeeper;
    private Ball mBall;

    public Player getMyPlayer1() {
        return myPlayer1;
    }

    public void setMyPlayer1(Player myPlayer1) {
        this.myPlayer1 = myPlayer1;
    }

    public Player getMyPlayer2() {
        return myPlayer2;
    }

    public void setMyPlayer2(Player myPlayer2) {
        this.myPlayer2 = myPlayer2;
    }

    public Player getMyPlayer3() {
        return myPlayer3;
    }

    public void setMyPlayer3(Player myPlayer3) {
        this.myPlayer3 = myPlayer3;
    }

    public Player getOppPlayer1() {
        return oppPlayer1;
    }

    public void setOppPlayer1(Player oppPlayer1) {
        this.oppPlayer1 = oppPlayer1;
    }

    public Player getOppPlayer2() {
        return oppPlayer2;
    }

    public void setOppPlayer2(Player oppPlayer2) {
        this.oppPlayer2 = oppPlayer2;
    }

    public Player getOppPlayer3() {
        return oppPlayer3;
    }

    public void setOppPlayer3(Player oppPlayer3) {
        this.oppPlayer3 = oppPlayer3;
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

    public Player getMyPlayer4() {
        return myPlayer4;
    }

    public void setMyPlayer4(Player myPlayer4) {
        this.myPlayer4 = myPlayer4;
    }

    public Player getMyPlayer5() {
        return myPlayer5;
    }

    public void setMyPlayer5(Player myPlayer5) {
        this.myPlayer5 = myPlayer5;
    }

    public Player getOppPlayer4() {
        return oppPlayer4;
    }

    public void setOppPlayer4(Player oppPlayer4) {
        this.oppPlayer4 = oppPlayer4;
    }

    public Player getOppPlayer5() {
        return oppPlayer5;
    }

    public void setOppPlayer5(Player oppPlayer5) {
        this.oppPlayer5 = oppPlayer5;
    }
}
