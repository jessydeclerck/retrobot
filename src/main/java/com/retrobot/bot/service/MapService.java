package com.retrobot.bot.service;

import com.retrobot.bot.model.dofus.RetroDofusMap;
import com.retrobot.extresources.GraphQLService;
import com.retrobot.extresources.dto.MapsDataDto;
import com.retrobot.scriptloader.FileLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MapService {

    private Map<Integer, RetroDofusMap> maps = new HashMap<>();

    private final GraphQLService graphQLService;

    public MapService(GraphQLService graphQLService) {
        this.graphQLService = graphQLService;
    }

    @PostConstruct
    public void init() {
        log.info("Start");
        MapsDataDto mapsData;
        try {
            mapsData = FileLoader.loadMaps();
        } catch (IOException e) {
            log.info("Couldn't retrieve maps from file, calling api");
            mapsData = graphQLService.getMaps();
            FileLoader.saveMaps(mapsData);
        }
        log.info("Starting init");
        maps = mapsData.getMaps().stream()
                .map(RetroDofusMap::new)
                .collect(Collectors.toMap(RetroDofusMap::getId, Function.identity()));
        log.info("Init done");
    }

    public RetroDofusMap getRetroDofusMap(int id) {
        return maps.get(id);
    }

}
