package processor;

import lombok.extern.slf4j.Slf4j;
import model.packet.TurnBeginsData;
import service.FightService;
import state.CharacterState;
import utils.TimeUtils;

@Slf4j
public class TurnBeginsProcessor extends PacketProcessor {

    private final FightService fightService = FightService.getInstance();
    private final CharacterState characterState = CharacterState.getInstance();

    @Override
    public void processPacket(String dofusPacket) {
        TurnBeginsData turnBeginsData = new TurnBeginsData(dofusPacket);
        if (turnBeginsData.getPlayerId() == characterState.getPlayerId()) {
            log.info("Tour du joueur detecté");
            TimeUtils.sleep(1500);
            //TODO
            fightService.playTurn();
            //TODO update player and monster position
        }
    }


    @Override
    public String getPacketId() {
        return "GTS";
    }

}
