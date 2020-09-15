package model.packet;

import lombok.Data;
import lombok.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Value
public class FightTurnInformationData extends PacketData {

    private final List<FighterTurnInformationData> fighterTurnInformationDataList = new ArrayList<>();


    public FightTurnInformationData(String fullPacket) {
        super(fullPacket);
        String[] fightTurnInformationData = fullPacket.replace("GTM|", "").split("\\|");
        Arrays.stream(fightTurnInformationData).forEach(fighterInformations -> {
            String[] fighterData = fighterInformations.split(";");
            int fighterId = Integer.parseInt(fighterData[0]);
            int unknown = Integer.parseInt(fighterData[1]);
            int currentHp = Integer.parseInt(fighterData[2]);
            int pa = Integer.parseInt(fighterData[3]);
            int pm = Integer.parseInt(fighterData[4]);
            int cellId = Integer.parseInt(fighterData[5]);
            int hpMax = Integer.parseInt(fighterData[7]);
            this.fighterTurnInformationDataList.add(new FighterTurnInformationData(fighterId, unknown, currentHp, pa, pm, cellId, hpMax));
        });

    }

    //GTM|-1;0;16;4;9;210;;16|-2;0;8;5;3;268;;69|140043545;0;190;6;3;283;;262

}
