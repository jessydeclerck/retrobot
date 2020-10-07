package com.retrobot.config;

import com.retrobot.bot.service.DeplacementService;
import com.retrobot.scriptloader.model.gathering.ScriptPath;
import com.retrobot.utils.TimeUtils;
import com.retrobot.utils.automation.NativeWindowsEvents;
import lombok.extern.slf4j.Slf4j;
import org.pcap4j.core.PcapHandle;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
public class StartBotConfig {

    private final PcapHandle pcapHandle;
    private final DeplacementService deplacementService;
    private final ScriptPath scriptPath;

    public StartBotConfig(PcapHandle pcapHandle, DeplacementService deplacementService, ScriptPath scriptPath) {
        this.pcapHandle = pcapHandle;
        this.deplacementService = deplacementService;
        this.scriptPath = scriptPath;
    }

    @PostConstruct
    public void init() {
        while (!pcapHandle.isOpen()) {
            TimeUtils.sleep(100);
        }
        log.info("Listener is open, bot path can be started");
        NativeWindowsEvents.prepareForAutomation(scriptPath.getCharacterName());
        TimeUtils.sleep(500);
        deplacementService.startDeplacement();
    }

}
