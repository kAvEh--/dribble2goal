package ir.eynajgroup.dribble2goal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by kAvEh on 2/19/2016.
 */
public class Assets implements Disposable {
    private static Assets instance;

    public static Assets getInstance() {
        if (instance == null) {
            instance = new Assets();
        }
        return instance;
    }

    private Assets() {
    }

    public Texture player;
    public Texture shirt1;
    public Texture shirt2;
    public Texture shirt3;
    public Texture shirt4;
    public Texture ball;
    public Texture stamina;
    public Texture splash;
    public Texture goal_sample;
    public BitmapFont fontDroidSans17;
    public Texture goalImage;
    public Texture pie;
    public Texture pie_slot;
    public Texture ball_light;
    public Texture arrow;
    public Texture main_bg;
    public Texture main_bg_fade;
    public Texture main_arc;

    public Texture main_item_bg;
    public Texture main_item_online;
    public Texture main_item_light;

    public Texture main_item_coach;

    public Texture main_icon_settings;
    public Texture main_icon_shop;

    public Texture profile_background;
    public Texture profile_coin;

    public Texture avatar_1;
    public Texture avatar_2;
    public Texture avatar_3;
    public Texture avatar_4;
    public Texture avatar_5;
    public Texture avatar_6;

    public Texture title_train;
    public Texture arrow_left;
    public Texture arrow_right;
    public Texture setting_formation;
    public Texture selected_player;
    public Texture player_stamina;
    public Texture player_size;
    public Texture player_speed;
    public Texture setting_coin;
    public Texture icon_ok;
    public Texture icon_back;
    public Texture stadium_button_1;
    public Texture stadium_button_2;
    public Texture stadium_button_3;
    public Texture stadium_button_4;
    public Texture stadium_button_5;
    public Texture stadium_button_6;
    public Texture progress_circle;
    public Texture select_game_arc;
    public Texture[] select_game = new Texture[6];
    public Texture shadow;
    public Texture logo;
    public Texture loading_bar_bg;
    public Texture loading_bar_mid;
    public Texture loading_bar_top;
    public Texture profile_page_coins;
    public Texture level_right;
    public Texture level_left;
    public Texture profile_stat_on;
    public Texture profile_stat_off;
    public Texture profile_ach_on;
    public Texture profile_ach_off;
    public Texture profile_gp;
    public Texture profile_gw;
    public Texture profile_wp;
    public Texture profile_last5;
    public Texture achiev_a1;
    public Texture achiev_a2;
    public Texture achiev_a3;
    public Texture achiev_a4;
    public Texture achiev_a5;
    public Texture achiev_b1;
    public Texture achiev_b2;
    public Texture achiev_b3;
    public Texture achiev_b4;
    public Texture achiev_b5;
    public Texture achiev_b6;
    public Texture achiev_c1;
    public Texture achiev_c2;
    public Texture achiev_c3;
    public Texture achiev_c4;
    public Texture achiev_c5;
    public Texture achiev_c6;
    public Texture achiev_d1;
    public Texture achiev_d2;
    public Texture achiev_d3;
    public Texture achiev_d4;

    public Texture setting_music_icon;
    public Texture setting_game_icon;
    public Texture setting_item_bg;
    public Texture setting_button;
    public Texture setting_button_bg;
    public Texture findmatch_main;

    public Texture keeper_piece_1;
    public Texture keeper_piece_2;
    public Texture keeper_piece_3;
    public Texture keeper_piece_4;
    public Texture keeper_piece_5;
    public Texture keeper_piece_6;

    public Texture keeper_1;
    public Texture keeper_2;
    public Texture keeper_3;
    public Texture keeper_4;
    public Texture keeper_5;
    public Texture keeper_6;

    public Texture court_1;
    public Texture court_2;
    public Texture court_3;
    public Texture court_4;
    public Texture court_5;
    public Texture court_6;

    public Texture court_dark_1;
    public Texture court_dark_2;
    public Texture court_dark_3;
    public Texture court_dark_4;
    public Texture court_dark_5;
    public Texture court_dark_6;

    public Texture court_arc_1;
    public Texture court_arc_2;
    public Texture court_arc_3;
    public Texture court_arc_4;
    public Texture court_arc_5;
    public Texture court_arc_6;

    public Texture board_bg;
    public Texture formation_bg;
    public Texture formation_stamina;
    public Texture formation_size;
    public Texture formation_speed;
    public Texture time_bar;

