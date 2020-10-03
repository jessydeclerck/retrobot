package com.retrobot.scriptloader.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.retrobot.network.message.incoming.LoadScriptMessage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class ScriptPath {

    private String characterName;

    @JsonProperty("toGather")
    private List<Integer> ressourcesToGather;

    private int startMapId;

    private int bankMapId;

    private boolean isPaysan;

    private Map<Integer, GatherMapAction> gatherPath;

    private Map<Integer, BankMapAction> bankPath;

    private Object displayData;

    public ScriptPath(LoadScriptMessage loadScriptMessage) {
        this.characterName = loadScriptMessage.getScript().getCharacterName();
        this.startMapId = loadScriptMessage.getScript().getStartMapId();
        this.bankMapId = loadScriptMessage.getScript().getBankMapId();
        this.gatherPath = loadScriptMessage.getScript().getGatherPath();
        this.bankPath = loadScriptMessage.getScript().getBankPath();
        this.ressourcesToGather = loadScriptMessage.getScript().getRessourcesToGather();
        this.displayData = loadScriptMessage.getDisplayData();
        this.isPaysan = loadScriptMessage.getScript().isPaysan();
    }
}
