package processor;

import lombok.extern.slf4j.Slf4j;
import model.packet.MapPacketData;
import state.MapState;
import utils.TimeUtils;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class MapProcessor extends PacketProcessor {

    private final MapState mapState = MapState.getInstance();

    public MapProcessor() {
    }

    public void processPacket(String dofusPacket) {
        log.info(dofusPacket);
        MapPacketData mapPacketData = new MapPacketData(dofusPacket);
        log.info(mapPacketData.toString());
        mapState.setCurrentMap(mapPacketData.getMap());
        mapState.addAvailableRessources(mapPacketData.getMap().getRessources());
        mapState.getCurrentMap().getTriggers()
                .forEach(retroTriggerCell -> log.debug("Trigger {} Next map : {} Next cell : {}", retroTriggerCell.id(), retroTriggerCell.getNextMapId(), retroTriggerCell.getNextCellId()));
        CompletableFuture.runAsync(() -> {
            TimeUtils.sleep(2000);
            //mapState.startRecolte();
            /**if (!CharacterState.getInstance().isGoingBank()) {
             DeplacementService.getInstance().goNextMap();
             if (mapState.getCurrentMap().getId() == 7454) {
             DeplacementService.getInstance().goToBank();
             }
             } else {
             DeplacementService.getInstance().goToBank();
             }*/
        });
        log.info("Current map id : {}", mapPacketData.getMap().getId());
    }

    @Override
    public String getPacketId() {
        return "GDM";
    }

}
