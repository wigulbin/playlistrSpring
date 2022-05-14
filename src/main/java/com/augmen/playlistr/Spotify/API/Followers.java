package com.augmen.playlistr.Spotify.API;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Followers {
    private String href = "";
    private int total;

    public Followers() {
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
