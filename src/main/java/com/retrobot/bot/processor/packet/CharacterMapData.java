package com.retrobot.bot.processor.packet;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Getter
public class CharacterMapData extends PacketData {

    private List<CharacterData> mapCharacters = new ArrayList<>();

    //GM|+182;1;0;-1;520;-4;9048^100;0;-1;-1;-1;0,0,0,0,0;;0|+272;1;0;-2;522;-4;9048^100;0;-1;-1;-1;0,0,0,0,0;;0
    public CharacterMapData(String fullPacket) {
        super(fullPacket);
        String[] characters = fullPacket.replace("GM|+", "").split("\\|");
        Arrays.stream(characters).forEach(character -> {
            try {
                CharacterData characterData = new CharacterData(character);
                mapCharacters.add(characterData);
            } catch (Exception e) {
                log.info("Character data (GM) couldn't be parsed");
            }
        });
    }


}
