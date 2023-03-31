package com.shailesh.chat.services;

import com.shailesh.chat.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class MessageService {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    SessionStore sessionStore;

    public void notifyUser(Message message) {
        String sessionId = sessionStore.getSessionId(message.getRecipientId());
        simpMessagingTemplate.convertAndSendToUser(sessionId,"/topic/private",message,createHeader(sessionId));
    }

    public void sendActiveUsers() {
        simpMessagingTemplate.convertAndSend("/topic/users",sessionStore.getUserSessionInfoMap(),createHeader(null));
    }

    private MessageHeaders createHeader(String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        if(!isNull(sessionId)) {
            headerAccessor.setSessionId(sessionId);
        }
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }
}
