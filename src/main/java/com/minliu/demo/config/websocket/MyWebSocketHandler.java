package com.minliu.demo.config.websocket;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class MyWebSocketHandler implements WebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(MyWebSocketHandler.class);

    private static final Map<Integer, WebSocketSession> users = new HashMap<>();

    /**
     * 连接成功
     *
     * @param webSocketSession session
     * @throws Exception 异常
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        logger.info("成功建立连接...");
        Integer id = Integer.parseInt(webSocketSession.getUri().toString().split("ID=")[1]);
        logger.info("ID:{}", id);
        users.put(id, webSocketSession);
        webSocketSession.sendMessage(new TextMessage("成功建立socket连接..."));
        logger.info("SESSION:{}", webSocketSession);
        logger.info("当前在线人数:{}", users.size());
    }

    /**
     * 处理接受客户端信息
     *
     * @param webSocketSession session
     * @param webSocketMessage message
     * @throws Exception 异常
     * @see org.springframework.web.socket.WebSocketSession
     * @see org.springframework.web.socket.WebSocketMessage
     */
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject((String) webSocketMessage.getPayload());
        logger.info("ID:{}", jsonObject.getString("id"));
        logger.info("MESSAGE:{}", jsonObject.getString("message"));
        webSocketSession.sendMessage(new TextMessage("服务端收到了..."));
    }

    /**
     * 处理传输错误
     *
     * @param webSocketSession session
     * @param throwable        throwable
     */
    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if (webSocketSession != null && webSocketSession.isOpen()) {
            webSocketSession.close();
            users.remove(getClientId(webSocketSession));
        }
        logger.error("连接出错...", throwable);
    }

    /**
     * 连接关闭
     *
     * @param webSocketSession session
     * @param closeStatus      status
     * @throws Exception 异常
     */
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        logger.info("连接已关闭:{}", closeStatus);
        users.remove(getClientId(webSocketSession));
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 发送消息给指定用户
     *
     * @param clientId 客户Id
     * @param message  消息
     * @return 是否成功
     */
    public boolean sendMessageToUser(Integer clientId, TextMessage message) {
        if (users.get(clientId) == null) return false;
        WebSocketSession session = users.get(clientId);
        if (session.isOpen()) {
            logger.info("SEND MESSAGE:{}", message.getPayload());
            try {
                session.sendMessage(message);
            } catch (IOException e) {
                logger.error("发送失败...", e);
                return false;
            }
        }
        return true;
    }


    /**
     * 发送消息给所有在线用户
     *
     * @param message 消息
     * @return 是否全部成功
     */
    public boolean sendMessageToAllUsers(TextMessage message) {
        boolean allSuccess = true;
        Set<Integer> ids = users.keySet();
        for (Integer id : ids) {
            WebSocketSession session = users.get(id);
            if (session.isOpen()) {
                try {
                    session.sendMessage(message);
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                    allSuccess = false;
                }
            }
        }
        return allSuccess;
    }

    private Integer getClientId(WebSocketSession session) {
        try {
            return (Integer) session.getAttributes().get("WEBSOCKET_USERID");
        } catch (Exception e) {
            return null;
        }
    }
}
