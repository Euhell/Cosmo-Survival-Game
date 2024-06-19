package com.mygdx.game;

import static com.mygdx.game.utility.GameResources.FONT_PATH;

import static java.awt.Color.WHITE;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.managers.AudioManager;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.PickLevelScreen;
import com.mygdx.game.screens.SetGameModeScreen;
import com.mygdx.game.screens.SettingsScreen;
import com.mygdx.game.utility.FontBuilder;
import com.mygdx.game.utility.GameResources;
import com.mygdx.game.utility.GameSettings;

public class MyGdxGame extends Game {
	//создание полей
	public World world;
	public SpriteBatch batch;
	public OrthographicCamera camera;

	public GameScreen gameScreen;
	public MenuScreen menuScreen;
	public SetGameModeScreen gameModeScreen;
	public PickLevelScreen pickLevelScreen;
	public PickLevelScreen pickLevelScreen2;
	public SettingsScreen settingsScreen;

	public Vector3 touch;
	public BitmapFont commonWhiteFont;
	public BitmapFont commonBlackFont;
	public BitmapFont largeBlackFont;
	public BitmapFont largeWhiteFont;
	public AudioManager audioManager;
	float accumulator = 0;

	@Override
	public void create () {
		//запускаем библиотеку
		Box2D.init();

		//инициализация мира
		world = new World(new Vector2(0, 0), true);

		largeWhiteFont = FontBuilder.generate(48, Color.WHITE, GameResources.FONT_PATH);
		commonWhiteFont = FontBuilder.generate(24, Color.WHITE, GameResources.FONT_PATH);
		largeBlackFont = FontBuilder.generate(96, Color.BLACK, GameResources.FONT_PATH);
		commonBlackFont = FontBuilder.generate(24, Color.BLACK, GameResources.FONT_PATH);

		audioManager = new AudioManager();

		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);

		gameScreen = new GameScreen(this);
		menuScreen = new MenuScreen(this);
		gameModeScreen = new SetGameModeScreen(this);
		settingsScreen = new SettingsScreen(this);
		pickLevelScreen = new PickLevelScreen(this);
		pickLevelScreen2 = new PickLevelScreen(this);

		setScreen(menuScreen);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	public void stepWorld() {
		float delta = Gdx.graphics.getDeltaTime();
		accumulator += Math.min(delta, 0.25f);

		if (accumulator >= GameSettings.STEP_TIME) {
			accumulator -= GameSettings.STEP_TIME;
			world.step(GameSettings.STEP_TIME, GameSettings.VELOCITY_ITERATIONS, GameSettings.POSITION_ITERATIONS);
		}
	}
}
