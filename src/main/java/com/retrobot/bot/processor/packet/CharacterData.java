package com.retrobot.bot.processor.packet;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class CharacterData extends PacketData {

    private int cellId;
    private int characterId;
    private String characterName;

    public CharacterData(String characterString) {
        super(characterString);
        String characterData[] = characterString.split(";");
        this.cellId = Integer.parseInt(characterData[0]);
        this.characterId = Integer.parseInt(characterData[3]);
        this.characterName = characterData[4];
    }
}
