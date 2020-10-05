package com.retrobot.bot.state;

import com.retrobot.bot.async.RetroTaskQueue;
import com.retrobot.bot.model.dofus.RetroDofusMap;
import com.retrobot.bot.model.dofus.RetroRessourceCell;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@Getter
@Setter
public class MapState {

    private RetroDofusMap currentMap;
    private Map<Integer, RetroRessourceCell> availableRessources = new ConcurrentHashMap<>();
    private Map<Integer, RetroRessourceCell> unavailableRessources = new ConcurrentHashMap<>();
    private Map<Integer, Integer> monsterPositions = new ConcurrentHashMap<>();

    private final RetroTaskQueue retroTaskQueue;

    public MapState(RetroTaskQueue retroTaskQueue) {
        this.retroTaskQueue = retroTaskQueue;
    }

    public void resetMapState() {
        this.availableRessources.clear();
        this.unavailableRessources.clear();
        monsterPositions.clear();
        retroTaskQueue.removeMapTask(currentMap);
    }

}