    public Texture login_username;
    public Texture login_pass;
    public Texture login_pass_re;
    public Texture login_email;
    public Texture lose_image;
    public Texture win_image;

    public void init() {
//        Texture.setEnforcePotImages(false);
        player = new Texture(Gdx.files.internal("img/piece_stable.png"));
        shirt1 = new Texture(Gdx.files.internal("img/shirt_1.png"));
        shirt2 = new Texture(Gdx.files.internal("img/shirt_2.png"));
        shirt3 = new Texture(Gdx.files.internal("img/shirt_3.png"));
        shirt4 = new Texture(Gdx.files.internal("img/shirt_4.png"));
        stamina = new Texture(Gdx.files.internal("img/ball.png"));
        splash = new Texture(Gdx.files.internal("img/splash.png"));
        goal_sample = new Texture(Gdx.files.internal("img/goal_sample.png"));
        goalImage = new Texture(Gdx.files.internal("img/goal.jpg"));
        pie = new Texture(Gdx.files.internal("img/stamina_high.png"));
        pie_slot = new Texture(Gdx.files.internal("img/stamina_low.png"));
        ball = new Texture(Gdx.files.internal("img/ball_main.png"));
        ball_light = new Texture(Gdx.files.internal("img/ball_light.png"));
        arrow = new Texture(Gdx.files.internal("img/arrow.png"));
        main_bg = new Texture(Gdx.files.internal("img/main_bg.jpg"));
        main_bg_fade = new Texture(Gdx.files.internal("img/main_bg_fade.jpg"));
        main_arc = new Texture(Gdx.files.internal("img/main_arc.png"));
        main_item_bg = new Texture(Gdx.files.internal("img/main_item_bg.png"));
        main_item_online = new Texture(Gdx.files.internal("img/menu_item_online.png"));
        main_item_light = new Texture(Gdx.files.internal("img/menu_item_light.png"));
        main_item_coach = new Texture(Gdx.files.internal("img/menu_item_coach.png"));
        main_icon_settings = new Texture(Gdx.files.internal("img/icon_settings.png"));
        main_icon_shop = new Texture(Gdx.files.internal("img/icon_shop.png"));
        profile_background = new Texture(Gdx.files.internal("img/profile_background.png"));
        profile_coin = new Texture(Gdx.files.internal("img/profile_coin.png"));
        avatar_1 = new Texture(Gdx.files.internal("img/av_01.png"));
        avatar_2 = new Texture(Gdx.files.internal("img/av_02.png"));
        avatar_3 = new Texture(Gdx.files.internal("img/av_03.png"));
        avatar_4 = new Texture(Gdx.files.internal("img/av_04.png"));
        avatar_5 = new Texture(Gdx.files.internal("img/av_05.png"));
        avatar_6 = new Texture(Gdx.files.internal("img/av_06.png"));
        title_train = new Texture(Gdx.files.internal("img/title_train.png"));
        arrow_left = new Texture(Gdx.files.internal("img/arrow_left.png"));
        arrow_right = new Texture(Gdx.files.internal("img/arrow_right.png"));
        setting_formation = new Texture(Gdx.files.internal("img/setting_formation.png"));
        selected_player = new Texture(Gdx.files.internal("img/selected_player.png"));
        player_stamina = new Texture(Gdx.files.internal("img/player_stamina.png"));
        player_speed = new Texture(Gdx.files.internal("img/player_speed.png"));
        player_size = new Texture(Gdx.files.internal("img/player_size.png"));
        setting_coin = new Texture(Gdx.files.internal("img/setting_coin.png"));
        icon_ok = new Texture(Gdx.files.internal("img/icon_confirm.png"));
        icon_back = new Texture(Gdx.files.internal("img/icon_back.png"));
        stadium_button_1 = new Texture(Gdx.files.internal("img/stadium_button_1.png"));
        stadium_button_2 = new Texture(Gdx.files.internal("img/stadium_button_2.png"));
        stadium_button_3 = new Texture(Gdx.files.internal("img/stadium_button_3.png"));
        stadium_button_4 = new Texture(Gdx.files.internal("img/stadium_button_4.png"));
        stadium_button_5 = new Texture(Gdx.files.internal("img/stadium_button_5.png"));
        stadium_button_6 = new Texture(Gdx.files.internal("img/stadium_button_6.png"));
        progress_circle = new Texture(Gdx.files.internal("img/progress_circle.png"));
        select_game_arc = new Texture(Gdx.files.internal("img/select_game_arc.png"));
        select_game[0] = new Texture(Gdx.files.internal("img/select_game_1.png"));
        select_game[1] = new Texture(Gdx.files.internal("img/select_game_2.png"));
        select_game[2] = new Texture(Gdx.files.internal("img/select_game_3.png"));
        select_game[3] = new Texture(Gdx.files.internal("img/select_game_4.png"));
        select_game[4] = new Texture(Gdx.files.internal("img/select_game_5.png"));
        select_game[5] = new Texture(Gdx.files.internal("img/select_game_6.png"));
        shadow = new Texture(Gdx.files.internal("img/shadow.png"));
        logo = new Texture(Gdx.files.internal("img/logo.png"));
        loading_bar_bg = new Texture(Gdx.files.internal("img/loading_bar_bg.png"));
        loading_bar_mid = new Texture(Gdx.files.internal("img/loading_bar_mid.9.png"));
        loading_bar_top = new Texture(Gdx.files.internal("img/loading_bar_top.png"));
        profile_page_coins = new Texture(Gdx.files.internal("img/profile_coins.png"));
        level_right = new Texture(Gdx.files.internal("img/level_right.png"));
        level_left = new Texture(Gdx.files.internal("img/level_left.png"));
        profile_stat_on = new Texture(Gdx.files.internal("img/stat_on.png"));
        profile_stat_off = new Texture(Gdx.files.internal("img/stat_off.png"));
        profile_ach_on = new Texture(Gdx.files.internal("img/ach_on.png"));
        profile_ach_off = new Texture(Gdx.files.internal("img/ach_off.png"));
        profile_gp = new Texture(Gdx.files.internal("img/profile_gp.png"));
        profile_gw = new Texture(Gdx.files.internal("img/profile_gw.png"));
        profile_wp = new Texture(Gdx.files.internal("img/profile_wp.png"));
        profile_last5 = new Texture(Gdx.files.internal("img/profile_last5.png"));

        achiev_a1 = new Texture(Gdx.files.internal("img/ach_a1.png"));
        achiev_a2 = new Texture(Gdx.files.internal("img/ach_a2.png"));
        achiev_a3 = new Texture(Gdx.files.internal("img/ach_a3.png"));
        achiev_a4 = new Texture(Gdx.files.internal("img/ach_a4.png"));
        achiev_a5 = new Texture(Gdx.files.internal("img/ach_a5.png"));

        achiev_b1 = new Texture(Gdx.files.internal("img/ach_b1.png"));
        achiev_b2 = new Texture(Gdx.files.internal("img/ach_b2.png"));
        achiev_b3 = new Texture(Gdx.files.internal("img/ach_b3.png"));
        achiev_b4 = new Texture(Gdx.files.internal("img/ach_b4.png"));
        achiev_b5 = new Texture(Gdx.files.internal("img/ach_b5.png"));
        achiev_b6 = new Texture(Gdx.files.internal("img/ach_b6.png"));

        achiev_c1 = new Texture(Gdx.files.internal("img/ach_c1.png"));
        achiev_c2 = new Texture(Gdx.files.internal("img/ach_c2.png"));
        achiev_c3 = new Texture(Gdx.files.internal("img/ach_c3.png"));
        achiev_c4 = new Texture(Gdx.files.internal("img/ach_c4.png"));
        achiev_c5 = new Texture(Gdx.files.internal("img/ach_c5.png"));
        achiev_c6 = new Texture(Gdx.files.internal("img/ach_c6.png"));

        achiev_d1 = new Texture(Gdx.files.internal("img/ach_d1.png"));
        achiev_d2 = new Texture(Gdx.files.internal("img/ach_d2.png"));
        achiev_d3 = new Texture(Gdx.files.internal("img/ach_d3.png"));
        achiev_d4 = new Texture(Gdx.files.internal("img/ach_d4.png"));

        board_bg = new Texture(Gdx.files.internal("img/board.png"));
        formation_bg = new Texture(Gdx.files.internal("img/formation_bg.png"));
        formation_stamina = new Texture(Gdx.files.internal("img/stamina_s.png"));
        formation_size = new Texture(Gdx.files.internal("img/size_s.png"));
        formation_speed = new Texture(Gdx.files.internal("img/speed_s.png"));

        findmatch_main = new Texture(Gdx.files.internal("img/findmatch_main.png"));

        setting_music_icon = new Texture(Gdx.files.internal("img/setting_music.png"));
        setting_game_icon = new Texture(Gdx.files.internal("img/setting_game.png"));
        setting_item_bg = new Texture(Gdx.files.internal("img/settings_bg.png"));
        setting_button = new Texture(Gdx.files.internal("img/setting_button.png"));
        setting_button_bg = new Texture(Gdx.files.internal("img/setting_button_bg.png"));

        keeper_piece_1 = new Texture(Gdx.files.internal("img/keeper_piece_1.png"));
        keeper_piece_2 = new Texture(Gdx.files.internal("img/keeper_piece_2.png"));
        keeper_piece_3 = new Texture(Gdx.files.internal("img/keeper_piece_3.png"));
        keeper_piece_4 = new Texture(Gdx.files.internal("img/keeper_piece_4.png"));
        keeper_piece_5 = new Texture(Gdx.files.internal("img/keeper_piece_5.png"));
        keeper_piece_6 = new Texture(Gdx.files.internal("img/keeper_piece_6.png"));

        keeper_1  = new Texture(Gdx.files.internal("img/keeper_1.png"));
        keeper_2  = new Texture(Gdx.files.internal("img/keeper_2.png"));
        keeper_3  = new Texture(Gdx.files.internal("img/keeper_3.png"));
        keeper_4  = new Texture(Gdx.files.internal("img/keeper_4.png"));
        keeper_5  = new Texture(Gdx.files.internal("img/keeper_5.png"));
        keeper_6  = new Texture(Gdx.files.internal("img/keeper_6.png"));

        court_1  = new Texture(Gdx.files.internal("img/court_1.jpg"));
        court_2  = new Texture(Gdx.files.internal("img/court_2.jpg"));
        court_3  = new Texture(Gdx.files.internal("img/court_3.jpg"));
        court_4  = new Texture(Gdx.files.internal("img/court_4.jpg"));
        court_5  = new Texture(Gdx.files.internal("img/court_5.jpg"));
        court_6  = new Texture(Gdx.files.internal("img/court_6.jpg"));

        court_dark_1  = new Texture(Gdx.files.internal("img/court_dark_1.jpg"));
        court_dark_2  = new Texture(Gdx.files.internal("img/court_dark_2.jpg"));
        court_dark_3  = new Texture(Gdx.files.internal("img/court_dark_3.jpg"));
        court_dark_4  = new Texture(Gdx.files.internal("img/court_dark_4.jpg"));
        court_dark_5  = new Texture(Gdx.files.internal("img/court_dark_5.jpg"));
        court_dark_6  = new Texture(Gdx.files.internal("img/court_dark_6.jpg"));

        court_arc_1  = new Texture(Gdx.files.internal("img/court_arc_1.png"));
        court_arc_2  = new Texture(Gdx.files.internal("img/court_arc_2.png"));
        court_arc_3  = new Texture(Gdx.files.internal("img/court_arc_3.png"));
        court_arc_4  = new Texture(Gdx.files.internal("img/court_arc_4.png"));
        court_arc_5  = new Texture(Gdx.files.internal("img/court_arc_5.png"));
        court_arc_6  = new Texture(Gdx.files.internal("img/court_arc_6.png"));

        time_bar  = new Texture(Gdx.files.internal("img/time_bar.png"));

        login_email  = new Texture(Gdx.files.internal("img/login_email.png"));
        login_pass  = new Texture(Gdx.files.internal("img/login_pass.png"));
        login_pass_re  = new Texture(Gdx.files.internal("img/login_pass_re.png"));
        login_username  = new Texture(Gdx.files.internal("img/login_username.png"));

        lose_image  = new Texture(Gdx.files.internal("img/loser.jpg"));
        win_image  = new Texture(Gdx.files.internal("img/winner.jpg"));

        player.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        shirt1.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        shirt2.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        shirt3.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        shirt4.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        ball.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        ball_light.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        stamina.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        splash.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        pie.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        pie_slot.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        arrow.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        main_bg.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        setting_formation.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
        icon_back.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        fontDroidSans17 = new BitmapFont(Gdx.files.internal("fonts/DroidSans17.fnt"));
        fontDroidSans17.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    @Override
    public void dispose() {
        player.dispose();
        ball.dispose();
        shirt1.dispose();
        shirt2.dispose();
        shirt3.dispose();
        shirt4.dispose();
        splash.dispose();
//        bgNormalMap1.dispose();
        fontDroidSans17.dispose();
    }
}
