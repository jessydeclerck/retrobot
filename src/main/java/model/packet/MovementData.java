package model.packet;

import fr.arakne.utils.encoding.Base64;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Value
public class MovementData extends PacketData {

    private final int monsterId;
    private final int monsterCellId;

    public MovementData(String fullPacket) {
        super(fullPacket);
        String movementData = fullPacket.replace("GA0", "");
        String lastCellInfo = movementData.substring(movementData.length() - 4);
        this.monsterCellId = getCellId(lastCellInfo);
        this.monsterId = Integer.parseInt(movementData.split(";")[1]);
        //TODO current cell when gathering might not be the last one

    }

    //TODO refacto externalize
    private int getCellId(String lastCellInfo) {
        char lastCellInfoArray[] = lastCellInfo.toCharArray();
        //char direction = lastCellInfoArray[0];
        int H = Base64.decode(String.valueOf(lastCellInfoArray[1]));
        int L = Base64.decode(String.valueOf(lastCellInfoArray[2]));
        return ((H & 0xF) << 6) | (L & 0x3F);
    }

    /**
     * 19:21:35.355 [main] INFO  handler.PacketHandler - GA0;1;-1;afgdfIfe1
     * 19:21:37.732 [main] INFO  handler.PacketHandler - GA;129;-1;-1,-5
     * 19:21:38.897 [main] INFO  handler.PacketHandler - GTF-1
     * 19:21:38.948 [main] INFO  handler.PacketHandler - GTR-1
     * 19:21:38.948 [main] INFO  handler.PacketHandler - GT
     *
     * 19:21:38.948 [main] INFO  handler.PacketHandler - GTM|-1;0;63;6;5;309;;106|140043545;0;262;6;3;293;;265
     * 19:21:38.948 [main] INFO  handler.PacketHandler - GTS140043545|29000
     * 19:21:38.948 [main] INFO  processor.TurnBeginsProcessor - Tour du joueur detecté
     * 19:21:42.159 [main] INFO  handler.PacketHandler - GA001hd7
     *
     * 19:21:42.210 [main] INFO  handler.PacketHandler - GAS140043545
     * 19:21:42.210 [main] INFO  handler.PacketHandler - GA0;1;140043545;aeLhd7
     * @return
     */
}
