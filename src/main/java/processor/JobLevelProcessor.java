package processor;

import lombok.extern.slf4j.Slf4j;
import service.RecolteService;

@Slf4j
public class JobLevelProcessor extends PacketProcessor {

    //TODO refacto in interface service
    private final RecolteService recolteService = RecolteService.getInstance();

    @Override
    public void processPacket(String dofusPacket) {
        log.info("Fermeture notification niveau métier");
        recolteService.fermerPopupNiveauMetier();
    }

    @Override
    public String getPacketId() {
        return "JN";
    }
}
