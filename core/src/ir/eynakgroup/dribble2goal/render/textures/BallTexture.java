package ir.eynakgroup.dribble2goal.render.textures;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import ir.eynakgroup.dribble2goal.Assets;
import ir.eynakgroup.dribble2goal.template.Ball;

/**
 * Created by kAvEh on 2/19/2016.
 */
public class BallTexture implements ITexture<Ball> {
    private Texture bgTexture;
    private Texture ballTexture;
    private Texture ballShadow;
    private Sprite ballSprite;
    private float angle = 0f;

    public BallTexture() {
        bgTexture = Assets.getInstance().ball_light;
        ballTexture = Assets.getInstance().ball;
        ballShadow = Assets.getInstance().ball_shadow;

        ballSprite = new Sprite(ballTexture);
    }

    @Override
    public void draw(SpriteBatch batch, Ball domainObj) {
        float x = domainObj.getPosition().x;
        float y = domainObj.getPosition().y;
        float size = domainObj.getRadius();
        ballSprite.setSize(size * 2, size * 2);
        angle = (angle + domainObj.getAngularVelocity()) % 360;
        ballSprite.setPosition(x - size, y - size);
        ballSprite.setOrigin(size, size);
        ballSprite.setRotation(angle);

        batch.draw(ballShadow, x - (size * 1.25f), y - (size * 1.25f), size * 2.5f, size * 2.5f);
        ballSprite.draw(batch);
        batch.draw(bgTexture, x - size, y - size, size * 2f, size * 2f);
    }

    @Override
    public void draw(SpriteBatch batch, Ball domainObj, int type, int num) {
    }

    @Override
    public void drawStamina(SpriteBatch batch, int num, Ball domainObj, Camera c) {
    }
}