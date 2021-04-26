package no.ntnu.tdt4240.game.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Arrays;

import no.ntnu.tdt4240.game.StudentLifeGame;
import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.guiElements.ButtonElement;
import no.ntnu.tdt4240.game.guiElements.NavbarElement;
import no.ntnu.tdt4240.game.systems.AudioSystem;

public class UpgradeShopScreen implements Screen {

    final StudentLifeGame game;

    final float BUTTONHEIGHTGUI;
    final float BUTTONWIDTHGUI;
    final int SCREENHEIGTH;
    final int SCREENWIDTH;

    final ButtonElement combineButton, increaseClickValueButton;
    private PlayerComponent pc;
    private AudioSystem as;
    private NavbarElement navbar;
    private float originalClickValuePrice;

    private float clickValuePrice;

    private int clickValueCounter;

    public UpgradeShopScreen(final StudentLifeGame game){

        this.game = game;
        game.getStage().clear();

        pc = game.getPlayer().getComponent(PlayerComponent.class);
        as = game.getEngine().getSystem(AudioSystem.class);
        as.setSound("music/combine_buttons.mp3");

        SCREENHEIGTH = Gdx.graphics.getHeight();
        SCREENWIDTH = Gdx.graphics.getWidth();
        BUTTONHEIGHTGUI = SCREENHEIGTH/8f;
        BUTTONWIDTHGUI = SCREENWIDTH/4f;

        originalClickValuePrice = 200;
        clickValueCounter = pc.getClickValueCounter();

        combineButton = new ButtonElement(
                BUTTONWIDTHGUI*3, BUTTONHEIGHTGUI,
                SCREENWIDTH/2f-BUTTONWIDTHGUI*3/2, SCREENHEIGTH*5/8f,
                "Combine Buttons: 100 000,-", game.getSkin(),
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                        if(pc.getKokCount() >= 100000f && !pc.getCombinedButtons()) {
                            pc.setKokCount(pc.getKokCount()-100000);
                            pc.setCombinedButtons(true);
                            as.playSound();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    long time = System.currentTimeMillis();
                                    while (System.currentTimeMillis() < time + 4000){}
                                    Gdx.app.postRunnable(new Runnable() {
                                        @Override
                                        public void run() {
                                            game.setScreen(new GameScreen(game));
                                        }
                                    });
                                }
                            }).start();
                        }
                        return true;
                    }

                }
        );

        clickValuePrice = (float) (originalClickValuePrice * Math.pow(1,10* clickValueCounter));
        String description = "Increase Click Value by 5%";
        String price = "Price: " + clickValuePrice + " kok";
        //String kokPerSec = "Kok gain: /s";
        ArrayList<String> buttonText = new ArrayList<>(Arrays.asList(description, price));
        increaseClickValueButton = new ButtonElement(
                BUTTONWIDTHGUI*3, BUTTONHEIGHTGUI,
                SCREENWIDTH/2f-BUTTONWIDTHGUI*3/2, SCREENHEIGTH*5/8f-BUTTONHEIGHTGUI - 50,
                buttonText, game.getSkin(),
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if(pc.getKokCount() >= clickValuePrice){
                            pc.setKokCount(pc.getKokCount()-clickValuePrice);
                            pc.setClickValue(pc.getClickValue()+0.05f);
                            pc.increaseClickValueCounter();
                            clickValueCounter++;
                            //clickValuePrice = (float) (originalClickValuePrice * Math.pow(1,10* pc.getClickValueCounter()));
                            System.out.println(pc.getClickValue());
                            System.out.println(clickValuePrice);

                            as.playSound();
                        }
                        return true;
                    }

                }
        );

        combineButton.disableButton(pc.getCombinedButtons());


        game.getStage().addActor(combineButton);
        game.getStage().addActor(increaseClickValueButton);

        navbar = new NavbarElement(game, BUTTONWIDTHGUI, BUTTONHEIGHTGUI, SCREENWIDTH );
        for(Button btn : navbar.getActors()){
            game.getStage().addActor(btn);
        }
    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(57/255f, 72f/255f, 85f/255f, 1);

        game.getStage().act();
        game.getStage().draw();

        game.getEngine().update(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
