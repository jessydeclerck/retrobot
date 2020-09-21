package com.retrobot.scriptloader.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ScriptPath {

    private String characterName;

    @JsonProperty("toGather")
    private List<Integer> ressourcesToGather;

    private int startMapId;

    private int bankMapId;

    private Map<Integer, GatherMapAction> gatherPath;

    private Map<Integer, BankMapAction> bankPath;

}
