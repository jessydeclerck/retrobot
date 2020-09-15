package model.packet;

import fr.arakne.utils.encoding.Base64;
import lombok.Value;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Value
public class CharacterMovementData extends PacketData {

    private final int targetCellId;

    public CharacterMovementData(String fullPacket) {
        super(fullPacket);
        String movementData = fullPacket.replace("GA001", "");
        String lastCellInfo = movementData.substring(movementData.length() - 4);
        this.targetCellId = getCellId(lastCellInfo);
        //TODO current cell when gathering might not be the last one
        log.debug("Character targetedCell : {}", targetCellId);
    }

    //TODO refacto externalize
    private int getCellId(String lastCellInfo) {
        char lastCellInfoArray[] = lastCellInfo.toCharArray();
        //char direction = lastCellInfoArray[0];
        int H = Base64.decode(String.valueOf(lastCellInfoArray[1]));
        int L = Base64.decode(String.valueOf(lastCellInfoArray[2]));
        return ((H & 0xF) << 6) | (L & 0x3F);
    }

}
