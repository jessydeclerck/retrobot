package processor;

import lombok.extern.slf4j.Slf4j;
import model.packet.PodsUpdateData;
import service.DeplacementService;
import state.CharacterState;

@Slf4j
public class PodsUpdateProcessor extends PacketProcessor {

    private final CharacterState characterState = CharacterState.getInstance();
    private final DeplacementService deplacementService = DeplacementService.getInstance();

    @Override
    public void processPacket(String dofusPacket) {
        PodsUpdateData podsUpdateData = new PodsUpdateData(dofusPacket);
        characterState.setCurrentPods(podsUpdateData.getCurrentPods());
        characterState.setMaxPods(podsUpdateData.getMaxPods());
        log.info("Pods : {}/{}", podsUpdateData.getCurrentPods(), podsUpdateData.getMaxPods());
        if ((double) podsUpdateData.getCurrentPods() / podsUpdateData.getMaxPods() > 0.90) {
            deplacementService.goToBank();
        }
        //Ow1046|2076
    }

    @Override
    public String getPacketId() {
        return "Ow";
    }
}
