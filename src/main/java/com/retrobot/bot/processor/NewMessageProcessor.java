package com.retrobot.bot.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.retrobot.bot.processor.packet.NewMessageData;
import com.retrobot.network.BotServer;
import com.retrobot.network.message.going.NewMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
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
