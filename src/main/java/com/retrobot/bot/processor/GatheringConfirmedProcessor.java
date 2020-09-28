package com.retrobot.bot.processor;

import com.retrobot.bot.processor.common.GatheringConfirmedUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GatheringConfirmedProcessor extends PacketProcessor {

    private final GatheringConfirmedUtils gatheringConfirmedUtils;

    public GatheringConfirmedProcessor(GatheringConfirmedUtils gatheringConfirmedUtils) {
        this.gatheringConfirmedUtils = gatheringConfirmedUtils;
    }

    @Override
    public void processPacket(String dofusPacket) {
        gatheringConfirmedUtils.updateGatheringConfirmed(dofusPacket);
    }

    @Override
    public String getPacketId() {
        return "GA1";
    }
}
