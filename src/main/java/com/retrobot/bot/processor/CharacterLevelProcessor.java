package com.retrobot.bot.processor;

import com.retrobot.utils.TimeUtils;
import com.retrobot.utils.automation.NativeWindowsEvents;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CharacterLevelProcessor extends PacketProcessor {
    @Override
    public void processPacket(String dofusPacket) {
        log.info("Fermeture notification niveau personnage");
        TimeUtils.sleep(1000);
        NativeWindowsEvents.clic(475, 355);
    }

    @Override
    public String getPacketId() {
        return "AN";
    }
}
