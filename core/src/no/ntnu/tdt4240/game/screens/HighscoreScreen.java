package no.ntnu.tdt4240.game.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import no.ntnu.tdt4240.game.StudentLifeGame;
import no.ntnu.tdt4240.game.components.TextFieldComponent;

public class HighscoreScreen implements Screen {

    final StudentLifeGame game;
    private Button gameButton;


    public HighscoreScreen(final StudentLifeGame game) {
        this.game = game;

        game.getStage().clear();

        Label title = new TextFieldComponent().create(null, "Highscores", game.getSkin(), 6, false).getTextFieldComponent();
        title.setPosition(Gdx.graphics.getWidth()/3f,
                Gdx.graphics.getHeight()/1.2f);

        String[] users = {"holde", "flatner"};
        String[] stats = {"2", "4"};

        Table table = new Table();
        table.setFillParent(true);
        table.defaults().minWidth(400).minHeight(100).pad(1);
        table.add(new TextFieldComponent().create(null, "User", game.getSkin(), 4, true).getTextFieldComponent());
        table.add(new TextFieldComponent().create(null, "Clicks", game.getSkin(), 4, true).getTextFieldComponent());
        table.add(new TextFieldComponent().create(null, "Kok", game.getSkin(), 4, true).getTextFieldComponent());
        table.row();

        for(int i = 0; i < users.length; i++) {
            table.add(new TextFieldComponent().create(null, users[i], game.getSkin(), 3, true).getTextFieldComponent());
            table.add(new TextFieldComponent().create(null, stats[i], game.getSkin(), 3, true).getTextFieldComponent());
            table.add(new TextFieldComponent().create(null, "23" , game.getSkin(), 3, true).getTextFieldComponent());
            table.row();
        }

        gameButton = new TextButton("back",game.getSkin());
        gameButton.setSize(Gdx.graphics.getWidth()/2f,Gdx.graphics.getHeight()/8f);
        gameButton.setPosition(
                Gdx.graphics.getWidth()/2f - gameButton.getWidth()/2,
                0);
        gameButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                game.setScreen(new StatScreen(game));

                return true;
            }
        });

        game.getStage().addActor(title);
        game.getStage().addActor(table);
        game.getStage().addActor(gameButton);
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0.2f, 1);

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

