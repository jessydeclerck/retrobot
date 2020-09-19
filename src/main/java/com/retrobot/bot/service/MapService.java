package com.retrobot.bot.service;

import com.retrobot.bot.model.dofus.RetroDofusMap;
import com.retrobot.maploader.MapsLoader;
import com.retrobot.maploader.dto.MapsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MapService {

    private Map<Integer, RetroDofusMap> maps = new HashMap<>();

    @PostConstruct
    public void init() {
        log.info("Start");
        MapsDto mapsDto = null;
        try {
            mapsDto = MapsLoader.loadMapsData();
        } catch (URISyntaxException | IOException e) {
            log.error("", e);
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
