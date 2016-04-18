package ir.eynajgroup.dribble2goal.render.textures;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ShortArray;

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

    private Vector2 polySize;

    public ProgressCircle(TextureRegion region, PolygonSpriteBatch polyBatch, Vector2 size) {
        super(region);

        this.texture = region;
        this.polyBatch = polyBatch;

        center = new Vector2(this.getWidth() / 2, this.getHeight() / 2);
        centerTop = new Vector2(this.getWidth() / 2, this.getHeight());
        leftTop = new Vector2(0, this.getHeight());
        leftBottom = new Vector2(0, 0);
        rightBottom = new Vector2(this.getWidth(), 0);
        rightTop = new Vector2(this.getWidth(), this.getHeight());
        progressPoint = new Vector2(this.getWidth() / 2, this.getHeight() / 2);

        polySize = size;
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

        if (percent < 2f)
            percent = 2f;

        double angle = Math.toRadians(percent * 360d / 100d);

        float len = this.getWidth() > this.getHeight() ? this.getWidth() : this.getHeight();
        float dy = (float) (Math.cos(angle) * len);
        float dx = (float) (Math.sin(angle) * len);

        Vector2 line = new Vector2(center.x + dx, center.y + dy);

        Vector2 v = IntersectPoint(line);

        if (intersectAt == IntersectAt.TOP) {
            if (v.x < this.getWidth() / 2) {
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

//        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        polyBatch.begin();
        poly.draw(polyBatch);
        polyBatch.end();

//        mStage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }


//-----------------------------------------------------------------


    float convertToDegrees(float angleInRadians) {
        float angleInDegrees = angleInRadians * 57.2957795f;
        return angleInDegrees;
    }

    float convertToRadians(float angleInDegrees) {
        float angleInRadians = angleInDegrees * 0.0174532925f;
        return angleInRadians;
    }


}
