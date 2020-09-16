package script.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ScriptPath {

    @JsonProperty("toGather")
    private List<Integer> ressourcesToGather;

    private MapAction startMap;

    private int bankMapId;

    private List<MapAction> gatherPath;

    private List<MapAction> bankPath;

}
