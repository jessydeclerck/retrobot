package processor;

import lombok.extern.slf4j.Slf4j;
import model.dofus.RetroDofusCell;
import model.dofus.RetroDofusMap;
import model.packet.CharacterMovementData;
import state.CharacterState;
import state.MapState;

@Slf4j
public class CharacterMovementProcessor extends PacketProcessor {

    private final CharacterState characterState = CharacterState.getInstance();
    private final MapState mapState = MapState.getInstance();

    @Override
    public void processPacket(String dofusPacket) {
        CharacterMovementData characterMovementData = new CharacterMovementData(dofusPacket);
        RetroDofusMap currentMap = mapState.getCurrentMap();
        if (currentMap == null || characterState.isFighting()) {
            return;
        }
        RetroDofusCell targetedCell = currentMap.get(characterMovementData.getTargetCellId());
        //log.info("Character current target cell : {}", targetedCell.id());
        characterState.setCurrentCellTarget(targetedCell);
    }

    @Override
    public String getPacketId() {
        return "GA001";
    }
}
