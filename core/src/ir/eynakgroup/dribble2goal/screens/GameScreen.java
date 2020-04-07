package ir.eynakgroup.dribble2goal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.FitViewport;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import io.socket.emitter.Emitter;
import ir.eynakgroup.dribble2goal.Assets;
import ir.eynakgroup.dribble2goal.Constants;
import ir.eynakgroup.dribble2goal.GamePrefs;
import ir.eynakgroup.dribble2goal.MatchStats;
import ir.eynakgroup.dribble2goal.MyGame;
import ir.eynakgroup.dribble2goal.Server.ServerTool;
import ir.eynakgroup.dribble2goal.Util.Popups;
import ir.eynakgroup.dribble2goal.Util.Util;
import ir.eynakgroup.dribble2goal.input.Controls;
import ir.eynakgroup.dribble2goal.input.State;
import ir.eynakgroup.dribble2goal.model.IModel;
import ir.eynakgroup.dribble2goal.model.IModelListener;
import ir.eynakgroup.dribble2goal.model.IRenderer;
import ir.eynakgroup.dribble2goal.model.PhysicalModel;
import ir.eynakgroup.dribble2goal.render.GameRenderer;
import ir.eynakgroup.dribble2goal.render.textures.ProgressLine;
import ir.eynakgroup.dribble2goal.template.Field;

/**
 * Created by kAvEh on 3/21/2016.
 */

public class GameScreen implements Screen, IModelListener {

    TweenManager mTweenManager;
    private OrthographicCamera mMainCamera;
    SpriteBatch mMainBatch;

    Image bg;
    private Image bg_dark;
    private Table board;
    private Table board_fake;
    private Image resign;
    private Image end_turn;
    private Image next_icon;
    private Image prev_icon;
    private Image[] players;
    private Image[] players_bg;
    private Image selected;
    private Label t_stamina;
    private Label t_size;
    private Label t_speed;
    private Label left_score;
    private Image temp;
    private Label right_score;
    private Image goaler_up_1;
    private Image goaler_up_2;
    private Image goaler_down_1;
    private Image goaler_down_2;
    private ProgressLine[] progress = new ProgressLine[2];
    private Image[] stamina_dots;
    private Label round_num;

    private int selected_player = 1;

    private Stage mStage0;
    private Stage mStage;
    private Stage mStage2;

    private Util util;
    private Tween prog_bar;

    private boolean isBoardUp = true;

    private MatchStats matchStat;

    private float dt = 1 / 60f;
    private float accumulator;

    private final static int logic_FPSupdateIntervall = 1;  //--- display FPS alle x sekunden
    private long logic_lastRender;
    private int logic_frameCount = 0;

    private static final long RENDERER_SLEEP_MS = 0; // 34 -> 30 fps, 30 -> 34 fps, 22 gives ~46 FPS, 20 = 100, 10 = 50
    private long start;

    private IModel mModel;
    private IRenderer mGameRenderer;

    private Json mJson;
    private Image[] myPlayers;
    private Image[] myPlayersStable;
    private Image[] myPlayersOn;
    private Image[] oppPlayers;
    private Image[] oppPlayersStable;
    private Image[] balls;
    private Image[] keeper;

    private int tmp_z_index;
    private int tmp_z_index_bg;

    private Vector2[] position;

    private Array<ModelInstance> instances = new Array<ModelInstance>();
    private ModelBatch modelBatch;
    private Environment environment;
    private PerspectiveCamera cam;
    private ModelInstance inst1;
    private ModelInstance inst2;

    private boolean fh_w_flag = false;
    private boolean sh_w_flag = false;
    private boolean p_w_flag = false;

    private Popups popup;
    final private String popup_string = "popup";
    private Image dark_bg;
    GameScreen screen;

