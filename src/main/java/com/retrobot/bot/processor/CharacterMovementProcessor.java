package com.retrobot.bot.processor;

import com.retrobot.bot.model.dofus.RetroDofusCell;
import com.retrobot.bot.model.dofus.RetroDofusMap;
import com.retrobot.bot.processor.packet.CharacterMovementData;
import com.retrobot.bot.state.CharacterState;
import com.retrobot.bot.state.MapState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CharacterMovementProcessor extends PacketProcessor {

    private final CharacterState characterState;
    private final MapState mapState;

    public CharacterMovementProcessor(CharacterState characterState, MapState mapState) {
        this.characterState = characterState;
        this.mapState = mapState;
    }

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
