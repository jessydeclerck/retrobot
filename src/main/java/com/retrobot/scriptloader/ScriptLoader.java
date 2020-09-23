package com.retrobot.scriptloader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retrobot.scriptloader.model.ScriptPath;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Paths;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
public class ScriptLoader {

    public static ObjectMapper objectMapper = new ObjectMapper();

    public static ScriptPath loadScript(String scriptName) {
        try {
            File file = new File(URLDecoder.decode(getScriptsDir() + scriptName, UTF_8.toString()));
            log.info(file.getAbsolutePath());
            return objectMapper.readValue(file, ScriptPath.class);
        } catch (IOException e) {
            log.error("Error while parsing script", e);
        }
        return null;
    }

    public static void saveScript(String scriptName, ScriptPath scriptPath) {
        try {
            objectMapper.writeValue(Paths.get(getScriptsDir() + scriptName + ".json").toFile(), scriptPath);
        } catch (IOException e) {
            log.error("Error while saving script {}", scriptName, e);
        }
    }

    private static String getScriptsDir() {
        return getCurrentDir() + "/scripts/";
    }

    private static String getCurrentDir() {
        return new File("").getAbsolutePath();
    }

}
