package api.service;

import api.dto.ConfigurationDto;

public class ConfigurationService {

    public ConfigurationDto getCurrentConfiguration() {
        return new ConfigurationDto("Harvest");
    }
}
