package model.dofus;

import fr.arakne.utils.maps.AbstractCellDataAdapter;
import fr.arakne.utils.maps.serializer.CellData;
import lombok.Data;

@Data
public class RetroDofusCell extends AbstractCellDataAdapter<RetroDofusMap> {

    private int abscisse;

    private int ordonnee;

    public RetroDofusCell(RetroDofusMap map, CellData data, int id) {
        super(map, data, id);
    }



}
