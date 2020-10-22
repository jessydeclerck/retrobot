package com.retrobot.bot.processor;

import com.retrobot.bot.processor.packet.FightOnMapData;
import com.retrobot.bot.state.MapState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FightOnMapProcessor extends PacketProcessor {

    private final MapState mapState;

    public FightOnMapProcessor(MapState mapState) {
        this.mapState = mapState;
    }

    @Override
    public void processPacket(String dofusPacket) {
        try {
            FightOnMapData fightOnMapData = new FightOnMapData(dofusPacket);
            mapState.getMonsterPositions().remove(fightOnMapData.getMonsterId());
            mapState.getMonsterLevels().remove(fightOnMapData.getMonsterId());
            log.info("Monster group {} is now unavailable", fightOnMapData.getMonsterId());
        } catch (Exception e) {
            log.info("Gc packet couldn't be parsed");
        }
    }

    @Override
    public String getPacketId() {
        return "Gc";
    }
}
