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
        initCoordonnees();
    }

    private void initCoordonnees() {
        int id = this.id();
        int width = this.map().getWidth();
        int impairWidth = width - 1;
        for (int i = 0; true; i++) {
            if(i == 0 && id < width) {
                this.setAbscisse(id*2);
                this.setOrdonnee(i);
                return;
            }
            if (isPair(i)) {
                id = id - width;
                if (id < impairWidth){
                    this.setAbscisse(id*2+1);
                    this.setOrdonnee(i+1);
                    return;
                }
            } else {
                id = id - impairWidth;
                if (id < width)
                    this.setAbscisse(id*2);
                    this.setOrdonnee(i+1);
                    return;
            }
        }
    }

    private boolean isPair(int i) {
        return i % 2 == 0;
    }

}
