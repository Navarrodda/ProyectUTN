package utn.project.session;

import utn.project.domain.User;

import java.util.Date;

public class Session {

    String token;
    User loggedUser;
    Date lastEvent;

    public Session(String token, User loggedUser, Date lastEvent) {
        this.token = token;
        this.loggedUser = loggedUser;
        this.lastEvent = lastEvent;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public Date getLastEvent() {
        return lastEvent;
    }

    public void setLastEvent(Date lastEvent) {
        this.lastEvent = lastEvent;
    }
}
