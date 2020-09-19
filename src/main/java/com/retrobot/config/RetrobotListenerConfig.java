package com.retrobot.config;

import com.retrobot.bot.listener.RetroBotListener;
import com.retrobot.bot.processor.PacketProcessor;
import lombok.extern.slf4j.Slf4j;
import org.pcap4j.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

@Slf4j
@Configuration
public class RetrobotListenerConfig {

    private final List<PacketProcessor> packetProcessors;

    public RetrobotListenerConfig(List<PacketProcessor> packetProcessors) {
        this.packetProcessors = packetProcessors;
    }

    @Bean
    public PcapHandle pcapHandle() {
        PcapNetworkInterface device = null;
        InetAddress ip = null;
        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            ip = socket.getLocalAddress();
        } catch (SocketException | UnknownHostException e) {
            log.error("Error while getting host ip : ", e);
        }
        try {
            device = Pcaps.getDevByAddress(ip);
        } catch (PcapNativeException e) {
            log.error("", e);
        }
        if (device == null) {
            log.error("No device could be selected.");
            System.exit(1);
        }
        int snapshotLength = 65536; // in bytes
        int readTimeout = 50; // in milliseconds
        PcapHandle handle = null;
        String filter = "tcp port 5555";
        try {
            handle = device.openLive(snapshotLength, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, readTimeout);
            handle.setFilter(filter, BpfProgram.BpfCompileMode.OPTIMIZE);
        } catch (PcapNativeException | NotOpenException e) {
            log.error("", e);
        }
        return handle;
    }

    @Bean
    public RetroBotListener retroBotListener() {
        return new RetroBotListener(packetProcessors);
    }

    @PostConstruct
    public void init() {
        try {
            pcapHandle().loop(-1, retroBotListener());
        } catch (PcapNativeException | InterruptedException | NotOpenException e) {
            log.error("", e);
        }
    }

}
