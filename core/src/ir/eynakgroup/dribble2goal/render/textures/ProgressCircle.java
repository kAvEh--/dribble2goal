package ir.eynakgroup.dribble2goal.render.textures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ShortArray;

import ir.eynakgroup.dribble2goal.Constants;

public class ProgressCircle extends Image {
    public enum IntersectAt {
        NONE, TOP, BOTTOM, LEFT, RIGHT;
    }

    TextureRegion texture;

    PolygonSpriteBatch polyBatch;

    Vector2 center;
    Vector2 centerTop;
    Vector2 leftTop;
    Vector2 leftBottom;
    Vector2 rightBottom;
    Vector2 rightTop;
    Vector2 progressPoint;

    float[] fv;

    float percent = 0;

    IntersectAt intersectAt;

    Stage stage;

    public ProgressCircle(TextureRegion region, PolygonSpriteBatch polyBatch, Vector2 size, Stage st) {
        super(region);

        this.texture = region;
        this.polyBatch = polyBatch;
        this.stage = st;

        center = new Vector2(this.getWidth() / 2, this.getHeight() / 2);
        centerTop = new Vector2(this.getWidth() / 2, this.getHeight());
        leftTop = new Vector2(0, this.getHeight());
        leftBottom = new Vector2(0, 0);
        rightBottom = new Vector2(this.getWidth(), 0);
        rightTop = new Vector2(this.getWidth(), this.getHeight());
        progressPoint = new Vector2(this.getWidth() / 2, this.getHeight() / 2);
    }

    private Vector2 IntersectPoint(Vector2 line) {
        Vector2 v = new Vector2();
        boolean isIntersect;

        //check top
        isIntersect = Intersector.intersectSegments(leftTop, rightTop, center, line, v);

        //check bottom
        if (isIntersect) {
            intersectAt = IntersectAt.TOP;
            return v;
        } else
            isIntersect = Intersector.intersectSegments(leftBottom, rightBottom, center, line, v);

        //check left
        if (isIntersect) {
            intersectAt = IntersectAt.BOTTOM;
            return v;
        } else isIntersect = Intersector.intersectSegments(leftTop, leftBottom, center, line, v);

        //check bottom
        if (isIntersect) {
            intersectAt = IntersectAt.LEFT;
            return v;
        } else isIntersect = Intersector.intersectSegments(rightTop, rightBottom, center, line, v);

        if (isIntersect) {
            intersectAt = IntersectAt.RIGHT;
            return v;
        } else {
            intersectAt = IntersectAt.NONE;
            return null;
        }
    }

    public float getPercent() {
        return percent;
    }

    public void setPercentage(float p) {
        percent = p;

        double angle = Math.toRadians(percent * 360f / 100f);

        float len = this.getWidth() > this.getHeight() ? this.getWidth() : this.getHeight();
        float dy = (float) (Math.cos(angle) * len);
        float dx = (float) (Math.sin(angle) * len);

        Vector2 line = new Vector2(center.x + dx, center.y + dy);

        Vector2 v = IntersectPoint(line);

        if (intersectAt == IntersectAt.TOP) {
            if (v.x <= center.x) {
                fv = new float[]{
                        center.x,
                        center.y,
                        centerTop.x,
                        centerTop.y,
                        rightTop.x,
                        rightTop.y,
                        rightBottom.x,
                        rightBottom.y,
                        leftBottom.x,
                        leftBottom.y,
                        leftTop.x,
                        leftTop.y,
                        v.x,
                        v.y
                };
            } else {
                fv = new float[]{
                        center.x,
                        center.y,
                        centerTop.x,
                        centerTop.y,
                        v.x,
                        v.y
                };

            }
        } else if (intersectAt == IntersectAt.BOTTOM) {
            fv = new float[]{
                    center.x,
                    center.y,
                    centerTop.x,
                    centerTop.y,
                    rightTop.x,
                    rightTop.y,
                    rightBottom.x,
                    rightBottom.y,
                    v.x,
                    v.y
            };

        } else if (intersectAt == IntersectAt.LEFT) {
            fv = new float[]{
                    center.x,
                    center.y,
                    centerTop.x,
                    centerTop.y,
                    rightTop.x,
                    rightTop.y,
                    rightBottom.x,
                    rightBottom.y,
                    leftBottom.x,
                    leftBottom.y,
                    v.x,
                    v.y
            };

        } else if (intersectAt == IntersectAt.RIGHT) {
            fv = new float[]{
                    center.x,
                    center.y,
                    centerTop.x,
                    centerTop.y,
                    rightTop.x,
                    rightTop.y,
                    v.x,
                    v.y
            };

        } else // if (intersectAt == IntersectAt.NONE)
        {
            fv = null;
        }


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
