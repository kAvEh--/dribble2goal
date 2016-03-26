package ir.eynajgroup.dribble2goal.model;

/**
 * Created by kAvEh on 2/19/2016.
 */
public interface IModelListener {
    void onModelUpdate(String modelState);
    void goalEvent();
    void winEvent();
}
