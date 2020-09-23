package com.retrobot.network.message.incoming;

import com.retrobot.network.message.WSMessage;
import com.retrobot.scriptloader.model.ScriptPath;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoadScriptMessage extends WSMessage {

    public LoadScriptMessage() {
        super("script");
    }

    public LoadScriptMessage(String scriptName, ScriptPath script, String displayPath) {
        super("script");
        this.scriptName = scriptName;
        this.script = script;
        this.displayData = displayData;
    }

    private String scriptName;

    private ScriptPath script;

    private Object displayData;

}
