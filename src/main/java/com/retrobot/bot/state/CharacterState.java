package com.retrobot.bot.state;

import com.retrobot.bot.model.dofus.RetroDofusCell;
import com.retrobot.bot.model.dofus.RetroRessourceCell;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Service
public class CharacterState {

    private boolean gathering;
    private boolean fighting;
    private boolean moving;
    private boolean goingBank;
    private int playerId;
    private RetroRessourceCell currentGatheringTarget;
    private RetroDofusCell currentCellTarget;
    private RetroDofusCell currentPlayerCell;
    private int currentPods;
    private int maxPods;

    //TODO refactor
    private RetroDofusCell currentFightCell;

    private Map<Integer, RetroDofusCell> currentFightMonsterCells = new HashMap<>();
    private int fightMonstersNumber;

}
