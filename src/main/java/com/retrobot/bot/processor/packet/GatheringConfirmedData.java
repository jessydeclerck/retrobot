package com.retrobot.bot.processor.packet;

import lombok.Getter;

@Getter
public class GatheringConfirmedData extends PacketData {

    private Integer playerId;

    private Integer cellId;

    private Integer gatheringDuration;

    public GatheringConfirmedData(String fullPacket) {
        super(fullPacket);
        String[] gatheringConfirmedData = fullPacket.split(";");
        this.playerId = Integer.valueOf(gatheringConfirmedData[2]);
        String ressourceData[] = gatheringConfirmedData[3].split(",");
        this.cellId = Integer.valueOf(ressourceData[0]);
        this.gatheringDuration = Integer.valueOf(ressourceData[1]);
    }

}
