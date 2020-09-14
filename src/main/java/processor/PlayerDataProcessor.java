package processor;

import lombok.extern.log4j.Log4j2;
import model.packet.PlayerData;
import state.CharacterState;

@Log4j2
public class PlayerDataProcessor extends PacketProcessor {

    @Override
    public void processPacket(String dofusPacket) {
        PlayerData playerData = new PlayerData(dofusPacket);
        log.info("Player id : {}", playerData.getPlayerId()); //TODO can we get another way ? maybe EW+140043545| ou GA;2;140043545;
        //TODO
        CharacterState.getInstance().setPlayerId(playerData.getPlayerId());
    }

    @Override
    public String getPacketId() {
        return "ASK";
    }
}
