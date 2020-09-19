package com.retrobot.scriptloader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retrobot.scriptloader.model.ScriptPath;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
public class ScriptLoader {

    public ScriptPath loadScript() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File(URLDecoder.decode(
                    new File(this.getClass().getClassLoader().getResource("script.json").getFile()).getAbsolutePath(),
                    java.nio.charset.StandardCharsets.UTF_8.toString()));
            return objectMapper.readValue(file, ScriptPath.class);

        } catch (IOException e) {
            log.error("Error while parsing script", e);
        }
        return null;
    }

}
