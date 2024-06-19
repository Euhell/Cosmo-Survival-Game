package com.mygdx.game.objects;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.utility.GameSettings;

public class SuperTrashObject extends TrashObject {

    public SuperTrashObject(int width, int height, String texturePath, World world) {
        super(width, height, texturePath, GameSettings.SUPER_TRASH_BIT, world);
        body.setLinearVelocity(new Vector2(5, -GameSettings.SUPER_TRASH_VELOCITY_INITIAL));
        livesLeft = 3;
    }

    public boolean isInFrame() {
        return getY() - height < GameSettings.SCREEN_HEIGHT;
    }

    public boolean isUp() {
        return getY() > 0;
    }

    @Override
    public void hit() {
        livesLeft -= 1;
        changeSuperTrashDirection();
    }

    @Override
    public void deactivateFreezeBoost() {
        body.setLinearVelocity(new Vector2(5, -GameSettings.SUPER_TRASH_VELOCITY_INITIAL));
        freezeBoostActivationTime = 0;
        freezeBoostIsActive = false;
    }
    @Override
    public void kill() {
        livesLeft = 0;
    }

    public void changeSuperTrashDirection() {
        body.setLinearVelocity(new Vector2(-5, GameSettings.SUPER_TRASH_VELOCITY_AFTER));
    }
    public boolean gotHit() {
        return livesLeft != 3;
    }
}
