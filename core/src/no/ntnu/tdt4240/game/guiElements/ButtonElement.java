package no.ntnu.tdt4240.game.guiElements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class ButtonElement extends TextButton {
    private Boolean checked = false;

    //public ButtonElement(float width, float height, float x, float y, String text, Skin skin, final TextButton.TextButtonStyle style, InputListener listener) {
    public ButtonElement(float width, float height, float x, float y, String text, Skin skin, InputListener listener) {

        super(text, skin);
        super.setSize(width,height);
        super.setPosition(x, y);
        super.addListener(listener);
    }

    public ButtonElement(float x, float y, String text, Skin skin, InputListener listener) {
        super(text, skin);
        super.setPosition(x, y);
        super.addListener(listener);

        // Default values
        float width =  Gdx.graphics.getWidth()/2f;
        float height = Gdx.graphics.getHeight()/8f;
        super.setSize(width, height);
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
