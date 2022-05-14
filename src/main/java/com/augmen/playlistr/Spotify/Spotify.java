package com.augmen.playlistr.Spotify;


import com.augmen.playlistr.Common;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Spotify {
    private String clientid = "";
    private String clientSecret = "";

    private static Spotify spotify;
    private Spotify(){}

    public static Spotify initialize() {
        try
        {
            spotify = new Spotify();
            JsonParser parser = JsonParserFactory.getJsonParser();
            String path = Common.getResourcePath("keys.json");

            Map<String, Object> keyMap = Common.parseJsonStringForMap(Files.readString(Path.of(path)));
            spotify.clientid = (String) keyMap.getOrDefault("clientid", "");
            spotify.clientSecret = (String) keyMap.getOrDefault("clientSecret", "");

            return spotify;
        } catch (Exception e)
        {
            System.out.printf(e.getMessage());
            return null;
        }
    }

    public static String getClientid()
    {
        if(spotify == null) Spotify.initialize();
        return spotify.clientid;
    }
    public static String getClientSecret() {
        if(spotify == null) Spotify.initialize();
        return spotify.clientSecret;
    }
}
