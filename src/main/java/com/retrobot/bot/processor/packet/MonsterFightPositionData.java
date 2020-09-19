package com.retrobot.bot.processor.packet;

import lombok.Value;

@Value
public class MonsterFightPositionData  {

    int monsterId;
    int cellId;

    public MonsterFightPositionData(int monsterId, int cellId) {
        this.monsterId = monsterId;
        this.cellId = cellId;
    }
}
