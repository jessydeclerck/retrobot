package model.packet;

import lombok.Getter;
import state.MapState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class RessourcesUpdatesData extends PacketData {

    private final MapState mapState = MapState.getInstance();

    /**
     * GDF|162;2;0
     * GDF : Packet name
     * 162 : cellID
     * 2 : status
     * 0 : ???
     */

    private List<RessourceUpdateData> updatedRessources = new ArrayList<>();

    public RessourcesUpdatesData(String fullPacket){
        super(fullPacket);
        // entree de map : GDF|161;4;0|162;4;0|146;4;0 => 4 revert status
        // entree de map : 162;5;1 => repousse
        String[] parsedPacket = fullPacket.split("\\|")[1].split(";");
        List<String> ressourcesStatusUpdates = new ArrayList<>(Arrays.asList(fullPacket.split("\\|")));
        ressourcesStatusUpdates.remove(0);//Remove packet id
        ressourcesStatusUpdates.forEach(update -> {
            String[] parsedUpdated = update.split(";");
            int cellId = Integer.parseInt(parsedUpdated[0]);
            RessourceStatus status = RessourceStatus.labelOfStatus(parsedPacket[1]);
            int availableFlag = Integer.parseInt(parsedUpdated[2]);
            updatedRessources.add(new RessourceUpdateData(cellId, status, availableFlag == 1));
        });
    }

    @Override
    public String getFullPacket() {
        return fullPacket;
    }

}
