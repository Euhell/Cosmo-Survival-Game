package com.mygdx.game.managers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.objects.GameObject;
import com.mygdx.game.objects.ShipObject;
import com.mygdx.game.objects.SuperTrashObject;
import com.mygdx.game.objects.TrashObject;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.utility.GameSettings;

public class ContactManager {

    World world;

    public ContactManager(World world) {
        this.world = world;

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                //начало контакта
                Fixture fixA = contact.getFixtureA();
                Fixture fixB = contact.getFixtureB();

                int cDef = fixA.getFilterData().categoryBits;
                int cDef2 = fixB.getFilterData().categoryBits;

                if (cDef == GameSettings.TRASH_BIT && cDef2 == GameSettings.BULLET_BIT
                        || cDef == GameSettings.BULLET_BIT && cDef2 == GameSettings.TRASH_BIT
                        || cDef == GameSettings.TRASH_BIT && cDef2 == GameSettings.SHIP_BIT
                        || cDef == GameSettings.SHIP_BIT && cDef2 == GameSettings.TRASH_BIT
                        || cDef == GameSettings.SUPER_TRASH_BIT && cDef2 == GameSettings.BULLET_BIT
                        || cDef == GameSettings.BULLET_BIT && cDef2 == GameSettings.SUPER_TRASH_BIT
                        || cDef == GameSettings.BOSS_BIT && cDef2 == GameSettings.BULLET_BIT
                        || cDef == GameSettings.BULLET_BIT && cDef2 == GameSettings.BOSS_BIT
                        || cDef == GameSettings.SHIP_BIT && cDef2 == GameSettings.BOSS_BIT
                        || cDef == GameSettings.BOSS_BIT && cDef2 == GameSettings.SHIP_BIT) {
                    ((GameObject) fixA.getUserData()).hit();
                    ((GameObject) fixB.getUserData()).hit();
                }
                else if (cDef == GameSettings.SUPER_TRASH_BIT && cDef2 == GameSettings.SHIP_BIT) {
                    ((GameObject) fixA.getUserData()).kill();
                    ((GameObject) fixB.getUserData()).hit();
                }
                else if (cDef == GameSettings.SHIP_BIT && cDef2 == GameSettings.SUPER_TRASH_BIT) {
                    ((GameObject) fixA.getUserData()).hit();
                    ((GameObject) fixB.getUserData()).kill();
                }
                else if (cDef == GameSettings.SPEED_BOOST_BIT && cDef2 == GameSettings.SHIP_BIT) {
                    ((GameObject) fixA.getUserData()).hit();
                    ((GameObject) fixB.getUserData()).useSpeedBoost();
                }
                else if (cDef == GameSettings.SHIP_BIT && cDef2 == GameSettings.SPEED_BOOST_BIT) {
                    ((GameObject) fixA.getUserData()).useSpeedBoost();
                    ((GameObject) fixB.getUserData()).hit();
                }
                else if (cDef == GameSettings.HEAL_BOOST_BIT && cDef2 == GameSettings.SHIP_BIT) {
                    ((GameObject) fixA.getUserData()).hit();
                    ((GameObject) fixB.getUserData()).useHealBoost();
                }
                else if (cDef == GameSettings.SHIP_BIT && cDef2 == GameSettings.HEAL_BOOST_BIT) {
                    ((GameObject) fixA.getUserData()).useHealBoost();
                    ((GameObject) fixB.getUserData()).hit();
                }
                else if (cDef == GameSettings.FREEZE_BOOST_BIT && cDef2 == GameSettings.SHIP_BIT) {
                    ((GameObject) fixA.getUserData()).hit();
                    ((GameObject) fixB.getUserData()).useFreezeBoost();
                }
                else if (cDef == GameSettings.SHIP_BIT && cDef2 == GameSettings.FREEZE_BOOST_BIT) {
                    ((GameObject) fixA.getUserData()).useFreezeBoost();
                    ((GameObject) fixB.getUserData()).hit();
                }

                else if (cDef == GameSettings.FREEZE_BOOST_BIT && cDef2 == GameSettings.BULLET_BIT) {
                    ((GameObject) fixA.getUserData()).hit();
                    ((GameObject) fixB.getUserData()).useFreezeBoost();
                    ((GameObject) fixB.getUserData()).hit();
                }
                else if (cDef == GameSettings.BULLET_BIT && cDef2 == GameSettings.FREEZE_BOOST_BIT) {
                    ((GameObject) fixA.getUserData()).useFreezeBoost();
                    ((GameObject) fixA.getUserData()).hit();
                    ((GameObject) fixB.getUserData()).hit();
                }
                else if (cDef == GameSettings.HEAL_BOOST_BIT && cDef2 == GameSettings.BULLET_BIT) {
                    ((GameObject) fixA.getUserData()).hit();
                    ((GameObject) fixB.getUserData()).useHealBoost();
                    ((GameObject) fixB.getUserData()).hit();
                }
                else if (cDef == GameSettings.BULLET_BIT && cDef2 == GameSettings.HEAL_BOOST_BIT) {
                    ((GameObject) fixA.getUserData()).useHealBoost();
                    ((GameObject) fixA.getUserData()).hit();
                    ((GameObject) fixB.getUserData()).hit();
                }
                else if (cDef == GameSettings.SPEED_BOOST_BIT && cDef2 == GameSettings.BULLET_BIT) {
                    ((GameObject) fixA.getUserData()).hit();
                    ((GameObject) fixB.getUserData()).useSpeedBoost();
                    ((GameObject) fixB.getUserData()).hit();
                }
                else if (cDef == GameSettings.BULLET_BIT && cDef2 == GameSettings.SPEED_BOOST_BIT) {
                    ((GameObject) fixA.getUserData()).useSpeedBoost();
                    ((GameObject) fixA.getUserData()).hit();
                    ((GameObject) fixB.getUserData()).hit();
                }
            }

            @Override
            public void endContact(Contact contact) {
                //завершение контакта
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                //перед вычислением всех контактоа
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
                // после вычислений контактов
            }
        });
    }

}
