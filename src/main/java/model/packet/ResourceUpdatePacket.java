package model.packet;

import model.dofus.RetroRessourceCell;
import service.MapService;

public class ResourceUpdatePacket implements Packet {

    /**
     * GDF|162;2;0
     * GDF : package name
     * 162 : cellID
     * 2 : status
     * 0 : ???
     */

    private String fullPacket;

    private int cellId;

    private ResourceStatus status;

    private MapService mapService;

    public ResourceUpdatePacket(String fullPacket, MapService mapService){
        this.fullPacket = fullPacket;
        this.mapService = mapService;
        String[] parsedPacket = fullPacket.split("\\|")[1].split(";");
        this.cellId = Integer.parseInt(parsedPacket[0]);
        this.status = ResourceStatus.labelOfStatus(parsedPacket[1]);
    }

    @Override
    public String getFullPacket() {
        return fullPacket;
    }

    @Override
    public String toString() {
        RetroRessourceCell retroRessourceCell = mapService.getCurrentMap().getRessourceCell(cellId);
        return "Resource on cell with id "
                + cellId
                + " located in "
                + retroRessourceCell.getAbscisse()
                + ", "
                + retroRessourceCell.getOrdonnee()
                + " is now "
                + status;
    }

}

enum ResourceStatus {

    AVAILABLE("1"),
    BUSY("2"),
    GONE("3"),
    BACK("4");

    public final String status;

    private ResourceStatus(String status) {
        this.status = status;
    }

    public static ResourceStatus labelOfStatus(String status) {
        for (ResourceStatus e : values()) {
            if (e.status.equals(status)) {
                return e;
            }
        }
        return null;
    }

}