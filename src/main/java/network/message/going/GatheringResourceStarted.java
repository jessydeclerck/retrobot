package network.message.going;

import lombok.Data;
import model.packet.GoingToGatherData;
import network.message.WSMessage;

@Data
public class GatheringResourceStarted extends WSMessage {

    private int resource;

    public GatheringResourceStarted(GoingToGatherData goingToGatherData) {
        super("gathering");
        resource = goingToGatherData.getCell().getIdRessource();
    }
}
