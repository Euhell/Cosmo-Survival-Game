package com.mygdx.game.utility;

public class GameSettings {
    //Device settings
    public static final int SCREEN_WIDTH = 720;
    public static final int SCREEN_HEIGHT = 1280;
    public static final int SCREEN_WIDTH2 = SCREEN_WIDTH / 2;
    public static final int SCREEN_HEIGHT2 = SCREEN_HEIGHT / 2;
    //Physic settings
    public static final float STEP_TIME = 1f / 60;
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 6;
    public static final float SCALE = 0.05f;
    public static final int SHIP_FORCE_RATIO = 10;
    public static final int TRASH_VELOCITY = 20;
    public static final float SUPER_TRASH_VELOCITY_AFTER = 6.5f;
    public static final int SUPER_TRASH_VELOCITY_INITIAL = 50;
    public static final float BOSS_VELOCITY = 5.5f;
    public static final float BOSS_CHANGE_DIRECTION = 3000;
    public static final int BULLET_VELOCITY = 200;
    public static final int BOOST_VELOCITY = 15;
    public static final int FREEZE_BOOST_VELOCITY = 5;
    public static final long STARTING_TRASH_APPEARANCE_COOL_DOWN = 2000;
    public static final long STARTING_BOOST_APPEARANCE_COOL_DOWN = 5000;
    public static final long STARTING_BOSS_APPEARANCE_COOL_DOWN = 30000;
    public static final int SHOOTING_COOL_DOWN_BLUE = 1000;
    public static final int QUEUE_COOL_DOWN_RED = 2500;
    public static final int SHOOTING_COOL_DOWN_RED = 250;
    public static final int PADDING_HORIZONTAL = 30;
    public static final short SPEED_BOOST_DURATION = 10000;
    public static final short FREEZE_BOOST_DURATION = 5000;
    public static final short TRASH_BIT = 1;
    public static final short SUPER_TRASH_BIT = 2;
    public static final short SHIP_BIT = 4;
    public static final short BULLET_BIT = 8;
    public static final short SPEED_BOOST_BIT = 16;
    public static final short HEAL_BOOST_BIT = 32;
    public static final short FREEZE_BOOST_BIT = 64;
    public static final short BOSS_BIT = 128;
    //Object sizes
    public static final int SHIP_WIDTH1 = 150;
    public static final int SHIP_HEIGHT1 = 150;
    public static final int SHIP_WIDTH2 = 200;
    public static final int SHIP_HEIGHT2 = 200;
    public static final int TRASH_WIDTH = 140;
    public static final int TRASH_HEIGHT = 100;
    public static final int SUPER_TRASH_WIDTH = 120;
    public static final int SUPER_TRASH_HEIGHT = 100;
    public static final int BOSS_WIDTH = 520;
    public static final int BOSS_HEIGHT = 500;
    public static final int BULLET_WIDTH = 15;
    public static final int BULLET_HEIGHT = 45;
    public static final int BOOST_WIDTH = 30;
    public static final int BOOST_HEIGHT = 60;
}
