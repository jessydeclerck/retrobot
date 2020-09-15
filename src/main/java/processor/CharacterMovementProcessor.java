package processor;

import lombok.extern.slf4j.Slf4j;
import model.dofus.RetroDofusCell;
import model.dofus.RetroDofusMap;
import model.packet.CharacterMovementData;
import state.CharacterState;
import state.MapState;

@Slf4j
public class CharacterMovementProcessor extends PacketProcessor {

    @Override
    public void processPacket(String dofusPacket) {
        CharacterMovementData characterMovementData = new CharacterMovementData(dofusPacket);
        RetroDofusMap currentMap = MapState.getInstance().getCurrentMap();
        if(currentMap == null || CharacterState.getInstance().isFighting()) {
            return;
        }
        RetroDofusCell targetedCell = currentMap.get(characterMovementData.getTargetCellId());
        //TODO do not update when in combat
        //TODO check monster movement also
        CharacterState.getInstance().setCurrentCellTarget(targetedCell);
        /**if(CharacterState.getInstance().isFighting()) { handled by GTM
            log.info("Player current fighting cell {}", targetedCell.id());
            CharacterState.getInstance().setCurrentFightCell(targetedCell);
        }*/
    }

    @Override
    public String getPacketId() {
        return "GA001";
    }
}
