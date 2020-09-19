package com.retrobot.going;

import com.retrobot.bot.processor.packet.MapPacketData;
import com.retrobot.network.message.WSMessage;
import lombok.Value;

@Value
public class NewMap extends WSMessage {

    int x;
    int y;

    public NewMap(MapPacketData mapPacketData) {
        super("map");
        this.x = mapPacketData.getMap().getX();
        this.y = mapPacketData.getMap().getY();
    }
}
