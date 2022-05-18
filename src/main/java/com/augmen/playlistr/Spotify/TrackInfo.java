package com.augmen.playlistr.Spotify;

import com.augmen.playlistr.Spotify.API.AudioFeature;
import com.augmen.playlistr.Spotify.API.Track;

import java.util.ArrayList;
import java.util.List;

public class TrackInfo {
    private Track track;
    private AudioFeature audioFeature;
    private List<Tag> tags = new ArrayList<>();

    public TrackInfo() {
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public AudioFeature getAudioFeature() {
        return audioFeature;
    }

    public void setAudioFeature(AudioFeature audioFeature) {
        this.audioFeature = audioFeature;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }
}
