package no.oslomet.gatewayservice.model.view;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class ViewTweet {
    private long id;
    private String idParent;
    private long idUser;
    private String screenNameUser;
    private String firstNameUser;
    private String lastNameUser;
    private String urlImage;
    private Timestamp timestamp;
    private String text;

    public ViewTweet() {}

    public ViewTweet(long idTweet, String idParent, long idUser, String screenNameUser, String firstNameUser, String lastNameUser, String urlImage, Timestamp timestamp, String text) {
        this.id = idTweet;
        this.idParent = idParent;
        this.idUser = idUser;
        this.screenNameUser = screenNameUser;
        this.firstNameUser = firstNameUser;
        this.lastNameUser = lastNameUser;
        this.urlImage = urlImage;
        this.timestamp = timestamp;
        this.text = text;
    }

    public long getIdTweet() { return id; }
    public String getIdParent() { return idParent; }
    public long getIdUser() { return idUser; }
    public void setScreenNameUser(String screenNameUser) {  this.screenNameUser = screenNameUser; }
    public void setFirstNameUser(String firstNameUser) { this.firstNameUser = firstNameUser; }
    public void setLastNameUser(String lastNameUser) { this.lastNameUser = lastNameUser; }
    public String getUrlImage() { return urlImage; }
    public Timestamp getTimestamp() { return timestamp; }
    public String getText() { return text; }

    public void setIdTweet(long id) { this.id = id; }
    public void setIdParent(String idParent) { this.idParent = idParent; }
    public void setIdUser(long idUser) { this.idUser = idUser; }
    public String getScreenNameUser() { return screenNameUser; }
    public String getFirstNameUser() {  return firstNameUser; }
    public String getLastNameUser() { return lastNameUser; }
    public void setUrlImage(String urlImage) { this.urlImage = urlImage; }
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }
    public void setText(String text) { this.text = text; }
}
