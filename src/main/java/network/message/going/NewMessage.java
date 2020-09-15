package network.message.going;

import lombok.Data;
import model.packet.MessageType;
import model.packet.NewMessageData;
import network.message.WSMessage;

@Data
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
