package model.packet;

import lombok.Data;

@Data
public class JoinCombatData extends PacketData {

    public JoinCombatData(String fullPacket) {
        super(fullPacket);
    }

}
