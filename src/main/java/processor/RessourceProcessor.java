package processor;

import lombok.extern.slf4j.Slf4j;
import model.packet.RessourcesUpdatesData;
import state.MapState;

@Slf4j
public class RessourceProcessor extends PacketProcessor {

    private final MapState mapState = MapState.getInstance();

    @Override
    public void processPacket(String dofusPacket) {
        RessourcesUpdatesData ressourcesUpdatesData = new RessourcesUpdatesData(dofusPacket);
        //TODO put in gatheringState
        ressourcesUpdatesData.getUpdatedRessources().forEach(ressourceUpdateData -> {
            if (ressourceUpdateData.isAvailable()) {
                mapState.setAvailableRessource(ressourceUpdateData.getCellId());
            } else {
                mapState.setUnavailableRessource(ressourceUpdateData.getCellId());
            }
        });
    }

    @Override
    public String getPacketId() {
        return "GDF";
    }
}
