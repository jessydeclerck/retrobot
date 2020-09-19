package com.retrobot.network.message.going;

import com.retrobot.bot.processor.packet.GatheringFinishedData;
import com.retrobot.network.message.WSMessage;
import lombok.Value;

@Value
public class GatheredResourceFinished extends WSMessage {

    public int amount;

    public GatheredResourceFinished(GatheringFinishedData gatheringFinishedData) {
        super("gathered");
        this.amount = gatheringFinishedData.getGatheredQty();
    }
}
