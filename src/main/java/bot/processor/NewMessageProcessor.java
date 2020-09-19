package bot.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import bot.processor.packet.NewMessageData;
import network.BotServer;
import network.message.going.NewMessage;

@Slf4j
public class NewMessageProcessor extends PacketProcessor {
    @Override
    public void processPacket(String dofusPacket) {
        NewMessageData newMessageData = new NewMessageData(dofusPacket);
        try {
            BotServer.getInstance().emitMessage(new NewMessage(newMessageData));
        } catch (JsonProcessingException e) {
            log.error("Erreur lors de l'émission du socket de Nouveau message", e);
        }

    }

    @Override
    public String getPacketId() {
        return "cMK";
    }
}
