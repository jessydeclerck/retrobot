package model.packet;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Data
public class EnterNewMapData extends PacketData{

    private int playerId;

    public EnterNewMapData(String fullPacket) {
        super(fullPacket);
        playerId = Integer.parseInt(fullPacket.split("\\+")[1].split("\\|")[0]);
    }


}
