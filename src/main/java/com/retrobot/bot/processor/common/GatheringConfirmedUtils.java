package com.retrobot.bot.processor.common;

import com.retrobot.bot.processor.packet.GatheringConfirmedData;
import com.retrobot.bot.state.CharacterState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GatheringConfirmedUtils {

    private final CharacterState characterState;

    public GatheringConfirmedUtils(CharacterState characterState) {
        this.characterState = characterState;
    }

    public void updateGatheringConfirmed(String dofusPacket) {
        try {
            GatheringConfirmedData gatheringConfirmedData = new GatheringConfirmedData(dofusPacket);
            if (gatheringConfirmedData.getPlayerId().equals(characterState.getPlayerId())) {
                log.info("Gathering action confirmed");
                characterState.setGatheringConfirmed(true);
            }
        } catch (Exception e) {
            log.debug("Couldn't parse game action packet (GA)", e);
        }
    }
}
