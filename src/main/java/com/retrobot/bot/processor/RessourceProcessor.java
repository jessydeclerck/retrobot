package com.retrobot.bot.processor;

import com.retrobot.bot.processor.packet.RessourcesUpdatesData;
import com.retrobot.bot.service.BotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RessourceProcessor extends PacketProcessor {

    private final BotService botService;

    public RessourceProcessor(BotService botService) {
        this.botService = botService;
    }

    @Override
    public void processPacket(String dofusPacket) {
        RessourcesUpdatesData ressourcesUpdatesData = new RessourcesUpdatesData(dofusPacket);
        //TODO put in gatheringState
        ressourcesUpdatesData.getUpdatedRessources().forEach(ressourceUpdateData -> {
            if (ressourceUpdateData.isAvailable()) {
                botService.setAvailableRessource(ressourceUpdateData.getCellId());
            } else {
                botService.setUnavailableRessource(ressourceUpdateData.getCellId());
            }
        });
    }

    @Override
    public String getPacketId() {
        return "GDF";
    }
}
