package com.retrobot.bot.processor.packet;

import lombok.Value;

@Value
public class GameActionData extends PacketData {

    int actionNumber;
    int playerId;
    int monsterId;

    public GameActionData(String fullPacket) {
        super(fullPacket);
        String gameActionData = fullPacket.replace("GA;", "");
        String[] gameActionArray = gameActionData.split(";");
        actionNumber = Integer.parseInt(gameActionArray[0]);
        playerId = Integer.parseInt(gameActionArray[1]);
        monsterId = Integer.parseInt(gameActionArray[2]);
        //GA;103;190004801;-3
    }

}
