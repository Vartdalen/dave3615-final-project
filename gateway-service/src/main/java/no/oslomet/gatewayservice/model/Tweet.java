package no.oslomet.gatewayservice.model;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class Tweet {
    private long id;
    private String idParent;
    private long idUser;
    private String urlImage;
    private Timestamp timestamp;
    private String text;

    public  Tweet() {}

    public Tweet(String idParent, long idUser, String urlImage, Timestamp timestamp, String text) {
        this.idParent = idParent;
        this.idUser = idUser;
        this.urlImage = urlImage;
        this.timestamp = timestamp;
        this.text = text;
    }
}
