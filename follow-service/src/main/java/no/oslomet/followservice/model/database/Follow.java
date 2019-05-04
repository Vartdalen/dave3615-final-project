package no.oslomet.followservice.model.database;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "follow")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_follow")
    private long id;
    private long idFollower;
    private long idFollowed;

    public Follow(long idFollower, long followed) {
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
