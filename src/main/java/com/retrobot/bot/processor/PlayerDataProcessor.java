package com.retrobot.bot.processor;

import com.retrobot.bot.processor.packet.PlayerData;
import com.retrobot.bot.state.CharacterState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PlayerDataProcessor extends PacketProcessor {

    private final CharacterState characterState;

    public PlayerDataProcessor(CharacterState characterState) {
        this.characterState = characterState;
    }

    @Override
    public void processPacket(String dofusPacket) {
        PlayerData playerData = new PlayerData(dofusPacket);
        log.info("Player id : {}", playerData.getPlayerId());
        characterState.setPlayerId(playerData.getPlayerId());
    }

    @Override
    public String getPacketId() {
        return "ASK";
    }
}
