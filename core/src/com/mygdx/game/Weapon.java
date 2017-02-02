package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.Map;

public class Weapon extends Entity {

    float speed;
    int distance;
    Vector2 playerPos;

    Weapon() {
    }

    boolean IsOutsideOfDistance() {
        return (position.x > playerPos.x + distance || position.x < playerPos.x - distance) ||
                (position.y > playerPos.y + distance || position.y < playerPos.y - distance);
    }

    public void Update() {
        velocity.x = (float) Math.cos(angle * 0.017453f) * speed;
        velocity.y = (float) Math.sin(angle * 0.017453f) * speed;
        position.x += velocity.x;
        position.y += velocity.y;
        sprite.setRotation(angle);
        sprite.setX(position.x);
        sprite.setY(position.y);
        if (IsOutsideOfDistance()) {
            CreateExplosion(explosions.get(this.getClass().getSimpleName()));
            SetDead();
        }
    }
}

class Projectile extends Weapon {
    Projectile(Sprite sprite, Vector2 position, float angle) {
        this.playerPos = new Vector2(position);
        this.sprite = sprite;
        this.sprite.setOrigin(this.sprite.getWidth() / 2, this.sprite.getHeight() / 2);
        this.position = new Vector2(position);
        this.velocity = new Vector2();
        this.angle = angle;
        this.speed = 20;
        this.distance = 370;
    }
}

class Missile extends Weapon {
    Missile(Sprite sprite, Vector2 position, float angle) {
        this.sprite = sprite;
        this.sprite.setOrigin(this.sprite.getWidth() / 2, this.sprite.getHeight() / 2);
        this.playerPos = new Vector2(position);
        this.position = new Vector2(position);
        this.velocity = new Vector2();
        this.angle = angle;
        this.speed = 10;
        this.distance = 470;
    }

}

class SmartMissile extends Missile {
    SmartMissile(Sprite sprite, Vector2 position, float angle) {
        super(sprite, position, angle);
        this.position = position;
        velocity = new Vector2(5, 5);
    }

    @Override
    public void Update() {

    }
}
