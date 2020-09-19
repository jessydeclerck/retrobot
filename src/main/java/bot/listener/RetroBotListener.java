package bot.listener;

import bot.handler.PacketHandler;
import lombok.extern.slf4j.Slf4j;
import org.pcap4j.core.PacketListener;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.Packet;

import java.nio.charset.StandardCharsets;

@Slf4j
public class RetroBotListener implements PacketListener {

    final private PacketHandler packetHandler;

    public RetroBotListener() {
        this.packetHandler = new PacketHandler();
    }

    @Override
    public void gotPacket(Packet packet) {
        IpV4Packet ipV4Packet = packet.get(IpV4Packet.class);
        String dofusPackets;
        try {
            dofusPackets = new String(ipV4Packet.getPayload().getPayload().getRawData(), StandardCharsets.UTF_8);
        } catch (NullPointerException ignored) {
            return;
        }
        if (dofusPackets.isBlank()) {
            return;
        }
        packetHandler.handlePacket(dofusPackets);
    }


}
