package ca.saultcollege.Lab3.data;


public class AccessToken {
    private String accessToken;

    public AccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public AccessToken() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}

