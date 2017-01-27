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
    protected double ACCELERATION;
    protected double DECELERATION;
    protected boolean isAggro;
    protected int aggroDistance;
    protected int min_distance;

    protected void CheckAggro(double distance) {
        if (!isAggro && (distance < aggroDistance || health != MAX_HEALTH)) {
            isAggro = true;
        }
    }

    protected void FlyTowardPlayer(Player player) {
        Vector2 playerCoordinates = new Vector2(player.GetSprite().getX(), player.GetSprite().getY());
        double distance = Math.sqrt((playerCoordinates.x - sprite.getX()) * (playerCoordinates.x - sprite.getX()) +
                (playerCoordinates.y - sprite.getY()) * (playerCoordinates.y - sprite.getY()));
        CheckAggro(distance);
        if (!isAggro) {
            rotation =
                    (Math.atan2(playerCoordinates.y - sprite.getY(), playerCoordinates.x - sprite.getX())) *
                            180 / Math.PI;
            if (distance > min_distance) {
//                state = MOVE;
                velocity.x += (ACCELERATION * (playerCoordinates.x - sprite.getX()) / distance); //TODO: add time
                velocity.y += (ACCELERATION * (playerCoordinates.y - sprite.getY()) / distance);
            } else {
//                state = SLIDE;
                velocity.x *= DECELERATION;
                velocity.y *= DECELERATION;
            }
            sprite.setRotation((float) rotation);
//            sprite.getX() += velocity.x;
//            sprite.getY() += velocity.y;
            sprite.setX(sprite.getX() + velocity.x);
            sprite.setY(sprite.getY() + velocity.y);
        }
    }

    protected void Update(Player player) {
        FlyTowardPlayer(player);
    }

}
