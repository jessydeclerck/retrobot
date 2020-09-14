package model.packet;

import lombok.Data;

@Data
public class TurnBeginsData extends PacketData {

    private final int playerId;

    private final int timeToPlayMs;

    public TurnBeginsData(String fullPacket) {
        super(fullPacket);
        String turnDataString = fullPacket.replace("GTS", "");
        String[] turnData = turnDataString.split("\\|");
        this.playerId = Integer.parseInt(turnData[0]);
        this.timeToPlayMs = Integer.parseInt(turnData[1]);
    }

}
