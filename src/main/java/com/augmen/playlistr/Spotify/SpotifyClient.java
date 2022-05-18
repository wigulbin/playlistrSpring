package com.augmen.playlistr.Spotify;

import com.augmen.playlistr.Common;
import com.augmen.playlistr.Spotify.API.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.thymeleaf.util.ListUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.*;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class SpotifyClient {

    private static final List<String> scopes = List.of("playlist-read-collaborative", "playlist-modify-public", "playlist-read-private", "playlist-modify-private", "user-library-read");
    private static final String authUrl = "https://accounts.spotify.com/authorize?";

    private String state = "";
    private final String code;
    private String accessToken = "";
    private Playlists playlists;
    private Tracks userTracks;


    private static final String sessionKey = "client";
    private static final String REDIRECT_URI = "http://localhost:8081/callback";
    private static final String SPOTIFY_API = "https://api.spotify.com/v1/";
    private static final String TOKEN_URI = "https://accounts.spotify.com/api/token";


    public static String encodeParam(String param) {
        try {
            return URLEncoder.encode(param, StandardCharsets.UTF_8.toString());
        } catch (Exception e) {
            return "";
        }
    }

    public static String buildAuth() {
        List<String> params = new ArrayList<>();
        params.add("client_id=" + encodeParam(Spotify.getClientid()));
        params.add("state=123123123");
        params.add("scope=" + encodeParam(String.join(" ", scopes)));
        params.add("redirect_uri=" + encodeParam(REDIRECT_URI));
        params.add("response_type=code");

        return authUrl + String.join("&", params);
    }

    public static SpotifyClient setupClient(String code) {
        SpotifyClient client = new SpotifyClient(code);
        client.initializeAccessToken();
        return client;
    }

    public static SpotifyClient getClient(HttpServletRequest request) {
        return (SpotifyClient) request.getSession().getAttribute(sessionKey);
    }

    public SpotifyClient(String code) {
        this.code = code;
    }

    public void initializeAccessToken() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(TOKEN_URI);

        Form form = new Form();
        form.param("grant_type", "authorization_code");
        form.param("code", code);
        form.param("redirect_uri", REDIRECT_URI);

        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        invocationBuilder.header("Authorization", "Basic " + Base64.encodeBase64URLSafeString((Spotify.getClientid() + ":" + Spotify.getClientSecret()).getBytes(StandardCharsets.UTF_8)));
        String accessTokenJson;
        try (Response response = invocationBuilder.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE))) {
            accessTokenJson = response.readEntity(String.class);
        }

        Map<String, Object> keyMap = Common.parseJsonStringForMap(accessTokenJson);

        accessToken = (String) keyMap.getOrDefault("access_token", "");
    }

    public UserProfile getUserInfo() {
        Invocation.Builder invocationBuilder = getBuilderForCurrentUser(SPOTIFY_API + "me");

        return invocationBuilder.get(UserProfile.class);
    }

    public Playlists getPlaylistsForUser() {
        Invocation.Builder invocationBuilder = getBuilderForCurrentUser(SPOTIFY_API + "me/playlists");

        playlists = invocationBuilder.get(Playlists.class);
        return playlists;
    }

    public Tracks getTracksForCurrentUser() {
        Invocation.Builder invocationBuilder = getBuilderForCurrentUser(SPOTIFY_API + "me/tracks");

        userTracks = invocationBuilder.get(Tracks.class);
        return userTracks;
    }

    public List<Tracks> getAllTracksForCurrentUser() {
        List<Tracks> tracks = new ArrayList<>();
        Queue<String> urlQueue = new PriorityQueue<>();
        urlQueue.add(SPOTIFY_API + "me/tracks");
        Tracks userTracks = null;
//        do {
            Map<String, String> queryParams = new HashMap<>();
            queryParams.put("limit", "50");

            Invocation.Builder invocationBuilder = getBuilderForCurrentUser(urlQueue.remove(), queryParams);
            userTracks = invocationBuilder.get(Tracks.class);
            if(userTracks != null)
            {
                tracks.add(userTracks);
                if(userTracks.getNext() != null)
                    urlQueue.add(userTracks.getNext());
            }
//        }  while (userTracks != null && urlQueue.size() > 0);

        return tracks;
    }

    public List<Track> getTrackListForCurrentUser() {
        return getAllTracksForCurrentUser().stream().map(Tracks::getItems).flatMap(List::stream).map(TrackWrapper::getTrack).collect(Collectors.toList());
    }

    public Map<String, AudioFeature> analyzeTracks(List<Track> tracks) {
        Map<String, String> queryParams = new HashMap<>();

        List<AudioFeatures> audioFeatures = new ArrayList<>();
        for (List<Track> trackList : partitionList(tracks, 100)) {
            queryParams.put("ids", trackList.stream().map(Track::getId).collect(Collectors.joining(",")));
            Invocation.Builder invocationBuilder = getBuilderForCurrentUser(SPOTIFY_API + "audio-features", queryParams);
            AudioFeatures features = invocationBuilder.get(AudioFeatures.class);
            audioFeatures.add(features);
        }

        return audioFeatures.stream().map(AudioFeatures::getAudioFeatures).flatMap(List::stream).collect(Collectors.toMap(AudioFeature::getId, t->t, (t1,t2)->t1));
    }

    public List<List<Track>> partitionList(List<Track> items, int listSize) {
        List<List<Track>> trackListList = new ArrayList<>();
        int index = 0;
        while(index + listSize < items.size()){
            trackListList.add(items.subList(index, index + listSize));
            index += listSize;
        }
        if(index < items.size())
            trackListList.add(items.subList(index, items.size()));

        return trackListList;
    }


    private Invocation.Builder getBuilderForCurrentUser(String endpoint) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(endpoint);
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        invocationBuilder.header("Authorization", "Bearer " + accessToken);
        return invocationBuilder;
    }

    private Invocation.Builder getBuilderForCurrentUser(String endpoint, Map<String, String> queryParams) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(endpoint);
        for (String key : queryParams.keySet())
            target = target.queryParam(key, queryParams.get(key));

        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        invocationBuilder.header("Authorization", "Bearer " + accessToken);
        return invocationBuilder;
    }

}
