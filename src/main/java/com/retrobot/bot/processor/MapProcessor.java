package com.retrobot.bot.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.retrobot.bot.processor.packet.MapPacketData;
import com.retrobot.bot.service.BotService;
import com.retrobot.bot.service.DeplacementService;
import com.retrobot.bot.service.MapService;
import com.retrobot.bot.state.CharacterState;
import com.retrobot.bot.state.MapState;
import com.retrobot.network.BotServer;
import com.retrobot.network.message.going.NewMap;
import com.retrobot.scriptloader.model.GatherMapAction;
import com.retrobot.scriptloader.model.ScriptPath;
import com.retrobot.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class MapProcessor extends PacketProcessor {

    private final MapService mapService;

    private final CharacterState characterState;

    private final DeplacementService deplacementService;

    private final BotService botService;

    private final MapState mapState;

    private final BotServer botServer;

    private final ScriptPath scriptPath;

    public MapProcessor(MapService mapService, CharacterState characterState, DeplacementService deplacementService, BotService botService, MapState mapState, BotServer botServer, ScriptPath scriptPath) {
        this.mapService = mapService;
        this.characterState = characterState;
        this.deplacementService = deplacementService;
        this.botService = botService;
        this.mapState = mapState;
        this.botServer = botServer;
        this.scriptPath = scriptPath;
    }

    public void processPacket(String dofusPacket) {
        //log.info(dofusPacket);
        MapPacketData mapPacketData = new MapPacketData(dofusPacket, this.mapService);
        log.info(mapPacketData.toString());
        botService.setCurrentMap(mapPacketData.getMap());
        characterState.setMoving(false);
        botService.addAvailableRessources(mapPacketData.getMap().getRessources());
        mapState.getCurrentMap().getTriggers()
                .forEach(retroTriggerCell -> log.debug("Trigger {} Next map : {} Next cell : {}", retroTriggerCell.id(), retroTriggerCell.getNextMapId(), retroTriggerCell.getNextCellId()));
        CompletableFuture.runAsync(() -> {
            TimeUtils.sleep(2000);
            if (!characterState.isGoingBank()) {
                changeMapWithRetry(deplacementService::goNextGatherMap, mapState.getCurrentMap().getId()); //wont be compatible with gathering
            } else {
                changeMapWithRetry(deplacementService::goToBank, mapState.getCurrentMap().getId());
            }
        });
        log.info("Current map id : {}", mapPacketData.getMap().getId());
        try {
            botServer.emitMessage(new NewMap(mapPacketData));
        } catch (JsonProcessingException e) {
            log.error("Erreur lors de l'émission du socket de nouvelle map", e);
        }
    }

    private void changeMapWithRetry(Runnable changeMapAction, int startMapId) {
        changeMapAction.run();
        CompletableFuture.runAsync(() -> {
            TimeUtils.sleep(15000);
            int currentMapId = mapState.getCurrentMap().getId();
            Optional<GatherMapAction> gatherMapAction = Optional.ofNullable(scriptPath.getGatherPath().get(currentMapId));
            if (startMapId == currentMapId) {
                if (gatherMapAction.isPresent() && gatherMapAction.get().isGather()) {
                    log.info("Map didn't change because we're on a gathering map, won't be retried");
                } else {
                    log.info("Map didn't change, let's retry");
                    changeMapWithRetry(changeMapAction, startMapId);
                }
            } else {
                log.info("Map has changed, won't be retried");
            }
        });
    }

    @Override
    public String getPacketId() {
        return "GDM";
    }



}
