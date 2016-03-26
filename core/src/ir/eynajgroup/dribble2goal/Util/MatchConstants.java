package ir.eynajgroup.dribble2goal.Util;

import com.badlogic.gdx.math.Vector2;

import ir.eynajgroup.dribble2goal.Constants;

/**
 * Created by kAvEh on 3/4/2016.
 */
public class MatchConstants {

    private Vector2[] arrange;

    public int getGoalstoWin(int level) {
        switch (level) {
            case 1:
                return 3;
            case 2:
                return 4;
            default:
                return 3;
        }
    }

    public Vector2[] getP1Arrange(int model) {
        switch (model) {
            case 2:
                arrange = new Vector2[3];
                arrange[0] = new Vector2(-Constants.SCREEN_WIDTH / 4f, Constants.SCREEN_HEIGHT / 8f);
                arrange[1] = new Vector2(0f, Constants.SCREEN_HEIGHT * .3125f);
                arrange[2] = new Vector2(Constants.SCREEN_WIDTH / 4f, Constants.SCREEN_HEIGHT / 8f);

                return arrange;
            case 3:
                arrange = new Vector2[3];
                arrange[0] = new Vector2(-Constants.SCREEN_WIDTH / 4f, Constants.SCREEN_HEIGHT / 8f);
                arrange[1] = new Vector2(-Constants.SCREEN_WIDTH / 8f, Constants.SCREEN_HEIGHT * .3125f);
                arrange[2] = new Vector2(Constants.SCREEN_WIDTH / 4f, Constants.SCREEN_HEIGHT / 4f);

                return arrange;
            default:
                arrange = new Vector2[3];
                arrange[0] = new Vector2(-Constants.SCREEN_WIDTH / 4f, Constants.SCREEN_HEIGHT / 4f);
                arrange[1] = new Vector2(0f, Constants.SCREEN_HEIGHT / 4f);
                arrange[2] = new Vector2(Constants.SCREEN_WIDTH / 4f, Constants.SCREEN_HEIGHT / 4f);

                return arrange;
        }

    }

    public Vector2[] getP2Arrange(int model) {
        switch (model) {
            case 2:
                arrange = new Vector2[3];
                arrange[0] = new Vector2(-Constants.SCREEN_WIDTH / 4f, -Constants.SCREEN_HEIGHT / 8f);
                arrange[1] = new Vector2(0, -Constants.SCREEN_HEIGHT * .3125f);
                arrange[2] = new Vector2(Constants.SCREEN_WIDTH / 4f, -Constants.SCREEN_HEIGHT / 8f);

                return arrange;
            case 3:
                arrange = new Vector2[3];
                arrange[0] = new Vector2(-Constants.SCREEN_WIDTH / 4f, -Constants.SCREEN_HEIGHT / 8f);
                arrange[1] = new Vector2(-Constants.SCREEN_WIDTH / 8f, -Constants.SCREEN_HEIGHT * .3125f);
                arrange[2] = new Vector2(Constants.SCREEN_WIDTH / 4f, -Constants.SCREEN_HEIGHT / 4f);

                return arrange;
            default:
                arrange = new Vector2[3];
                arrange[0] = new Vector2(-Constants.SCREEN_WIDTH / 4f, -Constants.SCREEN_HEIGHT / 4f);
                arrange[1] = new Vector2(0, -Constants.SCREEN_HEIGHT / 4f);
                arrange[2] = new Vector2(Constants.SCREEN_WIDTH / 4f, -Constants.SCREEN_HEIGHT / 4f);

                return arrange;
        }
    }
}
