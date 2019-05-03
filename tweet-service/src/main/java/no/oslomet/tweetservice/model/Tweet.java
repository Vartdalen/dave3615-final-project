package no.oslomet.tweetservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_tweet")
    private long id;
    private String idParent;
    private long idUser;
    private String urlImage;
    private Timestamp timestamp;
    private String text;

    public Tweet(String idParent, long idUser, String urlImage, Timestamp timestamp, String text) {
        this.idParent = idParent;
        this.idUser = idUser;
        this.urlImage = urlImage;
        this.timestamp = timestamp;
        this.text = text;
    }

    public long getId() { return id; }
    public String getIdParent() { return idParent; }
    public long getIdUser() { return idUser; }
    public String getUrlImage() { return urlImage; }
    public Timestamp getTimestamp() { return timestamp; }
    public String getText() { return text; }

    public void setId(long id) { this.id = id; }
    public void setIdParent(String idParent) { this.idParent = idParent; }
    public void setIdUser(long idUser) { this.idUser = idUser; }
    public void setUrlImage(String urlImage) { this.urlImage = urlImage; }
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }
    public void setText(String text) { this.text = text; }
}
