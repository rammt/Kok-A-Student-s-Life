package no.ntnu.tdt4240.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class ButtonComponent implements Component {
    private Button textButton;
    private InputListener inputListener;

    public ButtonComponent create(float width, float height, float x, float y, String text, Skin skin, InputListener inputListener) {

        textButton = new TextButton(text, skin);
        textButton.setSize(width,height);
        textButton.setPosition(x, y);
        textButton.addListener(inputListener);

        return this;
    }

    public Button getTextButton() {
        return textButton;
    }
}