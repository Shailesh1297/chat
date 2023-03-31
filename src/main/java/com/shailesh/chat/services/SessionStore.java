package com.shailesh.chat.services;

import com.shailesh.chat.models.SessionInfo;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class SessionStore {
    private HashMap<String, SessionInfo> userSessionInfoMap = new HashMap<String, SessionInfo>();
      private HashMap<String, String> sessionUserMap = new HashMap<String, String>();


    public void addUserAndSession(SessionInfo session) {
        this.userSessionInfoMap.put(session.getUserId(), session);
        this.sessionUserMap.put(session.getSessionId(), session.getUserId());
    }

    public void removeUserAndSession(String userId) {
        this.sessionUserMap.remove(getSessionId(userId));
        this.userSessionInfoMap.remove(userId);
    }

    public HashMap<String, SessionInfo> getUserSessionInfoMap(){
        return userSessionInfoMap;
    }

    public String getSessionId(String userId) {
        return userSessionInfoMap.get(userId).getSessionId();
    }

    public String getUserId(String sessionId) {
        return sessionUserMap.get(sessionId);
    }

}
