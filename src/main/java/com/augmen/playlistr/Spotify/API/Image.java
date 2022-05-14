package com.augmen.playlistr.Spotify.API;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Image {
    private String url = "";
    private int height;
    private int width;

    public Image() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
