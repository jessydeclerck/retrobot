package com.retrobot.extresources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GraphQLQuery {

    @JsonProperty("variables")
    private Object variables;

    @JsonProperty("query")
    private String query;

}
