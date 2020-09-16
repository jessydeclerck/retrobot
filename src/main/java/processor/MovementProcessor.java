package processor;

import lombok.extern.slf4j.Slf4j;
import model.packet.MovementData;
import state.CharacterState;
import state.MapState;

@Slf4j
public class MovementProcessor extends PacketProcessor {

    private final CharacterState characterState = CharacterState.getInstance();
    private final MapState mapState = MapState.getInstance();

    @Override
    public void processPacket(String dofusPacket) {
        MovementData movementData = new MovementData(dofusPacket);
        if (characterState.getPlayerId() == movementData.getEntityId() && !characterState.isFighting()) {
            log.info("Current player position cell : {}", movementData.getEntityCellId());
            characterState.setCurrentPlayerCell(mapState.getCurrentMap().get(movementData.getEntityCellId()));
        }
    }

    @Override
    public String getPacketId() {
        return "GA0";
    }
}
