package com.retrobot.extresources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retrobot.extresources.dto.MapsDataDto;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GraphQLService {

    private String getMapsQuery = "query GetMaps {\n" +
            "  maps {\n" +
            "    map_id\n" +
            "    pos_x\n" +
            "    pos_y\n" +
            "    width\n" +
            "    height\n" +
            "    data\n" +
            "    triggers {\n" +
            "      cell_id\n" +
            "      next_cell\n" +
            "      next_map\n" +
            "    }\n" +
            "    \n" +
            "  }\n" +
            "}\n";

    //TODO refacto
    @Cacheable("maps")
    public MapsDataDto getMaps() {
        RestTemplate restTemplate = new RestTemplateBuilder()
                .additionalMessageConverters(new MappingJackson2HttpMessageConverter(new ObjectMapper()))
                .build();
        GraphQLQuery getMapsGraphQLQuery = new GraphQLQuery();
        getMapsGraphQLQuery.setQuery(getMapsQuery);
        ResponseEntity<GraphQLMapsDataResponse> result = restTemplate.postForEntity("http://vps408293.ovh.net:8081/v1/graphql", getMapsGraphQLQuery, GraphQLMapsDataResponse.class);
        return result.getBody().getData();
    }

}
