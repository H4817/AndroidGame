package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

public abstract class AbstractWeapon extends Entity {
    float speed;
    float cooldown;
    int distance;
    Vector2 playerPos;
    AbstractWeapon weapon;

    public float getCooldown() {
        return cooldown;
    }

    boolean IsOutsideOfDistance() {
        return (position.x > playerPos.x + distance || position.x < playerPos.x - distance) ||
                (position.y > playerPos.y + distance || position.y < playerPos.y - distance);
    }

//    public abstract void MakeShot();

    public void Update() {
        //checkCooldown();
        //
        if (!IsOutsideOfDistance()) {
            velocity.x = (float) Math.cos(angle * 0.017453f) * speed;
            velocity.y = (float) Math.sin(angle * 0.017453f) * speed;
            position.x += velocity.x;
            position.y += velocity.y;
            sprite.setRotation(angle);
            sprite.setX(position.x);
            sprite.setY(position.y);
        } else {
            CreateExplosion(EXPLOSIONS.get(this.getClass().getSimpleName()));
            SetDead();

        }
    }
}
