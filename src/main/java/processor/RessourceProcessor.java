package processor;

import lombok.extern.log4j.Log4j2;
import model.packet.RessourcesUpdatesData;
import state.CharacterState;
import state.MapState;

@Log4j2
public class RessourceProcessor extends PacketProcessor {

    private final CharacterState characterState;
    private final MapState mapState = MapState.getInstance();

    public RessourceProcessor() {
        this.characterState = CharacterState.getInstance();
    }

    @Override
    public void processPacket(String dofusPacket) {
        RessourcesUpdatesData ressourcesUpdatesData = new RessourcesUpdatesData(dofusPacket);
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
