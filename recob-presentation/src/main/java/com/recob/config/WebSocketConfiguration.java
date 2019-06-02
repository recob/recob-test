package com.recob.config;

import com.recob.controller.ws.ControllerChannel;
import com.recob.controller.ws.DisplayChannel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerAdapter;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class WebSocketConfiguration {

    @Bean
    public HandlerAdapter webSocketHandlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

    @Bean
    public HandlerMapping answerHandler(ControllerChannel channel) {
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        Map<String, WebSocketHandler> urlMap = new HashMap<>();

        urlMap.put("/control/**", channel);

        mapping.setUrlMap(urlMap);
        mapping.setOrder(0);

        return mapping;
    }

    @Bean
    public HandlerMapping dashboardHandler(DisplayChannel channel) {
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        Map<String, WebSocketHandler> urlMap = new HashMap<>();

        urlMap.put("/display/**", channel);

        mapping.setUrlMap(urlMap);
        mapping.setOrder(0);

        return mapping;
    }
}
