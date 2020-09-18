package processor;

import lombok.extern.slf4j.Slf4j;
import model.packet.CharacterData;
import model.packet.CharacterMapData;
import service.BanqueService;
import state.CharacterState;

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
