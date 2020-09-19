package com.retrobot.bot.processor;

import com.retrobot.bot.service.BotService;
import com.retrobot.bot.service.FightService;
import com.retrobot.bot.state.CharacterState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JoinCombatProcessor extends PacketProcessor {

    private final BotService botService;
    private final FightService fightService;

    public JoinCombatProcessor(CharacterState characterState, BotService botService, FightService fightService) {
        this.botService = botService;
        this.fightService = fightService;
    }

    @Override
    public void processPacket(String dofusPacket) {
        //JoinCombatData joinCombatData = new JoinCombatData(dofusPacket);
        log.info("Combat detecté");
        botService.setFighting(true);
        fightService.prepareFight();
    }

    @Override
    public String getPacketId() {
        return "GJK2";
    }
}
