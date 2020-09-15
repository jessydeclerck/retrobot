package processor;

import lombok.Data;

@Data
public class MonsterFightPositionData  {

    private final int monsterId;
    private final int cellId;

    public MonsterFightPositionData(int monsterId, int cellId) {
        this.monsterId = monsterId;
        this.cellId = cellId;
    }
}
