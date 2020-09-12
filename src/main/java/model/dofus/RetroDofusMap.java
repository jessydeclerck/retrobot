package model.dofus;

import fr.arakne.utils.maps.DofusMap;
import fr.arakne.utils.maps.serializer.CellData;
import fr.arakne.utils.maps.serializer.DefaultMapDataSerializer;
import fr.arakne.utils.value.Dimensions;
import loader.dto.MapDto;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class RetroDofusMap implements DofusMap<RetroDofusCell> {

    @Getter
    final private int id;

    @Getter
    final private int width;

    @Getter
    final private int height;

    final private CellData[] cells;

    @Getter
    final private int x;

    @Getter
    final private int y;

    @Getter
    final private List<RetroTriggerCell> triggers = new ArrayList<>();

    @Getter
    final private List<RetroRessourceCell> ressources = new ArrayList<>();

    /**
     * interactive 1.29 GDF|cell_id|etat_id
     * <p>
     * statut 1: disponible
     * statut 2: en attente
     * statut 3: no disponible
     * statut 4: rechargé - Si vous êtes sur la carte, vous recevrez le 4
     */

    public RetroDofusMap(MapDto mapDto) {
        this.id = mapDto.getId();
        this.x = mapDto.getAbscissa();
        this.y = mapDto.getOrdinate();
        this.width = mapDto.getWidth();
        this.height = mapDto.getHeight();
        DefaultMapDataSerializer serializer = new DefaultMapDataSerializer();
        this.cells = serializer.deserialize(mapDto.getData().getValue());
        if (!mapDto.getData().getValue().isBlank()) { //exclude maps without data value
            mapDto.getTriggers().forEach(triggerDto ->
                    triggers.add(this.getTriggerCell(triggerDto.getCell()))
            );
        }
        for (int i = 0; i < cells.length; i++) {
            CellData cell = cells[i];
            if (cell.layer2().interactive()) {
                ressources.add(this.getRessourceCell(i));
            }
        }
    }

    @Override
    public int size() {
        return cells.length;
    }

    @Override
    public RetroDofusCell get(int id) {
        return new RetroDofusCell(this, cells[id], id);
    }

    public RetroTriggerCell getTriggerCell(int id) {
        return new RetroTriggerCell(this, cells[id], id);
    }

    public RetroRessourceCell getRessourceCell(int id) {
        return new RetroRessourceCell(this, cells[id], id);
    }

    @Override
    public Dimensions dimensions() {
        return new Dimensions(15, 17);
    }

}
