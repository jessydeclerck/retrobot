package com.retrobot.bot.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.retrobot.bot.async.RetroTaskQueue;
import com.retrobot.bot.processor.packet.GatheringFinishedData;
import com.retrobot.bot.service.DeplacementService;
import com.retrobot.bot.state.CharacterState;
import com.retrobot.network.BotServer;
import com.retrobot.network.message.going.GatheredResourceFinished;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GatheringFinishedProcessor extends PacketProcessor {

    private final CharacterState characterState;

    private final BotServer botServer;

    private final RetroTaskQueue retroTaskQueue;

    private final DeplacementService deplacementService;

    public GatheringFinishedProcessor(CharacterState characterState, BotServer botServer, RetroTaskQueue retroTaskQueue, DeplacementService deplacementService) {
        this.characterState = characterState;
        this.botServer = botServer;
        this.retroTaskQueue = retroTaskQueue;
        this.deplacementService = deplacementService;
    }

    @Override
    public void processPacket(String dofusPacket) {
        GatheringFinishedData gatheringFinishedData = new GatheringFinishedData(dofusPacket);
        log.info("Gathered qty : {}", gatheringFinishedData.getGatheredQty());
        characterState.setGathering(false);
        try {
            botServer.emitMessage(new GatheredResourceFinished(gatheringFinishedData));
        } catch (JsonProcessingException e) {
            log.error("Erreur lors de l'émission du socket de fin de récolte", e);
        }
        if (retroTaskQueue.isEmpty()) { //TODO refacto
            deplacementService.goNextGatherMap();
        }

    }

    @Override
    public String getPacketId() {
        return "IQ";
    }
}
