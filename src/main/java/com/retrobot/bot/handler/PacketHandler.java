package com.retrobot.bot.handler;

import com.retrobot.bot.processor.PacketProcessor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class PacketHandler {

    final private Map<String, PacketProcessor> packetProcessorMap = new HashMap<>();
    final private Set<String> processedPackets = new HashSet<>();

    public PacketHandler(List<PacketProcessor> packetProcessors) {
        packetProcessors.forEach(this::addPacketProcessor);
    }

    public void handlePacket(String dofusPackets) {
        List<String> gamePackets = Arrays.asList(dofusPackets.split("\0"));
        gamePackets.forEach(gamePacket -> {
            log.info(gamePacket);
            if (gamePacket.length() < 3) return;
            String packetId = getPacketId(gamePacket);
            Optional.ofNullable(this.packetProcessorMap.get(packetId)).ifPresent(packetProcessor -> packetProcessor.processPacket(gamePacket));
        });
    }

    private void addPacketProcessor(PacketProcessor packetProcessor) {
        this.packetProcessorMap.put(packetProcessor.getPacketId(), packetProcessor);
        this.processedPackets.add(packetProcessor.getPacketId());
    }

    private String getPacketId(String gamePacket) {
        return processedPackets.stream().filter(gamePacket::startsWith).findFirst().orElse("");
    }

}
