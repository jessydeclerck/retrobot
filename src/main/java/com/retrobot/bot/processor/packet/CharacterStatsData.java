package com.retrobot.bot.processor.packet;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Value
public class CharacterStatsData extends PacketData {

    private int xp;
    private int xpDepart;
    private int xpFin;
    private int capitalStats;
    private int capitalSorts;
    private int alignement;
    private int currentHp;
    private int hpMax;
    private int currentEnergy;
    private int energyMax;


    public CharacterStatsData(String fullPacket) {
        super(fullPacket);
        String statsData = fullPacket.replace("As", "");
        String[] statsDataArray = statsData.split("\\|");
        String xpData = statsDataArray[0];
        String[] xpDataArray = xpData.split(",");
        xp = Integer.parseInt(xpDataArray[0]);
        xpDepart = Integer.parseInt(xpDataArray[1]);
        xpFin = Integer.parseInt(xpDataArray[2]);
        capitalStats = Integer.parseInt(statsDataArray[1]);
        capitalSorts = Integer.parseInt(statsDataArray[2]);
        alignement = Integer.parseInt(statsDataArray[3]);
        String[] hpDataArray = statsDataArray[5].split(",");
        currentHp = Integer.parseInt(hpDataArray[0]);
        hpMax = Integer.parseInt(hpDataArray[1]);
        String[] energyDataArray = statsDataArray[6].split(",");
        currentEnergy = Integer.parseInt(energyDataArray[0]);
        energyMax = Integer.parseInt(energyDataArray[1]);
    }

}
