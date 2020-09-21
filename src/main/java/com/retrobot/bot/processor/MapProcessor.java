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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MapProcessor extends PacketProcessor {

    private final MapService mapService;

    private final CharacterState characterState;

    private final DeplacementService deplacementService;

    private final BotService botService;

    private final MapState mapState;

    public MapProcessor(MapService mapService, CharacterState characterState, DeplacementService deplacementService, BotService botService, MapState mapState) {
        this.mapService = mapService;
        this.characterState = characterState;
        this.deplacementService = deplacementService;
        this.botService = botService;
        this.mapState = mapState;
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
        deplacementService.goNextMap();
        log.info("Current map id : {}", mapPacketData.getMap().getId());
        try {
            BotServer.getInstance().emitMessage(new NewMap(mapPacketData));
        } catch (JsonProcessingException e) {
            log.error("Erreur lors de l'émission du socket de nouvelle map", e);
        }
    }

    @Override
    public String getPacketId() {
        return "GDM";
    }



}
