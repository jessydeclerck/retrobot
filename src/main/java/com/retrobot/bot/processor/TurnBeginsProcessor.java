package com.retrobot.bot.processor;

import com.retrobot.bot.processor.packet.TurnBeginsData;
import com.retrobot.bot.service.FightService;
import com.retrobot.bot.state.CharacterState;
import com.retrobot.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TurnBeginsProcessor extends PacketProcessor {

    private final FightService fightService;
    private final CharacterState characterState;

    public TurnBeginsProcessor(FightService fightService, CharacterState characterState) {
        this.fightService = fightService;
        this.characterState = characterState;
    }

    @Override
    public void processPacket(String dofusPacket) {
        TurnBeginsData turnBeginsData = new TurnBeginsData(dofusPacket);
        if (turnBeginsData.getPlayerId() == characterState.getPlayerId()) {
            log.info("Tour du joueur detecté");
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
