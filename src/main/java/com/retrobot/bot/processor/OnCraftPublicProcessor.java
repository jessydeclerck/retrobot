package com.retrobot.bot.processor;


import com.retrobot.bot.processor.packet.OnCraftPublicData;
import com.retrobot.bot.state.CharacterState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OnCraftPublicProcessor extends PacketProcessor {

    private final CharacterState characterState;

    public OnCraftPublicProcessor(CharacterState characterState) {
        this.characterState = characterState;
    }

    @Override
    public void processPacket(String dofusPacket) {
        OnCraftPublicData onCraftPublicData = new OnCraftPublicData(dofusPacket);
        if (characterState.getPlayerId() == 0) {
            log.info("Player id has been set : {}", onCraftPublicData.getPlayerId());
            characterState.setPlayerId(onCraftPublicData.getPlayerId());
        }
    }

    @Override
    public String getPacketId() {
        return "EW";
    }
}
