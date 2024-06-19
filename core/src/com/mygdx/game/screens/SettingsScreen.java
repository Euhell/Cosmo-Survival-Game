package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.managers.MemoryManager;
import com.mygdx.game.utility.GameResources;
import com.mygdx.game.view.ButtonView;
import com.mygdx.game.view.ImageView;
import com.mygdx.game.view.MovingBackgroundView;
import com.mygdx.game.view.TextView;

import java.util.ArrayList;

public class SettingsScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    ImageView blackoutMid;
    MovingBackgroundView backgroundView;
    ButtonView returnButton;
    TextView textMusic;
    TextView textSound;
    TextView textClear;
    TextView textTitle;

    public SettingsScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        blackoutMid = new ImageView(85, 365, GameResources.BLACKOUT_MIDDLE_IMG_PATH);
        returnButton = new ButtonView(250, 250, 440, 70,
                myGdxGame.commonBlackFont, GameResources.BUTTON_SHORT_IMG_PATH, "return");
        textTitle = new TextView(myGdxGame.largeWhiteFont, 250, 950, "Settings");
        textMusic = new TextView(myGdxGame.commonWhiteFont, 100, 650, "music: ON");
        textSound = new TextView(myGdxGame.commonWhiteFont, 100, 550, "sound: ON");
        textClear = new TextView(myGdxGame.commonWhiteFont, 100, 450, "clear records");
    }

    @Override
    public void render(float delta) {

        handleInput();

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();

        backgroundView.draw(myGdxGame.batch);
        textTitle.draw(myGdxGame.batch);
        blackoutMid.draw(myGdxGame.batch);
        returnButton.draw(myGdxGame.batch);
        textMusic.draw(myGdxGame.batch);
        textSound.draw(myGdxGame.batch);
        textClear.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }


    void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (returnButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.menuScreen);
            }
            if (textClear.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                textClear.setText("clear records cleared");
            }
            if (textMusic.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                MemoryManager.saveMusicSettings(!MemoryManager.loadIsMusicOn());
                textMusic.setText("music: " + translateStateToText(myGdxGame.audioManager.isMusicOn));
                myGdxGame.audioManager.updateMusicFlag();
            }
            if (textSound.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                MemoryManager.saveSoundSettings(!MemoryManager.loadIsSoundOn());
                textSound.setText("sound: " + translateStateToText(myGdxGame.audioManager.isSoundOn));
                myGdxGame.audioManager.updateSoundFlag();
            }
            if (textClear.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                MemoryManager.saveTableOfRecords(new ArrayList<>());
            }
        }
    }
    private String translateStateToText(boolean state) {
        return state ? "ON" : "OFF";
    }
}
