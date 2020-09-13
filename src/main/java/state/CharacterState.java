package state;

import lombok.Data;
import model.dofus.RetroRessourceCell;

@Data
public class CharacterState {

    private boolean gathering;
    private boolean fighting;
    private boolean moving;
    private int playerId;
    private RetroRessourceCell currentGatheringTarget;

    private static final CharacterState instance = new CharacterState();

    synchronized public static final CharacterState getInstance() {
        return instance;
    }



}
