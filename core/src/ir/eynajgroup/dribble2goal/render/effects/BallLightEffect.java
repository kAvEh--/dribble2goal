package ir.eynajgroup.dribble2goal.render.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;

import ir.eynajgroup.dribble2goal.Assets;
import ir.eynajgroup.dribble2goal.template.Ball;


/**
 * Created by kAvEh on 2/19/2016.
 */
public class BallLightEffect implements IEffect<Ball> {

    public static final float DEFAULT_LIGHT_Z = 0.02f;
    public static final float AMBIENT_INTENSITY = 0.3f;
    public static final float LIGHT_INTENSITY = 14f;
    public static final Vector3 LIGHT_COLOR = new Vector3(1.0f, 0.8f, 0.6f);
    public static final Vector3 AMBIENT_COLOR = new Vector3(1.0f, 1.0f, 1.0f);
    public static final Vector3 FALLOFF = new Vector3(1f, 1f, 1f);

    private OrthographicCamera mGameCamera;
    private Texture mBgTexture;
//    private Texture mBgNormalMapTexture;
    private ShaderProgram mLightShader;
    private SpriteBatch mBatch;
    private boolean mPlayed;
    private Vector3 mTempVec;

    public BallLightEffect(OrthographicCamera gameCamera) {
        mGameCamera = gameCamera;

        mBgTexture = Assets.getInstance().main_bg;
//        mBgNormalMapTexture = Assets.getInstance().bgNormalMap1;

        mLightShader = ShaderHelper.loadShader("normal_mapping");

        mLightShader.begin();
        mLightShader.setUniformi("u_normals", 1);
        mLightShader.setUniformf("lightColor", LIGHT_COLOR.x, LIGHT_COLOR.y, LIGHT_COLOR.z, LIGHT_INTENSITY);
        mLightShader.setUniformf("ambientColor", AMBIENT_COLOR.x, AMBIENT_COLOR.y, AMBIENT_COLOR.z, AMBIENT_INTENSITY);
        mLightShader.setUniformf("falloff", FALLOFF);
        mLightShader.end();

        mBatch = new SpriteBatch();
        mBatch.setShader(mLightShader);

        mLightShader.begin();
        mLightShader.setUniformf("resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mLightShader.end();

        mTempVec = new Vector3();
    }

    @Override
    public void draw(SpriteBatch batch, Ball domainObj) {
        if (mPlayed) {
            final int width = Gdx.graphics.getWidth();
            final int height = Gdx.graphics.getHeight();
            mBatch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
            mBatch.begin();

            mTempVec.set(domainObj.getPosition().x, domainObj.getPosition().y, DEFAULT_LIGHT_Z);
            mGameCamera.project(mTempVec);
            mTempVec.x = mTempVec.x / width;
            mTempVec.y = mTempVec.y / height;
            mTempVec.z = DEFAULT_LIGHT_Z;
            mLightShader.setUniformf("lightPos", mTempVec);

//            mBgNormalMapTexture.bind(1);

            //bind diffuse color to texture unit 0
            //important that we specify 0 otherwise we'll still be bound to glActiveTexture(GL_TEXTURE1)
            mBgTexture.bind(0);

            //draw the texture unit 0 with our shader effect applied
            mBatch.draw(mBgTexture, 0, 0, width, height);

            mBatch.end();
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
        mBatch.dispose();
        mLightShader.dispose();
    }
}
