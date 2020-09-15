package model.packet;

import lombok.Getter;
import processor.MonsterFightPositionData;
import state.CharacterState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Getter
public class FightersCoordinatesStartFightData extends PacketData {

    private int playerId;

    private int playerCellId;

    private final List<MonsterFightPositionData> monsterPositions = new ArrayList<>();

    public FightersCoordinatesStartFightData(String fullPacket) {
        super(fullPacket);
        String[] fightersCoordinatesData = fullPacket.replace("GIC|", "").split("\\|");
        Arrays.stream(fightersCoordinatesData).forEach(fighterCoordinatesData -> {
            String[] fighterData = fighterCoordinatesData.split(";");
            int fighterId = Integer.parseInt(fighterData[0]);
            int cellId = Integer.parseInt(fighterData[1]);
            if (CharacterState.getInstance().getPlayerId() == fighterId) {
                this.playerId = fighterId;
                this.playerCellId = cellId;
            } else if (fighterId < 0) {
                this.monsterPositions.add(new MonsterFightPositionData(fighterId, cellId));
            }

        });
    }
}
