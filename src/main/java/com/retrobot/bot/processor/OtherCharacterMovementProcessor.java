package com.retrobot.bot.processor;

import com.retrobot.bot.processor.packet.OtherCharacterMovementData;
import com.retrobot.bot.state.MapState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OtherCharacterMovementProcessor extends PacketProcessor {

    private final MapState mapState;

    public OtherCharacterMovementProcessor(MapState mapState) {
        this.mapState = mapState;
    }

    @Override
    public void processPacket(String dofusPacket) {
        try {
            OtherCharacterMovementData otherCharacterMovementData = new OtherCharacterMovementData(dofusPacket);
            if (otherCharacterMovementData.getGroupId() < 0) {
                if (mapState.getMonsterPositions().containsKey(otherCharacterMovementData.getGroupId())) {
                    mapState.getMonsterPositions().put(otherCharacterMovementData.getGroupId(), otherCharacterMovementData.getCellId());
                }
            }
        } catch (Exception e) {
            log.info("Couldn't parse GA; packet");
        }
    }

    @Override
    public String getPacketId() {
        return "GA;";
    }
}
