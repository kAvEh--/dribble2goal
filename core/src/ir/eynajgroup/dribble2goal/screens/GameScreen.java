package ir.eynajgroup.dribble2goal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import io.socket.emitter.Emitter;
import ir.eynajgroup.dribble2goal.Assets;
import ir.eynajgroup.dribble2goal.Constants;
import ir.eynajgroup.dribble2goal.GamePrefs;
import ir.eynajgroup.dribble2goal.MatchStats;
import ir.eynajgroup.dribble2goal.MyGame;
import ir.eynajgroup.dribble2goal.Server.ServerTool;
import ir.eynajgroup.dribble2goal.Util.MatchConstants;
import ir.eynajgroup.dribble2goal.Util.Util;
import ir.eynajgroup.dribble2goal.input.Controls;
import ir.eynajgroup.dribble2goal.input.State;
import ir.eynajgroup.dribble2goal.model.IModel;
import ir.eynajgroup.dribble2goal.model.IModelListener;
import ir.eynajgroup.dribble2goal.model.IRenderer;
import ir.eynajgroup.dribble2goal.model.PhysicalModel;
import ir.eynajgroup.dribble2goal.render.GameRenderer;
import ir.eynajgroup.dribble2goal.render.textures.ProgressCircle;
import ir.eynajgroup.dribble2goal.render.textures.ProgressLine;
import ir.eynajgroup.dribble2goal.template.Field;

/**
 * Created by kAvEh on 3/21/2016.
 */
public class GameScreen implements Screen, IModelListener {

    TweenManager mTweenManager;
    private OrthographicCamera mMainCamera;
    SpriteBatch mMainBatch;

    Image bg;
    Image bg_dark;
    Table board;
    Table board_fake;
    Image bg_arc;
    Image goal_board;
    Image board_bg;
    Image avatar_bg_left;
    Image avatar_left;
    Image avatar_bg_right;
    Image avatar_right;
    Image court;
    Image next_icon;
    Image prev_icon;
    Image[] players;
    Image selected;
    Image p_stamina;
    Image p_size;
    Image p_speed;
    Label t_stamina;
    Label t_size;
    Label t_speed;
    Label left_name;
    Label left_score;
    Label right_name;
    Label right_score;
    ProgressLine progress;

    int selected_player = 1;

    Stage mStage;
    Stage mStage2;
    Table mainTable;
    Skin mSkin;
    Random random;

    Util util;

    boolean isBoardUp = true;

    MatchStats matchStat;
    MatchConstants matchConstants;

    float dt = 1 / 60f;
    float accumulator;

    private final static int logic_FPSupdateIntervall = 1;  //--- display FPS alle x sekunden
    private long logic_lastRender;
    public int logic_frameCount = 0;
    public int logic_lastFPS = 0;

    public static final long RENDERER_SLEEP_MS = 0; // 34 -> 30 fps, 30 -> 34 fps, 22 gives ~46 FPS, 20 = 100, 10 = 50
    private long now2, diff, start;

    private IModel mModel;
    private IRenderer mGameRenderer;

    private Json mJson;
    ProgressCircle sprite;

