package com.mygdx.game.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.screens.PickLevelScreen;
import com.mygdx.game.screens.SetGameModeScreen;
import com.mygdx.game.utility.GameSettings;

public class BossObject extends TrashObject {
    long lastChangeTime;
    boolean flag;
    public BossObject(int width, int height, String texturePath, World world) {
        super(width, height, texturePath, GameSettings.BOSS_BIT, world);
        body.setLinearVelocity(new Vector2(0, -GameSettings.TRASH_VELOCITY));
        livesLeft = 10 * PickLevelScreen.levelNumber;
        lastChangeTime = TimeUtils.millis();
        flag = false;
    }

    @Override
    public void deactivateFreezeBoost() {
        body.setLinearVelocity(new Vector2(2, -GameSettings.BOSS_VELOCITY));
        freezeBoostActivationTime = 0;
        freezeBoostIsActive = false;
    }

    public void changeBossDirection() {
        if (!flag) {
            body.setLinearVelocity(new Vector2(-2, GameSettings.BOSS_VELOCITY));
            flag = true;
        } else {
            body.setLinearVelocity(new Vector2(2, -GameSettings.BOSS_VELOCITY));
            flag = false;
        }
    }

    public void ChangeDirection() {
        if (SetGameModeScreen.gameMode) {
            if (TimeUtils.millis() - lastChangeTime >= GameSettings.BOSS_CHANGE_DIRECTION) {
                lastChangeTime = TimeUtils.millis();
                changeBossDirection();
            }
        }
    }
}
