package no.oslomet.userservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_user")
    private long id;
    private String email;
    private String password;
    private String role;
    private String screenName;
    private String firstName;
    private String lastName;
    private String bio;

    public User(String email, String password, String role, String screenName, String firstName, String lastName, String bio) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.screenName = screenName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
    }
}
