package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class TouchPad implements ApplicationListener {
    private OrthographicCamera camera;
    private Stage stage;
    //    private SpriteBatch batch;
    private Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;

    private Button button;
    private Button.ButtonStyle buttonStyle;
//    private Skin buttonSkin;


    @Override
    public void create() {
//        batch = new SpriteBatch();
        //Create camera
        float aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 10f * aspectRatio, 10f);

        touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", new Texture("images/touchBackground.png"));
        touchpadSkin.add("touchKnob", new Texture("images/touchKnob.png"));
        touchpadStyle = new Touchpad.TouchpadStyle();
        //Create Drawable's from TouchPad skin
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");
        //Apply the Drawables to the TouchPad Style
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        //Create new TouchPad with the created style
        touchpad = new Touchpad(10, touchpadStyle);
        //setBounds(x,y,width,height)
        touchpad.setBounds(100, 100, 200, 200);

        buttonStyle = new Button.ButtonStyle();
        buttonStyle.checked = touchBackground;
        buttonStyle.down = touchBackground;
        buttonStyle.up = touchBackground;
        buttonStyle.checkedOver = touchBackground;

        button = new Button(buttonStyle);
        button.setBounds(1600, 100, 200, 200);


        //Create a Stage and add TouchPad
        stage = new Stage();
        stage.addActor(touchpad);
        stage.addActor(button);



        Gdx.input.setInputProcessor(stage);

    }

    public boolean IsButtonPressed() {
        return button.isPressed();
    }

    @Override
    public void dispose() {

    }

    public Touchpad GetTouchpad() {
        return touchpad;
    }

    @Override
    public void render() {
//        Gdx.gl.glClearColor(0.294f, 0.294f, 0.294f, 1f);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        //Draw
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void resize(int width, int height) {
    }
}

