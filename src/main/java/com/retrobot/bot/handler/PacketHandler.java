package com.retrobot.bot.handler;

import com.retrobot.bot.processor.PacketProcessor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
            log.trace(gamePacket);
            if (gamePacket.length() < 3) return;
            String packetId = getPacketId(gamePacket);
            Optional.ofNullable(this.packetProcessorMap.get(packetId)).ifPresent(packetProcessor -> {
                try {
                    packetProcessor.processPacket(gamePacket);
                } catch (Exception e) {
                    log.error("Error while processing packet", e);
                }
            });
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
