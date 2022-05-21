package com.augmen.playlistr.Spotify.Attributes;

import com.augmen.playlistr.Spotify.API.AudioFeature;
import com.augmen.playlistr.Spotify.TrackInfo;

public class RangeAttribute extends Attribute{
    private Object endValue;

    public RangeAttribute(String name, float value, float endValue) {
        super(name, value);
        this.endValue = endValue;
    }

    @Override
    public boolean matches(TrackInfo trackInfo) {
        AudioFeature audioFeature = trackInfo.getAudioFeature();
        float start = Float.parseFloat(value + "");
        float end = Float.parseFloat(endValue + "");

        if(name.equalsIgnoreCase("acousticness"))
            return audioFeature.getAcousticness() <= end && audioFeature.getAcousticness() >= start;

        if(name.equalsIgnoreCase("danceability"))
            return audioFeature.getDanceability() <= end && audioFeature.getDanceability() >= start;

        if(name.equalsIgnoreCase("energy"))
            return audioFeature.getEnergy() <= end && audioFeature.getEnergy() >= start;

        if(name.equalsIgnoreCase("instrumentalness"))
            return audioFeature.getInstrumentalness() <= end && audioFeature.getInstrumentalness() >= start;

        if(name.equalsIgnoreCase("liveness"))
            return audioFeature.getLiveness() <= end && audioFeature.getLiveness() >= start;

        if(name.equalsIgnoreCase("loudness"))
            return audioFeature.getLoudness() <= end && audioFeature.getLoudness() >= start;

        if(name.equalsIgnoreCase("speechiness"))
            return audioFeature.getSpeechiness() <= end && audioFeature.getSpeechiness() >= start;

        if(name.equalsIgnoreCase("tempo"))
            return audioFeature.getTempo() <= end && audioFeature.getTempo() >= start;

        if(name.equalsIgnoreCase("valence"))
            return audioFeature.getValence() <= end && audioFeature.getValence() >= start;

        return false;
    }
}
