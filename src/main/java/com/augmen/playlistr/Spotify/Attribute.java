package com.augmen.playlistr.Spotify;

import com.augmen.playlistr.Spotify.API.AudioFeature;

public class Attribute {
    String name = "";
    Object value = "";

    public Attribute() {
    }

    public Attribute(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean matches(TrackInfo trackInfo) {
        AudioFeature audioFeature = trackInfo.getAudioFeature();

        if(name.equalsIgnoreCase("key"))
            return ((Integer) value) == audioFeature.getKey();

        if(name.equalsIgnoreCase("mode"))
            return ((Integer) value) == audioFeature.getMode();

        return false;
    }
}
