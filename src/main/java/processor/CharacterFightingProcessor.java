package processor;

import state.CharacterState;

public class CharacterFightingProcessor extends PacketProcessor {
    @Override
    public void processPacket(String dofusPacket) {
        if (!CharacterState.getInstance().isFighting()) return;
        //CharacterFightingData characterFightingData = new CharacterFightingData(dofusPacket);
    }

    @Override
    public String getPacketId() {
        return "GM";
    }
}
