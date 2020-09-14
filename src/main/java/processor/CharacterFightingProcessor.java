package processor;

import model.packet.CharacterFightingData;

public class CharacterFightingProcessor extends PacketProcessor {
    @Override
    public void processPacket(String dofusPacket) {
        CharacterFightingData characterFightingData = new CharacterFightingData(dofusPacket);
    }

    @Override
    public String getPacketId() {
        return "GM";
    }
}
