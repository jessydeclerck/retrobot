package processor;

import lombok.extern.slf4j.Slf4j;
import model.packet.GatheringFinishedData;
import state.CharacterState;

@Slf4j
public class GatheringFinishedProcessor extends PacketProcessor {

    private final CharacterState characterState = CharacterState.getInstance();

    @Override
    public void processPacket(String dofusPacket) {
        GatheringFinishedData gatheringFinishedData = new GatheringFinishedData(dofusPacket);
        log.debug("Gathered qty : {}", gatheringFinishedData.getGatheredQty());
        characterState.setGathering(false);
    }

    @Override
    public String getPacketId() {
        return "IQ";
    }
}
