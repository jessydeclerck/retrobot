package processor;

import lombok.Getter;

@Getter
public abstract class PacketProcessor {

    protected PacketProcessor() {
    }

    abstract public void processPacket(String dofusPacket);

    abstract public String getPacketId();

}
