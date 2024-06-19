package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.utility.GameResources;

public class LiveView extends View {
    private final static int livePadding = 6;
    private int leftLives;
    private Texture texture;
    public LiveView(float x, float y, String imagePath) {
        super(x, y);
        texture = new Texture(GameResources.LIVE_IMG_PATH);
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        leftLives = 0;
    }

    public void setLeftLives(int leftLives) {
        this.leftLives = leftLives;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (leftLives > 0) batch.draw(texture, x + (texture.getWidth() + livePadding) + 25, y, width, height);
        if (leftLives > 1) batch.draw(texture, x + 25, y, width, height);
        if (leftLives > 2) batch.draw(texture, x + 2 * (texture.getWidth() + livePadding) + 25, y, width, height);
        if (leftLives > 3) batch.draw(texture, x - (texture.getWidth() + livePadding) + 25, y, width, height);
        if (leftLives > 4) batch.draw(texture, x + 3 * (texture.getWidth() + livePadding) + 25, y, width, height);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}