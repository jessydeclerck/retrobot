package processor;

import lombok.extern.slf4j.Slf4j;
import model.packet.FightTurnInformationData;
import state.CharacterState;
import state.MapState;

@Slf4j
public class FightTurnInformationProcessor extends PacketProcessor {

    private final CharacterState characterState = CharacterState.getInstance();
    private final MapState mapState = MapState.getInstance();

    @Override
    public void processPacket(String dofusPacket) {
        FightTurnInformationData fightTurnInformationData = new FightTurnInformationData(dofusPacket);
        characterState.getCurrentFightMonsterCells().clear();
        fightTurnInformationData.getFighterTurnInformationDataList().forEach(fighterData -> {
            if (fighterData.getFighterId() < 0) {
                log.info("Monster {} currentCell : {}", fighterData.getFighterId(), fighterData.getCellId());
                characterState.getCurrentFightMonsterCells().put(fighterData.getFighterId(), mapState.getCurrentMap().get(fighterData.getCellId()));
            } else if (fighterData.getFighterId() == characterState.getPlayerId()) {
                log.info("Player current fighting cell {}", fighterData.getCellId());
                characterState.setCurrentFightCell(mapState.getCurrentMap().get(fighterData.getCellId()));
            }
        });
    }

    @Override
    public String getPacketId() {
        return "GTM";
    }
}
