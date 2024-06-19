package com.mygdx.game.screens;

import static com.badlogic.gdx.math.MathUtils.random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.objects.BoostObject;
import com.mygdx.game.objects.BossObject;
import com.mygdx.game.objects.FreezeBoostObject;
import com.mygdx.game.objects.HealBoostObject;
import com.mygdx.game.objects.SpeedBoostObject;
import com.mygdx.game.objects.SuperTrashObject;
import com.mygdx.game.view.RecordsListView;
import com.mygdx.game.managers.MemoryManager;
import com.mygdx.game.utility.GameState;
import com.mygdx.game.objects.BulletObject;
import com.mygdx.game.managers.ContactManager;
import com.mygdx.game.utility.GameSession;
import com.mygdx.game.objects.TrashObject;
import com.mygdx.game.utility.GameResources;
import com.mygdx.game.utility.GameSettings;
import com.mygdx.game.MyGdxGame;

import com.mygdx.game.objects.ShipObject;
import com.mygdx.game.view.ButtonView;
import com.mygdx.game.view.ImageView;
import com.mygdx.game.view.LiveView;
import com.mygdx.game.view.MovingBackgroundView;
import com.mygdx.game.view.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class GameScreen extends ScreenAdapter {
    //создание обьектов
    MyGdxGame myGdxGame;
    ShipObject ship;
    GameSession gameSession;
    ArrayList<TrashObject> trashArray;
    ArrayList<BulletObject> bulletArray;
    ArrayList<SuperTrashObject> superTrashArray;
    ArrayList<BoostObject> BoostArray;
    ArrayList<BossObject> BossArray;
    ContactManager contactManager;
    MovingBackgroundView backgroundView;
    ImageView topBlackoutView;
    ImageView fullBlackoutView;
    LiveView liveView;
    TextView scoreTextView;
    TextView pauseTextView;
    TextView recordsTextView;
    TextView levelEndingTrue, levelEndingFalse;
    ButtonView pauseButton;
    ButtonView homeButton;
    ButtonView homeButton2;
    RecordsListView recordsListView;
    ButtonView continueButton;
    public static int bossCount;
    //конструктор класса
    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        gameSession = new GameSession();


        contactManager = new ContactManager(myGdxGame.world);
        //arrays
        trashArray = new ArrayList<>();
        superTrashArray = new ArrayList<>();
        bulletArray = new ArrayList<>();
        BoostArray = new ArrayList<>();
        BossArray = new ArrayList<>();

        //инициализирование корабля
        if (SetGameModeScreen.heroChoose) {
            ship = new ShipObject(
                    GameSettings.SCREEN_WIDTH / 2, 150,
                    GameSettings.SHIP_WIDTH1, GameSettings.SHIP_HEIGHT1,
                    GameResources.SHIP1_IMG_PATH,
                    myGdxGame.world
            );
        } else {
            ship = new ShipObject(
                    GameSettings.SCREEN_WIDTH / 2, 150,
                    GameSettings.SHIP_WIDTH2, GameSettings.SHIP_HEIGHT2,
                    GameResources.SHIP2_IMG_PATH,
                    myGdxGame.world
            );
        }
        //other view
        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        topBlackoutView = new ImageView(0, 1180, GameResources.BLACKOUT_TOP_IMG_PATH);
        fullBlackoutView = new ImageView(0, 0, GameResources.BLACKOUT_FULL_IMG_PATH);
        liveView = new LiveView(375, 1220, GameResources.LIVE_IMG_PATH);
        recordsListView = new RecordsListView(myGdxGame.commonWhiteFont, 690);
        //text
        recordsTextView = new TextView(myGdxGame.largeWhiteFont, 46, 842, "Last records");
        scoreTextView = new TextView(myGdxGame.commonWhiteFont, 50, 1225);
        pauseTextView = new TextView(myGdxGame.largeWhiteFont, GameSettings.SCREEN_WIDTH2 - 125,
                842, "Pause");
        levelEndingTrue = new TextView(myGdxGame.largeWhiteFont, 40, 842, "Level Cleared!");
        levelEndingFalse = new TextView(myGdxGame.largeWhiteFont, 40, 842, "Level Failed!");

        //buttons
        pauseButton = new ButtonView(605, 1200, 46, 54, GameResources.PAUSE_IMG_PATH);
        homeButton = new ButtonView(GameSettings.SCREEN_WIDTH2 + 25, GameSettings.SCREEN_HEIGHT2,
                250, 100, myGdxGame.commonBlackFont,
                GameResources.BUTTON_SHORT_IMG_PATH, "Home");
        homeButton2 = new ButtonView(
                280, 365,
                160, 70,
                myGdxGame.commonBlackFont,
                GameResources.BUTTON_SHORT_IMG_PATH,
                "Home"
        );

        continueButton = new ButtonView(GameSettings.SCREEN_WIDTH2 - 275,
                GameSettings.SCREEN_HEIGHT2, 250, 100,
                myGdxGame.commonBlackFont, GameResources.BUTTON_SHORT_IMG_PATH, "Continue");

    }

    @Override
    public void show() {
        restartGame();
    }

    @Override
    public void render(float delta) {

        handleInput();

        if (gameSession.state == GameState.PLAYING) {
            if (gameSession.shouldSpawnTrash()) {
                if (random.nextInt(5) != 0) {
                    TrashObject trashObject = new TrashObject(
                            GameSettings.TRASH_WIDTH, GameSettings.TRASH_HEIGHT,
                            GameResources.TRASH_IMG_PATH,
                            myGdxGame.world
                    );
                    trashArray.add(trashObject);
                } else {
                    SuperTrashObject superTrashObject = new SuperTrashObject(
                            GameSettings.SUPER_TRASH_WIDTH, GameSettings.SUPER_TRASH_HEIGHT,
                            GameResources.SUPER_TRASH_IMG_PATH,
                            myGdxGame.world
                    );
                    superTrashArray.add(superTrashObject);
                }
            }

            if (gameSession.shouldSpawnBoost()) {
                int temp = random.nextInt(5);
                if (temp == 0) {
                    HealBoostObject Boost = new HealBoostObject(
                            GameSettings.BOOST_WIDTH, GameSettings.BOOST_HEIGHT,
                            GameResources.HEAL_BOOST_IMG_PATH,
                            myGdxGame.world
                    );
                    BoostArray.add(Boost);
                } else if (temp == 1) {
                    SpeedBoostObject Boost = new SpeedBoostObject(
                            GameSettings.BOOST_WIDTH, GameSettings.BOOST_HEIGHT,
                            GameResources.ATTACK_SPEED_BOOST_IMG_PATH,
                            myGdxGame.world
                    );
                    BoostArray.add(Boost);
                } else if (temp == 2) {
                    FreezeBoostObject Boost = new FreezeBoostObject(
                            GameSettings.BOOST_WIDTH, GameSettings.BOOST_HEIGHT,
                            GameResources.FREEZE_BOOST_IMG_PATH,
                            myGdxGame.world
                    );
                    BoostArray.add(Boost);
                }
            }

            if (gameSession.shouldSpawnBoss() && bossCount < 1) {
                bossCount++;
                BossObject boss = new BossObject(GameSettings.BOSS_WIDTH, GameSettings.BOSS_HEIGHT,
                        GameResources.BOSS_IMG_PATH,
                        myGdxGame.world
                );
                BossArray.add(boss);
                if (myGdxGame.audioManager.isSoundOn)
                    myGdxGame.audioManager.deploySound.play(0.1f);
            }
                for (BossObject boss : BossArray) boss.ChangeDirection();

            if (ship.needToShoot()) {
                if (SetGameModeScreen.heroChoose) {
                    BulletObject laserBullet = new BulletObject(
                            ship.getX(), ship.getY() + ship.height / 2,
                            GameSettings.BULLET_WIDTH, GameSettings.BULLET_HEIGHT,
                            GameResources.BULLET_IMG_PATH,
                            myGdxGame.world
                    );
                    bulletArray.add(laserBullet);
                    if (myGdxGame.audioManager.isSoundOn)
                        myGdxGame.audioManager.shootSound.play(0.05f);
                } else {
                        BulletObject laserBullet = new BulletObject(
                                ship.getX(), ship.getY() + ship.height / 2,
                                GameSettings.BULLET_WIDTH, GameSettings.BULLET_HEIGHT,
                                GameResources.BULLET_IMG_PATH,
                                myGdxGame.world
                        );
                        bulletArray.add(laserBullet);
                        ship.queueCount++;
                        if (myGdxGame.audioManager.isSoundOn)
                            myGdxGame.audioManager.shootSound.play(0.05f);
                }
            }

            if (ship.checkFreezeBoostTo()) {
                ship.useFreezeBoost();
            }
            if (ship.checkSpeedBoostTo()) {
                ship.useSpeedBoost();
            }
            if (ship.checkHealBoostTo()) {
                ship.useHealBoost();
            }
            if (ship.checkFreezeBoost()) {
                ship.freezeBoostIsActive = false;
                for (TrashObject trash : trashArray) {
                    trash.useFreezeBoostTrash();
                }
                for (SuperTrashObject trash : superTrashArray) {
                    trash.useFreezeBoostTrash();
                }
                for (BossObject boss : BossArray) {
                    boss.useFreezeBoostTrash();
                }
            }

            if (TimeUtils.millis() - TrashObject.freezeBoostActivationTime
                    > GameSettings.FREEZE_BOOST_DURATION && TrashObject.freezeBoostIsActive) {
                for (TrashObject trash : trashArray) {
                    trash.deactivateFreezeBoost();
                }
            }
            if (TimeUtils.millis() - TrashObject.freezeBoostActivationTime
                    > GameSettings.FREEZE_BOOST_DURATION && TrashObject.freezeBoostIsActive) {
                for (SuperTrashObject trash : superTrashArray) {
                    trash.deactivateFreezeBoost();
                }
            }

            if (TimeUtils.millis() - ShipObject.speedBoostActivationTime
                    > GameSettings.SPEED_BOOST_DURATION && ShipObject.speedBoostIsActive) {
                ship.resetSpeedBoost();
            }

            if (!ship.isAlive()) {
                gameSession.endGame();
                recordsListView.setRecords(MemoryManager.loadRecordsTable());
            }
            for (BossObject boss : BossArray) {
                if (!boss.isAlive() && SetGameModeScreen.gameMode) {
                    gameSession.endGame();
                }
            }
            if (!ship.isAlive() && SetGameModeScreen.gameMode) {
                gameSession.endGame();
            }

            update();
            backgroundView.move();
            gameSession.updateScore();
            scoreTextView.setText("Score: " + gameSession.getScore());
            liveView.setLeftLives(ship.getLiveLeft());

            myGdxGame.stepWorld();
        }

        draw();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //функции:
    //все что связано с нажатием
    private void handleInput() {
        if (Gdx.input.isTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(
                    Gdx.input.getX(), Gdx.input.getY(), 0));

            switch (gameSession.state) {
                case PLAYING:
                    if (pauseButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        gameSession.pauseGame();
                    }
                    ship.move(myGdxGame.touch);
                    break;

                case PAUSED:
                    if (continueButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        gameSession.resumeGame();
                    }
                    if (homeButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        myGdxGame.setScreen(myGdxGame.menuScreen);
                    }
                    break;
                case ENDED:
                    if (homeButton2.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        myGdxGame.setScreen(myGdxGame.menuScreen);
                    }
                    break;
            }

        }
    }

    //все что связано с отрисовкой
    private void draw() {
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();

        backgroundView.draw(myGdxGame.batch);
        for (TrashObject trash : trashArray) trash.draw(myGdxGame.batch);
        for (SuperTrashObject trash : superTrashArray) trash.draw(myGdxGame.batch);
        for (BoostObject boost : BoostArray) boost.draw(myGdxGame.batch);
        ship.draw(myGdxGame.batch);
        for (BulletObject bullet : bulletArray) bullet.draw(myGdxGame.batch);
        topBlackoutView.draw(myGdxGame.batch);
        scoreTextView.draw(myGdxGame.batch);
        liveView.draw(myGdxGame.batch);
        pauseButton.draw(myGdxGame.batch);
        for (BossObject boss : BossArray) boss.draw(myGdxGame.batch);

        if (gameSession.state == GameState.PAUSED) {
            fullBlackoutView.draw(myGdxGame.batch);
            pauseTextView.draw(myGdxGame.batch);
            homeButton.draw(myGdxGame.batch);
            continueButton.draw(myGdxGame.batch);
        } else if (gameSession.state == GameState.ENDED) {
            fullBlackoutView.draw(myGdxGame.batch);
            homeButton2.draw(myGdxGame.batch);
            if (!SetGameModeScreen.gameMode) {
                recordsTextView.draw(myGdxGame.batch);
                recordsListView.draw(myGdxGame.batch);
            } else {
                if (!ship.isAlive()) {
                    levelEndingFalse.draw(myGdxGame.batch);
                } else {
                    levelEndingTrue.draw(myGdxGame.batch);
                }
            }
        }

        myGdxGame.batch.end();
    }

    //обновление массивов:
    private void update() {
        //default trash update
        Iterator<TrashObject> trashObjectiterator = trashArray.iterator();

        while (trashObjectiterator.hasNext()) {

            TrashObject nextTrash = trashObjectiterator.next();
            boolean hasToBeDestroyed = !nextTrash.isAlive() || !nextTrash.isInFrame();
            if (!nextTrash.isAlive()) {
                gameSession.destructionRegistration();
                if (myGdxGame.audioManager.isSoundOn) {
                    myGdxGame.audioManager.explosionSound.play(0.05f);
                }
            }
            if (hasToBeDestroyed) {
                myGdxGame.world.destroyBody(nextTrash.body);
                trashObjectiterator.remove();
            }
        }

        //superTrash update
        Iterator<SuperTrashObject> superTrashObjectIterator = superTrashArray.iterator();

        while (superTrashObjectIterator.hasNext()) {

            SuperTrashObject nextSuperTrash = superTrashObjectIterator.next();
            boolean hasToBeDestroyed = !nextSuperTrash.isAlive()
                    || (!nextSuperTrash.isInFrame() && nextSuperTrash.gotHit())
                    || !nextSuperTrash.isUp();
            if (!nextSuperTrash.isAlive()) {
                gameSession.SuperDestructionRegistration();
                if (myGdxGame.audioManager.isSoundOn) {
                    myGdxGame.audioManager.explosionSound.play(0.05f);
                    myGdxGame.audioManager.explosionSound.play(0.1f);
                }
            }
            if (hasToBeDestroyed) {
                myGdxGame.world.destroyBody(nextSuperTrash.body);
                superTrashObjectIterator.remove();
            }
        }

        //boss update if necessary
            Iterator<BossObject> bossObjectiterator = BossArray.iterator();

            while (bossObjectiterator.hasNext()) {

                BossObject nextBoss = bossObjectiterator.next();
                if (!nextBoss.isAlive()) {
                    gameSession.BossDestructionRegistration();
                    if (myGdxGame.audioManager.isSoundOn) {
                        myGdxGame.audioManager.deploySound.play(0.1f);
                        myGdxGame.world.destroyBody(nextBoss.body);
                        bossObjectiterator.remove();
                }
            }
        }

        //bullet update
        Iterator<BulletObject> bulletObjectIterator = bulletArray.iterator();

        while (bulletObjectIterator.hasNext()) {

            BulletObject nextBullet = bulletObjectIterator.next();
            if (nextBullet.hasToBeDestroyed()) {
                myGdxGame.world.destroyBody(nextBullet.body);
                bulletObjectIterator.remove();
            }
        }
        //boost update
        Iterator<BoostObject> boostObjectIterator = BoostArray.iterator();

        while (boostObjectIterator.hasNext()) {

            BoostObject nextBoost = boostObjectIterator.next();
            if (nextBoost.hasToBeDestroyed()) {
                myGdxGame.world.destroyBody(nextBoost.body);
                boostObjectIterator.remove();
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    private void restartGame() {
        for (int i = 0; i < trashArray.size(); i++) {
            myGdxGame.world.destroyBody(trashArray.get(i).body);
            trashArray.remove(i--);
        }

        for (int i = 0; i < superTrashArray.size(); i++) {
            myGdxGame.world.destroyBody(superTrashArray.get(i).body);
            superTrashArray.remove(i--);
        }

        for (int i = 0; i < BoostArray.size(); i++) {
            myGdxGame.world.destroyBody(BoostArray.get(i).body);
            BoostArray.remove(i--);
        }
        for (int i = 0; i < BossArray.size(); i++) {
            myGdxGame.world.destroyBody(BossArray.get(i).body);
            BossArray.remove(i--);
        }
        for (int i = 0; i < bulletArray.size(); i++) {
            myGdxGame.world.destroyBody(bulletArray.get(i).body);
            bulletArray.remove(i--);
        }

        if (ship != null) {
            myGdxGame.world.destroyBody(ship.body);
        }

        if (SetGameModeScreen.heroChoose) {
            ship = new ShipObject(
                    GameSettings.SCREEN_WIDTH / 2, 150,
                    GameSettings.SHIP_WIDTH1, GameSettings.SHIP_HEIGHT1,
                    GameResources.SHIP1_IMG_PATH,
                    myGdxGame.world
            );
        } else {
            ship = new ShipObject(
                    GameSettings.SCREEN_WIDTH / 2, 150,
                    GameSettings.SHIP_WIDTH2, GameSettings.SHIP_HEIGHT2,
                    GameResources.SHIP2_IMG_PATH,
                    myGdxGame.world
            );
        }

        bulletArray.clear();
        gameSession.startGame();
    }
}
