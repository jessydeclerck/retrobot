package processor;

import lombok.extern.slf4j.Slf4j;
import model.packet.JoinCombatData;
import state.CharacterState;

@Slf4j
public class JoinCombatProcessor extends PacketProcessor{

    @Override
    public void processPacket(String dofusPacket) {
        JoinCombatData joinCombatData = new JoinCombatData(dofusPacket);
        log.info("Combat detecté");
        CharacterState.getInstance().setFighting(true);
    }

    @Override
    public String getPacketId() {
        return "GJK2";
    }
}
