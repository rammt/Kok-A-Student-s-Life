package no.ntnu.tdt4240.game.components;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.audio.Sound;

import com.badlogic.ashley.core.Component;

public class SoundComponent implements Component {
        private Sound sound;

        public SoundComponent create(String path){
            this.sound = Gdx.audio.newSound(Gdx.files.internal(path));
            return this;
        }

        public void startSound(){
                sound.play();
        }

        public void setSound(String path) {
            this.sound = Gdx.audio.newSound(Gdx.files.internal(path));
        }

        public Sound getSound() {
            return sound;
        }
}
