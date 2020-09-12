package model.packet;

import lombok.Getter;
import model.dofus.RetroDofusMap;
import service.MapService;

public class NewMapPacket implements Packet {

    private static MapService mapService = MapService.getInstance();

    /**
     * GDM|7454|0706131721|7e3b4646573b7b7971...
     * GDM : Packet Name
     * 7454 : MapId
     * 0706131721 : ???
     * 7e3b4646573b7b7971: ???
     */

    private String fullPacket;

    @Getter
    private RetroDofusMap map;

    public NewMapPacket(String packet) {
        fullPacket = packet;
        String[] parsedPacket = packet.split("\\|");
        this.map = mapService.setRetroDofusMap(Integer.parseInt(parsedPacket[1]));
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
