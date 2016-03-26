package ir.eynajgroup.dribble2goal.model;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

import ir.eynajgroup.dribble2goal.screens.GameScreen;
import ir.eynajgroup.dribble2goal.template.Team;

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
    void powerUp(Team team, Vector2 v);
    void sendStartRound();
    void sendResetRound();
    void debugRender(OrthographicCamera camera);
    void setScreen(GameScreen gs);
}
