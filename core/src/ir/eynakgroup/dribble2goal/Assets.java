package ir.eynakgroup.dribble2goal;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by kAvEh on 2/19/2016.
 */
public class Assets implements Disposable {
    private static Assets instance;
    public static AssetManager assetManager;

    public static Assets getInstance() {
        if (instance == null) {
            instance = new Assets();
            assetManager = new AssetManager();
        }
        return instance;
    }

    private Assets() {
    }

    public Texture[] player_stable;
    public Texture[] shirts = new Texture[24];
    public Texture ball;
    public Texture ball_light;
    public Texture ball_shadow;
    public Texture goal_sample;
    public Texture goalImage;
    public Texture penaltyImage;
    public Texture secondHalfImage;
    public Texture arrow;
    public Texture main_bg;
    public Texture main_bg_fade;
    public Texture main_arc;
    public Texture penalty_icon;
    public Texture penalty_text;

    public Texture main_item_bg;
    public Texture main_item_online;
    public Texture main_item_light;

    public Texture main_item_coach;

    public Texture main_icon_settings;
    public Texture main_icon_shop;
    public Texture main_icon_gift;

    public Texture profile_background;
    public Texture profile_coin;
    public Texture profile_coin_right;

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
    public Texture my_player_on;
    public Texture player_stamina;
    public Texture player_size;
    public Texture player_speed;
    public Texture player_stamina_text;
    public Texture player_size_text;
    public Texture player_speed_text;
    public Texture setting_coin;
    public Texture icon_ok;
    public Texture icon_back;
    public Texture stadium_button_1;
    public Texture stadium_button_2_lock;
    public Texture stadium_button_2;
    public Texture stadium_button_3_lock;
    public Texture stadium_button_3;
    public Texture stadium_button_4_lock;
    public Texture stadium_button_4;
    public Texture stadium_button_5_lock;
    public Texture stadium_button_5;
    public Texture stadium_button_6_lock;
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
    public Texture setting_icon;
    public Texture setting_game_icon;
    public Texture setting_item_bg;
    public Texture setting_logout;
    public Texture setting_button;
    public Texture setting_button_bg;
    public Texture setting_music_bg;
    public Texture setting_sound_bg;
    public Texture setting_vibe_bg;
    public Texture setting_dot;
    public Texture setting_shop_1;
    public Texture setting_shop_2;
    public Texture setting_shop_3;
    public Texture setting_shop_4;
    public Texture setting_shop_5;
    public Texture findmatch_main;
    public Texture result_main;

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

    public Texture board_bg_1;
    public Texture board_bg_2;
    public Texture board_bg_3;
    public Texture board_bg_4;
    public Texture board_bg_5;
    public Texture formation_bg;
    public Texture formation_stamina;
    public Texture formation_size;
    public Texture formation_speed;
    public Texture time_bar;
    public Texture stamina_bar;

    public Texture login;
    public Texture register;
    public Texture login_google;
    public Texture login_username;
    public Texture login_pass;
    public Texture login_pass_re;
    public Texture login_email;
    public Texture lose_image;
    public Texture win_image;

    public Texture page_indicator_dot;
    public Texture shop_tab_coins;
    public Texture shop_tab_app;
    public Texture shop_tab_coins_on;
    public Texture shop_tab_app_on;
    public Texture[] shop_shirts = new Texture[24];
    public Texture[] shop_shirts_bg = new Texture[24];
    public Texture shop_coin_500;
    public Texture shop_coin_1500;
    public Texture shop_coin_4k;
    public Texture shop_coin_10k;
    public Texture shop_shirt_select;
    public Texture shop_shirt_selected;

    public Texture prize_1;
    public Texture prize_2;
    public Texture prize_3;
    public Texture prize_4;
    public Texture prize_5;

    public Texture popup_bg;

    public Texture squadlabel;
    public Texture onevonelabel;

    public Texture dark_bg;

    public Texture loading_bar;

    public Texture coins_sample;

    public Texture icon_resign;
    public Texture icon_finish;

    public Texture goaler_up;
    public Texture goaler_down;

    public Texture test_image;

    public Music main_theme;
    public Music coins;
    public Music stadium;
    public Music goal;
    public Music win;
    public Music lose;
    public Music kick;
    public Music upgrade;
    public Music match_found;
    public Music free_coin;
    public Music whistle;
    public Music click;

    public Texture help_bg;
    public Texture help_bg_1;
    public Texture help_bg_2;
    public Texture help_bg_3;
    public Texture help_bg_4;
    public Texture help_bg_5;
    public Texture help_bg_6;
    public Texture help_bg_7;
    public Texture help_box;
    public Texture help_ic_next;
    public Texture help_ic_skip;

    public void initLoading() {
        assetManager.load("img/main_bg_fade.jpg", Texture.class);
        assetManager.load("img/logo.png", Texture.class);
        assetManager.load("img/loading_bar.png", Texture.class);
    }

    public void initSplashTexture() {
        main_bg_fade = assetManager.get("img/main_bg_fade.jpg", Texture.class);
        logo = assetManager.get("img/logo.png", Texture.class);
        loading_bar = assetManager.get("img/loading_bar.png", Texture.class);
    }

