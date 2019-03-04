package com.minliu.demo.config.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class WebSocketInterceptor implements HandshakeInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketInterceptor.class);

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler, Map<String, Object> map) throws Exception {
        if (request instanceof ServletServerHttpRequest){
            int id = Integer.parseInt(request.getURI().toString().split("ID=")[1]);
            logger.info("当前SESSION ID:{}",id);
            map.put("WEBSOCKET_USERID", id);
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        logger.info("WebSocketInterceptor.afterHandshake()...");
    }
}
