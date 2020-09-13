package model.packet;

import lombok.Getter;
import model.dofus.RetroRessourceCell;
import service.MapService;

@Getter
public class ResourceUpdatePacket implements Packet {

    private static MapService mapService = MapService.getInstance();

    /**
     * GDF|162;2;0
     * GDF : Packet name
     * 162 : cellID
     * 2 : status
     * 0 : ???
     */

    private String fullPacket;

    private int cellId;

    private RessourceStatus status;

    public ResourceUpdatePacket(String fullPacket){
        this.fullPacket = fullPacket;
        String[] parsedPacket = fullPacket.split("\\|")[1].split(";");
        this.cellId = Integer.parseInt(parsedPacket[0]);
        this.status = RessourceStatus.labelOfStatus(parsedPacket[1]);
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
