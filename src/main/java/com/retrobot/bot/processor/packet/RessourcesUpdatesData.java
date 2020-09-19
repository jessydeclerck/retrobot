package com.retrobot.bot.processor.packet;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class RessourcesUpdatesData extends PacketData {

    private List<RessourceUpdateData> updatedRessources = new ArrayList<>();

    public RessourcesUpdatesData(String fullPacket) {
        super(fullPacket);
        String[] parsedPacket = fullPacket.split("\\|")[1].split(";");
        List<String> ressourcesStatusUpdates = new ArrayList<>(Arrays.asList(fullPacket.split("\\|")));
        ressourcesStatusUpdates.remove(0);//Remove packet id
        ressourcesStatusUpdates.forEach(update -> {
            String[] parsedUpdated = update.split(";");
            int cellId = Integer.parseInt(parsedUpdated[0]);
            RessourceStatus status = RessourceStatus.labelOfStatus(parsedPacket[1]);
            int availableFlag = Integer.parseInt(parsedUpdated[2]);
            updatedRessources.add(new RessourceUpdateData(cellId, status, availableFlag == 1));
        });
    }

    @Override
    public String getFullPacket() {
        return fullPacket;
    }

}
