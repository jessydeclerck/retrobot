package com.retrobot.bot.processor;

import com.retrobot.bot.service.RecolteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JobLevelProcessor extends PacketProcessor {

    //TODO refacto in interface com.retrobot.bot.service
    private final RecolteService recolteService;

    public JobLevelProcessor(RecolteService recolteService) {
        this.recolteService = recolteService;
    }

    @Override
    public void processPacket(String dofusPacket) {
        log.info("Fermeture notification niveau métier");
        recolteService.fermerPopupNiveauMetier();
    }

    @Override
    public String getPacketId() {
        return "JN";
    }
}
