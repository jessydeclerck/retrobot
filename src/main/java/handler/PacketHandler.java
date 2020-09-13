package handler;

import lombok.extern.log4j.Log4j2;
import processor.MapProcessor;
import processor.PacketProcessor;
import processor.RessourceProcessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * interactive 1.29 GDF|cell_id|etat_id
 * <p>
 * statut 1: disponible
 * statut 2: en attente
 * statut 3: no disponible
 * statut 4: rechargé - Si vous êtes sur la carte, vous recevrez le 4
 * 5 déjà fauché ?
 */
@Log4j2
public class PacketHandler {

    final private Map<String, PacketProcessor> packetProcessorMap;

    public PacketHandler() {
        this.packetProcessorMap = new HashMap<>();
        this.addPacketProcessor(new MapProcessor());
        this.addPacketProcessor(new RessourceProcessor());
    }

    public void handlePacket(String dofusPackets) {
        List<String> gamePackets = List.of(dofusPackets.split("\0"));
        gamePackets.forEach(gamePacket -> {
            String packetId = gamePacket.substring(0, 3);
            Optional.ofNullable(this.packetProcessorMap.get(packetId)).ifPresent(packetProcessor -> packetProcessor.processPacket(gamePacket));
        });
    }

    private void addPacketProcessor(PacketProcessor packetProcessor) {
        this.packetProcessorMap.put(packetProcessor.getPacketId(), packetProcessor);
    }
}
