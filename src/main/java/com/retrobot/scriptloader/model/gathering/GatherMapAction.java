package com.retrobot.scriptloader.model.gathering;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GatherMapAction extends MapAction {

    private boolean gather;

}
