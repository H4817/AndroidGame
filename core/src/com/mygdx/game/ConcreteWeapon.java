package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

class ConcreteWeapon extends AbstractWeapon {

    ConcreteWeapon(String name, Sprite sprite, Vector2 position, float angle) {

        if (name.equals("Projectile")) {
            weapon = new Projectile(sprite, position, angle);
        } else if (name.equals("Missile")) {
            weapon = new Missile(sprite, position, angle);
        } else if (name.equals("SmartMissile")) {
            weapon = new SmartMissile(sprite, position, angle);
        }

    }

    ConcreteWeapon() { }

}

final class Projectile extends ConcreteWeapon {
    Projectile(Sprite sprite, Vector2 position, float angle) {
        this.playerPos = new Vector2(position);
        this.sprite = sprite;
        this.sprite.setOrigin(this.sprite.getWidth() / 2, this.sprite.getHeight() / 2);
        this.position = new Vector2(position);
        this.velocity = new Vector2();
        this.angle = angle;
        this.speed = 20;
        this.distance = 1700;
    }
}

class Missile extends ConcreteWeapon {
    Missile(Sprite sprite, Vector2 position, float angle) {
        this.sprite = sprite;
        this.sprite.setOrigin(this.sprite.getWidth() / 2, this.sprite.getHeight() / 2);
        this.playerPos = new Vector2(position);
        this.position = new Vector2(position);
        this.velocity = new Vector2();
        this.angle = angle;
        this.speed = 10;
        this.distance = 500;
    }

}

final class SmartMissile extends Missile {
    SmartMissile(Sprite sprite, Vector2 position, float angle) {
        super(sprite, position, angle);
        this.position = position;
        velocity = new Vector2(5, 5);
    }

    @Override
    public void Update() {

    }
}
