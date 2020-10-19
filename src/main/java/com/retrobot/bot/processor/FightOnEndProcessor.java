package com.retrobot.bot.processor;

import com.retrobot.bot.processor.packet.FightOnEndData;
import com.retrobot.bot.service.BotService;
import com.retrobot.bot.service.FightService;
import com.retrobot.bot.state.FightState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FightOnEndProcessor extends PacketProcessor {

    private final BotService botService;
    private final FightService fightService;
    private final FightState fightState;

    public FightOnEndProcessor(BotService botService, FightService fightService, FightState fightState) {
        this.botService = botService;
        this.fightService = fightService;
        this.fightState = fightState;
    }


    @Override
    public void processPacket(String dofusPacket) {
        log.info("Fin du combat detecté");
        FightOnEndData fightOnEndData = new FightOnEndData(dofusPacket);
        botService.setFighting(false);
        fightState.resetState();
        fightService.fermerFenetreFinCombat(fightOnEndData.getFighterId());
        fightService.regen();
    }

    @Override
    public String getPacketId() {
        return "GE";
    }
}
