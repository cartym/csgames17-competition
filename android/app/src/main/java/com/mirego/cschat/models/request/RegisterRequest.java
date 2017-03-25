package com.mirego.cschat.models.request;

/**
 * Created by Carty on 2017-03-25.
 */

public class RegisterRequest {

    private String username;
    private String password;
    private String url;

    public RegisterRequest() {
    }

    public RegisterRequest(String username, String password, String url) {
        this.username = username;
        this.password = password;
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getURL(){ return url;}

    public void setURL(String url){ this.url = url;}
}
