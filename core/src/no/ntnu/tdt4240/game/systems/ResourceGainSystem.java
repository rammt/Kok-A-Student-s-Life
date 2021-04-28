package no.ntnu.tdt4240.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

import java.util.List;
import java.util.Map;

import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.components.ResourceGainerComponent;

public class ResourceGainSystem extends EntitySystem {

    private ImmutableArray<Entity> player;
    private ImmutableArray<Entity> gameGainers;

    private final ComponentMapper<PlayerComponent> pm = ComponentMapper.getFor(PlayerComponent.class);

    public void addedToEngine(Engine engine) {
        player = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
        gameGainers = engine.getEntitiesFor(Family.all(ResourceGainerComponent.class).get());
    }

    public void update(float deltaTime) {
        addResourcesByTime(deltaTime);
    }

    private float calculateNewResourceGain(float gainIncrement) {
        float addedResources = 0;
        PlayerComponent pc = pm.get(player.get(0));

        List<Map<String, Object>> playerGainers = pc.getResourceGainers();


        for (Map<String, Object> resourceGainer : playerGainers) {
            int rg_amount = (int) resourceGainer.get("amount");
            String playerGainerId = (String) resourceGainer.get("id");
            for (Entity gameGainer : gameGainers) {
                ResourceGainerComponent gameGainer_rgc = gameGainer.getComponent(ResourceGainerComponent.class);
                if (gameGainer_rgc.getId().equals(playerGainerId)) {
                    addedResources += (gameGainer_rgc.getGainPerSecond() * gainIncrement) * rg_amount;
                }
            }
        }

        return addedResources;
    }

    public float getResourceGainPerSecond() {
        float addedResources = 0;
        PlayerComponent pc = pm.get(player.get(0));

        List<Map<String, Object>> playerGainers = pc.getResourceGainers();

        for (Map<String, Object> resourceGainer : playerGainers) {
            int rg_amount = (int) resourceGainer.get("amount");
            String playerGainerId = (String) resourceGainer.get("id");
            for (Entity gameGainer : gameGainers) {
                ResourceGainerComponent gameGainer_rgc = gameGainer.getComponent(ResourceGainerComponent.class);
                if (gameGainer_rgc.getId().equals(playerGainerId)) {
                    addedResources += gameGainer_rgc.getGainPerSecond() * rg_amount;
                }
            }
        }

        return addedResources;

    }


    public float addResourcesByTime(float time) {
        PlayerComponent pc = pm.get(player.get(0));
        float newResources = calculateNewResourceGain(time);
        pc.setKokCount(pc.getKokCount() + newResources);
        return newResources;
    }

    public int countResourceGainers(ResourceGainerComponent rg) {

        int resourceGainerCounter = 0;
        PlayerComponent pc = pm.get(player.get(0));
        List<Map<String, Object>> playerGainers = pc.getResourceGainers();

        for (Map<String, Object> playerGainer : playerGainers) {
            int rg_amount = (int) playerGainer.get("amount");
            if (playerGainer.get("id").equals(rg.getId())) {
                resourceGainerCounter += rg_amount;
            }
        }

        return resourceGainerCounter;
    }

}
