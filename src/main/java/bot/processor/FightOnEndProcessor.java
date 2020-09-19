package bot.processor;

import lombok.extern.slf4j.Slf4j;
import bot.service.FightService;
import bot.state.CharacterState;

@Slf4j
public class FightOnEndProcessor extends PacketProcessor {

    private final CharacterState characterState = CharacterState.getInstance();
    private final FightService fightService = FightService.getInstance();

    @Override
    public void processPacket(String dofusPacket) {
        log.info("Fin du combat detecté");
        characterState.setFighting(false);
        fightService.fermerFenetreFinCombat();
    }

    @Override
    public String getPacketId() {
        return "GE";
    }
}
