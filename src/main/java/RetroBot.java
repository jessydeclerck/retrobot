import async.RetroTaskConsumerRunner;
import automation.NativeWindowsEvents;
import listener.RetroBotListener;
import lombok.extern.slf4j.Slf4j;
import network.BotServer;
import org.pcap4j.core.BpfProgram;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.util.NifSelector;

import java.io.IOException;
import java.net.UnknownHostException;

@Slf4j
public class RetroBot {

    public static void main(String[] args) throws UnknownHostException {

        BotServer.getInstance().start();

        NativeWindowsEvents.prepareForAutomation("Carlatorium - Dofus Retro v1.32.1");
        PcapNetworkInterface device = null;

        try {
            device = new NifSelector().selectNetworkInterface();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("You chose: " + device);

        if (device == null) {
            log.error("No device chosen.");
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
        // Create a listener that defines what to do with the received packets
        RetroTaskConsumerRunner.getInstance().startEventConsumer();
        PacketListener listener = new RetroBotListener();
        // Tell the handle to loop using the listener we created
        try {
            handle.loop(-1, listener);
        } catch (InterruptedException | PcapNativeException | NotOpenException e) {
            log.error("", e);
        }
    }

}
