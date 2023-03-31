package com.shailesh.chat.models;

public class SessionInfo {
    private String sessionId;
    private String userId;

    private String alias;

    public SessionInfo() {
    }

    public SessionInfo(String sessionId, String userId, String alias) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.alias= alias;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
