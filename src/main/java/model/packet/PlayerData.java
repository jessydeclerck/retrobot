package model.packet;

import lombok.Data;

@Data
public class PlayerData extends PacketData {

    private final int playerId;

    public PlayerData(String fullPacket) {
        super(fullPacket);
        this.playerId = Integer.parseInt(this.fullPacket.split("\\|")[1]);
    }

    @Override
    public String getFullPacket() {
        return null;
    }
}
