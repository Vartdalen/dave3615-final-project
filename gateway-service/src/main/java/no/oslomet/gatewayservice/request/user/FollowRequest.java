package no.oslomet.gatewayservice.request.user;

import lombok.Data;
import no.oslomet.gatewayservice.model.User;

@Data
public class FollowRequest extends UserRequest {
    public final static String REQUEST_FOLLOW = "follow";

    public FollowRequest(User user, String requestType) {
        super(user, requestType);
    }
}
