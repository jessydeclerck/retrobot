package processor;

import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.core.JsonProcessingException;
import model.packet.GatheringFinishedData;
import network.BotServer;
import network.message.going.GatheredResourceFinished;
import network.message.going.NewMessage;
import state.CharacterState;

@Slf4j
public class GatheringFinishedProcessor extends PacketProcessor {

    private final CharacterState characterState = CharacterState.getInstance();

    @Override
    public void processPacket(String dofusPacket) {
        GatheringFinishedData gatheringFinishedData = new GatheringFinishedData(dofusPacket);
        log.info("Gathered qty : {}", gatheringFinishedData.getGatheredQty());
        characterState.setGathering(false);
        try {
            BotServer.getInstance().emitMessage(new GatheredResourceFinished(gatheringFinishedData));
        } catch (JsonProcessingException e) {
            log.error("Erreur lors de l'émission du socket de fin de récolte", e);
        }
    }

    @Override
    public String getPacketId() {
        return "IQ";
    }
}
