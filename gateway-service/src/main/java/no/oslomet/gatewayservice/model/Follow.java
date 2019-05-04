package no.oslomet.gatewayservice.model;

import lombok.Data;

@Data
public class Follow {
    private long id;
    private long idFollower;
    private long idFollowed;

    public Follow() {}

    public Follow(long idFollower, long idFollowed) {
        this.idFollower = idFollower;
        this.idFollowed = idFollowed;
    }

    public long getId() { return id; }
    public long getIdFollower() { return idFollower; }
    public long getIdFollowed() { return idFollowed; }

    public void setId(long id) { this.id = id; }
    public void setIdFollower(long idFollower) { this.idFollower = idFollower; }
    public void setIdFollowed(long idFollowed) { this.idFollowed = idFollowed; }
}
