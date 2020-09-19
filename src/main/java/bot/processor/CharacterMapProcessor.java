package bot.processor;

import lombok.extern.slf4j.Slf4j;
import bot.processor.packet.CharacterData;
import bot.processor.packet.CharacterMapData;
import bot.service.BanqueService;
import bot.state.CharacterState;

import java.util.Optional;

@Slf4j
public class CharacterMapProcessor extends PacketProcessor {

    private final static String BANK_NPC_ID = "520";

    private final BanqueService banqueService = BanqueService.getInstance();

    @Override
    public void processPacket(String dofusPacket) {
        if(CharacterState.getInstance().isGoingBank()){
            CharacterMapData characterMapData = new CharacterMapData(dofusPacket);
            findBankNpc(characterMapData).ifPresent(characterData -> {
                banqueService.viderInventaire(characterData);
            });
        }
        //if (!CharacterState.getInstance().isFighting()) return;
        //CharacterFightingData characterFightingData = new CharacterFightingData(dofusPacket);
    }

    private Optional<CharacterData> findBankNpc(CharacterMapData characterMapData) {
        return characterMapData.getMapCharacters().stream().filter(c -> BANK_NPC_ID.equals(c.getCharacterName())).findAny();
    }

    @Override
    public String getPacketId() {
        return "GM";
    }
}
