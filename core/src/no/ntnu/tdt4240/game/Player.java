package no.ntnu.tdt4240.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.Date;

public class Player {
    private float kokCount;
    private String name;

    private int amtResourceGainers;

    private final Preferences resourcePrefs;

    private long lastSave;

    public Player() {
        this.resourcePrefs = Gdx.app.getPreferences("resourcePrefs");
        this.lastSave =  new Date(resourcePrefs.getLong("lastSave")).getTime();
        this.amtResourceGainers = resourcePrefs.getInteger("amtResourceGainers", 0);
        this.kokCount = resourcePrefs.getFloat("kokCount", 0);

        /*this.name = resourcePrefs.getString("name", "NoNameFound");*/

    }

    public float getKokCount() {
        return kokCount;
    }

    public void setKokCount(float kokCount) {
        this.kokCount = kokCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmtResourceGainers() {
        return amtResourceGainers;
    }

    public void setAmtResourceGainers(int amtResourceGainers) {
        this.amtResourceGainers = amtResourceGainers;
    }

    public long getLastSave() {
        return lastSave;
    }

    public void setLastSave(long lastSave) {
        this.lastSave = lastSave;
    }

    public void saveOffline() {
        System.out.println("Saving game offline");
        resourcePrefs.putLong("lastSave", new Date().getTime());
        resourcePrefs.putInteger("amtResourceGainers", amtResourceGainers);
        resourcePrefs.putFloat("kokCount", kokCount);
        resourcePrefs.flush();
    }
}
