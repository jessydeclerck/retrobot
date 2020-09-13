package state;

import lombok.Data;

@Data
public class CharacterState {

    private boolean gathering;
    private boolean fighting;
    private boolean moving;

    private static final CharacterState instance = new CharacterState();

    synchronized public static final CharacterState getInstance() {
        return instance;
    }



}
