package no.oslomet.userservice.model;

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
    @ManyToOne
    @JoinColumn(name="id_follower")
    private User follower;
    private long idFollowed;

    public Follow(User follower, long idFollowed) {
        this.follower = follower;
        this.idFollowed = idFollowed;
    }
}
