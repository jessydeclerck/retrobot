package processor;

import lombok.extern.slf4j.Slf4j;
import model.packet.CharacterFightingData;
import state.CharacterState;

@Slf4j
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
