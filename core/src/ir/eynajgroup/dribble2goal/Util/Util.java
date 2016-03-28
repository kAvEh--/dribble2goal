package ir.eynajgroup.dribble2goal.Util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ir.eynajgroup.dribble2goal.Assets;
import ir.eynajgroup.dribble2goal.Constants;

/**
 * Created by kAvEh on 3/9/2016.
 */
public class Util {

    public Texture getShirt(int sh) {
        switch (sh) {
            case 2:
                return Assets.getInstance().shirt2;
            case 3:
                return Assets.getInstance().shirt3;
            case 4:
                return Assets.getInstance().shirt4;
            default:
                return Assets.getInstance().shirt1;
        }
    }

    public Texture getStadium(int id) {
        switch (id) {
            case 2:
                return Assets.getInstance().court_2;
            case 3:
                return Assets.getInstance().court_3;
            case 4:
                return Assets.getInstance().court_4;
            case 5:
                return Assets.getInstance().court_5;
            case 6:
                return Assets.getInstance().court_6;
            default:
                return Assets.getInstance().court_1;
        }
    }

    public Texture getStadiumDark(int id) {
        switch (id) {
            case 2:
                return Assets.getInstance().court_dark_2;
            case 3:
                return Assets.getInstance().court_dark_3;
            case 4:
                return Assets.getInstance().court_dark_4;
            case 5:
                return Assets.getInstance().court_dark_5;
            case 6:
                return Assets.getInstance().court_dark_6;
            default:
                return Assets.getInstance().court_dark_1;
        }
    }

    public Texture getStadiumArc(int id) {
        switch (id) {
            case 2:
                return Assets.getInstance().court_arc_2;
            case 3:
                return Assets.getInstance().court_arc_3;
            case 4:
                return Assets.getInstance().court_arc_4;
            case 5:
                return Assets.getInstance().court_arc_5;
            case 6:
                return Assets.getInstance().court_arc_6;
            default:
                return Assets.getInstance().court_arc_1;
        }
    }

    public Texture getKeeperShirt(int sh) {
        switch (sh) {
            case 2:
                return Assets.getInstance().keeper_2;
            case 3:
                return Assets.getInstance().keeper_3;
            case 4:
                return Assets.getInstance().keeper_4;
            case 5:
                return Assets.getInstance().keeper_5;
            case 6:
                return Assets.getInstance().keeper_6;
            default:
                return Assets.getInstance().keeper_1;
        }
    }

    public Texture getKeeperPiece(int sh) {
        switch (sh) {
            case 2:
                return Assets.getInstance().keeper_piece_2;
            case 3:
                return Assets.getInstance().keeper_piece_3;
            case 4:
                return Assets.getInstance().keeper_piece_4;
            case 5:
                return Assets.getInstance().keeper_piece_5;
            case 6:
                return Assets.getInstance().keeper_piece_6;
            default:
                return Assets.getInstance().keeper_piece_1;
        }
    }

    public Texture getAvatar(int id) {
        switch (id) {
            case 2:
                return Assets.getInstance().avatar_2;
            case 3:
                return Assets.getInstance().avatar_3;
            case 4:
                return Assets.getInstance().avatar_4;
            case 5:
                return Assets.getInstance().avatar_5;
            case 6:
                return Assets.getInstance().avatar_6;
            default:
                return Assets.getInstance().avatar_1;
        }
    }

