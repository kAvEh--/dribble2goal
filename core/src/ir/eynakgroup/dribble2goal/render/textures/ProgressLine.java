package ir.eynakgroup.dribble2goal.render.textures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by kAvEh on 3/24/2016.
 */
public class ProgressLine extends Image {

    Texture texture;
    float percent = 0;
    SpriteBatch sBatch;
    TextureRegionDrawable drawable;
    TextureRegion region;
    Vector2 size;

    public ProgressLine(Texture t, TextureRegion reg, Vector2 s) {
        super(reg);

        this.texture = t;
        this.size = s;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercentage(float p) {
        percent = p;
        region = new TextureRegion(texture, 0, 0, (int) (texture.getWidth() * percent), (int) (texture.getHeight()));
        this.setDrawable(new TextureRegionDrawable(region));
        this.setSize(size.x * percent, size.y);
    }

}
