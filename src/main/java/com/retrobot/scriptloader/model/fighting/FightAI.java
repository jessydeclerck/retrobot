package com.retrobot.scriptloader.model.fighting;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.TreeSet;

@Data
@NoArgsConstructor
public class FightAI {

    private TreeSet<Spell> spells = new TreeSet<>();

}
