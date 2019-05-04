package no.oslomet.gatewayservice.request.user;

import lombok.Data;
import no.oslomet.gatewayservice.model.User;

@Data
public class UserRequest {

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
