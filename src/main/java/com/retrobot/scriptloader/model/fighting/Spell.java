package com.retrobot.scriptloader.model.fighting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Spell implements Comparable<Spell> {

    private int priority;
    private int spellPosition;
    private int castNumber;
    private int turnsBeforeRecast;
    private TargetEnum target;

    @Override
    public int compareTo(Spell o) {
        return Integer.compare(this.priority, o.priority);
    }

}
