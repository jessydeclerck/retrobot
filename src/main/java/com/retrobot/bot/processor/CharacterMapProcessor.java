package com.retrobot.bot.processor;

import com.retrobot.bot.processor.packet.CharacterData;
import com.retrobot.bot.processor.packet.CharacterMapData;
import com.retrobot.bot.service.BanqueService;
import com.retrobot.bot.state.CharacterState;
import com.retrobot.bot.state.MapState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class CharacterMapProcessor extends PacketProcessor {

    private final static List<String> BANK_NPC_ID = List.of("520", "100"); //TODO check other banks

    private final BanqueService banqueService;

    private final CharacterState characterState;

    private final MapState mapState;

    public CharacterMapProcessor(BanqueService banqueService, CharacterState characterState, MapState mapState) {
        this.banqueService = banqueService;
        this.characterState = characterState;
        this.mapState = mapState;
    }

    @Override
    public void processPacket(String dofusPacket) {
        CharacterMapData characterMapData = new CharacterMapData(dofusPacket);
        characterMapData.getMapCharacters().forEach(characterData -> {
            if (characterData.getCharacterId() < 0 && characterData.getCharacterType() == -3) { //if monster
                mapState.getMonsterPositions().put(characterData.getCharacterId(), characterData.getCellId());
                mapState.getMonsterLevels().put(characterData.getCharacterId(), characterData.getGrpLevel());
            }
        });
        if (characterState.isGoingBank()) {
            findBankNpc(characterMapData).ifPresent(banqueService::viderInventaire);
        }
    }

    private Optional<CharacterData> findBankNpc(CharacterMapData characterMapData) {
        return characterMapData.getMapCharacters().stream().filter(Objects::nonNull).filter(c -> BANK_NPC_ID.contains(c.getCharacterName())).findAny();
    }

    @Override
    public String getPacketId() {
        return "GM";
    }
}
