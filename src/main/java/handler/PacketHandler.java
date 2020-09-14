package handler;

import lombok.extern.log4j.Log4j2;
import processor.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
    final private Set<String> processedPackets = new HashSet<>();

    public PacketHandler() {
        this.packetProcessorMap = new HashMap<>();
        this.addPacketProcessor(new MapProcessor());
        this.addPacketProcessor(new RessourceProcessor());
        this.addPacketProcessor(new GatheringFinishedProcessor());
        this.addPacketProcessor(new PlayerDataProcessor());
        this.addPacketProcessor(new GoingToGatherProcessor());
        this.addPacketProcessor(new CharacterMovementProcessor());
        this.addPacketProcessor(new JoinCombatProcessor());
        this.addPacketProcessor(new TurnBeginsProcessor());
        this.addPacketProcessor(new PlayerCoordinatesStartFightProcessor());
        this.addPacketProcessor(new OnCraftPublicProcessor());
        this.addPacketProcessor(new StartsToPlayFightProcessor());
        //this.addPacketProcessor(new StartsLandingOnCellProcessor());
        this.addPacketProcessor(new NewMessageProcessor());
        this.addPacketProcessor(new CharacterFightingProcessor());
    }

    public void handlePacket(String dofusPackets) {
        List<String> gamePackets = List.of(dofusPackets.split("\0"));
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
