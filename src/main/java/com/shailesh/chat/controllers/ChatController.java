package com.shailesh.chat.controllers;

import com.shailesh.chat.models.Message;
import com.shailesh.chat.models.SessionInfo;
import com.shailesh.chat.models.SignalMessage;
import com.shailesh.chat.services.MessageService;
import com.shailesh.chat.services.SessionStore;
import com.shailesh.chat.services.SignalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RestController
public class ChatController {

    @Autowired
    SessionStore sessionStore;

    @Autowired
    MessageService messageService;

    @Autowired
    SignalService signalService;

    @MessageMapping("/all")
    @SendTo("/topic/all")
    public Message sendToAll(Message message) {
        return message;
    }

    @MessageMapping("/private")
    public void sendToPrivate(@Payload Message message) {
        this.messageService.notifyUser(message);
    }


    @MessageMapping("/signal")
    public void webRtc(@Payload SignalMessage message) {
        this.signalService.signalUser(message);
    }

    @MessageMapping("/actives")
    public void refreshActiveUsers() {
        this.messageService.sendActiveUsers();
    }

    @EventListener
    public void onSocketConnected(SessionConnectedEvent connectedEvent) {
        String userId = null,alias = null;
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(connectedEvent.getMessage());
        GenericMessage<?> genericMessage = (GenericMessage<?>) sha.getHeader(SimpMessageHeaderAccessor.CONNECT_MESSAGE_HEADER);
        if(nonNull(genericMessage)) {
            SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.wrap(genericMessage);
            userId = getUserId(headerAccessor);
            alias = getUserAlias(headerAccessor);
        }
        sessionStore.addUserAndSession(new SessionInfo(sha.getSessionId(), userId,alias));
        this.messageService.sendActiveUsers();
    }

    @EventListener
    public void onSocketDisconnected(SessionDisconnectEvent disconnectEvent) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(disconnectEvent.getMessage());
        sessionStore.removeUserAndSession(sessionStore.getUserId(sha.getSessionId()));
        this.messageService.sendActiveUsers();
    }

    private String getUserId(SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        List<String> userIdValue = simpMessageHeaderAccessor.getNativeHeader("userId");
        return isNull(userIdValue) ? null : userIdValue.stream().findFirst().orElse(null);
    }

    private String getUserAlias(SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        List<String> userIdValue = simpMessageHeaderAccessor.getNativeHeader("alias");
        return isNull(userIdValue) ? null : userIdValue.stream().findFirst().orElse(null);
    }

}
