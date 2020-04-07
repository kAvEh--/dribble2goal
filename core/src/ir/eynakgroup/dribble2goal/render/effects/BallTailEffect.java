package ir.eynakgroup.dribble2goal.render.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import ir.eynakgroup.dribble2goal.template.Ball;

/**
 * Created by kAvEh on 2/19/2016.
 */
public class BallTailEffect implements IEffect<Ball> {

    private final ParticleEffect mEffect;
    private OrthographicCamera mCamera;
    private Vector3 mTempVec;
    private boolean mPlayed;

    public BallTailEffect(OrthographicCamera camera) {
        mCamera = camera;
        mEffect = new ParticleEffect();
        mEffect.load(Gdx.files.internal("effects/ball.eff"), Gdx.files.internal("effects/"));
        mEffect.start();
        mTempVec = new Vector3();
    }

    @Override
    public void draw(SpriteBatch batch, Ball domainObj) {
        if (mPlayed) {
            batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.begin();
            float x = domainObj.getPosition().x;
            float y = domainObj.getPosition().y;
            mTempVec.set(x, y, 0);
            mCamera.project(mTempVec);
            mEffect.setPosition(mTempVec.x, mTempVec.y);
            mEffect.draw(batch, Gdx.graphics.getDeltaTime());
            batch.end();
        }
    }

    @Override
    public void play() {
        mPlayed = true;
    }

    @Override
    public void stop() {
        mPlayed = false;
    }

    @Override
    public void dispose() {
        mEffect.dispose();
    }
}
