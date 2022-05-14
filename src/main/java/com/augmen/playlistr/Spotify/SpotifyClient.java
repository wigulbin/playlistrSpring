package com.augmen.playlistr.Spotify;

import com.augmen.playlistr.Common;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.*;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpotifyClient {

    private static final List<String> scopes = List.of("playlist-read-collaborative", "playlist-modify-public", "playlist-read-private", "playlist-modify-private", "user-library-read");
    private static final String authUrl = "https://accounts.spotify.com/authorize?";

    private String state = "";
    private final String code;
    private String accessToken = "";
    private static final String REDIRECT_URI = "http://localhost:8081/callback";
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

    public static void setupClient(HttpServletRequest request) {
        SpotifyClient client = new SpotifyClient(request);
        client.initializeAccessToken();
        request.getSession().setAttribute("client", client);
    }

    public SpotifyClient(HttpServletRequest request) {
        code = request.getParameter("code");
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
        Response response = invocationBuilder.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        String accessTokenJson = response.readEntity(String.class);

        Map<String, Object> keyMap = Common.parseJsonStringForMap(accessTokenJson);

        accessToken = (String) keyMap.getOrDefault("access_token", "");
    }

    public void getPlaylists(){
        Client client = ClientBuilder.newClient();

    }
}
