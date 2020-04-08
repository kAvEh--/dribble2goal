package ir.eynakgroup.dribble2goal.Util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ir.eynakgroup.dribble2goal.Assets;
import ir.eynakgroup.dribble2goal.Constants;
import ir.eynakgroup.dribble2goal.GamePrefs;

/**
 * Created by kAvEh on 3/9/2016.
 */
public class Util {

    public Texture getShirt(int sh) {
        switch (sh) {
            case 2:
                return Assets.getInstance().shirts[1];
            case 3:
                return Assets.getInstance().shirts[2];
            case 4:
                return Assets.getInstance().shirts[3];
            case 5:
                return Assets.getInstance().shirts[4];
            case 6:
                return Assets.getInstance().shirts[5];
            case 7:
                return Assets.getInstance().shirts[6];
            case 8:
                return Assets.getInstance().shirts[7];
            case 9:
                return Assets.getInstance().shirts[8];
            case 10:
                return Assets.getInstance().shirts[9];
            case 11:
                return Assets.getInstance().shirts[10];
            case 12:
                return Assets.getInstance().shirts[11];
            case 13:
                return Assets.getInstance().shirts[12];
            case 14:
                return Assets.getInstance().shirts[13];
            case 15:
                return Assets.getInstance().shirts[14];
            case 16:
                return Assets.getInstance().shirts[15];
            case 17:
                return Assets.getInstance().shirts[16];
            case 18:
                return Assets.getInstance().shirts[17];
            case 19:
                return Assets.getInstance().shirts[18];
            case 20:
                return Assets.getInstance().shirts[19];
            case 21:
                return Assets.getInstance().shirts[20];
            case 22:
                return Assets.getInstance().shirts[21];
            case 23:
                return Assets.getInstance().shirts[22];
            case 24:
                return Assets.getInstance().shirts[23];
            default:
                return Assets.getInstance().shirts[0];
        }
    }

