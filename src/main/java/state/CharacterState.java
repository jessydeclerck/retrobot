package state;

import lombok.Data;
import model.dofus.RetroDofusCell;
import model.dofus.RetroRessourceCell;

@Data
public class CharacterState {

    private boolean gathering;
    private boolean fighting;
    private boolean moving;
    private int playerId;
    private RetroRessourceCell currentGatheringTarget;
    private RetroDofusCell currentCellTarget;
    private RetroDofusCell currentFightCell;

    private static final CharacterState instance = new CharacterState();

    synchronized public static final CharacterState getInstance() {
        return instance;
    }

    public void setFighting(boolean fighting) {
        if (fighting) {
            MapState.getInstance().stopRecolte();
        }
        this.fighting = fighting;
    }


}
