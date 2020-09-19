package network.message.going;

import lombok.Data;
import model.packet.MapPacketData;
import network.message.WSMessage;

@Data
public class NewMap extends WSMessage {

    private int x;
    private int y;

    public NewMap(MapPacketData mapPacketData) {
        super("map");
        this.x = mapPacketData.getMap().getX();
        this.y = mapPacketData.getMap().getY();
    }
}
