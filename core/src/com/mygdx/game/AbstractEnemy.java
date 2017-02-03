package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

abstract class AbstractEnemy extends Entity {
    int health;
    int MAX_HEALTH;
    int shield;
    double ACCELERATION;
    double DECELERATION;
    boolean isAggro;
    int aggroDistance;
    int min_distance;
    Sprite withoutThrust;
    Sprite withThrust;
    AbstractEnemy enemy;

    private void CheckAggro(double distance) {
        if (!isAggro && (distance < aggroDistance || health != MAX_HEALTH)) {
            isAggro = true;
        }
    }

    private void FlyTowardPlayer(Vector2 playerCoordinates) {
        double distance = Math.sqrt((playerCoordinates.x - position.x) * (playerCoordinates.x - position.x) +
                (playerCoordinates.y - position.y) * (playerCoordinates.y - position.y));
        CheckAggro(distance);
        if (isAggro) {
            angle = (float) ((Math.atan2(playerCoordinates.y - position.y, playerCoordinates.x - position.x)) * 180 / Math.PI);
            if (distance > min_distance) {
                sprite.set(withThrust);
                velocity.x += (ACCELERATION * (playerCoordinates.x - position.x) / distance);
                velocity.y += (ACCELERATION * (playerCoordinates.y - position.y) / distance);
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
            sprite.setRotation(angle);
            sprite.setX(position.x);
            sprite.setY(position.y);
        }
    }

    void Update(Vector2 playerPosition) {
        FlyTowardPlayer(playerPosition);
    }

}
