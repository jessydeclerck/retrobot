package processor;

import lombok.extern.slf4j.Slf4j;
import model.packet.TurnBeginsData;
import service.FightService;
import state.CharacterState;

@Slf4j
public class TurnBeginsProcessor extends PacketProcessor {

    private final FightService fightService = FightService.getInstance();

    @Override
    public void processPacket(String dofusPacket) {
        TurnBeginsData turnBeginsData = new TurnBeginsData(dofusPacket);
        if (turnBeginsData.getPlayerId() == CharacterState.getInstance().getPlayerId()) {
            log.info("Tour du joueur detecté");
            try {
                Thread.sleep(2000);
                //TODO
                fightService.moveTowardMonster(CharacterState.getInstance().getCurrentFightCell(), CharacterState.getInstance().getCurrentFightMonsterCells().get(-1));
                //TODO update player and monster position
            } catch (InterruptedException e) {
                log.error("", e);
            }
        }
    }


    @Override
    public String getPacketId() {
        return "GTS";
    }

}
