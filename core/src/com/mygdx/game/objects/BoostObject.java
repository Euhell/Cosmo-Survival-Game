package com.mygdx.game.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.utility.GameSettings;

import java.util.Random;

public abstract class BoostObject extends GameObject {
    public boolean wasHit;
    public BoostObject(int width, int height, String texturePath, short boostBit, World world) {
        super(texturePath,
                width / 2 + GameSettings.PADDING_HORIZONTAL + (new Random()).nextInt((GameSettings.SCREEN_WIDTH - 2 * GameSettings.PADDING_HORIZONTAL - width)),
                GameSettings.SCREEN_HEIGHT + height / 2,
                width,
                height,
                boostBit,
                world
        );
        body.setLinearVelocity(new Vector2(0, -GameSettings.BOOST_VELOCITY));
        wasHit = false;
    }

    public boolean hasToBeDestroyed() {
        return wasHit || getY() + height / 2 < 0;
    }

    public void hit() {
        wasHit = true;
    }
}
