package model.packet;

import lombok.Data;


@Data
public class FightersCoordinatesStartFightData extends PacketData {

    //private final int playerId;

    //private final int cellId;

    public FightersCoordinatesStartFightData(String fullPacket) {
        super(fullPacket);
        String[] fightersCoordinatesData = fullPacket.replace("GIC|", "").split("\\|");
        /**for (String fightersCoordinatesDatum : fightersCoordinatesData) {
            
        }
        String[] playerData = playerCoordinatesData[playerCoordinatesData.length - 1].split(";");
        this.playerId = Integer.parseInt(playerData[0]);
        this.cellId = Integer.parseInt(playerData[1]);
        if (playerCoordinatesData.length > 2) {

        }*/
        //GIC|-1;81;1|-2;119;1|140043545;199;1|-3;300;1
        //TODO get monstre cell id if lenght = 3
        //GIC|-1;66;1|140043545;53;1
        //GM|+257;1;0;-3;134,134,101;-3;1570^90,1570^95,1566^90;1,1,3;-1,-1,-1;0,0,0,0;-1,-1,-1;0,0,0,0;-1,-1,-1;0,0,0,0; apparition de monstre
    }
}
