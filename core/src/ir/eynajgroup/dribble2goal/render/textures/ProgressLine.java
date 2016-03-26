package ir.eynajgroup.dribble2goal.render.textures;

import com.badlogic.gdx.graphics.Texture;
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
    boolean colorFlag;

    public ProgressLine(Texture t, SpriteBatch batch, TextureRegion reg, Vector2 s, boolean flag) {
        super(reg);

        this.texture = t;
        this.sBatch = batch;
        this.size = s;
        this.colorFlag = flag;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercentage(float p) {
        percent = p;
//        region = new TextureRegion(texture, 0, 0, texture.getWidth() * percent / 100f, texture.getHeight());
//        System.out.println(texture.getWidth() * percent / 100f + "=====" + texture.getHeight());
//        drawable = new TextureRegionDrawable(region);
//        this.setDrawable(drawable);

//        Texture jj =  Assets.getInstance().setting_item_bg;
        region = new TextureRegion(texture, 0, 0, (int) (texture.getWidth() * percent), (int) (texture.getHeight()));
//        Image tt = new Image(region);
        this.setDrawable(new TextureRegionDrawable(region));
        this.setSize(size.x * percent, size.y);
        if (colorFlag) {
            this.setColor(.4f * (1 - percent), .4f * percent, 0f, 1f);
        }
    }

//    public void draw(Batch batch, float parentAlpha) {
//        batch.end();
//        batch.begin();
//
//    }
}
