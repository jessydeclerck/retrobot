package com.retrobot.bot.processor.packet;

import com.retrobot.bot.model.dofus.RetroDofusMap;
import com.retrobot.bot.service.MapService;
import lombok.Getter;

public class MapPacketData extends PacketData {

    @Getter
    private RetroDofusMap map;

    public MapPacketData(String packet, MapService mapService) {
        super(packet);
        String[] parsedPacket = packet.split("\\|");
        int mapId = Integer.parseInt(parsedPacket[1]);
        this.map = mapService.getRetroDofusMap(mapId);
    }

    @Override
    public String getFullPacket() {
        return this.fullPacket;
    }

    @Override
    public String toString() {
        return "New map : Coordonnées : " + this.map.getX() + ", " + this.map.getY();
    }
}
