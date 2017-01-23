package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//        config.width = Gdx.graphics.getWidth();
//        config.height = Gdx.graphics.getWidth();
        config.width = 1920;
        config.height = 1080;
        config.fullscreen = true;
        config.useGL30 = true;
        new LwjglApplication(new MyGdxGame(), config);
    }
}
