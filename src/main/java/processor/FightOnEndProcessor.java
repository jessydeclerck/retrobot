package processor;

import lombok.extern.slf4j.Slf4j;
import state.CharacterState;

@Slf4j
public class FightOnEndProcessor extends PacketProcessor {

    private final CharacterState characterState = CharacterState.getInstance();

    @Override
    public void processPacket(String dofusPacket) {
        log.info("Fin du combat detecté");
        characterState.setFighting(false);
    }

    @Override
    public String getPacketId() {
        return "GE";
    }
}
