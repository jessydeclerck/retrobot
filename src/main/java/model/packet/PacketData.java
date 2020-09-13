package model.packet;

public abstract class PacketData {

    protected final String fullPacket;

    public PacketData(String fullPacket) {
        this.fullPacket = fullPacket;
    }

    public String getFullPacket() {
        return fullPacket;
    }

}
