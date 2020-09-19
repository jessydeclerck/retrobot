package com.retrobot.bot.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.retrobot.bot.processor.packet.NewMessageData;
import com.retrobot.network.BotServer;
import com.retrobot.network.message.going.NewMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NewMessageProcessor extends PacketProcessor {

    @Autowired
    private BotServer botServer;

    @Override
    public void processPacket(String dofusPacket) {
        NewMessageData newMessageData = new NewMessageData(dofusPacket);
        try {
            botServer.emitMessage(new NewMessage(newMessageData));
        } catch (JsonProcessingException e) {
            log.error("Erreur lors de l'émission du socket de Nouveau message", e);
        }

    }

    @Override
    public String getPacketId() {
        return "cMK";
    }
}
