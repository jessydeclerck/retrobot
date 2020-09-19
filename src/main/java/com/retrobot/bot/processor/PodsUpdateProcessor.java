package com.retrobot.bot.processor;

import com.retrobot.bot.processor.packet.PodsUpdateData;
import com.retrobot.bot.service.DeplacementService;
import com.retrobot.bot.state.CharacterState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PodsUpdateProcessor extends PacketProcessor {

    private final CharacterState characterState;
    private final DeplacementService deplacementService;

    public PodsUpdateProcessor(CharacterState characterState, DeplacementService deplacementService) {
        this.characterState = characterState;
        this.deplacementService = deplacementService;
    }

    @Override
    public void processPacket(String dofusPacket) {
        PodsUpdateData podsUpdateData = new PodsUpdateData(dofusPacket);
        characterState.setCurrentPods(podsUpdateData.getCurrentPods());
        characterState.setMaxPods(podsUpdateData.getMaxPods());
        log.info("Pods : {}/{}", podsUpdateData.getCurrentPods(), podsUpdateData.getMaxPods());
        if ((double) podsUpdateData.getCurrentPods() / podsUpdateData.getMaxPods() > 0.90) {
            deplacementService.goToBank();
        }
        //Ow1046|2076
    }

    @Override
    public String getPacketId() {
        return "Ow";
    }
}
