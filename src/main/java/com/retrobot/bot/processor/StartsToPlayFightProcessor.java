package com.retrobot.bot.processor;

import com.retrobot.bot.processor.packet.StartsToPlayFightData;

@SuppressWarnings("unused")
public class StartsToPlayFightProcessor extends PacketProcessor{
    @Override
    public void processPacket(String dofusPacket) {
        StartsToPlayFightData startsToPlayFightData = new StartsToPlayFightData(dofusPacket);
    }

    @Override
    public String getPacketId() {
        return "GS";
    }
}
