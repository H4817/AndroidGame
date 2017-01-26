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
        double distance = Math.sqrt((player.GetPosition().x - position.x) * (player.GetPosition().x - position.x) +
                (player.GetPosition().y - position.y) * (player.GetPosition().y - position.y));
        CheckAggro(distance);
        if (!isAggro) {
            rotation =
                    (Math.atan2(player.GetPosition().y - position.y, player.GetPosition().x - position.x)) *
                            180 / Math.PI;
            if (distance > min_distance) {
//                state = MOVE;
                velocity.x += (ACCELERATION * (player.GetPosition().x - position.x) / distance); //TODO: add time
                velocity.y += (ACCELERATION * (player.GetPosition().y - position.y) / distance);
            } else {
//                state = SLIDE;
                velocity.x *= DECELERATION;
                velocity.y *= DECELERATION;
            }
            sprite.setRotation((float) rotation);
            position.x += velocity.x;
            position.y += velocity.y;
            sprite.setX(position.x);
            sprite.setY(position.y);
        }
    }

    protected void Update(Player player) {
        FlyTowardPlayer(player);
    }

}
