package ir.eynakgroup.dribble2goal.model;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

import ir.eynakgroup.dribble2goal.screens.GameScreen;
import ir.eynakgroup.dribble2goal.screens.HelpGameScreen;
import ir.eynakgroup.dribble2goal.template.Team;

/**
 * Created by kAvEh on 2/19/2016.
 */
public interface IModel extends ITouchControls {
    void addModelListener(IModelListener listener);
    void update(float delta);
    void dispose();
    void sendMoveUp(Team team);
    void sendMoveDown(Team team);
    void startRendering();
    void sendAfter();
    void sendBefore();
    void powerUp(Team team, Vector2 v);
    void sendStartRound();
    void sendResetRound();
    void debugRender(OrthographicCamera camera);
    void setScreen(GameScreen gs);
    void setScreen(HelpGameScreen gs);
    void checkPositions(Vector2 keeper, Vector2 ball, Vector2 p1, Vector2 p2,Vector2 p3, Vector2 p4, Vector2 p5);
    void setBallPosition(float x, float y);
    void setKeeperPosition(float x, float y);
    void setMyPlayerPosition(int num, float x, float y);
    void setOppPlayerPosition(int num, float x, float y);
    float[] getMyStamina();
}
