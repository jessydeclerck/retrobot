package processor;

import model.packet.NewMessageData;

public class NewMessageProcessor extends PacketProcessor {
    @Override
    public void processPacket(String dofusPacket) {
        NewMessageData newMessageData = new NewMessageData(dofusPacket);
    }

    @Override
    public String getPacketId() {
        return "cMK";
    }
}
