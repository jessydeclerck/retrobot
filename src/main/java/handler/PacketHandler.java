package handler;

import lombok.extern.log4j.Log4j2;
import model.dofus.RetroDofusMap;
import model.packet.ResourceUpdatePacket;
import service.MapService;

import java.util.List;

/**
 * interactive 1.29 GDF|cell_id|etat_id
 * <p>
 * statut 1: disponible
 * statut 2: en attente
 * statut 3: no disponible
 * statut 4: rechargé - Si vous êtes sur la carte, vous recevrez le 4
 * 5 déjà fauché ?
 *
 */
@Log4j2
public class PacketHandler {

    final private MapService mapService;

    public PacketHandler() {
        this.mapService = MapService.getInstance();
    }

    public void handlePacket(String dofusPackets) {
        List<String> gamePackets = List.of(dofusPackets.split("\0"));
        gamePackets.forEach(gamePacket -> {

            String packetId = gamePacket.substring(0, 3);
            if ("GDM".equals(packetId)) {
                RetroDofusMap retroDofusMap = mapService.setRetroDofusMap(getMapId(dofusPackets));
                log.info("Coordonnées : {},{}", retroDofusMap.getX(), retroDofusMap.getY());
                retroDofusMap.getRessources().forEach(retroRessourceCell -> {
                    log.info("Ressource cell id : {} - Position : {},{}", retroRessourceCell.id(), retroRessourceCell.getAbscisse(), retroRessourceCell.getOrdonnee());
                });
            }
            if ("GDF".equals(packetId)) {
                ResourceUpdatePacket resourceUpdatePacket = new ResourceUpdatePacket(gamePacket, mapService);
                log.info(resourceUpdatePacket.toString());
            }
        });
    }

    private int getMapId(String dofusPacket) {
        return Integer.parseInt(dofusPacket.split("\\|")[1]);
    }

}
