package processor;

import lombok.extern.slf4j.Slf4j;
import model.dofus.RetroDofusCell;
import model.packet.FightersCoordinatesStartFightData;
import state.CharacterState;
import state.MapState;

@Slf4j
public class PlayerCoordinatesStartFightProcessor extends PacketProcessor {

    private final CharacterState characterState = CharacterState.getInstance();
    private final MapState mapState = MapState.getInstance();

    @Override
    public void processPacket(String dofusPacket) {
        FightersCoordinatesStartFightData startFightData = new FightersCoordinatesStartFightData(dofusPacket);
        log.info("Player current fighting cell id : {}", startFightData.getPlayerCellId());
        RetroDofusCell currentFightCell = mapState.getCurrentMap().get(startFightData.getPlayerCellId());
        //TODO put in fightstate
        characterState.setCurrentFightCell(currentFightCell);
        startFightData.getMonsterPositions().forEach(monsterFightPositionData -> {
            RetroDofusCell monsterFightCell = mapState.getCurrentMap().get(monsterFightPositionData.getCellId());
            log.info("Monster current fighting cell id : {}", monsterFightPositionData.getCellId());
            characterState.getCurrentFightMonsterCells().put(monsterFightPositionData.getMonsterId(), monsterFightCell);
        });
    }

    @Override
    public String getPacketId() {
        return "GIC";
    }
}
