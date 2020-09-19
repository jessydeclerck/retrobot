package com.retrobot.bot.processor;

import com.retrobot.bot.service.BotService;
import com.retrobot.bot.service.FightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FightOnEndProcessor extends PacketProcessor {

    private final BotService botService;
    private final FightService fightService;

    public FightOnEndProcessor(BotService botService, FightService fightService) {
        this.botService = botService;
        this.fightService = fightService;
    }


    @Override
    public void processPacket(String dofusPacket) {
        log.info("Fin du combat detecté");
        botService.setFighting(false);
        fightService.fermerFenetreFinCombat();
    }

    @Override
    public String getPacketId() {
        return "GE";
    }
}
