package com.mygdx.game.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.utility.GameSettings;

import java.util.Random;

public class TrashObject extends GameObject {
    protected int livesLeft;
    public static long freezeBoostActivationTime;
    public static boolean freezeBoostIsActive;
    public long spawnX = width / 2 + GameSettings.PADDING_HORIZONTAL + (new Random()).nextInt((GameSettings.SCREEN_WIDTH - 2 * GameSettings.PADDING_HORIZONTAL - width));
    public long spawnY;

    public TrashObject(int width, int height, String texturePath, short boostBit, World world) {
        super(texturePath,
                width / 2 + GameSettings.PADDING_HORIZONTAL + (new Random()).nextInt((GameSettings.SCREEN_WIDTH - 2 * GameSettings.PADDING_HORIZONTAL - width)),
                GameSettings.SCREEN_HEIGHT + height / 2,
                width, height,
                boostBit,
                world
        );
        body.setLinearVelocity(new Vector2(0, -GameSettings.TRASH_VELOCITY));
        livesLeft = 1;
        freezeBoostActivationTime = 0;
        freezeBoostIsActive = false;
        spawnX = width / 2 + GameSettings.PADDING_HORIZONTAL + (new Random()).nextInt((GameSettings.SCREEN_WIDTH - 2 * GameSettings.PADDING_HORIZONTAL - width));
        spawnY = GameSettings.SCREEN_HEIGHT + height / 2;
    }
    public TrashObject(int width, int height, String texturePath, World world) {
        super(texturePath,
                width / 2 + GameSettings.PADDING_HORIZONTAL + (new Random()).nextInt((GameSettings.SCREEN_WIDTH - 2 * GameSettings.PADDING_HORIZONTAL - width)),
                GameSettings.SCREEN_HEIGHT + height / 2,
                width, height,
                GameSettings.TRASH_BIT,
                world
        );
        body.setLinearVelocity(new Vector2(0, -GameSettings.TRASH_VELOCITY));
        livesLeft = 1;
    }

    public void useFreezeBoostTrash() {
        body.setLinearVelocity(new Vector2(0, 0));
        freezeBoostActivationTime = TimeUtils.millis();
        freezeBoostIsActive = true;
    }
    public void deactivateFreezeBoost() {
        body.setLinearVelocity(new Vector2(0, -GameSettings.TRASH_VELOCITY));
        freezeBoostActivationTime = 0;
        freezeBoostIsActive = false;
    }
    public boolean isInFrame() {
        return getY() + height / 2 > 0;
    }

    @Override
    public void hit() {
        livesLeft -= 1;
    }
    public boolean isAlive() {
        return livesLeft > 0;
    }
}
