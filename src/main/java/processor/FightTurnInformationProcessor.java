package processor;

import lombok.extern.slf4j.Slf4j;
import model.packet.FightTurnInformationData;
import state.CharacterState;
import state.MapState;

@Slf4j
public class FightTurnInformationProcessor extends PacketProcessor {
    @Override
    public void processPacket(String dofusPacket) {
        FightTurnInformationData fightTurnInformationData = new FightTurnInformationData(dofusPacket);
        fightTurnInformationData.getFighterTurnInformationDataList().forEach(fighterData -> {
            if (fighterData.getFighterId() < 0) {
                log.info("Monster {} currentCell : {}", fighterData.getFighterId(), fighterData.getCellId());
                CharacterState.getInstance().getCurrentFightMonsterCells().put(fighterData.getFighterId(), MapState.getInstance().getCurrentMap().get(fighterData.getCellId()));
            } else if (fighterData.getFighterId() == CharacterState.getInstance().getPlayerId()) {
                log.info("Player current fighting cell {}", fighterData.getCellId());
                CharacterState.getInstance().setCurrentFightCell(MapState.getInstance().getCurrentMap().get(fighterData.getCellId()));
            }
        });
    }

    @Override
    public String getPacketId() {
        return "GTM";
    }
}
