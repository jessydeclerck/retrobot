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
        //GE55392|190004801|0|2;190004801;Pepite;72;0;25209000;26375342;26707000;1078;0;;;10|0;-2;920;15;1;;;;;;;;|0;-1;922;10;1;;;;;;;;|5;-10;La Table Ronde;16;0;;;;;4;;;0
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
