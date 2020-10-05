package com.retrobot.bot.processor.packet;

import com.retrobot.bot.processor.common.PathDataDecoder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Value
public class OtherCharacterMovementData extends PacketData {

    int groupId;
    int cellId;


    public OtherCharacterMovementData(String fullPacket) {
        super(fullPacket);
        //GA;1;-1;acTddL
        String movementData = fullPacket.replace("GA;", "");
        String[] movementDataArray = movementData.split(";");
        this.groupId = Integer.parseInt(movementDataArray[1]);
        String pathData = movementDataArray[2];
        String lastCellInfo = pathData.substring(pathData.length() - 3);
        this.cellId = PathDataDecoder.getCellId(lastCellInfo);
    }
}
