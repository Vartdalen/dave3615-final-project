package no.oslomet.userservice.request;

import lombok.Data;
import no.oslomet.userservice.model.User;

@Data
public class UserRequest {
    public final static String REQUEST_FOLLOW = "follow";

    private User user;
    private String requestType;

    public UserRequest() {}

    public UserRequest(User user, String requestType) {
        this.user = user;
        this.requestType = requestType;
    }

    public User getUser() { return user; }
    public String getRequestType() { return requestType; }

    public void setUser(User user) { this.user = user; }
    public void setRequestType(String requestType) { this.requestType = requestType; }
}