    public void loadingAssests() {
        assetManager.load("sound/main_theme.mp3", Music.class);
        assetManager.load("sound/crowd.mp3", Music.class);
        assetManager.load("sound/coins_game.mp3", Music.class);
        assetManager.load("sound/goal_scored.mp3", Music.class);
        assetManager.load("sound/upgrade.mp3", Music.class);
        assetManager.load("sound/match_find.mp3", Music.class);
        assetManager.load("sound/free_coin.mp3", Music.class);
        assetManager.load("sound/whistle.mp3", Music.class);
        assetManager.load("sound/click.wav", Music.class);

        assetManager.load("img/main_bg.jpg", Texture.class);
        assetManager.load("img/piece_stable_1.png", Texture.class);
        assetManager.load("img/piece_stable_2.png", Texture.class);
        assetManager.load("img/piece_stable_3.png", Texture.class);
        assetManager.load("img/piece_stable_4.png", Texture.class);
        assetManager.load("img/piece_stable_5.png", Texture.class);
        for (int i = 0; i < 24; i++) {
            assetManager.load("img/shirt_" + (i + 1) + ".png", Texture.class);
        }
        assetManager.load("img/penalty_icon.png", Texture.class);
        assetManager.load("img/penalty_txt.png", Texture.class);
        assetManager.load("img/goal_sample.png", Texture.class);
        assetManager.load("img/goal.png", Texture.class);
        assetManager.load("img/penalty.png", Texture.class);
        assetManager.load("img/second_half.png", Texture.class);
        assetManager.load("img/ball_main.png", Texture.class);
        assetManager.load("img/ball_light.png", Texture.class);
        assetManager.load("img/ball_shadow.png", Texture.class);
        assetManager.load("img/arrow.png", Texture.class);
        assetManager.load("img/main_bg.jpg", Texture.class);
        assetManager.load("img/main_arc.png", Texture.class);
        assetManager.load("img/main_item_bg.png", Texture.class);
        assetManager.load("img/menu_item_online.png", Texture.class);
        assetManager.load("img/menu_item_light.png", Texture.class);
        assetManager.load("img/menu_item_coach.png", Texture.class);
        assetManager.load("img/icon_settings.png", Texture.class);
        assetManager.load("img/icon_shop.png", Texture.class);
        assetManager.load("img/icon_gift.png", Texture.class);
        assetManager.load("img/profile_background.png", Texture.class);
        assetManager.load("img/profile_coin.png", Texture.class);
        assetManager.load("img/profile_coin_right.png", Texture.class);
        assetManager.load("img/av_01.png", Texture.class);
        assetManager.load("img/av_02.png", Texture.class);
        assetManager.load("img/av_03.png", Texture.class);
        assetManager.load("img/av_04.png", Texture.class);
        assetManager.load("img/av_05.png", Texture.class);
        assetManager.load("img/av_06.png", Texture.class);
        assetManager.load("img/title_train.png", Texture.class);
        assetManager.load("img/arrow_left.png", Texture.class);
        assetManager.load("img/arrow_right.png", Texture.class);
        assetManager.load("img/setting_formation.png", Texture.class);
        assetManager.load("img/selected_player.png", Texture.class);
        assetManager.load("img/my_player.png", Texture.class);
        assetManager.load("img/player.png", Texture.class);
        assetManager.load("img/player_stamina.png", Texture.class);
        assetManager.load("img/player_speed.png", Texture.class);
        assetManager.load("img/player_size.png", Texture.class);
        assetManager.load("img/stamina_text.png", Texture.class);
        assetManager.load("img/speed_text.png", Texture.class);
        assetManager.load("img/size_text.png", Texture.class);
        assetManager.load("img/setting_coin.png", Texture.class);
        assetManager.load("img/icon_confirm.png", Texture.class);
        assetManager.load("img/icon_back.png", Texture.class);
        assetManager.load("img/stadium_button_1.png", Texture.class);
        assetManager.load("img/stadium_button_2.png", Texture.class);
        assetManager.load("img/stadium_button_2_lock.png", Texture.class);
        assetManager.load("img/stadium_button_3.png", Texture.class);
        assetManager.load("img/stadium_button_3_lock.png", Texture.class);
        assetManager.load("img/stadium_button_4.png", Texture.class);
        assetManager.load("img/stadium_button_4_lock.png", Texture.class);
        assetManager.load("img/stadium_button_5.png", Texture.class);
        assetManager.load("img/stadium_button_5_lock.png", Texture.class);
        assetManager.load("img/stadium_button_6.png", Texture.class);
        assetManager.load("img/stadium_button_6_lock.png", Texture.class);
        assetManager.load("img/progress_circle.png", Texture.class);
        assetManager.load("img/select_game_arc.png", Texture.class);
        assetManager.load("img/select_game_1.png", Texture.class);
        assetManager.load("img/select_game_2.png", Texture.class);
        assetManager.load("img/select_game_3.png", Texture.class);
        assetManager.load("img/select_game_4.png", Texture.class);
        assetManager.load("img/select_game_5.png", Texture.class);
        assetManager.load("img/select_game_6.png", Texture.class);
        assetManager.load("img/shadow.png", Texture.class);
        assetManager.load("img/loading_bar_bg.png", Texture.class);
        assetManager.load("img/loading_bar_mid.9.png", Texture.class);
        assetManager.load("img/loading_bar_top.png", Texture.class);
        assetManager.load("img/profile_coins.png", Texture.class);
        assetManager.load("img/level_right.png", Texture.class);
        assetManager.load("img/level_left.png", Texture.class);
        assetManager.load("img/stat_on.png", Texture.class);
        assetManager.load("img/stat_off.png", Texture.class);
        assetManager.load("img/ach_on.png", Texture.class);
        assetManager.load("img/ach_off.png", Texture.class);
        assetManager.load("img/profile_gp.png", Texture.class);
        assetManager.load("img/profile_gw.png", Texture.class);
        assetManager.load("img/profile_wp.png", Texture.class);
        assetManager.load("img/profile_last5.png", Texture.class);

        assetManager.load("img/ach_a1.png", Texture.class);
        assetManager.load("img/ach_a2.png", Texture.class);
        assetManager.load("img/ach_a3.png", Texture.class);
        assetManager.load("img/ach_a4.png", Texture.class);
        assetManager.load("img/ach_a5.png", Texture.class);

        assetManager.load("img/ach_b1.png", Texture.class);
        assetManager.load("img/ach_b2.png", Texture.class);
        assetManager.load("img/ach_b3.png", Texture.class);
        assetManager.load("img/ach_b4.png", Texture.class);
        assetManager.load("img/ach_b5.png", Texture.class);
        assetManager.load("img/ach_b6.png", Texture.class);

        assetManager.load("img/ach_c1.png", Texture.class);
        assetManager.load("img/ach_c2.png", Texture.class);
        assetManager.load("img/ach_c3.png", Texture.class);
        assetManager.load("img/ach_c4.png", Texture.class);
        assetManager.load("img/ach_c5.png", Texture.class);
        assetManager.load("img/ach_c6.png", Texture.class);

        assetManager.load("img/ach_d1.png", Texture.class);
        assetManager.load("img/ach_d2.png", Texture.class);
        assetManager.load("img/ach_d3.png", Texture.class);
        assetManager.load("img/ach_d4.png", Texture.class);

        assetManager.load("img/board_1.png", Texture.class);
        assetManager.load("img/board_2.png", Texture.class);
        assetManager.load("img/board_3.png", Texture.class);
        assetManager.load("img/board_4.png", Texture.class);
        assetManager.load("img/board_5.png", Texture.class);
        assetManager.load("img/formation_bg.png", Texture.class);
        assetManager.load("img/stamina_s.png", Texture.class);
        assetManager.load("img/size_s.png", Texture.class);
        assetManager.load("img/speed_s.png", Texture.class);

        assetManager.load("img/findmatch_main.png", Texture.class);
        assetManager.load("img/result_main.png", Texture.class);

        assetManager.load("img/setting_music.png", Texture.class);
        assetManager.load("img/setting_icon.png", Texture.class);
        assetManager.load("img/setting_game.png", Texture.class);
        assetManager.load("img/settings_bg.png", Texture.class);
        assetManager.load("img/setting_logout.png", Texture.class);
        assetManager.load("img/setting_button.png", Texture.class);
        assetManager.load("img/setting_button_bg.png", Texture.class);
        assetManager.load("img/setting_music_bg.png", Texture.class);
        assetManager.load("img/setting_sound_bg.png", Texture.class);
        assetManager.load("img/setting_vibe_bg.png", Texture.class);
        assetManager.load("img/setting_dot.png", Texture.class);
        assetManager.load("img/coach_shop_1.png", Texture.class);
        assetManager.load("img/coach_shop_2.png", Texture.class);
        assetManager.load("img/coach_shop_3.png", Texture.class);
        assetManager.load("img/coach_shop_4.png", Texture.class);
        assetManager.load("img/coach_shop_5.png", Texture.class);

        assetManager.load("img/keeper_piece_1.png", Texture.class);
        assetManager.load("img/keeper_piece_2.png", Texture.class);
        assetManager.load("img/keeper_piece_3.png", Texture.class);
        assetManager.load("img/keeper_piece_4.png", Texture.class);
        assetManager.load("img/keeper_piece_5.png", Texture.class);
        assetManager.load("img/keeper_piece_6.png", Texture.class);

        assetManager.load("img/keeper_1.png", Texture.class);
        assetManager.load("img/keeper_2.png", Texture.class);
        assetManager.load("img/keeper_3.png", Texture.class);
        assetManager.load("img/keeper_4.png", Texture.class);
        assetManager.load("img/keeper_5.png", Texture.class);
        assetManager.load("img/keeper_6.png", Texture.class);

        assetManager.load("img/court_1.jpg", Texture.class);
        assetManager.load("img/court_2.jpg", Texture.class);
        assetManager.load("img/court_3.jpg", Texture.class);
        assetManager.load("img/court_4.jpg", Texture.class);
        assetManager.load("img/court_5.jpg", Texture.class);
        assetManager.load("img/court_6.jpg", Texture.class);

        assetManager.load("img/court_dark_1.jpg", Texture.class);
        assetManager.load("img/court_dark_2.jpg", Texture.class);
        assetManager.load("img/court_dark_3.jpg", Texture.class);
        assetManager.load("img/court_dark_4.jpg", Texture.class);
        assetManager.load("img/court_dark_5.jpg", Texture.class);
        assetManager.load("img/court_dark_6.jpg", Texture.class);

        assetManager.load("img/court_arc_1.png", Texture.class);
        assetManager.load("img/court_arc_2.png", Texture.class);
        assetManager.load("img/court_arc_3.png", Texture.class);
        assetManager.load("img/court_arc_4.png", Texture.class);
        assetManager.load("img/court_arc_5.png", Texture.class);
        assetManager.load("img/court_arc_6.png", Texture.class);

        assetManager.load("img/time_bar.png", Texture.class);

        assetManager.load("img/login.png", Texture.class);
        assetManager.load("img/register.png", Texture.class);
        assetManager.load("img/login_google.png", Texture.class);

        assetManager.load("img/login_email.png", Texture.class);
        assetManager.load("img/login_pass.png", Texture.class);
        assetManager.load("img/login_pass_re.png", Texture.class);
        assetManager.load("img/login_username.png", Texture.class);

        assetManager.load("img/lose.png", Texture.class);
        assetManager.load("img/win.png", Texture.class);

        assetManager.load("img/page_indicator_dot.png", Texture.class);
        assetManager.load("img/shop_tab_coins.png", Texture.class);
        assetManager.load("img/shop_tab_app.png", Texture.class);
        assetManager.load("img/shop_tab_coins_on.png", Texture.class);
        assetManager.load("img/shop_tab_app_on.png", Texture.class);
        assetManager.load("img/shop_coin_500.png", Texture.class);
        assetManager.load("img/shop_coin_1500.png", Texture.class);
        assetManager.load("img/shop_coin_4k.png", Texture.class);
        assetManager.load("img/shop_coin_10k.png", Texture.class);

        assetManager.load("img/shop_shirt_1.png", Texture.class);
        assetManager.load("img/shop_shirt_2.png", Texture.class);
        assetManager.load("img/shop_shirt_3.png", Texture.class);
        assetManager.load("img/shop_shirt_4.png", Texture.class);
        assetManager.load("img/shop_shirt_5.png", Texture.class);
        assetManager.load("img/shop_shirt_6.png", Texture.class);
        assetManager.load("img/shop_shirt_7.png", Texture.class);
        assetManager.load("img/shop_shirt_8.png", Texture.class);
        assetManager.load("img/shop_shirt_9.png", Texture.class);
        assetManager.load("img/shop_shirt_10.png", Texture.class);
        assetManager.load("img/shop_shirt_11.png", Texture.class);
        assetManager.load("img/shop_shirt_12.png", Texture.class);
        assetManager.load("img/shop_shirt_13.png", Texture.class);
        assetManager.load("img/shop_shirt_14.png", Texture.class);
        assetManager.load("img/shop_shirt_15.png", Texture.class);
        assetManager.load("img/shop_shirt_16.png", Texture.class);
        assetManager.load("img/shop_shirt_17.png", Texture.class);
        assetManager.load("img/shop_shirt_18.png", Texture.class);
        assetManager.load("img/shop_shirt_19.png", Texture.class);
        assetManager.load("img/shop_shirt_20.png", Texture.class);
        assetManager.load("img/shop_shirt_21.png", Texture.class);
        assetManager.load("img/shop_shirt_22.png", Texture.class);
        assetManager.load("img/shop_shirt_23.png", Texture.class);
        assetManager.load("img/shop_shirt_24.png", Texture.class);

        assetManager.load("img/shop_shirt_select.png", Texture.class);
        assetManager.load("img/shop_shirt_select.png", Texture.class);
        assetManager.load("img/shop_shirt_1k.png", Texture.class);
        assetManager.load("img/shop_shirt_2k.png", Texture.class);
        assetManager.load("img/shop_shirt_9k.png", Texture.class);
        assetManager.load("img/shop_shirt_9k.png", Texture.class);
        assetManager.load("img/shop_shirt_1k.png", Texture.class);
        assetManager.load("img/shop_shirt_9k.png", Texture.class);
        assetManager.load("img/shop_shirt_9k.png", Texture.class);
        assetManager.load("img/shop_shirt_2k.png", Texture.class);
        assetManager.load("img/shop_shirt_2k.png", Texture.class);
        assetManager.load("img/shop_shirt_5k.png", Texture.class);
        assetManager.load("img/shop_shirt_5k.png", Texture.class);
        assetManager.load("img/shop_shirt_1k.png", Texture.class);
        assetManager.load("img/shop_shirt_2k.png", Texture.class);
        assetManager.load("img/shop_shirt_2k.png", Texture.class);
        assetManager.load("img/shop_shirt_2k.png", Texture.class);
        assetManager.load("img/shop_shirt_5k.png", Texture.class);
        assetManager.load("img/shop_shirt_5k.png", Texture.class);
        assetManager.load("img/shop_shirt_5k.png", Texture.class);
        assetManager.load("img/shop_shirt_2t.png", Texture.class);
        assetManager.load("img/shop_shirt_2t.png", Texture.class);
        assetManager.load("img/shop_shirt_2t.png", Texture.class);
        assetManager.load("img/shop_shirt_2t.png", Texture.class);

        assetManager.load("img/shop_shirt_select.png", Texture.class);
        assetManager.load("img/shop_shirt_selected.png", Texture.class);

        assetManager.load("img/prize_1.png", Texture.class);
        assetManager.load("img/prize_2.png", Texture.class);
        assetManager.load("img/prize_3.png", Texture.class);
        assetManager.load("img/prize_4.png", Texture.class);
        assetManager.load("img/prize_5.png", Texture.class);

        assetManager.load("img/popup_bg.png", Texture.class);

        assetManager.load("img/squad.png", Texture.class);
        assetManager.load("img/onevsone.png", Texture.class);

        assetManager.load("img/dark_bg.png", Texture.class);

        assetManager.load("img/coin_sample.png", Texture.class);

        assetManager.load("img/stamina_bar.png", Texture.class);

        assetManager.load("img/icon_resign.png", Texture.class);
        assetManager.load("img/icon_done.png", Texture.class);

        assetManager.load("img/arrow_up.png", Texture.class);
        assetManager.load("img/arrow_down.png", Texture.class);

        assetManager.load("img/help_bg.png", Texture.class);
        assetManager.load("img/help_bg_1.png", Texture.class);
        assetManager.load("img/help_bg_2.png", Texture.class);
        assetManager.load("img/help_bg_3.png", Texture.class);
        assetManager.load("img/help_bg_4.png", Texture.class);
        assetManager.load("img/help_bg_5.png", Texture.class);
        assetManager.load("img/help_bg_6.png", Texture.class);
        assetManager.load("img/help_bg_7.png", Texture.class);
        assetManager.load("img/help_box.png", Texture.class);
        assetManager.load("img/ic_next.png", Texture.class);
        assetManager.load("img/ic_skip.png", Texture.class);
    }

