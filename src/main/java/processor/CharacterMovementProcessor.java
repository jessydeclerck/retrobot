package processor;

import model.dofus.RetroDofusCell;
import model.dofus.RetroDofusMap;
import model.packet.CharacterMovementData;
import state.CharacterState;
import state.MapState;

public class CharacterMovementProcessor extends PacketProcessor {

    @Override
    public void processPacket(String dofusPacket) {
        CharacterMovementData characterMovementData = new CharacterMovementData(dofusPacket);
        RetroDofusMap currentMap = MapState.getInstance().getCurrentMap();
        if(currentMap == null) {
            return;
        }
        RetroDofusCell targetedCell = currentMap.get(characterMovementData.getTargetCellId());
        //TODO do not update when in combat
        //TODO check monster movement also
        CharacterState.getInstance().setCurrentCellTarget(targetedCell);
    }

    @Override
    public String getPacketId() {
        return "GA001";
    }
}
