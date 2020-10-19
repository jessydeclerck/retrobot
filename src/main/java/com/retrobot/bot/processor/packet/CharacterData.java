package com.retrobot.bot.processor.packet;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class CharacterData extends PacketData {

    private int cellId;
    private int characterId;
    private String characterName;
    private int grpLevel = 0;
    private int characterType;

    // GM|+317;1;2;-2;371,48,79,78,48;-3;1209^100,1569^110,1560^105,1561^90,1569^105;46,15,8,4,14;-1,-1,-1;0,0,0,0;-1,-1,-1;0,0,0,0;-1,-1,-1;0,0,0,0;-1,-1,-1;0,0,0,0;-1,-1,-1;0,0,0,0;|+227;5;1;-3;79,78,78,59,371,78;-3;1560^90,1561^95,1561^90,1565^100,1209^90,1561^105;2,5,4,5,38,7;-1,-1,-1;0,0,0,0;-1,-1,-1;0,0,0,0;-1,-1,-1;0,0,0,0;-1,-1,-1;0,0,0,0;-1,-1,-1;0,0,0,0;-1,-1,-1;0,0,0,0;|+82;1;13;-1;48,48,48,79,78,78;-3;1569^110,1569^95,1569^90,1560^100,1561^105,1561^110;15,10,8,4,7,8;-1,-1,-1;0,0,0,0;-1,-1,-1;0,0,0,0;-1,-1,-1;0,0,0,0;-1,-1,-1;0,0,0,0;-1,-1,-1;0,0,0,0;-1,-1,-1;0,0,0,0;
    public CharacterData(String characterString) {
        super(characterString);
        String characterData[] = characterString.split(";");
        this.cellId = Integer.parseInt(characterData[0]);
        this.characterId = Integer.parseInt(characterData[3]);
        this.characterName = characterData[4];
        this.characterType = Integer.parseInt(characterData[5]);
        String[] grpLevelArray = characterData[7].split(",");
        for (String s : grpLevelArray) {
            grpLevel += Integer.parseInt(s);
        }
    }
}
