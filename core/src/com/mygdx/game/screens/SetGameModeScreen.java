package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utility.GameResources;
import com.mygdx.game.view.ButtonView;
import com.mygdx.game.view.ImageView;
import com.mygdx.game.view.MovingBackgroundView;
import com.mygdx.game.view.TextView;

import java.awt.Image;

public class SetGameModeScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    MovingBackgroundView backgroundView;
    ButtonView modeEndless;
    ButtonView modeLevel;
    ImageView buttonImageFir;
    ImageView buttonImageSec;
    TextView heroOneText;
    TextView heroTwoText;
    public static boolean heroChoose;
    public static boolean gameMode;
    public SetGameModeScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        heroChoose = true;

        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        modeEndless = new ButtonView(140, 742, 440, 70, myGdxGame.commonBlackFont,
                GameResources.BUTTON_LONG_IMG_PATH, "Endless \n mode");
        modeLevel = new ButtonView(140, 646, 440, 70, myGdxGame.commonBlackFont,
                GameResources.BUTTON_LONG_IMG_PATH, "Level \n mode");
        heroOneText = new TextView(myGdxGame.commonBlackFont, 225, 561, "blue ship \n [base]");
        heroTwoText = new TextView(myGdxGame.commonBlackFont, 225, 466, "red ship");
        buttonImageFir = new ImageView(140, 551, GameResources.BUTTON_LONG_IMG_PATH);
        buttonImageSec = new ImageView(140, 456, GameResources.BUTTON_LONG_IMG_PATH);
    }

    @Override
    public void render(float delta) {
        handleInput();
        draw();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if(modeEndless.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                gameMode = false;
                myGdxGame.setScreen(myGdxGame.gameScreen);
            }
            if (modeLevel.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                gameMode = true;
                myGdxGame.setScreen(myGdxGame.pickLevelScreen);
            }
            if (heroOneText.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                heroOneText.setText("blue ship \nis chosen");
                heroTwoText.setText("red ship");
                heroChoose = true;
            }
            if (heroTwoText.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                heroTwoText.setText("red ship \nis chosen");
                heroOneText.setText("blue ship");
                heroChoose = false;
            }
        }
    }

    private void draw() {
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();

        backgroundView.draw(myGdxGame.batch);
        modeEndless.draw(myGdxGame.batch);
        modeLevel.draw(myGdxGame.batch);
        buttonImageFir.draw(myGdxGame.batch);
        buttonImageSec.draw(myGdxGame.batch);
        heroOneText.draw(myGdxGame.batch);
        heroTwoText.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }
}
