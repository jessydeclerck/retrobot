package processor;

import lombok.extern.slf4j.Slf4j;
import model.dofus.RetroDofusCell;
import model.packet.FightersCoordinatesStartFightData;
import state.CharacterState;
import state.MapState;

@Slf4j
public class PlayerCoordinatesStartFightProcessor extends PacketProcessor {
    @Override
    public void processPacket(String dofusPacket) {
        FightersCoordinatesStartFightData startFightData = new FightersCoordinatesStartFightData(dofusPacket);
        /**if (startFightData.getPlayerId() == CharacterState.getInstance().getPlayerId()) {
            log.info("Player current fighting cell id : {}", startFightData.getCellId());
            RetroDofusCell currentFightCell = MapState.getInstance().getCurrentMap().get(startFightData.getCellId());
            CharacterState.getInstance().setCurrentFightCell(currentFightCell);
        }*/
    }

    @Override
    public String getPacketId() {
        return "GIC";
    }
}
