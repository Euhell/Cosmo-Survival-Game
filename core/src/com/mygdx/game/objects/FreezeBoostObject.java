package com.mygdx.game.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.utility.GameSettings;

public class FreezeBoostObject extends BoostObject {
    public FreezeBoostObject(int width, int height, String texturePath, World world) {
        super(width, height, texturePath, GameSettings.FREEZE_BOOST_BIT, world);

        body.setLinearVelocity(new Vector2(0, -GameSettings.FREEZE_BOOST_VELOCITY));
        wasHit = false;
    }
}
