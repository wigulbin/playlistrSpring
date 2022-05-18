package com.augmen.playlistr.Spotify.API;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AudioFeatures {
    @JsonProperty("audio_features")
    private List<AudioFeature> audioFeatures;

    public AudioFeatures() {
    }

    public List<AudioFeature> getAudioFeatures() {
        return audioFeatures;
    }

    public void setAudioFeatures(List<AudioFeature> audioFeatures) {
        this.audioFeatures = audioFeatures;
    }
}
