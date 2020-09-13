package model.packet;

import lombok.Data;

@Data
public class GatheringFinishedData extends PacketData {

    private final int playerId;
    private final int gatheredQty;

    public GatheringFinishedData(String fullPacket) {
        super(fullPacket);
        String[] gatheringFinishedData = fullPacket.replace("IQ", "").split("\\|");
        this.playerId = Integer.parseInt(gatheringFinishedData[0]);
        this.gatheredQty = Integer.parseInt(gatheringFinishedData[1]);
    }

    @Override
    public String getFullPacket() {
        return fullPacket;
    }

}