    public Texture getStadium(int id) {
        if (id > 6) {
            id -= 6;
        }
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
        if (id > 6) {
            id -= 6;
        }
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
        if (id > 6) {
            id -= 6;
        }
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
        if (sh > 6) {
            sh -= 6;
        }
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

    public Texture getBoard(int sh) {
        if (sh > 6) {
            sh -= 6;
        }
        switch (sh) {
            case 2:
                return Assets.getInstance().board_bg_2;
            case 3:
                return Assets.getInstance().board_bg_3;
            case 4:
                return Assets.getInstance().board_bg_4;
            case 5:
                return Assets.getInstance().board_bg_5;
            default:
                return Assets.getInstance().board_bg_1;
        }
    }

    public Texture getKeeperPiece(int sh) {
        if (sh > 6) {
            sh -= 6;
        }
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

    public String displayName(String name) {
        String tmp = "";
        if (name.length() > 10) {
            tmp = name.substring(0, 8) + "...";
        }
        return tmp;
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

    public Vector2[] getInGameSettingPosition(int pos) {
        Vector2[] ret = new Vector2[5];
        switch (pos) {
            case 1:
                ret[0] = new Vector2(Constants.HUD_SCREEN_WIDTH * .214f, Constants.HUD_SCREEN_HEIGHT * .35f);
                ret[1] = new Vector2(Constants.HUD_SCREEN_WIDTH * .214f, Constants.HUD_SCREEN_HEIGHT * .48f);
                ret[2] = new Vector2(Constants.HUD_SCREEN_WIDTH * .214f, Constants.HUD_SCREEN_HEIGHT * .615f);
                ret[3] = new Vector2(Constants.HUD_SCREEN_WIDTH * .383f, Constants.HUD_SCREEN_HEIGHT * .36f);
                ret[4] = new Vector2(Constants.HUD_SCREEN_WIDTH * .383f, Constants.HUD_SCREEN_HEIGHT * .47f);
                break;
            case 2:
                ret[0] = new Vector2(Constants.HUD_SCREEN_WIDTH * .23f, Constants.HUD_SCREEN_HEIGHT * .35f);
                ret[1] = new Vector2(Constants.HUD_SCREEN_WIDTH * .203f, Constants.HUD_SCREEN_HEIGHT * .48f);
                ret[2] = new Vector2(Constants.HUD_SCREEN_WIDTH * .23f, Constants.HUD_SCREEN_HEIGHT * .615f);
                ret[3] = new Vector2(Constants.HUD_SCREEN_WIDTH * .383f, Constants.HUD_SCREEN_HEIGHT * .36f);
                ret[4] = new Vector2(Constants.HUD_SCREEN_WIDTH * .383f, Constants.HUD_SCREEN_HEIGHT * .47f);
                break;
            case 3:
                ret[0] = new Vector2(Constants.HUD_SCREEN_WIDTH * .23f, Constants.HUD_SCREEN_HEIGHT * .35f);
                ret[1] = new Vector2(Constants.HUD_SCREEN_WIDTH * .203f, Constants.HUD_SCREEN_HEIGHT * .44f);
                ret[2] = new Vector2(Constants.HUD_SCREEN_WIDTH * .214f, Constants.HUD_SCREEN_HEIGHT * .615f);
                ret[3] = new Vector2(Constants.HUD_SCREEN_WIDTH * .383f, Constants.HUD_SCREEN_HEIGHT * .36f);
                ret[4] = new Vector2(Constants.HUD_SCREEN_WIDTH * .383f, Constants.HUD_SCREEN_HEIGHT * .47f);
                break;
        }

        return ret;
    }

    public Vector2[] getAbovePlayerPosition(int model, int[] players) {
        Vector2[] arrange;
        switch (model) {
            case 2:
                arrange = new Vector2[5];
                arrange[players[0]] = new Vector2(-Constants.SCREEN_WIDTH / 4f, Constants.SCREEN_HEIGHT / 8f);
                arrange[players[1]] = new Vector2(0f, Constants.SCREEN_HEIGHT * .3125f);
                arrange[players[2]] = new Vector2(Constants.SCREEN_WIDTH / 4f, Constants.SCREEN_HEIGHT / 8f);
                arrange[players[3]] = new Vector2(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
                arrange[players[4]] = new Vector2(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

                return arrange;
            case 3:
                arrange = new Vector2[5];
                arrange[players[0]] = new Vector2(-Constants.SCREEN_WIDTH / 4f, Constants.SCREEN_HEIGHT / 8f);
                arrange[players[1]] = new Vector2(-Constants.SCREEN_WIDTH / 8f, Constants.SCREEN_HEIGHT * .3125f);
                arrange[players[2]] = new Vector2(Constants.SCREEN_WIDTH / 4f, Constants.SCREEN_HEIGHT / 4f);
                arrange[players[3]] = new Vector2(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
                arrange[players[4]] = new Vector2(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

                return arrange;
            default:
                arrange = new Vector2[5];
                arrange[players[0]] = new Vector2(-Constants.SCREEN_WIDTH / 4f, Constants.SCREEN_HEIGHT / 4f);
                arrange[players[1]] = new Vector2(0f, Constants.SCREEN_HEIGHT / 4f);
                arrange[players[2]] = new Vector2(Constants.SCREEN_WIDTH / 4f, Constants.SCREEN_HEIGHT / 4f);
                arrange[players[3]] = new Vector2(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
                arrange[players[4]] = new Vector2(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

                return arrange;
        }
    }

    public Vector2[] getPenaltyPosition1(int num) {
        Vector2[] arrange;
        arrange = new Vector2[5];
        arrange[0] = new Vector2(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        arrange[1] = new Vector2(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        arrange[2] = new Vector2(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        arrange[3] = new Vector2(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        arrange[4] = new Vector2(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        arrange[num] = new Vector2(-Constants.SCREEN_WIDTH / 3f, 0);

        return arrange;
    }

    public Vector2[] getPenaltyPosition2(int num) {
        Vector2[] arrange;
        arrange = new Vector2[5];
        arrange[0] = new Vector2(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        arrange[1] = new Vector2(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        arrange[2] = new Vector2(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        arrange[3] = new Vector2(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        arrange[4] = new Vector2(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        arrange[num] = new Vector2(-Constants.SCREEN_WIDTH / 3f, Constants.SCREEN_HEIGHT / 2.2f);

        return arrange;
    }

    public Vector2[] getBelowPlayerPosition(int model, int[] players) {
        Vector2[] arrange;
        switch (model) {
            case 2:
                arrange = new Vector2[5];
                arrange[players[0]] = new Vector2(-Constants.SCREEN_WIDTH / 4f, -Constants.SCREEN_HEIGHT / 8f);
                arrange[players[1]] = new Vector2(0, -Constants.SCREEN_HEIGHT * .3125f);
                arrange[players[2]] = new Vector2(Constants.SCREEN_WIDTH / 4f, -Constants.SCREEN_HEIGHT / 8f);
                arrange[players[3]] = new Vector2(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
                arrange[players[4]] = new Vector2(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

                return arrange;
            case 3:
                arrange = new Vector2[5];
                arrange[players[0]] = new Vector2(-Constants.SCREEN_WIDTH / 4f, -Constants.SCREEN_HEIGHT / 8f);
                arrange[players[1]] = new Vector2(-Constants.SCREEN_WIDTH / 8f, -Constants.SCREEN_HEIGHT * .3125f);
                arrange[players[2]] = new Vector2(Constants.SCREEN_WIDTH / 4f, -Constants.SCREEN_HEIGHT / 4f);
                arrange[players[3]] = new Vector2(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
                arrange[players[4]] = new Vector2(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

                return arrange;
            default:
                arrange = new Vector2[5];
                arrange[players[0]] = new Vector2(-Constants.SCREEN_WIDTH / 4f, -Constants.SCREEN_HEIGHT / 4f);
                arrange[players[1]] = new Vector2(0, -Constants.SCREEN_HEIGHT / 4f);
                arrange[players[2]] = new Vector2(Constants.SCREEN_WIDTH / 4f, -Constants.SCREEN_HEIGHT / 4f);
                arrange[players[3]] = new Vector2(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
                arrange[players[4]] = new Vector2(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

                return arrange;
        }
    }

    public String coinsStyle() {
        int coins = GamePrefs.getInstance().coins_num;
        String ret;
        if (coins >= 1000000000) {
            ret = String.valueOf(coins / 1000000000) + "B";
        } else if (coins >= 1000000) {
            ret = String.valueOf(coins / 1000000) + "M";
        } else if (coins >= 1000) {
            ret = String.valueOf(coins / 1000) + "k";
        } else {
            ret = String.valueOf(coins);
        }

        return ret;
    }
}