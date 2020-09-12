package service;

import loader.MapsLoader;
import loader.dto.MapsDto;
import lombok.extern.log4j.Log4j2;
import model.dofus.RetroDofusMap;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Log4j2
public class MapService {

    private final Map<Integer, RetroDofusMap> maps;

    public MapService(){
        log.info("Start");
        MapsDto mapsDto = null;
        try {
            mapsDto = MapsLoader.loadMapsData();
        } catch (URISyntaxException | IOException e) {
            log.error(e);
        }
        assert mapsDto != null;
        log.info("Starting init");
        maps = mapsDto.getMaps().stream()
                .map(RetroDofusMap::new)
                .collect(Collectors.toMap(RetroDofusMap::getId, Function.identity()));
        log.info("Init done");
    }

    public RetroDofusMap getRetroDofusMap(int id) {
        return maps.get(id);
    }

}