    public GameScreen(MatchStats stat, JSONObject prev) {
        ServerTool.getInstance().socket.on("startTheFuckinMatch", onReadyListener);
        ServerTool.getInstance().socket.on("before", onBeforeListener);
        ServerTool.getInstance().socket.on("beforeGoaler", onBeforeGoalerListener);
        ServerTool.getInstance().socket.on("after", onAfterListener);
        ServerTool.getInstance().socket.on("resign", onResignListener);
        ServerTool.getInstance().socket.on("anotherUserHasDisconnected", onOppDCListener);
        ServerTool.getInstance().socket.on("finalResult", onFinalListener);

        screen = this;

        mTweenManager = MyGame.mTweenManager;
        mMainBatch = new SpriteBatch();

        matchStat = stat;

        players = new Image[5];

        mStage0 = new Stage(new FitViewport(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT), mMainBatch);
        mStage = new Stage(new FitViewport(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT), mMainBatch);
        mStage2 = new Stage(new FitViewport(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT), mMainBatch);
        Table mainTable = new Table();

        util = new Util();
        Skin mSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        bg = new Image(util.getStadium(stat.matchLevel));
        bg_dark = new Image(util.getStadiumDark(stat.matchLevel));
        Image bg_arc = new Image(util.getStadiumArc(stat.matchLevel));
        Image goal_board = new Image(Assets.getInstance().goal_sample);
        bg.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);
        bg_dark.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);
        bg_arc.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);
        goal_board.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        board = new Table();
        board.setSize(Constants.HUD_SCREEN_WIDTH * .59f, Constants.HUD_SCREEN_HEIGHT * .99f);
        board.setPosition(Constants.HUD_SCREEN_WIDTH * .205f, Constants.HUD_SCREEN_HEIGHT * .85f);

        board_fake = new Table();
        board_fake.setSize(Constants.HUD_SCREEN_WIDTH * .54f, Constants.HUD_SCREEN_HEIGHT * .99f);
        board_fake.setPosition(Constants.HUD_SCREEN_WIDTH * .23f, Constants.HUD_SCREEN_HEIGHT * .911f);

        dark_bg = new Image(Assets.getInstance().dark_bg);
        dark_bg.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);
        dark_bg.setPosition(0, Constants.HUD_SCREEN_HEIGHT * -1);

        temp = new Image();
        temp.setSize(Constants.HUD_SCREEN_WIDTH * .54f, Constants.HUD_SCREEN_HEIGHT * .99f);

        board_fake.addActor(temp);

        Image board_bg = new Image(util.getBoard(stat.matchLevel));
        board_bg.setSize(Constants.HUD_SCREEN_WIDTH * .59f, Constants.HUD_SCREEN_HEIGHT * .99f);

        Label.LabelStyle style3 = new Label.LabelStyle();
        style3.font = mSkin.getFont("font21");
        style3.fontColor = Color.WHITE;

        round_num = new Label(matchStat.oppGoals + "", style3);
        round_num.setPosition(Constants.HUD_SCREEN_WIDTH * .29f, Constants.HUD_SCREEN_HEIGHT * .09f);
        round_num.setText("-");

        Label.LabelStyle style2 = new Label.LabelStyle();
        style2.font = mSkin.getFont("default-font");
        style2.fontColor = Color.DARK_GRAY;

        resign = new Image(Assets.getInstance().icon_resign);
        resign.setSize(Constants.HUD_SCREEN_WIDTH * .1664f, Constants.HUD_SCREEN_HEIGHT * .116f);
        resign.setPosition(Constants.HUD_SCREEN_WIDTH * .12f, Constants.HUD_SCREEN_HEIGHT * .15f);

        end_turn = new Image(Assets.getInstance().icon_finish);
        end_turn.setSize(Constants.HUD_SCREEN_WIDTH * .1664f, Constants.HUD_SCREEN_HEIGHT * .116f);
        end_turn.setPosition(Constants.HUD_SCREEN_WIDTH * .31f, Constants.HUD_SCREEN_HEIGHT * .15f);

        Image stamina_bar = new Image(Assets.getInstance().stamina_bar);
        stamina_bar.setSize(Constants.HUD_SCREEN_WIDTH * .184f, Constants.HUD_SCREEN_HEIGHT * .122f);
        stamina_bar.setPosition(Constants.HUD_SCREEN_WIDTH * .196f, Constants.HUD_SCREEN_HEIGHT * .24f);

        stamina_dots = new Image[5];
        for (int i = 0; i < 5; i++) {
            stamina_dots[i] = new Image(Assets.getInstance().setting_dot);
            stamina_dots[i].setSize(Constants.HUD_SCREEN_HEIGHT * .04f, Constants.HUD_SCREEN_HEIGHT * .04f);
            stamina_dots[i].setPosition(Constants.HUD_SCREEN_WIDTH * .256f + (i * Constants.HUD_SCREEN_WIDTH * .022f), Constants.HUD_SCREEN_HEIGHT * .288f);
        }

        Image court = new Image(Assets.getInstance().formation_bg);
        court.setSize(Constants.HUD_SCREEN_WIDTH * .165f, Constants.HUD_SCREEN_HEIGHT * .5325f);
        court.setPosition(Constants.HUD_SCREEN_WIDTH * .2125f, Constants.HUD_SCREEN_HEIGHT * .36f);

        prev_icon = new Image(Assets.getInstance().arrow_left);
        prev_icon.setSize(Constants.HUD_SCREEN_WIDTH * .085f, Constants.HUD_SCREEN_HEIGHT * .119f);
        prev_icon.setPosition(Constants.HUD_SCREEN_WIDTH * .122f, Constants.HUD_SCREEN_HEIGHT * .625f);

        next_icon = new Image(Assets.getInstance().arrow_right);
        next_icon.setSize(Constants.HUD_SCREEN_WIDTH * .085f, Constants.HUD_SCREEN_HEIGHT * .119f);
        next_icon.setPosition(Constants.HUD_SCREEN_WIDTH * .383f, Constants.HUD_SCREEN_HEIGHT * .625f);

        Image p_stamina = new Image(Assets.getInstance().formation_stamina);
        p_stamina.setSize(Constants.HUD_SCREEN_WIDTH * .08f, Constants.HUD_SCREEN_HEIGHT * .074f);
        p_stamina.setPosition(Constants.HUD_SCREEN_WIDTH * .122f, Constants.HUD_SCREEN_HEIGHT * .54f);

        Image p_size = new Image(Assets.getInstance().formation_size);
        p_size.setSize(Constants.HUD_SCREEN_WIDTH * .08f, Constants.HUD_SCREEN_HEIGHT * .074f);
        p_size.setPosition(Constants.HUD_SCREEN_WIDTH * .122f, Constants.HUD_SCREEN_HEIGHT * .46f);

        Image p_speed = new Image(Assets.getInstance().formation_speed);
        p_speed.setSize(Constants.HUD_SCREEN_WIDTH * .08f, Constants.HUD_SCREEN_HEIGHT * .074f);
        p_speed.setPosition(Constants.HUD_SCREEN_WIDTH * .122f, Constants.HUD_SCREEN_HEIGHT * .38f);

        goaler_up_1 = new Image(Assets.getInstance().goaler_up);
        goaler_up_1.setSize(Constants.HUD_SCREEN_HEIGHT * .075f, Constants.HUD_SCREEN_HEIGHT * .075f);
        goaler_up_1.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .53f);
        goaler_up_1.setVisible(false);

        goaler_up_2 = new Image(Assets.getInstance().goaler_up);
        goaler_up_2.setSize(Constants.HUD_SCREEN_HEIGHT * .075f, Constants.HUD_SCREEN_HEIGHT * .075f);
        goaler_up_2.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .6f);
        goaler_up_2.setVisible(false);

        goaler_down_1 = new Image(Assets.getInstance().goaler_down);
        goaler_down_1.setSize(Constants.HUD_SCREEN_HEIGHT * .075f, Constants.HUD_SCREEN_HEIGHT * .075f);
        goaler_down_1.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .395f);
        goaler_down_1.setVisible(false);

        goaler_down_2 = new Image(Assets.getInstance().goaler_down);
        goaler_down_2.setSize(Constants.HUD_SCREEN_HEIGHT * .075f, Constants.HUD_SCREEN_HEIGHT * .075f);
        goaler_down_2.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .325f);
        goaler_down_2.setVisible(false);

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = mSkin.getFont("font21");
        style.fontColor = Color.WHITE;

        t_stamina = new Label(matchStat.myPlayers[selected_player][0] + "", style);
        t_stamina.setPosition(Constants.HUD_SCREEN_WIDTH * .17f, Constants.HUD_SCREEN_HEIGHT * .557f);

        t_size = new Label(matchStat.myPlayers[selected_player][1] + "", style);
        t_size.setPosition(Constants.HUD_SCREEN_WIDTH * .17f, Constants.HUD_SCREEN_HEIGHT * .477f);

        t_speed = new Label(matchStat.myPlayers[selected_player][2] + "", style);
        t_speed.setPosition(Constants.HUD_SCREEN_WIDTH * .17f, Constants.HUD_SCREEN_HEIGHT * .397f);

        Label left_name = new Label(util.displayName(matchStat.myName), style);
        left_name.setSize(board.getWidth() * .27f, Constants.HUD_SCREEN_HEIGHT * .1f);
        left_name.setPosition(board.getWidth() * .23f, Constants.HUD_SCREEN_HEIGHT * .07f);
        left_name.setAlignment(Align.left);

        left_score = new Label(matchStat.myGoals + "", style2);
        left_score.setPosition(Constants.HUD_SCREEN_WIDTH * .055f, Constants.HUD_SCREEN_HEIGHT * .059f);

        Label right_name = new Label(util.displayName(matchStat.oppName), style);
        right_name.setSize(board.getWidth() * .27f, Constants.HUD_SCREEN_HEIGHT * .1f);
        right_name.setPosition(board.getWidth() * .5f, Constants.HUD_SCREEN_HEIGHT * .07f);
        right_name.setAlignment(Align.right);

        right_score = new Label(matchStat.oppGoals + "", style2);
        right_score.setPosition(Constants.HUD_SCREEN_WIDTH * .535f - right_score.getWidth(), Constants.HUD_SCREEN_HEIGHT * .059f);

        keeper = new Image[2];
        keeper[0] = new Image(util.getKeeperPiece(stat.matchLevel));
        keeper[1] = new Image(util.getKeeperShirt(stat.matchLevel));

        balls = new Image[3];
        balls[0] = new Image(Assets.getInstance().ball_shadow);
        balls[1] = new Image(Assets.getInstance().ball);
        balls[2] = new Image(Assets.getInstance().ball_light);

        myPlayers = new Image[5];
        myPlayersStable = new Image[5];
        myPlayersOn = new Image[5];
        oppPlayers = new Image[5];
        oppPlayersStable = new Image[5];
        for (int i = 0; i < 5; i++) {
            myPlayers[i] = new Image(util.getShirt(stat.myShirt));
            myPlayers[i].setSize(Constants.HUD_SCREEN_WIDTH * .06f, Constants.HUD_SCREEN_WIDTH * .06f);
            myPlayersStable[i] = new Image(Assets.getInstance().player_stable[i]);
            myPlayersStable[i].setSize(Constants.HUD_SCREEN_WIDTH * .06f, Constants.HUD_SCREEN_WIDTH * .06f);
            myPlayersOn[i] = new Image(Assets.getInstance().my_player_on);
            myPlayersOn[i].setSize(Constants.HUD_SCREEN_WIDTH * .06f, Constants.HUD_SCREEN_WIDTH * .06f);
            myPlayersOn[i].setColor(1, 1, 1, 0);
            oppPlayers[i] = new Image(util.getShirt(stat.oppShirt));
            oppPlayers[i].setSize(Constants.HUD_SCREEN_WIDTH * .06f, Constants.HUD_SCREEN_WIDTH * .06f);
            oppPlayersStable[i] = new Image(Assets.getInstance().player_stable[i]);
            oppPlayersStable[i].setSize(Constants.HUD_SCREEN_WIDTH * .06f, Constants.HUD_SCREEN_WIDTH * .06f);
        }

        Vector2 size = new Vector2(Constants.HUD_SCREEN_WIDTH * .452f, Constants.HUD_SCREEN_HEIGHT * .196f);
        progress[0] = new ProgressLine(Assets.getInstance().time_bar, new TextureRegion(Assets.getInstance().time_bar), size);
        progress[0].setSize(size.x, size.y);
        progress[0].setPosition(Constants.HUD_SCREEN_WIDTH * .069f, 0f);
        progress[0].setPercentage(1);

        progress[1] = new ProgressLine(Assets.getInstance().time_bar, new TextureRegion(Assets.getInstance().time_bar), size);
        progress[1].setSize(size.x, size.y);
        progress[1].setPosition(Constants.HUD_SCREEN_WIDTH * .069f, 0f);
        progress[1].setPercentage(1);
        progress[1].setColor(.78f, .29f, .29f, .9f);

        ProgressLine progress_half = new ProgressLine(Assets.getInstance().time_bar, new TextureRegion(Assets.getInstance().time_bar), size);
        progress_half.setSize(size.x, size.y);
        progress_half.setPosition(Constants.HUD_SCREEN_WIDTH * .069f, 0f);
        progress_half.setPercentage(1);

        players[0] = new Image(new Util().getShirt(stat.myShirt));
        players[1] = new Image(new Util().getShirt(stat.myShirt));
        players[2] = new Image(new Util().getShirt(stat.myShirt));
        players[3] = new Image(new Util().getShirt(stat.myShirt));
        players[4] = new Image(new Util().getShirt(stat.myShirt));

        players_bg = new Image[5];
        for (int i = 0; i < 5; i++) {
            players_bg[i] = new Image(Assets.getInstance().player_stable[i]);
        }

        setSubsListeners();

        selected = new Image(Assets.getInstance().selected_player);
        selected.setSize(Constants.HUD_SCREEN_WIDTH * .06f * 1.2f, Constants.HUD_SCREEN_HEIGHT * .107f * 1.2f);

        for (int i = 0; i < 5; i++) {
            players[i].setSize(Constants.HUD_SCREEN_WIDTH * .08f, Constants.HUD_SCREEN_HEIGHT * .142f);
            players_bg[i].setSize(Constants.HUD_SCREEN_WIDTH * .08f, Constants.HUD_SCREEN_HEIGHT * .142f);
        }

        board.addActor(board_bg);
        board.addActor(court);
        board.addActor(p_stamina);
        board.addActor(p_size);
        board.addActor(p_speed);
        board.addActor(t_stamina);
        board.addActor(t_size);
        board.addActor(t_speed);
        board.addActor(selected);
        board.addActor(stamina_bar);
        for (int i = 0; i < 5; i++) {
            board.addActor(stamina_dots[i]);
        }
        board.addActor(round_num);
        board.addActor(left_name);
        board.addActor(left_score);
        board.addActor(right_name);
        board.addActor(right_score);
        board.addActor(progress_half);
        board.addActor(progress[1]);
        board.addActor(progress[0]);
        board_fake.addActor(prev_icon);
        board_fake.addActor(next_icon);
        board_fake.addActor(resign);
        board_fake.addActor(end_turn);
        for (int i = 0; i < 5; i++) {
            board_fake.addActor(players_bg[i]);
            board_fake.addActor(players[i]);
        }

        mainTable.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        mStage0.addActor(bg);
        mStage0.addActor(bg_dark);
        for (int i = 0; i < 5; i++) {
            mStage.addActor(myPlayersOn[i]);
            mStage.addActor(myPlayersStable[i]);
            mStage.addActor(oppPlayersStable[i]);
        }
        for (int i = 0; i < 5; i++) {
            mStage.addActor(myPlayers[i]);
            mStage.addActor(oppPlayers[i]);
        }
        mStage.addActor(keeper[0]);
        mStage.addActor(keeper[1]);
        mStage.addActor(balls[0]);
        mStage.addActor(balls[1]);
        mStage.addActor(balls[2]);
        mainTable.addActor(board);
        mainTable.addActor(bg_arc);
        mainTable.addActor(goal_board);
        mainTable.addActor(board_fake);

        mStage2.addActor(mainTable);
        mStage2.addActor(goaler_up_1);
        mStage2.addActor(goaler_up_2);
        mStage2.addActor(goaler_down_1);
        mStage2.addActor(goaler_down_2);
        mStage2.addActor(dark_bg);

        initiateListeners();
    }

    private void initiateListeners() {
        goaler_up_1.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                if (matchStat.GAME_STATE == Constants.GAME_SHOOTING) {
                    matchStat.goaler_position = 1;
                    goaler_down_1.setVisible(false);
                    goaler_down_2.setVisible(false);
                    goaler_up_2.setVisible(false);
                }
                goaler_up_1.setSize(Constants.HUD_SCREEN_HEIGHT * .075f, Constants.HUD_SCREEN_HEIGHT * .075f);
                goaler_up_1.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .53f);
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                goaler_up_1.setSize(Constants.HUD_SCREEN_HEIGHT * .075f * .8f, Constants.HUD_SCREEN_HEIGHT * .075f * .8f);
//                goaler_up_1.setPosition(Constants.HUD_SCREEN_WIDTH * .85f + Constants.HUD_SCREEN_HEIGHT * .0075f, Constants.HUD_SCREEN_HEIGHT * .5375f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                goaler_up_1.setSize(Constants.HUD_SCREEN_HEIGHT * .075f, Constants.HUD_SCREEN_HEIGHT * .075f);
//                goaler_up_1.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .53f);
            }
        });

        goaler_up_2.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                if (matchStat.GAME_STATE == Constants.GAME_SHOOTING) {
                    matchStat.goaler_position = 2;
                    goaler_down_1.setVisible(false);
                    goaler_down_2.setVisible(false);
                    goaler_up_1.setVisible(false);
                }
                goaler_up_2.setSize(Constants.HUD_SCREEN_HEIGHT * .075f, Constants.HUD_SCREEN_HEIGHT * .075f);
                goaler_up_2.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .6f);
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                goaler_up_2.setSize(Constants.HUD_SCREEN_HEIGHT * .075f * .8f, Constants.HUD_SCREEN_HEIGHT * .075f * .8f);
//                goaler_up_2.setPosition(Constants.HUD_SCREEN_WIDTH * .85f + Constants.HUD_SCREEN_HEIGHT * .0075f, Constants.HUD_SCREEN_HEIGHT * .5475f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                goaler_up_2.setSize(Constants.HUD_SCREEN_HEIGHT * .075f, Constants.HUD_SCREEN_HEIGHT * .075f);
//                goaler_up_2.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .6f);
            }
        });

        goaler_down_1.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                if (matchStat.GAME_STATE == Constants.GAME_SHOOTING) {
                    matchStat.goaler_position = 3;
                    goaler_up_1.setVisible(false);
                    goaler_up_2.setVisible(false);
                    goaler_down_2.setVisible(false);
                }
                goaler_down_1.setSize(Constants.HUD_SCREEN_HEIGHT * .075f, Constants.HUD_SCREEN_HEIGHT * .075f);
                goaler_down_1.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .395f);
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                goaler_down_1.setSize(Constants.HUD_SCREEN_HEIGHT * .075f * .8f, Constants.HUD_SCREEN_HEIGHT * .075f * .8f);
//                goaler_down_1.setPosition(Constants.HUD_SCREEN_WIDTH * .85f + Constants.HUD_SCREEN_HEIGHT * .0075f, Constants.HUD_SCREEN_HEIGHT * .4025f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                goaler_down_1.setSize(Constants.HUD_SCREEN_HEIGHT * .075f, Constants.HUD_SCREEN_HEIGHT * .075f);
//                goaler_down_1.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .395f);
            }
        });

        goaler_down_2.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                if (matchStat.GAME_STATE == Constants.GAME_SHOOTING) {
                    matchStat.goaler_position = 4;
                    goaler_up_1.setVisible(false);
                    goaler_up_2.setVisible(false);
                    goaler_down_1.setVisible(false);
                }
                goaler_down_2.setSize(Constants.HUD_SCREEN_HEIGHT * .075f, Constants.HUD_SCREEN_HEIGHT * .075f);
                goaler_down_2.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .325f);
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                goaler_down_2.setSize(Constants.HUD_SCREEN_HEIGHT * .075f * .8f, Constants.HUD_SCREEN_HEIGHT * .075f * .8f);
//                goaler_down_2.setPosition(Constants.HUD_SCREEN_WIDTH * .85f + Constants.HUD_SCREEN_HEIGHT * .0075f, Constants.HUD_SCREEN_HEIGHT * .3925f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                goaler_down_2.setSize(Constants.HUD_SCREEN_HEIGHT * .075f, Constants.HUD_SCREEN_HEIGHT * .075f);
//                goaler_down_2.setPosition(Constants.HUD_SCREEN_WIDTH * .85f, Constants.HUD_SCREEN_HEIGHT * .385f);
            }
        });

        temp.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (isBoardUp) {
                    isBoardUp = false;
                    Tween.to(board, 1, .3f)
                            .target(Constants.HUD_SCREEN_WIDTH * .205f, Constants.HUD_SCREEN_HEIGHT * .01f)
                            .ease(TweenEquations.easeInCubic)
                            .start(mTweenManager).delay(0.0F);
                    Tween.to(board_fake, 1, .3f)
                            .target(Constants.HUD_SCREEN_WIDTH * .205f, Constants.HUD_SCREEN_HEIGHT * .01f)
                            .ease(TweenEquations.easeInCubic)
                            .start(mTweenManager).delay(0.0F);
                    setStaminaDots();
                } else {
                    isBoardUp = true;
                    Tween.to(board, 1, .3f)
                            .target(Constants.HUD_SCREEN_WIDTH * .205f, Constants.HUD_SCREEN_HEIGHT * .85f)
                            .ease(TweenEquations.easeInCubic)
                            .start(mTweenManager).delay(0.0F);
                    Tween.to(board_fake, 1, .3f)
                            .target(Constants.HUD_SCREEN_WIDTH * .205f, Constants.HUD_SCREEN_HEIGHT * .85f)
                            .ease(TweenEquations.easeInCubic)
                            .start(mTweenManager).delay(0.0F);
                }
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        prev_icon.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                if (matchStat.myFormation == 1) {
                    matchStat.myFormation = GamePrefs.getInstance().position_num;
                } else {
                    matchStat.myFormation = matchStat.myFormation - 1;
                }
                position = util.getInGameSettingPosition(matchStat.myFormation);
                setPosition(position);

                if (matchStat.isMeFirst) {
                    matchStat.myStartPosition = util.getAbovePlayerPosition(matchStat.myFormation, matchStat.myLineup);
                } else {
                    matchStat.myStartPosition = util.getBelowPlayerPosition(matchStat.myFormation, matchStat.myLineup);
                }
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                prev_icon.setSize(Constants.HUD_SCREEN_WIDTH * .085f * .8f, Constants.HUD_SCREEN_HEIGHT * .119f * .8f);
                prev_icon.setPosition(Constants.HUD_SCREEN_WIDTH * .1305f, Constants.HUD_SCREEN_HEIGHT * .6369f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                prev_icon.setSize(Constants.HUD_SCREEN_WIDTH * .085f, Constants.HUD_SCREEN_HEIGHT * .119f);
                prev_icon.setPosition(Constants.HUD_SCREEN_WIDTH * .122f, Constants.HUD_SCREEN_HEIGHT * .625f);
            }
        });

        resign.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                popup = new Popups(Constants.HUD_SCREEN_WIDTH * .61f, Constants.HUD_SCREEN_HEIGHT * .8f,
                        screen, "Are you sure you want to exit?");
                popup.setSize(Constants.HUD_SCREEN_WIDTH * .61f, Constants.HUD_SCREEN_HEIGHT * .8f);
                popup.setPosition(Constants.HUD_SCREEN_WIDTH * .195f, Constants.HUD_SCREEN_HEIGHT * .1f);
                popup.setName(popup_string);
                mStage2.addActor(popup);
                popup.setColor(popup.getColor().r, popup.getColor().g, popup.getColor().b, 0);
                Tween.to(popup, 5, .1f)
                        .target(1).ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F)
                        .setCallback(new TweenCallback() {
                            public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {

                            }
                        });
                dark_bg.setColor(bg.getColor().r, bg.getColor().g, bg.getColor().b, 0);
                dark_bg.setPosition(0, 0);
                Tween.to(dark_bg, 3, 1.5f)
                        .target(1)
                        .ease(TweenEquations.easeOutExpo)
                        .start(mTweenManager).delay(0.0F);
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                resign.setSize(Constants.HUD_SCREEN_WIDTH * .1664f * .8f, Constants.HUD_SCREEN_HEIGHT * .116f * .8f);
                resign.setPosition(Constants.HUD_SCREEN_WIDTH * .13664f, Constants.HUD_SCREEN_HEIGHT * .16116f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                resign.setSize(Constants.HUD_SCREEN_WIDTH * .1664f, Constants.HUD_SCREEN_HEIGHT * .116f);
                resign.setPosition(Constants.HUD_SCREEN_WIDTH * .12f, Constants.HUD_SCREEN_HEIGHT * .15f);
            }
        });

        end_turn.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                if (matchStat.GAME_STATE == Constants.GAME_SHOOTING) {
                    mTweenManager.killTarget(progress);
                    mModel.sendBefore();
                    if (matchStat.isOppReady) {
                        mModel.startRendering();
                    } else {
                        matchStat.GAME_STATE = Constants.GAME_WAITING;
                    }
                }
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                end_turn.setSize(Constants.HUD_SCREEN_WIDTH * .1664f * .8f, Constants.HUD_SCREEN_HEIGHT * .116f * .8f);
                end_turn.setPosition(Constants.HUD_SCREEN_WIDTH * .32664f, Constants.HUD_SCREEN_HEIGHT * .1616f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                end_turn.setSize(Constants.HUD_SCREEN_WIDTH * .1664f, Constants.HUD_SCREEN_HEIGHT * .116f);
                end_turn.setPosition(Constants.HUD_SCREEN_WIDTH * .31f, Constants.HUD_SCREEN_HEIGHT * .15f);
            }
        });

        next_icon.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                if (matchStat.myFormation == GamePrefs.getInstance().position_num) {
                    matchStat.myFormation = 1;
                } else {
                    matchStat.myFormation = matchStat.myFormation + 1;
                }
                position = util.getInGameSettingPosition(matchStat.myFormation);
                setPosition(position);

                if (matchStat.isMeFirst) {
                    matchStat.myStartPosition = util.getAbovePlayerPosition(matchStat.myFormation, matchStat.myLineup);
                } else {
                    matchStat.myStartPosition = util.getBelowPlayerPosition(matchStat.myFormation, matchStat.myLineup);
                }
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                next_icon.setSize(Constants.HUD_SCREEN_WIDTH * .085f * .8f, Constants.HUD_SCREEN_HEIGHT * .119f * .8f);
                next_icon.setPosition(Constants.HUD_SCREEN_WIDTH * .3915f, Constants.HUD_SCREEN_HEIGHT * .6369f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                next_icon.setSize(Constants.HUD_SCREEN_WIDTH * .085f, Constants.HUD_SCREEN_HEIGHT * .119f);
                next_icon.setPosition(Constants.HUD_SCREEN_WIDTH * .383f, Constants.HUD_SCREEN_HEIGHT * .625f);
            }
        });

        players[0].addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                Tween.to(selected, 1, .3f)
                        .target(players[0].getX() + Constants.HUD_SCREEN_WIDTH * .004f,
                                players[0].getY() + Constants.HUD_SCREEN_HEIGHT * .006f)
                        .ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F);

                selected_player = 0;

                t_stamina.setText(matchStat.myPlayers[selected_player][0] + "");
                t_size.setText(matchStat.myPlayers[selected_player][1] + "");
                t_speed.setText(matchStat.myPlayers[selected_player][2] + "");

                setStaminaDots();
            }
        });

        players[1].addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                Tween.to(selected, 1, .3f)
                        .target(players[1].getX() + Constants.HUD_SCREEN_WIDTH * .004f,
                                players[1].getY() + Constants.HUD_SCREEN_HEIGHT * .006f)
                        .ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F);

                selected_player = 1;

                t_stamina.setText(matchStat.myPlayers[selected_player][0] + "");
                t_size.setText(matchStat.myPlayers[selected_player][1] + "");
                t_speed.setText(matchStat.myPlayers[selected_player][2] + "");

                setStaminaDots();
            }
        });

        players[2].addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                Tween.to(selected, 1, .3f)
                        .target(players[2].getX() + Constants.HUD_SCREEN_WIDTH * .004f,
                                players[2].getY() + Constants.HUD_SCREEN_HEIGHT * .006f)
                        .ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F);

                selected_player = 2;

                t_stamina.setText(matchStat.myPlayers[selected_player][0] + "");
                t_size.setText(matchStat.myPlayers[selected_player][1] + "");
                t_speed.setText(matchStat.myPlayers[selected_player][2] + "");

                setStaminaDots();
            }
        });

        players[3].addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                Tween.to(selected, 1, .3f)
                        .target(players[3].getX() + Constants.HUD_SCREEN_WIDTH * .004f,
                                players[3].getY() + Constants.HUD_SCREEN_HEIGHT * .006f)
                        .ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F);

                selected_player = 3;

                t_stamina.setText(matchStat.myPlayers[selected_player][0] + "");
                t_size.setText(matchStat.myPlayers[selected_player][1] + "");
                t_speed.setText(matchStat.myPlayers[selected_player][2] + "");

                setStaminaDots();
            }
        });

        players[4].addListener(new ActorGestureListener() {
//            public boolean longPress(Actor actor, float x, float y) {
//                if (selected_player != 4) {
//                    boolean isSelectedonBench;
//                    boolean isClickedonBench;
//                    isSelectedonBench = matchStat.myLineup[3] == selected_player ||
//                            matchStat.myLineup[4] == selected_player;
//                    isClickedonBench = matchStat.myLineup[3] == 4 ||
//                            matchStat.myLineup[4] == 4;
//                    if (isSelectedonBench) {
//                        if (isClickedonBench) {
//                            return false;
//                        } else {
//                            substitution(4, selected_player);
//                        }
//                    } else {
//                        if (isClickedonBench) {
//                            substitution(selected_player, 4);
//                        } else {
//                            return false;
//                        }
//                    }
//                }
//                return true;
//            }

            public void tap(InputEvent event, float x, float y, int count, int button) {
                Tween.to(selected, 1, .3f)
                        .target(players[4].getX() + Constants.HUD_SCREEN_WIDTH * .004f,
                                players[4].getY() + Constants.HUD_SCREEN_HEIGHT * .006f)
                        .ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F);

                selected_player = 4;

                t_stamina.setText(matchStat.myPlayers[selected_player][0] + "");
                t_size.setText(matchStat.myPlayers[selected_player][1] + "");
                t_speed.setText(matchStat.myPlayers[selected_player][2] + "");

                setStaminaDots();
            }
        });
    }

    private void setStaminaDots() {
        int tmp;
        if (mModel.getMyStamina()[selected_player] == 1f) {
            tmp = 5;
        } else if (mModel.getMyStamina()[selected_player] == .9f) {
            tmp = 4;
        } else if (mModel.getMyStamina()[selected_player] == .8f) {
            tmp = 3;
        } else if (mModel.getMyStamina()[selected_player] == .7f) {
            tmp = 2;
        } else {
            tmp = 1;
        }

        for (int i = 0; i < tmp; i++) {
            Tween.to(stamina_dots[i], 3, .2f)
                    .target(1)
                    .ease(TweenEquations.easeInCubic)
                    .start(mTweenManager).delay(.2F * i);
        }
        for (int i = tmp; i < 5; i++) {
            Tween.to(stamina_dots[i], 3, .2f)
                    .target(0)
                    .ease(TweenEquations.easeInCubic)
                    .start(mTweenManager).delay(.2F * i);
        }
    }

    private void setSubsListeners() {
        players[0].addListener(new DragListener() {
            public void drag(InputEvent event, float x, float y, int pointer) {
                tmp_z_index = players[0].getZIndex();
                tmp_z_index_bg = players_bg[0].getZIndex();
                players_bg[0].setZIndex(900);
                players[0].setZIndex(1000);
                players_bg[0].moveBy(x - players_bg[0].getWidth() / 2, y - players_bg[0].getHeight() / 2);
                players[0].moveBy(x - players[0].getWidth() / 2, y - players[0].getHeight() / 2);
            }

            public void dragStop(InputEvent event, float x, float y, int pointer) {
                if (players[0].getX() > players[1].getX() - players[1].getWidth() / 4 &&
                        players[0].getX() < players[1].getX() + players[1].getWidth() / 4 &&
                        players[0].getY() > players[1].getY() - players[1].getHeight() / 4 &&
                        players[0].getY() < players[1].getY() + players[1].getHeight() / 4) {
                    changeMyPlayer(0, 1);
                } else if (players[0].getX() > players[2].getX() - players[2].getWidth() / 4 &&
                        players[0].getX() < players[2].getX() + players[2].getWidth() / 4 &&
                        players[0].getY() > players[2].getY() - players[2].getHeight() / 4 &&
                        players[0].getY() < players[2].getY() + players[2].getHeight() / 4) {
                    changeMyPlayer(0, 2);
                } else if (players[0].getX() > players[3].getX() - players[3].getWidth() / 4 &&
                        players[0].getX() < players[3].getX() + players[3].getWidth() / 4 &&
                        players[0].getY() > players[3].getY() - players[3].getHeight() / 4 &&
                        players[0].getY() < players[3].getY() + players[3].getHeight() / 4) {
                    changeMyPlayer(0, 3);
                } else if (players[0].getX() > players[4].getX() - players[4].getWidth() / 4 &&
                        players[0].getX() < players[4].getX() + players[4].getWidth() / 4 &&
                        players[0].getY() > players[4].getY() - players[4].getHeight() / 4 &&
                        players[0].getY() < players[4].getY() + players[4].getHeight() / 4) {
                    changeMyPlayer(0, 4);
                } else {
                    setPosition(position);
                }
                players_bg[0].setZIndex(tmp_z_index_bg);
                players[0].setZIndex(tmp_z_index);
            }
        });
        players[1].addListener(new DragListener() {
            public void drag(InputEvent event, float x, float y, int pointer) {
                tmp_z_index = players[1].getZIndex();
                tmp_z_index_bg = players_bg[1].getZIndex();
                players_bg[1].setZIndex(900);
                players[1].setZIndex(1000);
                players_bg[1].moveBy(x - players_bg[1].getWidth() / 2, y - players_bg[1].getHeight() / 2);
                players[1].moveBy(x - players[1].getWidth() / 2, y - players[1].getHeight() / 2);
            }

            public void dragStop(InputEvent event, float x, float y, int pointer) {
                if (players[1].getX() > players[0].getX() - players[0].getWidth() / 4 &&
                        players[1].getX() < players[0].getX() + players[0].getWidth() / 4 &&
                        players[1].getY() > players[0].getY() - players[0].getHeight() / 4 &&
                        players[1].getY() < players[0].getY() + players[0].getHeight() / 4) {
                    changeMyPlayer(1, 0);
                } else if (players[1].getX() > players[2].getX() - players[2].getWidth() / 4 &&
                        players[1].getX() < players[2].getX() + players[2].getWidth() / 4 &&
                        players[1].getY() > players[2].getY() - players[2].getHeight() / 4 &&
                        players[1].getY() < players[2].getY() + players[2].getHeight() / 4) {
                    changeMyPlayer(1, 2);
                } else if (players[1].getX() > players[3].getX() - players[3].getWidth() / 4 &&
                        players[1].getX() < players[3].getX() + players[3].getWidth() / 4 &&
                        players[1].getY() > players[3].getY() - players[3].getHeight() / 4 &&
                        players[1].getY() < players[3].getY() + players[3].getHeight() / 4) {
                    changeMyPlayer(1, 3);
                } else if (players[1].getX() > players[4].getX() - players[4].getWidth() / 4 &&
                        players[1].getX() < players[4].getX() + players[4].getWidth() / 4 &&
                        players[1].getY() > players[4].getY() - players[4].getHeight() / 4 &&
                        players[1].getY() < players[4].getY() + players[4].getHeight() / 4) {
                    changeMyPlayer(1, 4);
                } else {
                    setPosition(position);
                }
                players_bg[1].setZIndex(tmp_z_index_bg);
                players[1].setZIndex(tmp_z_index);
            }
        });
        players[2].addListener(new DragListener() {
            public void drag(InputEvent event, float x, float y, int pointer) {
                tmp_z_index = players[2].getZIndex();
                tmp_z_index_bg = players_bg[2].getZIndex();
                players_bg[2].setZIndex(900);
                players[2].setZIndex(1000);
                players_bg[2].moveBy(x - players_bg[2].getWidth() / 2, y - players_bg[2].getHeight() / 2);
                players[2].moveBy(x - players[2].getWidth() / 2, y - players[2].getHeight() / 2);
            }

            public void dragStop(InputEvent event, float x, float y, int pointer) {
                if (players[2].getX() > players[1].getX() - players[1].getWidth() / 4 &&
                        players[2].getX() < players[1].getX() + players[1].getWidth() / 4 &&
                        players[2].getY() > players[1].getY() - players[1].getHeight() / 4 &&
                        players[2].getY() < players[1].getY() + players[1].getHeight() / 4) {
                    changeMyPlayer(2, 1);
                } else if (players[2].getX() > players[0].getX() - players[0].getWidth() / 4 &&
                        players[2].getX() < players[0].getX() + players[0].getWidth() / 4 &&
                        players[2].getY() > players[0].getY() - players[0].getHeight() / 4 &&
                        players[2].getY() < players[0].getY() + players[0].getHeight() / 4) {
                    changeMyPlayer(2, 0);
                } else if (players[2].getX() > players[3].getX() - players[3].getWidth() / 4 &&
                        players[2].getX() < players[3].getX() + players[3].getWidth() / 4 &&
                        players[2].getY() > players[3].getY() - players[3].getHeight() / 4 &&
                        players[2].getY() < players[3].getY() + players[3].getHeight() / 4) {
                    changeMyPlayer(2, 3);
                } else if (players[2].getX() > players[4].getX() - players[4].getWidth() / 4 &&
                        players[2].getX() < players[4].getX() + players[4].getWidth() / 4 &&
                        players[2].getY() > players[4].getY() - players[4].getHeight() / 4 &&
                        players[2].getY() < players[4].getY() + players[4].getHeight() / 4) {
                    changeMyPlayer(2, 4);
                } else {
                    setPosition(position);
                }
                players_bg[2].setZIndex(tmp_z_index_bg);
                players[2].setZIndex(tmp_z_index);
            }
        });
        players[3].addListener(new DragListener() {
            public void drag(InputEvent event, float x, float y, int pointer) {
                tmp_z_index = players[3].getZIndex();
                tmp_z_index_bg = players_bg[3].getZIndex();
                players_bg[3].setZIndex(900);
                players[3].setZIndex(1000);
                players_bg[3].moveBy(x - players_bg[3].getWidth() / 2, y - players_bg[3].getHeight() / 2);
                players[3].moveBy(x - players[3].getWidth() / 2, y - players[3].getHeight() / 2);
            }

            public void dragStop(InputEvent event, float x, float y, int pointer) {
                if (players[3].getX() > players[1].getX() - players[1].getWidth() / 4 &&
                        players[3].getX() < players[1].getX() + players[1].getWidth() / 4 &&
                        players[3].getY() > players[1].getY() - players[1].getHeight() / 4 &&
                        players[3].getY() < players[1].getY() + players[1].getHeight() / 4) {
                    changeMyPlayer(3, 1);
                } else if (players[3].getX() > players[2].getX() - players[2].getWidth() / 4 &&
                        players[3].getX() < players[2].getX() + players[2].getWidth() / 4 &&
                        players[3].getY() > players[2].getY() - players[2].getHeight() / 4 &&
                        players[3].getY() < players[2].getY() + players[2].getHeight() / 4) {
                    changeMyPlayer(3, 2);
                } else if (players[3].getX() > players[0].getX() - players[0].getWidth() / 4 &&
                        players[3].getX() < players[0].getX() + players[0].getWidth() / 4 &&
                        players[3].getY() > players[0].getY() - players[0].getHeight() / 4 &&
                        players[3].getY() < players[0].getY() + players[0].getHeight() / 4) {
                    changeMyPlayer(3, 0);
                } else if (players[3].getX() > players[4].getX() - players[4].getWidth() / 4 &&
                        players[3].getX() < players[4].getX() + players[4].getWidth() / 4 &&
                        players[3].getY() > players[4].getY() - players[4].getHeight() / 4 &&
                        players[3].getY() < players[4].getY() + players[4].getHeight() / 4) {
                    changeMyPlayer(3, 4);
                } else {
                    setPosition(position);
                }
                players_bg[3].setZIndex(tmp_z_index_bg);
                players[3].setZIndex(tmp_z_index);
            }
        });
        players[4].addListener(new DragListener() {
            public void drag(InputEvent event, float x, float y, int pointer) {
                tmp_z_index = players[4].getZIndex();
                tmp_z_index_bg = players_bg[4].getZIndex();
                players_bg[4].setZIndex(900);
                players[4].setZIndex(1000);
                players_bg[4].moveBy(x - players_bg[4].getWidth() / 2, y - players_bg[4].getHeight() / 2);
                players[4].moveBy(x - players[4].getWidth() / 2, y - players[4].getHeight() / 2);
            }

            public void dragStop(InputEvent event, float x, float y, int pointer) {
                if (players[4].getX() > players[1].getX() - players[1].getWidth() / 4 &&
                        players[4].getX() < players[1].getX() + players[1].getWidth() / 4 &&
                        players[4].getY() > players[1].getY() - players[1].getHeight() / 4 &&
                        players[4].getY() < players[1].getY() + players[1].getHeight() / 4) {
                    changeMyPlayer(4, 1);
                } else if (players[4].getX() > players[2].getX() - players[2].getWidth() / 4 &&
                        players[4].getX() < players[2].getX() + players[2].getWidth() / 4 &&
                        players[4].getY() > players[2].getY() - players[2].getHeight() / 4 &&
                        players[4].getY() < players[2].getY() + players[2].getHeight() / 4) {
                    changeMyPlayer(4, 2);
                } else if (players[4].getX() > players[3].getX() - players[3].getWidth() / 4 &&
                        players[4].getX() < players[3].getX() + players[3].getWidth() / 4 &&
                        players[4].getY() > players[3].getY() - players[3].getHeight() / 4 &&
                        players[4].getY() < players[3].getY() + players[3].getHeight() / 4) {
                    changeMyPlayer(4, 3);
                } else if (players[4].getX() > players[0].getX() - players[0].getWidth() / 4 &&
                        players[4].getX() < players[0].getX() + players[0].getWidth() / 4 &&
                        players[4].getY() > players[0].getY() - players[0].getHeight() / 4 &&
                        players[4].getY() < players[0].getY() + players[0].getHeight() / 4) {
                    changeMyPlayer(4, 0);
                } else {
                    setPosition(position);
                }
                players_bg[4].setZIndex(tmp_z_index_bg);
                players[4].setZIndex(tmp_z_index);
            }
        });
    }

    private Emitter.Listener onFinalListener = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            JSONObject response = (JSONObject) args[0];
            System.out.println("final result ::: " + response);
            ServerTool.getInstance().socket.off("startTheFuckinMatch");
            ServerTool.getInstance().socket.off("before");
            ServerTool.getInstance().socket.off("after");
            ServerTool.getInstance().socket.off("finalResult");
            try {
                if (response.getString("playerId1").matches(GamePrefs.getInstance().playerId)) {
                    JSONObject achs = response.getJSONObject("achievements1");
                    JSONObject level = response.getJSONObject("level1");
                    GamePrefs.getInstance().xp = level.getInt("xp");
                    if (level.getInt("lvl") > GamePrefs.getInstance().level) {
                        GamePrefs.getInstance().isLevelUp = true;
                    }
                    GamePrefs.getInstance().level = level.getInt("lvl");
                    if (GamePrefs.getInstance().achieve_winInaRow < achs.getInt("winInaRow")) {
                        MyGame.mPlayServices.unlockAchievement(achs.getInt("winInaRow") + 17);
                    }
                    if (GamePrefs.getInstance().achieve_win < achs.getInt("win")) {
                        MyGame.mPlayServices.unlockAchievement(achs.getInt("win"));
                    }
                    if (GamePrefs.getInstance().achieve_cleanSheet < achs.getInt("cleanSheet")) {
                        MyGame.mPlayServices.unlockAchievement(achs.getInt("cleanSheet") + 11);
                    }
                    if (GamePrefs.getInstance().achieve_goal < achs.getInt("goal")) {
                        MyGame.mPlayServices.unlockAchievement(achs.getInt("goal") + 5);
                    }
                    GamePrefs.getInstance().achieve_winInaRow = achs.getInt("winInaRow");
                    GamePrefs.getInstance().achieve_win = achs.getInt("win");
                    GamePrefs.getInstance().achieve_cleanSheet = achs.getInt("cleanSheet");
                    GamePrefs.getInstance().achieve_goal = achs.getInt("goal");
                    matchStat.myGoals = response.getInt("result1");
                    matchStat.oppGoals = response.getInt("result2");
                } else {
                    JSONObject achs = response.getJSONObject("achievements2");
                    JSONObject level = response.getJSONObject("level2");
                    GamePrefs.getInstance().xp = level.getInt("xp");
                    if (level.getInt("lvl") > GamePrefs.getInstance().level) {
                        GamePrefs.getInstance().isLevelUp = true;
                    }
                    GamePrefs.getInstance().level = level.getInt("lvl");
                    if (GamePrefs.getInstance().achieve_winInaRow < achs.getInt("winInaRow")) {
                        MyGame.mPlayServices.unlockAchievement(achs.getInt("winInaRow") + 17);
                    }
                    if (GamePrefs.getInstance().achieve_win < achs.getInt("win")) {
                        MyGame.mPlayServices.unlockAchievement(achs.getInt("win"));
                    }
                    if (GamePrefs.getInstance().achieve_cleanSheet < achs.getInt("cleanSheet")) {
                        MyGame.mPlayServices.unlockAchievement(achs.getInt("cleanSheet") + 11);
                    }
                    if (GamePrefs.getInstance().achieve_goal < achs.getInt("goal")) {
                        MyGame.mPlayServices.unlockAchievement(achs.getInt("goal") + 5);
                    }
                    GamePrefs.getInstance().achieve_winInaRow = achs.getInt("winInaRow");
                    GamePrefs.getInstance().achieve_win = achs.getInt("win");
                    GamePrefs.getInstance().achieve_cleanSheet = achs.getInt("cleanSheet");
                    GamePrefs.getInstance().achieve_goal = achs.getInt("goal");
                    matchStat.myGoals = response.getInt("result2");
                    matchStat.oppGoals = response.getInt("result1");
                }
                endGame();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private Emitter.Listener onReadyListener = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            matchStat.isMatchReady = true;
            mModel.sendAfter();
        }

    };

    private Emitter.Listener onBeforeListener = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            JSONObject response = (JSONObject) args[0];
            JSONObject tmp;
            JSONArray array;
            try {
                if (!response.getString("playerId").equals(GamePrefs.getInstance().playerId)) {
                    tmp = response.getJSONObject("direction");
                    matchStat.oppPlayerShooting = tmp.getInt("player");
                    array = tmp.getJSONArray("arrow");
                    matchStat.oppShootDirection = new Vector2(Float.parseFloat(array.getString(0)), Float.parseFloat(array.getString(1)));
                    matchStat.isOppReady = true;
                } else {
                    tmp = response.getJSONObject("direction");
                    matchStat.myPlayerShooting = tmp.getInt("player");
                    array = tmp.getJSONArray("arrow");
                    matchStat.myShootDirection = new Vector2(Float.parseFloat(array.getString(0)), Float.parseFloat(array.getString(1)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    };

    private Emitter.Listener onBeforeGoalerListener = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            JSONObject response = (JSONObject) args[0];
            try {
                if (!response.getString("playerId").equals(GamePrefs.getInstance().playerId)) {
                    matchStat.goaler_position = response.getInt("direction");
                    matchStat.isOppReady = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    };

    private Emitter.Listener onAfterListener = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            if (matchStat.half_status != Constants.HALF_PENALTY) {
                JSONObject response = (JSONObject) args[0];
                try {
                    String playerId = response.getString("playerId");
                    if (!playerId.equals(GamePrefs.getInstance().playerId)) {
                        JSONArray keeper = response.getJSONArray("keeperPosition");
                        JSONArray ball = response.getJSONArray("ballPosition");
                        JSONObject players = response.getJSONObject("playersPosition");
                        mModel.checkPositions(new Vector2(Float.parseFloat(keeper.getString(0)), Float.parseFloat(keeper.getString(1))),
                                new Vector2(Float.parseFloat(ball.getString(0)), Float.parseFloat(ball.getString(1))),
                                new Vector2(Float.parseFloat(players.getJSONArray("1").getString(0)), Float.parseFloat(players.getJSONArray("1").getString(1))),
                                new Vector2(Float.parseFloat(players.getJSONArray("2").getString(0)), Float.parseFloat(players.getJSONArray("2").getString(1))),
                                new Vector2(Float.parseFloat(players.getJSONArray("3").getString(0)), Float.parseFloat(players.getJSONArray("3").getString(1))),
                                new Vector2(Float.parseFloat(players.getJSONArray("4").getString(0)), Float.parseFloat(players.getJSONArray("4").getString(1))),
                                new Vector2(Float.parseFloat(players.getJSONArray("5").getString(0)), Float.parseFloat(players.getJSONArray("5").getString(1))));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    };

    private Emitter.Listener onResignListener = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            JSONObject response = (JSONObject) args[0];

            try {
                if (response.getString("playerId1").matches(GamePrefs.getInstance().playerId)) {
                    JSONObject achs = response.getJSONObject("achievements1");
                    JSONObject level = response.getJSONObject("level1");
                    GamePrefs.getInstance().xp = level.getInt("xp");
                    if (level.getInt("lvl") > GamePrefs.getInstance().level) {
                        GamePrefs.getInstance().isLevelUp = true;
                    }
                    GamePrefs.getInstance().level = level.getInt("lvl");
                    if (GamePrefs.getInstance().achieve_winInaRow < achs.getInt("winInaRow")) {
                        MyGame.mPlayServices.unlockAchievement(achs.getInt("winInaRow") + 17);
                    }
                    if (GamePrefs.getInstance().achieve_win < achs.getInt("win")) {
                        MyGame.mPlayServices.unlockAchievement(achs.getInt("win"));
                    }
                    if (GamePrefs.getInstance().achieve_cleanSheet < achs.getInt("cleanSheet")) {
                        MyGame.mPlayServices.unlockAchievement(achs.getInt("cleanSheet") + 11);
                    }
                    if (GamePrefs.getInstance().achieve_goal < achs.getInt("goal")) {
                        MyGame.mPlayServices.unlockAchievement(achs.getInt("goal") + 5);
                    }
                    GamePrefs.getInstance().achieve_winInaRow = achs.getInt("winInaRow");
                    GamePrefs.getInstance().achieve_win = achs.getInt("win");
                    GamePrefs.getInstance().achieve_cleanSheet = achs.getInt("cleanSheet");
                    GamePrefs.getInstance().achieve_goal = achs.getInt("goal");
                    matchStat.myGoals = response.getInt("result1");
                    matchStat.oppGoals = response.getInt("result2");
                } else {
                    JSONObject achs = response.getJSONObject("achievements2");
                    JSONObject level = response.getJSONObject("level2");
                    GamePrefs.getInstance().xp = level.getInt("xp");
                    if (level.getInt("lvl") > GamePrefs.getInstance().level) {
                        GamePrefs.getInstance().isLevelUp = true;
                    }
                    GamePrefs.getInstance().level = level.getInt("lvl");
                    if (GamePrefs.getInstance().achieve_winInaRow < achs.getInt("winInaRow")) {
                        MyGame.mPlayServices.unlockAchievement(achs.getInt("winInaRow") + 17);
                    }
                    if (GamePrefs.getInstance().achieve_win < achs.getInt("win")) {
                        MyGame.mPlayServices.unlockAchievement(achs.getInt("win"));
                    }
                    if (GamePrefs.getInstance().achieve_cleanSheet < achs.getInt("cleanSheet")) {
                        MyGame.mPlayServices.unlockAchievement(achs.getInt("cleanSheet") + 11);
                    }
                    if (GamePrefs.getInstance().achieve_goal < achs.getInt("goal")) {
                        MyGame.mPlayServices.unlockAchievement(achs.getInt("goal") + 5);
                    }
                    GamePrefs.getInstance().achieve_winInaRow = achs.getInt("winInaRow");
                    GamePrefs.getInstance().achieve_win = achs.getInt("win");
                    GamePrefs.getInstance().achieve_cleanSheet = achs.getInt("cleanSheet");
                    GamePrefs.getInstance().achieve_goal = achs.getInt("goal");
                    matchStat.myGoals = response.getInt("result2");
                    matchStat.oppGoals = response.getInt("result1");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            matchStat.GAME_STATE = Constants.GAME_WINNER;
            matchStat.isWinner = true;
            endGame();
        }

    };

    private Emitter.Listener onOppDCListener = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            matchStat.GAME_STATE = Constants.GAME_WINNER;
        }

    };

    private void changeMyPlayer(int out, int in) {
        int out_index = 0, in_index = 0;
        for (int i = 0; i < 5; i++) {
            if (matchStat.myLineup[i] == out)
                out_index = i;
            else if (matchStat.myLineup[i] == in)
                in_index = i;
        }
        matchStat.myLineup[out_index] = in;
        matchStat.myLineup[in_index] = out;

        setPosition(position);
    }

    void setPosition(final Vector2[] position) {
        for (int i = 0; i < 4; i++) {
            Tween.to(players[matchStat.myLineup[i]], 1, .3f)
                    .target(position[i].x, position[i].y)
                    .ease(TweenEquations.easeInBack)
                    .start(mTweenManager).delay(0.0F);

            Tween.to(players_bg[matchStat.myLineup[i]], 1, .3f)
                    .target(position[i].x, position[i].y)
                    .ease(TweenEquations.easeInBack)
                    .start(mTweenManager).delay(0.0F);
        }
        Tween.to(players[matchStat.myLineup[4]], 1, .3f)
                .target(position[4].x, position[4].y)
                .ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(0.0F)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                        Tween.to(selected, 1, .3f)
                                .target(players[selected_player].getX() + Constants.HUD_SCREEN_WIDTH * .004f,
                                        players[selected_player].getY() + Constants.HUD_SCREEN_HEIGHT * .006f)
                                .ease(TweenEquations.easeInBack)
                                .start(mTweenManager).delay(0.0F);
                        setStaminaDots();
                    }
                });

        Tween.to(players_bg[matchStat.myLineup[4]], 1, .3f)
                .target(position[4].x, position[4].y)
                .ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(0.0F);
    }

    @Override
    public void show() {
        mMainCamera = new OrthographicCamera(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        mMainCamera.update();
        OrthographicCamera mHUDCamera = new OrthographicCamera(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);
        mHUDCamera.update();

        if (GamePrefs.getInstance().isMusicOn() == 1) {
            Assets.getInstance().stadium.setVolume(1f);
        }

        mJson = new Json();

        Tween.to(bg_dark, 3, .2f)
                .target(.5f).ease(TweenEquations.easeInCubic)
                .start(mTweenManager).delay(.5f)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                        Tween.to(bg_dark, 3, .4f)
                                .target(.95f).ease(TweenEquations.easeInCubic)
                                .start(mTweenManager).delay(0F)
                                .setCallback(new TweenCallback() {
                                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                                        Tween.to(bg_dark, 3, .3f)
                                                .target(.5f).ease(TweenEquations.easeInCubic)
                                                .start(mTweenManager).delay(0F)
                                                .setCallback(new TweenCallback() {
                                                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                                                        Tween.to(bg_dark, 3, .4f)
                                                                .target(.9f).ease(TweenEquations.easeInCubic)
                                                                .start(mTweenManager).delay(0F)
                                                                .setCallback(new TweenCallback() {
                                                                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                                                                        Tween.to(bg_dark, 3, .3f)
                                                                                .target(0).ease(TweenEquations.easeInCubic)
                                                                                .start(mTweenManager).delay(0F)
                                                                                .setCallback(new TweenCallback() {
                                                                                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {

                                                                                    }
                                                                                });
                                                                    }
                                                                });
                                                    }
                                                });
                                    }
                                });
                    }
                });

        mModel = new PhysicalModel(matchStat, mTweenManager);
        mModel.setScreen(this);
        mModel.addModelListener(this);

        mGameRenderer = new GameRenderer(mMainCamera, mMainBatch, matchStat, mTweenManager, mStage,
                myPlayers, oppPlayers, myPlayersStable, oppPlayersStable, myPlayersOn, balls, keeper);

        Controls mControls = new Controls(mModel, mMainCamera);
        mControls.setState(State.IDLE);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(mStage2);
        inputMultiplexer.addProcessor(mControls);
        Gdx.input.setInputProcessor(inputMultiplexer);

        position = util.getInGameSettingPosition(matchStat.myFormation);
        setPosition(position);

        modelBatch = new ModelBatch();

        ModelLoader<?> loader = new ObjLoader();

        FileHandle in = Gdx.files.internal("img/dd/att_in.obj");
        in.read();
        Model model1 = loader.loadModel(in);

        in = Gdx.files.internal("img/dd/def_in.obj");
        in.read();
        Model model2 = loader.loadModel(in);

        BlendingAttribute attr1 = new BlendingAttribute(GL20.GL_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        BlendingAttribute attr2 = new BlendingAttribute(GL20.GL_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        inst1 = new ModelInstance(model1);
        inst2 = new ModelInstance(model2);
        inst1.transform.setToTranslation(0f, 0f, 4f);
        inst1.transform.setToScaling(.1f, .1f, .1f);
        inst2.transform.setToTranslation(0f, 0f, 4f);
        inst2.transform.setToScaling(.1f, .1f, .1f);
        Material mtr = inst1.materials.get(0);
        mtr.set(attr1);
        mtr = inst2.materials.get(0);
        mtr.set(attr2);
        attr1.opacity = .5f;
        attr2.opacity = .5f;
        if (matchStat.turn.matches(GamePrefs.getInstance().playerId)) {
            instances.add(inst1);
        } else {
            instances.add(inst2);
        }

        doRotaion();

        environment = new Environment();

        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(0f, 0f, 5f);
        cam.lookAt(0, 0, 0);
        cam.near = 1f;
        cam.far = 100f;
        cam.update();
    }

    private void doRotaion() {
        Tween.to(inst1, 1, 4f)
                .target(360).ease(TweenEquations.easeInElastic)
                .start(mTweenManager).delay(0F);
        Tween.to(inst2, 1, 4f)
                .target(360).ease(TweenEquations.easeInExpo)
                .start(mTweenManager).delay(0F);
    }

    public void endofRound() {
        for (int i = 0; i < 5; i++) {
            Tween.to(myPlayersOn[i], 3, .7f)
                    .target(1).ease(TweenEquations.easeInExpo)
                    .start(mTweenManager).delay(0F);
        }
        doRotaion();
        matchStat.roundNum += 1;
        if (matchStat.roundNum == 11) {
            round_num.setText("-");
        } else if (matchStat.roundNum == 22) {
            round_num.setText("-");
        } else if (matchStat.roundNum > 22) {
            round_num.setText("P" + (matchStat.roundNum - 21) / 2);
        } else if (matchStat.roundNum > 11) {
            round_num.setText((matchStat.roundNum - 11) + "");
        } else {
            round_num.setText(matchStat.roundNum + "");
        }
        matchStat.myShootDirection = null;
        matchStat.goaler_position = 0;
        int tmp = 0;
        if (matchStat.isPenaltymode) {
            tmp = 4;
        }
        if (matchStat.roundNum == 22) {
            if (matchStat.myGoals > matchStat.oppGoals) {
                matchStat.GAME_STATE = Constants.GAME_WINNER;
                matchStat.isWinner = true;
                endGame();
            } else if (matchStat.oppGoals > matchStat.myGoals) {
                matchStat.GAME_STATE = Constants.GAME_LOSER;
                matchStat.isWinner = false;
                endGame();
            } else {
                instances.clear();
                if (matchStat.turn.matches(GamePrefs.getInstance().playerId)) {
                    matchStat.turn = "";
                } else {
                    matchStat.turn = GamePrefs.getInstance().playerId;
                }
                matchStat.half_status = Constants.HALF_PENALTY;
                if (matchStat.isPenaltymode) {
                    matchStat.GAME_STATE = Constants.GAME_PRE_PENALTY;
                } else {
                    matchStat.GAME_STATE = Constants.GAME_PENALTY;
                }
            }
            return;
        } else if (matchStat.roundNum == 11) {
            matchStat.half_status = Constants.HALF_SECOND;
            matchStat.GAME_STATE = Constants.GAME_2ND_HALF;
            if (matchStat.turn.matches(GamePrefs.getInstance().playerId)) {
                matchStat.turn = "";
                instances.clear();
                instances.add(inst2);
            } else {
                matchStat.turn = GamePrefs.getInstance().playerId;
                instances.clear();
                instances.add(inst1);
            }

            return;
        } else if (matchStat.roundNum <= (28 + tmp) && matchStat.roundNum > 22) {
            int diff;
            if (matchStat.roundNum % 2 == 1) {
                diff = (28 + tmp - matchStat.roundNum + 1) / 2;
            } else {
                diff = (28 + tmp - matchStat.roundNum) / 2;
            }
            if (matchStat.myGoals > matchStat.oppGoals) {
                if (matchStat.myGoals > matchStat.oppGoals + diff) {
                    matchStat.GAME_STATE = Constants.GAME_WINNER;
                    matchStat.isWinner = true;
                    endGame();
                    return;
                } else if (matchStat.oppGoals > matchStat.myGoals + diff) {
                    matchStat.GAME_STATE = Constants.GAME_LOSER;
                    matchStat.isWinner = false;
                    endGame();
                    return;
                }
            }
        } else if (matchStat.roundNum > (28 + tmp) && (matchStat.roundNum % 2 == 1)) {
            if (matchStat.myGoals > matchStat.oppGoals) {
                matchStat.GAME_STATE = Constants.GAME_WINNER;
                matchStat.isWinner = true;
                endGame();
                return;
            } else if (matchStat.oppGoals > matchStat.myGoals) {
                matchStat.GAME_STATE = Constants.GAME_LOSER;
                matchStat.isWinner = false;
                endGame();
                return;
            }
        }
        if (matchStat.half_status == Constants.HALF_PENALTY) {
            if (matchStat.isMeFirst) {
                if (matchStat.turn.matches(GamePrefs.getInstance().playerId)) {
                    matchStat.turn = "";
                } else {
                    matchStat.turn = GamePrefs.getInstance().playerId;
                }
                goaler_up_1.setVisible(true);
                goaler_up_2.setVisible(true);
                goaler_down_1.setVisible(true);
                goaler_down_2.setVisible(true);
            } else {
                goaler_up_1.setVisible(false);
                goaler_up_2.setVisible(false);
                goaler_down_1.setVisible(false);
                goaler_down_2.setVisible(false);
            }

            if (GamePrefs.getInstance().isEffectOn() == 1 && !p_w_flag) {
                Assets.getInstance().whistle.play();
                p_w_flag = true;
            }

            progress[0].setPercentage(1f);
            progress[1].setPercentage(1f);
            prog_bar = Tween.to(progress, 1, 12f)
                    .target(0).ease(TweenEquations.easeInCubic)
                    .start(mTweenManager).delay(0F)
                    .setCallback(new TweenCallback() {
                        public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                            for (int i = 0; i < 5; i++) {
                                Tween.to(myPlayersOn[i], 3, .7f)
                                        .target(0).ease(TweenEquations.easeInExpo)
                                        .start(mTweenManager).delay(0F);
                            }
                            mModel.sendBefore();
                            if (matchStat.isOppReady) {
                                mModel.startRendering();
                            } else {
                                matchStat.GAME_STATE = Constants.GAME_WAITING;
                            }
                        }
                    });
        } else {
            if (GamePrefs.getInstance().isEffectOn() == 1 && !fh_w_flag) {
                Assets.getInstance().whistle.play();
                fh_w_flag = true;
            }
            if (GamePrefs.getInstance().isEffectOn() == 1 && !sh_w_flag && matchStat.half_status == Constants.HALF_SECOND) {
                Assets.getInstance().whistle.play();
                sh_w_flag = true;
            }
            progress[0].setPercentage(1f);
            progress[1].setPercentage(1f);
            prog_bar = Tween.to(progress, 1, 15f)
                    .target(0).ease(TweenEquations.easeInCubic)
                    .start(mTweenManager).delay(0F)
                    .setCallback(new TweenCallback() {
                        public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                            for (int i = 0; i < 5; i++) {
                                Tween.to(myPlayersOn[i], 3, .7f)
                                        .target(0).ease(TweenEquations.easeInExpo)
                                        .start(mTweenManager).delay(0F);
                            }
                            mModel.sendBefore();
                            if (matchStat.isOppReady) {
                                mModel.startRendering();
                            } else {
                                matchStat.GAME_STATE = Constants.GAME_WAITING;
                            }
                        }
                    });
        }
    }

    public void finishGameScreen() {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                MyGame.mainInstance.setResultScreen(matchStat);
            }
        });
    }

    private void endGame() {
        mTweenManager.killTarget(prog_bar);

        JSONObject data = new JSONObject();
        try {
            data.put("playerId", GamePrefs.getInstance().playerId);

            ServerTool.getInstance().socket.emit("matchEnd", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        GamePrefs.getInstance().game_played += 1;
        GamePrefs.getInstance().goals += matchStat.myGoals;

        if (GamePrefs.getInstance().isMusicOn() == 1) {
            Assets.getInstance().main_theme.play();
            Assets.getInstance().stadium.setVolume(.2f);
        }
        if (GamePrefs.getInstance().isEffectOn() == 1) {
            Assets.getInstance().whistle.play();
        }

        if (matchStat.isWinner) {
            matchStat.GAME_STATE = Constants.GAME_WINNER;
            GamePrefs.getInstance().game_won += 1;
            if (GamePrefs.getInstance().isVibrateOn() == 1) {
                Gdx.input.vibrate(new long[]{0, 500, 110, 500, 110, 450}, -1);
            }
        } else {
            matchStat.GAME_STATE = Constants.GAME_LOSER;
        }
        GamePrefs.getInstance().win_percent = GamePrefs.getInstance().game_won / GamePrefs.getInstance().game_played;
    }

    public void goalScored() {
        if (matchStat.half_status == Constants.HALF_PENALTY) {
            if (!matchStat.isMeFirst) {
                matchStat.myGoals += 1;
                Tween.to(left_score, 5, .5f)
                        .target(0).ease(TweenEquations.easeInOutBounce)
                        .delay(0F).repeatYoyo(11, 0)
                        .start(mTweenManager)
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                left_score.setColor(1, 1, 1, 1);
                            }
                        });
                JSONObject data = new JSONObject();
                try {
                    data.put("playerId", GamePrefs.getInstance().playerId);

                    ServerTool.getInstance().socket.emit("goal", data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Tween.to(right_score, 5, .5f)
                        .target(0).ease(TweenEquations.easeInOutBounce)
                        .delay(0F).repeatYoyo(10, 0)
                        .start(mTweenManager)
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                right_score.setColor(1, 1, 1, 1);
                            }
                        });
                matchStat.oppGoals += 1;
            }
        } else {
            if (matchStat.turn.matches(GamePrefs.getInstance().playerId)) {
                matchStat.myGoals += 1;
                Tween.to(left_score, 6, .5f)
                        .target(200).ease(TweenEquations.easeInOutBounce)
                        .delay(0F).repeat(10, 0f).repeatYoyo(10, 0)
                        .start(mTweenManager)
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                left_score.setColor(1, 1, 1, 1);
                            }
                        });
                JSONObject data = new JSONObject();
                try {
                    data.put("playerId", GamePrefs.getInstance().playerId);

                    ServerTool.getInstance().socket.emit("goal", data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Tween.to(right_score, 6, .5f)
                        .target(200).ease(TweenEquations.easeInOutBounce)
                        .delay(0F).repeat(10, 0f).repeatYoyo(10, 0)
                        .start(mTweenManager)
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                right_score.setColor(1, 1, 1, 1);
                            }
                        });
                matchStat.oppGoals += 1;
            }
        }

        if (GamePrefs.getInstance().isVibrateOn() == 1) {
            Gdx.input.vibrate(800);
        }

        left_score.setText(matchStat.myGoals + "");
        right_score.setText(matchStat.oppGoals + "");

        if (GamePrefs.getInstance().isEffectOn() == 1) {
            Assets.getInstance().goal.play();
        }
    }

    public void removePopups() {
        for (Actor actor : mStage.getActors()) {
            if (actor.getName() != null) {
                if (actor.getName().equals(this.popup_string)) {
                    actor.remove();
                }
            }
        }
        dark_bg.setPosition(0, Constants.HUD_SCREEN_HEIGHT * -1);
    }

    @Override
    public void render(float delta) {

        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        while (accumulator >= dt) {
            mModel.update(dt);
            accumulator -= dt;
        }

        //Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        // Restore the stage's viewport.
        mStage0.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        mStage0.act(dt);
        mStage0.draw();

        //TODO darim mirinim
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        // Draw all model instances using the camera
        modelBatch.begin(cam);
        modelBatch.render(instances, environment);
        modelBatch.end();

        // Restore the stage's viewport.
        mStage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        mStage.act(dt);
        mStage.draw();

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        mGameRenderer.render();
        mModel.debugRender(mMainCamera);

        // Restore the stage's viewport.
        mStage2.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        mStage2.act(dt);
        mStage2.draw();

        /*---------- FPS check ----------------------------
        frameCount++;
        long now = System.nanoTime();

        if ((now - lastRender) >= FPSupdateIntervall * 1000000000) {

            lastFPS = frameCount / FPSupdateIntervall;

            frameCount = 0;
            lastRender = System.nanoTime();
        }
        //--------------------------------------------------------------
        renderFIXEDTIMESTEP(delta);*/
    }

    //====================================================================================
    //	http://gafferongames.com/game-physics/fix-your-timestep/
    public void renderFIXEDTIMESTEP(float delta) {

        if (delta > 0.25f) delta = 0.25f;      // note: max frame time to avoid spiral of death

        accumulator += delta;

        while (accumulator >= dt) {

//            game.getGameObjectManager().copyCurrentPosition();

            updating(dt);
            accumulator -= dt;

//            game.getGameObjectManager().interpolateCurrentPosition(accumulator / dt);

            //---------- FPS check -----------------------------
            logic_frameCount++;
            long logic_now = System.nanoTime();

            if ((logic_now - logic_lastRender) >= logic_FPSupdateIntervall * 1000000000) {

                int logic_lastFPS = logic_frameCount / logic_FPSupdateIntervall;
                logic_frameCount = 0;
                logic_lastRender = System.nanoTime();
            }
            //--------------------------------------------------------------
        }

        rendering(delta);
    }


    //====================================================================================
    private void updating(float delta) {
        mModel.update(delta);
    }

    //====================================================================================
    private void rendering(float delta) {

        //------------- to limit fps ------------------------
        if (RENDERER_SLEEP_MS > 0) {

            long now2 = System.currentTimeMillis();
            long diff = now2 - start;

            if (diff < RENDERER_SLEEP_MS) {
                try {
                    Thread.sleep(RENDERER_SLEEP_MS - diff);
                } catch (InterruptedException ignored) {
                }
            }

            start = System.currentTimeMillis();
        }
        //-----------------------------------------------------
    }

    @Override
    public void resize(int width, int height) {
        float aspectRatio = (float) width / height;
        if (aspectRatio < Constants.SCREEN_WIDTH / Constants.SCREEN_HEIGHT) {
            mMainCamera.viewportHeight = Constants.SCREEN_WIDTH / aspectRatio;
            mMainCamera.viewportWidth = Constants.SCREEN_WIDTH;
        } else {
            mMainCamera.viewportWidth = Constants.SCREEN_HEIGHT * aspectRatio;
            mMainCamera.viewportHeight = Constants.SCREEN_HEIGHT;
        }
        mMainCamera.update();

        mStage.getViewport().update(width, (int) (Constants.SCREEN_HEIGHT * aspectRatio), true);
        if (aspectRatio < Constants.SCREEN_WIDTH / Constants.SCREEN_HEIGHT) {
            mMainBatch.getProjectionMatrix().setToOrtho2D(0, 0, (int) (Constants.SCREEN_WIDTH / aspectRatio), height);
        } else {
            mMainBatch.getProjectionMatrix().setToOrtho2D(0, 0, width, (int) (Constants.SCREEN_HEIGHT * aspectRatio));
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void onModelUpdate(String modelState) {
        mGameRenderer.updateModel(mJson.fromJson(Field.class, modelState));
    }

    @Override
    public void goalEvent() {

    }

    @Override
    public void winEvent() {

    }

    @Override
    public void dispose() {
        mStage.dispose();
        if (mModel != null) {
            mModel.dispose();
        }

        ServerTool.getInstance().socket.off("startTheFuckinMatch");
        ServerTool.getInstance().socket.off("before");
        ServerTool.getInstance().socket.off("after");
        ServerTool.getInstance().socket.off("finalResult");
    }
}