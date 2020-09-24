package com.retrobot.network.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.retrobot.RetroBot;
import com.retrobot.network.message.incoming.LoadScriptMessage;
import com.retrobot.scriptloader.ScriptLoader;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageHandler {

    public static void handleMessage(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = ScriptLoader.objectMapper;
        final ObjectNode node = new ObjectMapper().readValue(message, ObjectNode.class);
        String type = node.get("type").asText(); //TODO refacto
        if ("stop".equals(type)) {
            RetroBot.stop();
        } else if ("script".equals(type)) {
            LoadScriptMessage loadScriptMessage = null;
            try {
                loadScriptMessage = objectMapper.readValue(message, LoadScriptMessage.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            ScriptLoader.saveScript(loadScriptMessage.getScriptName(), loadScriptMessage);
            RetroBot.start(new String[]{"--script=" + loadScriptMessage.getScriptName() + ".json"});
        }

    }

}
