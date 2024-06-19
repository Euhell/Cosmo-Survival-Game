package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.utility.GameSettings;

public abstract class GameObject {
    public int width, height;
    public Body body;
    public short cBits;

    Texture texture;

    GameObject(String texturePath, int x, int y, int width, int height, short cBits, World world) {
        this.width = width;
        this.height = height;
        this.cBits = cBits;

        texture = new Texture(texturePath);
        body = createBody(x, y, world);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, getX() - (width / 2f), getY() - (height / 2f), width, height);
    }
    public int getX() {
        return (int) (body.getPosition().x / GameSettings.SCALE);
    }

    public int getY() {
        return (int) (body.getPosition().y / GameSettings.SCALE);
    }

    public void setX(int x) {
        body.setTransform(x * GameSettings.SCALE, body.getPosition().y, 0);
    }

    public void setY(int y) {
        body.setTransform(body.getPosition().x, y * GameSettings.SCALE, 0);
    }

    public void hit() {
        // all physics objects could be hit
    }
    public void kill() {

    }
    public void useSpeedBoost() {

    }
    public void useHealBoost() {

    }
    public void useFreezeBoost() {

    }
    private Body createBody(float x, float y, World world) {
        BodyDef def = new BodyDef(); // объект содержащий все необходимые данные

        def.type = BodyDef.BodyType.DynamicBody; // тип тела
        def.fixedRotation = true; // фиксируем тело
        Body body = world.createBody(def); // создаём объект по определению

        CircleShape circleShape = new CircleShape(); // задаём коллайдер в форме круга
        circleShape.setRadius(Math.max(width, height) * GameSettings.SCALE / 2f); // определяем радиус круга коллайдера

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape; // устанавливаем коллайдер
        fixtureDef.density = 0.1f; // плотность тела
        fixtureDef.friction = 1f; // коэффициент трения
        fixtureDef.filter.categoryBits = cBits;

        Fixture fixture = body.createFixture(fixtureDef); // создаём fixture по определению
        fixture.setUserData(this); //
        circleShape.dispose(); // очистка ненужного обьекта

        body.setTransform(x * GameSettings.SCALE, y * GameSettings.SCALE, 0); // устанавливаем позицию тела
        return body;
    }
}
