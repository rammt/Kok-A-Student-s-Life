package no.ntnu.tdt4240.game;

import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.screens.StartScreen;
import no.ntnu.tdt4240.game.screens.TutorialScreen;

public class StudentLifeGame extends Game {
	public Firebase firebase;

	public StudentLifeGame(Firebase firebase) {
		this.firebase = firebase;
	}

	private SpriteBatch batch;
	private BitmapFont font;
	private Stage stage;
	private Skin skin;
	private TextureAtlas textureAtlas;
    private boolean showTutorial = true;

    private ECSengine engine;
    private ShapeRenderer shapeRenderer;

    // Show tutorial on first launch
    public boolean showTutorialFirstLaunch() {
        if (showTutorial) {
            this.showTutorial = false;
            return true;
        }
        return false;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("skin/font-big-export.fnt"));
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        shapeRenderer = new ShapeRenderer();

        engine = new ECSengine(shapeRenderer, font, batch, stage, firebase);

        this.setScreen(new StartScreen(this, null));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        batch.dispose();
        font.dispose();
        stage.dispose();
        skin.dispose();
        textureAtlas.dispose();
    }

    public SpriteBatch getBatch(){
        return batch;
    }
    public BitmapFont getFont(){
        return font;
    }
    public Stage getStage(){
        return stage;
    }
    public Skin getSkin(){
        return skin;
    }
    public TextureAtlas getTextureAtlas(){
        return textureAtlas;
    }
    public PooledEngine getEngine() {
        return engine.getEngine();
    }

    public Entity getPlayer() {
        ImmutableArray<Entity> players = getEngine().getEntitiesFor(Family.all(PlayerComponent.class).get());
        return players.get(0);
    }

    public String formatMillions(double num){
        String unit = "";
        if(num >= 1000000000000000f){
            num = num/1000000000000000f;
            unit="Quadrillion";
        }
        else if(num >= 1000000000000f){
            num = num/1000000000000f;
            unit="Trillion";
        }
        else if(num>=1000000000f){
            num = num/1000000000;
            unit = "Billion";
        }
        else if(num >= 1000000f){
            num = num/1000000;
            unit = "Million";
        }
		/*else if(num >= 1000){
			num = num/1000;
			unit = "K";
		}*/
        else {
            return String.format("%.0f", num) + " " + unit;
        }
        return String.format("%.3f", num) + " " + unit;
    }
}
