package bot.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import bot.processor.packet.GoingToGatherData;
import network.BotServer;
import network.message.going.GatheringResourceStarted;
import bot.state.CharacterState;

@Slf4j
public class GoingToGatherProcessor extends PacketProcessor {

    private final CharacterState characterState = CharacterState.getInstance();

    @Override
    public void processPacket(String dofusPacket) {
        GoingToGatherData goingToGatherData = new GoingToGatherData(dofusPacket);
        characterState.setCurrentGatheringTarget(goingToGatherData.getCell());
        try {
            BotServer.getInstance().emitMessage(new GatheringResourceStarted(goingToGatherData));
        } catch (JsonProcessingException e) {
            log.error("Erreur lors de l'émission du socket de fin de récolte", e);
        }

    }

    @Override
    public String getPacketId() {
        return "GA500";
    }
}
