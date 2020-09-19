package maploader.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MapDto {

    @JacksonXmlProperty(localName = "id")
    private int id;
    @JacksonXmlProperty(localName = "abscissa")
    private int abscissa;
    @JacksonXmlProperty(localName = "ordinate")
    private int ordinate;
    @JacksonXmlProperty(localName = "width")
    private int width;
    @JacksonXmlProperty(localName = "height")
    private int height;
    @JacksonXmlProperty(localName = "date")
    private String date;
    @JacksonXmlProperty(localName = "subscriber")
    private int subscriber;
    @JacksonXmlProperty(localName = "canFight")
    private int canFight;
    @JacksonXmlProperty(localName = "data")
    private DataDto data;
    @JacksonXmlProperty(localName = "key")
    private KeyDto key;
    @JacksonXmlProperty(localName = "trigger")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<TriggerDto> triggers = new ArrayList<>();
    @JacksonXmlProperty(localName = "startCells")
    @JacksonXmlElementWrapper(useWrapping = false, localName = "startCells")
    private List<StartCellsDto> startCells = new ArrayList<>();

}
