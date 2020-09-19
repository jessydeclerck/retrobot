package network.message.going;

import lombok.Data;
import bot.processor.packet.GatheringFinishedData;
import network.message.WSMessage;

@Data
public class GatheredResourceFinished extends WSMessage {

    public int amount;

    public GatheredResourceFinished(GatheringFinishedData gatheringFinishedData) {
        super("gathered");
        this.amount = gatheringFinishedData.getGatheredQty();
    }
}
