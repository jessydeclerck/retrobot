package com.retrobot.bot.processor;

import com.retrobot.bot.processor.packet.PodsUpdateData;
import com.retrobot.bot.service.DeplacementService;
import com.retrobot.bot.state.CharacterState;
import com.retrobot.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PodsUpdateProcessor extends PacketProcessor {

    private final CharacterState characterState;
    private final DeplacementService deplacementService;
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public PodsUpdateProcessor(CharacterState characterState, DeplacementService deplacementService, ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.characterState = characterState;
        this.deplacementService = deplacementService;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    @Override
    public void processPacket(String dofusPacket) {
        PodsUpdateData podsUpdateData = new PodsUpdateData(dofusPacket);
        characterState.setCurrentPods(podsUpdateData.getCurrentPods());
        characterState.setMaxPods(podsUpdateData.getMaxPods());
        log.info("Pods : {}/{}", podsUpdateData.getCurrentPods(), podsUpdateData.getMaxPods());
        if ((double) podsUpdateData.getCurrentPods() / podsUpdateData.getMaxPods() > 0.90 && !characterState.isGoingBank()) {
            threadPoolTaskExecutor.execute(() -> {
                if (!characterState.isFighting()) {// let the bot fight if a fight happens just before going to bank
                    TimeUtils.sleep(2000);
                    deplacementService.goToBank();
                }
            });
        }
        //Ow1046|2076
    }

    @Override
    public String getPacketId() {
        return "Ow";
    }
}
