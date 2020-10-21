package com.retrobot.bot.processor.packet;

import lombok.Value;

@Value
public class FightOnMapData extends PacketData {

    int monsterId;

    public FightOnMapData(String fullPacket) {
        super(fullPacket);
        //Gc+-254;4|-254;136;0;-1|-255;107;1;-1
        String[] fightOnMapArray = fullPacket.split("\\|");
        String groupDataString = fightOnMapArray[2];
        String[] groupDataArray = groupDataString.split(";");
        monsterId = Integer.parseInt(groupDataArray[3]);
    }

}
