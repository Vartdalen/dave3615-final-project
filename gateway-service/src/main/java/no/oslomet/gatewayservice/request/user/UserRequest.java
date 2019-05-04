package no.oslomet.gatewayservice.request.user;

import lombok.Data;
import no.oslomet.gatewayservice.model.User;

@Data
public class UserRequest {
    public final static String REQUEST_FOLLOW = "follow";

    private User user;
    private String requestType;
    private User followed;

    public UserRequest() {}

    public UserRequest(User user, String requestType, User followed) {
        this.user = user;
        this.requestType = requestType;
        this.followed = followed;
    }

    public User getUser() { return user; }
    public String getRequestType() { return requestType; }

    public void setUser(User user) { this.user = user; }
    public void setRequestType(String requestType) { this.requestType = requestType; }
}
