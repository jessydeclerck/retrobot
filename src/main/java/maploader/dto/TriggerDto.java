package maploader.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class TriggerDto {

    @JacksonXmlProperty(localName = "id")
    private int id;

    @JacksonXmlProperty(localName = "cell")
    private int cell;

    @JacksonXmlProperty(localName = "next_map")
    private int nextMap;

    @JacksonXmlProperty(localName = "next_cell")
    private int nextCell;

}
