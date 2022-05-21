package com.augmen.playlistr.Spotify;

import com.augmen.playlistr.Spotify.API.AudioFeature;
import com.augmen.playlistr.Spotify.API.Track;
import com.augmen.playlistr.Spotify.Tag.Tag;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public String getTagids() {
        return tags.stream().map(Tag::getId).map(id -> id + "").collect(Collectors.joining(";"));
    }

    public String getTrackJson() {
        try{
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(getTrack());
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}
