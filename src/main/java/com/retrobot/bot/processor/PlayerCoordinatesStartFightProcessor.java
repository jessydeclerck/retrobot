package com.retrobot.bot.processor;

import com.retrobot.bot.model.dofus.RetroDofusCell;
import com.retrobot.bot.processor.packet.FightersCoordinatesStartFightData;
import com.retrobot.bot.state.CharacterState;
import com.retrobot.bot.state.MapState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PlayerCoordinatesStartFightProcessor extends PacketProcessor {

    private final CharacterState characterState;
    private final MapState mapState;

    public PlayerCoordinatesStartFightProcessor(CharacterState characterState, MapState mapState) {
        this.characterState = characterState;
        this.mapState = mapState;
    }

    @Override
    public void processPacket(String dofusPacket) {
        FightersCoordinatesStartFightData startFightData = new FightersCoordinatesStartFightData(dofusPacket, characterState);
        log.info("Player current fighting cell id : {}", startFightData.getPlayerCellId());
        RetroDofusCell currentFightCell = mapState.getCurrentMap().get(startFightData.getPlayerCellId());
        //TODO put in fightstate
        characterState.setCurrentFightCell(currentFightCell);
        startFightData.getMonsterPositions().forEach(monsterFightPositionData -> {
            RetroDofusCell monsterFightCell = mapState.getCurrentMap().get(monsterFightPositionData.getCellId());
            log.info("Monster current fighting cell id : {}", monsterFightPositionData.getCellId());
            characterState.getCurrentFightMonsterCells().put(monsterFightPositionData.getMonsterId(), monsterFightCell);
        });
        characterState.setFightMonstersNumber(startFightData.getMonsterPositions().size());
        //TODO we need number on monster to validate end of combat interface
    }

    @Override
    public String getPacketId() {
        return "GIC";
    }
}
