package processor;

import lombok.extern.slf4j.Slf4j;
import model.packet.TurnBeginsData;
import service.FightService;
import state.CharacterState;

@Slf4j
public class TurnBeginsProcessor extends PacketProcessor {

    @Override
    public void processPacket(String dofusPacket) {
        TurnBeginsData turnBeginsData = new TurnBeginsData(dofusPacket);
        if (turnBeginsData.getPlayerId() == CharacterState.getInstance().getPlayerId()) {
            log.info("Tour du joueur detecté");
            try {
                Thread.sleep(2000);
                new FightService().moveTowardMonster(CharacterState.getInstance().getCurrentFightCell(), CharacterState.getInstance().getCurrentFightMonsterCells().get(-1));
                //TODO update player and monster position
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public String getPacketId() {
        return "GTS";
    }

    //GJK2|0|1|0|30000|4 debut temps placement onJoin
    //GTS140043545|29000 DEBUT TOUR onTurnStart
    //GPeEe8fAfMfNgbgrgG|babbbnbRcfcwcYdo|0 case placement combat ? onPositionStart
    //GA;300;140043545;163,107,905,1,31,2,1 lancer de sort ? onActions
    //GCK|1|Carlatorium resultat combat ? onCreate
    //GE6868|140043545|0|2;140043545;Carlatorium;20;0;171000;194512;202000;29;;;;2|0;-1;59;5;1;;;;;;;; onEnd
    //GR1140043545 onReady
    //GTM|-1;0;40;4;3;316;;40|-2;0;16;4;9;416;;16|140043545;0;260;6;3;178;;260
    //GIC|140043545;307;1 onPlayersCoordinates

    /**
     * 18:01:43.639 [main] INFO  handler.PacketHandler - GIC|-1;316;1|-2;416;1|140043545;178;1 onPlayersCoordinates
     * 18:01:43.639 [main] INFO  handler.PacketHandler - GS onStartToPlay
     * 18:01:43.639 [main] INFO  handler.PacketHandler - GTL|140043545|-2|-1 onTurnlist
     * 18:01:43.639 [main] INFO  handler.PacketHandler - Gd41;0;;10;0;10;0 about challenge
     * 18:01:45.670 [main] INFO  handler.PacketHandler - GTM|-1;0;40;4;3;316;;40|-2;0;16;4;9;416;;16|140043545;0;260;6;3;178;;260 onTurnMiddle
     * 18:01:45.670 [main] INFO  handler.PacketHandler - GTS140043545|29000 onTurnStart
     *
     * 18:03:22.989 [main] INFO  handler.PacketHandler - GT defaultProcessAction
     *
     * 18:03:37.801 [main] INFO  handler.PacketHandler - As194512,171000,202000|25404|1|3|0~0,0,0,0,0,0|256,260|9780,10000|398|101|6,0,0,0,6|3,0,0,0,3|0,75,0,0|0,110,0,0|0,0,0,0|0,15,0,0|0,0,0,0|72,104,0,0|0,0,0,0|1,0,0,0|0,4,0,0|0,0,0,0|0,0,0,0|0,3,0,0|0,0,0,0|0,0,0,0|0,0,0,0|0,0,0,0|0,0,0,0|0,0,0,0|0,0,0,0|0,0,0,0|0,0,0,0|0,0,0,0|0,0,0,0|0,0,0,0|0,0,0,0|0,0,0,0|0,0,0,0|0,0,0,0|0,0,0,0|0,0,0,0|0,0,0,0|0,0,0,0|0,0,0,0|0,0,0,0|0,0,0,0|0,0,0,0|0,0,0,0|0,0,0,0|0,0,0,0|0,0,0,0|50 onStats
     * 18:03:37.801 [main] INFO  handler.PacketHandler - GAS140043545 onActionsStart
     * 18:03:37.801 [main] INFO  handler.PacketHandler - GA;300;140043545;161,393,902,5,31,2,1 onActions
     * 18:03:37.801 [main] INFO  handler.PacketHandler - GA;100;140043545;-2,-16 onActions
     * 18:03:37.801 [main] INFO  handler.PacketHandler - GA;103;140043545;-2 onActions
     * 18:03:37.801 [main] INFO  handler.PacketHandler - GIE320;;1;;;;1;161 onEffect
     * 18:03:37.801 [main] INFO  handler.PacketHandler - GA;102;140043545;140043545,-4
     * 18:03:37.801 [main] INFO  handler.PacketHandler - GAF0|140043545 onActionsFinish
     * 18:03:37.801 [main] INFO  handler.PacketHandler - BD
     *
     * 18:03:37.801 [main] INFO  handler.PacketHandler - BD650|8|14
     * 18:03:40.135 [main] INFO  handler.PacketHandler - GKK0
     *
     * 18:03:40.186 [main] INFO  handler.PacketHandler - BN
     */
}
