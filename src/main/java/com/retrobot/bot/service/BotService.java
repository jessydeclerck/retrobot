package com.retrobot.bot.service;

import com.retrobot.bot.model.dofus.RetroDofusMap;
import com.retrobot.bot.model.dofus.RetroRessourceCell;
import com.retrobot.bot.state.CharacterState;
import com.retrobot.bot.state.MapState;
import com.retrobot.scriptloader.model.ScriptPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BotService {

    private final MapState mapState;

    private final CharacterState characterState;

    private final ScriptPath scriptPath;

    private final TaskService taskService;

    private final DeplacementService deplacementService;

    public BotService(MapState mapState, CharacterState characterState, ScriptPath scriptPath, TaskService taskService, DeplacementService deplacementService) {
        this.mapState = mapState;
        this.characterState = characterState;
        this.scriptPath = scriptPath;
        this.taskService = taskService;
        this.deplacementService = deplacementService;
    }

    public void setFighting(boolean fighting) {
        if (fighting) {
            deplacementService.stopRecolte();
        }
        characterState.setFighting(fighting);
    }

    public void addAvailableRessources(List<RetroRessourceCell> ressourcesCells) {
        ressourcesCells.forEach(ressourceCell -> {
            if (mapState.getAvailableRessources().containsKey(ressourceCell.id()) || !scriptPath.getRessourcesToGather().contains(ressourceCell.getIdRessource())) {
                return;
            }
            mapState.getAvailableRessources().put(ressourceCell.id(), ressourceCell);
        });
    }

    public void setUnavailableRessource(int cellId) {
        RetroRessourceCell unavailableRessourceCell = mapState.getAvailableRessources().get(cellId);
        if (unavailableRessourceCell == null) return;
        mapState.getAvailableRessources().remove(cellId);
        mapState.getUnavailableRessources().put(cellId, unavailableRessourceCell);
        log.debug("Ressource indisponible : {}", unavailableRessourceCell.id());
        logAvailableRessources();
        taskService.removeQueueTask(unavailableRessourceCell);
    }

    public void setAvailableRessource(int cellId) {
        RetroRessourceCell availableRessourceCell = mapState.getUnavailableRessources().get(cellId);
        if (availableRessourceCell == null) return;
        mapState.getUnavailableRessources().remove(cellId);
        mapState.getUnavailableRessources().put(cellId, availableRessourceCell);
        //log.info("Ressource disponible : {}, {}", availableRessourceCell.getWindowRelativeX(), availableRessourceCell.getWindowRelativeY());
        taskService.queueTaskRecolte(availableRessourceCell);
    }

    private void logAvailableRessources() {
        this.mapState.getAvailableRessources().keySet().forEach(key -> {
            RetroRessourceCell ressourceCell = mapState.getAvailableRessources().get(key);
            log.debug("Ressource disponible : {}", ressourceCell.id());
        });
    }

    public void setCurrentMap(RetroDofusMap newMap) {
        if (mapState.getCurrentMap() == null) {
            mapState.setCurrentMap(newMap);
            return;
        }
        if (mapState.getCurrentMap() != newMap) {
            mapState.getCurrentMap().getTriggers().stream().filter(t -> t.id() == characterState.getCurrentCellTarget().id())
                    .findFirst().ifPresent(t -> {
                characterState.setCurrentPlayerCell(newMap.get(t.getNextCellId()));
                characterState.setCurrentCellTarget(newMap.get(t.getNextCellId()));
            });
        }
        mapState.setCurrentMap(newMap);
    }

}
