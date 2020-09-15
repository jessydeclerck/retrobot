package model.packet;

import fr.arakne.utils.encoding.Base64;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@SuppressWarnings("unused")
@Slf4j
@Value
public class MovementData extends PacketData {

    int monsterId;
    int monsterCellId;

    public MovementData(String fullPacket) {
        super(fullPacket);
        String movementData = fullPacket.replace("GA0", "");
        String lastCellInfo = movementData.substring(movementData.length() - 4);
        this.monsterCellId = getCellId(lastCellInfo);
        this.monsterId = Integer.parseInt(movementData.split(";")[1]);
    }

    private int getCellId(String lastCellInfo) {
        char lastCellInfoArray[] = lastCellInfo.toCharArray();
        //char direction = lastCellInfoArray[0];
        int H = Base64.decode(String.valueOf(lastCellInfoArray[1]));
        int L = Base64.decode(String.valueOf(lastCellInfoArray[2]));
        return ((H & 0xF) << 6) | (L & 0x3F);
    }

}
