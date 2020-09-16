package model.dofus;

import fr.arakne.utils.maps.DofusMap;
import fr.arakne.utils.maps.serializer.CellData;
import fr.arakne.utils.maps.serializer.DefaultMapDataSerializer;
import fr.arakne.utils.value.Dimensions;
import loader.dto.MapDto;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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
                    triggers.add(this.getTriggerCell(triggerDto.getCell(), triggerDto.getNextMap(), triggerDto.getNextCell()))
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

    public RetroTriggerCell getTriggerCell(int id, int nextMapId, int nextCellId) {
        return new RetroTriggerCell(this, cells[id], id, nextMapId, nextCellId);
    }

    public RetroRessourceCell getRessourceCell(int id) {
        return new RetroRessourceCell(this, cells[id], id);
    }

    @Override
    public Dimensions dimensions() {
        return new Dimensions(this.getWidth(), this.getHeight());
    }

}
