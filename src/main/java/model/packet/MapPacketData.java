package model.packet;

import lombok.Getter;
import model.dofus.RetroDofusMap;
import service.MapService;

public class MapPacketData extends PacketData {

    private static MapService mapService = MapService.getInstance();

    @Getter
    private RetroDofusMap map;

    public MapPacketData(String packet) {
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
