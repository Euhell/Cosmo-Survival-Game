package com.mygdx.game.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.utility.GameSettings;

public class SpeedBoostObject extends BoostObject {
    public SpeedBoostObject(int width, int height, String texturePath, World world) {
        super(width, height, texturePath, GameSettings.SPEED_BOOST_BIT, world);

        body.setLinearVelocity(new Vector2(0, -GameSettings.BOOST_VELOCITY));
        wasHit = false;
    }
}
