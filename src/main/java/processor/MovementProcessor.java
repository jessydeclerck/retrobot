package processor;

import lombok.extern.slf4j.Slf4j;
import model.packet.MovementData;

@Slf4j
public class MovementProcessor extends PacketProcessor {
    @Override
    public void processPacket(String dofusPacket) {
        MovementData movementData = new MovementData(dofusPacket);
    }

    /**
     * 19:21:35.355 [main] INFO  handler.PacketHandler - GA0;1;-1;afgdfIfe1
     * 19:21:37.732 [main] INFO  handler.PacketHandler - GA;129;-1;-1,-5
     * 19:21:38.897 [main] INFO  handler.PacketHandler - GTF-1
     * 19:21:38.948 [main] INFO  handler.PacketHandler - GTR-1
     * 19:21:38.948 [main] INFO  handler.PacketHandler - GT
     * <p>
     * 19:21:38.948 [main] INFO  handler.PacketHandler - GTM|-1;0;63;6;5;309;;106|140043545;0;262;6;3;293;;265
     * 19:21:38.948 [main] INFO  handler.PacketHandler - GTS140043545|29000
     * 19:21:38.948 [main] INFO  processor.TurnBeginsProcessor - Tour du joueur detecté
     * 19:21:42.159 [main] INFO  handler.PacketHandler - GA001hd7
     * <p>
     * 19:21:42.210 [main] INFO  handler.PacketHandler - GAS140043545
     * 19:21:42.210 [main] INFO  handler.PacketHandler - GA0;1;140043545;aeLhd7
     *
     * @return
     */

    @Override
    public String getPacketId() {
        return "GA0";
    }
}
