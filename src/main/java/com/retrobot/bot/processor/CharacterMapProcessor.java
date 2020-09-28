package com.retrobot.bot.processor;

import com.retrobot.bot.processor.packet.CharacterData;
import com.retrobot.bot.processor.packet.CharacterMapData;
import com.retrobot.bot.service.BanqueService;
import com.retrobot.bot.state.CharacterState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CharacterMapProcessor extends PacketProcessor {

    private final static List<String> BANK_NPC_ID = List.of("520", "100"); //TODO check other banks

    private final BanqueService banqueService;

    private final CharacterState characterState;

    public CharacterMapProcessor(BanqueService banqueService, CharacterState characterState) {
        this.banqueService = banqueService;
        this.characterState = characterState;
    }

    @Override
    public void processPacket(String dofusPacket) {
        if (characterState.isGoingBank()) {
            CharacterMapData characterMapData = new CharacterMapData(dofusPacket);
            findBankNpc(characterMapData).ifPresent(characterData -> {
                banqueService.viderInventaire(characterData);
            });
        }
        //if (!CharacterState.getInstance().isFighting()) return;
        //CharacterFightingData characterFightingData = new CharacterFightingData(dofusPacket);
    }

    private Optional<CharacterData> findBankNpc(CharacterMapData characterMapData) {
        return characterMapData.getMapCharacters().stream().filter(c -> BANK_NPC_ID.contains(c.getCharacterName())).findAny();
    }

    @Override
    public String getPacketId() {
        return "GM";
    }
}
