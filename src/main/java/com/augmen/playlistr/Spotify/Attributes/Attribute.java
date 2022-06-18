package com.augmen.playlistr.Spotify.Attributes;

import com.augmen.playlistr.Spotify.API.AudioFeature;
import com.augmen.playlistr.Spotify.TrackInfo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "ATTRIBUTE")
public class Attribute {
    @Id
    @GeneratedValue
    private long id;
    String name = "";
    String value = "";

    public static final List<String> ATTRIBUTES = Collections.unmodifiableList(List.of("acousticness", "danceability", "energy", "instrumentalness", "liveness", "loudness", "speechiness", "tempo", "valence"));
    public Attribute() {
    }

    public Attribute(String name, Object value) {
        this.name = name;
        this.value = value.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value.toString();
    }

    public boolean matches(TrackInfo trackInfo) {
        AudioFeature audioFeature = trackInfo.getAudioFeature();

        if(name.equalsIgnoreCase("key"))
            return (Integer.parseInt(value)) == audioFeature.getKey();

        if(name.equalsIgnoreCase("mode"))
            return (Integer.parseInt(value)) == audioFeature.getMode();

        return false;
    }
}
