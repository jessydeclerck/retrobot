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
        log.info("Player current fighting cell id : {}", startFightData.getPlayerCellId());
        RetroDofusCell currentFightCell = MapState.getInstance().getCurrentMap().get(startFightData.getPlayerCellId());
        CharacterState.getInstance().setCurrentFightCell(currentFightCell);
        startFightData.getMonsterPositions().forEach(monsterFightPositionData -> {
            RetroDofusCell monsterFightCell = MapState.getInstance().getCurrentMap().get(monsterFightPositionData.getCellId());
            log.info("Monster current fighting cell id : {}", monsterFightPositionData.getCellId());
            CharacterState.getInstance().getCurrentFightMonsterCells().put(monsterFightPositionData.getMonsterId(), monsterFightCell);
        });
    }

    @Override
    public String getPacketId() {
        return "GIC";
    }
}
