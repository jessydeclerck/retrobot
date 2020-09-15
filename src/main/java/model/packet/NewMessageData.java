package model.packet;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NewMessageData extends PacketData {

    String content;
    String fromId;
    String fromName;
    MessageType type;

    public NewMessageData(String fullPacket) {
        super(fullPacket);
        String[] parsedPacket = fullPacket.split("\\|");
        type = parsedPacket[0].split("cMK").length > 1 ? MessageType.labelOfType(parsedPacket[0].split("cMK")[1]) : MessageType.labelOfType("");//MessageType.labelOfType([1]);
        fromId = parsedPacket[1];
        fromName = parsedPacket[2];
        content = parsedPacket[3];
        log.info("Nouveau message de type {} de {} contenant {}", type, fromName, content);
    }
}
