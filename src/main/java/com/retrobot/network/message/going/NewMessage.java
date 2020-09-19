package com.retrobot.network.message.going;

import com.retrobot.bot.processor.packet.MessageType;
import com.retrobot.bot.processor.packet.NewMessageData;
import com.retrobot.network.message.WSMessage;
import lombok.Value;

@Value
public class NewMessage extends WSMessage {

    private MessageType messageType;
    private String from;
    private String content;

    public NewMessage(NewMessageData newMessageData) {
        super("message");
        this.messageType = newMessageData.getType();
        this.from = newMessageData.getFromName();
        this.content = newMessageData.getContent();
    }
}
