package ir.eynakgroup.dribble2goal.model;

/**
 * Created by kAvEh on 2/19/2016.
 */
public interface ITouchControls {
    void sendTouchDown(float x, float y, int pointer);
    void sendTouchDragged(float x, float y, int pointer);
    void sendTouchUp(float x, float y, int pointer);
    void destroyTJoint();
}
