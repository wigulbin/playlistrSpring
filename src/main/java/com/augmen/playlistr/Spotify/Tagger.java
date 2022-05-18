package com.augmen.playlistr.Spotify;

import com.augmen.playlistr.Spotify.API.AudioFeature;

import java.util.List;

public class Tagger {
    
    public static void autoTagSongs(List<TrackInfo> trackInfoList){
        List<Tag> defaultTags = Tag.generateDefault();
        for (TrackInfo trackInfo : trackInfoList) {
            for (Tag defaultTag : defaultTags) {
                if(defaultTag.getAttributes().stream().allMatch(attr -> attr.matches(trackInfo)))
                    trackInfo.addTag(defaultTag);
            }
        }
    }
}
