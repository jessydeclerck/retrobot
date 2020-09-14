package processor;

import lombok.extern.log4j.Log4j2;
import model.packet.MapPacketData;
import state.MapState;
import utils.TimeUtils;

@Log4j2
public class MapProcessor extends PacketProcessor {

    private final MapState mapState = MapState.getInstance();

    public MapProcessor() {
    }

    public void processPacket(String dofusPacket) {
        MapPacketData mapPacketData = new MapPacketData(dofusPacket);
        log.info(mapPacketData.toString());
        TimeUtils.sleep(1000);
        mapState.setCurrentMap(mapPacketData.getMap());
        mapState.addAvailableRessources(mapPacketData.getMap().getRessources());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            log.error(e);
        }
        mapState.getCurrentMap().getTriggers().forEach(retroTriggerCell -> log.info("Trigger {} Next map : {} Next cell : {}", retroTriggerCell.id(), retroTriggerCell.getNextMapId(), retroTriggerCell.getNextCellId()));
        mapState.startRecolte();
    }

    @Override
    public String getPacketId() {
        return "GDM";
    }

}
