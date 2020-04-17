package ir.eynakgroup.dribble2goal.render.textures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

import ir.eynakgroup.dribble2goal.Assets;
import ir.eynakgroup.dribble2goal.Constants;
import ir.eynakgroup.dribble2goal.GamePrefs;
import ir.eynakgroup.dribble2goal.MatchStats;
import ir.eynakgroup.dribble2goal.Util.Util;
import ir.eynakgroup.dribble2goal.template.Player;

/**
 * Created by kAvEh on 2/19/2016.
 */
public class PlayerTexture implements ITexture<Player> {

    private Texture playerStable1;
    private Texture playerStable2;
    private Texture playerStable3;
    private Texture playerStable4;
    private Texture playerStable5;
    private Texture keeperStable;
    private Sprite p1Sprite;
    private Sprite p2Sprite;
    private Sprite goalerSprite;

    public PlayerTexture(MatchStats stat, PolygonSpriteBatch polyBatch, Stage mStage) {

//        playerStable1 = Assets.getInstance().player_1;
//        playerStable2 = Assets.getInstance().player_2;
//        playerStable3 = Assets.getInstance().player_3;
//        playerStable4 = Assets.getInstance().player_4;
//        playerStable5 = Assets.getInstance().player_5;

        Util util = new Util();
        keeperStable = util.getKeeperPiece(stat.matchLevel);
        p1Sprite = new Sprite(util.getShirt(GamePrefs.getInstance().user.getShirt()));
        p2Sprite = new Sprite(util.getShirt(stat.oppShirt));
        goalerSprite = new Sprite(util.getKeeperShirt(stat.matchLevel));

//        staminaBar1 = new StaminaTexture(new TextureRegion(Assets.getInstance().stamina_ring), polyBatch);
//        staminaBar2 = new StaminaTexture(new TextureRegion(Assets.getInstance().avatar_2), polyBatch);
//        staminaBar3 = new StaminaTexture(new TextureRegion(Assets.getInstance().avatar_3), polyBatch);
//        staminaBar4 = new StaminaTexture(new TextureRegion(Assets.getInstance().avatar_4), polyBatch);
//        staminaBar5 = new StaminaTexture(new TextureRegion(Assets.getInstance().avatar_5), polyBatch);

//        staminaBar1.setSize(Constants.HUD_SCREEN_WIDTH * .08f * 1.8f, Constants.HUD_SCREEN_HEIGHT * .142f * 1.8f);
//        System.out.println(Constants.HUD_SCREEN_WIDTH * .08f * 1.2f+":"+ Constants.HUD_SCREEN_HEIGHT * .142f * 1.2f);
//        staminaBar1.setPosition(Constants.HUD_SCREEN_WIDTH * .1f, Constants.HUD_SCREEN_HEIGHT * .1f);

//        mStage.addActor(staminaBar1);
//        mStage.addActor(staminaBar2);
//        mStage.addActor(staminaBar3);
//        mStage.addActor(staminaBar4);
//        mStage.addActor(staminaBar5);
    }

    @Override
    public void draw(SpriteBatch batch, Player domainObj) {
    }

    @Override
    public void draw(SpriteBatch batch, Player domainObj, int type, int num) {
        float x = domainObj.getPosition().x;
        float y = domainObj.getPosition().y;
        float size = domainObj.getRadius();

        if (type == 2) {
            batch.draw(keeperStable, x - size * 1.58f, y - size * 1.58f, size * 3.16f, size * 3.16f);
        }

        switch (num) {
            case 1:
                batch.draw(playerStable1, x - size * 1.58f, y - size * 1.58f, size * 3.16f, size * 3.16f);
                break;
            case 2:
                batch.draw(playerStable2, x - size * 1.58f, y - size * 1.58f, size * 3.16f, size * 3.16f);
                break;
            case 3:
                batch.draw(playerStable3, x - size * 1.58f, y - size * 1.58f, size * 3.16f, size * 3.16f);
                break;
            case 4:
                batch.draw(playerStable4, x - size * 1.58f, y - size * 1.58f, size * 3.16f, size * 3.16f);
                break;
            case 5:
                batch.draw(playerStable5, x - size * 1.58f, y - size * 1.58f, size * 3.16f, size * 3.16f);
                break;
        }

        if (type == 0) {
            p1Sprite.setSize(size * 3.16f, size * 3.16f);
            p1Sprite.setPosition(x - size * 1.58f, y - size * 1.58f);
            p1Sprite.setOrigin(size * 1.58f, size * 1.667f);
            p1Sprite.setRotation(domainObj.getAngle());
            p1Sprite.draw(batch);
        } else if (type == 1) {
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
    public void drawStamina(SpriteBatch batch, int num, Player domainObj, Camera camera) {
        float x = domainObj.getPosition().x;
        float y = domainObj.getPosition().y;
        float size = domainObj.getRadius();

//        switch (num) {
//            case 1:
////                System.out.println(x + ":" + y + "==" + (x + Constants.HUD_SCREEN_WIDTH / 2f) * 64f + ":" + (y + Constants.HUD_SCREEN_HEIGHT / 2f) * 64f);
////                System.out.println(staminaBar1.getWidth() + ":" + staminaBar1.getHeight() + "===" + domainObj.getStamina());
////                staminaBar1.setSize(size * 64f, size * 64f);
//                staminaBar1.setPosition((x + Constants.HUD_SCREEN_WIDTH / 2f - size / 2f) * 64f, (y + Constants.HUD_SCREEN_HEIGHT / 2f - size / 2f) * 64f);
//                staminaBar1.setPercentage(domainObj.getStamina());
//                break;
//            case 2:
//                staminaBar2.setPosition(x * 1, y * 1);
//                staminaBar2.setPercentage(.5f);
//                staminaBar2.setColor(0, 0, 0, 0);
//                break;
//            case 3:
//                staminaBar3.setPosition(x * 30, y * 30);
//                staminaBar3.setPercentage(.5f);
//                staminaBar3.setColor(0, 0, 0, 0);
//                break;
//            case 4:
//                staminaBar4.setPosition(x * 4, y * 4);
//                staminaBar4.setPercentage(.5f);
//                staminaBar4.setColor(0, 0, 0, 0);
//                break;
//            case 5:
//                staminaBar5.setPosition(x * 1, y * 1);
//                staminaBar5.setPercentage(.5f);
//                staminaBar5.setColor(0, 0, 0, 0);
//                break;
//        }

//        stamina.setSize(size * 2, size * 3f);
//        stamina.setPosition(x - size, y - size * 1.2f);
//        stamina.setPercentage(domainObj.getStamina(), size, new Vector2(x, y));
//        staminaBar.draw(batch, camera, stage);
//        pie = new PieCounter(Assets.getInstance().pie, Assets.getInstance().pie_slot,
//                new Vector2(x - .015f, y), domainObj.getStamina(), size);
//        batch.draw(stamina, x - size * 1.2f, y - size * 1.2f, (int) (x - size * 1.2f),
//                (int) (y - size * 1.2f),(int) (size * 2.4f), (int) (size * 2.4f));

//        pie.render(batch);
    }

}
