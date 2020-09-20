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
}
