package no.ntnu.tdt4240.game.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import no.ntnu.tdt4240.game.StudentLifeGame;
import no.ntnu.tdt4240.game.components.ResourceGainerComponent;
import no.ntnu.tdt4240.game.guiElements.ButtonElement;
import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.guiElements.NavbarElement;

public class GameScreen implements Screen{

	private TextButton.TextButtonStyle textButtonStyleDOWN;
	private TextButton.TextButtonStyle textButtonStyleUP;
    private ButtonElement copyButton, pasteButton, deliverButton, copyPasteDeliverButton;
    private boolean copied;
    private boolean pasted;
    private boolean delivered;
    private boolean upgraded = false;
    private int SCREENWIDTH, SCREENHEIGHT,BUTTONHEIGHTGUI,BUTTONWIDTHGUI;
	private NavbarElement navbar;
	private Entity player;
	private PlayerComponent pc;

	final StudentLifeGame game;
	public GameScreen(final StudentLifeGame game) {

		this.game = game;
		this.game.getStage().clear();
		SCREENHEIGHT = Gdx.graphics.getHeight();
		SCREENWIDTH = Gdx.graphics.getWidth();
		BUTTONHEIGHTGUI = SCREENHEIGHT/8;
		BUTTONWIDTHGUI = SCREENWIDTH/4;
		player = game.getPlayer();
		pc = player.getComponent(PlayerComponent.class);
		copied = false;
		pasted = false;
		delivered = false;

		//TODO æsj fiks dette her
		float width = Gdx.graphics.getWidth()/2f;
		float height = Gdx.graphics.getHeight()/8f;
		float x = Gdx.graphics.getWidth()/2f - width/2;
		float copyY = Gdx.graphics.getHeight()/2f + height/2;
		float pasteY = Gdx.graphics.getHeight()/2f - height/2;
		float deliverY = Gdx.graphics.getHeight()/2f - height*1.5f;
		//kode for knappene pluss logikk når knappen trykkes

		copyButton = new ButtonElement(
			x, copyY,
			"COPY", game.getSkin(), new InputListener(){
		@Override
		public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
			if(!copied){
				copied = true;
				copyButton.disableButton(copied);
				pc.setClickCount(pc.getClickCount()+1);
			}
			return true;
			}
		});

		pasteButton = new ButtonElement(
			x, pasteY,
			"PASTE", game.getSkin(), new InputListener(){
		@Override
		public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
			if(copied && !pasted){
				pasted = true;
				pasteButton.disableButton(pasted);
				pc.setClickCount(pc.getClickCount()+1);
			}
			return true;
			}
		});

		deliverButton = new ButtonElement(
			x, deliverY,
			"DELIVER", game.getSkin(), new InputListener(){
		@Override
		public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
			if(copied && pasted && !delivered){
				copied=false;
				pasted=false;
				pc.setClickCount(pc.getClickCount()+1);
				pc.setKokCount(pc.getKokCount() + 1 + pc.getClickValue()*getGainpersecond());
				copyButton.disableButton(copied);
				pasteButton.disableButton(pasted);
			}
			return true;
			}
		});


		copyPasteDeliverButton = new ButtonElement(x, pasteY, "KOK", game.getSkin(), new InputListener() {
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
			pc.setKokCount(pc.getKokCount() + 1 + pc.getClickValue()*getGainpersecond());
			pc.setClickCount(pc.getClickCount() + 1);
			return true;
			}
		});

		//legger til aktors

		navbar = new NavbarElement(game, BUTTONWIDTHGUI, BUTTONHEIGHTGUI, SCREENWIDTH );
		for(Button btn : navbar.getActors()){
			game.getStage().addActor(btn);
		}

		if(pc.getCombinedButtons()){
			game.getStage().addActor(copyPasteDeliverButton);
		} else {
			game.getStage().addActor(copyButton);
			game.getStage().addActor(pasteButton);
			game.getStage().addActor(deliverButton);
		}
	}

	@Override
	public void render(float delta) {

        ScreenUtils.clear(57/255f, 72f/255f, 85f/255f, 1);

		game.getStage().act();
		game.getStage().draw();
		game.getBatch().begin();
		game.getFont().draw(
			game.getBatch(),
			"Kok : " + pc.getKokCount(),
			Gdx.graphics.getWidth()/3f,
			Gdx.graphics.getHeight()/1.2f
		);
		game.getFont().draw(
				game.getBatch(),
				getGainpersecond() + " kok/s",
				Gdx.graphics.getWidth()/3f,
				Gdx.graphics.getHeight()/1.3f
		);
		game.getBatch().end();

		game.getEngine().update(Gdx.graphics.getDeltaTime());

	}

	public float getGainpersecond(){
		float temp = 0f;
		for(ResourceGainerComponent rg : pc.getResourceGainers()){
			temp += rg.getGainPerSecond();
		}
		return temp;
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
    
}
