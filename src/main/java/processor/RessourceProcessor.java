package processor;

import lombok.extern.log4j.Log4j2;
import model.packet.ResourceUpdatePacket;
import model.packet.RessourceStatus;
import state.CharacterState;

@Log4j2
public class RessourceProcessor extends PacketProcessor {

    private final CharacterState characterState;

    public RessourceProcessor() {
        this.characterState = CharacterState.getInstance();
    }

    @Override
    public void processPacket(String dofusPacket) {
        ResourceUpdatePacket resourceUpdatePacket = new ResourceUpdatePacket(dofusPacket);
        if (RessourceStatus.BUSY.equals(resourceUpdatePacket.getStatus())) {
            //TODO use GA1 to identify who is gathering
            log.info("Character is gathering");
            characterState.setGathering(true);
        }
        if (RessourceStatus.GONE.equals(resourceUpdatePacket.getStatus())) {
            log.info("Gathering done");
            characterState.setGathering(false);
        }
        log.info(resourceUpdatePacket.toString());
    }

    @Override
    public String getPacketId() {
        return "GDF";
    }
}
