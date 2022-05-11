package com.augmen.playlistr.Spotify;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SpotifyClient {

    private static final List<String> scopes = List.of("playlist-read-collaborative", "playlist-modify-public", "playlist-read-private", "playlist-modify-private", "user-library-read");
    private static final String authUrl = "https://accounts.spotify.com/authorize?";

    private String state = "";


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
        params.add("redirect_uri=" + encodeParam("http://localhost:8081/callback"));
        params.add("response_type=code");

        return authUrl + String.join("&", params);
    }

    public void getPlaylists(){
        Client client = ClientBuilder.newClient();

    }
}
