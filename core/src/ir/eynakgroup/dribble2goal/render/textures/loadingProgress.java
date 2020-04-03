package ir.eynakgroup.dribble2goal.render.textures;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ShortArray;

/**
 * Created by Eynak_PC2 on 8/6/2016.
 */
public class LoadingProgress extends Image {

    TextureRegion texture;

    PolygonSpriteBatch polyBatch;

    Vector2 leftTop;
    Vector2 leftBottom;
    Vector2 rightBottom;
    Vector2 rightTop;

    float[] fv;

    float percent = 0;

    Stage stage;

    public LoadingProgress(TextureRegion region, PolygonSpriteBatch polyBatch, Stage st) {
        super(region);

        this.texture = region;
        this.polyBatch = polyBatch;
        this.stage = st;

        leftTop = new Vector2(0, this.getHeight());
        leftBottom = new Vector2(0, 0);
        rightBottom = new Vector2(this.getWidth(), 0);
        rightTop = new Vector2(this.getWidth(), this.getHeight());
    }

    public float getPercent() {
        return percent;
    }

    public void setPercentage(float p) {
        percent = p;

        float width = rightTop.x - leftTop.x;
        fv = new float[]{
                leftTop.x + (width * .5f * (1 - percent)),
                leftTop.y,
                leftTop.x + (width * .5f * (1 + percent)),
                leftTop.y,
                leftTop.x + (width * .5f * (1 + percent)),
                leftBottom.y,
                leftTop.x + (width * .5f * (1 - percent)),
                leftBottom.y
        };
    }

    public void draw(Batch batch, float parentAlpha) {
        if (fv == null) return;

        batch.end();
        drawMe();
        batch.begin();
    }

    public void drawMe() {
        EarClippingTriangulator e = new EarClippingTriangulator();
        ShortArray sv = e.computeTriangles(fv);

        PolygonRegion polyReg = new PolygonRegion(texture, fv, sv.toArray());

        PolygonSprite poly = new PolygonSprite(polyReg);

        poly.setOrigin(this.getOriginX(), this.getOriginY());
        poly.setSize(this.getWidth(), this.getHeight());
        poly.setPosition(this.getX(), this.getY());
        poly.setRotation(this.getRotation());
        poly.setColor(this.getColor());

        polyBatch.setProjectionMatrix(stage.getCamera().combined);

        polyBatch.begin();
        poly.draw(polyBatch);
        polyBatch.end();
    }
}