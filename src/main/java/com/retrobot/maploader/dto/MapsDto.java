package com.retrobot.maploader.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MapsDto {

    @JacksonXmlProperty(localName = "map")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<MapDto> maps = new ArrayList<>();

}
