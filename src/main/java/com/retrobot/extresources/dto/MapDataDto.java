package com.retrobot.extresources.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class MapDataDto {

    @JsonProperty("map_id")
    private Integer mapId;
    private Integer width;
    private Integer height;
    @JsonProperty("pos_x")
    private Integer x;
    @JsonProperty("pos_y")
    private Integer y;
    private String data;
    private List<TriggerDataDto> triggers;

}
