package processor;

import lombok.extern.log4j.Log4j2;
import model.packet.GatheringFinishedData;
import state.CharacterState;

@Log4j2
public class GatheringFinishedProcessor extends PacketProcessor{

    @Override
    public void processPacket(String dofusPacket) {
        GatheringFinishedData gatheringFinishedData = new GatheringFinishedData(dofusPacket);
        log.debug("Gathered qty : {}", gatheringFinishedData.getGatheredQty());
        CharacterState.getInstance().setGathering(false);
    }

    @Override
    public String getPacketId() {
        return "IQ";
    }
}
