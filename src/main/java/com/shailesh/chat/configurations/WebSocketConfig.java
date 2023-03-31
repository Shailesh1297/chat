package com.shailesh.chat.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig  implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
       registry.enableSimpleBroker("/topic");
       registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat-ws")
                .setAllowedOriginPatterns("*")
                .withSockJS()
                .setInterceptors(httpSessionHandshakeInterceptor());
    }

    @Bean
    public HttpSessionHandshakeInterceptor httpSessionHandshakeInterceptor(){
        return new HttpSessionHandshakeInterceptor();
    }

}
