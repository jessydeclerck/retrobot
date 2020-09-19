package com.retrobot.bot.listener;

import com.retrobot.bot.handler.PacketHandler;
import com.retrobot.bot.processor.PacketProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.pcap4j.core.PacketListener;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.Packet;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
public class RetroBotListener implements PacketListener {

    final private PacketHandler packetHandler;

    public RetroBotListener(List<PacketProcessor> packetProcessors) {
        this.packetHandler = new PacketHandler(packetProcessors);
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
        if (StringUtils.isBlank(dofusPackets)) {
            return;
        }
        packetHandler.handlePacket(dofusPackets);
    }


}
