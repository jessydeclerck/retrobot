package com.retrobot.network.message.going;

import com.retrobot.bot.processor.packet.GoingToGatherData;
import com.retrobot.network.message.WSMessage;
import lombok.Value;

@Value
public class GatheringResourceStarted extends WSMessage {

    private int resource;

    public GatheringResourceStarted(GoingToGatherData goingToGatherData) {
        super("gathering");
        resource = goingToGatherData.getCell().getIdRessource();
    }
}
