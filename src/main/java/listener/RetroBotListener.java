package listener;

import handler.PacketHandler;
import lombok.extern.log4j.Log4j2;
import org.pcap4j.core.PacketListener;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.Packet;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Log4j2
public class RetroBotListener implements PacketListener {

    final private PacketHandler packetHandler;

    public RetroBotListener() {
        this.packetHandler = new PacketHandler();
    }

    @Override
    public void gotPacket(Packet packet) {
        IpV4Packet ipV4Packet = packet.get(IpV4Packet.class);
        String dofusPackets = null;
        try {
            dofusPackets = new String(ipV4Packet.getPayload().getPayload().getRawData(), StandardCharsets.UTF_8);
        }catch (NullPointerException ignored){
            return;
        }
        if (dofusPackets.isBlank()) {
            return;
        }
        log.info(dofusPackets);
        packetHandler.handlePacket(dofusPackets);
    }


}
