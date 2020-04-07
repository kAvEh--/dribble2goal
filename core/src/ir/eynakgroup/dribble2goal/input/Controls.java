package ir.eynakgroup.dribble2goal.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import ir.eynakgroup.dribble2goal.model.IModel;
import ir.eynakgroup.dribble2goal.template.Team;

/**
 * Created by kAvEh on 2/19/2016.
 */
public class Controls extends InputAdapter {

    private final IModel mModel;
    private State mState;
    private OrthographicCamera mCamera;

    public Controls(IModel model, OrthographicCamera camera) {
        mModel = model;
        mCamera = camera;
    }

    public void process() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            mModel.sendMoveUp(Team.PLAYER1);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            mModel.sendMoveDown(Team.PLAYER1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            mModel.sendMoveUp(Team.PLAYER2);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            mModel.sendMoveDown(Team.PLAYER2);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.X)) {
            mModel.powerUp(Team.PLAYER2, new Vector2());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            mModel.powerUp(Team.PLAYER1, new Vector2());
        }
    }

    @Override
    public boolean touchDown(final int screenX, final int screenY, final int pointer, int button) {
        final Vector3 vec = new Vector3(screenX, screenY, 0);
        mCamera.unproject(vec);
        mModel.sendTouchDown(vec.x, vec.y, pointer);
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        final Vector3 vec = new Vector3(screenX, screenY, 0);
        mCamera.unproject(vec);
        mModel.sendTouchDragged(vec.x, vec.y, pointer);
        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        final Vector3 vec = new Vector3(screenX, screenY, 0);
        mCamera.unproject(vec);
        mModel.sendTouchUp(vec.x, vec.y, pointer);
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            if (mState == State.IDLE) {
                mModel.sendStartRound();
                mState = State.GAME;
                return true;
            } else if (mState == State.END_GAME) {
                mModel.sendResetRound();
                mModel.sendStartRound();
                mState = State.GAME;
                return true;
            }
        }
        return false;
    }

    public void setState(State state) {
        mState = state;
    }
}
