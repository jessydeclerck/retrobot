package com.retrobot.scriptloader.model.fighting;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.TreeSet;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FightAI {

    private TreeSet<Spell> spells = new TreeSet<>();

}
