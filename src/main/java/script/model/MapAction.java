package script.model;

import lombok.Data;

@Data
public abstract class MapAction {

    private String pos;

    private DirectionEnum direction;

    private Integer nextMapId;

}
