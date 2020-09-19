package com.retrobot.maploader.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class StartCellsDto {
    @JacksonXmlProperty(localName = "side")
    private int side;
    @JacksonXmlProperty(localName = "cells")
    private String cells;
}
