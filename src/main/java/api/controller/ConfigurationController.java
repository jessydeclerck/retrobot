package api.controller;

import api.dto.ConfigurationDto;
import api.service.ConfigurationService;

public class ConfigurationController {

    private ConfigurationService configurationService = new ConfigurationService();

    public ConfigurationDto getConfiguration() {
        return configurationService.getCurrentConfiguration();
    }
}
