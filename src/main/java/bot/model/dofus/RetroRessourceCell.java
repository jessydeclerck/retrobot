package bot.model.dofus;

import fr.arakne.utils.maps.serializer.CellData;

public class RetroRessourceCell extends RetroDofusCell{
    public RetroRessourceCell(RetroDofusMap map, CellData data, int id) {
        super(map, data, id);
    }

    public int getIdRessource(){
        return this.data.layer2().number();
    }

}
