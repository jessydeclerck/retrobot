package com.retrobot.bot.state;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Setter
@Getter
@Service
public class FightState {

    boolean cardsHidden;
    boolean challengeHidden;
    boolean tacticModeActivated;
    int turnNb;//TODO increment when turn starts

    public void resetState() {
        cardsHidden = false;
        turnNb = 0;
    }

    public void incrementTurnNb() {
        turnNb++;
    }
}