    public void init() {
        main_theme = assetManager.get("sound/main_theme.mp3", Music.class);
        coins = assetManager.get("sound/coins_game.mp3", Music.class);
        stadium = assetManager.get("sound/crowd.mp3", Music.class);
        goal = assetManager.get("sound/goal_scored.mp3", Music.class);
        upgrade = assetManager.get("sound/upgrade.mp3", Music.class);
        match_found = assetManager.get("sound/match_find.mp3", Music.class);
        free_coin = assetManager.get("sound/free_coin.mp3", Music.class);
        whistle = assetManager.get("sound/whistle.mp3", Music.class);
        click = assetManager.get("sound/click.wav", Music.class);

        //-----------------
        main_bg = assetManager.get("img/main_bg.jpg", Texture.class);
        player_stable = new Texture[5];
        player_stable[0] = assetManager.get("img/piece_stable_1.png", Texture.class);
        player_stable[1] = assetManager.get("img/piece_stable_2.png", Texture.class);
        player_stable[2] = assetManager.get("img/piece_stable_3.png", Texture.class);
        player_stable[3] = assetManager.get("img/piece_stable_4.png", Texture.class);
        player_stable[4] = assetManager.get("img/piece_stable_5.png", Texture.class);
        for (int i = 0; i < 24; i++) {
            shirts[i] = assetManager.get("img/shirt_" + (i + 1) + ".png", Texture.class);
        }
        penalty_icon = assetManager.get("img/penalty_icon.png", Texture.class);
        penalty_text = assetManager.get("img/penalty_txt.png", Texture.class);
        goal_sample = assetManager.get("img/goal_sample.png", Texture.class);
        goalImage = assetManager.get("img/goal.png", Texture.class);
        penaltyImage = assetManager.get("img/penalty.png", Texture.class);
        secondHalfImage = assetManager.get("img/second_half.png", Texture.class);
        ball = assetManager.get("img/ball_main.png", Texture.class);
        ball_light = assetManager.get("img/ball_light.png", Texture.class);
        ball_shadow = assetManager.get("img/ball_shadow.png", Texture.class);
        arrow = assetManager.get("img/arrow.png", Texture.class);
        main_bg = assetManager.get("img/main_bg.jpg", Texture.class);
        main_arc = assetManager.get("img/main_arc.png", Texture.class);
        main_item_bg = assetManager.get("img/main_item_bg.png", Texture.class);
        main_item_online = assetManager.get("img/menu_item_online.png", Texture.class);
        main_item_light = assetManager.get("img/menu_item_light.png", Texture.class);
        main_item_coach = assetManager.get("img/menu_item_coach.png", Texture.class);
        main_icon_settings = assetManager.get("img/icon_settings.png", Texture.class);
        main_icon_shop = assetManager.get("img/icon_shop.png", Texture.class);
        main_icon_gift = assetManager.get("img/icon_gift.png", Texture.class);
        profile_background = assetManager.get("img/profile_background.png", Texture.class);
        profile_coin = assetManager.get("img/profile_coin.png", Texture.class);
        profile_coin_right = assetManager.get("img/profile_coin_right.png", Texture.class);
        avatar_1 = assetManager.get("img/av_01.png", Texture.class);
        avatar_2 = assetManager.get("img/av_02.png", Texture.class);
        avatar_3 = assetManager.get("img/av_03.png", Texture.class);
        avatar_4 = assetManager.get("img/av_04.png", Texture.class);
        avatar_5 = assetManager.get("img/av_05.png", Texture.class);
        avatar_6 = assetManager.get("img/av_06.png", Texture.class);
        title_train = assetManager.get("img/title_train.png", Texture.class);
        arrow_left = assetManager.get("img/arrow_left.png", Texture.class);
        arrow_right = assetManager.get("img/arrow_right.png", Texture.class);
        setting_formation = assetManager.get("img/setting_formation.png", Texture.class);
        selected_player = assetManager.get("img/selected_player.png", Texture.class);
        my_player_on = assetManager.get("img/my_player.png", Texture.class);
        test_image = assetManager.get("img/player.png", Texture.class);
        player_stamina = assetManager.get("img/player_stamina.png", Texture.class);
        player_speed = assetManager.get("img/player_speed.png", Texture.class);
        player_size = assetManager.get("img/player_size.png", Texture.class);
        player_stamina_text = assetManager.get("img/stamina_text.png", Texture.class);
        player_speed_text = assetManager.get("img/speed_text.png", Texture.class);
        player_size_text = assetManager.get("img/size_text.png", Texture.class);
        setting_coin = assetManager.get("img/setting_coin.png", Texture.class);
        icon_ok = assetManager.get("img/icon_confirm.png", Texture.class);
        icon_back = assetManager.get("img/icon_back.png", Texture.class);
        stadium_button_1 = assetManager.get("img/stadium_button_1.png", Texture.class);
        stadium_button_2 = assetManager.get("img/stadium_button_2.png", Texture.class);
        stadium_button_2_lock = assetManager.get("img/stadium_button_2_lock.png", Texture.class);
        stadium_button_3 = assetManager.get("img/stadium_button_3.png", Texture.class);
        stadium_button_3_lock = assetManager.get("img/stadium_button_3_lock.png", Texture.class);
        stadium_button_4 = assetManager.get("img/stadium_button_4.png", Texture.class);
        stadium_button_4_lock = assetManager.get("img/stadium_button_4_lock.png", Texture.class);
        stadium_button_5 = assetManager.get("img/stadium_button_5.png", Texture.class);
        stadium_button_5_lock = assetManager.get("img/stadium_button_5_lock.png", Texture.class);
        stadium_button_6 = assetManager.get("img/stadium_button_6.png", Texture.class);
        stadium_button_6_lock = assetManager.get("img/stadium_button_6_lock.png", Texture.class);
        progress_circle = assetManager.get("img/progress_circle.png", Texture.class);
        select_game_arc = assetManager.get("img/select_game_arc.png", Texture.class);
        select_game[0] = assetManager.get("img/select_game_1.png", Texture.class);
        select_game[1] = assetManager.get("img/select_game_2.png", Texture.class);
        select_game[2] = assetManager.get("img/select_game_3.png", Texture.class);
        select_game[3] = assetManager.get("img/select_game_4.png", Texture.class);
        select_game[4] = assetManager.get("img/select_game_5.png", Texture.class);
        select_game[5] = assetManager.get("img/select_game_6.png", Texture.class);
        shadow = assetManager.get("img/shadow.png", Texture.class);
        loading_bar_bg = assetManager.get("img/loading_bar_bg.png", Texture.class);
        loading_bar_mid = assetManager.get("img/loading_bar_mid.9.png", Texture.class);
        loading_bar_top = assetManager.get("img/loading_bar_top.png", Texture.class);
        profile_page_coins = assetManager.get("img/profile_coins.png", Texture.class);
        level_right = assetManager.get("img/level_right.png", Texture.class);
        level_left = assetManager.get("img/level_left.png", Texture.class);
        profile_stat_on = assetManager.get("img/stat_on.png", Texture.class);
        profile_stat_off = assetManager.get("img/stat_off.png", Texture.class);
        profile_ach_on = assetManager.get("img/ach_on.png", Texture.class);
        profile_ach_off = assetManager.get("img/ach_off.png", Texture.class);
        profile_gp = assetManager.get("img/profile_gp.png", Texture.class);
        profile_gw = assetManager.get("img/profile_gw.png", Texture.class);
        profile_wp = assetManager.get("img/profile_wp.png", Texture.class);
        profile_last5 = assetManager.get("img/profile_last5.png", Texture.class);

        achiev_a1 = assetManager.get("img/ach_a1.png", Texture.class);
        achiev_a2 = assetManager.get("img/ach_a2.png", Texture.class);
        achiev_a3 = assetManager.get("img/ach_a3.png", Texture.class);
        achiev_a4 = assetManager.get("img/ach_a4.png", Texture.class);
        achiev_a5 = assetManager.get("img/ach_a5.png", Texture.class);

        achiev_b1 = assetManager.get("img/ach_b1.png", Texture.class);
        achiev_b2 = assetManager.get("img/ach_b2.png", Texture.class);
        achiev_b3 = assetManager.get("img/ach_b3.png", Texture.class);
        achiev_b4 = assetManager.get("img/ach_b4.png", Texture.class);
        achiev_b5 = assetManager.get("img/ach_b5.png", Texture.class);
        achiev_b6 = assetManager.get("img/ach_b6.png", Texture.class);

        achiev_c1 = assetManager.get("img/ach_c1.png", Texture.class);
        achiev_c2 = assetManager.get("img/ach_c2.png", Texture.class);
        achiev_c3 = assetManager.get("img/ach_c3.png", Texture.class);
        achiev_c4 = assetManager.get("img/ach_c4.png", Texture.class);
        achiev_c5 = assetManager.get("img/ach_c5.png", Texture.class);
        achiev_c6 = assetManager.get("img/ach_c6.png", Texture.class);

        achiev_d1 = assetManager.get("img/ach_d1.png", Texture.class);
        achiev_d2 = assetManager.get("img/ach_d2.png", Texture.class);
        achiev_d3 = assetManager.get("img/ach_d3.png", Texture.class);
        achiev_d4 = assetManager.get("img/ach_d4.png", Texture.class);

        board_bg_1 = assetManager.get("img/board_1.png", Texture.class);
        board_bg_2 = assetManager.get("img/board_2.png", Texture.class);
        board_bg_3 = assetManager.get("img/board_3.png", Texture.class);
        board_bg_4 = assetManager.get("img/board_4.png", Texture.class);
        board_bg_5 = assetManager.get("img/board_5.png", Texture.class);
        formation_bg = assetManager.get("img/formation_bg.png", Texture.class);
        formation_stamina = assetManager.get("img/stamina_s.png", Texture.class);
        formation_size = assetManager.get("img/size_s.png", Texture.class);
        formation_speed = assetManager.get("img/speed_s.png", Texture.class);

        findmatch_main = assetManager.get("img/findmatch_main.png", Texture.class);
        result_main = assetManager.get("img/result_main.png", Texture.class);

        setting_music_icon = assetManager.get("img/setting_music.png", Texture.class);
        setting_icon = assetManager.get("img/setting_icon.png", Texture.class);
        setting_game_icon = assetManager.get("img/setting_game.png", Texture.class);
        setting_item_bg = assetManager.get("img/settings_bg.png", Texture.class);
        setting_logout = assetManager.get("img/setting_logout.png", Texture.class);
        setting_button = assetManager.get("img/setting_button.png", Texture.class);
        setting_button_bg = assetManager.get("img/setting_button_bg.png", Texture.class);
        setting_music_bg = assetManager.get("img/setting_music_bg.png", Texture.class);
        setting_sound_bg = assetManager.get("img/setting_sound_bg.png", Texture.class);
        setting_vibe_bg = assetManager.get("img/setting_vibe_bg.png", Texture.class);
        setting_dot = assetManager.get("img/setting_dot.png", Texture.class);
        setting_shop_1 = assetManager.get("img/coach_shop_1.png", Texture.class);
        setting_shop_2 = assetManager.get("img/coach_shop_2.png", Texture.class);
        setting_shop_3 = assetManager.get("img/coach_shop_3.png", Texture.class);
        setting_shop_4 = assetManager.get("img/coach_shop_4.png", Texture.class);
        setting_shop_5 = assetManager.get("img/coach_shop_5.png", Texture.class);

        keeper_piece_1 = assetManager.get("img/keeper_piece_1.png", Texture.class);
        keeper_piece_2 = assetManager.get("img/keeper_piece_2.png", Texture.class);
        keeper_piece_3 = assetManager.get("img/keeper_piece_3.png", Texture.class);
        keeper_piece_4 = assetManager.get("img/keeper_piece_4.png", Texture.class);
        keeper_piece_5 = assetManager.get("img/keeper_piece_5.png", Texture.class);
        keeper_piece_6 = assetManager.get("img/keeper_piece_6.png", Texture.class);

        keeper_1 = assetManager.get("img/keeper_1.png", Texture.class);
        keeper_2 = assetManager.get("img/keeper_2.png", Texture.class);
        keeper_3 = assetManager.get("img/keeper_3.png", Texture.class);
        keeper_4 = assetManager.get("img/keeper_4.png", Texture.class);
        keeper_5 = assetManager.get("img/keeper_5.png", Texture.class);
        keeper_6 = assetManager.get("img/keeper_6.png", Texture.class);

        court_1 = assetManager.get("img/court_1.jpg", Texture.class);
        court_2 = assetManager.get("img/court_2.jpg", Texture.class);
        court_3 = assetManager.get("img/court_3.jpg", Texture.class);
        court_4 = assetManager.get("img/court_4.jpg", Texture.class);
        court_5 = assetManager.get("img/court_5.jpg", Texture.class);
        court_6 = assetManager.get("img/court_6.jpg", Texture.class);

        court_dark_1 = assetManager.get("img/court_dark_1.jpg", Texture.class);
        court_dark_2 = assetManager.get("img/court_dark_2.jpg", Texture.class);
        court_dark_3 = assetManager.get("img/court_dark_3.jpg", Texture.class);
        court_dark_4 = assetManager.get("img/court_dark_4.jpg", Texture.class);
        court_dark_5 = assetManager.get("img/court_dark_5.jpg", Texture.class);
        court_dark_6 = assetManager.get("img/court_dark_6.jpg", Texture.class);

        court_arc_1 = assetManager.get("img/court_arc_1.png", Texture.class);
        court_arc_2 = assetManager.get("img/court_arc_2.png", Texture.class);
        court_arc_3 = assetManager.get("img/court_arc_3.png", Texture.class);
        court_arc_4 = assetManager.get("img/court_arc_4.png", Texture.class);
        court_arc_5 = assetManager.get("img/court_arc_5.png", Texture.class);
        court_arc_6 = assetManager.get("img/court_arc_6.png", Texture.class);

        time_bar = assetManager.get("img/time_bar.png", Texture.class);

        login = assetManager.get("img/login.png", Texture.class);
        register = assetManager.get("img/register.png", Texture.class);
        login_google = assetManager.get("img/login_google.png", Texture.class);

        login_email = assetManager.get("img/login_email.png", Texture.class);
        login_pass = assetManager.get("img/login_pass.png", Texture.class);
        login_pass_re = assetManager.get("img/login_pass_re.png", Texture.class);
        login_username = assetManager.get("img/login_username.png", Texture.class);

        lose_image = assetManager.get("img/lose.png", Texture.class);
        win_image = assetManager.get("img/win.png", Texture.class);

        page_indicator_dot = assetManager.get("img/page_indicator_dot.png", Texture.class);
        shop_tab_coins = assetManager.get("img/shop_tab_coins.png", Texture.class);
        shop_tab_app = assetManager.get("img/shop_tab_app.png", Texture.class);
        shop_tab_coins_on = assetManager.get("img/shop_tab_coins_on.png", Texture.class);
        shop_tab_app_on = assetManager.get("img/shop_tab_app_on.png", Texture.class);
        shop_coin_500 = assetManager.get("img/shop_coin_500.png", Texture.class);
        shop_coin_1500 = assetManager.get("img/shop_coin_1500.png", Texture.class);
        shop_coin_4k = assetManager.get("img/shop_coin_4k.png", Texture.class);
        shop_coin_10k = assetManager.get("img/shop_coin_10k.png", Texture.class);

        shop_shirts[0] = assetManager.get("img/shop_shirt_1.png", Texture.class);
        shop_shirts[1] = assetManager.get("img/shop_shirt_2.png", Texture.class);
        shop_shirts[2] = assetManager.get("img/shop_shirt_3.png", Texture.class);
        shop_shirts[3] = assetManager.get("img/shop_shirt_4.png", Texture.class);
        shop_shirts[4] = assetManager.get("img/shop_shirt_5.png", Texture.class);
        shop_shirts[5] = assetManager.get("img/shop_shirt_6.png", Texture.class);
        shop_shirts[6] = assetManager.get("img/shop_shirt_7.png", Texture.class);
        shop_shirts[7] = assetManager.get("img/shop_shirt_8.png", Texture.class);
        shop_shirts[8] = assetManager.get("img/shop_shirt_9.png", Texture.class);
        shop_shirts[9] = assetManager.get("img/shop_shirt_10.png", Texture.class);
        shop_shirts[10] = assetManager.get("img/shop_shirt_11.png", Texture.class);
        shop_shirts[11] = assetManager.get("img/shop_shirt_12.png", Texture.class);
        shop_shirts[12] = assetManager.get("img/shop_shirt_13.png", Texture.class);
        shop_shirts[13] = assetManager.get("img/shop_shirt_14.png", Texture.class);
        shop_shirts[14] = assetManager.get("img/shop_shirt_15.png", Texture.class);
        shop_shirts[15] = assetManager.get("img/shop_shirt_16.png", Texture.class);
        shop_shirts[16] = assetManager.get("img/shop_shirt_17.png", Texture.class);
        shop_shirts[17] = assetManager.get("img/shop_shirt_18.png", Texture.class);
        shop_shirts[18] = assetManager.get("img/shop_shirt_19.png", Texture.class);
        shop_shirts[19] = assetManager.get("img/shop_shirt_20.png", Texture.class);
        shop_shirts[20] = assetManager.get("img/shop_shirt_21.png", Texture.class);
        shop_shirts[21] = assetManager.get("img/shop_shirt_22.png", Texture.class);
        shop_shirts[22] = assetManager.get("img/shop_shirt_23.png", Texture.class);
        shop_shirts[23] = assetManager.get("img/shop_shirt_24.png", Texture.class);

        shop_shirts_bg[0] = assetManager.get("img/shop_shirt_select.png", Texture.class);
        shop_shirts_bg[1] = assetManager.get("img/shop_shirt_select.png", Texture.class);
        shop_shirts_bg[2] = assetManager.get("img/shop_shirt_1k.png", Texture.class);
        shop_shirts_bg[3] = assetManager.get("img/shop_shirt_2k.png", Texture.class);
        shop_shirts_bg[4] = assetManager.get("img/shop_shirt_9k.png", Texture.class);
        shop_shirts_bg[5] = assetManager.get("img/shop_shirt_9k.png", Texture.class);
        shop_shirts_bg[6] = assetManager.get("img/shop_shirt_1k.png", Texture.class);
        shop_shirts_bg[7] = assetManager.get("img/shop_shirt_9k.png", Texture.class);
        shop_shirts_bg[8] = assetManager.get("img/shop_shirt_9k.png", Texture.class);
        shop_shirts_bg[9] = assetManager.get("img/shop_shirt_2k.png", Texture.class);
        shop_shirts_bg[10] = assetManager.get("img/shop_shirt_2k.png", Texture.class);
        shop_shirts_bg[11] = assetManager.get("img/shop_shirt_5k.png", Texture.class);
        shop_shirts_bg[12] = assetManager.get("img/shop_shirt_5k.png", Texture.class);
        shop_shirts_bg[13] = assetManager.get("img/shop_shirt_1k.png", Texture.class);
        shop_shirts_bg[14] = assetManager.get("img/shop_shirt_2k.png", Texture.class);
        shop_shirts_bg[15] = assetManager.get("img/shop_shirt_2k.png", Texture.class);
        shop_shirts_bg[16] = assetManager.get("img/shop_shirt_2k.png", Texture.class);
        shop_shirts_bg[17] = assetManager.get("img/shop_shirt_5k.png", Texture.class);
        shop_shirts_bg[18] = assetManager.get("img/shop_shirt_5k.png", Texture.class);
        shop_shirts_bg[19] = assetManager.get("img/shop_shirt_5k.png", Texture.class);
        shop_shirts_bg[20] = assetManager.get("img/shop_shirt_2t.png", Texture.class);
        shop_shirts_bg[21] = assetManager.get("img/shop_shirt_2t.png", Texture.class);
        shop_shirts_bg[22] = assetManager.get("img/shop_shirt_2t.png", Texture.class);
        shop_shirts_bg[23] = assetManager.get("img/shop_shirt_2t.png", Texture.class);

        shop_shirt_select = assetManager.get("img/shop_shirt_select.png", Texture.class);
        shop_shirt_selected = assetManager.get("img/shop_shirt_selected.png", Texture.class);

        prize_1 = assetManager.get("img/prize_1.png", Texture.class);
        prize_2 = assetManager.get("img/prize_2.png", Texture.class);
        prize_3 = assetManager.get("img/prize_3.png", Texture.class);
        prize_4 = assetManager.get("img/prize_4.png", Texture.class);
        prize_5 = assetManager.get("img/prize_5.png", Texture.class);

        popup_bg = assetManager.get("img/popup_bg.png", Texture.class);

        squadlabel = assetManager.get("img/squad.png", Texture.class);
        onevonelabel = assetManager.get("img/onevsone.png", Texture.class);

        dark_bg = assetManager.get("img/dark_bg.png", Texture.class);

        coins_sample = assetManager.get("img/coin_sample.png", Texture.class);

        stamina_bar = assetManager.get("img/stamina_bar.png", Texture.class);

        icon_resign = assetManager.get("img/icon_resign.png", Texture.class);
        icon_finish = assetManager.get("img/icon_done.png", Texture.class);

        goaler_up = assetManager.get("img/arrow_up.png", Texture.class);
        goaler_down = assetManager.get("img/arrow_down.png", Texture.class);

        help_bg = assetManager.get("img/help_bg.png", Texture.class);
        help_bg_1 = assetManager.get("img/help_bg_1.png", Texture.class);
        help_bg_2 = assetManager.get("img/help_bg_2.png", Texture.class);
        help_bg_3 = assetManager.get("img/help_bg_3.png", Texture.class);
        help_bg_4 = assetManager.get("img/help_bg_4.png", Texture.class);
        help_bg_5 = assetManager.get("img/help_bg_5.png", Texture.class);
        help_bg_6 = assetManager.get("img/help_bg_6.png", Texture.class);
        help_bg_7 = assetManager.get("img/help_bg_7.png", Texture.class);
        help_box = assetManager.get("img/help_box.png", Texture.class);
        help_ic_next = assetManager.get("img/ic_next.png", Texture.class);
        help_ic_skip = assetManager.get("img/ic_skip.png", Texture.class);
    }

