package processor;

import lombok.extern.slf4j.Slf4j;
import model.packet.MapPacketData;
import state.MapState;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class MapProcessor extends PacketProcessor {

    private final MapState mapState = MapState.getInstance();

    public MapProcessor() {
    }

    public void processPacket(String dofusPacket) {
        MapPacketData mapPacketData = new MapPacketData(dofusPacket);
        log.info(mapPacketData.toString());
        //TimeUtils.sleep(1000);
        mapState.setCurrentMap(mapPacketData.getMap());
        mapState.addAvailableRessources(mapPacketData.getMap().getRessources());
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                log.error("", e);
            }
            mapState.getCurrentMap().getTriggers()
                    .forEach(retroTriggerCell -> log.debug("Trigger {} Next map : {} Next cell : {}", retroTriggerCell.id(), retroTriggerCell.getNextMapId(), retroTriggerCell.getNextCellId()));
            mapState.startRecolte();
        });
    }

    @Override
    public String getPacketId() {
        return "GDM";
    }

}
