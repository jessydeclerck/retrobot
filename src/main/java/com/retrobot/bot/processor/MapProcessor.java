package com.retrobot.bot.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.retrobot.bot.processor.packet.MapPacketData;
import com.retrobot.bot.service.BotService;
import com.retrobot.bot.service.DeplacementService;
import com.retrobot.bot.service.MapService;
import com.retrobot.bot.state.CharacterState;
import com.retrobot.bot.state.MapState;
import com.retrobot.going.NewMap;
import com.retrobot.network.BotServer;
import com.retrobot.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.*;
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

    public MapProcessor(MapService mapService, CharacterState characterState, DeplacementService deplacementService, BotService botService, MapState mapState, BotServer botServer) {
        this.mapService = mapService;
        this.characterState = characterState;
        this.deplacementService = deplacementService;
        this.botService = botService;
        this.mapState = mapState;
        this.botServer = botServer;
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
            TimeUtils.sleep(2000); //TODO check gathering
            if (!characterState.isGoingBank()) {
                deplacementService.goNextGatherMap();
            } else {
                deplacementService.goToBank();
            }
        });
        log.info("Current map id : {}", mapPacketData.getMap().getId());
        try {
            botServer.emitMessage(new NewMap(mapPacketData));
        } catch (JsonProcessingException e) {
            log.error("Erreur lors de l'émission du socket de nouvelle map", e);
        }
    }

    @Override
    public String getPacketId() {
        return "GDM";
    }

    private void displayMapId(MapPacketData mapPacketData) {
        SystemTray tray = SystemTray.getSystemTray();
        TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().createImage(new byte[0]));
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        trayIcon.displayMessage(mapPacketData.getMap().getX() + "," + mapPacketData.getMap().getY(), "" + mapPacketData.getMap().getId(), TrayIcon.MessageType.INFO);
    }

}
