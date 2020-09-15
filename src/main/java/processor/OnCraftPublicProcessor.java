package processor;


import lombok.extern.slf4j.Slf4j;
import model.packet.OnCraftPublicData;
import state.CharacterState;

@Slf4j
public class OnCraftPublicProcessor extends PacketProcessor {

    private final CharacterState characterState = CharacterState.getInstance();

    @Override
    public void processPacket(String dofusPacket) {
        OnCraftPublicData onCraftPublicData = new OnCraftPublicData(dofusPacket);
        if (characterState.getPlayerId() == 0) {
            log.info("Player id has been set : {}", onCraftPublicData.getPlayerId());
            characterState.setPlayerId(onCraftPublicData.getPlayerId());
        }
    }

    @Override
    public String getPacketId() {
        return "EW";
    }
}
