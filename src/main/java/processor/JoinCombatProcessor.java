package processor;

import lombok.extern.slf4j.Slf4j;
import state.CharacterState;

@Slf4j
public class JoinCombatProcessor extends PacketProcessor {

    private final CharacterState characterState = CharacterState.getInstance();

    @Override
    public void processPacket(String dofusPacket) {
        //JoinCombatData joinCombatData = new JoinCombatData(dofusPacket);
        log.info("Combat detecté");
        characterState.setFighting(true);
    }

    @Override
    public String getPacketId() {
        return "GJK2";
    }
}
