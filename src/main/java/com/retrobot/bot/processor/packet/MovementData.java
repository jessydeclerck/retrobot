package com.retrobot.bot.processor.packet;

import fr.arakne.utils.encoding.Base64;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Value
public class MovementData extends PacketData {

    int entityId;
    int entityCellId;

    public MovementData(String fullPacket) {
        super(fullPacket);
        String movementData = fullPacket.replace("GA0;", "");
        String[] movementDataArray = movementData.split(";");
        String lastCellInfo = movementDataArray[2].substring(movementDataArray[2].length() - 3);
        if (StringUtils.isNumeric(lastCellInfo)) { //data cant be extracted
            entityId = 0;
            entityCellId = 0;
            return;
        }
        this.entityId = Integer.parseInt(movementDataArray[1]);
        this.entityCellId = getCellId(lastCellInfo);
    }

    private int getCellId(String lastCellInfo) {
        char lastCellInfoArray[] = lastCellInfo.toCharArray();
        //char direction = lastCellInfoArray[0];
        int H = Base64.decode(String.valueOf(lastCellInfoArray[1]));
        int L = Base64.decode(String.valueOf(lastCellInfoArray[2]));
        return ((H & 0xF) << 6) | (L & 0x3F);
    }

}
