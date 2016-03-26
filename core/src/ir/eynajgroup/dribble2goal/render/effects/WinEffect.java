package ir.eynajgroup.dribble2goal.render.effects;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ir.eynajgroup.dribble2goal.Assets;
import ir.eynajgroup.dribble2goal.Constants;
import ir.eynajgroup.dribble2goal.template.Team;

/**
 * Created by kAvEh on 2/19/2016.
 */
public class WinEffect implements IEffect<Team> {
    private static final float TOP_MARGIN = 100;
    private final BitmapFont mBitmapFont;
    private final OrthographicCamera mHudCamera;

    private boolean mPlaying;
    GlyphLayout layout = new GlyphLayout();

    public WinEffect(OrthographicCamera hudCamera) {
        mHudCamera = hudCamera;
        mBitmapFont = Assets.getInstance().fontDroidSans17;
    }

    @Override
    public void draw(SpriteBatch batch, Team team) {
        if (mPlaying) {
            batch.setProjectionMatrix(mHudCamera.combined);
            batch.begin();

            if (team == Team.PLAYER1) {
                String str = "Left player WIN!";
                layout.setText(mBitmapFont, str);
                float length = layout.width;
                mBitmapFont.draw(batch, str, 0 - length / 2, Constants.HUD_SCREEN_HEIGHT / 2 - TOP_MARGIN);
            } else {
                String str = "Right player WIN!";
                layout.setText(mBitmapFont, str);
                float length = layout.width;
                mBitmapFont.draw(batch, str, 0 - length / 2, Constants.HUD_SCREEN_HEIGHT / 2 - TOP_MARGIN);
            }
            batch.end();
        }
    }

    @Override
    public void play() {
        mPlaying = true;
    }

    @Override
    public void stop() {
        mPlaying = false;
    }

    @Override
    public void dispose() {

    }
}
