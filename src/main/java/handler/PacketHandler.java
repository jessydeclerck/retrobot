package handler;

import lombok.extern.log4j.Log4j2;
import model.dofus.RetroDofusMap;
import model.packet.NewMapPacket;
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
                NewMapPacket newMapPacket = new NewMapPacket(gamePacket);
                log.info(newMapPacket.toString());
                newMapPacket.getMap().getRessources().forEach(retroRessourceCell -> {
                    log.info("Ressource cell id : {} - Position : {},{}", retroRessourceCell.id(), retroRessourceCell.getAbscisse(), retroRessourceCell.getOrdonnee());
                });
            }
            if ("GDF".equals(packetId)) {
                ResourceUpdatePacket resourceUpdatePacket = new ResourceUpdatePacket(gamePacket);
                log.info(resourceUpdatePacket.toString());
            }
        });
    }
}
