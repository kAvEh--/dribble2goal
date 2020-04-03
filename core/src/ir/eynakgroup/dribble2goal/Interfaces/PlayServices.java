package ir.eynakgroup.dribble2goal.Interfaces;

import ir.eynakgroup.dribble2goal.SignInFinishHandler;

/**
 * Created by Eynak_PC2 on 11/7/2016.
 */
public interface PlayServices {
    public void signIn(SignInFinishHandler.LoginFinishHandler handler);

    public void signOut(SignInFinishHandler.LoginFinishHandler handler);

    public void rateGame();

    public void unlockAchievement(int achs);

    public void submitScore(int highScore);

    public void showAchievement();

    public void showScore();

    public boolean isSignedIn();
}
