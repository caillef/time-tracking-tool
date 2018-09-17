package models;

import java.util.UUID;

public class User {
    private String login;
    private String password;
    private UUID id;
    private String pseudo;

    public User() {

    }

    public User(String login, String password, UUID id, String pseudo) {
        this.login = login;
        this.password = password;
        this.id = id;
        this.pseudo = pseudo;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.pseudo = login;
        this.id = UUID.randomUUID();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getID() {
        return id;
    }

    public void setID(UUID id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}