    @Override
    public void dispose() {
        ball.dispose();
        ball_light.dispose();
        ball_shadow.dispose();
        goal_sample.dispose();
        goalImage.dispose();
        arrow.dispose();
        main_bg.dispose();
        main_bg_fade.dispose();
        main_arc.dispose();

        main_item_bg.dispose();
        main_item_online.dispose();
        main_item_light.dispose();

        main_item_coach.dispose();

        main_icon_settings.dispose();
        main_icon_shop.dispose();
        main_icon_gift.dispose();

        profile_background.dispose();
        profile_coin.dispose();
        profile_coin_right.dispose();

        avatar_1.dispose();
        avatar_2.dispose();
        avatar_3.dispose();
        avatar_4.dispose();
        avatar_5.dispose();
        avatar_6.dispose();

        title_train.dispose();
        arrow_left.dispose();
        arrow_right.dispose();
        setting_formation.dispose();
        selected_player.dispose();
        player_stamina.dispose();
        player_size.dispose();
        player_speed.dispose();
        player_stamina_text.dispose();
        player_size_text.dispose();
        player_speed_text.dispose();
        setting_coin.dispose();
        icon_ok.dispose();
        icon_back.dispose();
        stadium_button_1.dispose();
        stadium_button_2.dispose();
        stadium_button_3.dispose();
        stadium_button_4.dispose();
        stadium_button_5.dispose();
        stadium_button_6.dispose();
        progress_circle.dispose();
        select_game_arc.dispose();
        shadow.dispose();
        logo.dispose();
        loading_bar_bg.dispose();
        loading_bar_mid.dispose();
        loading_bar_top.dispose();
        profile_page_coins.dispose();
        level_right.dispose();
        level_left.dispose();
        profile_stat_on.dispose();
        profile_stat_off.dispose();
        profile_ach_on.dispose();
        profile_ach_off.dispose();
        profile_gp.dispose();
        profile_gw.dispose();
        profile_wp.dispose();
        profile_last5.dispose();
        achiev_a1.dispose();
        achiev_a2.dispose();
        achiev_a3.dispose();
        achiev_a4.dispose();
        achiev_a5.dispose();
        achiev_b1.dispose();
        achiev_b2.dispose();
        achiev_b3.dispose();
        achiev_b4.dispose();
        achiev_b5.dispose();
        achiev_b6.dispose();
        achiev_c1.dispose();
        achiev_c2.dispose();
        achiev_c3.dispose();
        achiev_c4.dispose();
        achiev_c5.dispose();
        achiev_c6.dispose();
        achiev_d1.dispose();
        achiev_d2.dispose();
        achiev_d3.dispose();
        achiev_d4.dispose();

        setting_music_icon.dispose();
        setting_game_icon.dispose();
        setting_item_bg.dispose();
        setting_logout.dispose();
        setting_button.dispose();
        setting_button_bg.dispose();
        setting_music_bg.dispose();
        setting_sound_bg.dispose();
        setting_vibe_bg.dispose();
        setting_dot.dispose();
        setting_shop_1.dispose();
        setting_shop_2.dispose();
        setting_shop_3.dispose();
        setting_shop_4.dispose();
        setting_shop_5.dispose();
        findmatch_main.dispose();

        keeper_piece_1.dispose();
        keeper_piece_2.dispose();
        keeper_piece_3.dispose();
        keeper_piece_4.dispose();
        keeper_piece_5.dispose();
        keeper_piece_6.dispose();

        keeper_1.dispose();
        keeper_2.dispose();
        keeper_3.dispose();
        keeper_4.dispose();
        keeper_5.dispose();
        keeper_6.dispose();

        court_1.dispose();
        court_2.dispose();
        court_3.dispose();
        court_4.dispose();
        court_5.dispose();
        court_6.dispose();

        court_dark_1.dispose();
        court_dark_2.dispose();
        court_dark_3.dispose();
        court_dark_4.dispose();
        court_dark_5.dispose();
        court_dark_6.dispose();

        court_arc_1.dispose();
        court_arc_2.dispose();
        court_arc_3.dispose();
        court_arc_4.dispose();
        court_arc_5.dispose();
        court_arc_6.dispose();

        board_bg_1.dispose();
        board_bg_2.dispose();
        board_bg_3.dispose();
        board_bg_4.dispose();
        board_bg_5.dispose();
        formation_bg.dispose();
        formation_stamina.dispose();
        formation_size.dispose();
        formation_speed.dispose();
        time_bar.dispose();
        stamina_bar.dispose();

        login.dispose();
        register.dispose();
        login_username.dispose();
        login_pass.dispose();
        login_pass_re.dispose();
        login_email.dispose();
        lose_image.dispose();
        win_image.dispose();

        page_indicator_dot.dispose();
        shop_tab_coins.dispose();
        shop_tab_app.dispose();
        shop_tab_coins_on.dispose();
        shop_tab_app_on.dispose();
        shop_coin_500.dispose();
        shop_coin_1500.dispose();
        shop_coin_4k.dispose();
        shop_coin_10k.dispose();
        shop_shirt_select.dispose();
        shop_shirt_selected.dispose();

        prize_1.dispose();
        prize_2.dispose();
        prize_3.dispose();
        prize_4.dispose();
        prize_5.dispose();

        popup_bg.dispose();

        squadlabel.dispose();
        onevonelabel.dispose();

        dark_bg.dispose();

        loading_bar.dispose();

        coins_sample.dispose();

        icon_resign.dispose();
        icon_finish.dispose();

        goaler_up.dispose();
        goaler_down.dispose();
    }
}
