package script.model;

import lombok.Data;

@Data
public class MapAction {

    private Integer mapId;

    private String pos;

    private Integer nextMapId;

    private boolean gather;

    private Integer bankMapId;

}
