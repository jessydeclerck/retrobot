package com.retrobot.scriptloader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retrobot.network.message.going.GetScriptMessage;
import com.retrobot.network.message.incoming.LoadScriptMessage;
import com.retrobot.scriptloader.model.ScriptPath;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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

    public static void saveScript(String scriptName, LoadScriptMessage loadScriptMessage) {
        try {
            ScriptPath scriptPath = new ScriptPath(loadScriptMessage);
            objectMapper.writeValue(Paths.get(getScriptsDir() + scriptName + ".json").toFile(), scriptPath); //TODO handle display data
        } catch (IOException e) {
            log.error("Error while saving script {}", scriptName, e);
        }
    }

    public static List<GetScriptMessage> getCurrentScripts() {
        List<GetScriptMessage> scripts = new ArrayList<>();
        try {
            Files.walk(Path.of(getScriptsDir())).forEach(scriptFile -> {
                try {
                    GetScriptMessage getScriptMessage = new GetScriptMessage();
                    ScriptPath scriptPath = objectMapper.readValue(scriptFile.toFile(), ScriptPath.class);
                    getScriptMessage.setScriptName(scriptFile.getFileName().toString());
                    getScriptMessage.setType("script");
                    getScriptMessage.setScript(scriptPath);
                    scripts.add(getScriptMessage);
                } catch (IOException e) {
                    log.error("", e);
                }
            });
        } catch (IOException e) {
            log.error("", e);
        }
        return scripts;
    }

    private static String getScriptsDir() {
        return getCurrentDir() + "/scripts/";
    }

    private static String getCurrentDir() {
        return new File("").getAbsolutePath();
    }

}
