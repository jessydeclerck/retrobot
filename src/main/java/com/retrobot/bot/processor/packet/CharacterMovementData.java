package com.retrobot.bot.processor.packet;

import com.retrobot.bot.processor.common.PathDataDecoder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Value
public class CharacterMovementData extends PacketData {

    int targetCellId;

    public CharacterMovementData(String fullPacket) {
        super(fullPacket);
        String movementData = fullPacket.replace("GA001", "");
        String lastCellInfo = movementData.substring(movementData.length() - 4);
        this.targetCellId = PathDataDecoder.getCellId(lastCellInfo);
        log.debug("Character targetedCell : {}", targetCellId);
    }

}
