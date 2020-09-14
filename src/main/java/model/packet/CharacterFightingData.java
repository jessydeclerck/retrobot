package model.packet;

//GM|+240;1;0;60049072;Nit-Ultoko-[HOG];11;110^100;0;87;1,0,0,60049159;14a9ea;306fb;-1;,1964,309,1fd7,;1490;8;5;0;0;0;8;0;23;23;0;;
public class CharacterFightingData extends PacketData {

    int charId;
    String charName;
    int charLvl;
    int hp;
    int PA;
    int PM;
    int prctNeutre, prctTerre, prctFeu, prctEau, prctAir, prctRetraitPa, prctRetraitPm;

    public CharacterFightingData(String fullPacket) {
        super(fullPacket);
        String[] parsedPacket = fullPacket.split("\\|")[1].split(";");
        charId = Integer.parseInt(parsedPacket[3]);
        charName = parsedPacket[4];
        charLvl = Integer.parseInt(parsedPacket[8]);
        hp = Integer.parseInt(parsedPacket[14]);
        PA = Integer.parseInt(parsedPacket[15]);
        PM = Integer.parseInt(parsedPacket[16]);
        prctNeutre = Integer.parseInt(parsedPacket[17]);
        prctTerre = Integer.parseInt(parsedPacket[18]);
        prctFeu = Integer.parseInt(parsedPacket[19]);
        prctEau = Integer.parseInt(parsedPacket[20]);
        prctAir = Integer.parseInt(parsedPacket[21]);
        prctRetraitPa = Integer.parseInt(parsedPacket[22]);
        prctRetraitPm = Integer.parseInt(parsedPacket[23]);
    }


}
