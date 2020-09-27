package com.retrobot.bot.processor.packet;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class FightOnEndData extends PacketData {

    private List<Integer> fighterId = new ArrayList<>();

    public FightOnEndData(String fullPacket) {
        super(fullPacket);
        String[] fightEndData = fullPacket.split("\\|");
        Arrays.stream(fightEndData).forEach(fightEndElementData -> {
            String[] fighterData = fightEndElementData.split(";");
            if (fighterData.length > 1) {
                fighterId.add(Integer.valueOf(fighterData[1]));
            }
        });
    }
}
