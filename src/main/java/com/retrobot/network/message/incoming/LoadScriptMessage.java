package com.retrobot.network.message.incoming;

import com.retrobot.network.message.WSMessage;
import com.retrobot.scriptloader.model.gathering.ScriptPath;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoadScriptMessage extends WSMessage {

    public LoadScriptMessage() {
        super("script");
    }

    private String scriptName;

    private ScriptPath script;

    private Object displayData;

}
