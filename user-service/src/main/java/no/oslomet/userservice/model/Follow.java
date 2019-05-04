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
    private long idFollower;

    @ManyToOne
    @JoinColumn(name="id_followed")
    private User followed;

    public Follow(long idFollower, User followed) {
        this.idFollower = idFollower;
        this.followed = followed;
    }
}
