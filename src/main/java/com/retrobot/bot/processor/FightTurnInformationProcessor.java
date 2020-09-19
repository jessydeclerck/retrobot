package com.retrobot.bot.processor;

import com.retrobot.bot.processor.packet.FightTurnInformationData;
import com.retrobot.bot.state.CharacterState;
import com.retrobot.bot.state.MapState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FightTurnInformationProcessor extends PacketProcessor {

    private final CharacterState characterState;
    private final MapState mapState;

    public FightTurnInformationProcessor(CharacterState characterState, MapState mapState) {
        this.characterState = characterState;
        this.mapState = mapState;
    }

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
