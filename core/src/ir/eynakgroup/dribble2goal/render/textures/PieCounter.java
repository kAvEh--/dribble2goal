package ir.eynakgroup.dribble2goal.render.textures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import ir.eynakgroup.dribble2goal.tween.SpriteAccessor;

/**
 * Created by kAvEh on 2/28/2016.
 */
public class PieCounter {
    TextureRegion pieTexture;
    TextureRegion slotTexture;
    float pieAngle;
    int maxSlices;
    int stamina;

    Vector2 center;
    float width, height;

    Sprite centerIcon;

    Tween iconTween;

    public final static float DEFAULT_PIE_ANGLE = 15f;

    public PieCounter(Texture pie, Texture slot, Vector2 c, float stamina, float radius) {
        super();
        this.pieTexture = new TextureRegion(pie);
        this.slotTexture = new TextureRegion(slot);
        center = c;
        pieAngle = DEFAULT_PIE_ANGLE;

        height = radius * 1f;
        width = height * .26f;
        maxSlices = (int) (360.0f / DEFAULT_PIE_ANGLE);
        this.stamina = (int) (stamina * maxSlices);
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < stamina; i = i + 1) {
            batch.draw(pieTexture,
                    center.x - width / 2.3f, center.y,
                    width / 2f, 0.0f,
                    width, height,
                    1.2f, 1.2f,
                    -(i * (pieAngle) + DEFAULT_PIE_ANGLE / 2));
        }
        for (int i = stamina; i < maxSlices; i++) {
            batch.draw(slotTexture,
                    center.x - width / 2.3f, center.y,
                    width / 2f, 0.0f,
                    width, height,
                    1.15f, 1.15f,
                    -(i * (pieAngle) + DEFAULT_PIE_ANGLE / 2));
        }
    }

    public void trigger() {
        if (iconTween != null)
            iconTween.free();
        centerIcon.setScale(1.0f);
        iconTween = Tween.to(centerIcon, SpriteAccessor.SET_SCALE, 0.1f)
                .target(1.8f, 1.8f)
                .repeatYoyo(3, 0.0f)
                .delay(0.2f)
                .ease(TweenEquations.easeInCubic);

        iconTween.start();


    }

}