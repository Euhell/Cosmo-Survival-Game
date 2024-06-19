package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utility.GameResources;
import com.mygdx.game.view.ButtonView;
import com.mygdx.game.view.MovingBackgroundView;
import com.mygdx.game.view.TextView;

public class MenuScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    MovingBackgroundView backgroundView;
    TextView titleView1, titleView2;
    ButtonView playButton;
    ButtonView settingsButtonView;
    ButtonView exitButtonView;
    public MenuScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        //title
        titleView1 = new TextView(myGdxGame.largeWhiteFont, 180, 960, "Cosmo");
        titleView2 = new TextView(myGdxGame.largeWhiteFont, 180, 910, "survival");
        //buttons
        playButton = new ButtonView(140, 646, 440, 70,
                myGdxGame.commonBlackFont, GameResources.BUTTON_LONG_IMG_PATH, "play");
        settingsButtonView = new ButtonView(140, 551, 440, 70,
                myGdxGame.commonBlackFont, GameResources.BUTTON_LONG_IMG_PATH, "settings");
        exitButtonView = new ButtonView(140, 456, 440, 70,
                myGdxGame.commonBlackFont, GameResources.BUTTON_LONG_IMG_PATH, "exit");
    }

    @Override
    public void render(float delta) {
        handleInput();
        draw();
    }


    //functions
    private void draw() {
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();

        backgroundView.draw(myGdxGame.batch);
        titleView1.draw(myGdxGame.batch);
        titleView2.draw(myGdxGame.batch);
        exitButtonView.draw(myGdxGame.batch);
        settingsButtonView.draw(myGdxGame.batch);
        playButton.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (playButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.gameModeScreen);
            }
            if (exitButtonView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                Gdx.app.exit();
            }
            if (settingsButtonView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.settingsScreen);
            }
        }
    }
}
