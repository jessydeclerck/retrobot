package com.retrobot.bot.processor;

import com.retrobot.bot.processor.packet.CharacterStatsData;
import com.retrobot.bot.state.CharacterState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CharacterStatsProcessor extends PacketProcessor {

    private final CharacterState characterState;

    public CharacterStatsProcessor(CharacterState characterState) {
        this.characterState = characterState;
    }

    @Override
    public void processPacket(String dofusPacket) {
        CharacterStatsData characterStatsData = new CharacterStatsData(dofusPacket);
        characterState.setCurrentHp(characterStatsData.getCurrentHp());
        characterState.setMaxHp(characterStatsData.getHpMax());
    }

    @Override
    public String getPacketId() {
        return "As";
    }
}
