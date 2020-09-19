package com.retrobot.config;

import com.retrobot.scriptloader.ScriptLoader;
import com.retrobot.scriptloader.model.ScriptPath;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScriptLoaderConfig {

    @Bean
    public ScriptPath scriptPath() {
        return new ScriptLoader().loadScript();
    }

}
