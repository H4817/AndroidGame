package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Weapon extends Entity {

    float speed;
    int distance;
    Vector2 playerPos;
//    Weapon(String name) {
//        if (name.equals("Projectile")) {
//
//        }
//    }

    Weapon() {
    }

    boolean IsOutsideOfDistance() {
//        System.out.print("position: ");
//        System.out.println(position);
//        System.out.print("player position: ");
//        System.out.println(playerPos);
//        System.out.println("====================");
        return (Math.abs(position.x) > (Math.abs(playerPos.x) + distance)) ||
                (Math.abs(position.y) > (Math.abs(playerPos.y) + distance));
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
            SetDead();
        }
    }
}

class Projectile extends Weapon {
    Projectile(Sprite sprite, Vector2 position, float angle) {
        this.playerPos = new Vector2(position);
        this.sprite = sprite;
        this.sprite.setOrigin(this.sprite.getWidth() / 2, this.sprite.getHeight() / 2);
        this.position = position;
        this.velocity = new Vector2();
        this.angle = angle;
        this.speed = 20;
        this.distance = 1700;
    }
}

class Missile extends Weapon {
    //    Explosion explosion;
    Missile(Sprite sprite, Vector2 position, float angle) {
        this.position = position;
        velocity = new Vector2(10, 10);
    }

    Missile() {
    }
}

class SmartMissile extends Missile {
    SmartMissile(Vector2 position) {
//        super(position);
        this.position = position;
        velocity = new Vector2(5, 5);
    }

    @Override
    public void Update() {

    }
}
