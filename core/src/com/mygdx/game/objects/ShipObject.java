package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.screens.SetGameModeScreen;
import com.mygdx.game.utility.GameSettings;

public class ShipObject extends GameObject {

    long lastShotTime;
    static int livesLeft;
    int maxHealth;
    public int queueCount;
    public long attackCoolDown;
    public long baseAttackCoolDown;
    public  static long speedBoostActivationTime;
    public static boolean speedBoostIsActive;
    public static boolean freezeBoostIsActive;
    public static boolean freezeBoostNeedToActivate;
    public static boolean speedBoostNeedToActivate;
    public static boolean healBoostNeedToActivate;

    public ShipObject(int x, int y, int width, int height, String texturePath, World world) {
        super(texturePath, x, y, width, height, GameSettings.SHIP_BIT, world);
        if (SetGameModeScreen.heroChoose) {
            //blue hero
            livesLeft = 3;
            maxHealth = 3;
            attackCoolDown = GameSettings.SHOOTING_COOL_DOWN_BLUE;
            body.setLinearDamping(10);
        } else {
            //red hero
            livesLeft = 5;
            maxHealth = 5;
            queueCount = 3;
            baseAttackCoolDown = GameSettings.QUEUE_COOL_DOWN_RED;
            attackCoolDown = baseAttackCoolDown;
            body.setLinearDamping(15);
        }
        speedBoostActivationTime = 0;
        speedBoostIsActive = false;
        freezeBoostIsActive = false;
        freezeBoostNeedToActivate = false;
        speedBoostNeedToActivate = false;
        healBoostNeedToActivate = false;
    }

    @Override
    public void draw(SpriteBatch batch) {
        putInFrame();
        super.draw(batch);
    }
    
    public void move(Vector3 vector3) {
        body.applyForceToCenter(new Vector2((vector3.x - getX()) * GameSettings.SHIP_FORCE_RATIO,
                        (vector3.y - getY()) * GameSettings.SHIP_FORCE_RATIO),
                true
        );
    }

    private void putInFrame() {
        if (getY() > (GameSettings.SCREEN_HEIGHT / 2f - height / 2f)) {
            setY(GameSettings.SCREEN_HEIGHT / 2 - height / 2);
        }
        if (getY() <= (height / 2f)) {
            setY(height / 2);
        }
        if (getX() <= -width / 2f) {
            setX(GameSettings.SCREEN_WIDTH);
        }
        if (getX() > (GameSettings.SCREEN_WIDTH + width / 2f)) {
            setX(0);
        }
    }

    public boolean needToShoot() {
        if (SetGameModeScreen.heroChoose) {
            //blue hero
            if (TimeUtils.millis() - lastShotTime >= attackCoolDown) {
                lastShotTime = TimeUtils.millis();
                return true;
            }
            return false;
        } else {
            //red hero
            if (TimeUtils.millis() - lastShotTime >= attackCoolDown && queueCount != 3) {
                lastShotTime = TimeUtils.millis();
                attackCoolDown = GameSettings.SHOOTING_COOL_DOWN_RED;
                return true;
            }
            if (queueCount == 3) {
                attackCoolDown = baseAttackCoolDown;
                queueCount = 0;
            }
            return false;
        }
    }

    @Override
    public void hit() {
        livesLeft -= 1;
    }

    @Override
    public void useSpeedBoost() {
        if (SetGameModeScreen.heroChoose) {
            //blue hero
            attackCoolDown /= 2;
        } else {
            //red hero
            baseAttackCoolDown /= 2;
        }
        speedBoostActivationTime = TimeUtils.millis();
        speedBoostIsActive = true;
        speedBoostNeedToActivate = false;
    }

    public void resetSpeedBoost() {
        if (SetGameModeScreen.heroChoose) {
            //blue hero
            attackCoolDown = GameSettings.SHOOTING_COOL_DOWN_BLUE;
        } else {
            //red hero
            baseAttackCoolDown = GameSettings.QUEUE_COOL_DOWN_RED;
        }
        speedBoostActivationTime = 0;
        speedBoostIsActive = false;
    }

    @Override
    public void useHealBoost() {
        if (livesLeft != maxHealth) livesLeft += 1;
        healBoostNeedToActivate = false;
    }

    @Override
    public void useFreezeBoost() {
        freezeBoostIsActive = true;
        freezeBoostNeedToActivate = false;
    }
    public boolean checkFreezeBoost() {
        return freezeBoostIsActive;
    }
    public boolean checkFreezeBoostTo() {
        return freezeBoostNeedToActivate;
    }
    public boolean checkSpeedBoostTo() {
        return speedBoostNeedToActivate;
    }
    public boolean checkHealBoostTo() {
        return healBoostNeedToActivate;
    }
    public boolean isAlive() {
        return livesLeft > 0;
    }
    public int getLiveLeft() {
        return livesLeft;
    }
}
