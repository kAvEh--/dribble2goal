package ir.eynakgroup.dribble2goal.Util;

import com.badlogic.gdx.math.Vector2;

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


}
