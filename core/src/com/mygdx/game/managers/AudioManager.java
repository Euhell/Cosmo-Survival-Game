package com.mygdx.game.managers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.utility.GameResources;

public class AudioManager {
    public Music backgroundMusic;
    public Sound shootSound;
    public Sound explosionSound;
    public Sound deploySound;
    public boolean isSoundOn;
    public boolean isMusicOn;

    public void updateMusicFlag() {
        isMusicOn = MemoryManager.loadIsMusicOn();
        if (isMusicOn) backgroundMusic.play();
        else backgroundMusic.stop();
    }
    public void updateSoundFlag() {
        isSoundOn = MemoryManager.loadIsSoundOn();
    }


    public AudioManager() {

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(GameResources.BACKGROUND_MUSIC_PATH));
        shootSound = Gdx.audio.newSound(Gdx.files.internal(GameResources.SHOOT_SOUND_PATH));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal(GameResources.DESTROY_SOUND_PATH));
        deploySound = Gdx.audio.newSound(Gdx.files.internal(GameResources.DEPLOY_SOUND_PATH));

        backgroundMusic.setVolume(0.3f);
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
        updateSoundFlag();
        updateMusicFlag();
    }
}
