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

public class PickLevelScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    MovingBackgroundView backgroundView;
    ButtonView level1;
    ButtonView level2;
    ButtonView level3;
    ButtonView level4;
    public static int levelNumber;
    public PickLevelScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        level1 = new ButtonView(70, 742, 280, 280, myGdxGame.largeBlackFont,
                GameResources.BUTTON_SHORT_IMG_PATH, "1");
        level2 = new ButtonView(380, 742, 280, 280, myGdxGame.largeBlackFont,
                GameResources.BUTTON_SHORT_IMG_PATH, "2");
        level3 = new ButtonView(70, 356, 280, 280, myGdxGame.largeBlackFont,
                GameResources.BUTTON_SHORT_IMG_PATH, "3");
        level4 = new ButtonView(380, 356, 280, 280, myGdxGame.largeBlackFont,
                GameResources.BUTTON_SHORT_IMG_PATH, "4");
    }

    @Override
    public void render(float delta) {
        handleInput();
        draw();
    }

    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if(level1.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                levelNumber = 1;
                myGdxGame.setScreen(myGdxGame.gameScreen);

            }
            if (level2.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                levelNumber = 2;
                myGdxGame.setScreen(myGdxGame.gameScreen);

            }
            if (level3.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                levelNumber = 3;
                myGdxGame.setScreen(myGdxGame.gameScreen);

            }
            if (level4.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                levelNumber = 4;
                myGdxGame.setScreen(myGdxGame.gameScreen);
            }
        }
    }

    protected void draw() {
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();

        backgroundView.draw(myGdxGame.batch);
            level1.draw(myGdxGame.batch);
            level2.draw(myGdxGame.batch);
            level3.draw(myGdxGame.batch);
            level4.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }
}
