package com.mygdx.game.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.utility.GameSettings;

public class BulletObject extends GameObject {

    public boolean wasHit;
    public BulletObject(int x, int y, int width, int height, String texturePath, World world) {
        super(texturePath, x, y, width, height, GameSettings.BULLET_BIT, world);
        body.setLinearVelocity(new Vector2(0, GameSettings.BULLET_VELOCITY));
        body.setBullet(true);
        wasHit = false;
    }

    @Override
    public void useHealBoost() {
        ShipObject.healBoostNeedToActivate = true;
    }
    @Override
    public void useFreezeBoost() {
        ShipObject.freezeBoostNeedToActivate = true;
    }
    @Override
    public void useSpeedBoost() {
        ShipObject.speedBoostNeedToActivate = true;
    }
    public boolean hasToBeDestroyed() {
        return wasHit || getY() - height / 2 > GameSettings.SCREEN_HEIGHT;
    }
    public void hit() {
        wasHit = true;
    }
}
