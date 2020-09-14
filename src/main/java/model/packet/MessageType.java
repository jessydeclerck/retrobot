package model.packet;

public enum MessageType {

    DEFAULT(""),
    // TODO : Guild code
    GUILD("??"),
    PRIVATE_TOWARD("T"),
    PRIVATE_FROM("F"),
    TEAM("#"),
    RECRUITMENT ("?"),
    TRADE(":"),
    // TODO : PVP Code
    PVP("??"),
    PARTY("$"),
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
