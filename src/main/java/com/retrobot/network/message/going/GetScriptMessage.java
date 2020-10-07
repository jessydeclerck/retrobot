package com.retrobot.network.message.going;

import com.retrobot.network.message.WSMessage;
import com.retrobot.scriptloader.model.gathering.ScriptPath;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class GetScriptMessage extends WSMessage {


    private String scriptName;

    private ScriptPath script;


}
