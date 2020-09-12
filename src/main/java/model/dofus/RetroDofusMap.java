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

    public RetroDofusMap(MapDto mapDto) {
        this.id = mapDto.getId();
        this.x = mapDto.getAbscissa();
        this.y = mapDto.getOrdinate();
        this.width = mapDto.getWidth();
        this.height = mapDto.getHeight();
        DefaultMapDataSerializer serializer = new DefaultMapDataSerializer();
        log.info(id);
        this.cells = serializer.deserialize(mapDto.getData().getValue());
        if (!mapDto.getData().getValue().isBlank()) { //exclude maps without data value
            mapDto.getTriggers().forEach(triggerDto ->
                    triggers.add(this.getTriggerCell(triggerDto.getCell()))
            );

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

    @Override
    public Dimensions dimensions() {
        return new Dimensions(15, 17);
    }


}
