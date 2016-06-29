package ir.eynajgroup.dribble2goal;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Eynak_PC2 on 6/20/2016.
 */
public class ParticleEffectActor extends Actor {
    ParticleEffect effect;
    Vector2 acc = new Vector2();

    public ParticleEffectActor(ParticleEffect effect) {
        this.effect = effect;
    }

    public void draw(Batch batch, float parentAlpha) {
        effect.draw(batch);
    }

    public void act(float delta) {
        super.act(delta);
        if (effect.isComplete())
            effect.start();
        acc.set(getWidth()/2, getHeight()/2);
        localToStageCoordinates(acc);
        effect.setPosition(acc.x, acc.y);
        effect.update(delta);
    }

    public void start() {
        effect.start();
    }

    public void allowCompletion() {
        effect.allowCompletion();
    }

    public ParticleEffect getEffect() {
        return effect;
    }
}