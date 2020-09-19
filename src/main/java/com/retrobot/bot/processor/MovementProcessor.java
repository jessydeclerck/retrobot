package com.retrobot.bot.processor;

import com.retrobot.bot.processor.packet.MovementData;
import com.retrobot.bot.state.CharacterState;
import com.retrobot.bot.state.MapState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MovementProcessor extends PacketProcessor {

    private final CharacterState characterState;
    private final MapState mapState;

    public MovementProcessor(CharacterState characterState, MapState mapState) {
        this.characterState = characterState;
        this.mapState = mapState;
    }

    @Override
    public void processPacket(String dofusPacket) {
        MovementData movementData = new MovementData(dofusPacket);
        if (characterState.getPlayerId() == movementData.getEntityId() && !characterState.isFighting()) {
            log.info("Current player position cell : {}", movementData.getEntityCellId());
            characterState.setCurrentPlayerCell(mapState.getCurrentMap().get(movementData.getEntityCellId()));
        }
    }

    @Override
    public String getPacketId() {
        return "GA0";
    }
}
