package com.retrobot.config;

import com.retrobot.bot.service.DeplacementService;
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

    public StartBotConfig(PcapHandle pcapHandle, DeplacementService deplacementService) {
        this.pcapHandle = pcapHandle;
        this.deplacementService = deplacementService;
    }

    @PostConstruct
    public void init() {
        while (!pcapHandle.isOpen()) {
            TimeUtils.sleep(100);
        }
        log.info("Listener is open, bot path can be started");
        NativeWindowsEvents.prepareForAutomation("Carlatorium - Dofus Retro v1.33.0");
        TimeUtils.sleep(500);
        deplacementService.startDeplacement();
    }

}
