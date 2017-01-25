package com.mygdx.game;


import com.badlogic.gdx.math.Vector2;

/**
 * Created by nikolaj on 1/25/17.
 */

public abstract class AbstractEnemy extends Entity {
    protected int health;
    protected int MAX_HEALTH;
    protected double rotation;
    protected int shield;
    protected Vector2 velocity;
    protected int ACCELERATION;
    protected int DECELERATION;
    protected Vector2 playerCoordinates;
    protected boolean isAggro;
    protected int aggroDistance;
    protected int min_distance;

    protected void CheckAggro(double distance) {
        if (!isAggro && (distance < aggroDistance || health != MAX_HEALTH)) {
            isAggro = true;
        }
    }

    protected void FlyTowardPlayer() {
        double distance = Math.sqrt((playerCoordinates.x - position.x) * (playerCoordinates.x - position.x) +
                (playerCoordinates.y - position.y) * (playerCoordinates.y - position.y));
        CheckAggro(distance);
        if (isAggro) {
            rotation =
                    (Math.atan2(playerCoordinates.y - position.y, playerCoordinates.x - position.x)) *
                            180 / Math.PI;
            if (distance > min_distance) {
//                state = MOVE;
                velocity.x += (ACCELERATION * (playerCoordinates.x - position.x) / distance); //TODO: add time
                velocity.y += (ACCELERATION * (playerCoordinates.y - position.y) / distance);
            } else {
//                state = SLIDE;
                velocity.x *= DECELERATION;
                velocity.y *= DECELERATION;
            }
            sprite.setRotation((float) rotation);
            position.x += velocity.x;
            position.y += velocity.y;
        }
    }

}
