package processor;

import lombok.extern.slf4j.Slf4j;
import model.packet.PlayerData;
import state.CharacterState;

@Slf4j
public class PlayerDataProcessor extends PacketProcessor {

    private final CharacterState characterState = CharacterState.getInstance();

    @Override
    public void processPacket(String dofusPacket) {
        PlayerData playerData = new PlayerData(dofusPacket);
        log.info("Player id : {}", playerData.getPlayerId());
        characterState.setPlayerId(playerData.getPlayerId());
    }

    @Override
    public String getPacketId() {
        return "ASK";
    }
}
