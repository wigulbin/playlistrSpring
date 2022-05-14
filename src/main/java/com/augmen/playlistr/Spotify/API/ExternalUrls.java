package com.augmen.playlistr.Spotify.API;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ExternalUrls {
    private String spotify = "";

    public ExternalUrls() {
    }

    public String getSpotify() {
        return spotify;
    }

    public void setSpotify(String spotify) {
        this.spotify = spotify;
    }
}
