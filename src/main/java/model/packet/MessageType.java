package model.packet;

public enum MessageType {

    DEFAULT(""),
    // TODO : Guild code
    GUILD("??"),
    TEAM("#"),
    RECRUITMENT ("?"),
    TRADE(":"),
    // TODO : PVP Code
    PVP("??"),
    // TODO : Party code
    PARTY("??"),
    UNKNOWN("not Found");

    public final String type;

    MessageType(String type) {
        this.type = type;
    }

    public static MessageType labelOfType(String type) {
        for (MessageType e : values()) {
            if (e.type.equals(type)) {
                return e;
            }
        }
        return UNKNOWN;
    }
}
