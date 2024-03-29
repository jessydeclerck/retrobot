package com.retrobot.scriptloader.model.fighting;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Spell implements Comparable<Spell> {

    private int priority;
    private int spellPosition;
    private int castNumber;
    private int turnsBeforeRecast;
    private int range;
    private TargetEnum target;

    @Override
    public int compareTo(Spell o) {
        return Integer.compare(this.priority, o.priority);
    }

}
