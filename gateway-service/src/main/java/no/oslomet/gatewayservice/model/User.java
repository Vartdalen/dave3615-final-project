package no.oslomet.gatewayservice.model;

import lombok.Data;

@Data
public class User {
    private long id;
    private String email;
    private String password;
    private String role;
    private String screenName;
    private String firstName;
    private String lastName;
    private String bio;

    public User() {}

    public User(long id, String email, String password, String role, String screenName, String firstName, String lastName, String bio) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.screenName = screenName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
    }
}
