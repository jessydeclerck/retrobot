package com.retrobot.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.retrobot.network.message.WSMessage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Collections;

@Slf4j
public class BotServer extends WebSocketServer {

    private static BotServer instance;

    public static void init(int port) {
        instance = new BotServer(port);
        instance.start();
    }

    public static BotServer getInstance() {
        return instance;
    }

    public BotServer(int port) {
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
        log.info(webSocket.getRemoteSocketAddress().getAddress().getHostAddress() + " connected");
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        log.info(webSocket + " disconnected");
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        broadcast(s);
        log.info((webSocket + ": " + s));
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        log.info("WebSocketError :", e);
        if (webSocket != null) {
            // some errors like port binding failed may not be assignable to a specific websocket
        }
    }

    @Override
    public void onStart() {
        log.info("WebSocketServer started");
        setConnectionLostTimeout(0);
        setConnectionLostTimeout(100);
    }

    public void emitMessage(WSMessage wsMessage) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String converteDMessage = objectMapper.writeValueAsString(wsMessage);
        broadcast(converteDMessage);
    }
}