    public Vector2[] getSettingPosition(int pos) {
        Vector2[] ret = new Vector2[5];
        switch (pos) {
            case 1:
                ret[0] = new Vector2(Constants.HUD_SCREEN_WIDTH * .125f, Constants.HUD_SCREEN_HEIGHT * .15f);
                ret[1] = new Vector2(Constants.HUD_SCREEN_WIDTH * .125f, Constants.HUD_SCREEN_HEIGHT * .3f);
                ret[2] = new Vector2(Constants.HUD_SCREEN_WIDTH * .125f, Constants.HUD_SCREEN_HEIGHT * .45f);
                ret[3] = new Vector2(Constants.HUD_SCREEN_WIDTH * .35f, Constants.HUD_SCREEN_HEIGHT * .08f);
                ret[4] = new Vector2(Constants.HUD_SCREEN_WIDTH * .35f, Constants.HUD_SCREEN_HEIGHT * .2f);
                break;
            case 2:
                ret[0] = new Vector2(Constants.HUD_SCREEN_WIDTH * .15f, Constants.HUD_SCREEN_HEIGHT * .15f);
                ret[1] = new Vector2(Constants.HUD_SCREEN_WIDTH * .1f, Constants.HUD_SCREEN_HEIGHT * .3f);
                ret[2] = new Vector2(Constants.HUD_SCREEN_WIDTH * .15f, Constants.HUD_SCREEN_HEIGHT * .45f);
                ret[3] = new Vector2(Constants.HUD_SCREEN_WIDTH * .35f, Constants.HUD_SCREEN_HEIGHT * .08f);
                ret[4] = new Vector2(Constants.HUD_SCREEN_WIDTH * .35f, Constants.HUD_SCREEN_HEIGHT * .2f);
                break;
            case 3:
                ret[0] = new Vector2(Constants.HUD_SCREEN_WIDTH * .15f, Constants.HUD_SCREEN_HEIGHT * .15f);
                ret[1] = new Vector2(Constants.HUD_SCREEN_WIDTH * .09f, Constants.HUD_SCREEN_HEIGHT * .2f);
                ret[2] = new Vector2(Constants.HUD_SCREEN_WIDTH * .125f, Constants.HUD_SCREEN_HEIGHT * .45f);
                ret[3] = new Vector2(Constants.HUD_SCREEN_WIDTH * .35f, Constants.HUD_SCREEN_HEIGHT * .08f);
                ret[4] = new Vector2(Constants.HUD_SCREEN_WIDTH * .35f, Constants.HUD_SCREEN_HEIGHT * .2f);
                break;
        }

        return ret;
    }

    public Vector2[] getInGameSettingPosition(int pos, int[] players) {
        Vector2[] ret = new Vector2[5];
        switch (pos) {
            case 1:
                ret[players[0]] = new Vector2(Constants.HUD_SCREEN_WIDTH * .214f, Constants.HUD_SCREEN_HEIGHT * .19f);
                ret[players[1]] = new Vector2(Constants.HUD_SCREEN_WIDTH * .214f, Constants.HUD_SCREEN_HEIGHT * .32f);
                ret[players[2]] = new Vector2(Constants.HUD_SCREEN_WIDTH * .214f, Constants.HUD_SCREEN_HEIGHT * .455f);
                ret[players[3]] = new Vector2(Constants.HUD_SCREEN_WIDTH * .383f, Constants.HUD_SCREEN_HEIGHT * .2f);
                ret[players[4]] = new Vector2(Constants.HUD_SCREEN_WIDTH * .383f, Constants.HUD_SCREEN_HEIGHT * .31f);
                break;
            case 2:
                ret[players[0]] = new Vector2(Constants.HUD_SCREEN_WIDTH * .23f, Constants.HUD_SCREEN_HEIGHT * .19f);
                ret[players[1]] = new Vector2(Constants.HUD_SCREEN_WIDTH * .203f, Constants.HUD_SCREEN_HEIGHT * .32f);
                ret[players[2]] = new Vector2(Constants.HUD_SCREEN_WIDTH * .23f, Constants.HUD_SCREEN_HEIGHT * .455f);
                ret[players[3]] = new Vector2(Constants.HUD_SCREEN_WIDTH * .383f, Constants.HUD_SCREEN_HEIGHT * .2f);
                ret[players[4]] = new Vector2(Constants.HUD_SCREEN_WIDTH * .383f, Constants.HUD_SCREEN_HEIGHT * .31f);
                break;
            case 3:
                ret[players[0]] = new Vector2(Constants.HUD_SCREEN_WIDTH * .23f, Constants.HUD_SCREEN_HEIGHT * .19f);
                ret[players[1]] = new Vector2(Constants.HUD_SCREEN_WIDTH * .203f, Constants.HUD_SCREEN_HEIGHT * .25f);
                ret[players[2]] = new Vector2(Constants.HUD_SCREEN_WIDTH * .214f, Constants.HUD_SCREEN_HEIGHT * .455f);
                ret[players[3]] = new Vector2(Constants.HUD_SCREEN_WIDTH * .383f, Constants.HUD_SCREEN_HEIGHT * .2f);
                ret[players[4]] = new Vector2(Constants.HUD_SCREEN_WIDTH * .383f, Constants.HUD_SCREEN_HEIGHT * .31f);
                break;
        }

        for (int i =0; i< 5;i++) {
            System.out.println(players[i] + "====" + ret[players[i]]);
        }

        return ret;
    }

    public Vector2[] getAbovePlayerPosition(int model) {
        Vector2[] arrange;
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

    public Vector2[] getBelowPlayerPosition(int model) {
        Vector2[] arrange;
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
