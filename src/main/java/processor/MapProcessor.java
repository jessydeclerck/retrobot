package processor;

import async.RetroTaskQueue;
import async.event.RecolterTaskEvent;
import lombok.extern.log4j.Log4j2;
import model.packet.NewMapPacket;
import utils.TimeUtils;

@Log4j2
public class MapProcessor extends PacketProcessor {

    public MapProcessor() {
    }

    public void processPacket(String dofusPacket) {
        NewMapPacket newMapPacket = new NewMapPacket(dofusPacket);
        log.info(newMapPacket.toString());
        TimeUtils.sleep(1000);
        newMapPacket.getMap().getRessources().forEach(retroRessourceCell -> {
            log.info("Ressource cell id : {} - Position : {},{}", retroRessourceCell.id(), retroRessourceCell.getAbscisse(), retroRessourceCell.getOrdonnee());
            RetroTaskQueue.getInstance().addTask(new RecolterTaskEvent(retroRessourceCell.getWindowRelativeX(), retroRessourceCell.getWindowRelativeY()));
        });
    }

    @Override
    public String getPacketId() {
        return "GDM";
    }

}
