package processor;

import lombok.extern.slf4j.Slf4j;
import model.packet.MovementData;

@SuppressWarnings("unused")
@Slf4j
public class MovementProcessor extends PacketProcessor {
    @Override
    public void processPacket(String dofusPacket) {
        MovementData movementData = new MovementData(dofusPacket);
    }

    @Override
    public String getPacketId() {
        return "GA0";
    }
}
