package bot.processor;


import lombok.extern.slf4j.Slf4j;
import bot.processor.packet.OnCraftPublicData;
import bot.state.CharacterState;

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
