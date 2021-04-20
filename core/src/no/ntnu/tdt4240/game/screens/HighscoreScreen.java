package no.ntnu.tdt4240.game.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.badlogic.gdx.utils.async.AsyncResult;
import com.badlogic.gdx.utils.async.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import no.ntnu.tdt4240.game.StudentLifeGame;
import no.ntnu.tdt4240.game.components.HighscoreComponent;
import no.ntnu.tdt4240.game.components.TextFieldComponent;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import no.ntnu.tdt4240.game.StudentLifeGame;
import no.ntnu.tdt4240.game.components.TextFieldComponent;
import no.ntnu.tdt4240.game.guiElements.ButtonElement;

public class HighscoreScreen implements Screen {

    final StudentLifeGame game;
    private Button gameButton, statButton;

    final float BUTTONHEIGHTGUI;
    final float BUTTONWIDTHGUI;
    final int SCREENHEIGTH;
    final int SCREENWIDTH;
    final int buttonPadding;

    private List<Map<String,Object>> hl = new ArrayList<Map<String,Object>>();

    private AsyncExecutor executor = new AsyncExecutor(4);
    private AsyncResult<Void> task;

    private boolean updated = false;

    String[] users = {"","","","",""};
    String[] stats = {"0", "0","0","0","0"};

    private Table table;


    public HighscoreScreen(final StudentLifeGame game) {
        this.game = game;

        game.getStage().clear();

        SCREENHEIGTH = Gdx.graphics.getHeight();
        SCREENWIDTH = Gdx.graphics.getWidth();
        BUTTONHEIGHTGUI = SCREENHEIGTH/8f;
        BUTTONWIDTHGUI = SCREENWIDTH/4f;
        buttonPadding = 10;

        Label title = new TextFieldComponent().create(null, "Highscores", game.getSkin(), 6, false).getTextFieldComponent();
        title.setPosition(Gdx.graphics.getWidth()/2f - title.getWidth()/2,
                Gdx.graphics.getHeight()/1.2f);


        task = executor.submit(new AsyncTask<Void>() {
            @Override
            public Void call() throws Exception {
                hl = game.firebase.getHighscore();
                return null;
            }
        });

        table = new Table();
        table.setFillParent(true);
        table.defaults().minWidth(400).minHeight(200).pad(1);
        table.add(new TextFieldComponent().create(null, "Rank", game.getSkin(), 4, true).getTextFieldComponent()).width(200);
        table.add(new TextFieldComponent().create(null, "User", game.getSkin(), 4, true).getTextFieldComponent());
        table.add(new TextFieldComponent().create(null, "Kok", game.getSkin(), 4, true).getTextFieldComponent());
        table.row();


        statButton = new ButtonElement(
                BUTTONWIDTHGUI,BUTTONHEIGHTGUI,
                (SCREENWIDTH/2f)-SCREENWIDTH/4f/2, 50,
                "BACK", game.getSkin(), new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new StatScreen(game));
                return true;
            }
        });

        game.getStage().addActor(title);
        game.getStage().addActor(table);
        game.getStage().addActor(statButton);
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(57/255f, 72f/255f, 85f/255f, 1);

        if(task.isDone() && !updated && !hl.isEmpty()){
            for(int i = 0; i<users.length; i++){
                users[i] = String.valueOf(hl.get(i).get("name"));
                stats[i] = String.valueOf(hl.get(i).get("kokCount"));
            }
            users[0] = String.valueOf(hl.get(0).get("name"));
            for(int i = 0; i < users.length; i++) {
                table.add(new TextFieldComponent().create(null, String.valueOf(i+1), game.getSkin(), 3, true).getTextFieldComponent()).width(200);
                table.add(new TextFieldComponent().create(null, users[i], game.getSkin(), 3, true).getTextFieldComponent());
                table.add(new TextFieldComponent().create(null, stats[i] , game.getSkin(), 3, true).getTextFieldComponent());
                table.row();
            }
            updated = true;
        }

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

