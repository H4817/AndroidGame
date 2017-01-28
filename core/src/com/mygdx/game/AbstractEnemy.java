package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by nikolaj on 1/25/17.
 */

abstract class AbstractEnemy extends Entity {
    int health;
    int MAX_HEALTH;
    double rotation;
    int shield;
    boolean isDead;
    Vector2 velocity;
    double ACCELERATION;
    double DECELERATION;
    boolean isAggro;
    int aggroDistance;
    int min_distance;
    Sprite withoutThrust;
    Sprite withThrust;

    private void CheckAggro(double distance) {
        if (!isAggro && (distance < aggroDistance || health != MAX_HEALTH)) {
            isAggro = true;
        }
    }

    private void FlyTowardPlayer(Vector2 playerCoordinates) {
        double distance = Math.sqrt((playerCoordinates.x - position.x) * (playerCoordinates.x - position.x) +
                (playerCoordinates.y - position.y) * (playerCoordinates.y - position.y));
        CheckAggro(distance);
        if (!isAggro) {
            rotation =
                    (Math.atan2(playerCoordinates.y - position.y, playerCoordinates.x - position.x)) *
                            180 / Math.PI;
            if (distance > min_distance) {
                sprite.set(withThrust);
                velocity.x += (ACCELERATION * (playerCoordinates.x - position.x) / distance); //TODO: add time
                velocity.y += (ACCELERATION * (playerCoordinates.y - position.y) / distance);
            } else {
                velocity.x *= DECELERATION;
                velocity.y *= DECELERATION;
                sprite.set(withoutThrust);
            }
            position.x += velocity.x;
            position.y += velocity.y;
            sprite.setRotation((float) rotation);
            sprite.setX(position.x);
            sprite.setY(position.y);
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    void Update(Vector2 playerPosition) {
        FlyTowardPlayer(playerPosition);
    }

}
