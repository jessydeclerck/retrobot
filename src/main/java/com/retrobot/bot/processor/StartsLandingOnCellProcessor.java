package com.retrobot.bot.processor;

import com.retrobot.bot.processor.packet.StartsLandingOnCellData;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("unused")
@Slf4j
public class StartsLandingOnCellProcessor extends PacketProcessor {
    @Override
    public void processPacket(String dofusPacket) {
        StartsLandingOnCellData startsLandingOnCellData = new StartsLandingOnCellData(dofusPacket);
        log.debug("Landing on cell begins");
    }

    @Override
    public String getPacketId() {
        return "GKKO";
    }
}
