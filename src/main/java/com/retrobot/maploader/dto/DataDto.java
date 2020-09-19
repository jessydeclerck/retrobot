package com.retrobot.maploader.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class DataDto {

    @JacksonXmlProperty(localName = "value")
    private String value;

}
