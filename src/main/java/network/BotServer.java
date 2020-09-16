package network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import network.message.WSMessage;
import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import service.RecolteService;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Collections;

public class BotServer extends WebSocketServer {

    private static final BotServer instance = new BotServer(new InetSocketAddress(80));

    synchronized public static BotServer getInstance() {
        return instance;
    }


    private BotServer(int port) throws UnknownHostException {
        super(new InetSocketAddress(port));
    }

    private BotServer(InetSocketAddress address) {
        super(address);
    }

    private BotServer(int port, Draft_6455 draft) {
        super(new InetSocketAddress(port), Collections.<Draft>singletonList(draft));
    }

    @SneakyThrows
    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        System.out.println(
                webSocket.getRemoteSocketAddress().getAddress().getHostAddress() + " entered the room!");
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        System.out.println(webSocket + " has left the room!");
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        broadcast(s);
        System.out.println(webSocket + ": " + s);
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        e.printStackTrace();
        if (webSocket != null) {
            // some errors like port binding failed may not be assignable to a specific websocket
        }
    }

    @Override
    public void onStart() {
        System.out.println("Server started!");
        setConnectionLostTimeout(0);
        setConnectionLostTimeout(100);
    }

    public void emitMessage(WSMessage wsMessage) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String converteDMessage = objectMapper.writeValueAsString(wsMessage);
        broadcast(converteDMessage);
    }
}
