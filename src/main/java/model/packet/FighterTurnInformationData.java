package model.packet;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class FighterTurnInformationData {

    private final int fighterId;

    private final int unknown;

    private final int currentHp;

    private final int pa;

    private final int pm;

    private final int cellId;

    private final int hpMax;

}
