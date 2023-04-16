package com.shailesh.chat.services;

import com.shailesh.chat.models.SignalMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SignalService {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    SessionStore sessionStore;

    public void signalUser(SignalMessage signal){
        simpMessagingTemplate.convertAndSendToUser(signal.getReceiver(),"/topic/signal",signal,sessionStore.createHeader(signal.getReceiver()));
    }
}
