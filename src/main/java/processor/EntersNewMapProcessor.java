package processor;


import lombok.extern.log4j.Log4j2;
import model.packet.EnterNewMapData;
import state.CharacterState;

@Log4j2
public class EntersNewMapProcessor extends PacketProcessor {

    @Override
    public void processPacket(String dofusPacket) {
        EnterNewMapData enterNewMapData = new EnterNewMapData(dofusPacket);
        if (CharacterState.getInstance().getPlayerId() != 0){
            CharacterState.getInstance().setPlayerId(enterNewMapData.getPlayerId());
        }

        log.debug("Fight accomplished/Entered new map");
    }

    @Override
    public String getPacketId() {
        return "EW";
    }
}
