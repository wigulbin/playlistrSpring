package com.augmen.playlistr.Spotify.API;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AudioFeature {
    private float acousticness;
    @JsonProperty("analysis_url")
    private String analysisUrl = "";
    private float danceability;
    @JsonProperty("duration_ms")
    private int durationMs;
    private float energy;
    private String id = "";
    private float instrumentalness;
    private int key;
    private float liveness;
    private float loudness;
    private int mode;
    private float speechiness;
    private float tempo;
    @JsonProperty("time_signature")
    private int timeSignature;
    @JsonProperty("track_href")
    private String trackHref = "";
    private String type = "";
    private String uri = "";
    private float valence;

    public AudioFeature() {
    }

    public String getKeyName() {
        return switch (key) {
            case 0 -> "C";
            case 1 -> "C#";
            case 2 -> "D";
            case 3 -> "D#";
            case 4 -> "E";
            case 5 -> "F";
            case 6 -> "F#";
            case 7 -> "G";
            case 8 -> "G#";
            case 9 -> "A";
            case 10 -> "A#";
            case 11 -> "B";
            default -> key + "";
        };
    }

    public String getModeName(){
        return switch (mode) {
            case 0 -> "Minor";
            case 1 -> "Major";
            default -> key + "";
        };
    }


    public float getAcousticness() {
        return acousticness;
    }

    public void setAcousticness(float acousticness) {
        this.acousticness = acousticness;
    }

    public String getAnalysisUrl() {
        return analysisUrl;
    }

    public void setAnalysisUrl(String analysisUrl) {
        this.analysisUrl = analysisUrl;
    }

    public float getDanceability() {
        return danceability;
    }

    public void setDanceability(float danceability) {
        this.danceability = danceability;
    }

    public int getDurationMs() {
        return durationMs;
    }

    public void setDurationMs(int durationMs) {
        this.durationMs = durationMs;
    }

    public float getEnergy() {
        return energy;
    }

    public void setEnergy(float energy) {
        this.energy = energy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getInstrumentalness() {
        return instrumentalness;
    }

    public void setInstrumentalness(float instrumentalness) {
        this.instrumentalness = instrumentalness;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public float getLiveness() {
        return liveness;
    }

    public void setLiveness(float liveness) {
        this.liveness = liveness;
    }

    public float getLoudness() {
        return loudness;
    }

    public void setLoudness(float loudness) {
        this.loudness = loudness;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public float getSpeechiness() {
        return speechiness;
    }

    public void setSpeechiness(float speechiness) {
        this.speechiness = speechiness;
    }

    public float getTempo() {
        return tempo;
    }

    public void setTempo(float tempo) {
        this.tempo = tempo;
    }

    public int getTimeSignature() {
        return timeSignature;
    }

    public void setTimeSignature(int timeSignature) {
        this.timeSignature = timeSignature;
    }

    public String getTrackHref() {
        return trackHref;
    }

    public void setTrackHref(String trackHref) {
        this.trackHref = trackHref;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public float getValence() {
        return valence;
    }

    public void setValence(float valence) {
        this.valence = valence;
    }
}
