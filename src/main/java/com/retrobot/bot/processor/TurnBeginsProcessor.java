package com.retrobot.bot.processor;

import com.retrobot.bot.processor.packet.TurnBeginsData;
import com.retrobot.bot.service.FightService;
import com.retrobot.bot.state.CharacterState;
import com.retrobot.bot.state.FightState;
import com.retrobot.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TurnBeginsProcessor extends PacketProcessor {

    private final FightService fightService;
    private final CharacterState characterState;
    private final FightState fightState;

    public TurnBeginsProcessor(FightService fightService, CharacterState characterState, FightState fightState) {
        this.fightService = fightService;
        this.characterState = characterState;
        this.fightState = fightState;
    }

    @Override
    public void processPacket(String dofusPacket) {
        TurnBeginsData turnBeginsData = new TurnBeginsData(dofusPacket);
        if (turnBeginsData.getPlayerId() == characterState.getPlayerId()) {
            log.info("Tour du joueur detecté");
            fightState.incrementTurnNb();
            TimeUtils.sleep(1500);
            //TODO
            fightService.playTurn();
            //TODO update player and monster position
        }
    }


    @Override
    public String getPacketId() {
        return "GTS";
    }

}
