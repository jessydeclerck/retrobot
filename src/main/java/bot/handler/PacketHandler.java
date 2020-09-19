package bot.handler;

import lombok.extern.slf4j.Slf4j;
import bot.processor.*;
import bot.service.DeplacementService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Slf4j
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
        //this.addPacketProcessor(new StartsToPlayFightProcessor());
        //this.addPacketProcessor(new StartsLandingOnCellProcessor());
        this.addPacketProcessor(new NewMessageProcessor());
        //this.addPacketProcessor(new CharacterFightingProcessor());
        this.addPacketProcessor(new MovementProcessor());
        this.addPacketProcessor(new FightTurnInformationProcessor());
        this.addPacketProcessor(new FightOnEndProcessor());
        this.addPacketProcessor(new PodsUpdateProcessor());
        this.addPacketProcessor(new JobLevelProcessor());
        this.addPacketProcessor(new CharacterMapProcessor());
        DeplacementService.getInstance().startDeplacement(); //TODO is it the right place to start ?
        //TODO pods et passage niveau
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
