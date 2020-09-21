package com.retrobot.bot.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.retrobot.bot.processor.packet.GoingToGatherData;
import com.retrobot.bot.state.CharacterState;
import com.retrobot.bot.state.MapState;
import com.retrobot.network.BotServer;
import com.retrobot.network.message.going.GatheringResourceStarted;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GoingToGatherProcessor extends PacketProcessor {

    private final CharacterState characterState;

    private final MapState mapState;

    public GoingToGatherProcessor(CharacterState characterState, MapState mapState) {
        this.characterState = characterState;
        this.mapState = mapState;
    }

    @Override
    public void processPacket(String dofusPacket) {
        GoingToGatherData goingToGatherData = new GoingToGatherData(dofusPacket, mapState);
        characterState.setCurrentGatheringTarget(goingToGatherData.getCell());
        try {
            BotServer.getInstance().emitMessage(new GatheringResourceStarted(goingToGatherData));
        } catch (JsonProcessingException e) {
            log.error("Erreur lors de l'émission du socket de fin de récolte", e);
        }

    }

    @Override
    public String getPacketId() {
        return "GA500";
    }
}
