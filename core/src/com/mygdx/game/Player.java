package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.mygdx.game.MyGdxGame.batch;
import static com.mygdx.game.MyGdxGame.entities;

class Player extends Entity {
    private float ACCELERATION = 0.04f;
    private float DECELERATION = 0.997f;
    private Sprite withoutThrust;
    private Sprite withThrust;
    private long startTime = 0;

    private enum CurrentWeapon {
        Projectile,
        Missile
    }

    CurrentWeapon currentWeapon;

    Map<CurrentWeapon, String> weaponImages;

    public CurrentWeapon getCurrentWeapon() {
        return currentWeapon;
    }

    public void setCurrentWeapon(CurrentWeapon currentWeapon) {
        this.currentWeapon = currentWeapon;
    }

    Player(Vector2 position) {
        velocity = new Vector2(0, 0);
        withoutThrust = new Sprite(new Texture("images/ship_without_thrust.png"));
        withThrust = new Sprite(new Texture("images/ship_with_thrust.png"));
        sprite = new Sprite(withoutThrust);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        this.position = position;
        sprite.setPosition(position.x, position.y);
        currentWeapon = CurrentWeapon.Missile;
        weaponImages = new HashMap<CurrentWeapon, String>();
        weaponImages.put(CurrentWeapon.Projectile, "images/BluePlasmaBullet.png");
        weaponImages.put(CurrentWeapon.Missile, "images/missile.png");
        startTime = TimeUtils.millis();

    }

    private void UpdateCoordinates(Touchpad touchPad) {

        Vector2 v = new Vector2(touchPad.getKnobPercentX(), touchPad.getKnobPercentY());
        if (!(Math.abs(v.x) < 0.3 && Math.abs(v.y) < 0.3)) {
            sprite.set(withThrust);
            angle = v.angle();
            velocity.x += v.x * ACCELERATION;
            velocity.y += v.y * ACCELERATION;
        } else {
            velocity.x *= DECELERATION;
            velocity.y *= DECELERATION;
            sprite.set(withoutThrust);
        }
        position.x += velocity.x;
        position.y += velocity.y;
        if (OnTheEdge()) {
            JumpToOppositeMapSide();
            ReduceSpeed();
        }
        sprite.setX(position.x);
        sprite.setY(position.y);
        sprite.setRotation(angle);
    }

    void MakeShot(TouchPad touchPad) {
        if (touchPad.IsButtonPressed()) {
            if (TimeUtils.timeSinceMillis(startTime) > 100) {
                new ConcreteWeapon(this.getCurrentWeapon().name(),
                        new Sprite(new Texture(weaponImages.get(this.getCurrentWeapon()))),
                        new Vector2(position), angle);
                startTime = TimeUtils.millis();
            }
        }
    }

    void ProcessingInput(TouchPad touchPad) {
        UpdateCoordinates(touchPad.GetTouchpad());
        MakeShot(touchPad);
    }

    void Draw() {
        this.GetSprite().draw(batch);
    }

    void Update(TouchPad touchPad, Vector2 vector2) {
//        UpdateCoordinates(touchPad.GetTouchpad());
        ProcessingInput(touchPad);
//        button.Update();
        Draw();
    }
}
