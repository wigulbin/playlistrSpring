package com.augmen.playlistr.routes;

import com.augmen.playlistr.Spotify.API.Playlists;
import com.augmen.playlistr.Spotify.API.Tracks;
import com.augmen.playlistr.Spotify.Spotify;
import com.augmen.playlistr.Spotify.SpotifyClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@SessionAttributes("client")
@org.springframework.stereotype.Controller
public class Controller {
    @Value("${spring.application.name}")
    String appName;

    @GetMapping("/")
    public String homePage(Model model) {
        Spotify.initialize();
        model.addAttribute("appName", appName);
        return "home";
    }

    @GetMapping("/authorize")
    public RedirectView callback(RedirectAttributes attributes) {
        System.out.println("here");

        return new RedirectView(SpotifyClient.buildAuth());
    }

    @GetMapping("/callback")
    public RedirectView callback(@RequestParam String code, Model model) {
        SpotifyClient client = SpotifyClient.setupClient(code);
        model.addAttribute("client", client);

        return new RedirectView("/");
    }

    @GetMapping("/user")
    public String getUserInfo(HttpServletRequest request){
        SpotifyClient client = SpotifyClient.getClient(request);
        client.getUserInfo();

        return "home";
    }

    @GetMapping("/playlist")
    public String getPlaylists(Model model){
        SpotifyClient client = (SpotifyClient) model.getAttribute("client");
        Playlists playlists = client.getPlaylistsForUser();

        return "home";
    }

    //Todo fix tracks so info is populated
    @GetMapping("/tracks")
    public String getTracks(Model model){
        SpotifyClient client = (SpotifyClient) model.getAttribute("client");
        Tracks tracks = client.getTracksForCurrentUser();

        return "home";
    }
}
