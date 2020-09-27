package com.retrobot.config;

import com.retrobot.scriptloader.FileLoader;
import com.retrobot.scriptloader.model.ScriptPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
@Slf4j
public class ScriptLoaderConfig {

    @Bean
    public ScriptPath scriptPath(ApplicationArguments applicationArguments) {
        String scriptName = Optional.ofNullable(applicationArguments.getOptionValues("script")).orElse(List.of("script.json")).get(0);
        log.info("Loading {} script path...", scriptName);
        return FileLoader.loadScript(scriptName);
    }

}
