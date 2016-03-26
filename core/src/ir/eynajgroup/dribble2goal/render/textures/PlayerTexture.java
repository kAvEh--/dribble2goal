package ir.eynajgroup.dribble2goal.render.textures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import ir.eynajgroup.dribble2goal.Assets;
import ir.eynajgroup.dribble2goal.MatchStats;
import ir.eynajgroup.dribble2goal.Util.Util;
import ir.eynajgroup.dribble2goal.template.Player;

/**
 * Created by kAvEh on 2/19/2016.
 */
public class PlayerTexture implements ITexture<Player> {

    private Texture playerStable;
    private Texture keeperStable;
    private Texture stamina;
    private Texture mTexture;
    private Texture mTexture2;

    private Sprite p1Sprite;
    private Sprite p2Sprite;
    private Sprite goalerSprite;

    ShapeRenderer shapeRenderer;
    PieCounter pie;

    public PlayerTexture(MatchStats stat) {

        playerStable = Assets.getInstance().player;
        mTexture = Assets.getInstance().shirt1;
        mTexture2 = Assets.getInstance().shirt2;
        stamina = Assets.getInstance().stamina;

        Util util = new Util();
        keeperStable = util.getKeeperPiece(stat.matchLevel);
        p1Sprite = new Sprite(util.getShirt(stat.p1Shirt));
        p2Sprite = new Sprite(util.getShirt(stat.p2Shirt));
        goalerSprite = new Sprite(util.getKeeperShirt(stat.matchLevel));

        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void draw(SpriteBatch batch, Player domainObj) {
    }

    @Override
    public void draw(SpriteBatch batch, Player domainObj, int type) {
        float x = domainObj.getPosition().x;
        float y = domainObj.getPosition().y;
        float size = domainObj.getRadius();

        if (type == 2) {
            batch.draw(keeperStable, x - size * 1.58f, y - size * 1.58f, size * 3.16f, size * 3.16f);
        } else {
            batch.draw(playerStable, x - size * 1.58f, y - size * 1.58f, size * 3.16f, size * 3.16f);
        }

        if (type == 0) {
            p1Sprite.setSize(size * 3.16f, size * 3.16f);
            p1Sprite.setPosition(x - size * 1.58f, y - size * 1.58f);
            p1Sprite.setOrigin(size * 1.58f, size * 1.667f);
            p1Sprite.setRotation(domainObj.getAngle());
            p1Sprite.draw(batch);
        } else if (type == 1){
            p2Sprite.setSize(size * 3.16f, size * 3.16f);
            p2Sprite.setPosition(x - size * 1.58f, y - size * 1.58f);
            p2Sprite.setOrigin(size * 1.58f, size * 1.667f);
            p2Sprite.setRotation(domainObj.getAngle());
            p2Sprite.draw(batch);
        } else {
            goalerSprite.setSize(size * 3.16f, size * 3.16f);
            goalerSprite.setPosition(x - size * 1.58f, y - size * 1.58f);
            goalerSprite.setOrigin(size * 1.58f, size * 1.667f);
            goalerSprite.setRotation(domainObj.getAngle());
            goalerSprite.draw(batch);
        }
    }

    @Override
    public void drawStamina(SpriteBatch batch, Player domainObj, float delta) {
        float x = domainObj.getPosition().x;
        float y = domainObj.getPosition().y;
        float size = domainObj.getRadius();

        pie = new PieCounter(Assets.getInstance().pie, Assets.getInstance().pie_slot,
                new Vector2(x - .015f, y), domainObj.getStamina(), size);
//        batch.draw(stamina, x - size * 1.2f, y - size * 1.2f, (int) (x - size * 1.2f),
//                (int) (y - size * 1.2f),(int) (size * 2.4f), (int) (size * 2.4f));

        pie.render(batch);
    }

}
