package processor;

import lombok.extern.slf4j.Slf4j;
import model.packet.MapPacketData;
import service.DeplacementService;
import state.CharacterState;
import state.MapState;
import utils.TimeUtils;

import java.awt.*;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class MapProcessor extends PacketProcessor {

    private final MapState mapState = MapState.getInstance();

    public MapProcessor() {
    }

    public void processPacket(String dofusPacket) {
        //log.info(dofusPacket);
        MapPacketData mapPacketData = new MapPacketData(dofusPacket);
        log.info(mapPacketData.toString());
        mapState.setCurrentMap(mapPacketData.getMap());
        mapState.addAvailableRessources(mapPacketData.getMap().getRessources());
        mapState.getCurrentMap().getTriggers()
                .forEach(retroTriggerCell -> log.debug("Trigger {} Next map : {} Next cell : {}", retroTriggerCell.id(), retroTriggerCell.getNextMapId(), retroTriggerCell.getNextCellId()));
        CompletableFuture.runAsync(() -> {
            TimeUtils.sleep(2000);
            //mapState.startRecolte();
            //TODO externalize
            if (!CharacterState.getInstance().isGoingBank()) {
                if (mapState.getCurrentMap().getId() == 7454) {
                    DeplacementService.getInstance().goToBank();
                }
                DeplacementService.getInstance().goNextMap();
            } else {
                DeplacementService.getInstance().goToBank();
            }
        });
        log.info("Current map id : {}", mapPacketData.getMap().getId());
    }

    @Override
    public String getPacketId() {
        return "GDM";
    }

    private void displayMapId(MapPacketData mapPacketData) {
        SystemTray tray = SystemTray.getSystemTray();
        TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().createImage(new byte[0]));
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        trayIcon.displayMessage(mapPacketData.getMap().getX() + "," + mapPacketData.getMap().getY(), "" + mapPacketData.getMap().getId(), TrayIcon.MessageType.INFO);
    }

}
