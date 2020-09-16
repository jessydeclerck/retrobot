package processor;

import lombok.extern.slf4j.Slf4j;
import service.FightService;
import state.CharacterState;

@Slf4j
public class JoinCombatProcessor extends PacketProcessor {

    private final CharacterState characterState = CharacterState.getInstance();
    private final FightService fightService = FightService.getInstance();

    @Override
    public void processPacket(String dofusPacket) {
        //JoinCombatData joinCombatData = new JoinCombatData(dofusPacket);
        log.info("Combat detecté");
        characterState.setFighting(true);
        fightService.prepareFight();
    }

    @Override
    public String getPacketId() {
        return "GJK2";
    }
}
