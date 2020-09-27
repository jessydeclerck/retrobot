package com.retrobot.extresources.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TriggerDataDto {

    @JsonProperty("cell_id")
    private Integer cellId;
    @JsonProperty("next_map")
    private Integer nextMap;
    @JsonProperty("next_cell")
    private Integer nextCell;

}