    public GameScreen(MatchStats stat) {
        ServerTool.getInstance().socket.on("startTheFuckinMatch", onReadyListener);
        ServerTool.getInstance().socket.on("before", onBeforeListener);
        ServerTool.getInstance().socket.on("after", onAfterListener);
        ServerTool.getInstance().socket.on("finalResult", onFinalListener);

        mTweenManager = MyGame.mTweenManager;
        mMainBatch = new SpriteBatch();

        random = new Random();

        matchStat = stat;
        mModel = new PhysicalModel(matchStat, mTweenManager);
        mModel.setScreen(this);
        mModel.addModelListener(this);

        matchConstants = new MatchConstants();

        players = new Image[5];

        mStage = new Stage(new FitViewport(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT), mMainBatch);
        mStage2 = new Stage(new FitViewport(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT), mMainBatch);
        mainTable = new Table();

        util = new Util();
        mSkin = new Skin(Gdx.files.internal("data/uiskin.json"));

        bg = new Image(util.getStadium(stat.matchLevel));
        bg_dark = new Image(util.getStadiumDark(stat.matchLevel));
        bg_arc = new Image(util.getStadiumArc(stat.matchLevel));
        goal_board = new Image(Assets.getInstance().goal_sample);
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

        Image temp = new Image();
        temp.setSize(Constants.HUD_SCREEN_WIDTH * .54f, Constants.HUD_SCREEN_HEIGHT * .99f);

        board_fake.addActor(temp);

        board_bg = new Image(Assets.getInstance().board_bg);
        board_bg.setSize(Constants.HUD_SCREEN_WIDTH * .59f, Constants.HUD_SCREEN_HEIGHT * .99f);

        avatar_bg_left = new Image(Assets.getInstance().profile_background);
        avatar_bg_left.setSize(Constants.HUD_SCREEN_WIDTH * .1f, Constants.HUD_SCREEN_HEIGHT * .178f);
        avatar_bg_left.setPosition(Constants.HUD_SCREEN_WIDTH * .16f, Constants.HUD_SCREEN_HEIGHT * .75f);

        avatar_bg_right = new Image(Assets.getInstance().profile_background);
        avatar_bg_right.setSize(Constants.HUD_SCREEN_WIDTH * .1f, Constants.HUD_SCREEN_HEIGHT * .178f);
        avatar_bg_right.setPosition(Constants.HUD_SCREEN_WIDTH * .33f, Constants.HUD_SCREEN_HEIGHT * .75f);

        avatar_left = new Image(new Util().getAvatar(GamePrefs.getInstance().avatar));
        avatar_left.setSize(Constants.HUD_SCREEN_WIDTH * .1f, Constants.HUD_SCREEN_HEIGHT * .178f);
        avatar_left.setPosition(Constants.HUD_SCREEN_WIDTH * .16f, Constants.HUD_SCREEN_HEIGHT * .75f);

        avatar_right = new Image(new Util().getAvatar(matchStat.oppAvatar));
        avatar_right.setSize(Constants.HUD_SCREEN_WIDTH * .1f, Constants.HUD_SCREEN_HEIGHT * .178f);
        avatar_right.setPosition(Constants.HUD_SCREEN_WIDTH * .33f, Constants.HUD_SCREEN_HEIGHT * .75f);

        court = new Image(Assets.getInstance().formation_bg);
        court.setSize(Constants.HUD_SCREEN_WIDTH * .165f, Constants.HUD_SCREEN_HEIGHT * .5325f);
        court.setPosition(Constants.HUD_SCREEN_WIDTH * .2125f, Constants.HUD_SCREEN_HEIGHT * .2f);

        prev_icon = new Image(Assets.getInstance().arrow_left);
        prev_icon.setSize(Constants.HUD_SCREEN_WIDTH * .085f, Constants.HUD_SCREEN_HEIGHT * .119f);
        prev_icon.setPosition(Constants.HUD_SCREEN_WIDTH * .122f, Constants.HUD_SCREEN_HEIGHT * .465f);

        next_icon = new Image(Assets.getInstance().arrow_right);
        next_icon.setSize(Constants.HUD_SCREEN_WIDTH * .085f, Constants.HUD_SCREEN_HEIGHT * .119f);
        next_icon.setPosition(Constants.HUD_SCREEN_WIDTH * .383f, Constants.HUD_SCREEN_HEIGHT * .465f);

        p_stamina = new Image(Assets.getInstance().formation_stamina);
        p_stamina.setSize(Constants.HUD_SCREEN_WIDTH * .08f, Constants.HUD_SCREEN_HEIGHT * .074f);
        p_stamina.setPosition(Constants.HUD_SCREEN_WIDTH * .122f, Constants.HUD_SCREEN_HEIGHT * .38f);

        p_size = new Image(Assets.getInstance().formation_size);
        p_size.setSize(Constants.HUD_SCREEN_WIDTH * .08f, Constants.HUD_SCREEN_HEIGHT * .074f);
        p_size.setPosition(Constants.HUD_SCREEN_WIDTH * .122f, Constants.HUD_SCREEN_HEIGHT * .3f);

        p_speed = new Image(Assets.getInstance().formation_speed);
        p_speed.setSize(Constants.HUD_SCREEN_WIDTH * .08f, Constants.HUD_SCREEN_HEIGHT * .074f);
        p_speed.setPosition(Constants.HUD_SCREEN_WIDTH * .122f, Constants.HUD_SCREEN_HEIGHT * .22f);

        mSkin.getFont("english").getData().scale(-.42f);
        t_stamina = new Label(matchStat.myPlayers[selected_player][0] + "", mSkin);
        t_stamina.setPosition(Constants.HUD_SCREEN_WIDTH * .17f, Constants.HUD_SCREEN_HEIGHT * .392f);

        t_size = new Label(matchStat.myPlayers[selected_player][1] + "", mSkin);
        t_size.setPosition(Constants.HUD_SCREEN_WIDTH * .17f, Constants.HUD_SCREEN_HEIGHT * .312f);

        t_speed = new Label(matchStat.myPlayers[selected_player][2] + "", mSkin);
        t_speed.setPosition(Constants.HUD_SCREEN_WIDTH * .17f, Constants.HUD_SCREEN_HEIGHT * .232f);

//        mSkin.getFont("english").getData().scale(.2f);
        left_name = new Label(matchStat.myName + "", mSkin);
        left_name.setPosition(Constants.HUD_SCREEN_WIDTH * .25f - left_name.getWidth(), Constants.HUD_SCREEN_HEIGHT * .09f);

        left_score = new Label(matchStat.myGoals + "", mSkin);
        left_score.setPosition(Constants.HUD_SCREEN_WIDTH * .06f, Constants.HUD_SCREEN_HEIGHT * .07f);

        right_name = new Label(matchStat.oppName + "", mSkin);
        right_name.setPosition(Constants.HUD_SCREEN_WIDTH * .34f, Constants.HUD_SCREEN_HEIGHT * .09f);

        right_score = new Label(matchStat.oppGoals + "", mSkin);
        right_score.setPosition(Constants.HUD_SCREEN_WIDTH * .53f - right_score.getWidth(), Constants.HUD_SCREEN_HEIGHT * .07f);

        TextureRegion region = new TextureRegion(Assets.getInstance().setting_item_bg);
        PolygonSpriteBatch pbatch = new PolygonSpriteBatch();
        sprite = new ProgressCircle(region, pbatch, new Vector2(Constants.HUD_SCREEN_WIDTH * .158f, Constants.HUD_SCREEN_HEIGHT * .158f));
        sprite.setSize(Constants.HUD_SCREEN_WIDTH * .5f, Constants.HUD_SCREEN_WIDTH * .07f);
        sprite.setPosition(Constants.HUD_SCREEN_WIDTH * .01f, Constants.HUD_SCREEN_HEIGHT * .01f);
        sprite.setPercentage(100f);

        Vector2 size = new Vector2(Constants.HUD_SCREEN_WIDTH * .425f, Constants.HUD_SCREEN_HEIGHT * .085f);
        progress = new ProgressLine(Assets.getInstance().time_bar, new SpriteBatch(), region,
                size, true);
        progress.setSize(size.x, size.y);
        progress.setPosition(Constants.HUD_SCREEN_WIDTH * .0825f, Constants.HUD_SCREEN_WIDTH * .0412f);
        progress.setPercentage(1);
        progress.setColor(0f, .6f, 0f, 1f);
        //.721    680
        //board.setSize(Constants.HUD_SCREEN_WIDTH * .59f, Constants.HUD_SCREEN_HEIGHT * .99f);

        players[0] = new Image(new Util().getShirt(GamePrefs.getInstance().shirt));
        players[1] = new Image(new Util().getShirt(GamePrefs.getInstance().shirt));
        players[2] = new Image(new Util().getShirt(GamePrefs.getInstance().shirt));
        players[3] = new Image(new Util().getShirt(GamePrefs.getInstance().shirt));
        players[4] = new Image(new Util().getShirt(GamePrefs.getInstance().shirt));

        selected = new Image(Assets.getInstance().selected_player);
        selected.setSize(Constants.HUD_SCREEN_WIDTH * .06f, Constants.HUD_SCREEN_HEIGHT * .107f);

        players[0].setSize(Constants.HUD_SCREEN_WIDTH * .08f, Constants.HUD_SCREEN_HEIGHT * .142f);
        players[1].setSize(Constants.HUD_SCREEN_WIDTH * .08f, Constants.HUD_SCREEN_HEIGHT * .142f);
        players[2].setSize(Constants.HUD_SCREEN_WIDTH * .08f, Constants.HUD_SCREEN_HEIGHT * .142f);
        players[3].setSize(Constants.HUD_SCREEN_WIDTH * .08f, Constants.HUD_SCREEN_HEIGHT * .142f);
        players[4].setSize(Constants.HUD_SCREEN_WIDTH * .08f, Constants.HUD_SCREEN_HEIGHT * .142f);

        board.addActor(board_bg);
        board.addActor(avatar_bg_left);
        board.addActor(avatar_bg_right);
        board.addActor(avatar_left);
        board.addActor(avatar_right);
        board.addActor(court);
        board.addActor(p_stamina);
        board.addActor(p_size);
        board.addActor(p_speed);
        board.addActor(t_stamina);
        board.addActor(t_size);
        board.addActor(t_speed);
        board.addActor(selected);
        board.addActor(left_name);
        board.addActor(left_score);
        board.addActor(right_name);
        board.addActor(right_score);
//        board.addActor(sprite);
        board.addActor(progress);
        board_fake.addActor(prev_icon);
        board_fake.addActor(next_icon);
        board_fake.addActor(players[0]);
        board_fake.addActor(players[1]);
        board_fake.addActor(players[2]);
        board_fake.addActor(players[3]);
        board_fake.addActor(players[4]);

        this.mainTable.setSize(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);
        mMainCamera = new OrthographicCamera(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);

        mSkin = new Skin(Gdx.files.internal("data/uiskin.json"));

        mStage.addActor(bg);
//        mStage.addActor(bg_dark);
        mainTable.addActor(board);
        mainTable.addActor(bg_arc);
        mainTable.addActor(goal_board);
        mainTable.addActor(board_fake);

        mStage2.addActor(this.mainTable);

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
                Vector2[] position = util.getInGameSettingPosition(matchStat.myFormation, matchStat.myLineup);
                setPosition(position);

                if (matchStat.isMeFirst) {
                    matchStat.myStartPosition = util.getAbovePlayerPosition(matchStat.myFormation, matchStat.myLineup);
                } else {
                    matchStat.myStartPosition = util.getBelowPlayerPosition(matchStat.myFormation, matchStat.myLineup);
                }
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                prev_icon.setSize(Constants.HUD_SCREEN_WIDTH * .069f, Constants.HUD_SCREEN_HEIGHT * .0966f);
                prev_icon.setPosition(Constants.HUD_SCREEN_WIDTH * .13f, Constants.HUD_SCREEN_HEIGHT * .4762f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                prev_icon.setSize(Constants.HUD_SCREEN_WIDTH * .085f, Constants.HUD_SCREEN_HEIGHT * .119f);
                prev_icon.setPosition(Constants.HUD_SCREEN_WIDTH * .122f, Constants.HUD_SCREEN_HEIGHT * .465f);
            }
        });

        next_icon.addListener(new ActorGestureListener() {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                if (matchStat.myFormation == GamePrefs.getInstance().position_num) {
                    matchStat.myFormation = 1;
                } else {
                    matchStat.myFormation = matchStat.myFormation + 1;
                }
                Vector2[] position = util.getInGameSettingPosition(matchStat.myFormation, matchStat.myLineup);
                setPosition(position);

                if (matchStat.isMeFirst) {
                    matchStat.myStartPosition = util.getAbovePlayerPosition(matchStat.myFormation, matchStat.myLineup);
                } else {
                    matchStat.myStartPosition = util.getBelowPlayerPosition(matchStat.myFormation, matchStat.myLineup);
                }
            }

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                next_icon.setSize(Constants.HUD_SCREEN_WIDTH * .069f, Constants.HUD_SCREEN_HEIGHT * .0966f);
                next_icon.setPosition(Constants.HUD_SCREEN_WIDTH * .391f, Constants.HUD_SCREEN_HEIGHT * .4762f);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                next_icon.setSize(Constants.HUD_SCREEN_WIDTH * .085f, Constants.HUD_SCREEN_HEIGHT * .119f);
                next_icon.setPosition(Constants.HUD_SCREEN_WIDTH * .383f, Constants.HUD_SCREEN_HEIGHT * .465f);
            }
        });

        players[0].addListener(new ActorGestureListener() {
            public boolean longPress(Actor actor, float x, float y) {
                if (selected_player != 0) {
                    boolean isSelectedonBench;
                    boolean isClickedonBench;
                    isSelectedonBench = matchStat.myLineup[3] == selected_player ||
                            matchStat.myLineup[4] == selected_player;
                    isClickedonBench = matchStat.myLineup[3] == 0 ||
                            matchStat.myLineup[4] == 0;
                    if (isSelectedonBench) {
                        if (isClickedonBench) {
                            return false;
                        } else {
                            substitution(0, selected_player);
                        }
                    } else {
                        if (isClickedonBench) {
                            substitution(selected_player, 0);
                        } else {
                            return false;
                        }
                    }
                }
                return true;
            }

            public void tap(InputEvent event, float x, float y, int count, int button) {
                Tween.to(selected, 1, .3f)
                        .target(players[0].getX() + Constants.HUD_SCREEN_WIDTH * .0097f,
                                players[0].getY() + Constants.HUD_SCREEN_HEIGHT * .022f)
                        .ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F);

                selected_player = 0;

                t_stamina.setText(matchStat.myPlayers[selected_player][0] + "");
                t_size.setText(matchStat.myPlayers[selected_player][1] + "");
                t_speed.setText(matchStat.myPlayers[selected_player][2] + "");
            }
        });

        players[1].addListener(new ActorGestureListener() {
            public boolean longPress(Actor actor, float x, float y) {
                if (selected_player != 1) {
                    boolean isSelectedonBench;
                    boolean isClickedonBench;
                    isSelectedonBench = matchStat.myLineup[3] == selected_player ||
                            matchStat.myLineup[4] == selected_player;
                    isClickedonBench = matchStat.myLineup[3] == 1 ||
                            matchStat.myLineup[4] == 1;
                    if (isSelectedonBench) {
                        if (isClickedonBench) {
                            return false;
                        } else {
                            substitution(1, selected_player);
                        }
                    } else {
                        if (isClickedonBench) {
                            substitution(selected_player, 1);
                        } else {
                            return false;
                        }
                    }
                }
                return true;
            }

            public void tap(InputEvent event, float x, float y, int count, int button) {
                Tween.to(selected, 1, .3f)
                        .target(players[1].getX() + Constants.HUD_SCREEN_WIDTH * .0097f,
                                players[1].getY() + Constants.HUD_SCREEN_HEIGHT * .022f)
                        .ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F);

                selected_player = 1;

                t_stamina.setText(matchStat.myPlayers[selected_player][0] + "");
                t_size.setText(matchStat.myPlayers[selected_player][1] + "");
                t_speed.setText(matchStat.myPlayers[selected_player][2] + "");
            }
        });

        players[2].addListener(new ActorGestureListener() {
            public boolean longPress(Actor actor, float x, float y) {
                if (selected_player != 2) {
                    boolean isSelectedonBench;
                    boolean isClickedonBench;
                    isSelectedonBench = matchStat.myLineup[3] == selected_player ||
                            matchStat.myLineup[4] == selected_player;
                    isClickedonBench = matchStat.myLineup[3] == 2 ||
                            matchStat.myLineup[4] == 2;
                    if (isSelectedonBench) {
                        if (isClickedonBench) {
                            return false;
                        } else {
                            substitution(2, selected_player);
                        }
                    } else {
                        if (isClickedonBench) {
                            substitution(selected_player, 2);
                        } else {
                            return false;
                        }
                    }
                }
                return true;
            }

            public void tap(InputEvent event, float x, float y, int count, int button) {
                Tween.to(selected, 1, .3f)
                        .target(players[2].getX() + Constants.HUD_SCREEN_WIDTH * .0097f,
                                players[2].getY() + Constants.HUD_SCREEN_HEIGHT * .022f)
                        .ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F);

                selected_player = 2;

                t_stamina.setText(matchStat.myPlayers[selected_player][0] + "");
                t_size.setText(matchStat.myPlayers[selected_player][1] + "");
                t_speed.setText(matchStat.myPlayers[selected_player][2] + "");
            }
        });

        players[3].addListener(new ActorGestureListener() {
            public boolean longPress(Actor actor, float x, float y) {
                if (selected_player != 3) {
                    boolean isSelectedonBench;
                    boolean isClickedonBench;
                    isSelectedonBench = matchStat.myLineup[3] == selected_player ||
                            matchStat.myLineup[4] == selected_player;
                    isClickedonBench = matchStat.myLineup[3] == 3 ||
                            matchStat.myLineup[4] == 3;
                    if (isSelectedonBench) {
                        if (isClickedonBench) {
                            return false;
                        } else {
                            substitution(3, selected_player);
                        }
                    } else {
                        if (isClickedonBench) {
                            substitution(selected_player, 3);
                        } else {
                            return false;
                        }
                    }
                }
                return true;
            }

            public void tap(InputEvent event, float x, float y, int count, int button) {
                Tween.to(selected, 1, .3f)
                        .target(players[3].getX() + Constants.HUD_SCREEN_WIDTH * .0097f,
                                players[3].getY() + Constants.HUD_SCREEN_HEIGHT * .022f)
                        .ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F);

                selected_player = 3;

                t_stamina.setText(matchStat.myPlayers[selected_player][0] + "");
                t_size.setText(matchStat.myPlayers[selected_player][1] + "");
                t_speed.setText(matchStat.myPlayers[selected_player][2] + "");
            }
        });

        players[4].addListener(new ActorGestureListener() {
            public boolean longPress(Actor actor, float x, float y) {
                if (selected_player != 4) {
                    boolean isSelectedonBench;
                    boolean isClickedonBench;
                    isSelectedonBench = matchStat.myLineup[3] == selected_player ||
                            matchStat.myLineup[4] == selected_player;
                    isClickedonBench = matchStat.myLineup[3] == 4 ||
                            matchStat.myLineup[4] == 4;
                    if (isSelectedonBench) {
                        if (isClickedonBench) {
                            return false;
                        } else {
                            substitution(4, selected_player);
                        }
                    } else {
                        if (isClickedonBench) {
                            substitution(selected_player, 4);
                        } else {
                            return false;
                        }
                    }
                }
                return true;
            }

            public void tap(InputEvent event, float x, float y, int count, int button) {
                Tween.to(selected, 1, .3f)
                        .target(players[4].getX() + Constants.HUD_SCREEN_WIDTH * .0097f,
                                players[4].getY() + Constants.HUD_SCREEN_HEIGHT * .022f)
                        .ease(TweenEquations.easeInBack)
                        .start(mTweenManager).delay(0.0F);

                selected_player = 4;

                t_stamina.setText(matchStat.myPlayers[selected_player][0] + "");
                t_size.setText(matchStat.myPlayers[selected_player][1] + "");
                t_speed.setText(matchStat.myPlayers[selected_player][2] + "");
            }
        });
    }

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
            System.out.println("before ------ " + response);
            try {
                if (response.getInt("playerId") != GamePrefs.getInstance().playerId) {
                    matchStat.isOppReady = true;
                    tmp = response.getJSONObject("direction");
                    matchStat.oppPlayerShooting = tmp.getInt("player");
                    array = tmp.getJSONArray("arrow");
                    matchStat.oppShootDirection = new Vector2(Float.parseFloat(array.getString(0)), Float.parseFloat(array.getString(1)));
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

    private Emitter.Listener onAfterListener = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            System.out.println("after------------" + args[0]);
            JSONObject response = (JSONObject) args[0];
            try {
                int playerId = response.getInt("playerId");
                if (playerId != GamePrefs.getInstance().playerId) {
                    JSONArray keeper = response.getJSONArray("keeperPosition");
                    JSONArray ball = response.getJSONArray("ballPosition");
                    JSONObject players = response.getJSONObject("playersPosition");
                    mModel.checkPositions(new Vector2((float) keeper.getDouble(0), (float) keeper.getDouble(1)),
                            new Vector2((float) ball.getDouble(0), (float) ball.getDouble(1)),
                            new Vector2((float) players.getJSONArray("1").getDouble(0), (float) players.getJSONArray("1").getDouble(1)),
                            new Vector2((float) players.getJSONArray("2").getDouble(0), (float) players.getJSONArray("2").getDouble(1)),
                            new Vector2((float) players.getJSONArray("3").getDouble(0), (float) players.getJSONArray("3").getDouble(1)),
                            new Vector2((float) players.getJSONArray("4").getDouble(0), (float) players.getJSONArray("4").getDouble(1)),
                            new Vector2((float) players.getJSONArray("5").getDouble(0), (float) players.getJSONArray("5").getDouble(1)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    };

    private Emitter.Listener onFinalListener = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            System.out.println("Final Result Recieved ........");
        }

    };

//    private Emitter.Listener onSubListener = new Emitter.Listener() {
//
//        @Override
//        public void call(Object... args) {
//            System.out.println("sub------------" + args[0]);
//            JSONObject response = (JSONObject) args[0];
//            try {
//                int playerId = response.getInt("playerId");
//                if (playerId != GamePrefs.getInstance().playerId) {
//                    changeOppPlayer(response.getInt("playerOut"), response.getInt("playerIn"));
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//    };

//    private void changeOppPlayer(int out, int in) {
//        int out_index, in_index;
//        if (matchStat.oppLineup[0] == out)
//            out_index = 0;
//        else if (matchStat.oppLineup[1] == out)
//            out_index = 1;
//        else
//            out_index = 2;
//        if (matchStat.oppLineup[3] == in)
//            in_index = 3;
//        else
//            in_index = 4;
//
//        matchStat.oppLineup[out_index] = in;
//        matchStat.oppLineup[in_index] = out;
//    }

    private void changeMyPlayer(int out, int in) {
        int out_index, in_index;
        if (matchStat.myLineup[0] == out)
            out_index = 0;
        else if (matchStat.myLineup[1] == out)
            out_index = 1;
        else
            out_index = 2;
        if (matchStat.myLineup[3] == in)
            in_index = 3;
        else
            in_index = 4;

        matchStat.myLineup[out_index] = in;
        matchStat.myLineup[in_index] = out;
    }

    private void substitution(int out, int in) {
        changeMyPlayer(out, in);

        Vector2 p_out = new Vector2(players[out].getX(), players[out].getY());
        Vector2 p_in = new Vector2(players[in].getX(), players[in].getY());

        Tween.to(players[out], 1, .3f)
                .target(p_in.x,
                        p_in.y)
                .ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(0.0F);

        Tween.to(players[in], 1, .3f)
                .target(p_out.x,
                        p_out.y)
                .ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(0.0F)
                .setCallback(new TweenCallback() {
                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
                        Tween.to(selected, 1, .3f)
                                .target(players[selected_player].getX() + Constants.HUD_SCREEN_WIDTH * .0097f,
                                        players[selected_player].getY() + Constants.HUD_SCREEN_HEIGHT * .022f)
                                .ease(TweenEquations.easeInBack)
                                .start(mTweenManager).delay(0.0F);
                    }
                });
    }

    void setPosition(Vector2[] position) {
        Tween.to(selected, 1, .3f)
                .target(position[selected_player].x + Constants.HUD_SCREEN_WIDTH * .0097f,
                        position[selected_player].y + Constants.HUD_SCREEN_HEIGHT * .022f)
                .ease(TweenEquations.easeInBack)
                .start(mTweenManager).delay(0.0F);

        for (int i = 0; i < 5; i++) {
            Tween.to(players[i], 1, .3f)
                    .target(position[i].x, position[i].y)
                    .ease(TweenEquations.easeInBack)
                    .start(mTweenManager).delay(0.0F);
        }
    }

    @Override
    public void show() {
        mMainCamera = new OrthographicCamera(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        mMainCamera.update();
        OrthographicCamera mHUDCamera = new OrthographicCamera(Constants.HUD_SCREEN_WIDTH, Constants.HUD_SCREEN_HEIGHT);
        mHUDCamera.update();

        mJson = new Json();

        mGameRenderer = new GameRenderer(mMainCamera, mHUDCamera, matchStat, mTweenManager);

        Controls mControls = new Controls(mModel, mMainCamera);
        mControls.setState(State.IDLE);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(mStage2);
        inputMultiplexer.addProcessor(mControls);
        Gdx.input.setInputProcessor(inputMultiplexer);

        Vector2[] position = util.getInGameSettingPosition(matchStat.myFormation, matchStat.myLineup);
        setPosition(position);
    }

    public void endofRound() {
        matchStat.roundNum += 1;
        matchStat.myShootDirection = null;
        progress.setPercentage(1f);
        Tween.to(progress, 1, 30f)
                .target(0).ease(TweenEquations.easeInCubic)
                .start(mTweenManager).delay(0F);
//                .setCallback(new TweenCallback() {
//                    public void onEvent(int type, BaseTween<?> paramAnonymousBaseTween) {
//                        System.out.println("end of tween-----------------");
//                    }
//                });
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                mModel.sendBefore();
                if (matchStat.isOppReady) {
                    mModel.startRendering();
                } else {
                    matchStat.GAME_STATE = Constants.GAME_WAITING;
                }
            }
        }, 15f, 0f, 0);
    }

    public void winGame(boolean isWinner) {
        if (isWinner) {
            matchStat.GAME_STATE = Constants.GAME_WINNER;
        } else {
            matchStat.GAME_STATE = Constants.GAME_LOSER;
        }
    }

    public void goalScored() {
        if (matchStat.lastTouch == 1) {
            matchStat.myGoals += 1;
            JSONObject data = new JSONObject();
            try {
                data.put("playerId", GamePrefs.getInstance().playerId);

                ServerTool.getInstance().socket.emit("goal", data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            matchStat.oppGoals += 1;
        }

        left_score.setText(matchStat.myGoals + "");
        right_score.setText(matchStat.oppGoals + "");
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
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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

                logic_lastFPS = logic_frameCount / logic_FPSupdateIntervall;
                logic_frameCount = 0;
                logic_lastRender = System.nanoTime();
            }
            //--------------------------------------------------------------
        }

        rendering(delta);
    }


    //====================================================================================
    public void updating(float delta) {
        mModel.update(delta);
    }

    //====================================================================================
    public void rendering(float delta) {

        //------------- to limit fps ------------------------
        if (RENDERER_SLEEP_MS > 0) {

            now2 = System.currentTimeMillis();
            diff = now2 - start;

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
        mMainCamera.viewportWidth = Constants.SCREEN_HEIGHT * aspectRatio;
        mMainCamera.update();

        mStage.getViewport().update(width, (int) (Constants.SCREEN_HEIGHT * aspectRatio), true);
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
    }
}