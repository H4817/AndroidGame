package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public final class Player extends Entity {
    private float angle;
    private static final float speed = 5;

    Player(Vector2 position) {
        LoadImage("8888.png");
        this.position = position;
        sprite.setPosition(position.x, position.y);
    }

    public static float getSpeed() {
        return speed;
    }

    public float getAngle() {
        return angle;
    }

    private void UpdateCoordinates(Touchpad touchPad) {

        Vector2 v = new Vector2(touchPad.getKnobPercentX(), touchPad.getKnobPercentY());
        if (!(Math.abs(v.x) < 0.3 && Math.abs(v.y) < 0.3)) {
            angle = v.angle();
        }
        sprite.setX(sprite.getX() + v.x * speed);
        sprite.setY(sprite.getY() + v.y * speed);
        sprite.setRotation(angle);
    }

    public void Update(TouchPad touchPad) {
        UpdateCoordinates(touchPad.GetTouchpad());
    }
}
