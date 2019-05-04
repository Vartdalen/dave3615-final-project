package no.oslomet.userservice.model.database;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user")
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

    public long getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public String getScreenName() { return screenName; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getBio() { return bio; }

    public void setId(long id) { this.id = id; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
    public void setScreenName(String screenName) { this.screenName = screenName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setBio(String bio) { this.bio = bio; }
}
