package processor;


import lombok.extern.log4j.Log4j2;
import model.packet.OnCraftPublicData;
import state.CharacterState;

@Log4j2
public class OnCraftPublicProcessor extends PacketProcessor {

    @Override
    public void processPacket(String dofusPacket) {
        OnCraftPublicData onCraftPublicData = new OnCraftPublicData(dofusPacket);
        if (CharacterState.getInstance().getPlayerId() != 0){
            CharacterState.getInstance().setPlayerId(onCraftPublicData.getPlayerId());
        }

        log.debug("Fight accomplished/Entered new map");
    }

    @Override
    public String getPacketId() {
        return "EW";
    }
}
